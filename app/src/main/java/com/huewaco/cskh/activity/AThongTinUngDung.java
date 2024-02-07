package com.huewaco.cskh.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.huewaco.cskh.helper.CommonHelper;

public class AThongTinUngDung extends AParent {
    private TextView id_tv_ungdung, id_tv_hdh, id_tv_phienban;
    private Button id_btn_update;
    private String ungdung;
    private String hdh;
    private String phienban;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athong_tin_ung_dung);
        initComponent();
        addListener();
    }

    @Override
    protected void initComponent() {
        initTopbarView();

        id_tv_title.setText(getString(R.string.ttud_title));
        id_btn_left.setBackgroundResource(R.drawable.btn_back);

        id_tv_ungdung = (TextView) findViewById(R.id.id_tv_ungdung);
        id_tv_hdh = (TextView) findViewById(R.id.id_tv_hdh);
        id_tv_phienban = (TextView) findViewById(R.id.id_tv_phienban);
        id_btn_update = (Button) findViewById(R.id.id_btn_update);

        ungdung = "<font color = \"#4d79ff\" >"+getString(R.string.ttud_ungdung)+" "+"</font>"+"<font color = \"#00061a\" >"+getString(R.string.app_name)+"</font>";
        id_tv_ungdung.setText(Html.fromHtml(ungdung));

        hdh = "<font color = \"#4d79ff\" >"+getString(R.string.ttud_hdh)+" "+"</font>"+"<font color = \"#00061a\" >"+getString(R.string.android)+"</font>";
        id_tv_hdh.setText(Html.fromHtml(hdh));

        phienban = "<font color = \"#4d79ff\" >"+getString(R.string.ttud_phienban)+" "+"</font>"+"<font color = \"#00061a\" >"+ CommonHelper.getVersionName(getApplicationContext())+"</font>";
        id_tv_phienban.setText(Html.fromHtml(phienban));
    }

    @Override
    protected void addListener() {
        id_btn_update.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_btn_update:
                if(CommonHelper.isNetworkAvailable(getApplicationContext())){
                    CommonHelper.openGooglePlayApp(AThongTinUngDung.this);
                }else{
                    CommonHelper.showWarning(AThongTinUngDung.this,getString(R.string.nointernet));
                }

                break;

            default:
                break;
        }
    }
}
