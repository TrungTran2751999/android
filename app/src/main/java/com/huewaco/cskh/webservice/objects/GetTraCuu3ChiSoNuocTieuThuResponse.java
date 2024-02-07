/**
 * Created by: Kiet.Duong
 * Dec-29-2014
 **/
package com.huewaco.cskh.webservice.objects;


import android.content.Context;
import android.util.Log;

import com.huewaco.cskh.activity.AParent;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.objects.CSTieuThuListItemObj;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetTraCuu3ChiSoNuocTieuThuResponse extends CommonResponse {

    private String Nam;
    private String Thang;
    private String ChiSoDau;
    private String ChiSoCuoi;
    private String M3TinhTien;
    private String M3TieuThuCungThangNamTruoc;

    ArrayList<CSTieuThuListItemObj> mArrItem;

    public GetTraCuu3ChiSoNuocTieuThuResponse(Context context, String result, AParent currentActivity) {
        super(context,result,currentActivity);
        if (CommonHelper.checkValidString(result) && (!this.hasError())) {
            parseStringToJson((result));
        }
    }

    private ArrayList<CSTieuThuListItemObj> parseStringToJson(String str) {
        JSONArray jsonArr = null;
        JSONObject obj = null;
        mArrItem = new ArrayList<CSTieuThuListItemObj>();
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
                CSTieuThuListItemObj csTieuThuListItemObj = new CSTieuThuListItemObj();
                try {
                    obj = jsonArr.getJSONObject(i);
                    if (obj != null) {
                        Nam = obj.getString("Nam");
                        Thang = obj.getString("Thang");
                        ChiSoDau = obj.getString("ChiSoDau");
                        ChiSoCuoi = obj.getString("ChiSoCuoi");
                        M3TinhTien = obj.getString("M3TinhTien");
                        M3TieuThuCungThangNamTruoc = obj.getString("M3TieuThuCungThangNamTruoc");

                        if (CommonHelper.checkValidString(Nam)) {
                            csTieuThuListItemObj.setNam(Nam.toString().trim());
                        }
                        if (CommonHelper.checkValidString(Thang)) {
                            csTieuThuListItemObj.setThang(Thang.toString().trim());
                        }
                        if (CommonHelper.checkValidString(ChiSoDau)) {
                            csTieuThuListItemObj.setChiSoDau(ChiSoDau.toString().trim());
                        }
                        if (CommonHelper.checkValidString(ChiSoCuoi)) {
                            csTieuThuListItemObj.setChiSoCuoi(ChiSoCuoi.toString().trim());
                        }
                        if (CommonHelper.checkValidString(M3TinhTien)) {
                            csTieuThuListItemObj.setM3TinhTien(M3TinhTien.toString().trim());
                        }
                        if (CommonHelper.checkValidString(M3TieuThuCungThangNamTruoc)) {
                            csTieuThuListItemObj.setM3TieuThuCungThangNamTruoc(M3TieuThuCungThangNamTruoc.toString().trim());
                        }
                        mArrItem.add(csTieuThuListItemObj);
                    }
                } catch (Exception e) {
                    Log.e("abc", "Parse json thu " + i + "fail");
                }
            }
        } else {
            Log.e("abc", "Get TC Thong Tin Diem Thu Fail");
        }

        return mArrItem;
    }

    public ArrayList<CSTieuThuListItemObj> getmArrItem() {
        return mArrItem;
    }
}
