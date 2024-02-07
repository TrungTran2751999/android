package com.huewaco.cskh.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.customview.edittext.CustomEditText;
import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.adapter.ListViewCauHoiTraLoiAdapter;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.objects.DvCauHoisListItemObj;
import com.huewaco.cskh.webservice.objects.GetDichVu3DvCauHoisResponse;
import com.huewaco.cskh.webservice.objects.GetDichVu3DvCauHois_PostResponse;
import com.huewaco.cskh.webservice.processing.ResultFromWebservice;

import java.util.ArrayList;


public class FTabDichVu3HoiTraLoi extends FParent implements AdapterView.OnItemClickListener{
    protected String title;
    private ListView id_lv_list_cauhoi;
    private ListViewCauHoiTraLoiAdapter adapter;
    private ArrayList<DvCauHoisListItemObj> mArrDvCauHoisList  = new ArrayList<>();;
    //
    private String TieuDe, NoiDung;
    boolean IS_SUCCESS;
    private Button id_btn_ok;
    private CustomEditText id_edt_cauhoi_tieude, id_edt_cauhoi_noidung;
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
        View v = inflater.inflate(R.layout.frag_tab_dichvu3_hoitraloi, container, false);
        initCommonView(v, this);
        initData();
        initComponent(v);
        addListener();
        setText();
        if (CommonHelper.isNetworkAvailable(fpActivity)) {
            new GetDichVu3HoiTraLoiTask(fpActivity).execute();
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
        id_tv_title.setText(title);
        //
        id_edt_cauhoi_tieude = (CustomEditText) v.findViewById(R.id.id_edt_cauhoi_tieude);
        id_edt_cauhoi_noidung = (CustomEditText) v.findViewById(R.id.id_edt_cauhoi_noidung);
        id_btn_ok = (Button) v.findViewById(R.id.id_btn_close_dialog_bcsc_ng);
        //
        id_lv_list_cauhoi = (ListView) v.findViewById(R.id.id_lv_list_cauhoi);
        adapter = new ListViewCauHoiTraLoiAdapter(fpActivity, mArrDvCauHoisList);
        id_lv_list_cauhoi.setAdapter(adapter);
    }

    private void initData() {

    }

    @Override
    protected void addListener() {
        id_btn_ok.setOnClickListener(this);
        id_lv_list_cauhoi.setOnItemClickListener(this);
        //
        final Drawable right = this.getResources().getDrawable(android.R.drawable.ic_delete);
        right.setBounds(0, 0, right.getIntrinsicHeight(), right.getIntrinsicHeight());
        setEditTextListener(id_edt_cauhoi_tieude, null, right);
        setEditTextListener(id_edt_cauhoi_noidung, null, right);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_close_dialog_bcsc_ng:
                    if(CommonHelper.isNetworkAvailable(fpActivity)){
                       TieuDe =  id_edt_cauhoi_tieude.getText().toString();
                       NoiDung =  id_edt_cauhoi_noidung.getText().toString();
                        if(TieuDe.length()>10 && NoiDung.length()>10){
                            new GetDichVu3HoiTraLoi_PostTask().execute();
                        }
                     else if(TieuDe.length()<=10){
                            CommonHelper.showWarning(fpActivity,getString(R.string.tab_tracuu_tieude_ngan));
                        }
                        else if(NoiDung.length()<=10){
                            CommonHelper.showWarning(fpActivity,getString(R.string.tab_tracuu_noidung_ngan));
                        }
                    }else{
                        CommonHelper.showWarning(fpActivity,getString(R.string.nointernet));
                    }
                break;
            default:
                break;
        }

    }

    public void setText() {
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if(mArrDvCauHoisList != null && mArrDvCauHoisList.size()>0){
            FTabDichVu3HoiTraLoiDetail frg = new FTabDichVu3HoiTraLoiDetail();
            frg.title = this.title;
            String TieuDe= "";
            String NoiDung= "";
            String TraLoi= "";
            DvCauHoisListItemObj obj = mArrDvCauHoisList.get(position);
            if(obj != null){
                if(CommonHelper.checkValidString(obj.getTieuDe())){
                    TieuDe = obj.getTieuDe();
                }
                if(CommonHelper.checkValidString(obj.getNoiDung())){
                    NoiDung = obj.getNoiDung();
                }
                if(CommonHelper.checkValidString(obj.getTraLoi())){
                    TraLoi = obj.getTraLoi();
                }
            }
            frg.TieuDe = TieuDe;
            frg.NoiDung = NoiDung;
            frg.TraLoi = TraLoi;
            FragmentTransaction transaction2 = getChildFragmentManager().beginTransaction();

            if (GlobalVariable.IS_ANIMATION) {
                transaction2.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            transaction2.addToBackStack(null);
            transaction2.replace(R.id.id_fr_tab_hoi_tra_loi, frg).commit();
        }
    }

    // toandtb
    public class GetDichVu3HoiTraLoiTask extends AsyncTask<String, Void, Void> {
        boolean isError = false;
        Context context;

        public GetDichVu3HoiTraLoiTask(Context context) {
            this.context = context;
            showLoading();
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        public Void doInBackground(String... params) {
            ResultFromWebservice rs = new ResultFromWebservice();
            GetDichVu3DvCauHoisResponse getDichVu3DvCauHoisResponse = rs.getDichVu3DvCauHois(fpActivity, GlobalVariable.LOGIN_TOKEN_TYPE, GlobalVariable.LOGIN_ACCESS_TOKEN,fpActivity);
            if (getDichVu3DvCauHoisResponse != null && getDichVu3DvCauHoisResponse.getmArrItem() != null) {

                mArrDvCauHoisList.clear();
                mArrDvCauHoisList.addAll(getDichVu3DvCauHoisResponse.getmArrItem());
            }
            isError = getDichVu3DvCauHoisResponse.hasError();
            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            if (isError) {
                Log.d("abc", "" + "---------------: " + isError);
                fpActivity.startDangNhap4();
            } else {
                disMissLoading();
                adapter.refresh(mArrDvCauHoisList);
            }
        }
    }

    // toandtb
    public class GetDichVu3HoiTraLoi_PostTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        public Void doInBackground(String... params) {
            ResultFromWebservice rs = new ResultFromWebservice();
            GetDichVu3DvCauHois_PostResponse getDichVu3DvCauHois_PostResponse   = rs.getDichVu3DvCauHois_PostResponse(fpActivity, TieuDe, NoiDung,GlobalVariable.LOGIN_TOKEN_TYPE+" "+ GlobalVariable.LOGIN_ACCESS_TOKEN,fpActivity);
            IS_SUCCESS  = Boolean.parseBoolean(getDichVu3DvCauHois_PostResponse.getReturnString());
            return null;
        }

        @Override
        public void onPostExecute(Void result) {

            if(IS_SUCCESS){
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                fpActivity.onBackPressed();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(fpActivity);
                builder.setMessage(fpActivity.getString(R.string.tab_dichvu_cauhoi_thanhcong)).setPositiveButton(fpActivity.getString(R.string.tab_caidat_dangxuat_ok), dialogClickListener).show();

            } else {
                CommonHelper.showWarning(fpActivity,fpActivity.getString(R.string.tab_dichvu_cauhoi_khongthanhcong) );
            }
        }
    }
}
