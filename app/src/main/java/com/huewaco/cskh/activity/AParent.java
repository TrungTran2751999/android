package com.huewaco.cskh.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.customview.edittext.CustomEditText;
import com.customview.edittext.DrawableClickListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.helper.ShareReferenceConfig;
import com.huewaco.cskh.interfacex.ITFShowlocation;
import com.huewaco.cskh.objects.BaoCaoSuCo_Post;
import com.huewaco.cskh.objects.KhachHangObj;
import com.huewaco.cskh.webservice.objects.GetDeviceTokenResponse;
import com.huewaco.cskh.webservice.objects.GetUserInfoResponse;
import com.huewaco.cskh.webservice.processing.ResultFromWebservice;

import java.util.ArrayList;

import me.leolin.shortcutbadger.ShortcutBadger;

public abstract class AParent extends FragmentActivity implements OnClickListener {
	public ITFShowlocation itfShowlocation = null;
	public void showBadgeNumber(int badgeCount){
		if(badgeCount < 0 ){
			badgeCount = 0;
		}
		//int badgeCount = 0;
		/*
		try {
			badgeCount = Integer.parseInt("0");
		} catch (NumberFormatException e) {
			Toast.makeText(getApplicationContext(), "Error input", Toast.LENGTH_SHORT).show();
		}
		*/

		boolean success = ShortcutBadger.applyCount(this, badgeCount);

		//Toast.makeText(getApplicationContext(), "Set count=" + badgeCount + ", success=" + success, Toast.LENGTH_SHORT).show();
	}
	public void FCM(){
		FirebaseMessaging.getInstance().subscribeToTopic(GlobalVariable.PREFERENCES).addOnSuccessListener(new OnSuccessListener<Void>() {
			@Override
			public void onSuccess(Void aVoid) {

			}
		});
		FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
			@Override
			public void onSuccess(InstanceIdResult instanceIdResult) {
				String token = instanceIdResult.getToken();
				if(token != null){
					GlobalVariable.DEVICE_TOKEN = token;
				}
				if(CommonHelper.checkValidString(GlobalVariable.DEVICE_TOKEN)){
					GlobalVariable.KHACH_HANG.setDeviceToken(GlobalVariable.DEVICE_TOKEN);
					GlobalVariable.KHACH_HANG_CURRENT = GlobalVariable.KHACH_HANG;
					ArrayList<KhachHangObj> arr = new ArrayList<>();
					arr.add(GlobalVariable.KHACH_HANG);
					arr.addAll(GlobalVariable.mArrKHang);
					CommonHelper.writeKhachHangObjects(getApplicationContext(),arr,GlobalVariable.KHACH_HANG_FILE);

					ShareReferenceConfig.instance(getApplicationContext()).setDeviceToken(GlobalVariable.DEVICE_TOKEN);
				}
				if(CommonHelper.isNetworkAvailable(getApplicationContext())){
					new GetDeviceTokenTask().execute();
				}
				Log.d("Activity:Token------",token);
			}
		});
//		FirebaseMessaging.getInstance().subscribeToTopic(GlobalVariable.PREFERENCES);
//		String token= FirebaseInstanceId.getInstance().getToken();

	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}
	@Override
	public void onResume() {
		super.onResume();
		if((this instanceof AIntro2 )|| (this instanceof  ALogin3) || (this instanceof  ADangNhap4)){
			if(CommonHelper.checkValidString(GlobalVariable.LOGIN_ACCESS_TOKEN) && CommonHelper.checkValidString(GlobalVariable.LOGIN_TOKEN_TYPE)){
				finish();
			}
		}

		if(itfShowlocation != null){
			itfShowlocation.showLocation();
		}
	}
	protected ViewGroup id_ly_topbar,id_ly_left,id_ly_center,id_ly_right;
	protected Button id_btn_left,id_btn_left2,id_btn_left3,id_btn_left4,id_btn_right,id_btn_right2,id_btn_right3,id_btn_right4;
	protected TextView id_tv_title,id_tv_title_sub;
	protected CustomEditText id_edt_search_main;
	protected ProgressBar id_prgbar_loading;
	protected void showView(View v){
		v.setVisibility(View.VISIBLE);
	}
	protected void hideView(View v){
		v.setVisibility(View.GONE);
	}
	protected void initTopbarView(){
		id_edt_search_main = (CustomEditText)findViewById(R.id.id_edt_search_main);
		id_prgbar_loading = (ProgressBar)findViewById(R.id.id_prgbar_loading);
		id_prgbar_loading.setVisibility(View.GONE);
		id_ly_topbar = (ViewGroup)findViewById(R.id.id_ly_topbar);
		id_ly_left = (ViewGroup)findViewById(R.id.id_ly_left);
		id_ly_center = (ViewGroup)findViewById(R.id.id_ly_center);
		id_ly_right = (ViewGroup)findViewById(R.id.id_ly_right);
		id_btn_right2 = (Button)findViewById(R.id.id_btn_right2);
		id_btn_right3 = (Button)findViewById(R.id.id_btn_right3);
		id_btn_right4 = (Button)findViewById(R.id.id_btn_right4);
		
		id_btn_left = (Button)findViewById(R.id.id_btn_left);
		id_btn_left2 = (Button)findViewById(R.id.id_btn_left2);
		id_btn_left3 = (Button)findViewById(R.id.id_btn_left3);
		id_btn_left4 = (Button)findViewById(R.id.id_btn_left4);
		id_btn_right = (Button)findViewById(R.id.id_btn_right);
		
		id_tv_title=(TextView)findViewById(R.id.id_tv_title);
		id_tv_title_sub=(TextView)findViewById(R.id.id_tv_title_sub);
		
		id_btn_left.setOnClickListener(this);
		id_btn_left2.setOnClickListener(this);
		id_btn_right.setOnClickListener(this);
		id_btn_right2.setOnClickListener(this);
		id_btn_right3.setOnClickListener(this);
		id_btn_left3.setOnClickListener(this);

		id_btn_left.setBackgroundResource(R.drawable.btn_back);

		hideView(id_edt_search_main);
		hideView(id_tv_title_sub);
		hideView(id_btn_left2);
		hideView(id_btn_left3);
		hideView(id_btn_left4);
		hideView(id_btn_right);
		hideView(id_btn_right2);
		hideView(id_btn_right3);
		hideView(id_btn_right4);
		id_btn_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	protected void initCommonView(AParent classX){

		if(classX instanceof AMain){

		}else if(classX instanceof ABaoCaoSuCo){
			showView(id_btn_right);
			id_btn_right.setBackgroundResource(android.R.drawable.ic_menu_send);
			id_tv_title.setText(getResources().getString(R.string.tab_dichvu_bcaosuco));
		}else if(classX instanceof  ABaoCaoSuCo_Recycler){
			showView(id_btn_right);
			id_btn_right.setBackgroundResource(android.R.drawable.ic_menu_search);

		}else if(classX instanceof  ABaoCaoSuCo_RecyclerSearch){
			showView(id_edt_search_main);
			hideView(id_tv_title);

		} else if(classX instanceof  AGhiChiSoPrepare){
			showView(id_btn_right);
			id_btn_right.setBackgroundResource(android.R.drawable.ic_menu_send);
		}
		
	}
	protected abstract void initComponent();

	protected abstract void addListener();
	

	private ProgressDialog dialogLoading;
	public void showLoading() {
		if (dialogLoading != null) {
			dialogLoading.dismiss();
		}
		if (dialogLoading == null) {
			dialogLoading = new ProgressDialog(this);
		}
		dialogLoading.setTitle(this.getResources().getString(R.string.app_name));
		dialogLoading.setMessage(getString(R.string.processing));
		dialogLoading.setCanceledOnTouchOutside(false);
		dialogLoading.setCancelable(false);
		dialogLoading.show();
	}

	public void disMissLoading() {

		if (dialogLoading != null) {
			dialogLoading.dismiss();
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == GlobalVariable.REQUEST_CALL_PHONE) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				//call
				CommonHelper.callPhone(this, getString(R.string.hotline));
			} else {
				CommonHelper.showWarning(this, getString(R.string.khong_cap_quyen_truy_cap));
			}
		}else if(requestCode == GlobalVariable.REQUEST_ID_ACCESS_COURSE_FINE_LOCATION){
			if(itfShowlocation != null) {
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					itfShowlocation.showLocation();
				}else{

//					CommonHelper.showToast(this, getString(R.string.khong_cap_quyen_truy_cap));
					DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							switch (which){
								case DialogInterface.BUTTON_POSITIVE:
									finish();
									break;

								case DialogInterface.BUTTON_NEGATIVE:
									finish();
									//No button clicked
									break;
							}
						}
					};

					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setMessage(this.getString(R.string.khong_cap_quyen_truy_cap)).setPositiveButton(this.getString(R.string.tab_caidat_dangxuat_ok), dialogClickListener).show();
				}

			}
		}

	}

	public class GetUserInfoTask extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		public Void doInBackground(String... params) {
			getUserInfor();

			//Log.d("abc", "" + getUserInfoResponse.getIdKh() + " 2: " + getUserInfoResponse.getCustomerName() + " 3: " + getUserInfoResponse.getPhonenumber());
			return null;
		}

		@Override
		public void onPostExecute(Void result) {
			//adapter.refresh(mArrKHang);
			//GlobalVariable.KHACH_HANG = mArrKHang.get(0);
		}
	}
	public void getUserInfor(){
		ResultFromWebservice rs = new ResultFromWebservice();
		GetUserInfoResponse getUserInfoResponse = rs.getUserInfo(this, GlobalVariable.LOGIN_TOKEN_TYPE, GlobalVariable.LOGIN_ACCESS_TOKEN,this);
		if (getUserInfoResponse != null && getUserInfoResponse.getMainCustomer() != null) {
			GlobalVariable.KHACH_HANG = getUserInfoResponse.getMainCustomer();
			GlobalVariable.KHACH_HANG.setDeviceType(GlobalVariable.DEVICE_TYPE);
			GlobalVariable.mArrKHang.clear();
			GlobalVariable.mArrKHang.addAll(getUserInfoResponse.getmArrCustomerGroup());
			ArrayList<KhachHangObj> arr = new ArrayList<>();
			arr.add(GlobalVariable.KHACH_HANG);
			arr.addAll(getUserInfoResponse.getmArrCustomerGroup());
			CommonHelper.writeKhachHangObjects(getApplicationContext(),arr,GlobalVariable.KHACH_HANG_FILE);

		}
	}
	public class GetDeviceTokenTask extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		public Void doInBackground(String... params) {
			ResultFromWebservice rs = new ResultFromWebservice();
			//GetLoginResponse getLogin = rs.getLogin("thilien", "000000");
			if(GlobalVariable.KHACH_HANG != null){
				GetDeviceTokenResponse getLogin = rs.getDeviceTokenResponse(AParent.this, GlobalVariable.KHACH_HANG.getMa_khang(), GlobalVariable.DEVICE_TOKEN, GlobalVariable.DEVICE_TYPE, GlobalVariable.LOGIN_TOKEN_TYPE + " " + GlobalVariable.LOGIN_ACCESS_TOKEN,AParent.this);
			}
			return null;
		}

		@Override
		public void onPostExecute(Void result) {
		}
	}

	protected void setEditTextListener(final CustomEditText edt, final Drawable left, final Drawable right) {

		if (edt.getText().toString().trim().length() > 0) {
			edt.setCompoundDrawables(left, null, right, null);
		}
		edt.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
				int length = edt.getText().toString().trim().length();
				if (length > 0) {
					edt.setCompoundDrawables(left, null, right, null);
				} else {
					edt.setCompoundDrawables(left, null, null, null);
				}
			}
		});
		edt.setDrawableClickListener(new DrawableClickListener() {

			public void onClick(DrawablePosition target) {
				switch (target) {
					case LEFT:
						break;
					case RIGHT:
						edt.setCompoundDrawables(left, null, null, null);
						edt.setText("");
						break;
					case TOP:
						break;
					case BOTTOM:
						break;
					default:
						break;
				}
			}

		});
	}


	protected void takePhoto(){};
	public void showMap(BaoCaoSuCo_Post post){}
	public void showPhanHoiKh(BaoCaoSuCo_Post post, int position){}
	public void showGalleryImgs(BaoCaoSuCo_Post post){}
	public void getErrorAndOut(){
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle(getString(R.string.app_name));
		alertDialog.setMessage(getString(R.string.time_out_session));
		alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						GlobalVariable.logOut(AParent.this);
						Intent i = new Intent(AParent.this, ADangNhap4.class);
						AParent.this.startActivity(i);
						finish();
					}
				});
		alertDialog.show();
	}
}
