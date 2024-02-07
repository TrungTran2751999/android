package com.huewaco.cskh.fragment;

import android.os.Bundle;
//import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.huewaco.cskh.activity.R;


public class FTabDichVu3HoiTraLoiDetail extends FParent {
    protected String title;
    protected String TieuDe= "";
    protected String NoiDung= "";
    protected String TraLoi= "";
    private TextView id_tv_noidung, id_tv_traloi, id_tv_tieude;

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
        View v = inflater.inflate(R.layout.frag_tab_dichvu3_hoitraloi_hoi_dapan, container, false);
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
        id_tv_noidung = (TextView)v.findViewById(R.id.id_tv_noidung);
        id_tv_traloi = (TextView)v.findViewById(R.id.id_tv_traloi);
        id_tv_tieude = (TextView)v.findViewById(R.id.id_tv_tieude);
    }

    private void initData() {

    }

    @Override
    protected void addListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            default:
                break;
        }

    }

    public void setText() {
        id_tv_noidung.setText(Html.fromHtml(NoiDung));
        id_tv_tieude.setText(Html.fromHtml(TieuDe));
        id_tv_traloi.setText(Html.fromHtml(TraLoi));
    }
}
