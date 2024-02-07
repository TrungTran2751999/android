/**
 * Created by: Kiet.Duong
 * Dec-29-2014
 **/
package com.huewaco.cskh.webservice.objects;


import android.content.Context;

import com.huewaco.cskh.helper.CommonHelper;

public class GetDichVuGCS_KH_TimeServer_Response {

    private String timesv = "";
    public GetDichVuGCS_KH_TimeServer_Response(Context context, String result) {

        if (CommonHelper.checkValidString(result)) {
            if(CommonHelper.checkValidString(result)){
                timesv = result;
            }
        }
    }

    public String getTimesv() {
        return timesv;
    }

    public void setTimesv(String timesv) {
        this.timesv = timesv;
    }
}
