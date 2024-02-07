package com.huewaco.cskh.webservice.objects;

import android.content.Context;
import android.util.Log;

import com.huewaco.cskh.activity.AParent;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.objects.TToanTienNuocListItemObj;
import com.huewaco.cskh.objects.ThongBaoListItemObj;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by diepthaibaotoan on 2/20/2017.
 */

public class GetTraCuu4ThongTinThanhToanAllResponse extends CommonResponse {
    private String Nam;
    private String Thang;
    private String M3TinhTien;
    private String TongTien;
    private String DaTra;
    private String IdKh;
    private String SumIdkh;
    //
    ArrayList<TToanTienNuocListItemObj> mArrItem;
    private String SumAll;
    //
    public GetTraCuu4ThongTinThanhToanAllResponse(Context context, String result, AParent currentActivity) {
        super(context,result,currentActivity);
        if (CommonHelper.checkValidString(result) && (!this.hasError())) {
            parseStringToJson((result));
        }
    }

    //
    private ArrayList<TToanTienNuocListItemObj> parseStringToJson(String str) {
        JSONArray jsonArr = null;
        JSONObject obj = null;
        mArrItem = new ArrayList<TToanTienNuocListItemObj>();
        try {
            obj = new JSONObject(str);
            SumAll = obj.getString("StrTotal");
            jsonArr = new JSONArray(obj.getString("Data"));

        } catch (Exception e1) {
            System.out.print(e1.getMessage());
        }
        if (jsonArr != null && jsonArr.length() > 0) {
            for (int i = 0; i < jsonArr.length(); i++) {

                try {
                    obj = jsonArr.getJSONObject(i);
                    if (obj != null) {
                        TToanTienNuocListItemObj item = new TToanTienNuocListItemObj();
                        IdKh = obj.getString("IdKH");
                        if (CommonHelper.checkValidString(IdKh)) {
                            item.setIdkh(IdKh.toString().trim());
                        }
                        SumIdkh = obj.getString("StrSum");
                        if (CommonHelper.checkValidString(IdKh)) {
                            item.setSumIdkh(SumIdkh.toString().trim());
                        }
                        mArrItem.add(item);
                        JSONArray data = new JSONArray(obj.getString("Data"));
                        if (data != null && data.length() > 0){
                            for (int j = 0; j < data.length(); j++) {
                                JSONObject objData = data.getJSONObject(j);
                                TToanTienNuocListItemObj ttoanTienNuocListItemObj = new TToanTienNuocListItemObj();
                                Nam = objData.getString("Nam");
                                Thang = objData.getString("Thang");
                                M3TinhTien = objData.getString("StrM3TinhTien");
                                TongTien = objData.getString("StrTongTien");
                                DaTra = objData.getString("DaTra");

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
                        }
                    }
                } catch (Exception e) {
                    Log.e("abc", "Parse json thu " + i + "fail");
                }
            }
        } else {
            Log.e("abc", "Get ThongBaoThanhToan false");
        }

        return mArrItem;
    }

    public ArrayList<TToanTienNuocListItemObj> getmArrItem() {
        return mArrItem;
    }
    public String getSumAll() {
        return SumAll;
    }
}
