/**
 * Created by: Kiet.Duong
 * Dec-29-2014
 **/
package com.huewaco.cskh.webservice.objects;


import android.content.Context;
import android.util.Log;

import com.huewaco.cskh.activity.AParent;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.objects.KhuVucListItemObj;
import com.huewaco.cskh.objects.KhuVucObj;
import com.huewaco.cskh.objects.QuanHuyenListItemObj;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetDichVu0DanhMucDiaChinh_KhuVucResponse extends CommonResponse {

    ArrayList<KhuVucObj> mArrItemKhuVuc;
    public ArrayList<KhuVucObj> getmArrItemKhuVuc() {
        return mArrItemKhuVuc;
    }

    public GetDichVu0DanhMucDiaChinh_KhuVucResponse(Context context, String result, AParent currentActivity) {
        super(context,result,currentActivity);
        if (CommonHelper.checkValidString(result) && (!this.hasError())) {
            mArrItemKhuVuc = CommonHelper.getArrayKhuVucObjFromJsonString(result);
        }
    }
}
