package com.huewaco.cskh.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.customview.edittext.CustomEditText;
import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.webservice.objects.GetChangePasswordResponse;
import com.huewaco.cskh.webservice.processing.ResultFromWebservice;


public class FTabCDDoiMatKhau extends FParent {
    protected String title;
    private CustomEditText id_edt_mk_cu, id_edt_mk_moi, id_edt_re_mk_moi;
    private Button id_btn_ok, id_btn_cancel;
    private String mkCu = "", mkMoi = "", re_MKMoi = "";
    private boolean IS_CHANGE_PASSWORD;

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
        View v = inflater.inflate(R.layout.frag_tab_cai_dat_doi_mk, container, false);
        initCommonView(v, this);
        initData();
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
        id_tv_title.setText(title);
        id_edt_mk_cu = (CustomEditText) v.findViewById(R.id.id_edt_mk_cu);
        id_edt_mk_moi = (CustomEditText) v.findViewById(R.id.id_edt_mk_moi);
        id_edt_re_mk_moi = (CustomEditText) v.findViewById(R.id.id_edt_re_mk_moi);
        id_btn_ok = (Button) v.findViewById(R.id.id_btn_close_dialog_bcsc_ng);
        id_btn_cancel = (Button) v.findViewById(R.id.id_btn_cancel);
    }

    private void initData() {

    }

    @Override
    protected void addListener() {
        id_btn_ok.setOnClickListener(this);
        id_btn_cancel.setOnClickListener(this);
        final Drawable right = this.getResources().getDrawable(android.R.drawable.ic_delete);
        right.setBounds(0, 0, right.getIntrinsicHeight(), right.getIntrinsicHeight());
        setEditTextListener(id_edt_mk_cu, null, right);
        setEditTextListener(id_edt_mk_moi, null, right);
        setEditTextListener(id_edt_re_mk_moi, null, right);
    }

    @Override
    public void onClick(View v) {
        mkCu = id_edt_mk_cu.getText().toString();
        mkMoi = id_edt_mk_moi.getText().toString();
        re_MKMoi = id_edt_re_mk_moi.getText().toString();
        switch (v.getId()) {
            case R.id.id_btn_close_dialog_bcsc_ng:
                if (checkValidate_PW()) {
                    if(CommonHelper.isNetworkAvailable(fpActivity)){
                        new GetChangePasswordTask().execute();
                    }else{
                        CommonHelper.showWarning(fpActivity,getString(R.string.nointernet));
                    }
                }
                break;
            case R.id.id_btn_cancel:
                fpActivity.onBackPressed();
                break;
            default:
                break;
        }
    }

    public void setText() {

    }

    private boolean checkValidate_PW() {
        if (CommonHelper.checkValidString(mkCu)) {
            if (CommonHelper.checkValidString(mkMoi)) {
                if (CommonHelper.checkValidString(re_MKMoi)) {
                    if (re_MKMoi.equals(mkMoi)) {
                        return true;
                    } else {
                        CommonHelper.showWarning(fpActivity, fpActivity.getString(R.string.tab_caidat_nhap_lai_re_mk_moi_fail));
                        id_edt_re_mk_moi.requestFocus();
                        return false;
                    }
                } else {
                    CommonHelper.showWarning(fpActivity, fpActivity.getString(R.string.tab_caidat_nhap_lai_re_mk_moi_none));
                    return false;
                }
            } else {
                CommonHelper.showWarning(fpActivity, fpActivity.getString(R.string.tab_caidat_nhap_lai_mk_moi_none));
                id_edt_mk_moi.requestFocus();
                return false;
            }
        } else {
            CommonHelper.showWarning(fpActivity, fpActivity.getString(R.string.tab_caidat_nhap_lai_mk_cu_none));
            id_edt_mk_cu.requestFocus();
            return false;
        }
    }
    //
    public class GetChangePasswordTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        public Void doInBackground(String... params) {
            ResultFromWebservice rs = new ResultFromWebservice();
            GetChangePasswordResponse    getChangePasswordResponse   = rs.getChangePasswordResponse(fpActivity, mkCu, mkMoi, re_MKMoi,GlobalVariable.LOGIN_TOKEN_TYPE+" "+ GlobalVariable.LOGIN_ACCESS_TOKEN,fpActivity);
            IS_CHANGE_PASSWORD  = Boolean.parseBoolean(getChangePasswordResponse.getReturnString());
            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            if(IS_CHANGE_PASSWORD){
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                GlobalVariable.logOut(fpActivity);
                                 fpActivity.startDangNhap4();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(fpActivity);
                builder.setMessage(fpActivity.getString(R.string.tab_caidat_doimatkhau_thanhcong)).setPositiveButton(fpActivity.getString(R.string.tab_caidat_dangxuat_ok), dialogClickListener).show();

            } else {
                CommonHelper.showWarning(fpActivity,fpActivity.getString(R.string.tab_caidat_doimatkhau_khongthanhcong) );
            }
        }
    }
}
