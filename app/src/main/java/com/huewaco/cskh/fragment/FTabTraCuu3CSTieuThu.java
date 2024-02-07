package com.huewaco.cskh.fragment;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.adapter.ListViewCSTieuThuAdapter;
import com.huewaco.cskh.adapter.ListViewCustomerGroupListItemAdapter;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.objects.CSTieuThuListItemObj;
import com.huewaco.cskh.objects.KhachHangObj;
import com.huewaco.cskh.webservice.objects.GetTraCuu3ChiSoNuocTieuThuResponse;
import com.huewaco.cskh.webservice.processing.ResultFromWebservice;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.Collections;


public class FTabTraCuu3CSTieuThu extends FParent {
    private ListView id_lv_list_cs_tieu_thu;
    private ListViewCSTieuThuAdapter adapter;
    private ArrayList<CSTieuThuListItemObj> mArrCSTieuThuList = new ArrayList<>();
    protected String title;
    private Spinner id_spn_customergroup;
    LinearLayout id_layoutcmb;
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
        View v = inflater.inflate(R.layout.frag_tab_tracuu3_chi_so_tieu_thu, container, false);
        initCommonView(v, this);
        initData();
        initComponent(v);
        addListener();
        setText();
        if (CommonHelper.isNetworkAvailable(fpActivity)) {

            new GetTCChiSoNuocTieuThuTask().execute();
        } else {
            CommonHelper.showWarning(fpActivity, getString(R.string.nointernet));
        }
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
        layout = (LinearLayout) v.findViewById(R.id.chart);
        id_tv_title.setText(title);
        id_lv_list_cs_tieu_thu = (ListView) v.findViewById(R.id.id_lv_list_cs_tieu_thu);
        adapter = new ListViewCSTieuThuAdapter(fpActivity, mArrCSTieuThuList);
        id_lv_list_cs_tieu_thu.setAdapter(adapter);

        id_layoutcmb = (LinearLayout)  v.findViewById(R.id.id_layoutcmb);
        id_spn_customergroup = (Spinner) v.findViewById(R.id.id_spn_customergroup);
        ListViewCustomerGroupListItemAdapter adapterCustomerGroup = new ListViewCustomerGroupListItemAdapter(fpActivity,false);
        id_spn_customergroup.setAdapter(adapterCustomerGroup);
        id_spn_customergroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the value selected by the user
                // e.g. to store it as a field or immediately call a method
                KhachHangObj user = (KhachHangObj) parent.getSelectedItem();
                GlobalVariable.KHACH_HANG_CURRENT = user;
                new GetTCChiSoNuocTieuThuTask().execute();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if(GlobalVariable.mArrKHang.size() >0){
            id_spn_customergroup.setSelection(adapterCustomerGroup.getIndexByIdkh(GlobalVariable.KHACH_HANG_CURRENT.getIdKh()),false );
            adapterCustomerGroup.refresh();
        }else{
            id_layoutcmb.setVisibility(View.GONE);
        }
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

    }

    //
    public class GetTCChiSoNuocTieuThuTask extends AsyncTask<String, Void, Void> {
        boolean isError = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        @Override
        public Void doInBackground(String... params) {

            ResultFromWebservice rs = new ResultFromWebservice();
            GetTraCuu3ChiSoNuocTieuThuResponse getTraCuu3ChiSoNuocTieuThuResponse = rs.getTCChiSoNuocTieuThuResponse(fpActivity, GlobalVariable.LOGIN_TOKEN_TYPE, GlobalVariable.LOGIN_ACCESS_TOKEN,fpActivity);
            if (getTraCuu3ChiSoNuocTieuThuResponse != null && getTraCuu3ChiSoNuocTieuThuResponse.getmArrItem() != null) {

                mArrCSTieuThuList.clear();
                mArrCSTieuThuList.addAll(getTraCuu3ChiSoNuocTieuThuResponse.getmArrItem());
            }
            isError = getTraCuu3ChiSoNuocTieuThuResponse.hasError();
            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            disMissLoading();
            adapter.refresh(mArrCSTieuThuList);
            startGrap();

            if (isError) {
                Log.d("abc", "" + "---------------: " + isError);
                fpActivity.startDangNhap4();
            }
        }
    }

    //for chart
    private XYMultipleSeriesDataset mDataset = null;//getDemoDataset();
    private XYMultipleSeriesRenderer mRenderer = getDemoRenderer();
    private GraphicalView mChartView;
    LinearLayout layout;

    private void startGrap() {
        mDataset = getDemoDataset();
        //start chart
        setRendererStyling();
        layout.removeAllViews();
        mChartView = ChartFactory.getLineChartView(fpActivity, mDataset,
                mRenderer);
        mRenderer.setSelectableBuffer(100);
        layout.addView(mChartView, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));

        //end chart
    }
    private double findMaxValue(ArrayList<CSTieuThuListItemObj> mArrCSTieuThuList){
        double max = 0;
        for(CSTieuThuListItemObj cs : mArrCSTieuThuList){
            double newMax = 0;
            double newMaxM3TinhTien = Double.parseDouble(cs.getM3TinhTien());
            double newMaxM3TieuThuCungThangNamTruoc = Double.parseDouble(cs.getM3TieuThuCungThangNamTruoc());
            newMax = (newMaxM3TinhTien>newMaxM3TieuThuCungThangNamTruoc)?newMaxM3TinhTien:newMaxM3TieuThuCungThangNamTruoc;
            max = (newMax>max)?newMax:max;
        }
        return max;
    }
    private double findMinValue(ArrayList<CSTieuThuListItemObj> mArrCSTieuThuList){
        double min = 0;
        for(CSTieuThuListItemObj cs : mArrCSTieuThuList){
            double newMin = 0;
            double newMinM3TinhTien = Double.parseDouble(cs.getM3TinhTien());
            double newMinM3TieuThuCungThangNamTruoc = Double.parseDouble(cs.getM3TieuThuCungThangNamTruoc());
            newMin = (newMinM3TinhTien<newMinM3TieuThuCungThangNamTruoc)?newMinM3TinhTien:newMinM3TieuThuCungThangNamTruoc;
            if(min==0){
                min = newMin;
            }//avoid 0 avlue
            min = (newMin<min)?newMin:min;
        }
        return min;
    }
    private void setRendererStyling() {
        int count = mArrCSTieuThuList.size();
        /*start padding chart inside axis XY*/
        double maxX = count - 0.9F;
        mRenderer.setXAxisMax(maxX);
        mRenderer.setXAxisMin(-0.09f);//cach 1 khoan so voi toa do Y, tranh ve tu toa do X = 0
        double maxY = findMaxValue(mArrCSTieuThuList);
        double minY = findMinValue(mArrCSTieuThuList);
        int unit = (int)maxY / (count>=1?count:1);
        mRenderer.setYAxisMax(maxY+unit/2);
        minY = minY-(count>=1?unit/count:0);
        mRenderer.setYAxisMin(minY);
        /*end padding cahrt inside axis XY*/

        for(int i = 0 ;i <count;i++){
            mRenderer.addXTextLabel(i,mArrCSTieuThuListNew.get(i).getThang()+"/"+mArrCSTieuThuListNew.get(i).getNam());

        }mRenderer.setXTitle("Kỳ");
        mRenderer.setXLabelsAlign(Paint.Align.CENTER);
        mRenderer.setXLabels(0);

        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.WHITE);
        mRenderer.setMarginsColor(Color.WHITE);

        mRenderer.setShowGrid(true);
        mRenderer.setGridColor(Color.GRAY);
        mRenderer.setShowCustomTextGrid(true);

        mRenderer.setAxisTitleTextSize(25);
        mRenderer.setChartTitleTextSize(25);
        mRenderer.setLabelsTextSize(25);
        mRenderer.setXLabelsColor(Color.BLACK);
        mRenderer.setYLabelsColor(0,Color.BLACK);
        mRenderer.setLabelsColor(Color.BLACK);
        mRenderer.setYLabelsAlign(Paint.Align.RIGHT);

        mRenderer.setLegendTextSize(25);
        mRenderer.setMargins(new int[]{30, 50, 40, 40});//t-l-b-r
        //mRenderer.setZoomButtonsVisible(true);
        //mRenderer.setYAxisAlign(Paint.Align.LEFT,0);
        //mRenderer.setPanEnabled(false,false);
        //mRenderer.setXAxisMin(-0.05f);
        //mRenderer.setXAxisMax(0.9f);
        //mRenderer.setYAxisMin(-0.02f);
        //mRenderer.setExternalZoomEnabled(false);
        //mRenderer.setScale(0.8f);
        mRenderer.setAxesColor(Color.BLACK);

        mRenderer.setPointSize(13);
    }
    ArrayList<CSTieuThuListItemObj> mArrCSTieuThuListNew = new ArrayList<>();
    private XYMultipleSeriesDataset getDemoDataset() {

        mArrCSTieuThuListNew.addAll(mArrCSTieuThuList);
        Collections.reverse(mArrCSTieuThuListNew);

        int count = mArrCSTieuThuListNew.size();
        double[] seriesFirstY = new double[count];
        double[] seriesSecondY = new double[count];
        for (int x = 0; x < count; x++) {
            double nn = Double.parseDouble(mArrCSTieuThuListNew.get(x).getM3TinhTien());
            double nt = Double.parseDouble(mArrCSTieuThuListNew.get(x).getM3TieuThuCungThangNamTruoc());
            seriesFirstY[x] = nn;
            seriesSecondY[x] = nt;
        }
/*        int count = mArrCSTieuThuList.size();CommonHelper.showToast(fpActivity,count+"");
        double[] seriesFirstY = {20,-20,67,180,-45,24,99,-34,-8};
        double[] seriesSecondY = {10,80,-40,-20,135,24,199,-34,80};*/

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        XYSeries firstSeries = new XYSeries("Năm nay");
        for (int i = 0; i < count; i++) {
            firstSeries.add(i, seriesFirstY[i]);
            //firstSeries.addAnnotation("2015",i,seriesFirstY[i]);
        }
        dataset.addSeries(firstSeries);

        XYSeries secondSeries = new XYSeries("Năm trước");
        for (int j = 0; j < count; j++) {
            secondSeries.add(j, seriesSecondY[j]);
        }
        dataset.addSeries(secondSeries);
        return dataset;
    }

    private XYMultipleSeriesRenderer getDemoRenderer() {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setMargins(new int[]{20, 30, 15, 10});
        XYSeriesRenderer r = new XYSeriesRenderer();
        r.setColor(Color.BLUE);
        r.setPointStyle(PointStyle.CIRCLE);
        r.setLineWidth(3);
        //r.setFillBelowLine(true);
        //r.setFillBelowLineColor(Color.WHITE);
        r.setFillPoints(true);
        renderer.addSeriesRenderer(r);
        r = new XYSeriesRenderer();
        r.setPointStyle(PointStyle.CIRCLE);
        r.setLineWidth(3);
        r.setColor(Color.RED);
        r.setFillPoints(true);
        renderer.addSeriesRenderer(r);
        renderer.setAxesColor(Color.DKGRAY);
        renderer.setLabelsColor(Color.LTGRAY);
        return renderer;
    }
}
