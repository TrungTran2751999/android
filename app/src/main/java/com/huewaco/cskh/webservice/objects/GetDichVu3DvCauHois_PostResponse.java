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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetDichVu3DvCauHois_PostResponse extends CommonResponse {

    String returnString = "";
    public String getReturnString() {
        return returnString;
    }

    public GetDichVu3DvCauHois_PostResponse(Context context, String result, AParent currentActivity) {
        super(context,result,currentActivity);
        if (CommonHelper.checkValidString(result) && (!this.hasError())) {
            if (CommonHelper.checkValidString(result) && (!this.hasError())) {
                if(CommonHelper.checkValidString(result) && ((result.equalsIgnoreCase("true") || (result.equalsIgnoreCase("false"))))){
                    returnString = result;
                }else{
                    parseStringToJson((result));
                }

            }
        }
    }

    private void parseStringToJson(String str) {
        JSONArray jsonArr = null;
        JSONObject obj = null;
        try {
			/*jsonArr = new JSONArray(str);
			if (jsonArr != null && jsonArr.length() > 0) {
				obj = jsonArr.getJSONObject(0);
			}*/
            obj = new JSONObject(str);
        } catch (Exception e1) {
            System.out.print(e1.getMessage());
        }
        if (obj != null) {
            try {


            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
        }
    }
}
