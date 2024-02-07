package com.huewaco.cskh.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.huewaco.cskh.activity.ABaoCaoSuCo;
import com.huewaco.cskh.activity.ABaoCaoSuCo_List;
import com.huewaco.cskh.activity.ABaoCaoSuCo_Recycler;
import com.huewaco.cskh.activity.AGhiChiSo;
import com.huewaco.cskh.activity.AGhiChiSoAPI;
import com.huewaco.cskh.activity.AHuongDanSuDung;
import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.adapter.GridTaskAdapter;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.objects.TaskObj;


import java.util.ArrayList;


public class FTabDichVu extends FParent implements AdapterView.OnItemClickListener {
    private GridView id_grid_task;
    private ArrayList<TaskObj> mArrTasks = new ArrayList<TaskObj>();
    private GridTaskAdapter adapter;

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
        View v = inflater.inflate(R.layout.frag_tab_dichvu, container, false);
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
        id_grid_task = (GridView) v.findViewById(R.id.id_grid_task);
        //---0
        TaskObj dkycapnuoc = new TaskObj();
        dkycapnuoc.setNameTask(getString(R.string.tab_dichvu_dkycapnuoc));
        dkycapnuoc.setDrawable(fpActivity.getResources().getDrawable(R.drawable.dangkycapnuocmoi));
        //dkycapnuoc.setNumberNotification(3);
        mArrTasks.add(dkycapnuoc);
        //---1
        TaskObj yeucaunangdoido = new TaskObj();
        yeucaunangdoido.setNameTask(getString(R.string.tab_dichvu_yeucaunangdoido));
        yeucaunangdoido.setDrawable(fpActivity.getResources().getDrawable(R.drawable.thaydoicongsuat));
        mArrTasks.add(yeucaunangdoido);

        //---2
        TaskObj yeucauhoi_traloi = new TaskObj();
        yeucauhoi_traloi.setNameTask(getString(R.string.tab_dichvu_hoi_traloi));
        yeucauhoi_traloi.setDrawable(fpActivity.getResources().getDrawable(R.drawable.didoicongto));
        mArrTasks.add(yeucauhoi_traloi);
        //---3
        TaskObj chatzalo = new TaskObj();
        chatzalo.setNameTask(getString(R.string.tab_dichvu_chatzalo));
        chatzalo.setDrawable(fpActivity.getResources().getDrawable(R.drawable.ic_zalo_chat));
        chatzalo.setLink(getString(R.string.zalochat_link));//zalochat_link
        mArrTasks.add(chatzalo);

        //---4
        TaskObj lienketnganhang = new TaskObj();
        lienketnganhang.setNameTask(getString(R.string.tab_tracuu_lienketnganhang));
        lienketnganhang.setDrawable(fpActivity.getResources().getDrawable(R.drawable.lienketnganhang));
        mArrTasks.add(lienketnganhang);
//        //---5 Chuyển sang FTabTraCuu
//        TaskObj barcode = new TaskObj();
//        barcode.setNameTask(getString(R.string.tab_dichvu_barcode));
//        barcode.setDrawable(fpActivity.getResources().getDrawable(R.drawable.ic_barcode));
//        mArrTasks.add(barcode);
        //---5 Hóa đơn điện tử
        TaskObj contract = new TaskObj();
        contract.setNameTask(getString(R.string.tab_dichvu_hddt));
        contract.setDrawable(fpActivity.getResources().getDrawable(R.drawable.ic_xacthuchd));
        mArrTasks.add(contract);
        //---6
        TaskObj gcs = new TaskObj();
        gcs.setNameTask(getString(R.string.tab_dichvu_gcs));
        gcs.setDrawable(fpActivity.getResources().getDrawable(R.drawable.ghichiso1));
        mArrTasks.add(gcs);
        //---7
        TaskObj bcaosuco = new TaskObj();
        bcaosuco.setNameTask(getString(R.string.tab_dichvu_bcaosuco));
        bcaosuco.setDrawable(fpActivity.getResources().getDrawable(R.drawable.thongtinsucomatnuoc));
        mArrTasks.add(bcaosuco);


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
        if (position == 0) {
            FTabDichVu0DKyCapNuoc frg = new FTabDichVu0DKyCapNuoc();
            frg.title = mArrTasks.get(position).getNameTask();
            FragmentTransaction transaction2 = getChildFragmentManager().beginTransaction();
            if (GlobalVariable.IS_ANIMATION) {
                transaction2.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            transaction2.addToBackStack(null);
            transaction2.replace(R.id.id_fr_tab_dichvu, frg).commit();

        }else if (position == 1) {
            FTabDichVu2NangDoiDo frg = new FTabDichVu2NangDoiDo();
            frg.title = mArrTasks.get(position).getNameTask();
            FragmentTransaction transaction2 = getChildFragmentManager().beginTransaction();
            if (GlobalVariable.IS_ANIMATION) {
                transaction2.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            transaction2.addToBackStack(null);
            transaction2.replace(R.id.id_fr_tab_dichvu, frg).commit();

        } else if (position == 2) {
            FTabDichVu3HoiTraLoi frg = new FTabDichVu3HoiTraLoi();
            frg.title = mArrTasks.get(position).getNameTask();
            FragmentTransaction transaction2 = getChildFragmentManager().beginTransaction();
            if (GlobalVariable.IS_ANIMATION) {
                transaction2.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            transaction2.addToBackStack(null);
            transaction2.replace(R.id.id_fr_tab_dichvu, frg).commit();

        }
        else if(position == 3){
            //ZALO chat
            String link = mArrTasks.get(position).getLink();
            if(CommonHelper.checkValidString(link)){
                CommonHelper.openFile(fpActivity,link);
            }
        }
        else if (position == 4) {
            FTabDichVuThanhToanOnline frg = new FTabDichVuThanhToanOnline();
            frg.title = mArrTasks.get(position).getNameTask();
            FragmentTransaction transaction2 = getChildFragmentManager().beginTransaction();
            if (GlobalVariable.IS_ANIMATION) {
                transaction2.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            transaction2.addToBackStack(null);
            transaction2.replace(R.id.id_fr_tab_dichvu, frg).commit();
//            FTabTraCuu7LienKetNganHang frg = new FTabTraCuu7LienKetNganHang();
//            frg.title = mArrTasks.get(position).getNameTask();
//            FragmentTransaction transaction2 = getChildFragmentManager().beginTransaction();
//            if (GlobalVariable.IS_ANIMATION) {
//                transaction2.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
//            }
//            transaction2.addToBackStack(null);
//            transaction2.replace(R.id.id_fr_tab_dichvu, frg).commit();
        }
        else if(position == 5){
            // Hddt
            FTabDichVu4Hddt frg1 = new FTabDichVu4Hddt();
            frg1.title = fpActivity.getString(R.string.tab_dichvu_hddt);
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            if (GlobalVariable.IS_ANIMATION) {
                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            transaction.addToBackStack(null);
            transaction.replace(R.id.id_fr_tab_dichvu, frg1).commit();


        }
        else if (position == 6) {
//
            FTabDichVuGCSHomeLoadMore frg = new FTabDichVuGCSHomeLoadMore();
            frg.title = mArrTasks.get(position).getNameTask();
            FragmentTransaction transaction2 = getChildFragmentManager().beginTransaction();
            if (GlobalVariable.IS_ANIMATION) {
                transaction2.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            transaction2.addToBackStack(null);
            transaction2.replace(R.id.id_fr_tab_dichvu, frg).commit();
        }else if (position == 7) {
            startActivity(new Intent(fpActivity, ABaoCaoSuCo_Recycler.class));

        }else {
            CommonHelper.showWarning(fpActivity, "Đang xây dựng chức năng");
        }
    }
    // Move to FtabTracuu
    private void barcodeShowing(){
//        final Dialog dialog = new Dialog(fpActivity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.setContentView(R.layout.dialog_barcode);
//        dialog.setTitle(R.string.app_name);
//        ImageView id_img_code128 = (ImageView) dialog.findViewById(R.id.id_img_code128);
//        String barcode = "XX"+GlobalVariable.KHACH_HANG.getMa_khang();
//        CommonHelper.showCode128(barcode, (int) fpActivity.getResources().getDimension(R.dimen.layout_750), (int) fpActivity.getResources().getDimension(R.dimen.layout_250), id_img_code128);
//        TextView tvBarcode = (TextView) dialog.findViewById(R.id.id_tv_barcode);
//        tvBarcode.setVisibility(View.GONE);
//        tvBarcode.setText(barcode);
//        dialog.show();
    }
}
