/**
 * Created by: Kiet.Duong
 * Dec-29-2014
 **/
package com.huewaco.cskh.webservice.objects;


import android.content.Context;

import com.huewaco.cskh.activity.AParent;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.objects.ParentObj;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetLoginResponse extends CommonResponse {
	private boolean isLoginSuccess = false;
	String access_token = "";
	String token_type = "";
	String expires_in = "";
	public GetLoginResponse(Context context, String result, AParent currentActivity) {
		super(context,result,currentActivity);
		if (CommonHelper.checkValidString(result) && (!this.hasError())) {
			parseStringToJson((result));
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
				access_token = obj.getString("access_token");
				token_type = obj.getString("token_type");
				expires_in = obj.getString("expires_in");

			} catch (Exception e) {
				System.out.print(e.getMessage());
			}
		}
	}

	public boolean isLoginSuccess() {
		return isLoginSuccess;
	}

	public String getAccess_token() {
		return access_token;
	}

	public String getToken_type() {
		return token_type;
	}

	public String getExpires_in() {
		return expires_in;
	}
}
