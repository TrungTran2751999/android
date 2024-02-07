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

import androidx.annotation.Nullable;

import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.adapter.ListViewCustomerGroupListItemAdapter;
import com.huewaco.cskh.adapter.ListViewHDonTNuocAdapter;
import com.huewaco.cskh.adapter.ListViewQuanHuyenListItemAdapter;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.objects.HDonTNuocListItemObj;
import com.huewaco.cskh.objects.KhachHangObj;
import com.huewaco.cskh.webservice.objects.GetTraCuu5TCuHDonResponse;
import com.huewaco.cskh.webservice.processing.ResultFromWebservice;

import java.util.ArrayList;
import java.util.Calendar;


public class FTabTraCuu5HDonTienNuoc extends FParent {
    private ListView id_lv_list_hdon_tnuoc;
    private ListViewHDonTNuocAdapter adapter;
    private ArrayList<HDonTNuocListItemObj> mArrHDonTNuocList = new ArrayList<>();;
    protected String title;
    //
    private Spinner  id_spn_thang, id_spn_nam,id_spn_customergroup;
    LinearLayout id_layoutcmb;
    private ArrayList<String> mArrYears = new ArrayList<String>();
    private ArrayList<String> mArrMonth = new ArrayList<String>();
    private int thisYear, thisMonth;
    private Button id_btn_timkiem;
    //



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
        View v = inflater.inflate(R.layout.frag_tab_tracuu5_hdon_dtu, container, false);
        initCommonView(v, this);
        initData();
        initComponent(v);
        addListener();
        setText();
        if(CommonHelper.isNetworkAvailable(fpActivity)){
            new GetTraCuu5HDonDienTuTask().execute();
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
        id_spn_thang = (Spinner) v.findViewById(R.id.id_spn_thang);
        id_spn_nam = (Spinner) v.findViewById(R.id.id_spn_nam);
         id_btn_timkiem = (Button) v.findViewById(R.id.id_btn_timkiem);
        setData_To_Spinner();
              ArrayAdapter<String> adapterThang = new ArrayAdapter<String>(fpActivity, android.R.layout.simple_spinner_item, mArrMonth);
        id_spn_thang.setAdapter(adapterThang);
        ArrayAdapter<String> adapterNam = new ArrayAdapter<String>(fpActivity, android.R.layout.simple_spinner_item, mArrYears);
        id_spn_nam.setAdapter(adapterNam);
        id_lv_list_hdon_tnuoc = (ListView) v.findViewById(R.id.id_lv_list_hdon_tnuoc);
        if (mArrHDonTNuocList != null) {
            adapter = new ListViewHDonTNuocAdapter(fpActivity, mArrHDonTNuocList);
            id_lv_list_hdon_tnuoc.setAdapter(adapter);
        }
        id_layoutcmb = (LinearLayout)  v.findViewById(R.id.id_layoutcmb);
        id_spn_customergroup = (Spinner) v.findViewById(R.id.id_spn_customergroup);
        ListViewCustomerGroupListItemAdapter adapterCustomerGroup = new ListViewCustomerGroupListItemAdapter(fpActivity,false  );
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
                    GlobalVariable.URL_TC5_HOADON = GlobalVariable.URL_TC5_HOADON_BYKY+"/"+id_spn_thang.getSelectedItem().toString()+"/"+id_spn_nam.getSelectedItem().toString()+"?idkh="+ GlobalVariable.KHACH_HANG_CURRENT.getIdKh();
                        new GetTraCuu5HDonDienTuTask().execute();

                }else{
                    CommonHelper.showWarning(fpActivity,getString(R.string.nointernet));
                }
                break;
            default:
                break;
        }

    }

    public void setText() {
        GlobalVariable.URL_TC5_HOADON = GlobalVariable.URL_TC5_HOADON_LAST+ "?idkh=" + GlobalVariable.KHACH_HANG_CURRENT.getIdKh();
    }

    //
    public class GetTraCuu5HDonDienTuTask extends AsyncTask<String, Void, Void> {
        boolean isError = false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        @Override
        public Void doInBackground(String... params) {

            ResultFromWebservice rs = new ResultFromWebservice();
            GetTraCuu5TCuHDonResponse getTraCuu5TCuHDonResponse = rs.getTraCuu5TCuHDonResponse(fpActivity, GlobalVariable.LOGIN_TOKEN_TYPE, GlobalVariable.LOGIN_ACCESS_TOKEN,fpActivity);
            //mArrHDonTNuocList.clear();
            if (getTraCuu5TCuHDonResponse != null && getTraCuu5TCuHDonResponse.getmArrItem() != null) {
                mArrHDonTNuocList.clear();
                mArrHDonTNuocList.addAll(getTraCuu5TCuHDonResponse.getmArrItem());
            }else{
                //mArrHDonTNuocList.add(new HDonTNuocListItemObj());
            }
            isError = getTraCuu5TCuHDonResponse.hasError();
            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            disMissLoading();
            id_spn_thang.setSelection(mArrMonth.indexOf(mArrHDonTNuocList.get(0).getThang()));
            id_spn_nam.setSelection(mArrYears.indexOf(mArrHDonTNuocList.get(0).getNam()));
            adapter.refresh(mArrHDonTNuocList);
            if (isError) {
                Log.d("abc", "" + "---------------: " + isError);
                fpActivity.startDangNhap4();
            }
        }
    }
}
