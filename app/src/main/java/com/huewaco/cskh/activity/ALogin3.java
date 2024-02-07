package com.huewaco.cskh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.huewaco.cskh.adapter.GridTaskAdapter;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.objects.TaskObj;

import java.util.ArrayList;

public class ALogin3 extends AParent implements AdapterView.OnItemClickListener {
    private GridView id_grid_task;
    private ArrayList<TaskObj> mArrTasks = new ArrayList<TaskObj>();
    private GridTaskAdapter adapter;
    private Button id_btn_login, id_btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alogin);
        initComponent();
        addListener();
    }

    @Override
    protected void initComponent() {
        id_btn_login = (Button) findViewById(R.id.id_btn_login);
        id_grid_task = (GridView) findViewById(R.id.id_grid_task);
        id_btn_register = (Button) findViewById(R.id.id_btn_register);

        //---
        TaskObj dangKyCapDien = new TaskObj();
        dangKyCapDien.setNameTask(getString(R.string.login_dangky_capdien));
        dangKyCapDien.setDrawable(getApplicationContext().getResources().getDrawable(R.drawable.dangkycapnuocmoi));
        mArrTasks.add(dangKyCapDien);
        //---
        TaskObj goiTongDai = new TaskObj();
        goiTongDai.setNameTask(getString(R.string.login_goi_tong_dai) + "\n" + getString(R.string.hotline));
        goiTongDai.setDrawable(getApplicationContext().getResources().getDrawable(R.drawable.goitongdai));
        mArrTasks.add(goiTongDai);
        //---
        TaskObj webCSKH = new TaskObj();
        webCSKH.setNameTask(getString(R.string.login_web_cskh));
        webCSKH.setDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_web_cskh));
        mArrTasks.add(webCSKH);
        //---
        TaskObj thongTinUngDung = new TaskObj();
        thongTinUngDung.setNameTask(getString(R.string.login_thongtin_ungdung));
        thongTinUngDung.setDrawable(getApplicationContext().getResources().getDrawable(R.drawable.thongtinungdung));
        mArrTasks.add(thongTinUngDung);

        //---
        TaskObj baocaosuco = new TaskObj();
        baocaosuco.setNameTask(getString(R.string.tab_dichvu_bcaosuco));
        baocaosuco.setDrawable(getApplicationContext().getResources().getDrawable(R.drawable.thongtinsucomatnuoc));
        mArrTasks.add(baocaosuco);
        //---
        adapter = new GridTaskAdapter(getApplicationContext(), mArrTasks);
        id_grid_task.setAdapter(adapter);
    }

    @Override
    protected void addListener() {
        id_grid_task.setOnItemClickListener(this);
        id_btn_login.setOnClickListener(this);
        id_btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_btn_login:
                startActivity(new Intent(getApplicationContext(), ADangNhap4.class));
                if (GlobalVariable.IS_ANIMATION_ACTIVITY) {
                    overridePendingTransition(R.anim.next_1, R.anim.next_2);
                }
                break;
            case R.id.id_btn_register:
                startActivity(new Intent(getApplicationContext(), ADangKy.class));
                if (GlobalVariable.IS_ANIMATION_ACTIVITY) {
                    overridePendingTransition(R.anim.next_1, R.anim.next_2);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (position == 0) {
            startActivity(new Intent(getApplicationContext(), ADangKyCapNuoc.class));
            if (GlobalVariable.IS_ANIMATION_ACTIVITY) {
                overridePendingTransition(R.anim.next_1, R.anim.next_2);
            }
        } else if (position == 1) {
            if (CommonHelper.isCallPhonePermissionGranted(ALogin3.this)) {
                CommonHelper.callPhone(ALogin3.this, getString(R.string.hotline));
            } else {

            }
        } else if (position == 2) {
            if (CommonHelper.isNetworkAvailable(getApplicationContext())) {
                CommonHelper.openWebCSKH(ALogin3.this, GlobalVariable.LINK_WEB_CSKH);
            } else {
                CommonHelper.showWarning(ALogin3.this, getString(R.string.nointernet));
            }
        } else if (position == 3) {
            startActivity(new Intent(getApplicationContext(), AThongTinUngDung.class));
            if (GlobalVariable.IS_ANIMATION_ACTIVITY) {
                overridePendingTransition(R.anim.next_1, R.anim.next_2);
            }

        } else if (position == 4) {
            startActivity(new Intent(this, ABaoCaoSuCo.class));
        }
    }
}
