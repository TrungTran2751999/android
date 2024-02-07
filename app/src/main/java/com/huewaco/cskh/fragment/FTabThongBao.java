package com.huewaco.cskh.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.adapter.GridTaskAdapter;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.interfacex.ITFRefreshThongBaoDaDoc;
import com.huewaco.cskh.objects.TaskObj;
import com.huewaco.cskh.objects.ThongBaoListItemObj;
import com.huewaco.cskh.webservice.objects.GetThongBaoSoTinChuaDocResponse;
import com.huewaco.cskh.webservice.processing.ResultFromWebservice;

import java.util.ArrayList;
import java.util.HashMap;


public class FTabThongBao extends FParent implements AdapterView.OnItemClickListener, ITFRefreshThongBaoDaDoc{
    private GridView id_grid_task;
    private ArrayList<TaskObj> mArrTasks = new ArrayList<TaskObj>();
    private GridTaskAdapter adapter;


    //
    HashMap<String, ArrayList<ThongBaoListItemObj>> hashMap = new HashMap<String, ArrayList<ThongBaoListItemObj>>();

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
        View v = inflater.inflate(R.layout.frag_tab_thongbao, container, false);
        fpActivity.itfRefreshThongBaoDaDoc = this;
        initCommonView(v, this);
        initComponent(v);
        addListener();
        setText();
        if (CommonHelper.isNetworkAvailable(fpActivity)) {
            new GetSoLuongThongBaoMoiTask().execute();
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
        id_grid_task = (GridView) v.findViewById(R.id.id_grid_task);
        //---
        TaskObj lichcatnuoc = new TaskObj();
        lichcatnuoc.setNameTask(getString(R.string.tab_thongbao_tb_lichcatnuoc));
        lichcatnuoc.setDrawable(fpActivity.getResources().getDrawable(R.drawable.thongbao_lichcatnuoc));
        //lichcatnuoc.setNumberNotification(3);
        mArrTasks.add(lichcatnuoc);
        //---
        TaskObj matnuoc = new TaskObj();
        matnuoc.setNameTask(getString(R.string.tab_thongbao_tb_ttmatnuoc));
        matnuoc.setDrawable(fpActivity.getResources().getDrawable(R.drawable.thongtinsucomatnuoc));
        mArrTasks.add(matnuoc);

        //---
        TaskObj tiennuoc = new TaskObj();
        tiennuoc.setNameTask(getString(R.string.tab_thongbao_tb_tttiennuoc));
        tiennuoc.setDrawable(fpActivity.getResources().getDrawable(R.drawable.thongtintiennuoc));
        mArrTasks.add(tiennuoc);
        //---
        TaskObj thanhtoan = new TaskObj();
        thanhtoan.setNameTask(getString(R.string.tab_thongbao_tb_thanhtoan));
        thanhtoan.setDrawable(fpActivity.getResources().getDrawable(R.drawable.thongbao_thanhtoan));
        mArrTasks.add(thanhtoan);

        //---
        TaskObj notiennuoc = new TaskObj();
        notiennuoc.setNameTask(getString(R.string.tab_thongbao_tb_notiennuoc));
        notiennuoc.setDrawable(fpActivity.getResources().getDrawable(R.drawable.thongbaonotiennuoc));
        mArrTasks.add(notiennuoc);
        //---
        TaskObj thongbaoghichiso = new TaskObj();
        thongbaoghichiso.setNameTask(getString(R.string.tab_thongbao_tb_ghichiso));
        thongbaoghichiso.setDrawable(fpActivity.getResources().getDrawable(R.drawable.ghichiso1));
        mArrTasks.add(thongbaoghichiso);

        //---
        TaskObj khac = new TaskObj();
        khac.setNameTask(getString(R.string.tab_thongbao_tb_khac));
        khac.setDrawable(fpActivity.getResources().getDrawable(R.drawable.thongbaokhac));
        mArrTasks.add(khac);
        //---
        adapter = new GridTaskAdapter(fpActivity, mArrTasks);
        id_grid_task.setAdapter(adapter);

    }

    @Override
    protected void addListener() {
        id_grid_task.setOnItemClickListener(this);
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

        startNewFrag(null, position);
    }

    public void startNewFrag(ArrayList<ThongBaoListItemObj> mArr, int position) {
        FTabThongBaoList frg2 = new FTabThongBaoList();
        frg2.position = position;
        //frg2.mArrThongBaoList = mArr;
        frg2.title = mArrTasks.get(position).getNameTask();
        FragmentTransaction transaction2 = getChildFragmentManager().beginTransaction();
        if (GlobalVariable.IS_ANIMATION) {
            transaction2.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        transaction2.addToBackStack(null);
        transaction2.replace(R.id.id_fr_tab_thongbao, frg2).commit();
    }

    public class GetSoLuongThongBaoMoiTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //showLoading();
        }

        @Override
        public Void doInBackground(String... params) {

            ResultFromWebservice rs = new ResultFromWebservice();
            //
            GetThongBaoSoTinChuaDocResponse getThongBaoSoTinChuaDocResponse = rs.getThongBaoSoTinChuaDocResponse(fpActivity, GlobalVariable.LOGIN_TOKEN_TYPE, GlobalVariable.LOGIN_ACCESS_TOKEN,fpActivity);
            if (getThongBaoSoTinChuaDocResponse != null) {
                for (int i = 0; i < mArrTasks.size(); i++) {
                    TaskObj t = mArrTasks.get(i);
                    if (i == 0) {
                        t.setNumberNotification(getThongBaoSoTinChuaDocResponse.getThongBaoLichCatNuoc());
                    } else if (i == 1) {
                        t.setNumberNotification(getThongBaoSoTinChuaDocResponse.getThongTinSuCoMatNuoc());
                    } else if (i == 2) {
                        t.setNumberNotification(getThongBaoSoTinChuaDocResponse.getThongTinTienNuoc());
                    } else if (i == 3) {
                        t.setNumberNotification(getThongBaoSoTinChuaDocResponse.getThongBaoThanhToan());
                    } else if (i == 4) {
                        t.setNumberNotification(getThongBaoSoTinChuaDocResponse.getThongBaoNoTienNuoc());
                    } else if (i == 5) {
                        t.setNumberNotification(getThongBaoSoTinChuaDocResponse.getThongBaoGhiChiSo());
                    } else if (i == 6) {
                        t.setNumberNotification(getThongBaoSoTinChuaDocResponse.getThongBaoKhac());
                    }
                }
            }
            GlobalVariable.TONG_TIN_CHUA_DOC = getThongBaoSoTinChuaDocResponse.getTongSoTinChuaDoc();
            //Log.d("abc", "" + getThongBaoResponse.getIdkh());
            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            //disMissLoading();
            adapter.refresh(mArrTasks);
            fpActivity.showBadgeNumber(GlobalVariable.TONG_TIN_CHUA_DOC);
        }
    }
    @Override
    public void reload(int position){
        TaskObj task = mArrTasks.get(position);
        int count = task.getNumberNotification();
        if(count > 0){
            count--;
            task.setNumberNotification(count);
            adapter.refresh(mArrTasks);
        }
    }
}
