/**
 * Created by: Kiet.Duong
 * Dec-29-2014
 **/
package com.huewaco.cskh.webservice.objects;


import android.content.Context;
import android.util.Log;

import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.objects.GhiChiSo4KiCuoi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetDichVuGCS_KH_ThuocDienGCS_Response {

    private boolean isOK = false;
    public GetDichVuGCS_KH_ThuocDienGCS_Response(Context context, String result) {

        if (CommonHelper.checkValidString(result)) {
            if(result.equalsIgnoreCase("true")){
                isOK = true;
            }else {
                isOK = false;
            }
        }
    }

    public boolean isOK() {
        return isOK;
    }

    public void setOK(boolean OK) {
        isOK = OK;
    }
}
