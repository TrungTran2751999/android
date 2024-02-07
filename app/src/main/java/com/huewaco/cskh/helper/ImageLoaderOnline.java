package com.huewaco.cskh.helper;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
public class ImageLoaderOnline {
	static private ImageLoaderOnline _instance;

	static public ImageLoaderOnline getInstance() {
		if (_instance == null) {
			_instance = new ImageLoaderOnline();
		}
		return _instance;
	}

	private static HashMap<String, Bitmap> _urlToBitmap;
	private Queue<Group> _queue;
	private DownloadThread _thread;
	private Bitmap _missing;
	private boolean _busy;

	/**
	 * Constructor
	 */
	public ImageLoaderOnline() {
		_urlToBitmap = new HashMap<String, Bitmap>();
		_queue = new LinkedList<Group>();
		_busy = false;
	}

	public static Bitmap get(String url) {
		return _urlToBitmap.get(url);
	}

	public void load(TextView v,ProgressBar pg, ImageView image, String url) {
		load(v,pg, image, url, false);
	}

	public void load(TextView v,ProgressBar pg, ImageView image, String url, boolean cache) {
		Bitmap bm=_urlToBitmap.get(url);

		if (bm!= null) {
			if (image != null) {

				image.setEnabled(true);
				image.setImageBitmap(bm);
			}
			if(pg!=null)
				pg.setVisibility(View.GONE);
			if(v!=null)  v.setVisibility(View.VISIBLE);
		}

		else {

			// image.setBackgroundResource(R.drawable.loading);
			//image.setImageResource(R.drawable.ic_user);
			if(pg!=null)pg.setVisibility(View.VISIBLE);
			image.setEnabled(false);
			queue(v,pg, image, url, cache);

		}
	}

	public void queue(TextView v,ProgressBar pg, ImageView image, String url, boolean cache) {
		Iterator<Group> it = _queue.iterator();

		if (image != null) {
			while (it.hasNext()) {
				if (it.next().image.equals(image)) {
					it.remove();
					break;
				}
			}
		} else if (url != null) {
			while (it.hasNext()) {
				if (it.next().url.equals(url)) {
					it.remove();
					break;
				}
			}
		}

		_queue.add(new Group(v,pg, image, url, null, cache));
		loadNext();
	}

	public void clearQueue() {
		_queue = new LinkedList<Group>();
	}

	public static void clearCache() {
		_urlToBitmap.clear();
	}

	public void cancel() {
		//	_queue.clear();

		if (_thread != null) {
			_thread.disconnect();
			_thread = null;
		}
	}

	public void setMissingBitmap(Bitmap bitmap) {
		_missing = bitmap;
	}

	private void loadNext() {
		Iterator<Group> it = _queue.iterator();

		if (!_busy && it.hasNext()) {
			_busy = true;
			Group group = it.next();
			it.remove();

			// double check image availability
			if (_urlToBitmap.get(group.url) != null) {
				if (group.image != null) {
					group.image.setEnabled(true);
					group.image.setImageBitmap(_urlToBitmap.get(group.url));
					if(group.pg!=null)
						group.pg.setVisibility(View.GONE);
					if(group.v!=null )
						group.v.setVisibility(View.VISIBLE);

				}

				_busy = false;
				loadNext();
			} else {
				_thread = new DownloadThread(group);
				_thread.start();
			}
		}
	}

	private void onLoad() {
		if (_thread != null) {
			Group group = _thread.group;

			if (group.bitmap != null) {
				if (group.cache) {
					_urlToBitmap.put(group.url, group.bitmap);
				}

				if (group.image != null) {
					group.image.setEnabled(true);
					group.image.setImageBitmap(group.bitmap);
					if(group.pg!=null)
						group.pg.setVisibility(View.GONE);
					if(group.v!=null)
						group.v.setVisibility(View.VISIBLE);
				}

			} else if (_missing != null) {
				if (group.image != null) {
					group.image.setImageBitmap(_missing);
				}
			}
		}

		_thread = null;
		_busy = false;
		loadNext();
	}

	private class Group {
		public Group(TextView v,ProgressBar pg, ImageView image, String url,
					 Bitmap bitmap, boolean cache) {
			this.image = image;
			this.url = url;
			this.bitmap = bitmap;
			this.cache = cache;
			this.pg = pg;
			this.v=v;
		}

		public ImageView image;
		public String url;
		public Bitmap bitmap;
		public ProgressBar pg;
		public TextView v;
		public boolean cache;
	}

	private class DownloadThread extends Thread {
		final Handler threadHandler = new Handler();
		final Runnable threadCallback = new Runnable() {
			public void run() {
				onLoad();
			}
		};
		private HttpURLConnection _conn;
		public Group group;

		public DownloadThread(Group group) {
			this.group = group;
		}

		@Override
		public void run() {
			InputStream inStream = null;
			_conn = null;

			try {
				_conn = (HttpURLConnection) new URL(group.url).openConnection();
				_conn.setDoInput(true);
				_conn.connect();
				_conn.setReadTimeout(7000);
				inStream = _conn.getInputStream();
				group.bitmap = scaleDown(BitmapFactory.decodeStream(inStream),GlobalVariable.MAXIMAGESIZE,true);
				inStream.close();
				_conn.disconnect();
				inStream = null;
				_conn = null;
			} catch (Exception ex) {
				// nothing
			}

			if (inStream != null) {
				try {
					inStream.close();
				} catch (Exception ex) {
				}
			}
			disconnect();
			inStream = null;
			_conn = null;
			threadHandler.post(threadCallback);
		}

		public void disconnect() {
			if (_conn != null) {
				_conn.disconnect();
			}
		}
	}
	public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
								   boolean filter) {
		float ratio = Math.min(
				(float) maxImageSize / realImage.getWidth(),
				(float) maxImageSize / realImage.getHeight());
		int width = Math.round((float) ratio * realImage.getWidth());
		int height = Math.round((float) ratio * realImage.getHeight());

		Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
				height, filter);
		return newBitmap;
	}
}
