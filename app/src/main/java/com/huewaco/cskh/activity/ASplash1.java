package com.huewaco.cskh.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.helper.ShareReferenceConfig;
import com.huewaco.cskh.objects.GcsObject;
import com.huewaco.cskh.objects.Img2TextObj;
import com.huewaco.cskh.objects.KhachHangObj;
import com.huewaco.cskh.webservice.objects.GetTextFromBitmapAPIResponse;
import com.huewaco.cskh.webservice.processing.ResultFromWebservice;

import java.util.ArrayList;

public class ASplash1 extends Activity implements OnClickListener {
	protected int time = 1500;
	boolean t = true;
	private Thread introThread;
	private ViewGroup id_ly_splashmain;
	private TextView id_tv_cty,id_tv_title1,id_tv_title2;

	@SuppressLint("LongLogTag")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		id_ly_splashmain = (ViewGroup) findViewById(R.id.id_ly_splashmain);

		id_ly_splashmain.setOnClickListener(this);


		//new GCSTaskTextAPI().execute();
		showSplash();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			t = false;
			checkLogin();
		}
		return true;
	}

	public void showSplash() {
		introThread = new Thread() {
			@Override
			public void run() {
				try {
					synchronized (this) {

						wait(time);
					}

					if (t) {
						Message msg = mHandler1.obtainMessage(0);
						mHandler1.sendMessage(msg);

					}

				} catch (Exception e) {
				}
			}
		};

		introThread.start();
	}

	private Handler mHandler1 = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				if (t) {
					id_ly_splashmain.performClick();
				}
			}
			super.handleMessage(msg);
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			t = false;
			this.finish();
			System.gc();
			System.runFinalization();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_ly_splashmain:
			if (t) {
				checkLogin();
			}
			break;
		default:
			break;
		}

	}
	private void jump(){
		Intent i = new Intent(getBaseContext(), AIntro2.class);// ALogin
		startActivity(i);
		if(GlobalVariable.IS_ANIMATION_ACTIVITY){
			overridePendingTransition(R.anim.next_1, R.anim.next_2);
		}

		finish();
	}
	private void checkLogin(){
		ArrayList<KhachHangObj> mArrKH = CommonHelper.readKhachHangObjects(getApplicationContext(), GlobalVariable.KHACH_HANG_FILE);
		if(mArrKH != null && mArrKH.size()>0){
			GlobalVariable.KHACH_HANG = mArrKH.get(0);
			GlobalVariable.KHACH_HANG_CURRENT = mArrKH.get(0);
			mArrKH.remove(0);
			GlobalVariable.mArrKHang = mArrKH;

			if(CommonHelper.checkValidString(GlobalVariable.KHACH_HANG.getDeviceToken())){
				GlobalVariable.DEVICE_TOKEN =GlobalVariable.KHACH_HANG.getDeviceToken();
			}
			if(CommonHelper.checkValidString(ShareReferenceConfig.instance(getApplicationContext()).getAccessToken())
					&& CommonHelper.checkValidString(ShareReferenceConfig.instance(getApplicationContext()).getTokenType())){
				GlobalVariable.LOGIN_ACCESS_TOKEN = ShareReferenceConfig.instance(getApplicationContext()).getAccessToken();
				GlobalVariable.LOGIN_TOKEN_TYPE = ShareReferenceConfig.instance(getApplicationContext()).getTokenType();
			}
			if(CommonHelper.checkValidString(ShareReferenceConfig.instance(getApplicationContext()).getDeviceToken())){
				GlobalVariable.DEVICE_TOKEN = ShareReferenceConfig.instance(getApplicationContext()).getDeviceToken();
			}
			try{
				ASplash1.this.startActivity(new Intent(ASplash1.this, AMain.class));
			}catch (Exception e){
			}


			finish();
		}else{
			jump();
		}
	}
	public class GCSTaskTextAPI extends AsyncTask<String, Void, Void> {
		Bitmap bm;
		Img2TextObj img2TextObj;
		boolean isSuccess = false;
		String failedStr = "";
		//String xxx = "";

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		public Void doInBackground(String... params) {
			bm = CommonHelper.getBitmapFromAsset(getApplicationContext(),"text_img.png");
			bm = CommonHelper.scaleImgDown(bm,GlobalVariable.MAXIMAGESIZE,true);
			String base64 = CommonHelper.bitmapToBase64(bm);
			//xxx = base64;
			GetTextFromBitmapAPIResponse getTextFromBitmapAPIResponse = new ResultFromWebservice().getTextFromBitmapAPIResponse(getApplicationContext(),base64,"");
			img2TextObj = getTextFromBitmapAPIResponse.getImg2TextObj();
			isSuccess = getTextFromBitmapAPIResponse.isSuccess();
			failedStr = getTextFromBitmapAPIResponse.getReturnString();
			return null;
		}

		public void onPostExecute(Void result) {


		}
	}
}