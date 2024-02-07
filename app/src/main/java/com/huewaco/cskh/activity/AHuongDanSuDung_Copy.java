package com.huewaco.cskh.activity;

import android.os.Bundle;
import android.view.View;

public class AHuongDanSuDung_Copy extends AParent {

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
