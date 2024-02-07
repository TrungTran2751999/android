package com.huewaco.cskh.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.customview.edittext.CustomEditText;
import com.huewaco.cskh.adapter.ListViewKhuVucListItemAdapter;
import com.huewaco.cskh.adapter.ListViewPhuongXaListItemAdapter;
import com.huewaco.cskh.adapter.ListViewQuanHuyenListItemAdapter;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.objects.KhuVucObj;
import com.huewaco.cskh.objects.PhuongXaListItmObj;
import com.huewaco.cskh.objects.QuanHuyenListItemObj;
import com.huewaco.cskh.webservice.objects.GetDichVu0DvYeuCausResponse;
import com.huewaco.cskh.webservice.processing.ResultFromWebservice;

import java.util.ArrayList;

public class ADangKyCapNuoc extends AParent {

    private ListViewKhuVucListItemAdapter adapterKhuvuc;
    private ListViewQuanHuyenListItemAdapter adapterQuanHuyen;
    private ListViewPhuongXaListItemAdapter adapterPhuongXa;
    private ArrayList<KhuVucObj> mArrItemKhuVuc = new ArrayList<>();
    private ArrayList<QuanHuyenListItemObj> mArrItemQuanHuyen = new ArrayList<>();
    private ArrayList<PhuongXaListItmObj> mArrItemPhuongXa = new ArrayList<>()  ;
    private Spinner id_spn_dv_chinhanhkhuvuc, id_spn_dv_quanhuyen, id_spn_dv_phuongxa;
    private RadioButton id_rb_dv_yeucaulapdatnuoc, id_rb_dv_yeucaulapdatnuocnganhan;
    private TextView id_tv_dv_loaiyeucau;
    private CustomEditText id_edt_dv_ten_khang, id_edt_dv_so_nha, id_edt_dv_dpho, id_edt_dv_email, id_edt_dv_sdt, id_edt_noidung;
    private Button id_btn_ok,id_btn_dv_taive;
    private String LoaiYC, TenKH, CMND, Email, SDT, SoNha, DuongPho, MaPhuong, MaKV, NoiDung;
    private boolean IS_DANGKY_THANHCONG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_tab_dichvu0_dky_cap_moi);
        initData();
        initComponent();
        addListener();
        setText();
        new GetDichVu0DanhMucDiaChinh_KhuVucTask().execute();
        new GetDichVu0DanhMucDiaChinh_QuanHuyenTask().execute();
    }

    @Override
    protected void initComponent() {
        initTopbarView();
        id_btn_dv_taive = (Button)findViewById(R.id.id_btn_dv_taive);
        id_tv_title.setText(getString(R.string.login_dangky_capdien));
        id_btn_left.setBackgroundResource(R.drawable.btn_back);
        //
        id_spn_dv_chinhanhkhuvuc = (Spinner) findViewById(R.id.id_spn_dv_chinhanhkhuvuc);
        id_spn_dv_quanhuyen = (Spinner) findViewById(R.id.id_spn_dv_quanhuyen);
        id_spn_dv_phuongxa = (Spinner) findViewById(R.id.id_spn_dv_phuongxa);
        //
        adapterKhuvuc = new ListViewKhuVucListItemAdapter(getApplicationContext(), mArrItemKhuVuc);
        id_spn_dv_chinhanhkhuvuc.setAdapter(adapterKhuvuc);
        //
        adapterQuanHuyen = new ListViewQuanHuyenListItemAdapter(getApplicationContext(), mArrItemQuanHuyen);
        id_spn_dv_quanhuyen.setAdapter(adapterQuanHuyen);
        //
        adapterPhuongXa = new ListViewPhuongXaListItemAdapter(getApplicationContext(), mArrItemPhuongXa);
        id_spn_dv_phuongxa.setAdapter(adapterPhuongXa);
        //
        id_rb_dv_yeucaulapdatnuoc = (RadioButton) findViewById(R.id.id_rb_dv_yeucaulapdatnuoc);
        id_rb_dv_yeucaulapdatnuocnganhan = (RadioButton) findViewById(R.id.id_rb_dv_yeucaulapdatnuocnganhan);
        id_tv_dv_loaiyeucau = (TextView) findViewById(R.id.id_tv_dv_loaiyeucau);
        id_edt_dv_ten_khang = (CustomEditText) findViewById(R.id.id_edt_dv_ten_khang);
        id_edt_dv_so_nha = (CustomEditText) findViewById(R.id.id_edt_dv_so_nha);
        id_edt_dv_dpho = (CustomEditText) findViewById(R.id.id_edt_dv_dpho);
        id_edt_dv_email = (CustomEditText) findViewById(R.id.id_edt_dv_email);
        id_edt_dv_sdt = (CustomEditText) findViewById(R.id.id_edt_dv_sdt);
        id_edt_noidung = (CustomEditText) findViewById(R.id.id_edt_noidung);
        //
        id_btn_ok = (Button) findViewById(R.id.id_btn_close_dialog_bcsc_ng);
        //
        id_tv_dv_loaiyeucau.setVisibility(View.GONE);
        id_rb_dv_yeucaulapdatnuoc.setVisibility(View.VISIBLE);
        id_rb_dv_yeucaulapdatnuocnganhan.setVisibility(View.VISIBLE);
        id_rb_dv_yeucaulapdatnuoc.setChecked(true);
    }

    private void initData() {
    }

    @Override
    protected void addListener() {
        id_btn_dv_taive.setOnClickListener(this);
        id_btn_ok.setOnClickListener(this);
        //
        final Drawable right = this.getResources().getDrawable(android.R.drawable.ic_delete);
        right.setBounds(0, 0, right.getIntrinsicHeight(), right.getIntrinsicHeight());
        setEditTextListener(id_edt_dv_ten_khang, null, right);
        setEditTextListener(id_edt_dv_so_nha, null, right);
        setEditTextListener(id_edt_dv_dpho, null, right);
        setEditTextListener(id_edt_dv_email, null, right);
        setEditTextListener(id_edt_dv_sdt, null, right);
        setEditTextListener(id_edt_noidung, null, right);
        //
        id_spn_dv_chinhanhkhuvuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                MaKV =  mArrItemKhuVuc.get(arg2).getMaKv().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        //
        id_spn_dv_quanhuyen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if(mArrItemQuanHuyen.get(arg2).getmArrPhuongXa().size()>0){
                    mArrItemPhuongXa  =  mArrItemQuanHuyen.get(arg2).getmArrPhuongXa();
                    adapterPhuongXa.refresh(mArrItemPhuongXa);
                    id_spn_dv_phuongxa.setSelection(0);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        //
        id_spn_dv_phuongxa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if(mArrItemPhuongXa.size()>0){
                    MaPhuong =  mArrItemPhuongXa.get(arg2).getMaPhuong().trim();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    public void onClick(View v) {
         switch (v.getId()) {
             case R.id.id_btn_dv_taive:
                 CommonHelper.openFile(ADangKyCapNuoc.this,GlobalVariable.DON_LAP_DAT_URL);
                 break;
            case R.id.id_btn_close_dialog_bcsc_ng:
                if (CommonHelper.isNetworkAvailable(getApplicationContext())) {
                    //
                    if (id_rb_dv_yeucaulapdatnuoc.isChecked()) {
                        LoaiYC = GlobalVariable.CAP_MOI;
                    } else if (id_rb_dv_yeucaulapdatnuocnganhan.isChecked()) {
                        LoaiYC = GlobalVariable.CAP_MOI_NH;
                    }
                    //
                    TenKH = id_edt_dv_ten_khang.getText().toString();
                    CMND = "";
                    Email = id_edt_dv_email.getText().toString();
                    SDT = id_edt_dv_sdt.getText().toString();
                    SoNha = id_edt_dv_so_nha.getText().toString();
                    DuongPho = id_edt_dv_dpho.getText().toString();
                    NoiDung = id_edt_noidung.getText().toString();
                    //
                    new GetDichVu0DvYeuCausTask().execute();
                } else {
                    CommonHelper.showWarning(getApplicationContext(), getString(R.string.nointernet));
                }

                break;
            case R.id.id_btn_cancel:
                ADangKyCapNuoc.this.onBackPressed();
                break;
            default:
                break;
        }
    }


    // toandtb
    public class GetDichVu0DanhMucDiaChinh_KhuVucTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        @Override
        public Void doInBackground(String... params) {

            mArrItemKhuVuc =  CommonHelper.readKhuVucObjs(getApplicationContext(), GlobalVariable.KHU_VUC_FILE);
            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            disMissLoading();
            if(mArrItemKhuVuc!=null &&mArrItemKhuVuc.size()>0 ){
                //CommonHelper.showWarning(ADangKyCapNuoc.this,"Lay tu cache" );
            } else {
                //CommonHelper.showWarning(ADangKyCapNuoc.this,"lay tu Asert" );
                mArrItemKhuVuc =  CommonHelper.getArrayKhuVucObjFromAssetJsonFile(ADangKyCapNuoc.this, GlobalVariable.KHU_VUC_ASSETS);
            }
            adapterKhuvuc.refresh(mArrItemKhuVuc);
        }
    }

    // toandtb
    public class GetDichVu0DanhMucDiaChinh_QuanHuyenTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        @Override
        public Void doInBackground(String... params) {

            mArrItemQuanHuyen =  CommonHelper.readQuanHuyenCache(ADangKyCapNuoc.this,GlobalVariable.QUAN_HUYEN_FILE);
            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            disMissLoading();
            if(mArrItemQuanHuyen!=null && mArrItemQuanHuyen.size()>0 ){
               // CommonHelper.showWarning(ADangKyCapNuoc.this,"Lay tu cache" );
            } else {
               // CommonHelper.showWarning(ADangKyCapNuoc.this,"lay tu Asert" );
                mArrItemQuanHuyen =  CommonHelper.getArrayQuanHuyenObjFromAssetJsonFile(ADangKyCapNuoc.this, GlobalVariable.QUAN_HUYEN_ASSETS);
            }
            adapterQuanHuyen.refresh(mArrItemQuanHuyen);
        }
    }

    //
    public class GetDichVu0DvYeuCausTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        public Void doInBackground(String... params) {
            ResultFromWebservice rs = new ResultFromWebservice();
            GetDichVu0DvYeuCausResponse getDichVu0DvYeuCausResponse = rs.getDichVu0DvYeuCausResponse(getApplicationContext(), LoaiYC, TenKH, CMND, Email, SDT, SoNha, DuongPho, MaPhuong, MaKV, NoiDung, GlobalVariable.LOGIN_TOKEN_TYPE + " " + GlobalVariable.LOGIN_ACCESS_TOKEN,ADangKyCapNuoc.this);
            IS_DANGKY_THANHCONG = Boolean.parseBoolean(getDichVu0DvYeuCausResponse.getReturnString());
            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            if (IS_DANGKY_THANHCONG) {
                //CommonHelper.showWarning(fpActivity,"True:"+ MaKV+ ":" + MaPhuong );
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                ADangKyCapNuoc.this.onBackPressed();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(ADangKyCapNuoc.this);
                builder.setMessage(ADangKyCapNuoc.this.getString(R.string.tab_dichvu_dangkycapnuoc_thanhcong)).setPositiveButton(ADangKyCapNuoc.this.getString(R.string.tab_caidat_dangxuat_ok), dialogClickListener).show();

            } else {
                CommonHelper.showWarning(ADangKyCapNuoc.this, ADangKyCapNuoc.this.getString(R.string.tab_dichvu_dangkycapnuoc_khongthanhcong));
            }
        }
    }

    public void setText() {
    }
}
