package com.huewaco.cskh.webservice.objects;

import android.content.Context;

import com.huewaco.cskh.activity.AParent;
import com.huewaco.cskh.helper.CommonHelper;

import org.json.JSONArray;
import org.json.JSONObject;

public class PostRegisterNotificationReceiveResponse extends CommonResponse {



        String message = "";



        public PostRegisterNotificationReceiveResponse(Context context, String result, AParent currentActivity) {
            super(context,result,currentActivity);
            if (CommonHelper.checkValidString(result) && (!this.hasError())) {
                message = result;
            }

        }

        public String getMessage() {
            return message;
        }
    }

