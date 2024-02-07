/**
 * Created by: Kiet.Duong
 * Dec-29-2014
 **/
package com.huewaco.cskh.webservice.objects;


import android.content.Context;
import android.util.Log;

import com.huewaco.cskh.activity.AParent;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.objects.TToanTienNuocListItemObj;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetTraCuu4ThongTinThanhToanTienNuocResponse extends CommonResponse {

    //
    private String Nam;
    private String Thang;
    private String M3TinhTien;
    private String TongTien;
    private String DaTra;

    ArrayList<TToanTienNuocListItemObj> mArrItem;

    public GetTraCuu4ThongTinThanhToanTienNuocResponse(Context context, String result, AParent currentActivity) {
        super(context,result,currentActivity);
        if (CommonHelper.checkValidString(result) && (!this.hasError())) {
            parseStringToJson((result));
        }
    }

    private ArrayList<TToanTienNuocListItemObj> parseStringToJson(String str) {
        JSONArray jsonArr = null;
        JSONObject obj = null;
        mArrItem = new ArrayList<TToanTienNuocListItemObj>();
        try {
            jsonArr = new JSONArray(str);

        } catch (Exception e1) {
            System.out.print(e1.getMessage());
        }
        if (jsonArr != null && jsonArr.length() > 0) {
            for (int i = 0; i < jsonArr.length(); i++) {
                TToanTienNuocListItemObj ttoanTienNuocListItemObj = new TToanTienNuocListItemObj();
                try {
                    obj = jsonArr.getJSONObject(i);
                    if (obj != null) {
                        Nam = obj.getString("Nam");
                        Thang = obj.getString("Thang");
                        M3TinhTien = obj.getString("StrM3TinhTien");
                        TongTien = obj.getString("StrTongTien");
                        DaTra = obj.getString("DaTra");

                        if (CommonHelper.checkValidString(Nam)) {
                            ttoanTienNuocListItemObj.setNam(Nam.toString().trim());
                        }
                        if (CommonHelper.checkValidString(Thang)) {
                            ttoanTienNuocListItemObj.setThang(Thang.toString().trim());
                        }
                        if (CommonHelper.checkValidString(M3TinhTien)) {
                            ttoanTienNuocListItemObj.setM3TinhTien(M3TinhTien.toString().trim());
                        }
                        if (CommonHelper.checkValidString(TongTien)) {
                            ttoanTienNuocListItemObj.setTongTien(TongTien.toString().trim());
                        }
                        if (CommonHelper.checkValidString(DaTra)) {
                            ttoanTienNuocListItemObj.setDaTra(DaTra.toString().trim());
                        }
                        mArrItem.add(ttoanTienNuocListItemObj);
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



    public ArrayList<TToanTienNuocListItemObj> getmArrItem() {
        return mArrItem;
    }
}
