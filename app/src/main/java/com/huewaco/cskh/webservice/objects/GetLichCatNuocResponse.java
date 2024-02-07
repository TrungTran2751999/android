/**
 * Created by: Kiet.Duong
 * Dec-29-2014
 **/
package com.huewaco.cskh.webservice.objects;


import android.content.Context;
import android.util.Log;

import com.huewaco.cskh.activity.AParent;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.objects.LichCatNuocListItemObj;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetLichCatNuocResponse extends CommonResponse {
//    String ID = "";
//    String MaLoTrinh = "";
//    String MaKV = "";
    String NgayBatDau = "";
//    String GioBatDau = "";
//    String PhutBatDau = "";
    String NgayKetThuc = "";
//    String GioKetThuc = "";
//    String PhutKetThuc = "";
    String LyDo = "";
    ArrayList<LichCatNuocListItemObj> mArrItem;

    public GetLichCatNuocResponse(Context context, String result, AParent currentActivity) {
        super(context,result,currentActivity);
        if (CommonHelper.checkValidString(result) && (!this.hasError())) {
            parseStringToJson((result));
        }
    }


    private ArrayList<LichCatNuocListItemObj> parseStringToJson(String str) {
        JSONArray jsonArr = null;
        JSONObject obj = null;
        mArrItem = new ArrayList<LichCatNuocListItemObj>();
        try {
            jsonArr = new JSONArray(str);
            /*if (jsonArr != null && jsonArr.length() > 0) {
                obj = jsonArr.getJSONObject(0);
			}*/

        } catch (Exception e1) {
            System.out.print(e1.getMessage());
        }
        if (jsonArr != null && jsonArr.length() > 0) {
            for (int i = 0; i < jsonArr.length(); i++) {
                LichCatNuocListItemObj lichcatnuoc = new LichCatNuocListItemObj();
                try {
                    obj = jsonArr.getJSONObject(i);
                    if (obj != null) {
//                        ID = obj.getString("ID");
//                        MaLoTrinh = obj.getString("MaLoTrinh");
//                        MaKV = obj.getString("MaKV");
                        NgayBatDau = obj.getString("NgayGioBatDau");
//                        GioBatDau = obj.getString("GioBatDau");
//                        PhutBatDau = obj.getString("PhutBatDau");
                        NgayKetThuc = obj.getString("NgayGioKetThuc");
//                        GioKetThuc = obj.getString("GioKetThuc");
//                        PhutKetThuc = obj.getString("PhutKetThuc");
                        LyDo = obj.getString("LyDo");
//                        if (CommonHelper.checkValidString(ID)) {
//                            lichcatnuoc.setID(ID.toString().trim());
//                        }
//                        if (CommonHelper.checkValidString(MaLoTrinh)) {
//                            lichcatnuoc.setMaLoTrinh(MaLoTrinh.toString().trim());
//                        }
//                        if (CommonHelper.checkValidString(MaKV)) {
//                            lichcatnuoc.setMaKV(MaKV.toString().trim());
//                        }
                        if (CommonHelper.checkValidString(NgayBatDau)) {
                            lichcatnuoc.setNgayBatDau(NgayBatDau.toString().trim());
                        }
//                        if (CommonHelper.checkValidString(GioBatDau)) {
//                            lichcatnuoc.setGioBatDau(GioBatDau.toString().trim());
//                        }
//                        if (CommonHelper.checkValidString(PhutBatDau)) {
//                            lichcatnuoc.setPhutBatDau(PhutBatDau.toString().trim());
//                        }
                        if (CommonHelper.checkValidString(NgayKetThuc)) {
                            lichcatnuoc.setNgayKetThuc(NgayKetThuc.toString().trim());
                        }
//                        if (CommonHelper.checkValidString(GioKetThuc)) {
//                            lichcatnuoc.setGioKetThuc(GioKetThuc.toString().trim());
//                        }
//                        if (CommonHelper.checkValidString(PhutKetThuc)) {
//                            lichcatnuoc.setPhutKetThuc(PhutKetThuc.toString().trim());
//                        }
                        if (CommonHelper.checkValidString(LyDo)) {
                            lichcatnuoc.setLyDo(LyDo.toString().trim());
                        }
                        mArrItem.add(lichcatnuoc);
                    }
                } catch (Exception e) {
                    Log.e("abc", "Parse json thu " + i + "fail");
                }
            }
        } else {
            Log.e("abc", "Get Lich Cat Nuoc Fail");
        }

        return mArrItem;
    }

    public String getNgayBatDau() {
        return NgayBatDau;
    }

    public ArrayList<LichCatNuocListItemObj> getmArrItem() {
        return mArrItem;
    }

//    public String getID() {
//        return ID;
//    }
//
//    public String getMaLoTrinh() {
//        return MaLoTrinh;
//    }
//
//    public String getMaKV() {
//        return MaKV;
//    }
//
//    public String getGioBatDau() {
//        return GioBatDau;
//    }
//
//    public String getPhutBatDau() {
//        return PhutBatDau;
//    }

    public String getNgayKetThuc() {
        return NgayKetThuc;
    }

//    public String getGioKetThuc() {
//        return GioKetThuc;
//    }
//
//    public String getPhutKetThuc() {
//        return PhutKetThuc;
//    }

    public String getLyDo() {
        return LyDo;
    }
}
