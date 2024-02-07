package com.huewaco.cskh.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.adapter.ListViewCustomerGroupListItemAdapter;
import com.huewaco.cskh.adapter.ListViewThongBaoListItemAdapter;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.interfacex.ITFReadDeleteThongBao;
import com.huewaco.cskh.objects.KhachHangObj;
import com.huewaco.cskh.objects.ThongBaoListItemObj;
import com.huewaco.cskh.webservice.objects.GetThongBaoAllTheoLoaiResponse;
import com.huewaco.cskh.webservice.objects.GetThongBaoTheoLoaiResponse;
import com.huewaco.cskh.webservice.processing.ResultFromWebservice;

import java.util.ArrayList;


public class FTabThongBaoList extends FParent implements AdapterView.OnItemClickListener,ITFReadDeleteThongBao {
    private ListView id_lv_list;
    protected ArrayList<ThongBaoListItemObj> mArrThongBaoList = new ArrayList<ThongBaoListItemObj>();
    private ListViewThongBaoListItemAdapter adapter;
    protected String title = "";
    protected int position;
    Spinner id_spn_customergroup;
    LinearLayout id_layoutcmb;
    ListViewCustomerGroupListItemAdapter adapterCustomerGroup;
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
        View v = inflater.inflate(R.layout.frag_tab_thongbao_list, container, false);
        initCommonView(v, this);
        initComponent(v);
        addListener();
        setText();
        id_layoutcmb = (LinearLayout)  v.findViewById(R.id.id_layoutcmb);
        id_spn_customergroup = (Spinner) v.findViewById(R.id.id_spn_customergroup);
        adapterCustomerGroup = new ListViewCustomerGroupListItemAdapter(fpActivity,true);
        id_spn_customergroup.setAdapter(adapterCustomerGroup);
        id_spn_customergroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the value selected by the user
                // e.g. to store it as a field or immediately call a method
                KhachHangObj user = (KhachHangObj) parent.getSelectedItem();
                GlobalVariable.KHACH_HANG_CURRENT = user;
                if(GlobalVariable.KHACH_HANG_CURRENT.getIdKh().isEmpty()){
                    new GetThongBaoAllTask().execute();
                }else {
                    new GetThongBaoTask().execute();
                }


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

        if(CommonHelper.isNetworkAvailable(fpActivity)){
            if(GlobalVariable.KHACH_HANG_CURRENT.getIdKh().isEmpty()){
                new GetThongBaoAllTask().execute();
            }else {
                new GetThongBaoTask().execute();
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
        id_tv_title.setText(title);
        id_lv_list = (ListView) v.findViewById(R.id.id_lv_list);
        if (mArrThongBaoList != null) {
            adapter = new ListViewThongBaoListItemAdapter(fpActivity, mArrThongBaoList);
            id_lv_list.setAdapter(adapter);
        }
    }

    @Override
    protected void addListener() {
        id_lv_list.setOnItemClickListener(this);
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        ThongBaoListItemObj obj = mArrThongBaoList.get(position);
        if(obj.getId() != null){
            startNewFrag(mArrThongBaoList.get(position));
        }


    }

    public void startNewFrag(ThongBaoListItemObj obj) {
        FTabThongBaoListDetail frg2 = new FTabThongBaoListDetail();
        frg2.obj = obj;
        if(obj.isDaDoc()){
            frg2.POSITION = -1;
        }else{
            frg2.POSITION = position;
        }

        frg2.iTFReadDeleteThongBao = this;

        FragmentTransaction transaction2 = getChildFragmentManager().beginTransaction();
        if (GlobalVariable.IS_ANIMATION) {
            transaction2.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        transaction2.addToBackStack(null);
        transaction2.replace(R.id.id_fr_tab_thongbao_list, frg2).commit();
    }

    @Override
    public void refreshRead(ThongBaoListItemObj obj) {
        if(!obj.isDaDoc()){
            obj.setDaDoc(true);
            adapter.refresh(mArrThongBaoList);
        }

    }

    @Override
    public void refreshDelete(ThongBaoListItemObj obj) {
        mArrThongBaoList.remove(obj);
        adapter.refresh(mArrThongBaoList);
    }

    //
    public class GetThongBaoTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        @Override
        public Void doInBackground(String... params) {

            ResultFromWebservice rs = new ResultFromWebservice();
            //
            GetThongBaoTheoLoaiResponse getThongBaoResponse = rs.getThongBaoTheoLoaiResponse(fpActivity, GlobalVariable.LOGIN_TOKEN_TYPE, GlobalVariable.LOGIN_ACCESS_TOKEN, position, GlobalVariable.KHACH_HANG_CURRENT.getIdKh(),fpActivity);
            if (getThongBaoResponse != null && getThongBaoResponse.getmArrItem() != null) {
                mArrThongBaoList.clear();
                mArrThongBaoList.addAll(getThongBaoResponse.getmArrItem());
            }
            //Log.d("abc", "" + getThongBaoResponse.getIdkh());
            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            disMissLoading();
            adapter.refresh(mArrThongBaoList);
        }
    }
    public class GetThongBaoAllTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        @Override
        public Void doInBackground(String... params) {

            ResultFromWebservice rs = new ResultFromWebservice();
            //
            GetThongBaoAllTheoLoaiResponse getThongBaoResponse = rs.getThongBaoAllTheoLoaiResponse(fpActivity, GlobalVariable.LOGIN_TOKEN_TYPE, GlobalVariable.LOGIN_ACCESS_TOKEN, position, adapterCustomerGroup.getIdkhAll(),fpActivity);
            if (getThongBaoResponse != null && getThongBaoResponse.getmArrItem() != null) {
                mArrThongBaoList.clear();
                mArrThongBaoList.addAll(getThongBaoResponse.getmArrItem());
            }
            //Log.d("abc", "" + getThongBaoResponse.getIdkh());
            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            disMissLoading();
            adapter.refresh(mArrThongBaoList);
        }
    }
}
