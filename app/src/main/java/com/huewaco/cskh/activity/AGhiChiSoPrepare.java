package com.huewaco.cskh.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.customview.edittext.CustomEditText;
import com.huewaco.cskh.adapter.ListViewBaoCaoSuCoAdapter;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.interfacex.ITFTakePhotoGallery;
import com.huewaco.cskh.interfacex.ITF_DialogButtonProcessing;
import com.huewaco.cskh.objects.BaoCaoSuCoObj;
import com.huewaco.cskh.objects.ThongTinKHObj;
import com.huewaco.cskh.ui.CustomListviewGCS;
import com.huewaco.cskh.webservice.objects.GetDichVuGCS_KH_ThuocDienGCS_Response;
import com.huewaco.cskh.webservice.objects.GetDichVuGCS_KH_TimeServer_Response;
import com.huewaco.cskh.webservice.objects.GetDichVuGCS_Send_Data_Response;
import com.huewaco.cskh.webservice.objects.GetDichVuGCS_TTKH_Response;
import com.huewaco.cskh.webservice.objects.GetDichVuGCS_TinhTien_Response;
import com.huewaco.cskh.webservice.processing.ResultFromWebservice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AGhiChiSoPrepare extends AParent implements ITFTakePhotoGallery ,ITF_DialogButtonProcessing{
    private TextView id_tv_ngay_hien_tai,id_tv_name_addr_kh,id_tv_kighi,id_tv_trangthai,id_tv_mdsd,
            id_tv_kytruoc,id_tv_matt,id_tv_chiso_kytruoc,id_tv_chiso_thanghientai,
            id_tv_tongtien_number,id_tv_tien_chu,id_tv_so_didong,id_tv_chiso_dau,id_tv_gcs_kltthu,id_tv_notification_result;

    private ImageView id_img_gcs_img,id_img_ic_up_down,id_img_ic_up_down_lv;
    private Button id_btn_get_img,id_btn_send_gcs_data;
    private CustomEditText id_edt_gcs_hientai;

    private ViewGroup id_ly_ttkh_hide_show,id_ly_info_add,id_ly_nhap_cs_edt,id_ly_sau_gcs,id_ly_group_imgs;

    private CustomListviewGCS id_lv_list_suco_imgs;
    private ArrayList<BaoCaoSuCoObj> mArrSuco = new ArrayList<>();
    private ListViewBaoCaoSuCoAdapter listViewBaoCaoSuCoAdapter;

    private ITFTakePhotoGallery iTFTakePhotoGallery= this;
    private String chiSoNhapTay;
    private String ChiSo_AI_Got_Incorrect = "";
    public static String CHISOCUOI = "";
    public static BaoCaoSuCoObj BCSC;
    private boolean IS_NHAP_TAY = false;
    private ThongTinKHObj thongTinKHObj = null;
    private boolean thuocGCS = false;

    public ITF_DialogButtonProcessing itfDialogButtonProcessing = this;
    int Request_AGhiChiSoPrepare_2_AGhiChiSoAPI = 1;

    private int WHAT_TYPE_AI_RETURNED = -1;//default = -1, AI read correct = 1, AI read incorrect = 2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gcs_prepare2);
        initTopbarView();
        initCommonView(this);
        initComponent();
        addListener();
        CHISOCUOI = "";
    }

    @Override
    protected void initComponent() {
        BCSC = null;
        //CommonHelper.showWarning(AGhiChiSoPrepare.this, getString(R.string.gcs_pre_guide));
        id_tv_notification_result = (TextView)findViewById(R.id.id_tv_notification_result);
        id_tv_title.setText(getString(R.string.tab_dichvu_gcs));
        id_btn_left.setBackgroundResource(R.drawable.btn_back);

        id_tv_ngay_hien_tai = (TextView)findViewById(R.id.id_tv_ngay_hien_tai);
        id_tv_name_addr_kh = (TextView)findViewById(R.id.id_tv_name_addr_kh);
        id_tv_kighi = (TextView)findViewById(R.id.id_tv_kighi);
        id_tv_trangthai = (TextView)findViewById(R.id.id_tv_trangthai);
        id_tv_mdsd = (TextView)findViewById(R.id.id_tv_mdsd);
        id_tv_kytruoc = (TextView)findViewById(R.id.id_tv_kytruoc);
        id_tv_matt = (TextView)findViewById(R.id.id_tv_matt);
        id_tv_chiso_kytruoc = (TextView)findViewById(R.id.id_tv_chiso_kytruoc);
        id_tv_chiso_thanghientai = (TextView)findViewById(R.id.id_tv_chiso_thanghientai);
        id_tv_tongtien_number = (TextView)findViewById(R.id.id_tv_tongtien_number);
        id_tv_tien_chu = (TextView)findViewById(R.id.id_tv_tien_chu);
        id_tv_so_didong = (TextView)findViewById(R.id.id_tv_so_didong);

        id_img_gcs_img = (ImageView)findViewById(R.id.id_img_gcs_img);
        id_btn_get_img = (Button)findViewById(R.id.id_btn_get_img);
        id_btn_send_gcs_data = (Button)findViewById(R.id.id_btn_send_gcs_data);
        id_edt_gcs_hientai = (CustomEditText)findViewById(R.id.id_edt_gcs_hientai);
        id_tv_chiso_dau = (TextView)findViewById(R.id.id_tv_chiso_dau);
        id_tv_gcs_kltthu = (TextView)findViewById(R.id.id_tv_gcs_kltthu);

        id_img_ic_up_down = (ImageView)findViewById(R.id.id_img_ic_up_down);
        id_img_ic_up_down_lv = (ImageView)findViewById(R.id.id_img_ic_up_down_lv);
        id_ly_ttkh_hide_show = (ViewGroup)findViewById(R.id.id_ly_ttkh_hide_show);
        id_ly_info_add = (ViewGroup)findViewById(R.id.id_ly_info_add);

        id_ly_nhap_cs_edt = (ViewGroup)findViewById(R.id.id_ly_nhap_cs_edt);
        id_ly_sau_gcs = (ViewGroup)findViewById(R.id.id_ly_sau_gcs);

        id_ly_group_imgs = (ViewGroup)findViewById(R.id.id_ly_group_imgs);




        for(int i = 0 ; i< 1; i++){
            BaoCaoSuCoObj sc = new BaoCaoSuCoObj();
            sc.setLink(""+i);
            if(i==0){
                sc.setProcessAI(true);
            }else{
                sc.setProcessAI(false);
            }
            mArrSuco.add(sc);
        }

        id_lv_list_suco_imgs = (CustomListviewGCS)findViewById(R.id.id_lv_list_suco_imgs);
        listViewBaoCaoSuCoAdapter = new ListViewBaoCaoSuCoAdapter(AGhiChiSoPrepare.this, mArrSuco, iTFTakePhotoGallery);
//        id_lv_list_suco_imgs.setAdapter(listViewBaoCaoSuCoAdapter);

//        id_ly_nhap_cs_edt.setVisibility(View.GONE);
        id_ly_sau_gcs.setVisibility(View.GONE);
        id_ly_info_add.setVisibility(View.GONE);

        if(id_ly_info_add.getVisibility() == View.VISIBLE){
            id_img_ic_up_down.setBackgroundResource(R.drawable.up_icon);

        }else{
            id_img_ic_up_down.setBackgroundResource(R.drawable.down_icon);
        }
        if(id_lv_list_suco_imgs.getVisibility() == View.VISIBLE){
            id_img_ic_up_down_lv.setBackgroundResource(R.drawable.up_icon_grey);

        }else{
            id_img_ic_up_down_lv.setBackgroundResource(R.drawable.down_icon_grey);
        }
        initData();
        showTakeMorePhoto();
    }


    @Override
    protected void addListener() {
        id_ly_group_imgs.setOnClickListener(this);
        id_btn_get_img.setOnClickListener(this);
        id_ly_ttkh_hide_show.setOnClickListener(this);
        id_btn_send_gcs_data.setOnClickListener(this);

        id_edt_gcs_hientai.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String sonhap = id_edt_gcs_hientai.getText().toString().trim();
                    if(isValidInputNumber()){
                        if(CommonHelper.checkValidString(sonhap)){
                            chiSoNhapTay = sonhap;
                            thongTinKHObj.setChiSoCuoi_NhapTay(chiSoNhapTay);
                            tinhtien();
                            return true;
                        }
                    }else{

                        //String currentStr = id_tv_notification_result.getText().toString();
                        //currentStr+= " | Lỗi: "+ getString(R.string.gcs_pre_input_invalid);
                        String currentStr = "Lỗi: "+ getString(R.string.gcs_pre_input_invalid);
                        id_tv_notification_result.setText(Html.fromHtml(currentStr));
                        CommonHelper.showToast(getApplicationContext(),getString(R.string.gcs_pre_input_invalid));
                    }


                }
                return false;
            }
        });
        id_edt_gcs_hientai.setOnClickListener(this);
    }

    private void setVisibleLV(int type){
        id_lv_list_suco_imgs.setVisibility(type);
        if(id_lv_list_suco_imgs.getVisibility() == View.VISIBLE){
            id_img_ic_up_down_lv.setBackgroundResource(R.drawable.up_icon_grey);

        }else{
            id_img_ic_up_down_lv.setBackgroundResource(R.drawable.down_icon_grey);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_ly_group_imgs:
                if(id_lv_list_suco_imgs.getVisibility() == View.VISIBLE){
                    id_lv_list_suco_imgs.setVisibility(View.GONE);
                    id_img_ic_up_down_lv.setBackgroundResource(R.drawable.down_icon_grey);

                }else{
                    id_lv_list_suco_imgs.setVisibility(View.VISIBLE);
                    id_img_ic_up_down_lv.setBackgroundResource(R.drawable.up_icon_grey);
                }
                break;
            case R.id.id_btn_send_gcs_data:
//                sendData();
                tinhtien();
                break;
            case R.id.id_ly_ttkh_hide_show:
                if(id_ly_info_add.getVisibility() == View.VISIBLE){
                    id_ly_info_add.setVisibility(View.GONE);
                    id_img_ic_up_down.setBackgroundResource(R.drawable.down_icon);

                }else{
                    id_ly_info_add.setVisibility(View.VISIBLE);
                    id_img_ic_up_down.setBackgroundResource(R.drawable.up_icon);
                }
                break;
            case R.id.id_edt_gcs_hientai:
                if(IS_NHAP_TAY){
                    id_edt_gcs_hientai.setText("");
                }
                id_edt_gcs_hientai.setEnabled(true);
                break;
            case R.id.id_btn_get_img:
            case R.id.id_btn_right:
                id_btn_send_gcs_data.performClick();
                break;
            default:
                break;
        }
    }

    private void sendData(){
        if(isValid()){
            if(thuocGCS){
                new SendDataTask().execute();
            }else{
                //CommonHelper.showDAG(this,getString(R.string.app_name),getString(R.string.gcs_pre_cannot_send_kh_to_server),GlobalVariable.BUTTON_OK_SHOW,itfDialogButtonProcessing,GlobalVariable.SEND_DATA_GCS,GlobalVariable.SEND_DATA_GCS);
                CommonHelper.showDAG(this,getString(R.string.app_name),getString(R.string.gcs_pre_cannot_send_kh_to_server),GlobalVariable.BUTTON_OK_NOT_SHOW,itfDialogButtonProcessing,GlobalVariable.SEND_DATA_GCS,GlobalVariable.SEND_DATA_GCS);
            }

        }
    }

    private boolean isValid(){
        boolean chk = true;

        if(!CommonHelper.checkValidString(thongTinKHObj.getNgayNhap())){
            chk = false;
            CommonHelper.showWarning(this,getString(R.string.gcs_pre_valid_time));
        }
//        if(chk && !isHasAtLeast1Photo()){
//            chk = false;
//            CommonHelper.showWarning(this,getString(R.string.dichvu_missing_img_gcs));
//        }
        if(chk && !isValidInputNumberAtSend()){
            chk = false;
            CommonHelper.showWarning(this,getString(R.string.gcs_pre_input_invalid));
        }

        return chk;
    }
    private void initData(){
        if(CommonHelper.isNetworkAvailable(this)){
            new GetDataFromServerTask().execute();
        }else{
            CommonHelper.showWarning(this,getString(R.string.nointernet));
        }
    }

    private class GetDataFromServerTask extends AsyncTask<Void, Void, Void> {

        private String timeServerNow = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            timeServerNow = "";
            thongTinKHObj = null;
            showLoading();
        }

        @Override
        public Void doInBackground(Void... params) {
            ResultFromWebservice rs = new ResultFromWebservice();
            GetDichVuGCS_KH_TimeServer_Response getDichVuGCS_KH_TimeServer_Response = rs.getDichVuGCS_KH_TimeServer_Response(AGhiChiSoPrepare.this, GlobalVariable.LOGIN_TOKEN_TYPE , GlobalVariable.LOGIN_ACCESS_TOKEN);
            timeServerNow =  getDichVuGCS_KH_TimeServer_Response.getTimesv();
            GetDichVuGCS_TTKH_Response getDichVuGCS_TTKH_Response = rs.getDichVuGCS_TTKH_Response(AGhiChiSoPrepare.this, GlobalVariable.LOGIN_TOKEN_TYPE , GlobalVariable.LOGIN_ACCESS_TOKEN);
            thongTinKHObj = getDichVuGCS_TTKH_Response.getTtKHOBj();
            GetDichVuGCS_KH_ThuocDienGCS_Response getDichVuGCS_KH_ThuocDienGCS_Response = rs.getDichVuGCS_KH_ThuocDienGCS_Response(AGhiChiSoPrepare.this, GlobalVariable.LOGIN_TOKEN_TYPE , GlobalVariable.LOGIN_ACCESS_TOKEN);
            thuocGCS = getDichVuGCS_KH_ThuocDienGCS_Response.isOK();
            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            disMissLoading();
            if(CommonHelper.checkValidString(timeServerNow)){
                id_tv_ngay_hien_tai.setText(CommonHelper.getDateString2FromStringDate1(timeServerNow,"dd/MM/yyyy").toString());
            }
            if(thongTinKHObj != null){
                thongTinKHObj.setNgayNhap(CommonHelper.getDateString2FromStringDate1(timeServerNow,"yyyy-MM-dd HH:mm:ss.SSSZ").toString());
                id_tv_name_addr_kh.setText(thongTinKHObj.getTenKh() + " - "+thongTinKHObj.getDiaChi());
                id_tv_kighi.setText(getString(R.string.gcs_pre_kyghi_tieude) + " " + thongTinKHObj.getKyGhi());
                id_tv_trangthai.setText(getString(R.string.gcs_pre_tinhtrangdongho_tieude) + " " + thongTinKHObj.getMaTthaiGhi());
                if(thongTinKHObj.getTenTthaiGhi().equalsIgnoreCase(GlobalVariable.TRANGTHAI_GCS_BT)){
                    //id_tv_trangthai.setBackgroundColor(getResources().getColor(R.color.green));
                    id_tv_trangthai.setTextColor(getResources().getColor(R.color.black));
                }else{
                    //id_tv_trangthai.setBackgroundColor(getResources().getColor(R.color.white2));
                    id_tv_trangthai.setTextColor(getResources().getColor(R.color.black));
                }
                id_tv_mdsd.setText(getString(R.string.gcs_pre_mdsd)+" "+thongTinKHObj.getTenMdsd()+getString(R.string.gcs_pre_nk)+" "+thongTinKHObj.getSoNk());
                id_tv_chiso_dau.setText(CommonHelper.getStringWithSeparatorThousand(thongTinKHObj.getChiSoDau())+"");

                id_tv_so_didong.setText(getString(R.string.gcs_pre_didong)+" "+thongTinKHObj.getDienThoai());
            }


        }
    }
    private void whatType(){
        if(WHAT_TYPE_AI_RETURNED == 1){//AI read correct, (send AI number, nhaptay = null)

        }else if(WHAT_TYPE_AI_RETURNED == 2){//AI can not got number + AI read incorrect number

            if(CommonHelper.checkValidString(ChiSo_AI_Got_Incorrect)){// AI read incorrect number (send both nhaptay + AI)
                thongTinKHObj.setChiSoCuoi(ChiSo_AI_Got_Incorrect);
            }else{ //AI can not got number (send nhaptay, AI = null)
                thongTinKHObj.setChiSoCuoi(null);
            }
        }
    }
    private class GetTinhTienTask extends AsyncTask<Void, Void, Void> {
        private boolean isOK = false;
        String errMsg ="";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
            CHISOCUOI = id_edt_gcs_hientai.getText().toString().trim();
            if(IS_NHAP_TAY){
                CommonHelper.hideKeyBoardWithEditext(AGhiChiSoPrepare.this,id_edt_gcs_hientai);
            }
        }

        @Override
        public Void doInBackground(Void... params) {
            if(thongTinKHObj != null){
                JSONObject holder = new JSONObject();
                try {
                    holder.put("Idkh",""+thongTinKHObj.getIdkh());
                    holder.put("ChiSoCuoi",""+CHISOCUOI);
                    holder.put("NgayNhap",""+thongTinKHObj.getNgayNhap());
                    holder.put("TThaiGhi",""+thongTinKHObj.getTenTthaiGhi());
                    ResultFromWebservice rs = new ResultFromWebservice();
                    GetDichVuGCS_TinhTien_Response getDichVuGCS_TinhTien_Response = rs.getDichVuGCS_TinhTien_Response(AGhiChiSoPrepare.this, GlobalVariable.LOGIN_TOKEN_TYPE , GlobalVariable.LOGIN_ACCESS_TOKEN,holder,AGhiChiSoPrepare.this);

                    if(getDichVuGCS_TinhTien_Response != null){
                         errMsg = getDichVuGCS_TinhTien_Response.getErrorMessage();
                       if(CommonHelper.checkValidString(errMsg)){
                           isOK = false;
                       }else{
                           isOK = true;
                           thongTinKHObj.setKlTieuThu(getDichVuGCS_TinhTien_Response.getKlTieuThu());
                           thongTinKHObj.setM3TinhTien(getDichVuGCS_TinhTien_Response.getM3TinhTien());
                           thongTinKHObj.setTongTien(getDichVuGCS_TinhTien_Response.getTongTien());
                           thongTinKHObj.setTongTienText(getDichVuGCS_TinhTien_Response.getTongTienText());
                           whatType();
                           /*
                           if(CommonHelper.checkValidString(ChiSo_AI_Got_Incorrect)){
                               if(WHAT_TYPE_AI_RETURNED != 1){//AI read incorrect and have nhap_tay
                                   thongTinKHObj.setChiSoCuoi(ChiSo_AI_Got_Incorrect);
                               }
                           }else{
                               thongTinKHObj.setChiSoCuoi(null);
                           }
                           */
                       }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            disMissLoading();
//            if(isOK){
//                id_ly_sau_gcs.setVisibility(View.VISIBLE);
//                if(isHasAtLeast1Photo()){
//                    id_tv_notification_result.setText(getString(R.string.dichvu_finish_gcs));
//                    setVisibleLV(View.GONE);
//                }else{
//                    id_tv_notification_result.setText(getString(R.string.dichvu_missing_img_gcs));
//                    setVisibleLV(View.VISIBLE);
//                }
//            }else{
//                if(CommonHelper.checkValidString(errMsg)){
//                    CommonHelper.showWarning(AGhiChiSoPrepare.this,getString(R.string.gcs_pre_error_tinhtien)+errMsg);
//                }
//            }
//            id_tv_gcs_kltthu.setText(thongTinKHObj.getKlTieuThu()+" ");
//            id_tv_gcs_kltthu.setText(CommonHelper.getStringWithSeparatorThousand(Double.valueOf(thongTinKHObj.getKlTieuThu())));
//            id_tv_tongtien_number.setText(CommonHelper.getStringWithSeparatorThousand(Double.valueOf(thongTinKHObj.getTongTien()))+"");
//            id_tv_tien_chu.setText(getString(R.string.gcs_pre_bangchu)+" "+thongTinKHObj.getTongTienText());

//            if(IS_NHAP_TAY){
//                id_edt_gcs_hientai.setEnabled(true);
//                long csNhapTay = Long.valueOf(id_edt_gcs_hientai.getText().toString().trim());
//                id_edt_gcs_hientai.setText(CommonHelper.getStringWithSeparatorThousand(Double.valueOf(csNhapTay)));
//
//            }else{
//                id_edt_gcs_hientai.setEnabled(false);
//                id_edt_gcs_hientai.setText(CommonHelper.getStringWithSeparatorThousand(Double.valueOf(BCSC.getAiAPIText())));
//            }
            if(isOK){
                String tongTienSo = "<p><b style='margin-left:4%'>Tổng tiền: </b>"+CommonHelper.getStringWithSeparatorThousand(thongTinKHObj.getTongTien())+" VNĐ"+"</p>";
                String tongTienChu = "<p><b style='margin-left:4%'>Bằng chữ: </b> "+thongTinKHObj.getTongTienText() + "</p>";
                String resultTongTien = tongTienSo+tongTienChu;
                CommonHelper.showAlertDialog(AGhiChiSoPrepare.this, resultTongTien);
            }else{
                CommonHelper.showWarning(AGhiChiSoPrepare.this, "Đầu vào không hợp lệ");
            }

        }
    }

    private boolean isHasAtLeast1Photo(){
        for(BaoCaoSuCoObj bcsc : mArrSuco){
            if(bcsc.getBm() != null){
                return true;
            }
        }
        return false;
    }
    private void showTakeMorePhoto(){
        if(mArrSuco.get(0).getBm() != null){
            if(mArrSuco.size()==1){
                for(int i = 1 ; i< 3; i++){
                    BaoCaoSuCoObj sc = new BaoCaoSuCoObj();
                    sc.setLink(""+i);
                    if(i==0){
                        sc.setProcessAI(true);
                    }else{
                        sc.setProcessAI(false);
                    }
                    mArrSuco.add(sc);
                }
            }else {

            }
        }else{
            ArrayList<BaoCaoSuCoObj> mArr0 = new ArrayList<>();
            mArr0.add(mArrSuco.get(0));
            mArrSuco.clear();
            mArrSuco.addAll(mArr0);
        }
        listViewBaoCaoSuCoAdapter.refresh(mArrSuco);
    }
    private void tinhtien(){
        if(isValidInputNumber()){
            new GetTinhTienTask().execute();
        }else{

            //String currentStr = //id_tv_notification_result.getText().toString();
            //currentStr+= " | Lỗi: "+ getString(R.string.gcs_pre_input_invalid);
            String currentStr ="Lỗi: "+ getString(R.string.gcs_pre_input_invalid);
            id_tv_notification_result.setText(Html.fromHtml(currentStr));
            CommonHelper.showAlertDialog(AGhiChiSoPrepare.this, getString(R.string.gcs_pre_input_invalid));
//            CommonHelper.showToast(getApplicationContext(),getString(R.string.gcs_pre_input_invalid));
        }

    }
    boolean isValidInputNumber(){
        String cscuoiStr = id_edt_gcs_hientai.getText().toString().trim();

        if(CommonHelper.checkValidString(cscuoiStr)){
            double csc = Double.parseDouble(cscuoiStr);
            double csdau = Double.valueOf(thongTinKHObj.getChiSoDau());
            if(csc < csdau){
                return false;
            }
        }else{
            return false;
        }
        if(!CommonHelper.isNumeric(cscuoiStr)){
            return false;
        }
        double cscx = Double.parseDouble(cscuoiStr);
        if (!((cscx == Math.floor(cscx)) && !Double.isInfinite(cscx))) {
            return false;
        }
        return  true;
    }
    boolean isValidInputNumberAtSend(){
        try{
            String cscuoiStr = id_edt_gcs_hientai.getText().toString().trim();
//            String newCsCuoi = CommonHelper.getStringWithSeparatorThousand(Double.valueOf(CHISOCUOI));
//            if(cscuoiStr.equalsIgnoreCase(newCsCuoi)){
//                cscuoiStr = CHISOCUOI;
                if(CommonHelper.checkValidString(cscuoiStr)){
                    double csc = Double.parseDouble(cscuoiStr);
                    double csdau = Double.valueOf(thongTinKHObj.getChiSoDau());
                    if(csc < csdau){
                        return false;
                    }
                }else{
                    return false;
                }

                if(!CommonHelper.isNumeric(cscuoiStr)){
                    return false;
                }
                double cscx = Double.parseDouble(cscuoiStr);
                if (!((cscx == Math.floor(cscx)) && !Double.isInfinite(cscx))) {
                    return false;
                }
//            }else{
//                return false;
//            }
        }catch (Exception e){
            System.out.println(e);
            return false;
        }



        return  true;
    }

    @Override
    public void takePhotoX( BaoCaoSuCoObj baoCaoSuCoObj){
        if(baoCaoSuCoObj.isEnableCamera()){

            BCSC = baoCaoSuCoObj;
//        startActivity(new Intent(this, AGhiChiSoAPI.class));
            startActivityForResult(new Intent(this, AGhiChiSoAPI.class), Request_AGhiChiSoPrepare_2_AGhiChiSoAPI);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Request_AGhiChiSoPrepare_2_AGhiChiSoAPI){
            if(listViewBaoCaoSuCoAdapter != null){
                listViewBaoCaoSuCoAdapter.refresh(mArrSuco);
            }
            if(resultCode == Activity.RESULT_OK){//AI đọc đúng
                String result=data.getStringExtra(GlobalVariable.KEY_GET_VALUE_GCS);
                CommonHelper.showToast(getApplicationContext(),result);
                if(BCSC != null){
                    if(CommonHelper.checkValidString(BCSC.getAiAPIText())){
                        if(id_edt_gcs_hientai != null){
                            id_edt_gcs_hientai.setEnabled(false);
                            id_ly_nhap_cs_edt.setVisibility(View.VISIBLE);
                            id_edt_gcs_hientai.setText(BCSC.getAiAPIText());
                            CHISOCUOI = BCSC.getAiAPIText();
                            mArrNumberRead.add(BCSC.getAiAPIText());
                            if(CommonHelper.isNumeric(CHISOCUOI)){
                                double cscx = Double.parseDouble(CHISOCUOI);
                                if (((cscx == Math.floor(cscx)) && !Double.isInfinite(cscx))) {
                                    ChiSo_AI_Got_Incorrect = "";
                                    thongTinKHObj.setChiSoCuoi(CHISOCUOI);
                                    thongTinKHObj.setChiSoCuoi_NhapTay(null);
                                    WHAT_TYPE_AI_RETURNED = 1;
                                    BCSC.setEnableCamera(false);
                                    tinhtien();
                                }

                            }else{
                                CommonHelper.showWarning(AGhiChiSoPrepare.this,getString(R.string.gcs_gcs_invalid_number_gcs));
                            }
                        }
                    }else {
                        id_tv_notification_result.setText(getString(R.string.gcs_pre_cannot_readimg));
                        id_edt_gcs_hientai.setEnabled(true);
                        id_ly_nhap_cs_edt.setVisibility(View.VISIBLE);
                    }
                }
                if(isTakeFullImage()){
                    if(!isGotNumberFromImg()){
                        CommonHelper.showWarning(AGhiChiSoPrepare.this,getString(R.string.gcs_pre_cannot_readimg));
                        id_edt_gcs_hientai.setEnabled(true);
                        id_ly_nhap_cs_edt.setVisibility(View.VISIBLE);
                    }
                }
            }else{
                if(data!=null){
                    if(BCSC.isProcessAI()){// AI đọc sai
                        String result=data.getStringExtra(GlobalVariable.KEY_GET_VALUE_GCS_FAILED);
                        ChiSo_AI_Got_Incorrect=data.getStringExtra(GlobalVariable.KEY_GET_VALUE_GCS_INCORRECT);
                        WHAT_TYPE_AI_RETURNED = 2;

                        if(CommonHelper.checkValidString(result)){
                            id_tv_notification_result.setText(Html.fromHtml(getString(R.string.gcs_pre_cannot_readimg)));
//                            id_tv_notification_result.setText(Html.fromHtml(getString(R.string.gcs_pre_cannot_readimg) + getString(R.string.dichvu_ai_error_message)+result));
                        }else{
                            id_tv_notification_result.setText(Html.fromHtml(getString(R.string.gcs_pre_cannot_readimg)));
                        }

                        id_edt_gcs_hientai.setEnabled(true);
                        IS_NHAP_TAY = true;
                        id_edt_gcs_hientai.setText("");
                        id_tv_gcs_kltthu.setText("0");
                        id_tv_tongtien_number.setText("0");
                        id_tv_tien_chu.setText("");
                        id_ly_nhap_cs_edt.setVisibility(View.VISIBLE);
                    }

                }

            }
            showTakeMorePhoto();
        }
    }
    @Override
    public void galleryX( BaoCaoSuCoObj baoCaoSuCoObj){

    }
    @Override
    public void cancelImg( BaoCaoSuCoObj baoCaoSuCoObj){
        if(baoCaoSuCoObj.isEnableCamera()){
            baoCaoSuCoObj.setBm(null);
            listViewBaoCaoSuCoAdapter.refresh(mArrSuco);
            showTakeMorePhoto();
        }

    }

    private ArrayList<String> mArrNumberRead = new ArrayList<>();
    @Override
    public void onResume() {
        super.onResume();
    }

    private boolean isTakeFullImage(){
        boolean chk = true;
        for(BaoCaoSuCoObj bcsc : mArrSuco){
            if(bcsc.getBm() == null){
                return false;
            }
        }
        return chk;
    }
    private boolean isGotNumberFromImg(){
        boolean chk = false;
        if(CommonHelper.checkValidString(CHISOCUOI)){
            chk =true;
        }
        return chk;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        BCSC = null;
        CHISOCUOI = "";
    }
    private class SendDataTask extends AsyncTask<Void, Void, Void> {
        private boolean isOK = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        @Override
        public Void doInBackground(Void... params) {
            ArrayList<String> mArrayBase64 = new ArrayList<>();
            for(BaoCaoSuCoObj obj : mArrSuco){
                if(obj != null){
                    if(obj.getBm() != null){
                        String x64 = CommonHelper.bitmapToBase64(obj.getBm());
                        mArrayBase64.add(x64);
                    }
                }
            }
            thongTinKHObj.setjSONArray(new JSONArray(mArrayBase64));
            ResultFromWebservice rs = new ResultFromWebservice();
            GetDichVuGCS_Send_Data_Response getDichVuGCS_Send_Data_Response = rs.getDichVuGCS_Send_Data_Response(AGhiChiSoPrepare.this,thongTinKHObj, GlobalVariable.LOGIN_TOKEN_TYPE , GlobalVariable.LOGIN_ACCESS_TOKEN);
            isOK = getDichVuGCS_Send_Data_Response.isOK();
            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            disMissLoading();
            if(isOK){
                for(BaoCaoSuCoObj obj : mArrSuco){
                    obj.setEnableCamera(false);
                }
                if(listViewBaoCaoSuCoAdapter != null){
                    listViewBaoCaoSuCoAdapter.refresh(mArrSuco);
                }
                id_edt_gcs_hientai.setEnabled(false);
                id_tv_notification_result.setText(getString(R.string.dichvu_finish_ok));
                id_tv_notification_result.setBackgroundColor(getResources().getColor(R.color.success));
                id_tv_notification_result.setTextColor(getResources().getColor(R.color.black));

                id_btn_send_gcs_data.setVisibility(View.GONE);
                id_btn_right.setVisibility(View.GONE);
            }else{
                CommonHelper.showAlertDialog(AGhiChiSoPrepare.this, getString(R.string.dichvu_finish_notok));
                id_tv_notification_result.setText(getString(R.string.dichvu_finish_notok));
                id_tv_notification_result.setBackgroundColor(getResources().getColor(R.color.red));
                id_tv_notification_result.setTextColor(getResources().getColor(R.color.black));
            }

        }
    }

    @Override
    public void okBtn(int type, String tenKh, String sdt, String diachi) {

    }

    @Override
    public void okBtn(int type, String tenKh, String sdt, String diachi, String hdtimvitrisuco) {

    }

    @Override
    public void cancelBtn(int type) {
        if(type == GlobalVariable.DO_NOTHING){
            finish();
        }else if(type == GlobalVariable.SEND_DATA_GCS){

        }
    }
    @Override
    public void okBtn(int type) {

        if(type == GlobalVariable.DO_NOTHING){

        }else if(type == GlobalVariable.SEND_DATA_GCS){
            new SendDataTask().execute();
        }
    }
}
