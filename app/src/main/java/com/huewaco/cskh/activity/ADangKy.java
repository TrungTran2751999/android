package com.huewaco.cskh.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.webservice.objects.GetDangKyResponse;
import com.huewaco.cskh.webservice.objects.GetResetPasswordResponse;
import com.huewaco.cskh.webservice.processing.ResultFromWebservice;


public class ADangKy extends AParent {
    private EditText id_edt_ma_kh, id_edt_username,id_edt_user_password,id_edt_user_confirmpassword;
    private String maKhachHang, username, passWord, confirmPassword;
    private Button id_btn_dangky;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);
        initComponent();
        addListener();
        addListener();
    }

    @Override
    protected void initComponent() {
        id_edt_ma_kh = (EditText)findViewById(R.id.id_edt_ma_kh);
        id_edt_username = (EditText)findViewById(R.id.id_edt_username);
        id_edt_user_password = (EditText)findViewById(R.id.id_edt_user_password);
        id_edt_user_confirmpassword = (EditText)findViewById(R.id.id_edt_user_confirmpassword);
        id_btn_dangky = (Button) findViewById(R.id.id_btn_dangky);
    }

    @Override
    protected void addListener() {
       id_btn_dangky.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_btn_dangky:
                if (isValidInforDangKy())
                    dangKy();
                break;
            default:
                break;
        }
    }

    private boolean isValidInforDangKy() {
        boolean chk = true;
        maKhachHang = id_edt_ma_kh.getText().toString().trim();
        username = id_edt_username.getText().toString().trim();
        passWord = id_edt_user_password.getText().toString().trim();
        confirmPassword = id_edt_user_confirmpassword.getText().toString().trim();

        if (!CommonHelper.checkValidString(maKhachHang)) {
            chk = false;
            CommonHelper.showWarning(ADangKy.this, getString(R.string.chua_nhap_ma_khachhang));
        } else if (!CommonHelper.checkValidString(username)) {
            chk = false;
            CommonHelper.showWarning(ADangKy.this, getString(R.string.chua_nhap_username));
        } else if (!CommonHelper.checkValidString(passWord)) {
            chk = false;
            CommonHelper.showWarning(ADangKy.this, getString(R.string.chua_nhap_pass));
        } else if (!CommonHelper.checkValidString(confirmPassword)) {
            chk = false;
            CommonHelper.showWarning(ADangKy.this, getString(R.string.chua_nhap_confirm_pass));
        }
        return chk;
    }

    private void dangKy(){
        if (CommonHelper.isNetworkAvailable(getApplicationContext())) {
            new DangKyTask().execute();
        } else {
            CommonHelper.showWarning(ADangKy.this, getString(R.string.nointernet));
        }
    }

    public class DangKyTask extends AsyncTask<String, Void, GetDangKyResponse> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        public GetDangKyResponse doInBackground(String... params) {
            ResultFromWebservice rs = new ResultFromWebservice();
            GetDangKyResponse getDangKyResponse = rs.getDangKyResponse(ADangKy.this,maKhachHang,username,passWord,confirmPassword,"",ADangKy.this);
            return getDangKyResponse;
        }

        public void onPostExecute(GetDangKyResponse result) {
            disMissLoading();
            CommonHelper.showWarning(ADangKy.this, result.getReturnString());
        }
    }
}
