package com.huewaco.cskh.webservice.objects;

import android.content.Context;
import android.util.Log;

import com.huewaco.cskh.activity.AParent;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.objects.KhachHangObj;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by diepthaibaotoan on 2/16/2017.
 */

public class GetUserInfoResponse extends CommonResponse {

    //
    String Username = "";
    String IdKh = "";
    String TenKhachHang = "";
    String SoNha = "";
    String DuongPho = "";
    String PhuongXa = "";
    String QuanHuyen = "";
    String Email = "";
    String DiDong = "";
    String MaDDK = "";
    String DiaChiLD = "";
    String MACQTT = "";
    Boolean IsInGroup = false;
    KhachHangObj mainCustomer = null;
    ArrayList<KhachHangObj> mArrCustomerGroup;

    public ArrayList<KhachHangObj> getmArrCustomerGroup() {
        return mArrCustomerGroup;
    }
    public KhachHangObj getMainCustomer() {
        return mainCustomer;
    }

    //
    public GetUserInfoResponse(Context context, String result, AParent currentActivity) {
        super(context,result,currentActivity);
        if (CommonHelper.checkValidString(result) && (!this.hasError())) {
            parseStringToJson((result));
        }
    }

    //
    private void parseStringToJson(String str) {
        JSONArray jsonArr = null;
        JSONObject obj = null;
        mArrCustomerGroup = new ArrayList<KhachHangObj>();
        try {
            obj = new JSONObject(str);
            mainCustomer = parseObject(obj);
        } catch (Exception e1) {
            System.out.print(e1.getMessage());
        }
        try {
           String group = obj.getString("CustomerGroup");
            if (CommonHelper.checkValidString(group)) {
                jsonArr = new JSONArray(group);
                if (jsonArr != null && jsonArr.length() > 0) {
                    for (int i = 0; i < jsonArr.length(); i++) {
                        obj = jsonArr.getJSONObject(i);
                        mArrCustomerGroup.add(parseObject(obj));
                    }
                }
            }

        } catch (Exception e1) {
            System.out.print(e1.getMessage());
        }
    }
    private KhachHangObj parseObject(JSONObject obj){
        KhachHangObj khachHangObj = new KhachHangObj();
        if (obj != null) {
            try {
                Username = obj.getString("Username");
                IdKh = obj.getString("IdKh");
                TenKhachHang = obj.getString("TenKhachHang");
                SoNha = obj.getString("SoNha");
                DuongPho = obj.getString("DuongPho");
                PhuongXa = obj.getString("PhuongXa");
                QuanHuyen = obj.getString("QuanHuyen");
                Email = obj.getString("Email");
                DiDong = obj.getString("DiDong");
                MaDDK = obj.getString("MaDDK");
                DiaChiLD = obj.getString("DiaChiLD");
                MACQTT = obj.getString("MACQTT");

                //
                if (CommonHelper.checkValidString(Username)) {
                    khachHangObj.setUsername(Username.toString().trim());
                }
                if (CommonHelper.checkValidString(IdKh)) {
                    khachHangObj.setMa_khang(IdKh.toString().trim());
                    khachHangObj.setIdKh(IdKh.toString().trim());
                }
                if (CommonHelper.checkValidString(TenKhachHang)) {
                    khachHangObj.setTenKhachHang(TenKhachHang.toString().trim());
                }
                if (CommonHelper.checkValidString(SoNha)) {
                    khachHangObj.setSoNha(SoNha.toString().trim());
                }
                if (CommonHelper.checkValidString(DuongPho)) {
                    khachHangObj.setDuongPho(DuongPho.toString().trim());
                }
                if (CommonHelper.checkValidString(PhuongXa)) {
                    khachHangObj.setPhuongXa(PhuongXa.toString().trim());
                }

                if (CommonHelper.checkValidString(QuanHuyen)) {
                    khachHangObj.setQuanHuyen(QuanHuyen.toString().trim());
                }
                if (CommonHelper.checkValidString(Email)) {
                    khachHangObj.setEmail(Email.toString().trim());
                }
                if (CommonHelper.checkValidString(DiDong)) {
                    khachHangObj.setDiDong(DiDong.toString().trim());
                }
                if (CommonHelper.checkValidString(MaDDK)) {
                    khachHangObj.setMaDDK(MaDDK.toString().trim());
                }
                if (CommonHelper.checkValidString(DiaChiLD)) {
                    khachHangObj.setDiaChiLD(DiaChiLD.toString().trim());
                }
                if (CommonHelper.checkValidString(MACQTT)) {
                    khachHangObj.setMACQTT(MACQTT.toString().trim());
                }
                try{
                    IsInGroup = obj.getBoolean("IsInGroup");
                }
                catch(Exception e2){}
                khachHangObj.setInGroup(IsInGroup);


            } catch (Exception e) {
                Log.e("abc", "Parse json thu fail");
            }

        } else {
            Log.e("abc", "Get UserInfo false");
        }
        return khachHangObj;
    }
}

