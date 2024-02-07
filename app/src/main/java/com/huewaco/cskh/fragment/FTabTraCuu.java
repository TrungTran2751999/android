package com.huewaco.cskh.fragment;

import android.app.Dialog;
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

import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.adapter.GridTaskAdapter;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.objects.TaskObj;

import java.util.ArrayList;


public class FTabTraCuu extends FParent implements AdapterView.OnItemClickListener {
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
        View v = inflater.inflate(R.layout.frag_tab_tracuu, container, false);
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
        TaskObj lichghinuoc = new TaskObj();
        lichghinuoc.setNameTask(getString(R.string.tab_tracuu_lichghinuoc));
        lichghinuoc.setDrawable(fpActivity.getResources().getDrawable(R.drawable.lichghinuoc));
        //lichghinuoc.setNumberNotification(3);
        mArrTasks.add(lichghinuoc);
        //---1
        TaskObj diemthutiennuoc = new TaskObj();
        diemthutiennuoc.setNameTask(getString(R.string.tab_tracuu_diemthutiennuoc));
        diemthutiennuoc.setDrawable(fpActivity.getResources().getDrawable(R.drawable.diemthutiennuoc));
        mArrTasks.add(diemthutiennuoc);
        //---2
        TaskObj lichcatnuoc = new TaskObj();
        lichcatnuoc.setNameTask(getString(R.string.tab_tracuu_lichcatnuoc));
        lichcatnuoc.setDrawable(fpActivity.getResources().getDrawable(R.drawable.lichcatnuoc));
        mArrTasks.add(lichcatnuoc);
        //---3
        TaskObj chisonuoctieuthu = new TaskObj();
        chisonuoctieuthu.setNameTask(getString(R.string.tab_tracuu_chisonuoctieuthu));
        chisonuoctieuthu.setDrawable(fpActivity.getResources().getDrawable(R.drawable.chisonuoctieuthu));
        mArrTasks.add(chisonuoctieuthu);
        //---4
        TaskObj thongtinthanhtoantiennuoc = new TaskObj();
        thongtinthanhtoantiennuoc.setNameTask(getString(R.string.tab_tracuu_thongtinthanhtoantiennuoc));
        thongtinthanhtoantiennuoc.setDrawable(fpActivity.getResources().getDrawable(R.drawable.thongtinthanhtoantiennuoc));
        mArrTasks.add(thongtinthanhtoantiennuoc);
        //---5
        TaskObj tracuuhoadontiennuoc = new TaskObj();
        tracuuhoadontiennuoc.setNameTask(getString(R.string.tab_tracuu_tracuuhoadontiennuoc));
        tracuuhoadontiennuoc.setDrawable(fpActivity.getResources().getDrawable(R.drawable.tracuuhoadontiennuoc));
        mArrTasks.add(tracuuhoadontiennuoc);
        //---6

/*        TaskObj webcskh = new TaskObj();
        webcskh.setNameTask(getString(R.string.tab_tracuu_webcskh));
        webcskh.setDrawable(fpActivity.getResources().getDrawable(R.drawable.ic_web_cskh));
        mArrTasks.add(webcskh);
        //---7
        TaskObj lienketnganhang = new TaskObj();
        lienketnganhang.setNameTask(getString(R.string.tab_tracuu_lienketnganhang));
        lienketnganhang.setDrawable(fpActivity.getResources().getDrawable(R.drawable.lienketnganhang));
        mArrTasks.add(lienketnganhang);
        //---8
        TaskObj goitongdai = new TaskObj();
        goitongdai.setNameTask(getString(R.string.tab_tracuu_goitongdai) + " " + getString(R.string.hotline));
        goitongdai.setDrawable(fpActivity.getResources().getDrawable(R.drawable.goitongdai));
        mArrTasks.add(goitongdai);
        */
        //---
        //---6 Chuyển từ FTabDichvu
//        TaskObj barcode = new TaskObj();
//        barcode.setNameTask(getString(R.string.tab_dichvu_barcode));
//        barcode.setDrawable(fpActivity.getResources().getDrawable(R.drawable.ic_barcode));
//        mArrTasks.add(barcode);

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
            //CommonHelper.showWarning(fpActivity,"chưa có service");
            FTabTraCuu0LichGhiNuoc frg = new FTabTraCuu0LichGhiNuoc();
            frg.title = mArrTasks.get(position).getNameTask();
            FragmentTransaction transaction2 = getChildFragmentManager().beginTransaction();
            if (GlobalVariable.IS_ANIMATION) {
                transaction2.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            transaction2.addToBackStack(null);
            transaction2.replace(R.id.id_fr_tab_tracuu, frg).commit();

        } else if (position == 1) {
            //CommonHelper.showWarning(fpActivity,"đang xây dựng");
            FTabTraCuu1DiemThuTien frg = new FTabTraCuu1DiemThuTien();
            frg.title = mArrTasks.get(position).getNameTask();
            FragmentTransaction transaction2 = getChildFragmentManager().beginTransaction();
            if (GlobalVariable.IS_ANIMATION) {
                transaction2.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            transaction2.addToBackStack(null);
            transaction2.replace(R.id.id_fr_tab_tracuu, frg).commit();

        } else if (position == 2) {
            FTabTraCuu2LichCatNuoc frg = new FTabTraCuu2LichCatNuoc();
            frg.title = mArrTasks.get(position).getNameTask();
            FragmentTransaction transaction2 = getChildFragmentManager().beginTransaction();
            if (GlobalVariable.IS_ANIMATION) {
                transaction2.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            transaction2.addToBackStack(null);
            transaction2.replace(R.id.id_fr_tab_tracuu, frg).commit();

        } else if (position == 3) {
            //CommonHelper.showWarning(fpActivity,"đang xây dựng");
            FTabTraCuu3CSTieuThu frg = new FTabTraCuu3CSTieuThu();
            frg.title = mArrTasks.get(position).getNameTask();
            FragmentTransaction transaction2 = getChildFragmentManager().beginTransaction();
            if (GlobalVariable.IS_ANIMATION) {
                transaction2.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            transaction2.addToBackStack(null);
            transaction2.replace(R.id.id_fr_tab_tracuu, frg).commit();

        } else if (position == 4) {
            //CommonHelper.showWarning(fpActivity,"đang xây dựng");
            FTabTraCuu4TToanTienNuoc frg = new FTabTraCuu4TToanTienNuoc();
            frg.title = mArrTasks.get(position).getNameTask();
            FragmentTransaction transaction2 = getChildFragmentManager().beginTransaction();
            if (GlobalVariable.IS_ANIMATION) {
                transaction2.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            transaction2.addToBackStack(null);
            transaction2.replace(R.id.id_fr_tab_tracuu, frg).commit();

        } else if (position == 5) {
            FTabTraCuu5HDonTienNuoc frg = new FTabTraCuu5HDonTienNuoc();
            frg.title = mArrTasks.get(position).getNameTask();
            FragmentTransaction transaction2 = getChildFragmentManager().beginTransaction();
            if (GlobalVariable.IS_ANIMATION) {
                transaction2.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            transaction2.addToBackStack(null);
            transaction2.replace(R.id.id_fr_tab_tracuu, frg).commit();

        }/*else if(position == 6 || position ==7 || position ==8){
             fpActivity.gotoTabPosition(2);
         }*//* else if (position == 6) {
            if (CommonHelper.isNetworkAvailable(fpActivity)) {
                CommonHelper.openWebCSKH(fpActivity, GlobalVariable.LINK_WEB_CSKH);
            } else {
                CommonHelper.showWarning(fpActivity, getString(R.string.nointernet));
            }
        }else if (position == 7) {
             FTabTraCuu7LienKetNganHang frg = new FTabTraCuu7LienKetNganHang();
             frg.title = mArrTasks.get(position).getNameTask();
             FragmentTransaction transaction2 = getChildFragmentManager().beginTransaction();
             if (GlobalVariable.IS_ANIMATION) {
                 transaction2.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
             }
             transaction2.addToBackStack(null);
             transaction2.replace(R.id.id_fr_tab_tracuu, frg).commit();
         }else if (position == 8) {
            if (CommonHelper.isCallPhonePermissionGranted(fpActivity)) {
                CommonHelper.callPhone(fpActivity, getString(R.string.hotline));
            } else {

            }
        }*/
         // Move from FTabDichVu
         else if (position == 6) {
             barcodeShowing();
         }
         else {
            CommonHelper.showWarning(fpActivity, "Đang xây dựng chức năng");
        }

    }
    private void barcodeShowing(){
        final Dialog dialog = new Dialog(fpActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_barcode);
        dialog.setTitle(R.string.app_name);
        ImageView id_img_code128 = (ImageView) dialog.findViewById(R.id.id_img_code128);
        String barcode = "XX"+GlobalVariable.KHACH_HANG.getMa_khang();
        CommonHelper.showCode128(barcode, (int) fpActivity.getResources().getDimension(R.dimen.layout_750), (int) fpActivity.getResources().getDimension(R.dimen.layout_250), id_img_code128);
        TextView tvBarcode = (TextView) dialog.findViewById(R.id.id_tv_barcode);
        tvBarcode.setVisibility(View.GONE);
        tvBarcode.setText(barcode);
        dialog.show();
    }
}
