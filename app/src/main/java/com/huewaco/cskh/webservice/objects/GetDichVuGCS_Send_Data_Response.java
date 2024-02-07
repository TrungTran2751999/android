/**
 * Created by: Kiet.Duong
 * Dec-29-2014
 **/
package com.huewaco.cskh.webservice.objects;


import android.content.Context;

import com.huewaco.cskh.helper.CommonHelper;

public class GetDichVuGCS_Send_Data_Response {

    private boolean isOK = false;
    public GetDichVuGCS_Send_Data_Response(Context context, String result) {

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
