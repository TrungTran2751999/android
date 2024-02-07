package com.huewaco.cskh.fragment;

import android.os.Bundle;
//import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.Nullable;

import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.adapter.GridTaskAdapter;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.objects.TaskObj;

import java.util.ArrayList;


public class FTabTraCuu7LienKetNganHang extends FParent implements AdapterView.OnItemClickListener {
    private GridView id_grid_task;
    private ArrayList<TaskObj> mArrTasks = new ArrayList<TaskObj>();
    private GridTaskAdapter adapter;
    protected String title;
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
        View v = inflater.inflate(R.layout.frag_tab_tracuu, container, false);
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
        id_grid_task = (GridView) v.findViewById(R.id.id_grid_task);
        //---0
        TaskObj viettin = new TaskObj();
        viettin.setNameTask(getString(R.string.viettin));
        viettin.setDrawable(fpActivity.getResources().getDrawable(R.drawable.ic_nh_viettinbank));
        viettin.setLink(getString(R.string.viettinib));
        mArrTasks.add(viettin);
        //---1
        TaskObj vietcombank = new TaskObj();
        vietcombank.setNameTask(getString(R.string.vietcombank));
        vietcombank.setDrawable(fpActivity.getResources().getDrawable(R.drawable.ic_nh_vietcombank));
        vietcombank.setLink(getString(R.string.vietcombankib));
        mArrTasks.add(vietcombank);
        //---2
        TaskObj agribank = new TaskObj();
        agribank.setNameTask(getString(R.string.agribank));
        agribank.setDrawable(fpActivity.getResources().getDrawable(R.drawable.ic_nh_agribank));
        agribank.setLink(getString(R.string.agribankib));
        mArrTasks.add(agribank);
        //---3
        TaskObj eximbank = new TaskObj();
        eximbank.setNameTask(getString(R.string.eximbank));
        eximbank.setDrawable(fpActivity.getResources().getDrawable(R.drawable.ic_nh_eximbankp));
        eximbank.setLink(getString(R.string.eximbankib));
        mArrTasks.add(eximbank);
        //---4
        TaskObj quandoi = new TaskObj();
        quandoi.setNameTask(getString(R.string.quandoi));
        quandoi.setDrawable(fpActivity.getResources().getDrawable(R.drawable.ic_nh_quandoi));
        quandoi.setLink(getString(R.string.quandoiib));
        mArrTasks.add(quandoi);
        //---5
        TaskObj sacombank = new TaskObj();
        sacombank.setNameTask(getString(R.string.sacombank));
        sacombank.setDrawable(fpActivity.getResources().getDrawable(R.drawable.ic_nh_sacombank));
        sacombank.setLink(getString(R.string.sacombankib));
        mArrTasks.add(sacombank);
        //---6
        TaskObj anbinh = new TaskObj();
        anbinh.setNameTask(getString(R.string.anbinh));
        anbinh.setDrawable(fpActivity.getResources().getDrawable(R.drawable.ic_nh_anbinh));
        anbinh.setLink(getString(R.string.anbinhib));
        mArrTasks.add(anbinh);
        //---7
        TaskObj vpbank = new TaskObj();
        vpbank.setNameTask(getString(R.string.vpbank));
        vpbank.setDrawable(fpActivity.getResources().getDrawable(R.drawable.ic_nh_vpbank));
        vpbank.setLink(getString(R.string.vpbankib));
        mArrTasks.add(vpbank);

        //---8
        TaskObj bidv = new TaskObj();
        bidv.setNameTask(getString(R.string.bidv));
        bidv.setDrawable(fpActivity.getResources().getDrawable(R.drawable.ic_bidv));
        bidv.setLink(getString(R.string.bidv_link));
        mArrTasks.add(bidv);

        TaskObj donga = new TaskObj();
        donga.setNameTask(getString(R.string.donga));
        donga.setDrawable(fpActivity.getResources().getDrawable(R.drawable.ic_donga));
        donga.setLink(getString(R.string.donga_link));
        mArrTasks.add(donga);

        adapter = new GridTaskAdapter(fpActivity, mArrTasks);
        id_grid_task.setAdapter(adapter);
    }

    @Override
    protected void addListener() {
        id_grid_task.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            default:
                break;
        }

    }

    public void setText() {
        id_tv_title.setText(title);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String link = mArrTasks.get(position).getLink();
        if(CommonHelper.checkValidString(link)){
            CommonHelper.openFile(fpActivity,link);
        }
    }
}
