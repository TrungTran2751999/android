/**
 * Created by: Kiet.Duong
 * Dec-29-2014
 **/
package com.huewaco.cskh.webservice.objects;


import android.content.Context;
import android.util.Log;

import com.huewaco.cskh.activity.AParent;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.objects.HDonTNuocListItemObj;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetTraCuu5TCuHDonResponse extends CommonResponse {

    //
    private String IDKH;
    private String MaDb;
    private String Nam;
    private String  Thang;
    private String ChiSoDau;
    private String ChiSoCuoi;
    private String  KhoiLuongTieuThuTinhhTien;
    private String M3Sh1;
    private String M3Sh2;
    private String  M3Sh3;
    private String M3Sh4;
    private String M3Sn;
    private String  M3Kd;
    private String M3Nd;
    private String M3Sh1Cu;
    private String  M3Sh2Cu;
    private String M3Sh3Cu;
    private String M3Sh4Cu;
    private String  M3SnCu;
    private String M3KdCu;
    private String M3NdCu;
    private String  TienNuoc;
    private String  TienPhiTn;
    private String TienPhiMt;
    private String TienThue;
    private String  TongTien;
    private String DaTra;

    ArrayList<HDonTNuocListItemObj> mArrItem;

    public GetTraCuu5TCuHDonResponse(Context context, String result, AParent currentActivity) {
        super(context,result,currentActivity);
        if (CommonHelper.checkValidString(result) && (!this.hasError())) {
            parseStringToJson((result));
        }
    }

    private ArrayList<HDonTNuocListItemObj> parseStringToJson(String str) {

        //
        JSONObject obj = null;
        mArrItem = new ArrayList<HDonTNuocListItemObj>();
        try {
            obj = new JSONObject(str);
        } catch (Exception e1) {
            System.out.print(e1.getMessage());
        }
        if (obj != null)  {
                HDonTNuocListItemObj tcHDonTNuocListItemObj = new HDonTNuocListItemObj();
                try {

                    if (obj != null) {
                        IDKH = obj.getString("IDKH");
                        MaDb = obj.getString("MaDb");
                        Nam = obj.getString("Nam");
                        Thang = obj.getString("Thang");
                        ChiSoDau = obj.getString("ChiSoDau");
                        ChiSoCuoi = obj.getString("ChiSoCuoi");
                        KhoiLuongTieuThuTinhhTien = obj.getString("KhoiLuongTieuThuTinhhTien");
                        M3Sh1 = obj.getString("StrM3Sh1");
                        M3Sh2 = obj.getString("M3Sh2");
                        M3Sh3 = obj.getString("M3Sh3");
                        M3Sh4 = obj.getString("M3Sh4");
                        //
                        M3Sn = obj.getString("M3Sn");
                        M3Kd = obj.getString("M3Kd");
                        M3Nd = obj.getString("M3Nd");
                        //
                        M3Sh1Cu = obj.getString("M3Sh1Cu");
                        M3Sh2Cu = obj.getString("M3Sh2Cu");
                        M3Sh3Cu = obj.getString("M3Sh3Cu");
                        M3Sh4Cu = obj.getString("M3Sh4Cu");
                        M3SnCu = obj.getString("M3SnCu");
                        //
                        M3KdCu = obj.getString("M3KdCu");
                        M3NdCu = obj.getString("M3NdCu");
                        //
                        TienNuoc = obj.getString("StrTienNuoc");
                        TienPhiTn = obj.getString("StrTienPhiTn");
                        TienPhiMt = obj.getString("StrTienPhiMt");
                        TienThue = obj.getString("StrTienThue");
                        TongTien = obj.getString("StrTongTien");
                        DaTra = obj.getString("DaTra");
                        if (CommonHelper.checkValidString(IDKH)) {
                            tcHDonTNuocListItemObj.setIDKH(IDKH.toString().trim());
                        }
                        if (CommonHelper.checkValidString(MaDb)) {
                            tcHDonTNuocListItemObj.setMaDb(MaDb.toString().trim());
                        }
                        if (CommonHelper.checkValidString(Nam)) {
                            tcHDonTNuocListItemObj.setNam(Nam.toString().trim());
                        }
                        if (CommonHelper.checkValidString(Thang)) {
                            tcHDonTNuocListItemObj.setThang(Thang.toString().trim());
                        }
                        if (CommonHelper.checkValidString(ChiSoDau)) {
                            tcHDonTNuocListItemObj.setChiSoDau(ChiSoDau.toString().trim());
                        }
                        if (CommonHelper.checkValidString(ChiSoCuoi)) {
                            tcHDonTNuocListItemObj.setChiSoCuoi(ChiSoCuoi.toString().trim());
                        }
                        if (CommonHelper.checkValidString(KhoiLuongTieuThuTinhhTien)) {
                            tcHDonTNuocListItemObj.setKhoiLuongTieuThuTinhhTien(KhoiLuongTieuThuTinhhTien.toString().trim());
                        }
                        if (CommonHelper.checkValidString(M3Sh1)) {
                            tcHDonTNuocListItemObj.setM3Sh1(M3Sh1.toString().trim());
                        }
                        if (CommonHelper.checkValidString(M3Sh2)) {
                            tcHDonTNuocListItemObj.setM3Sh2(M3Sh2.toString().trim());
                        }
                        if (CommonHelper.checkValidString(M3Sh3)) {
                            tcHDonTNuocListItemObj.setM3Sh3(M3Sh3.toString().trim());
                        }
                        if (CommonHelper.checkValidString(M3Sh4)) {
                            tcHDonTNuocListItemObj.setM3Sh4(M3Sh4.toString().trim());
                        }
                        //
                        if (CommonHelper.checkValidString(M3SnCu)) {
                            tcHDonTNuocListItemObj.setM3Sn(M3Sn.toString().trim());
                        }
                        if (CommonHelper.checkValidString(M3Kd)) {
                            tcHDonTNuocListItemObj.setM3Kd(M3Kd.toString().trim());
                        }
                        if (CommonHelper.checkValidString(M3Nd)) {
                            tcHDonTNuocListItemObj.setM3Nd(M3Nd.toString().trim());
                        }
                        //
                        if (CommonHelper.checkValidString(M3Sh1Cu)) {
                            tcHDonTNuocListItemObj.setM3Sh1Cu(M3Sh1Cu.toString().trim());
                        }
                        if (CommonHelper.checkValidString(M3Sh2Cu)) {
                            tcHDonTNuocListItemObj.setM3Sh2Cu(M3Sh2Cu.toString().trim());
                        }
                        if (CommonHelper.checkValidString(M3Sh3Cu)) {
                            tcHDonTNuocListItemObj.setM3Sh3Cu(M3Sh3Cu.toString().trim());
                        }
                        if (CommonHelper.checkValidString(M3Sh4Cu)) {
                            tcHDonTNuocListItemObj.setM3Sh4Cu(M3Sh4Cu.toString().trim());
                        }
                        if (CommonHelper.checkValidString(M3SnCu)) {
                            tcHDonTNuocListItemObj.setM3SnCu(M3SnCu.toString().trim());
                        }
                        //
                        if (CommonHelper.checkValidString(M3KdCu)) {
                            tcHDonTNuocListItemObj.setM3KdCu(M3KdCu.toString().trim());
                        }
                        if (CommonHelper.checkValidString(M3NdCu)) {
                            tcHDonTNuocListItemObj.setM3NdCu(M3NdCu.toString().trim());
                        }
                        if (CommonHelper.checkValidString(TienNuoc)) {
                            tcHDonTNuocListItemObj.setTienNuoc(TienNuoc.toString().trim());
                        }
                        if (CommonHelper.checkValidString(TienPhiTn)) {
                            tcHDonTNuocListItemObj.setTienPhiTn(TienPhiTn.toString().trim());
                        }
                        if (CommonHelper.checkValidString(TienPhiMt)) {
                            tcHDonTNuocListItemObj.setTienPhiMt(TienPhiMt.toString().trim());
                        }

                        if (CommonHelper.checkValidString(TienThue)) {
                            tcHDonTNuocListItemObj.setTienThue(TienThue.toString().trim());
                        }
                        if (CommonHelper.checkValidString(TongTien)) {
                            tcHDonTNuocListItemObj.setTongTien(TongTien.toString().trim());
                        }
                        if (CommonHelper.checkValidString(DaTra)) {
                            tcHDonTNuocListItemObj.setDaTra(DaTra.toString().trim());
                        }
                        mArrItem.add(tcHDonTNuocListItemObj);
                    }
                } catch (Exception e) {
                    Log.e("abc", "Parse json thu " + "fail");
                }
        } else {
            Log.e("abc", "Get TC Thong Tin Diem Thu Fail");
        }

        return mArrItem;
    }

    public ArrayList<HDonTNuocListItemObj> getmArrItem() {
        return mArrItem;
    }
}
