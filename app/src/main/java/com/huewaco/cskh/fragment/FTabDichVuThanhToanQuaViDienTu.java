package com.huewaco.cskh.fragment;

import android.os.Bundle;
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

//import android.support.annotation.Nullable;


public class FTabDichVuThanhToanQuaViDienTu extends FParent implements AdapterView.OnItemClickListener {
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
        //---1
        TaskObj momo = new TaskObj();
        momo.setNameTask(getString(R.string.momo));
        momo.setDrawable(fpActivity.getResources().getDrawable(R.drawable.ic_momo));
        momo.setLink(getString(R.string.momolink));
        mArrTasks.add(momo);
        //2
        TaskObj payoo = new TaskObj();
        payoo.setNameTask(getString(R.string.payoo));
        payoo.setDrawable(fpActivity.getResources().getDrawable(R.drawable.ic_payoo));
        payoo.setLink(getString(R.string.payoolink));
        mArrTasks.add(payoo);
        //---8
        TaskObj ecpay = new TaskObj();
        ecpay.setNameTask(getString(R.string.ecpay));
        ecpay.setDrawable(fpActivity.getResources().getDrawable(R.drawable.ic_ecpay));
        ecpay.setLink(getString(R.string.ecpay_link));
        mArrTasks.add(ecpay);
        //---0
        TaskObj vnptpay = new TaskObj();
        vnptpay.setNameTask(getString(R.string.vnptpay));
        vnptpay.setDrawable(fpActivity.getResources().getDrawable(R.drawable.ic_vnpt_pay));
        vnptpay.setLink(getString(R.string.vnptpay_link));
        mArrTasks.add(vnptpay);
        //---3
        TaskObj airpay = new TaskObj();
        airpay.setNameTask(getString(R.string.airpay));
        airpay.setDrawable(fpActivity.getResources().getDrawable(R.drawable.ic_airpay));
        airpay.setLink(getString(R.string.airpay_link));
        mArrTasks.add(airpay);
        //---4
        TaskObj vinid = new TaskObj();
        vinid.setNameTask(getString(R.string.vinid));
        vinid.setDrawable(fpActivity.getResources().getDrawable(R.drawable.ic_vinid));
        vinid.setLink(getString(R.string.vinid_link));
        mArrTasks.add(vinid);
        /*TaskObj bankplus = new TaskObj();
        bankplus.setNameTask(getString(R.string.bankplus));
        bankplus.setDrawable(fpActivity.getResources().getDrawable(R.drawable.ic_bankplus));
        bankplus.setLink(getString(R.string.bankplus_link));
        mArrTasks.add(bankplus);*/
        //---5
        TaskObj viettelpay = new TaskObj();
        viettelpay.setNameTask(getString(R.string.viettelpay));
        viettelpay.setDrawable(fpActivity.getResources().getDrawable(R.drawable.ic_vietel_pay));
        viettelpay.setLink(getString(R.string.viettelpay_link));
        mArrTasks.add(viettelpay);
        //---6
        TaskObj zalopay = new TaskObj();
        zalopay.setNameTask(getString(R.string.zalopay));
        zalopay.setDrawable(fpActivity.getResources().getDrawable(R.drawable.ic_zalopay));
        zalopay.setLink(getString(R.string.zalopay_link));
        mArrTasks.add(zalopay);
        //---7
        TaskObj grab = new TaskObj();
        grab.setNameTask(getString(R.string.grab));
        grab.setDrawable(fpActivity.getResources().getDrawable(R.drawable.ic_grab));
        grab.setLink(getString(R.string.grab_link));
        mArrTasks.add(grab);

        //---9
        TaskObj sendo = new TaskObj();
        sendo.setNameTask(getString(R.string.sendo));
        sendo.setDrawable(fpActivity.getResources().getDrawable(R.drawable.ic_sendo));
        sendo.setLink(getString(R.string.sendo_link));
        mArrTasks.add(sendo);



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
