/**
 * Created by: Kiet.Duong
 * Dec-29-2014
 **/
package com.huewaco.cskh.webservice.objects;


import android.content.Context;

import com.huewaco.cskh.helper.CommonHelper;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetCreateBaoCaoSuCoResponse {
	private int result;
	public GetCreateBaoCaoSuCoResponse(Context context, String result) {
		//super(context,result);
		parseStringToJson((result));
	}



	private void parseStringToJson(String str) {
		result = Integer.valueOf(str);
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
}
