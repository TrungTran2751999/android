package com.huewaco.cskh.activity;

import android.os.Bundle;
import android.view.View;

public class ADangNhapSDTMaKH extends AParent {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap_sdt_makh);
        initComponent();
        addListener();
    }

    @Override
    protected void initComponent() {
        initTopbarView();

        id_tv_title.setText(getString(R.string.dangnhap_kichhoatungdung));
        id_btn_left.setBackgroundResource(R.drawable.btn_back);
    }

    @Override
    protected void addListener() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            default:
                break;
        }
    }
}
