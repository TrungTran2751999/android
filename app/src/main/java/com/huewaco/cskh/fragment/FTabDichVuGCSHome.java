package com.huewaco.cskh.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.huewaco.cskh.activity.ABaoCaoSuCo_Recycler;
import com.huewaco.cskh.activity.AGhiChiSoPrepare;
import com.huewaco.cskh.activity.AShowGalleryImages_BaoCaoSuCo;
import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.adapter.ListViewGCS4KyAdapter;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.objects.GhiChiSo4KiCuoi;
import com.huewaco.cskh.objects.ImgInPostObj;
import com.huewaco.cskh.webservice.objects.GetDichVu0DvYeuCausResponse;
import com.huewaco.cskh.webservice.objects.GetDichVuGCSResponse;
import com.huewaco.cskh.webservice.processing.ResultFromWebservice;

import java.util.ArrayList;


public class FTabDichVuGCSHome extends FParent {
	private ListView id_lv_list_cs4ky;
	private Button id_btn_gcs;
	private ArrayList<GhiChiSo4KiCuoi> mArrGcss = new ArrayList<>();
	private ListViewGCS4KyAdapter adapter;

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
		View v = inflater.inflate(R.layout.frag_tab_dichvu_gcs_home, container, false);
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
		id_tv_title_sub.setText(getString(R.string.dichvu_lichsu_dungnuoc));
		id_lv_list_cs4ky = (ListView)v.findViewById(R.id.id_lv_list_cs4ky);
		id_btn_gcs = (Button)v.findViewById(R.id.id_btn_gcs);
		adapter = new ListViewGCS4KyAdapter(fpActivity,mArrGcss);
		id_lv_list_cs4ky.setAdapter(adapter);

		Log.d("XXXXX",""+CommonHelper.getStringWithSeparatorThousand(12345));
		new GetGCS4KyCuoiTask().execute();
	}

	@Override
	protected void addListener() {
		id_btn_gcs.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.id_btn_gcs:
				fpActivity.startActivity(new Intent(fpActivity, AGhiChiSoPrepare.class));
				break;
			case R.id.id_btn_right:
				id_btn_gcs.performClick();
				break;
		default:
			break;
		}

	}


	public void setText() {
		id_tv_title.setText(title);
	}


	public class GetGCS4KyCuoiTask extends AsyncTask<String, Void, ArrayList<GhiChiSo4KiCuoi>> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showLoading();
		}

		@Override
		public ArrayList<GhiChiSo4KiCuoi> doInBackground(String... params) {
			ResultFromWebservice rs = new ResultFromWebservice();
			GetDichVuGCSResponse getDichVuGCSResponse = rs.getDichVuGCSResponse(fpActivity, GlobalVariable.LOGIN_TOKEN_TYPE , GlobalVariable.LOGIN_ACCESS_TOKEN,fpActivity);
			return getDichVuGCSResponse.getmArrItem();
		}

		@Override
		public void onPostExecute(ArrayList<GhiChiSo4KiCuoi> result) {
			disMissLoading();
			if(result!=null &&result.size()>0 ){
				mArrGcss.addAll(result);
				adapter.refresh(mArrGcss);
			} else {

			}
		}
	}
}
