/**
 * Created by: Kiet.Duong
 * Dec-29-2014
 **/
package com.huewaco.cskh.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View.OnClickListener;
import android.webkit.URLUtil;
import android.widget.TabHost;
import android.widget.TabWidget;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.huewaco.cskh.activity.ADangNhap4;
import com.huewaco.cskh.activity.AMain;
import com.huewaco.cskh.activity.AParent;
import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.interfacex.ITFRefreshThongBaoDaDoc;

import java.io.File;


public abstract class FParentActivity extends AParent implements OnClickListener {
	protected FParentActivity fpActivity;
	protected TabHost mTabHost;// dependent in tabManager,contain all tabSpec,
								// to create
	// tabSpec (each tab when you click)
	protected TabWidget mTabWidget;// to custom UI when needed
	protected ViewPager mViewPager;
	protected AMain.TabsAdapter mTabsAdapter;
	public ITFRefreshThongBaoDaDoc itfRefreshThongBaoDaDoc;
	protected abstract void initComponent();

	protected abstract void addListener();

	//public ArrayList<TabObj> mArrTabs = new ArrayList<TabObj>();
	public SparseArray<FParent> registeredFragments = new SparseArray<>();
	//public Bundle data = null;// new Bundle();
	public int notificationId = -1;

	
	private ProgressDialog dialogLoading;

	public void gotoTabPosition(int p){
		mTabHost.setCurrentTab(p);
	}
	public void logOut() {
		startActivity(new Intent(this, ADangNhap4.class));
		finish();
	}
	public void startDangNhap4() {
		/*startActivity(new Intent(this, ADangNhap4.class));
		finish();*/
	}

	public void showLoading() {
		if (dialogLoading != null) {
			dialogLoading.dismiss();
		}
		if (dialogLoading == null) {
			dialogLoading = new ProgressDialog(fpActivity);
		}
		dialogLoading.setTitle(fpActivity.getResources().getString(R.string.app_name));
		dialogLoading.setMessage(getString(R.string.waiting));
		dialogLoading.setCanceledOnTouchOutside(false);
		dialogLoading.setCancelable(false);
		dialogLoading.show();
	}

	public void disMissLoading() {

		if (dialogLoading != null) {
			dialogLoading.dismiss();
		}
	}

	int countChildFragM = 0;

	private int reCursive(FParent fp) {
		int countChildFrag = fp.getChildFragmentManager().getBackStackEntryCount();
		if (countChildFrag > 0) {
			countChildFragM++;
			FragmentManager childFragmentManager = fp.getChildFragmentManager();
			FParent childFragment = (FParent) childFragmentManager.getFragments().get(0);
			reCursive(childFragment);
		}
		return countChildFragM;
	}

	public void popAllStacks(FParent fp) {
		reCursive(fp);
		for (int i = 0; i < countChildFragM; i++) {
			fpActivity.onBackPressed();
		}
		countChildFragM = 0;
	}
	public String getFileName(String url) {
		return URLUtil.guessFileName(url, null, null);
	}
	public void forwardDocument(File file) {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
		i.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_file_mtb));
		i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		i.setType("text/plain");
		startActivity(Intent.createChooser(i, getResources().getString(R.string.share_doc)));
	}



}
