package com.huewaco.cskh.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.objects.ThongBaoListItemObj;
import com.huewaco.cskh.webservice.objects.GetThongBaoDeleteResponse;
import com.huewaco.cskh.webservice.objects.GetThongBaoReadResponse;
import com.huewaco.cskh.webservice.processing.ResultFromWebservice;


public class FTabThongBaoListDetail extends FParent {
	protected com.huewaco.cskh.interfacex.ITFReadDeleteThongBao iTFReadDeleteThongBao;
	protected ThongBaoListItemObj obj;
	public int POSITION = -1;

	//protected String date;
	private TextView id_tv_content_message,id_tv_date;
	private Button id_btn_camera,id_btn_send;
	private EditText id_edt_content;
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
		View v = inflater.inflate(R.layout.frag_tab_thongbao_list_detail, container, false);
		initCommonView(v, this);
		initComponent(v);
		addListener();
		setText();
		if(CommonHelper.isNetworkAvailable(fpActivity)){
			if(!obj.isDaDoc()){
				new GetReadThongBaoMoiTask().execute();
			}

		}else{
			CommonHelper.showWarning(fpActivity,getString(R.string.nointernet));
		}
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
		id_tv_content_message = (TextView)v.findViewById(R.id.id_tv_content_message);
		id_tv_date = (TextView)v.findViewById(R.id.id_tv_date);

		id_btn_camera = (Button)v.findViewById(R.id.id_btn_camera);
		id_btn_send = (Button)v.findViewById(R.id.id_btn_send);
		id_edt_content=(EditText)v.findViewById(R.id.id_edt_content);

		//id_tv_content_message.setText(Html.fromHtml(obj.getNoiDung()));
		CommonHelper.setTextHtml(id_tv_content_message,obj.getNoiDung());
//		id_tv_date.setText(date);
//		id_tv_title.setText(title);

	}

	@Override
	protected void addListener() {
		id_btn_camera.setOnClickListener(this);
		id_btn_send.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.id_btn_camera:
				break;
			case R.id.id_btn_send:
				break;
			case R.id.id_btn_right:
				if(CommonHelper.isNetworkAvailable(fpActivity)){
					new GetDeleteThongBaoMoiTask().execute();
				}else{
					CommonHelper.showWarning(fpActivity,getString(R.string.nointernet));
				}
				break;
		default:
			break;
		}

	}

	public void setText() {

	}
	public class GetReadThongBaoMoiTask extends AsyncTask<String, Void, Void> {
		boolean isSuccess = false;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		public Void doInBackground(String... params) {

			ResultFromWebservice rs = new ResultFromWebservice();
			//
			GetThongBaoReadResponse getThongBaoReadResponse = rs.getThongBaoReadResponse(fpActivity,obj.getId(), GlobalVariable.LOGIN_TOKEN_TYPE, GlobalVariable.LOGIN_ACCESS_TOKEN,fpActivity);
			if(getThongBaoReadResponse.getReturnString().equalsIgnoreCase("true")){
				isSuccess = true;
			}else{
				isSuccess = false;
			}
			return null;
		}

		@Override
		public void onPostExecute(Void result) {
			if(isSuccess){
				iTFReadDeleteThongBao.refreshRead(obj);
				if(fpActivity.itfRefreshThongBaoDaDoc != null){
					if(POSITION != -1){

						fpActivity.itfRefreshThongBaoDaDoc.reload(POSITION);
					}
				}
				if(POSITION != -1) {
					if (GlobalVariable.TONG_TIN_CHUA_DOC > 0) {
						GlobalVariable.TONG_TIN_CHUA_DOC--;
						fpActivity.showBadgeNumber(GlobalVariable.TONG_TIN_CHUA_DOC);
					}
				}
			}else{
				CommonHelper.showWarning(fpActivity,getString(R.string.loi_xay_ra_thu_luc_khac));
			}

		}
	}
	public class GetDeleteThongBaoMoiTask extends AsyncTask<String, Void, Void> {
		boolean isSuccess = false;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		public Void doInBackground(String... params) {

			ResultFromWebservice rs = new ResultFromWebservice();
			//
			GetThongBaoDeleteResponse getThongBaoDeleteResponse = rs.getThongBaoDeleteResponse(fpActivity,obj.getId(), GlobalVariable.LOGIN_TOKEN_TYPE, GlobalVariable.LOGIN_ACCESS_TOKEN,fpActivity);
			if(getThongBaoDeleteResponse.getReturnString().equalsIgnoreCase("true")){
				isSuccess = true;
			}else{
				isSuccess = false;
			}
			return null;
		}

		@Override
		public void onPostExecute(Void result) {
			if(isSuccess){
				iTFReadDeleteThongBao.refreshDelete(obj);
				fpActivity.onBackPressed();
				if(GlobalVariable.TONG_TIN_CHUA_DOC > 0){
					GlobalVariable.TONG_TIN_CHUA_DOC--;
					if(GlobalVariable.TONG_TIN_CHUA_DOC>0){
						fpActivity.showBadgeNumber(GlobalVariable.TONG_TIN_CHUA_DOC);
					}else{
						fpActivity.showBadgeNumber(0);
					}
				}
			}else{
				CommonHelper.showWarning(fpActivity,getString(R.string.loi_xay_ra_thu_luc_khac));
			}
		}
	}
}
