package com.huewaco.cskh.fragment;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.FragmentManager;
//import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;

import com.customview.edittext.CustomEditText;
import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.adapter.ListViewCustomerGroupAdapter;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.objects.KhachHangObj;
import com.huewaco.cskh.webservice.objects.GetDichVu1ThayDoiTTKHResponse;
import com.huewaco.cskh.webservice.objects.PostRegisterNotificationReceiveResponse;
import com.huewaco.cskh.webservice.processing.ResultFromWebservice;

import java.util.ArrayList;
import java.util.Objects;


public class FTabCDDichVu1ThayDoiThongTinKH extends FParent {
    protected String title;
    private CustomEditText id_edt_dtlienhe, id_edt_email;
    private String dienthoai, email;
    private Boolean IS_CHANGE;
    private Button id_btn_ok;
    private TextView id_tv_tenkh,id_tv_diachi, id_tv_idkh,id_tv_username;
    private boolean isEdit = false;
    private ListView id_lv_list_customergroup;
    private LinearLayout id_customergroup_title;
    private ListViewCustomerGroupAdapter adapter;
    private Button id_btn_registernotification;
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
        View v = inflater.inflate(R.layout.frag_tab_dichvu1_thaydoi_thongtin_kh, container, false);
        initCommonView(v, this);
        initData();
        initComponent(v);
        addListener();
        setText();
        setEdit(isEdit);
        return v;
    }
    private void setEdit(boolean isEdt){
        isEdit = isEdt;
        if(isEdit){
            setEditextListener();
            id_btn_ok.setVisibility(View.VISIBLE);
            id_btn_right.setBackgroundResource(R.drawable.btn_save);


            id_edt_dtlienhe.setEnabled(true);
            id_edt_dtlienhe.setClickable(true);
            id_edt_dtlienhe.setFocusableInTouchMode(true);

//            id_edt_email.setFocusable(true);
            id_edt_email.setEnabled(true);
            id_edt_email.setClickable(true);
            id_edt_email.setFocusableInTouchMode(true);


            CommonHelper.showKeyBoardForce(fpActivity);

            id_edt_dtlienhe.setFocusable(true);
            id_edt_dtlienhe.requestFocus();
        }else{
            setNotEditextListener();
            id_btn_ok.setVisibility(View.GONE);
            id_btn_right.setBackgroundResource(android.R.drawable.ic_menu_edit);

            id_edt_dtlienhe.setEnabled(false);
            id_edt_email.setEnabled(false);
        }
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
        id_btn_registernotification =(Button) v.findViewById(R.id.id_btn_registernotification);
        //
        id_edt_dtlienhe = (CustomEditText) v.findViewById(R.id.id_edt_dtlienhe);
        id_edt_email = (CustomEditText) v.findViewById(R.id.id_edt_email);
        id_btn_ok = (Button) v.findViewById(R.id.id_btn_close_dialog_bcsc_ng);
        id_tv_tenkh = (TextView)v.findViewById(R.id.id_tv_tenkh);
        id_tv_idkh = (TextView)v.findViewById(R.id.id_tv_idkh);
        id_tv_diachi = (TextView)v.findViewById(R.id.id_tv_diachi);
        id_tv_username = (TextView)v.findViewById(R.id.id_tv_username);
//        id_edt_email.setBackground();
        id_edt_email.setBackgroundResource(R.drawable.border_bg_user);
        id_edt_dtlienhe.setBackgroundResource(R.drawable.border_bg_user);
        id_lv_list_customergroup = (ListView) v.findViewById(R.id.id_lv_list_customergroup);
        id_customergroup_title = (LinearLayout) v.findViewById(R.id.id_customergroup_title);
        id_lv_list_customergroup.setClickable(true);
        id_lv_list_customergroup.setFocusable(false);
        id_lv_list_customergroup.setFocusableInTouchMode(false);
        if (GlobalVariable.mArrKHang.size() > 0) {
            id_customergroup_title.setVisibility(View.VISIBLE);
            id_lv_list_customergroup.setVisibility(View.VISIBLE);
            adapter = new ListViewCustomerGroupAdapter(fpActivity, GlobalVariable.mArrKHang);
            id_lv_list_customergroup.setAdapter(adapter);
            id_lv_list_customergroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    GlobalVariable.KHACH_HANG_CURRENT = GlobalVariable.mArrKHang.get(i);
                    setText();
                    ((ListViewCustomerGroupAdapter)adapterView.getAdapter()).refresh(GlobalVariable.mArrKHang);


                }
            });
        }else{
            id_customergroup_title.setVisibility(View.GONE);
            id_lv_list_customergroup.setVisibility(View.GONE);
            id_btn_registernotification.setVisibility(View.GONE);
        }
    }

    private void initData() {

    }
    private boolean isValidInfo() {
        boolean chk = true;
        String dtlienhe = id_edt_dtlienhe.getText().toString().trim();
        String email = id_edt_email.getText().toString().trim();
        if (!CommonHelper.checkValidString(dtlienhe)) {
            chk = false;
            CommonHelper.showWarning(fpActivity, getString(R.string.thay_doi_ttkh_sdt));
        } else if (CommonHelper.checkValidString(email) && !CommonHelper.isEmailValidator(email)) {

                chk = false;
                CommonHelper.showWarning(fpActivity, getString(R.string.thay_doi_ttkh_email));


        }
        return chk;
    }
    private void setNotEditextListener(){
        final Drawable right = this.getResources().getDrawable(android.R.drawable.ic_delete);
        right.setBounds(0, 0, right.getIntrinsicHeight(), right.getIntrinsicHeight());
        setEditTextListener(id_edt_dtlienhe, null, null);
        setEditTextListener(id_edt_email, null, null);
    }
    private void setEditextListener(){
        final Drawable right = this.getResources().getDrawable(android.R.drawable.ic_delete);
        right.setBounds(0, 0, right.getIntrinsicHeight(), right.getIntrinsicHeight());
        //setEditTextListener(id_edt_dtlienhe, null, right);
        //setEditTextListener(id_edt_email, null, right);
        setEditTextListener(id_edt_dtlienhe, null, null);
        setEditTextListener(id_edt_email, null, null);

        id_edt_email.setBackgroundResource(R.drawable.border_bg_edit);
        id_edt_dtlienhe.setBackgroundResource(R.drawable.border_bg_edit);
    }
    @Override
    protected void addListener() {
        setNotEditextListener();
        //
        id_btn_ok.setOnClickListener(this);
        id_btn_registernotification.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dienthoai = Objects.requireNonNull(id_edt_dtlienhe.getText()).toString();
        email = id_edt_email.getText().toString();
        switch (v.getId()) {
            case R.id.id_btn_close_dialog_bcsc_ng:
                if(CommonHelper.isNetworkAvailable(fpActivity)){
                    if(isValidInfo()) {
                        new GetDvThayDoiTTKHTask().execute();
                    }
                }else{
                    CommonHelper.showWarning(fpActivity,getString(R.string.nointernet));
                }
                break;
            case R.id.id_btn_right:
                if(isEdit){
                    id_btn_ok.performClick();
                }else{
                    setEdit(true);
                }


                break;
            case R.id.id_btn_left:
//                CommonHelper.hideKeyBoardWithEditext(fpActivity.getApplicationContext(),id_edt_dtlienhe);
//                CommonHelper.hideKeyBoardWithEditext(fpActivity.getApplicationContext(),id_edt_email);
                id_edt_dtlienhe.setEnabled(false);
                id_edt_email.setEnabled(false);
                fpActivity.onBackPressed();
                break;
            case R.id.id_btn_registernotification:
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                if(CommonHelper.isNetworkAvailable(fpActivity)){
                                    new PostRegisterNotificationReceiveTask().execute();
                                }else{
                                    CommonHelper.showWarning(fpActivity,getString(R.string.nointernet));
                                }
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
                builder.setMessage("Bạn đang chọn Đăng ký nhận thông báo cho tất cả đồng hồ cùng CQTT").setPositiveButton("Tiếp tục", dialogClickListener)
                        .setNegativeButton("Huỷ", dialogClickListener).show();


                break;
            default:
                break;
        }

    }

    public void setText() {
        id_tv_username.setText(GlobalVariable.KHACH_HANG_CURRENT.getUsername());
        id_tv_idkh.setText(GlobalVariable.KHACH_HANG_CURRENT.getIdKh());
        id_tv_tenkh.setText(GlobalVariable.KHACH_HANG_CURRENT.getTenKhachHang());
        id_edt_dtlienhe.setText(GlobalVariable.KHACH_HANG_CURRENT.getDiDong());
        id_edt_email.setText(GlobalVariable.KHACH_HANG_CURRENT.getEmail());
        id_tv_diachi.setText(GlobalVariable.KHACH_HANG_CURRENT.getSoNha() +" "+ GlobalVariable.KHACH_HANG_CURRENT.getDuongPho()+", "+ GlobalVariable.KHACH_HANG_CURRENT.getPhuongXa()+", "+GlobalVariable.KHACH_HANG_CURRENT.getQuanHuyen());
    }

    // toandtb
    public class GetDvThayDoiTTKHTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        public Void doInBackground(String... params) {
            ResultFromWebservice rs = new ResultFromWebservice();
            GetDichVu1ThayDoiTTKHResponse getDvThayDoiTTKH = rs.getDvThayDoiTTKHResponse(fpActivity,dienthoai,email,GlobalVariable.KHACH_HANG_CURRENT.getMa_khang() ,GlobalVariable.LOGIN_TOKEN_TYPE+" "+GlobalVariable.LOGIN_ACCESS_TOKEN,fpActivity);
            IS_CHANGE = Boolean.parseBoolean(getDvThayDoiTTKH.getReturnString());
            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            if(IS_CHANGE){
                GlobalVariable.KHACH_HANG_CURRENT.setEmail(email);
                GlobalVariable.KHACH_HANG_CURRENT.setDiDong(dienthoai);
                for(int i = 0; i < GlobalVariable.mArrKHang.size();i++){
                    KhachHangObj obj = GlobalVariable.mArrKHang.get(i);
                    if(obj.getIdKh().equals(GlobalVariable.KHACH_HANG_CURRENT.getIdKh())){
                        GlobalVariable.mArrKHang.set(i,GlobalVariable.KHACH_HANG_CURRENT);
                        break;
                    }
                }

                ArrayList<KhachHangObj> arr = new ArrayList<>();
                arr.add(GlobalVariable.KHACH_HANG);
                arr.addAll(GlobalVariable.mArrKHang);
                CommonHelper.writeKhachHangObjects(fpActivity,arr,GlobalVariable.KHACH_HANG_FILE);
                changeTextThongTinKHAll();
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //fpActivity.onBackPressed();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(fpActivity);
                builder.setTitle(fpActivity.getResources().getString(R.string.app_name)).setMessage(fpActivity.getString(R.string.tab_dichvu_thaydoithongtin_doithongtin_thanhcong)).setPositiveButton(fpActivity.getString(R.string.tab_caidat_dangxuat_ok), dialogClickListener).show();


                setEdit(false);
                //CommonHelper.hiddenKeyBoardOk(fpActivity);


            } else {
                CommonHelper.showWarning(fpActivity,fpActivity.getString(R.string.tab_dichvu_thaydoithongtin_doithongtin_khongthanhcong) );
            }
        }
    }
    //start change color
    public void changeTextThongTinKHAll() {
        FParent fchangeColor = null;
        int totalTab = fpActivity.mTabsAdapter.getCount();
        for (int i = 0; i < totalTab; i++) {
            fchangeColor = (FParent) fpActivity.mTabsAdapter.getRegisteredFragment(i);
            if (fchangeColor != null) {
                FParent fp = (FParent) fchangeColor;
                changeTextEachChild(fp);

            }
        }
    }

    public void changeTextEachChild(FParent fp) {
        int childCount = fp.getChildFragmentManager().getBackStackEntryCount();
        fp.changeText();
        if (childCount > 0) {
            FragmentManager childFragmentManager = fp.getChildFragmentManager();
            FParent childFragment = (FParent) childFragmentManager.getFragments().get(0);
            changeTextEachChild((FParent) childFragment);
        }
    }
    public class PostRegisterNotificationReceiveTask extends AsyncTask<String, Void, PostRegisterNotificationReceiveResponse> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        public PostRegisterNotificationReceiveResponse doInBackground(String... params) {
            ResultFromWebservice rs = new ResultFromWebservice();
            PostRegisterNotificationReceiveResponse result = rs.getRegisterNotificationReceiveResponse(fpActivity, GlobalVariable.LOGIN_TOKEN_TYPE,GlobalVariable.LOGIN_ACCESS_TOKEN, GlobalVariable.DEVICE_TOKEN, GlobalVariable.DEVICE_TYPE, adapter.getIdkhList(),fpActivity);

            return result;
        }

        @Override
        public void onPostExecute(PostRegisterNotificationReceiveResponse result) {

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(fpActivity);
            builder.setTitle(fpActivity.getResources().getString(R.string.app_name)).setMessage(result.getMessage()).setPositiveButton("Ok", dialogClickListener)
                    .show();

        }
    }
}
