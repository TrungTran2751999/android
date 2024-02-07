package com.huewaco.cskh.fragment;


import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;

import com.customview.edittext.CustomEditText;
import com.huewaco.cskh.adapter.ListViewCustomerGroupListItemAdapter;
import com.huewaco.cskh.back.BackPressImpl;
import com.huewaco.cskh.objects.KhachHangObj;
import com.huewaco.cskh.webservice.objects.GetHddtDetailResponse;
import com.huewaco.cskh.webservice.objects.GetHddtStatusResponse;
import com.huewaco.cskh.webservice.objects.PostHddtResponse;
import com.kyanogen.signatureview.SignatureView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;


import androidx.annotation.Nullable;

import com.huewaco.cskh.activity.R;

import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;

import com.huewaco.cskh.interfacex.ITF_DialogButtonHddtProcessing;


import com.huewaco.cskh.webservice.processing.ResultFromWebservice;

import java.util.Calendar;
import com.github.barteksc.pdfviewer.PDFView;

public class FTabDichVu4Hddt extends FParent implements ITF_DialogButtonHddtProcessing {
    protected String title;
    public ITF_DialogButtonHddtProcessing itfDialogButtonProcessing = this;
    private Button id_btn_ok;
    private Button id_btn_clear;
//    private WebView webview;
    SignatureView signatureView;
    Bitmap bitmap;
    String verifyCode;
    LinearLayout signatureLayout;
    LinearLayout buttonLayout;
    LinearLayout buttonSignLayout;
    Button id_btn_sign;
    LinearLayout contractInfo;
    TextView contractname;
    TextView contractmaddk;
    TextView contractno;
    TextView contractdate;
    TextView contractStatus;
    TextView buyer;
    TextView signer;
    TextView signernote;
    TextView buyerinfo;
    TextView id_signerlabel;
    TextView id_signernotelabel;
    LinearLayout signerinfolayout;
    CustomEditText signerinput;
    CustomEditText signernoteinput;
    Button id_btn_viewdetail;
    String status;
    PDFView pdfView;
    Boolean isVerified = false;
    String buyerName;
    RadioButton radio_signer;
    RadioButton radio_signernote;
    boolean isBuyer = true;
    Spinner id_spn_customergroup;
    LinearLayout id_layoutcmb;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        if(!GlobalVariable
//                .KHACH_HANG_CURRENT.getMaDDK().isEmpty()){
//            new GetCheckHddtTask().execute();
//        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_tab_dichvu4_hddt, container, false);

        initCommonView(v, this);

        initComponent(v);
        addListener();
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
                if(GlobalVariable.KHACH_HANG_CURRENT.getMaDDK().isEmpty()){
                    initState();
                }else{
                    new GetCheckHddtTask().execute();
                }

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
            if(GlobalVariable.KHACH_HANG_CURRENT.getMaDDK().isEmpty()){
                initState();
            }else{
                new GetCheckHddtTask().execute();
            }
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
        id_btn_ok = (Button) v.findViewById(R.id.id_btn_close_dialog_bcsc_ng);
        id_btn_clear = (Button) v.findViewById(R.id.id_btn_clear);
        contractInfo = (LinearLayout) v.findViewById(R.id.contractinfo);
        contractname = (TextView) v.findViewById(R.id.contractname);
        contractmaddk = (TextView) v.findViewById(R.id.contractmaddk);
        contractno = (TextView) v.findViewById(R.id.contractno);
        contractdate = (TextView) v.findViewById(R.id.contractdate);
        contractStatus = (TextView) v.findViewById(R.id.contractstatus);
        buyer = (TextView) v.findViewById(R.id.buyer);
        signer = (TextView) v.findViewById(R.id.signer);
        signernote = (TextView) v.findViewById(R.id.signernote);
        signerinfolayout = (LinearLayout) v.findViewById(R.id.signerinfo);
        signerinput = (CustomEditText) v.findViewById(R.id.signerinput);
        signernoteinput = (CustomEditText) v.findViewById(R.id.signernoteinput);
        id_btn_viewdetail = (Button) v.findViewById(R.id.id_btn_viewdetail);
        pdfView = (PDFView) v.findViewById(R.id.pdfView);

        buttonSignLayout = (LinearLayout) v.findViewById(R.id.button1_linear);
        id_btn_sign = (Button) v.findViewById(R.id.id_btn_sign);
        signatureView =  (SignatureView) v.findViewById(R.id.signature_view);
        signatureLayout = (LinearLayout) v.findViewById(R.id.signature_linear);
        buttonLayout = (LinearLayout) v.findViewById(R.id.button_linear);
        radio_signer =  (RadioButton) v.findViewById(R.id.radio_signer);
        radio_signernote =  (RadioButton) v.findViewById(R.id.radio_signernote);
        buyerinfo = (TextView) v.findViewById(R.id.buyerinfo);
        id_signerlabel = (TextView) v.findViewById(R.id.id_signerlabel);
        id_signernotelabel = (TextView) v.findViewById(R.id.id_signernotelabel);
        initState();
    }
    private  void initState(){
        id_tv_title.setText(title);

        signerinput.setVisibility(View.GONE);
        signernoteinput.setVisibility(View.GONE);

        pdfView.setVisibility(View.GONE);
        buttonSignLayout.setVisibility(View.GONE);
        signerinfolayout.setVisibility(View.GONE);
        signatureLayout.setVisibility(View.GONE);
        buttonLayout.setVisibility(View.GONE);
        id_signerlabel.setVisibility(View.GONE);
        id_signernotelabel.setVisibility(View.GONE);

       //
        contractInfo.setVisibility(View.VISIBLE);
        contractname.setText("Tên hợp đồng: HỢP ĐỒNG DỊCH VỤ CẤP NƯỚC");
        contractmaddk.setText("Mã đơn đăng ký: ");
        contractno.setText("Số: ");

        buyer.setText("Chủ hộ: ");
        signer.setText("Người ký: " );
        signernote.setText("Quan hệ với chủ hộ: " );
        contractdate.setText("Ngày ký: " );
        contractStatus.setText("Trạng thái hợp đồng: ");
        id_btn_viewdetail.setText("XEM CHI TIẾT HỢP ĐỒNG");
        id_btn_viewdetail.setVisibility(View.GONE);
    }


    @Override
    protected void addListener() {
        id_btn_ok.setOnClickListener(this);
        id_btn_clear.setOnClickListener(this);
        id_btn_viewdetail.setOnClickListener(this);
        id_btn_sign.setOnClickListener(this);
        radio_signer.setOnClickListener(this);
        radio_signernote.setOnClickListener(this);
    }
    @Override
    public boolean onBackPressed() {
//        if(contractInfo.getVisibility() == View.VISIBLE|| (contractInfo.getVisibility() == View.GONE && webview.getVisibility() == View.GONE && buttonSignLayout.getVisibility() == View.GONE && signatureLayout.getVisibility() == View.GONE&& buttonLayout.getVisibility() == View.GONE)){
        if (contractInfo.getVisibility() == View.VISIBLE || (contractInfo.getVisibility() == View.GONE && pdfView.getVisibility() == View.GONE && buttonSignLayout.getVisibility() == View.GONE && signatureLayout.getVisibility() == View.GONE && buttonLayout.getVisibility() == View.GONE)) {
            checkFragmentToRefresh();
            return new BackPressImpl(this).onBackPressed();
        } else if (pdfView.getVisibility() == View.GONE) {
            pdfView.setVisibility(View.VISIBLE);
            buttonSignLayout.setVisibility(status =="true"? View.GONE:View.VISIBLE);
            signerinfolayout.setVisibility(View.GONE);
            signatureLayout.setVisibility(View.GONE);
            buttonLayout.setVisibility(View.GONE);
            return true;
        } else {

            pdfView.setVisibility(View.GONE);
            buttonSignLayout.setVisibility(View.GONE);
            signerinfolayout.setVisibility(View.GONE);
            signatureLayout.setVisibility(View.GONE);
            buttonLayout.setVisibility(View.GONE);
            new GetHddtDetailTask().execute();
            return true;
        }
        //refreshData();
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.id_btn_sign:
                buttonSignLayout.setVisibility(View.GONE);
                pdfView.setVisibility(View.GONE);
                signerinfolayout.setVisibility(View.VISIBLE);
                signatureLayout.setVisibility(View.VISIBLE);
                buttonLayout.setVisibility(View.VISIBLE);
                signerinput.setText(buyerName);
                signernoteinput.setText("");
                break;
            case R.id.id_btn_close_dialog_bcsc_ng:
                if(CommonHelper.isNetworkAvailable(fpActivity)){
                    if(isBuyer == false && (signerinput.getText().toString().equals(buyerName) || signerinput.getText().toString().equals("") || signernoteinput.getText().toString().equals(""))
                    || signerinput.getText().toString().equals(""))
                    {
                        CommonHelper.showWarning(fpActivity,"Thông tin người ký chưa hợp lệ");
                        break;
                    }
                    if(!signatureView.isBitmapEmpty()){
                        bitmap = signatureView.getSignatureBitmap();
                        String encoded = CommonHelper.bitmapToBase64(bitmap);
                        new PostSignatureTask().execute();
                    }else{
                        CommonHelper.showWarning(fpActivity,getString(R.string.tab_dichvu_hddt_signrequired));
                    }
                }else{
                    CommonHelper.showWarning(fpActivity,getString(R.string.nointernet));
                }
                break;
            case R.id.id_btn_clear:
                signatureView.clearCanvas();
                break;
            case R.id.id_btn_viewdetail:
                contractInfo.setVisibility(View.GONE);
//                webview.setVisibility(View.VISIBLE);
                pdfView.setVisibility(View.VISIBLE);
                buttonSignLayout.setVisibility(status =="true"? View.GONE:View.VISIBLE);
                signerinfolayout.setVisibility(View.GONE);
                signatureLayout.setVisibility(View.GONE);
                buttonLayout.setVisibility(View.GONE);
                break;
            case R.id.radio_signer:
                boolean checked = ((RadioButton) v).isChecked();
                if (checked) {
                    isBuyer = true;
                    id_signerlabel.setVisibility(View.GONE);
                    id_signernotelabel.setVisibility(View.GONE);
                    signerinput.setVisibility(View.GONE);
                    signernoteinput.setVisibility(View.GONE);
                    signerinput.setText(buyerName);
                    signernoteinput.setText("");
                }
                break;
            case R.id.radio_signernote:
                boolean checked2 = ((RadioButton) v).isChecked();
                if (checked2) {
                    isBuyer = false;
                    signerinput.setText("");
                    signernoteinput.setText("");
                    signerinput.setVisibility(View.VISIBLE);
                    signernoteinput.setVisibility(View.VISIBLE);
                    id_signerlabel.setVisibility(View.VISIBLE);
                    id_signernotelabel.setVisibility(View.VISIBLE);
                }
                break;

        }
    }
    @Override
    public void verifyBtn(String verifyCode) {
        if(CommonHelper.isNetworkAvailable(fpActivity)){
            this.verifyCode = verifyCode;
            new VerifyCodeTask().execute();
        }else{
            CommonHelper.showWarning(fpActivity,getString(R.string.nointernet));
        }
    }

    @Override
    public void cancelBtn() {

    }
    @Override
    public void resendBtn() {
        if (CommonHelper.isNetworkAvailable(fpActivity)) {
            new ResendCodeTask().execute();
        } else {
            CommonHelper.showWarning(fpActivity, getString(R.string.nointernet));
        }
    }

    public class GetCheckHddtTask extends AsyncTask<String, Void, GetHddtStatusResponse> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        public GetHddtStatusResponse doInBackground(String... params) {
            ResultFromWebservice rs = new ResultFromWebservice();

            GetHddtStatusResponse getHddtCheck = rs.getCheckSignedContract(fpActivity, GlobalVariable
                    .KHACH_HANG_CURRENT.getMaDDK(),GlobalVariable.LOGIN_TOKEN_TYPE + " " + GlobalVariable.LOGIN_ACCESS_TOKEN,fpActivity);
            return getHddtCheck;
        }

        public void onPostExecute(GetHddtStatusResponse result) {
            disMissLoading();
            status = result.getStatus();
            if(status == "false" || status == "true"){
//                webview.loadUrl("https://docs.google.com/gview?embedded=true&url="+ result.getLink());
                new GetHddtDetailTask().execute();
            }else{
                initState();
            }




        }
    }
    public class GetHddtDetailTask extends AsyncTask<String, Void, GetHddtDetailResponse> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        public GetHddtDetailResponse doInBackground(String... params) {
            ResultFromWebservice rs = new ResultFromWebservice();

            GetHddtDetailResponse getHddtCheck = rs.getHddtDetail(fpActivity, GlobalVariable
                    .KHACH_HANG_CURRENT.getMaDDK(),GlobalVariable.LOGIN_TOKEN_TYPE + " " + GlobalVariable.LOGIN_ACCESS_TOKEN,fpActivity);
            return getHddtCheck;
        }

        public void onPostExecute(GetHddtDetailResponse result) {
            disMissLoading();
            if(isVerified){
                isVerified = false;
            }else{
                contractInfo.setVisibility(View.VISIBLE);
                contractname.setText("Tên hợp đồng: HỢP ĐỒNG DỊCH VỤ CẤP NƯỚC");
                contractmaddk.setText("Mã đơn đăng ký: " + result.getContract().getMaDDK());
                contractno.setText("Số: " + result.getContract().getContractNo()+"/"+result.getContract().getContractYear());
                buyerName = result.getContract().getBuyerName();
                buyerinfo.setText(buyerName);
                buyer.setText("Chủ hộ: " + buyerName);
                signer.setText("Người ký: " + result.getContract().getCustomerSignerName());
                signernote.setText("Quan hệ với chủ hộ: " + result.getContract().getCustomerSignerNote());
                Calendar cl = Calendar.getInstance();
                cl.setTime(result.getContract().getContractIssueDate());
                contractdate.setText("Ngày ký: " + cl.get(Calendar.DAY_OF_MONTH)+ "-"+ (cl.get(Calendar.MONTH) + 1 )+"-"+ cl.get(Calendar.YEAR));
                status = result.getContract().getContratus();
                contractStatus.setText("Trạng thái hợp đồng: " + (status == "true"?"Đã ký và có hiệu lực": "Khách hàng chưa ký"));
                id_btn_viewdetail.setText( result.getContract().getContratus() == "true"?"XEM CHI TIẾT HỢP ĐỒNG": "XEM CHI TIẾT VÀ KÝ HỢP ĐỒNG");
                id_btn_viewdetail.setVisibility(View.VISIBLE);
            }

            pdfView.fromBytes(CommonHelper.pdfBase64ToByte(result.getContract().getEContractPdf())).enableAnnotationRendering(true).load();
            pdfView.loadPages();
        }
    }
    public class PostSignatureTask extends AsyncTask<String, Void, PostHddtResponse> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        public PostHddtResponse doInBackground(String... params) {
            ResultFromWebservice rs = new ResultFromWebservice();

            PostHddtResponse postSignature = rs.postOTP(fpActivity, GlobalVariable
                    .KHACH_HANG_CURRENT.getMaDDK(),GlobalVariable
                    .KHACH_HANG_CURRENT.getDiDong(), CommonHelper.bitmapToBase64(bitmap),signerinput.getText().toString(),signernoteinput.getText().toString(),GlobalVariable.LOGIN_TOKEN_TYPE + " " + GlobalVariable.LOGIN_ACCESS_TOKEN,fpActivity);
            return postSignature;
        }

        public void onPostExecute(PostHddtResponse result) {
            disMissLoading();
            if(result.getStatus() == "true"){
                CommonHelper.showDAGKyHddt(fpActivity,itfDialogButtonProcessing);
            }else{
                CommonHelper.showWarning(fpActivity, result.getMessage());
            }


        }
    }
    public class ResendCodeTask extends AsyncTask<String, Void, PostHddtResponse> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        public PostHddtResponse doInBackground(String... params) {
            ResultFromWebservice rs = new ResultFromWebservice();
            PostHddtResponse postSignature = rs.postResendOTP(fpActivity, GlobalVariable
                    .KHACH_HANG_CURRENT.getMaDDK(),GlobalVariable
                    .KHACH_HANG_CURRENT.getDiDong(),GlobalVariable.LOGIN_TOKEN_TYPE + " " + GlobalVariable.LOGIN_ACCESS_TOKEN,fpActivity);
            return postSignature;
        }

        public void onPostExecute(PostHddtResponse result) {
            disMissLoading();
            CommonHelper.showWarning(fpActivity, result.getMessage());
        }
    }
    public class VerifyCodeTask extends AsyncTask<String, Void, PostHddtResponse> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        public PostHddtResponse doInBackground(String... params) {
            ResultFromWebservice rs = new ResultFromWebservice();
            PostHddtResponse postSignature = rs.postCheckOTP(fpActivity, GlobalVariable
                    .KHACH_HANG_CURRENT.getMaDDK(),verifyCode,GlobalVariable.LOGIN_TOKEN_TYPE + " " + GlobalVariable.LOGIN_ACCESS_TOKEN,fpActivity);
            return postSignature;
        }

        public void onPostExecute(PostHddtResponse result) {
            disMissLoading();
            if(result.getStatus() == "false"){
                CommonHelper.showDAGKyHddt(fpActivity,itfDialogButtonProcessing);
            }else{
                CommonHelper.showWarning(fpActivity, result.getMessage());
                if(result.getStatus() == "true"){
                    //status = "true";
                    contractInfo.setVisibility(View.GONE);
//                    webview.loadUrl(webview.getUrl());
//                    webview.setVisibility(View.VISIBLE);
                    isVerified = true;
                    new GetHddtDetailTask().execute();
                    pdfView.setVisibility(View.VISIBLE);
                    buttonSignLayout.setVisibility(View.GONE);
                    signerinfolayout.setVisibility(View.GONE);
                    signatureLayout.setVisibility(View.GONE);
                    buttonLayout.setVisibility(View.GONE);
                }
            }

        }
    }

}
