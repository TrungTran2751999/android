/**
 * Created by: Kiet.Duong
 * Dec-29-2014
 **/
package com.huewaco.cskh.webservice.objects;


import android.content.Context;
import android.util.Log;

import com.huewaco.cskh.activity.AParent;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.objects.DvCauHoisListItemObj;
import com.huewaco.cskh.objects.LichCatNuocListItemObj;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetDichVu3DvCauHoisResponse extends CommonResponse {

    //
    String TieuDe= "";
    String NoiDung= "";
    String TraLoi= "";
    ArrayList<DvCauHoisListItemObj> mArrItem;

    //
    public String getTieuDe() {
        return TieuDe;
    }
    public String getNoiDung() {
        return NoiDung;
    }
    public String getTraLoi() {
        return TraLoi;
    }
    public ArrayList<DvCauHoisListItemObj> getmArrItem() {
        return mArrItem;
    }

    //
    public GetDichVu3DvCauHoisResponse(Context context, String result, AParent currentActivity) {
        super(context,result,currentActivity);
        if (CommonHelper.checkValidString(result) && (!this.hasError())) {
            parseStringToJson((result));
        }
    }


    private ArrayList<DvCauHoisListItemObj> parseStringToJson(String str) {
        JSONArray jsonArr = null;
        JSONObject obj = null;
        mArrItem = new ArrayList<DvCauHoisListItemObj>();
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
                DvCauHoisListItemObj dvCauHoisListItemObj = new DvCauHoisListItemObj();
                try {
                    obj = jsonArr.getJSONObject(i);
                    if (obj != null) {
                        TieuDe = obj.getString("TieuDe");
                        NoiDung = obj.getString("NoiDung");
                        TraLoi = obj.getString("TraLoi");
                        //
                        if (CommonHelper.checkValidString(TieuDe)) {
                            dvCauHoisListItemObj.setTieuDe(TieuDe.toString().trim());
                        }
                        if (CommonHelper.checkValidString(NoiDung)) {
                            dvCauHoisListItemObj.setNoiDung(NoiDung.toString().trim());
                        }
                        if (CommonHelper.checkValidString(TraLoi)) {
                            dvCauHoisListItemObj.setTraLoi(TraLoi.toString().trim());
                        }
                        mArrItem.add(dvCauHoisListItemObj);
                    }
                } catch (Exception e) {
                    Log.e("abc", "Parse json thu " + i + "fail");
                }
            }
        } else {
            Log.e("abc", "Get List cau hoi Fail");
        }

        return mArrItem;
    }

}
