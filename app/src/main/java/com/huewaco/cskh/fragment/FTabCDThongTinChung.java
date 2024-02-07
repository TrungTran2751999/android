package com.huewaco.cskh.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
//import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.adapter.ListViewThongTinChungAdapter;
import com.huewaco.cskh.helper.GlobalVariable;

import java.util.ArrayList;


public class FTabCDThongTinChung extends FParent {
    protected String title;
    private ArrayList<String> mArrMaKhang = new ArrayList<String>();
    private Spinner id_spn_khach_hang;
    private TextView id_tv_them_khang;
    private ListViewThongTinChungAdapter adapter;
    private ListView id_lv_list_khach_hang;


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
        View v = inflater.inflate(R.layout.frag_tab_cai_dat_thong_tin_chung, container, false);
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
        id_spn_khach_hang = (Spinner) v.findViewById(R.id.id_spn_khach_hang);
        ArrayAdapter<String> adapterKHang = new ArrayAdapter<String>(fpActivity, android.R.layout.simple_spinner_item, mArrMaKhang);
        id_spn_khach_hang.setAdapter(adapterKHang);
        //
        id_tv_them_khang = (TextView) v.findViewById(R.id.id_tv_them_khang);
        id_lv_list_khach_hang = (ListView) v.findViewById(R.id.id_lv_list_khach_hang);
        if (GlobalVariable.mArrKHang != null) {
            adapter = new ListViewThongTinChungAdapter(fpActivity, GlobalVariable.mArrKHang);
            id_lv_list_khach_hang.setAdapter(adapter);
        }
    }

    private void initData() {
    }

    @Override
    protected void addListener() {
        id_tv_them_khang.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tv_them_khang:
                showDialog(fpActivity.getString(R.string.tab_caidat_title));
                break;
            default:
                break;
        }

    }

    public void setText() {

    }

    private void showDialog(String title) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(fpActivity);
        builder1.setMessage(title);
        builder1.setCancelable(true);
        builder1.setPositiveButton(fpActivity.getString(R.string.tab_caidat_btn_dongy)
                ,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                fpActivity.getString(R.string.tab_caidat_btn_boqua),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    protected void changeText() {
        super.changeText();
        if(adapter != null){
            adapter.refresh(GlobalVariable.mArrKHang);
        }
    }
}
