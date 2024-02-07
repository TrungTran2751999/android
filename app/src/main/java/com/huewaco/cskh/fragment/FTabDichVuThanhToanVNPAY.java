package com.huewaco.cskh.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;

//import android.support.annotation.Nullable;


public class FTabDichVuThanhToanVNPAY extends FParent {
	private WebView id_wv_vnpay;
	String urlStr = "";
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frag_tab_dichvu_thanhtoan_vnpay, container, false);
		initCommonView(v, this);
		initComponent(v);
		addListener();
		setText();
		return v;
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	protected void initComponent(View v) {
		id_wv_vnpay = (WebView)v.findViewById(R.id.id_wv_vnpay);

		id_wv_vnpay.getSettings().setJavaScriptEnabled(true);
		id_wv_vnpay.getSettings().setLoadWithOverviewMode(true);
		id_wv_vnpay.getSettings().setUseWideViewPort(true);
		id_wv_vnpay.getSettings().setDomStorageEnabled(true);
		id_wv_vnpay.getSettings().setAllowFileAccess( true );


//		id_wv_vnpay.getSettings().setAppCachePath( fpActivity.getCacheDir().getAbsolutePath() );
//		id_wv_vnpay.getSettings().setAllowFileAccess( true );
//		id_wv_vnpay.getSettings().setAppCacheEnabled( true );
//		id_wv_vnpay.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
//		if(!CommonHelper.isNetworkAvailable(fpActivity)){
//			id_wv_vnpay.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK );
//		}
//		id_wv_vnpay.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
//		id_wv_vnpay.getSettings().setLoadWithOverviewMode(true);
//		id_wv_vnpay.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

		id_wv_vnpay.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {

				view.loadUrl(url);

				return true;
			}
			@Override
			public void onPageStarted(
					WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				fpActivity.showLoading();
			}
			@Override
			public void onPageFinished(WebView view, final String url) {
				fpActivity.disMissLoading();
			}
		});

		process();

	}
	private void process(){
		boolean chk = true;
		if(CommonHelper.isNetworkAvailable(fpActivity)){
			if(GlobalVariable.KHACH_HANG != null){
				if(CommonHelper.checkValidString(GlobalVariable.KHACH_HANG.getMa_khang())){
					urlStr = "https://merchant.vban.vn/iframe/hoa-don-nuoc.html?tmcode=31004101&svcode=BILLWATER-001&provider=NUOC_HUE&billcode="+GlobalVariable.KHACH_HANG.getMa_khang()+"&mobile=";
				}else{
					chk = false;
				}
				if(CommonHelper.checkValidString(GlobalVariable.KHACH_HANG.getDiDong())){
					urlStr = "https://merchant.vban.vn/iframe/hoa-don-nuoc.html?tmcode=31004101&svcode=BILLWATER-001&provider=NUOC_HUE&billcode="+GlobalVariable.KHACH_HANG.getMa_khang()+"&mobile="+GlobalVariable.KHACH_HANG.getDiDong();
				}
				if(chk){
					id_wv_vnpay.loadUrl(urlStr);
				}else{
					CommonHelper.showWarning(fpActivity,getString(R.string.dichvu_pay_via_vnpay_invalid));
				}
			}
		}else{
			CommonHelper.showWarning(fpActivity,getString(R.string.nointernet));
		}
	}
	@Override
	protected void addListener() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		default:
			break;
		}

	}

	public void setText() {
		id_tv_title.setText(title);
	}
}
