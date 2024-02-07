/**
 * Created by: Kiet.Duong
 * Dec-29-2014
 **/
package com.huewaco.cskh.webservice.objects;


import android.content.Context;
import android.util.Log;

import com.huewaco.cskh.activity.AParent;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.objects.LichGhiNuocListItemObj;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetTraCuu0LichGhiNuocResponse extends CommonResponse {

    private String ngayghinuoc;
    private String thang;
    private String nam;
    ArrayList<LichGhiNuocListItemObj> mArrItem;

    public GetTraCuu0LichGhiNuocResponse(Context context, String result, AParent currentActivity) {
        super(context,result,currentActivity);
        if (CommonHelper.checkValidString(result) && (!this.hasError())) {
            parseStringToJson((result));
        }
    }

    private ArrayList<LichGhiNuocListItemObj> parseStringToJson(String str) {
        JSONArray jsonArr = null;
        JSONObject obj = null;
        mArrItem = new ArrayList<LichGhiNuocListItemObj>();
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
                LichGhiNuocListItemObj tcThongTinDiemThuListItemObj = new LichGhiNuocListItemObj();
                try {
                    obj = jsonArr.getJSONObject(i);
                    if (obj != null) {
                        ngayghinuoc = obj.getString("NgayGhi");
                        thang = obj.getString("Thang");
                        nam = obj.getString("Nam");

                        if (CommonHelper.checkValidString(ngayghinuoc)) {
                            tcThongTinDiemThuListItemObj.setNgayghinuoc(ngayghinuoc.toString().trim());
                        }
                        if (CommonHelper.checkValidString(thang)) {
                            tcThongTinDiemThuListItemObj.setThang(thang.toString().trim());
                        }
                        if (CommonHelper.checkValidString(nam)) {
                            tcThongTinDiemThuListItemObj.setNam(nam.toString().trim());
                        }
                        tcThongTinDiemThuListItemObj.setIDKH(obj.getString("IdKH"));
                        mArrItem.add(tcThongTinDiemThuListItemObj);
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

   //


    public String getNgayghinuoc() {
        return ngayghinuoc;
    }

    public void setNgayghinuoc(String ngayghinuoc) {
        this.ngayghinuoc = ngayghinuoc;
    }

    public String getThang() {
        return thang;
    }

    public void setThang(String thang) {
        this.thang = thang;
    }

    public String getNam() {
        return nam;
    }

    public void setNam(String nam) {
        this.nam = nam;
    }

    public ArrayList<LichGhiNuocListItemObj> getmArrItem() {
        return mArrItem;
    }
}
