package com.huewaco.cskh.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.adapter.ListViewCustomerGroupListItemAdapter;
import com.huewaco.cskh.adapter.ListViewLichCatNuocAdapter;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.objects.KhachHangObj;
import com.huewaco.cskh.objects.LichCatNuocListItemObj;
import com.huewaco.cskh.webservice.objects.GetTraCuu2LichCatNuocResponse;
import com.huewaco.cskh.webservice.processing.ResultFromWebservice;

import java.util.ArrayList;


public class FTabTraCuu2LichCatNuoc extends FParent {
    private ListView id_lv_list_lich_cat_nuoc;
    private ListViewLichCatNuocAdapter adapter;
    private ArrayList<LichCatNuocListItemObj> mArrLichCatNuocList = new ArrayList<LichCatNuocListItemObj>();
    protected String title;
    private GetLichCatNuocTask getLichCatNuocTask;
    Spinner id_spn_customergroup;
    LinearLayout id_layoutcmb;
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
        View v = inflater.inflate(R.layout.frag_tab_tracuu2_lich_cat_nuoc, container, false);
        initCommonView(v, this);
        initData();
        initComponent(v);
        addListener();
        setText();
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
                new GetLichCatNuocTask().execute();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if(GlobalVariable.mArrKHang.size() >0){
            id_spn_customergroup.setSelection(adapterCustomerGroup.getIndexByIdkh(GlobalVariable.KHACH_HANG_CURRENT.getIdKh()),false );
            adapterCustomerGroup.refresh();
        }else{
            id_layoutcmb.setVisibility(View.GONE);
        }
        if (CommonHelper.isNetworkAvailable(fpActivity)) {
            new GetLichCatNuocTask().execute();
        } else {
            CommonHelper.showWarning(fpActivity, getString(R.string.nointernet));
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
        id_tv_title.setText(title);
        id_lv_list_lich_cat_nuoc = (ListView) v.findViewById(R.id.id_lv_list_lich_cat_nuoc);
        if (mArrLichCatNuocList != null) {
            adapter = new ListViewLichCatNuocAdapter(fpActivity, mArrLichCatNuocList);
            id_lv_list_lich_cat_nuoc.setAdapter(adapter);
        }
    }

    public class GetLichCatNuocTask extends AsyncTask<String, Void, Void> {
        boolean isError = false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        @Override
        public Void doInBackground(String... params) {

            ResultFromWebservice rs = new ResultFromWebservice();
            GetTraCuu2LichCatNuocResponse getLichCatNuocResponse = rs.getLichCatNuoc(fpActivity,GlobalVariable.LOGIN_TOKEN_TYPE, GlobalVariable.LOGIN_ACCESS_TOKEN,fpActivity);
            mArrLichCatNuocList.clear();
            if (getLichCatNuocResponse != null && getLichCatNuocResponse.getmArrItem() != null) {

                mArrLichCatNuocList.addAll(getLichCatNuocResponse.getmArrItem());
            }
            isError = getLichCatNuocResponse.hasError();
            //Log.d("abc", "" + getLichCatNuocResponse.getID() + " 2: " + getLichCatNuocResponse.getMaLoTrinh() + " 3: " + getLichCatNuocResponse.getMaKV());
            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            adapter.refresh(mArrLichCatNuocList);
            disMissLoading();
            if (isError) {
                Log.d("abc", "" + "---------------: " + isError);
                fpActivity.startDangNhap4();
            }
        }
    }

    private void initData() {
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

    }
}
