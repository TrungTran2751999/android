package com.huewaco.cskh.fragment;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.customview.edittext.CustomEditText;
import com.huewaco.cskh.activity.R;
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


public class FTabDichVu2NangDoiDo extends FParent {
    protected String title;
    private ListViewKhuVucListItemAdapter adapterKhuvuc;
    private ListViewQuanHuyenListItemAdapter adapterQuanHuyen;
    private ListViewPhuongXaListItemAdapter adapterPhuongXa;
    private ArrayList<KhuVucObj> mArrItemKhuVuc = new ArrayList<>();
    private ArrayList<QuanHuyenListItemObj> mArrItemQuanHuyen = new ArrayList<>();
    private ArrayList<PhuongXaListItmObj> mArrItemPhuongXa = new ArrayList<>()  ;
    private Spinner id_spn_dv_chinhanhkhuvuc, id_spn_dv_quanhuyen, id_spn_dv_phuongxa;
    private Button id_btn_ok,id_btn_dv_taive;
    private boolean IS_DANGKY_THANHCONG;
    private RadioButton id_rb_dv_yeucaulapdatnuoc, id_rb_dv_yeucaulapdatnuocnganhan;
    private TextView id_tv_dv_loaiyeucau,id_tv_title_maudon;
    private CustomEditText id_edt_dv_ten_khang, id_edt_dv_so_nha, id_edt_dv_dpho, id_edt_dv_email, id_edt_dv_sdt, id_edt_noidung;
    //
    String LoaiYC, TenKH, CMND, Email, SDT, SoNha, DuongPho, MaPhuong, MaKV, NoiDung, NgayTao;

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
        View v = inflater.inflate(R.layout.frag_tab_dichvu0_dky_cap_moi, container, false);
        initCommonView(v, this);
        initData();
        initComponent(v);
        addListener();
        setText();
       new GetDichVu0DanhMucDiaChinh_KhuVucTask().execute();
        new GetDichVu0DanhMucDiaChinh_QuanHuyenTask().execute();
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
        id_tv_title_maudon = (TextView)v.findViewById(R.id.id_tv_title_maudon);
        id_btn_dv_taive = (Button)v.findViewById(R.id.id_btn_dv_taive);
        id_tv_title_maudon.setVisibility(View.GONE);
        id_btn_dv_taive.setVisibility(View.GONE);
        //
        id_spn_dv_chinhanhkhuvuc = (Spinner) v.findViewById(R.id.id_spn_dv_chinhanhkhuvuc);
        id_spn_dv_quanhuyen = (Spinner) v.findViewById(R.id.id_spn_dv_quanhuyen);
        id_spn_dv_phuongxa = (Spinner) v.findViewById(R.id.id_spn_dv_phuongxa);
        //
        adapterKhuvuc = new ListViewKhuVucListItemAdapter(fpActivity, mArrItemKhuVuc);
        id_spn_dv_chinhanhkhuvuc.setAdapter(adapterKhuvuc);
        //
        adapterQuanHuyen = new ListViewQuanHuyenListItemAdapter(fpActivity, mArrItemQuanHuyen);
        id_spn_dv_quanhuyen.setAdapter(adapterQuanHuyen);
        //
        adapterPhuongXa = new ListViewPhuongXaListItemAdapter(fpActivity, mArrItemPhuongXa);
        id_spn_dv_phuongxa.setAdapter(adapterPhuongXa);
        //
        id_rb_dv_yeucaulapdatnuoc = (RadioButton) v.findViewById(R.id.id_rb_dv_yeucaulapdatnuoc);
        id_rb_dv_yeucaulapdatnuocnganhan = (RadioButton) v.findViewById(R.id.id_rb_dv_yeucaulapdatnuocnganhan);
        id_tv_dv_loaiyeucau = (TextView) v.findViewById(R.id.id_tv_dv_loaiyeucau);
        id_edt_dv_ten_khang = (CustomEditText) v.findViewById(R.id.id_edt_dv_ten_khang);
        id_edt_dv_so_nha = (CustomEditText) v.findViewById(R.id.id_edt_dv_so_nha);
        id_edt_dv_dpho = (CustomEditText) v.findViewById(R.id.id_edt_dv_dpho);
        id_edt_dv_email = (CustomEditText) v.findViewById(R.id.id_edt_dv_email);
        id_edt_dv_sdt = (CustomEditText) v.findViewById(R.id.id_edt_dv_sdt);
        id_edt_noidung = (CustomEditText) v.findViewById(R.id.id_edt_noidung);
        //
        id_btn_ok = (Button) v.findViewById(R.id.id_btn_close_dialog_bcsc_ng);
        // Hide Loại yêu cầu
        id_rb_dv_yeucaulapdatnuoc.setVisibility(View.GONE);
        id_rb_dv_yeucaulapdatnuocnganhan.setVisibility(View.GONE);
        id_tv_dv_loaiyeucau.setVisibility(View.GONE);
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
                CommonHelper.openFile(fpActivity,GlobalVariable.DON_LAP_DAT_URL);
                break;
            case R.id.id_btn_close_dialog_bcsc_ng:
                if (CommonHelper.isNetworkAvailable(fpActivity)) {
                    //
                        LoaiYC = GlobalVariable.CAP_MOI_NANG_DOI;
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
                    CommonHelper.showWarning(fpActivity, getString(R.string.nointernet));
                }

                break;
            case R.id.id_btn_cancel:
                fpActivity.onBackPressed();
                break;
            default:
                break;
        }
    }

    public void setText() {
        id_edt_dv_ten_khang.setText(GlobalVariable.KHACH_HANG.getTenKhachHang());
        id_edt_dv_so_nha.setText(GlobalVariable.KHACH_HANG.getSoNha());
        id_edt_dv_dpho.setText(GlobalVariable.KHACH_HANG.getDuongPho());
        id_edt_dv_email.setText(GlobalVariable.KHACH_HANG.getEmail());
        id_edt_dv_sdt.setText(GlobalVariable.KHACH_HANG.getDiDong());
        id_edt_noidung.setText("Nội dung");
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
            GetDichVu0DvYeuCausResponse getDichVu0DvYeuCausResponse = rs.getDichVu0DvYeuCausResponse(fpActivity, LoaiYC, TenKH, CMND, Email, SDT, SoNha, DuongPho, MaPhuong, MaKV, NoiDung, GlobalVariable.LOGIN_TOKEN_TYPE + " " + GlobalVariable.LOGIN_ACCESS_TOKEN,fpActivity);
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
                                fpActivity.onBackPressed();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(fpActivity);
                builder.setMessage(fpActivity.getString(R.string.common_guiyeucauthanhcong)).setPositiveButton(fpActivity.getString(R.string.tab_caidat_dangxuat_ok), dialogClickListener).show();

            } else {
                CommonHelper.showWarning(fpActivity, fpActivity.getString(R.string.common_guiyeucauthatbai));
            }
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

            mArrItemKhuVuc =  CommonHelper.readKhuVucObjs(fpActivity,GlobalVariable.KHU_VUC_FILE);
            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            disMissLoading();
            if(mArrItemKhuVuc!=null &&mArrItemKhuVuc.size()>0 ){
                //CommonHelper.showWarning(fpActivity,"Lay tu cache" );
            } else {
                //CommonHelper.showWarning(fpActivity,"lay tu Asert" );
                mArrItemKhuVuc =  CommonHelper.getArrayKhuVucObjFromAssetJsonFile(fpActivity, GlobalVariable.KHU_VUC_ASSETS);
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

            mArrItemQuanHuyen =  CommonHelper.readQuanHuyenCache(fpActivity,GlobalVariable.QUAN_HUYEN_FILE);
            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            disMissLoading();
            if(mArrItemQuanHuyen!=null && mArrItemQuanHuyen.size()>0 ){
                //CommonHelper.showWarning(fpActivity,"Lay tu cache" );
            } else {
                //CommonHelper.showWarning(fpActivity,"lay tu Asert" );
                mArrItemQuanHuyen =  CommonHelper.getArrayQuanHuyenObjFromAssetJsonFile(fpActivity, GlobalVariable.QUAN_HUYEN_ASSETS);
            }
            adapterQuanHuyen.refresh(mArrItemQuanHuyen);
        }
    }
}
