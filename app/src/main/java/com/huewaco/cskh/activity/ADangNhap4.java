package com.huewaco.cskh.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.helper.ShareReferenceConfig;
import com.huewaco.cskh.objects.KhuVucListItemObj;
import com.huewaco.cskh.objects.PhuongXaListItmObj;
import com.huewaco.cskh.objects.QuanHuyenListItemObj;
import com.huewaco.cskh.webservice.objects.GetLoginResponse;
import com.huewaco.cskh.webservice.objects.GetResetPasswordResponse;
import com.huewaco.cskh.webservice.processing.ResultFromWebservice;

import java.util.ArrayList;

public class ADangNhap4 extends AParent {
    private EditText id_edt_user, id_edt_password;
    private Button id_btn_dangnhap, id_btn_dangnhap_sdt_mkh, id_btn_reset_password;
    private TextView id_tv_thongbao;
    String userName;
    String passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
        initComponent();
        addListener();
        //new WriteBackgroungTask().execute();
        //new ReadBackgroungTask().execute();
    }

    public class DangNhapTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        public Void doInBackground(String... params) {

            ResultFromWebservice rs = new ResultFromWebservice();
            //GetLoginResponse getLogin = rs.getLogin("thilien", "000000");
            GetLoginResponse getLogin = rs.getLogin(ADangNhap4.this, userName, passWord,"",ADangNhap4.this);
            if (getLogin.getAccess_token() != null && getLogin.getAccess_token().length() > 0) {
                GlobalVariable.LOGIN_ACCESS_TOKEN = getLogin.getAccess_token();
                GlobalVariable.LOGIN_TOKEN_TYPE = getLogin.getToken_type();
            }
            if(CommonHelper.checkValidString(GlobalVariable.LOGIN_ACCESS_TOKEN) && CommonHelper.checkValidString(GlobalVariable.LOGIN_TOKEN_TYPE)){
                ShareReferenceConfig.instance(getApplicationContext()).setAccessToken(GlobalVariable.LOGIN_ACCESS_TOKEN);
                ShareReferenceConfig.instance(getApplicationContext()).setTokenType(GlobalVariable.LOGIN_TOKEN_TYPE);
                GlobalVariable.EXPIRED = false;
                getUserInfor();
                ShareReferenceConfig.instance(getApplicationContext()).setUserName(userName);
                ShareReferenceConfig.instance(getApplicationContext()).setPassWord(passWord);
            }else{
            }
            return null;
        }

        public void onPostExecute(Void result) {
            disMissLoading();
            if(CommonHelper.checkValidString(GlobalVariable.LOGIN_ACCESS_TOKEN) && CommonHelper.checkValidString(GlobalVariable.LOGIN_TOKEN_TYPE)){
                if(GlobalVariable.KHACH_HANG != null){
                    if(CommonHelper.checkValidString(GlobalVariable.KHACH_HANG.getMa_khang())){

                        ADangNhap4.this.startActivity(new Intent(ADangNhap4.this, AMain.class));
                        if (GlobalVariable.IS_ANIMATION_ACTIVITY) {
                            overridePendingTransition(R.anim.next_1, R.anim.next_2);
                        }
                    }else{
                        CommonHelper.showWarning(ADangNhap4.this,getString(R.string.thong_tin_nguoi_dung_khong_dung));
                    }
                }else{
                    CommonHelper.showWarning(ADangNhap4.this,"khong co data");
                }


            }else{
                CommonHelper.showWarning(ADangNhap4.this,getString(R.string.thong_tin_dang_nhap_khong_dung));
            }

        }
    }

    private String us="",pw="";//for auto set when login
    @Override
    protected void initComponent() {
        //initTopbarView();
        id_edt_user = (EditText)findViewById(R.id.id_edt_user);
        id_edt_password = (EditText)findViewById(R.id.id_edt_password);
        id_btn_dangnhap = (Button) findViewById(R.id.id_btn_dangnhap);
        id_btn_dangnhap_sdt_mkh = (Button) findViewById(R.id.id_btn_dangnhap_sdt_mkh);
        id_btn_reset_password = (Button) findViewById(R.id.id_btn_reset_password);
        id_tv_thongbao = (TextView) findViewById(R.id.id_tv_thongbao);
        id_tv_thongbao.setText(Html.fromHtml(getString(R.string.dangnhap_thongbao)));

        us = ShareReferenceConfig.instance(getApplicationContext()).getUserName();
        pw = ShareReferenceConfig.instance(getApplicationContext()).getPassWord();
        if(CommonHelper.checkValidString(us)){
            id_edt_user.setText(us);
        }
        if(CommonHelper.checkValidString(pw)){
            id_edt_password.setText(pw);
        }
    }

    @Override
    protected void addListener() {
        id_btn_dangnhap.setOnClickListener(this);
        id_btn_dangnhap_sdt_mkh.setOnClickListener(this);
        id_btn_reset_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_btn_dangnhap_sdt_mkh:
                startActivity(new Intent(getApplicationContext(), ADangNhapSDTMaKH.class));
                if (GlobalVariable.IS_ANIMATION_ACTIVITY) {
                    overridePendingTransition(R.anim.next_1, R.anim.next_2);
                }
                break;
            case R.id.id_btn_dangnhap:
                if (isValidInfo()) {
                    dangNhap();
                }
                break;
            case R.id.id_btn_reset_password:
                if (isValidInfoResetPassword()) {
                    resetPassword();
                }
                break;
            default:
                break;
        }
    }
    private void dangNhap(){
        if (CommonHelper.isNetworkAvailable(getApplicationContext())) {
            new DangNhapTask().execute();
        } else {
            CommonHelper.showWarning(ADangNhap4.this, getString(R.string.nointernet));
        }
    }
    private boolean isValidInfo() {
        boolean chk = true;
        userName = id_edt_user.getText().toString().trim();
        passWord = id_edt_password.getText().toString().trim();
        if (!CommonHelper.checkValidString(userName)) {
            chk = false;
            CommonHelper.showWarning(ADangNhap4.this, getString(R.string.chua_nhap_username));
        } else if (!CommonHelper.checkValidString(passWord)) {
            chk = false;
            CommonHelper.showWarning(ADangNhap4.this, getString(R.string.chua_nhap_pass));
        }
        return chk;
    }

    private boolean isValidInfoResetPassword() {
        boolean chk = true;
        userName = id_edt_user.getText().toString().trim();
        if (!CommonHelper.checkValidString(userName)) {
            chk = false;
            CommonHelper.showWarning(ADangNhap4.this, getString(R.string.chua_nhap_username));
        }
        return chk;
    }

    private void resetPassword() {
        if (CommonHelper.isNetworkAvailable(getApplicationContext())) {
            new ResetPasswordTask().execute();
        } else {
            CommonHelper.showWarning(ADangNhap4.this, getString(R.string.nointernet));
        }
    }

    public class ResetPasswordTask extends AsyncTask<String, Void, GetResetPasswordResponse> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        public GetResetPasswordResponse doInBackground(String... params) {
            ResultFromWebservice rs = new ResultFromWebservice();
            GetResetPasswordResponse getResetPassword = rs.getResetPassword(ADangNhap4.this, userName, "",ADangNhap4.this);
            return getResetPassword;
        }

        public void onPostExecute(GetResetPasswordResponse result) {
            disMissLoading();
            CommonHelper.showWarning(ADangNhap4.this, result.getReturnString());
        }
    }

    public class ReadBackgroungTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }
        public Void doInBackground(String... params) {
            //tasks
            ArrayList<KhuVucListItemObj> mArrKhuVuc = CommonHelper.getArrayKhuVucFromAssetJsonFile(getApplicationContext(),"khuvuc.json") ;// CommonHelper.readKhuVucObjAll(getApplicationContext(),GlobalVariable.KHU_VUC_FILE);
            for(int n = 0; n < mArrKhuVuc.size() ; n++){
                KhuVucListItemObj kh = mArrKhuVuc.get(n);

                ArrayList<QuanHuyenListItemObj> mArrQH = kh.getmArrItemQuanHuyen();
                if(mArrQH != null && mArrQH.size()>0){
                    for(int i = 0 ; i < mArrQH.size();i++){
                        QuanHuyenListItemObj qh = mArrQH.get(i);
                        ArrayList<PhuongXaListItmObj> mArrPX = qh.getmArrPhuongXa();
                        if(mArrPX != null && mArrPX.size()>0){
                            for(int j = 0 ; j < mArrPX.size(); j++){
                                PhuongXaListItmObj px = mArrPX.get(j);
                            }
                        }

                    }
                }

            }
            return null;
        }

        public void onPostExecute(Void result) {
            //codes
            disMissLoading();
        }
    }
    public class WriteBackgroungTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Log.d("abc","---------DANG VIET----------");
            showLoading();
        }

        public Void doInBackground(String... params) {
            ArrayList<KhuVucListItemObj> mArrKhuVuc = new ArrayList<>();
            for(int n = 0 ; n < 5; n++){
                KhuVucListItemObj kv = new KhuVucListItemObj();
                kv.setMaKv("Ma KV: "+n);
                kv.setTenKv("ten KV: "+n);
                ArrayList<QuanHuyenListItemObj> mArrQh = new ArrayList<>();
                for(int i = 0 ; i < 5; i++){
                    QuanHuyenListItemObj qh = new QuanHuyenListItemObj();
                    qh.setMaQuan("Ma QH: "+n+i);
                    qh.setTenQuan("Ten Quan huyen: "+n+i);
                    ArrayList<PhuongXaListItmObj> mArrPx = new ArrayList<>();
                    for(int j = 0; j < 5; j++){
                        PhuongXaListItmObj px = new PhuongXaListItmObj();
                        px.setMaPhuong("MP: "+ n+i+j);
                        px.setTenPhuong("Ten PX: "+n+i+j);
                        mArrPx.add(px);
                    }
                    qh.setmArrPhuongXa(mArrPx);
                    mArrQh.add(qh);
                }
                kv.setmArrItemQuanHuyen(mArrQh);
                mArrKhuVuc.add(kv);
            }
            CommonHelper.writeKhuVucObjAll(getApplicationContext(),mArrKhuVuc,GlobalVariable.KHU_VUC_FILE);
            return null;
        }

        public void onPostExecute(Void result) {
            //codes
            new ReadBackgroungTask().execute();
            disMissLoading();
        }
    }
}
