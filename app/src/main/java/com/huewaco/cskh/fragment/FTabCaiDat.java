package com.huewaco.cskh.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentTransaction;

import com.huewaco.cskh.activity.AHuongDanSuDung;
import com.huewaco.cskh.activity.AThongTinUngDung;
import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.objects.KhuVucObj;
import com.huewaco.cskh.objects.QuanHuyenListItemObj;
import com.huewaco.cskh.webservice.objects.GetDichVu0DanhMucDiaChinh_KhuVucResponse;
import com.huewaco.cskh.webservice.objects.GetDichVu0DanhMucDiaChinh_QuanHuyenPhuongXaResponse;
import com.huewaco.cskh.webservice.objects.GetLogOutResponse;
import com.huewaco.cskh.webservice.processing.ResultFromWebservice;

import java.util.ArrayList;


public class FTabCaiDat extends FParent {
    private ViewGroup id_ly_goitongdai,id_ly_webcskh, id_ly_thongtinungdung,id_ly_thaydoithongtinkh, id_ly_hdsd, id_ly_doimatkhau, id_ly_dangxuat;
    private boolean IS_LOGOUT;
    private LinearLayout id_ly_laydsdiachinh;
    private  ArrayList<KhuVucObj> mArrItemKhuVuc = new ArrayList<>();
    private  ArrayList<QuanHuyenListItemObj> mArrItemQuanHuyen = new ArrayList<>();

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
        View v = inflater.inflate(R.layout.frag_tab_tienich, container, false);
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
        id_ly_goitongdai = (ViewGroup) v.findViewById(R.id.id_ly_goitongdai);
        id_ly_webcskh = (ViewGroup)v.findViewById(R.id.id_ly_webcskh);
        id_ly_thongtinungdung = (ViewGroup) v.findViewById(R.id.id_ly_thongtinungdung);
        id_ly_thaydoithongtinkh = (ViewGroup) v.findViewById(R.id.id_ly_thaydoithongtinkh);
        id_ly_hdsd = (ViewGroup) v.findViewById(R.id.id_ly_hdsd);
        id_ly_hdsd.setVisibility(View.GONE);
        id_ly_doimatkhau = (ViewGroup) v.findViewById(R.id.id_ly_doimatkhau);
        id_ly_dangxuat = (ViewGroup) v.findViewById(R.id.id_ly_dangxuat);
        id_ly_laydsdiachinh = (LinearLayout) v.findViewById(R.id.id_ly_laydsdiachinh);
    }

    @Override
    protected void addListener() {
        id_ly_webcskh.setOnClickListener(this);
        id_ly_goitongdai.setOnClickListener(this);
        id_ly_thongtinungdung.setOnClickListener(this);
        id_ly_thaydoithongtinkh.setOnClickListener(this);
        id_ly_hdsd.setOnClickListener(this);
        id_ly_doimatkhau.setOnClickListener(this);
        id_ly_dangxuat.setOnClickListener(this);
        id_ly_laydsdiachinh.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_ly_webcskh:
                if (CommonHelper.isNetworkAvailable(fpActivity)) {
                    CommonHelper.openWebCSKH(fpActivity, GlobalVariable.LINK_WEB_CSKH);
                } else {
                    CommonHelper.showWarning(fpActivity, getString(R.string.nointernet));
                }
                break;
            case R.id.id_ly_goitongdai:
                if (CommonHelper.isCallPhonePermissionGranted(fpActivity)) {
                    CommonHelper.callPhone(fpActivity, getString(R.string.hotline));
                } else {

                }
                
                break;
            case R.id.id_ly_thongtinungdung:
                startActivity(new Intent(fpActivity, AThongTinUngDung.class));
                break;
            case R.id.id_ly_thaydoithongtinkh:
                FTabCDDichVu1ThayDoiThongTinKH frgFTabCDDichVu1ThayDoiThongTinKH = new FTabCDDichVu1ThayDoiThongTinKH();
                FragmentTransaction transactionFTabDichVu1ThayDoiThongTinKH = getChildFragmentManager().beginTransaction();
                if (GlobalVariable.IS_ANIMATION) {
                    transactionFTabDichVu1ThayDoiThongTinKH.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                transactionFTabDichVu1ThayDoiThongTinKH.addToBackStack(null);
                transactionFTabDichVu1ThayDoiThongTinKH.replace(R.id.id_fr_tab_caidat, frgFTabCDDichVu1ThayDoiThongTinKH).commit();
                break;
            case R.id.id_ly_laydsdiachinh:

                if(CommonHelper.isNetworkAvailable(fpActivity)){
                    new   GetDichVu0DanhMucDiaChinh_KhuVucTask().execute();
                    new GetDichVu0DanhMucDiaChinh_QuanHuyenTask().execute();
                }else{
                    CommonHelper.showWarning(fpActivity,getString(R.string.nointernet));
                }


                break;
            case R.id.id_ly_hdsd:
                startActivity(new Intent(fpActivity, AHuongDanSuDung.class));
                break;
            case R.id.id_ly_doimatkhau:
                FTabCDDoiMatKhau frg1 = new FTabCDDoiMatKhau();
                frg1.title = fpActivity.getString(R.string.tab_caidat_doimatkhau);
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                if (GlobalVariable.IS_ANIMATION) {
                    transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                transaction.addToBackStack(null);
                transaction.replace(R.id.id_fr_tab_caidat, frg1).commit();
                break;
            case  R.id.id_ly_dangxuat:
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                new GetLogOutTask().execute();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(fpActivity);
                builder.setMessage(fpActivity.getString(R.string.tab_caidat_dangxuat_noidung)).setPositiveButton(fpActivity.getString(R.string.tab_caidat_dangxuat_co), dialogClickListener)
                        .setNegativeButton(fpActivity.getString(R.string.tab_caidat_dangxuat_khong), dialogClickListener).show();

            default:
                break;
        }

    }

    public void setText() {

    }

    // toandtb
    public class GetLogOutTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        @Override
        public Void doInBackground(String... params) {
            ResultFromWebservice rs = new ResultFromWebservice();
            //GetLoginResponse getLogin = rs.getLogin("thilien", "000000");
            GetLogOutResponse getLogOut = rs.getLogOutResponse(fpActivity, GlobalVariable.KHACH_HANG.getMa_khang(),GlobalVariable.DEVICE_TOKEN,GlobalVariable.DEVICE_TYPE,GlobalVariable.LOGIN_TOKEN_TYPE+" "+GlobalVariable.LOGIN_ACCESS_TOKEN,fpActivity);
            IS_LOGOUT = Boolean.parseBoolean(getLogOut.getReturnString());
            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            disMissLoading();
            if(IS_LOGOUT){
                GlobalVariable.logOut(fpActivity);
                fpActivity.logOut();
            } else {
                CommonHelper.showWarning(fpActivity,fpActivity.getString(R.string.tab_caidat_dangxuat_dangxuatkhongduoc) );
            }
        }
    }

    // toandtb: Khu vực
    public class GetDichVu0DanhMucDiaChinh_KhuVucTask extends AsyncTask<String, Void, Void> {
        boolean isError = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        @Override
        public Void doInBackground(String... params) {

            ResultFromWebservice rs = new ResultFromWebservice();
            GetDichVu0DanhMucDiaChinh_KhuVucResponse getDichVu0DanhMucDiaChinhResponse = rs.getDichVu0DanhMucDiaChinhResponse(fpActivity, GlobalVariable.LOGIN_TOKEN_TYPE, GlobalVariable.LOGIN_ACCESS_TOKEN,fpActivity);
             if (getDichVu0DanhMucDiaChinhResponse != null && getDichVu0DanhMucDiaChinhResponse.getmArrItemKhuVuc() != null) {

                 mArrItemKhuVuc.clear();
                 mArrItemKhuVuc.addAll(getDichVu0DanhMucDiaChinhResponse.getmArrItemKhuVuc());

             }
            isError = getDichVu0DanhMucDiaChinhResponse.hasError();
            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            disMissLoading();
             if (isError) {
                 CommonHelper.showWarning(fpActivity,getString(R.string.loi_ket_noi_khuvuc));
             } else {
                 if(mArrItemKhuVuc!=null && mArrItemKhuVuc.size()>0) {
                    CommonHelper.writeKhuVucObjs(fpActivity, mArrItemKhuVuc, GlobalVariable.KHU_VUC_FILE);
                     CommonHelper.showWarning(fpActivity,getString(R.string.luu_ket_noi_khuvuc));
                 }
             }
        }
    }

    // toandtb: Quận huyện
    public class GetDichVu0DanhMucDiaChinh_QuanHuyenTask extends AsyncTask<String, Void, Void> {
        boolean isError = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        @Override
        public Void doInBackground(String... params) {

            ResultFromWebservice rs = new ResultFromWebservice();
            GetDichVu0DanhMucDiaChinh_QuanHuyenPhuongXaResponse dichVu0DanhMucDiaChinh_QuanHuyenPhuongXaResponse = rs.getDichVu0DanhMucDiaChinh_QuanHuyenPhuongXaResponse(fpActivity, GlobalVariable.LOGIN_TOKEN_TYPE, GlobalVariable.LOGIN_ACCESS_TOKEN,fpActivity);
                if (dichVu0DanhMucDiaChinh_QuanHuyenPhuongXaResponse != null && dichVu0DanhMucDiaChinh_QuanHuyenPhuongXaResponse.getmArrItemQuanHuyen() != null) {

                    mArrItemQuanHuyen.clear();
                    mArrItemQuanHuyen.addAll(dichVu0DanhMucDiaChinh_QuanHuyenPhuongXaResponse.getmArrItemQuanHuyen());

            }
            isError = dichVu0DanhMucDiaChinh_QuanHuyenPhuongXaResponse.hasError();
            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            disMissLoading();
            if (isError) {
                CommonHelper.showWarning(fpActivity,getString(R.string.loi_ket_noi_quanhuyen));
            } else {
                if(mArrItemKhuVuc!=null && mArrItemKhuVuc.size()>0) {
                    CommonHelper.writeQuanHuyenCache(fpActivity, mArrItemQuanHuyen, GlobalVariable.QUAN_HUYEN_FILE);
                    CommonHelper.showWarning(fpActivity,getString(R.string.luu_ket_noi_quanhuyen));
                }
            }
        }
    }
}
