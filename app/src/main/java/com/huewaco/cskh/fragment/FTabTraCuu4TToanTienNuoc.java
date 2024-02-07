package com.huewaco.cskh.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.adapter.ListViewCustomerGroupListItemAdapter;
import com.huewaco.cskh.adapter.ListViewTToanTNuocAdapter;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.objects.KhachHangObj;
import com.huewaco.cskh.objects.TToanTienNuocListItemObj;
import com.huewaco.cskh.webservice.objects.GetTraCuu4ThongTinThanhToanAllResponse;
import com.huewaco.cskh.webservice.objects.GetTraCuu4ThongTinThanhToanTienNuocResponse;
import com.huewaco.cskh.webservice.processing.ResultFromWebservice;

import java.util.ArrayList;
import java.util.Calendar;


public class FTabTraCuu4TToanTienNuoc extends FParent {
    private ListView id_lv_list_ttoan_tnuoc;
    private ListViewTToanTNuocAdapter adapter;
    private ArrayList<TToanTienNuocListItemObj> mArrTToanTienDienList = new ArrayList<>();
    protected String title;
    private Spinner id_spn_thang_den_thang, id_spn_nam_den_thang, id_spn_thang_tu_thang, id_spn_nam_tu_thang,id_spn_customergroup;
    private int thisYear, thisMonth;
    private ArrayList<String> mArrYears = new ArrayList<String>();
    private ArrayList<String> mArrMonth = new ArrayList<String>();
    private Button id_btn_timkiem;
    LinearLayout id_layoutcmb;
    LinearLayout id_ly_ttoan_title;
    ListViewCustomerGroupListItemAdapter adapterCustomerGroup;
    private TextView id_tv_tongtien;
    private String sumTotal;
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
        View v = inflater.inflate(R.layout.frag_tab_tracuu4_thtoan_tnuoc, container, false);
        initCommonView(v, this);
        initData();
        initComponent(v);
        addListener();
        setText();

        if(CommonHelper.isNetworkAvailable(fpActivity)){
            new GetTCThongTinThanhToanTienNuocTask().execute();
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
        id_spn_thang_den_thang = (Spinner) v.findViewById(R.id.id_spn_thang_den_thang);
        id_spn_nam_den_thang = (Spinner) v.findViewById(R.id.id_spn_nam_den_thang);
        id_spn_thang_tu_thang = (Spinner) v.findViewById(R.id.id_spn_thang_tu_thang);
        id_spn_nam_tu_thang = (Spinner) v.findViewById(R.id.id_spn_nam_tu_thang);

        setData_To_Spinner();
        ArrayAdapter<String> adapterThang = new ArrayAdapter<String>(fpActivity, android.R.layout.simple_spinner_item, mArrMonth);
        id_spn_thang_den_thang.setAdapter(adapterThang);
        id_spn_thang_tu_thang.setAdapter(adapterThang);
        ArrayAdapter<String> adapterNam = new ArrayAdapter<String>(fpActivity, android.R.layout.simple_spinner_item, mArrYears);
        id_spn_nam_den_thang.setAdapter(adapterNam);
        id_spn_nam_tu_thang.setAdapter(adapterNam);
        id_lv_list_ttoan_tnuoc = (ListView) v.findViewById(R.id.id_lv_list_ttoan_tnuoc);
        if (mArrTToanTienDienList != null) {
            adapter = new ListViewTToanTNuocAdapter(fpActivity, mArrTToanTienDienList);
            id_lv_list_ttoan_tnuoc.setAdapter(adapter);
        }
        //
        id_btn_timkiem = (Button) v.findViewById(R.id.id_btn_timkiem);
        id_tv_tongtien = (TextView) v.findViewById(R.id.id_tv_tongtien);
        id_ly_ttoan_title = (LinearLayout)  v.findViewById(R.id.id_ly_ttoan_title);
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

    private void setData_To_Spinner() {
        thisYear = Calendar.getInstance().get(Calendar.YEAR);
        thisMonth = Calendar.getInstance().get(Calendar.MONTH);
        mArrYears.clear();
        mArrMonth.clear();
        for (int i = 2010; i <= thisYear; i++) {
            mArrYears.add(Integer.toString(i));
        }
        for (int i = 1; i <= 12; i++) {
            mArrMonth.add(Integer.toString(i));
        }
    }

    private void initData() {

    }

    @Override
    protected void addListener() {
        id_btn_timkiem.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_timkiem:
                if(CommonHelper.isNetworkAvailable(fpActivity)){
                    GlobalVariable.URL_TC4_THONG_TIN_THANH_TOAN_TIEN_NUOC
                            = GlobalVariable.URL_TC4_THONG_TIN_THANH_TOAN_TIEN_NUOC_TUNGAY_DENNGAY
                            + "/"+CommonHelper.getNamThangNgay(id_spn_nam_tu_thang.getSelectedItem().toString(),id_spn_thang_tu_thang.getSelectedItem().toString(),""+1 )
                    + "/"+CommonHelper.getNamThangNgay(id_spn_nam_den_thang.getSelectedItem().toString(),id_spn_thang_den_thang.getSelectedItem().toString(),""+30 ) + "?idkh="+GlobalVariable.KHACH_HANG_CURRENT.getIdKh() ;

                    if(GlobalVariable.KHACH_HANG_CURRENT.getIdKh().isEmpty()){

                        new GetTCThongTinThanhToanTienNuocAllTask().execute();
                        id_tv_tongtien.setVisibility(View.VISIBLE);
                        id_ly_ttoan_title.setVisibility(View.GONE);
                    }else {
                        id_tv_tongtien.setVisibility(View.GONE);
                        id_ly_ttoan_title.setVisibility(View.VISIBLE);
                        new GetTCThongTinThanhToanTienNuocTask().execute();

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
        id_spn_thang_tu_thang.setSelection(thisMonth);
        id_spn_thang_den_thang.setSelection(thisMonth);
        id_spn_nam_den_thang.setSelection(mArrYears.size() - 1);
        id_spn_nam_tu_thang.setSelection(mArrYears.size() - 1);
        GlobalVariable.URL_TC4_THONG_TIN_THANH_TOAN_TIEN_NUOC = GlobalVariable.URL_TC4_THONG_TIN_THANH_TOAN_TIEN_NUOC_4KY+ "?idkh=" + GlobalVariable.KHACH_HANG_CURRENT.getIdKh();
    }

    //
    public class GetTCThongTinThanhToanTienNuocTask extends AsyncTask<String, Void, Void> {
        boolean isError = false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        @Override
        public Void doInBackground(String... params) {

            ResultFromWebservice rs = new ResultFromWebservice();
            GetTraCuu4ThongTinThanhToanTienNuocResponse getTraCuu4ThongTinThanhToanTienNuocResponse = rs.getTCThongTinThanhToanTienNuocResponse(fpActivity, GlobalVariable.LOGIN_TOKEN_TYPE, GlobalVariable.LOGIN_ACCESS_TOKEN,fpActivity);
            if (getTraCuu4ThongTinThanhToanTienNuocResponse != null && getTraCuu4ThongTinThanhToanTienNuocResponse.getmArrItem() != null) {

                mArrTToanTienDienList.clear();
                mArrTToanTienDienList.addAll(getTraCuu4ThongTinThanhToanTienNuocResponse.getmArrItem());
            }
            isError = getTraCuu4ThongTinThanhToanTienNuocResponse.hasError();
            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            disMissLoading();
            adapter.refresh(mArrTToanTienDienList);
            if(mArrTToanTienDienList != null && mArrTToanTienDienList.size()>1){
                int count = mArrTToanTienDienList.size();
                id_spn_thang_den_thang.setSelection(mArrMonth.indexOf(mArrTToanTienDienList.get(0).getThang()));
                id_spn_nam_den_thang.setSelection(mArrYears.indexOf(mArrTToanTienDienList.get(0).getNam()));

                id_spn_thang_tu_thang.setSelection(mArrMonth.indexOf(mArrTToanTienDienList.get(count-1).getThang()));
                id_spn_nam_tu_thang.setSelection(mArrYears.indexOf(mArrTToanTienDienList.get(count-1).getNam()));
            }
            if (isError) {
                Log.d("abc", "" + "---------------: " + isError);
                fpActivity.startDangNhap4();
            }
        }
    }
    public class GetTCThongTinThanhToanTienNuocAllTask extends AsyncTask<String, Void, Void> {
        boolean isError = false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        @Override
        public Void doInBackground(String... params) {

            ResultFromWebservice rs = new ResultFromWebservice();
            GetTraCuu4ThongTinThanhToanAllResponse getTraCuu4ThongTinThanhToanTienNuocResponse = rs.getTCThongTinThanhToanTienNuocAllResponse(fpActivity, GlobalVariable.LOGIN_TOKEN_TYPE, GlobalVariable.LOGIN_ACCESS_TOKEN,fpActivity, CommonHelper.getNamThangNgay(id_spn_nam_tu_thang.getSelectedItem().toString(),id_spn_thang_tu_thang.getSelectedItem().toString(),""+1 ),CommonHelper.getNamThangNgay(id_spn_nam_den_thang.getSelectedItem().toString(),id_spn_thang_den_thang.getSelectedItem().toString(),""+30 ),adapterCustomerGroup.getIdkhAll());
            if (getTraCuu4ThongTinThanhToanTienNuocResponse != null && getTraCuu4ThongTinThanhToanTienNuocResponse.getmArrItem() != null) {

                sumTotal = getTraCuu4ThongTinThanhToanTienNuocResponse.getSumAll();
                mArrTToanTienDienList.clear();
                mArrTToanTienDienList.addAll(getTraCuu4ThongTinThanhToanTienNuocResponse.getmArrItem());
            }
            isError = getTraCuu4ThongTinThanhToanTienNuocResponse.hasError();
            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            disMissLoading();
            adapter.refresh(mArrTToanTienDienList);
            id_tv_tongtien.setText("Tổng tiền : "+ sumTotal + " (VNĐ)");
            if(mArrTToanTienDienList != null && mArrTToanTienDienList.size()>1){
                int count = mArrTToanTienDienList.size();
                id_spn_thang_den_thang.setSelection(mArrMonth.indexOf(mArrTToanTienDienList.get(0).getThang()));
                id_spn_nam_den_thang.setSelection(mArrYears.indexOf(mArrTToanTienDienList.get(0).getNam()));

                id_spn_thang_tu_thang.setSelection(mArrMonth.indexOf(mArrTToanTienDienList.get(count-1).getThang()));
                id_spn_nam_tu_thang.setSelection(mArrYears.indexOf(mArrTToanTienDienList.get(count-1).getNam()));
            }
            if (isError) {
                Log.d("abc", "" + "---------------: " + isError);
                fpActivity.startDangNhap4();
            }
        }
    }
}
