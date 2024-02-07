/**
 * Created by: Kiet.Duong
 * Dec-29-2014
 **/
package com.huewaco.cskh.webservice.objects;


import android.content.Context;
import android.util.Log;

import com.huewaco.cskh.activity.AParent;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.objects.DiemThuTienListItemObj;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetTraCuu1ThongTinDiemThuResponse extends CommonResponse {

    String ID = "";
    String TenQuayThu = "";
    String DiaChi = "";
    String GhiChu = "";
    String MaKV = "";
    String KinhDo = "";
    String ViDo = "";
    ArrayList<DiemThuTienListItemObj> mArrItem;

    public GetTraCuu1ThongTinDiemThuResponse(Context context, String result, AParent currentActivity) {
        super(context,result,currentActivity);
        if (CommonHelper.checkValidString(result) && (!this.hasError())) {
            parseStringToJson((result));
        }
    }

    private ArrayList<DiemThuTienListItemObj> parseStringToJson(String str) {
        JSONArray jsonArr = null;
        JSONObject obj = null;
        mArrItem = new ArrayList<DiemThuTienListItemObj>();
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
                DiemThuTienListItemObj tcThongTinDiemThuListItemObj = new DiemThuTienListItemObj();
                try {
                    obj = jsonArr.getJSONObject(i);
                    if (obj != null) {
                        ID = obj.getString("ID");
                        TenQuayThu = obj.getString("TenQuayThu");
                        DiaChi = obj.getString("DiaChi");
                        GhiChu = obj.getString("GhiChu");
                        MaKV = obj.getString("MaKV");
                        KinhDo = obj.getString("KinhDo");
                        ViDo = obj.getString("ViDo");

                        if (CommonHelper.checkValidString(ID)) {
                            tcThongTinDiemThuListItemObj.setID(ID.toString().trim());
                        }
                        if (CommonHelper.checkValidString(TenQuayThu)) {
                            tcThongTinDiemThuListItemObj.setTenQuayThu(TenQuayThu.toString().trim());
                        }
                        if (CommonHelper.checkValidString(DiaChi)) {
                            tcThongTinDiemThuListItemObj.setDiaChi(DiaChi.toString().trim());
                        }
                        if (CommonHelper.checkValidString(GhiChu)) {
                            tcThongTinDiemThuListItemObj.setGhiChu(GhiChu.toString().trim());
                        }
                        if (CommonHelper.checkValidString(MaKV)) {
                            tcThongTinDiemThuListItemObj.setMaKV(MaKV.toString().trim());
                        }
                        if (CommonHelper.checkValidString(KinhDo)) {
                            tcThongTinDiemThuListItemObj.setKinhDo(KinhDo.toString().trim());
                        }
                        if (CommonHelper.checkValidString(ViDo)) {
                            tcThongTinDiemThuListItemObj.setViDo(ViDo.toString().trim());
                        }
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
   public String getID() {
       return ID;
   }

    public String getTenQuayThu() {
        return TenQuayThu;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public String getMaKV() {
        return MaKV;
    }

    public String getKinhDo() {
        return KinhDo;
    }

    public String getViDo() {
        return ViDo;
    }

    public ArrayList<DiemThuTienListItemObj> getmArrItem() {
        return mArrItem;
    }
}
