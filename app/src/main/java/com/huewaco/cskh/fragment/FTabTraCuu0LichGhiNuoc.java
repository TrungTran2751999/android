package com.huewaco.cskh.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import com.customview.edittext.CustomEditText;
import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.adapter.ListViewCustomerGroupListItemAdapter;
import com.huewaco.cskh.adapter.ListViewLichGhiNuocAdapter;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.objects.KhachHangObj;
import com.huewaco.cskh.objects.LichGhiNuocListItemObj;
import com.huewaco.cskh.webservice.objects.GetTraCuu0LichGhiNuocResponse;
import com.huewaco.cskh.webservice.processing.ResultFromWebservice;

import java.util.ArrayList;


public class FTabTraCuu0LichGhiNuoc extends FParent {
    private ListView id_lv_list_ghi_nuoc;
    private ListViewLichGhiNuocAdapter adapter;
    private ArrayList<LichGhiNuocListItemObj> mArrLichGhiList = new ArrayList<>();
    protected String title;
    private CustomEditText id_edt_date_from, id_edt_date_to;
    private Button id_btn_timkiem;
    private Spinner id_spn_customergroup;
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
        View v = inflater.inflate(R.layout.frag_tab_tracuu0_lich_ghi_nuoc, container, false);
        initCommonView(v, this);
        initData();
        initComponent(v);
        addListener();
        setText();
        if(CommonHelper.isNetworkAvailable(fpActivity)){
            new GetTraCuu0LichGhiNuocTask().execute();
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
        id_lv_list_ghi_nuoc = (ListView) v.findViewById(R.id.id_lv_list_ghi_nuoc);
        //
        adapter = new ListViewLichGhiNuocAdapter(fpActivity, mArrLichGhiList);
        id_lv_list_ghi_nuoc.setAdapter(adapter);
        //
        id_edt_date_from = (CustomEditText) v.findViewById(R.id.id_edt_date_from);
        id_edt_date_to = (CustomEditText) v.findViewById(R.id.id_edt_date_to);
        //
        id_btn_timkiem = (Button) v.findViewById(R.id.id_btn_timkiem);

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
    }

    private void initData() {

    }

    @Override
    protected void addListener() {
        id_edt_date_from.setOnClickListener(this);
        id_edt_date_to.setOnClickListener(this);
        id_btn_timkiem.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_edt_date_from:
                CommonHelper.datePicker(fpActivity, id_edt_date_from, null, this);
                break;
            case R.id.id_edt_date_to:
                CommonHelper.datePicker(fpActivity, id_edt_date_to, null, this);
                break;
            case R.id.id_btn_timkiem:
                if(CommonHelper.isNetworkAvailable(fpActivity)){
                    if(CommonHelper.getNamThangNgay(id_edt_date_from.getText().toString()).equalsIgnoreCase("false")){
                        CommonHelper.showWarning(fpActivity, getString(R.string.tab_tracuu_khongdungdinhdang));
                    } else {
                        GlobalVariable.URL_TC0_LICH_GHI_NUOC = GlobalVariable.URL_TC0_LICH_GHI_NUOC_TUNGAY_DENNGAY
                                + "/"+ CommonHelper.getNamThangNgay(id_edt_date_from.getText().toString())
                                + "/"+CommonHelper.getNamThangNgay(id_edt_date_to.getText().toString())
                                + "?idkh="+GlobalVariable.KHACH_HANG_CURRENT.getIdKh();
                        new GetTraCuu0LichGhiNuocTask().execute();
                    }
                }else{
                    CommonHelper.showWarning(fpActivity,getString(R.string.nointernet));
                }
                break;
            default:
                break;
        }
    }



    public void setText() {
        GlobalVariable.URL_TC0_LICH_GHI_NUOC = GlobalVariable.URL_TC0_LICH_GHI_NUOC_4KY_GANNHAT+ "?idkh=" + GlobalVariable.KHACH_HANG_CURRENT.getIdKh();
    }

    public class GetTraCuu0LichGhiNuocTask extends AsyncTask<String, Void, Void> {
        boolean isError = false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        @Override
        public Void doInBackground(String... params) {

            ResultFromWebservice rs = new ResultFromWebservice();
            GetTraCuu0LichGhiNuocResponse getTCLichGhiNuocResponse = rs.getTCLichGhiNuocResponse(fpActivity, GlobalVariable.LOGIN_TOKEN_TYPE, GlobalVariable.LOGIN_ACCESS_TOKEN,fpActivity);
            if (getTCLichGhiNuocResponse != null && getTCLichGhiNuocResponse.getmArrItem() != null) {

                mArrLichGhiList.clear();
                mArrLichGhiList.addAll(getTCLichGhiNuocResponse.getmArrItem());
            }
            isError = getTCLichGhiNuocResponse.hasError();
            Log.d("abc", "" + getTCLichGhiNuocResponse.getNam());
            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            disMissLoading();
            adapter.refresh(mArrLichGhiList);
            if (isError) {
                Log.d("abc", "" + "---------------: " + isError);
                fpActivity.startDangNhap4();
            }
            int size = mArrLichGhiList.size();
            if(mArrLichGhiList != null && size>0){
                LichGhiNuocListItemObj to  = mArrLichGhiList.get(0);
                LichGhiNuocListItemObj from  = mArrLichGhiList.get(size-1);
                id_edt_date_from.setText(from.getNgayghinuoc());
                id_edt_date_to.setText(to.getNgayghinuoc());
            }
        }
    }

    // Chọn ngày tháng năm xong nó nhảy vô đây
    public void onFinisedGetDatePicker(String date) {
        int a = 1;
    }
}
