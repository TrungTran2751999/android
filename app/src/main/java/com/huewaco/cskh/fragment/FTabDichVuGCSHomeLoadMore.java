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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.huewaco.cskh.activity.ABaoCaoSuCo_Recycler;
import com.huewaco.cskh.activity.AGhiChiSoPrepare;
import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.adapter.ListViewBaoCaoSuCoRecyclerAdapter;
import com.huewaco.cskh.adapter.ListViewCustomerGroupListItemAdapter;
import com.huewaco.cskh.adapter.ListViewGCS4KyAdapter;
import com.huewaco.cskh.adapter.ListViewGCSRecyclerAdapter;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.objects.BaoCaoSuCo_Post;
import com.huewaco.cskh.objects.GhiChiSo4KiCuoi;
import com.huewaco.cskh.objects.KhachHangObj;
import com.huewaco.cskh.webservice.objects.GetDichVuGCSResponse;
import com.huewaco.cskh.webservice.processing.ResultFromWebservice;

import java.util.ArrayList;
import java.util.Collections;


public class FTabDichVuGCSHomeLoadMore extends FParent {
	private Button id_btn_gcs;
	private ArrayList<GhiChiSo4KiCuoi> mArrGcss = new ArrayList<>();

	private ListViewGCSRecyclerAdapter adapterLoadMore;

	private RecyclerView id_rcl_list4;
	private LinearLayoutManager linearLayoutManager;
	private SwipeRefreshLayout id_swp_list4;
	private Spinner id_spn_customergroup;
	LinearLayout id_layoutcmb;

	String ids = "0";
	String records = "";
	String isolders = "";
	int WHAT_TYPE = 1;//1: load first, 2: load new, 3: load old

	private int totalItemCount,lastVisibleItem,visibleThreshold=5;
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
		View v = inflater.inflate(R.layout.frag_tab_dichvu_gcs_home_loadmore, container, false);
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
		id_btn_gcs = (Button)v.findViewById(R.id.id_btn_gcs);
		adapterLoadMore = new ListViewGCSRecyclerAdapter(fpActivity, mArrGcss, fpActivity);
		id_rcl_list4 = (RecyclerView) v.findViewById(R.id.id_rcl_list4);
		linearLayoutManager = new LinearLayoutManager(fpActivity);
		id_rcl_list4.setLayoutManager(linearLayoutManager);
		id_rcl_list4.setAdapter(adapterLoadMore);
		id_rcl_list4.setHasFixedSize(true);
		id_rcl_list4.setItemViewCacheSize(20);
		id_rcl_list4.setDrawingCacheEnabled(true);
		id_rcl_list4.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

		id_swp_list4 = (SwipeRefreshLayout)v.findViewById(R.id.id_swp_list4);

		id_layoutcmb = (LinearLayout)  v.findViewById(R.id.id_layoutcmb);
		id_spn_customergroup = (Spinner) v.findViewById(R.id.id_spn_customergroup);
		ListViewCustomerGroupListItemAdapter adapterCustomerGroup = new ListViewCustomerGroupListItemAdapter(fpActivity,false);
		id_spn_customergroup.setAdapter(adapterCustomerGroup);
		id_spn_customergroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// Get the value selected by the user
				// e.g. to store it as a field or immediately call a method
				KhachHangObj user = (KhachHangObj) parent.getSelectedItem();
				GlobalVariable.KHACH_HANG_CURRENT = user;
				ids = "0";
				records = "";
				isolders = "";
				isLoadFirstCompleted = false;
				WHAT_TYPE = 1;
				new GetGCS4KyCuoiTask().execute();
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		if(GlobalVariable.mArrKHang.size() >0){
			id_spn_customergroup.setSelection(adapterCustomerGroup.getIndexByIdkh(GlobalVariable.KHACH_HANG_CURRENT.getIdKh()),false );

		}else{
			id_layoutcmb.setVisibility(View.GONE);
			adapterCustomerGroup.refresh();
		}


	}
	private boolean isLoadingMoreNew = false;
	private boolean isLoadFirstCompleted = false;

	private boolean isLoadingMoreOld = false;

	@Override
	protected void addListener() {
		id_btn_gcs.setOnClickListener(this);
		id_swp_list4.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				if(!isLoadingMoreNew && isLoadFirstCompleted){
					WHAT_TYPE = 2;
					if(mArrGcss.size() >0){
						GhiChiSo4KiCuoi gcs = mArrGcss.get(0);
						ids = String.valueOf(gcs.getId());
						records = "";
						isolders = "false";
						new GetGCS4KyCuoiTask().execute();
					}else{
						ids = "0";
						records = "";
						isolders = "";
						new GetGCS4KyCuoiTask().execute();
					}
				}

			}
		});
		id_rcl_list4.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
			}
			@Override
			public void onScrolled(RecyclerView recyclerView,
								   int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);

				totalItemCount = linearLayoutManager.getItemCount();
				lastVisibleItem = linearLayoutManager
						.findLastVisibleItemPosition();

				if (isLoadFirstCompleted && (!isLoadingMoreOld) && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
					WHAT_TYPE = 3;
					if(mArrGcss.size() >4){
						int index = mArrGcss.size() - 1;
						GhiChiSo4KiCuoi gcs = mArrGcss.get(index);
						ids = String.valueOf(gcs.getId());
						records = "";
						isolders = "true";
						new GetGCS4KyCuoiTask().execute();
					}else{

					}
				}
				//end show/hide bottom bar
			}
		});
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
			if(WHAT_TYPE == 1){
				showLoading();
			}
			isLoadingMoreNew = true;
			isLoadingMoreOld = true;
		}

		@Override
		public ArrayList<GhiChiSo4KiCuoi> doInBackground(String... params) {
			ResultFromWebservice rs = new ResultFromWebservice();
			GetDichVuGCSResponse getDichVuGCSResponse = rs.getDichVuGCSResponse2(fpActivity, GlobalVariable.LOGIN_TOKEN_TYPE , GlobalVariable.LOGIN_ACCESS_TOKEN,fpActivity,ids,isolders,records,GlobalVariable.KHACH_HANG_CURRENT.getIdKh());
			return getDichVuGCSResponse.getmArrItem();
		}

		@Override
		public void onPostExecute(ArrayList<GhiChiSo4KiCuoi> result) {
			disMissLoading();
			isLoadingMoreNew = false;
			if(ids.equals("0")){
				isLoadFirstCompleted = true;
				mArrGcss.clear();
			}

			isLoadingMoreOld = false;

			if(result!=null &&result.size()>0 ){
				mArrGcss.addAll(result);
				Collections.sort(mArrGcss, GhiChiSo4KiCuoi.sort_GhiChiSo4KiCuoi_DESC);

			}
			adapterLoadMore.refresh(mArrGcss);
			if(WHAT_TYPE == 2){
				id_swp_list4.setRefreshing(false);
			}
			id_swp_list4.setRefreshing(false);
		}
	}
}
