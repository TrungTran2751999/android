/**
 * Created by: Kiet.Duong
 * Dec-29-2014
 **/
package com.huewaco.cskh.webservice.objects;


import android.content.Context;

import com.huewaco.cskh.activity.AParent;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.objects.KhuVucObj;
import com.huewaco.cskh.objects.QuanHuyenListItemObj;

import java.util.ArrayList;

public class GetDichVu0DanhMucDiaChinh_QuanHuyenPhuongXaResponse extends CommonResponse {

    ArrayList<QuanHuyenListItemObj> mArrItemQuanHuyen;
    public ArrayList<QuanHuyenListItemObj> getmArrItemQuanHuyen() {
        return mArrItemQuanHuyen;
    }

    public GetDichVu0DanhMucDiaChinh_QuanHuyenPhuongXaResponse(Context context, String result, AParent currentActivity) {
        super(context,result,currentActivity);
        if (CommonHelper.checkValidString(result) && (!this.hasError())) {
            mArrItemQuanHuyen = CommonHelper.getArrayQuanHuyenObjFromJsonString(result);
    }
}}
