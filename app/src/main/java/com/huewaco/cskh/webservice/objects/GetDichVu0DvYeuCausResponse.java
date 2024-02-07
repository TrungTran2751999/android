
package com.huewaco.cskh.webservice.objects;


import android.content.Context;

import com.huewaco.cskh.activity.AParent;
import com.huewaco.cskh.helper.CommonHelper;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetDichVu0DvYeuCausResponse extends CommonResponse {
	//
	String returnString = "";
	public String getReturnString() {
		return returnString;
	}


	public GetDichVu0DvYeuCausResponse(Context context, String result, AParent currentActivity) {
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
