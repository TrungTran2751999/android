package com.huewaco.cskh.webservice.objects;

import android.content.Context;

import com.huewaco.cskh.activity.AParent;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.objects.Img2TextObj;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetTextFromBitmapAPI_HWC_Response /*extends CommonResponse */{
    String returnString = "";
    private boolean isSuccess = false;
    public String getReturnString() {
        return returnString;
    }

    public GetTextFromBitmapAPI_HWC_Response(Context context, String result, AParent currentActivity) {
        //super(context,result,currentActivity);
        if (CommonHelper.checkValidString(result)) {
            if(result.equalsIgnoreCase("\"\"")){
                isSuccess = false;
            }else{
                returnString = result;
                isSuccess = true;
            }

        }else{
            isSuccess = false;
        }
    }


    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public void setReturnString(String returnString) {
        this.returnString = returnString;
    }
}
