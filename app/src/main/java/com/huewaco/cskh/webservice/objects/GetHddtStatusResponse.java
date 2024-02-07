package com.huewaco.cskh.webservice.objects;

import android.content.Context;

import com.huewaco.cskh.activity.AParent;
import com.huewaco.cskh.helper.CommonHelper;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetHddtStatusResponse extends CommonResponse {


    String status = "";
    String message = "";



    String link = "";


    public GetHddtStatusResponse(Context context, String result, AParent currentActivity) {
        super(context,result,currentActivity);
        if (CommonHelper.checkValidString(result) && (!this.hasError())) {
            parseStringToJson((result));
        }
    }

    private void parseStringToJson(String str) {

        JSONObject obj = null;
        try {

            obj = new JSONObject(str);
        } catch (Exception e1) {
            System.out.print(e1.getMessage());
        }
        if (obj != null) {
            try {
                status = obj.getString("status");
                message = obj.getString("message");
                link = obj.getString("link");
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
        }
    }
    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
    public String getLink() {
        return link;
    }
}
