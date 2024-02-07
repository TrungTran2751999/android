/**
 * Created by: Kiet.Duong
 * Dec-29-2014
 **/
package com.huewaco.cskh.webservice.objects;


import android.content.Context;

import com.huewaco.cskh.activity.AParent;
import com.huewaco.cskh.helper.CommonHelper;

import org.json.JSONObject;

public class GetThongBaoSoTinChuaDocResponse extends CommonResponse {
	int ThongBaoLichCatNuoc = 0;
	int ThongTinTienNuoc = 0;
	int ThongBaoThanhToan = 0;
	int ThongBaoNoTienNuoc = 0;
	int ThongTinSuCoMatNuoc = 0;
	int ThongBaoKhac = 0;
	int ThongBaoGhiChiSo = 0;
	public int getTongSoTinChuaDoc(){
		return ThongBaoLichCatNuoc+ThongTinTienNuoc+ThongBaoThanhToan+ThongBaoNoTienNuoc+ThongTinSuCoMatNuoc+ThongBaoKhac+ThongBaoGhiChiSo;
	}
	public GetThongBaoSoTinChuaDocResponse(Context context, String result, AParent currentActivity) {
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
				ThongBaoLichCatNuoc = obj.getInt("ThongBaoLichCatNuoc");
				ThongTinTienNuoc = obj.getInt("ThongTinTienNuoc");
				ThongBaoThanhToan = obj.getInt("ThongBaoThanhToan");
				ThongBaoNoTienNuoc = obj.getInt("ThongBaoNoTienNuoc");
				ThongTinSuCoMatNuoc = obj.getInt("ThongTinSuCoMatNuoc");
				ThongBaoKhac = obj.getInt("ThongBaoKhac");
				ThongBaoGhiChiSo = obj.getInt("ThongBaoGhiChiSo");

			} catch (Exception e) {
				System.out.print(e.getMessage());
			}
		}
	}

	public int getThongBaoLichCatNuoc() {
		return ThongBaoLichCatNuoc;
	}

	public void setThongBaoLichCatNuoc(int thongBaoLichCatNuoc) {
		ThongBaoLichCatNuoc = thongBaoLichCatNuoc;
	}

	public int getThongTinTienNuoc() {
		return ThongTinTienNuoc;
	}

	public void setThongTinTienNuoc(int thongTinTienNuoc) {
		ThongTinTienNuoc = thongTinTienNuoc;
	}

	public int getThongBaoThanhToan() {
		return ThongBaoThanhToan;
	}

	public void setThongBaoThanhToan(int thongBaoThanhToan) {
		ThongBaoThanhToan = thongBaoThanhToan;
	}

	public int getThongBaoNoTienNuoc() {
		return ThongBaoNoTienNuoc;
	}

	public void setThongBaoNoTienNuoc(int thongBaoNoTienNuoc) {
		ThongBaoNoTienNuoc = thongBaoNoTienNuoc;
	}

	public int getThongTinSuCoMatNuoc() {
		return ThongTinSuCoMatNuoc;
	}

	public void setThongTinSuCoMatNuoc(int thongTinSuCoMatNuoc) {
		ThongTinSuCoMatNuoc = thongTinSuCoMatNuoc;
	}

	public int getThongBaoKhac() {
		return ThongBaoKhac;
	}

	public void setThongBaoKhac(int thongBaoKhac) {
		ThongBaoKhac = thongBaoKhac;
	}

	public int getThongBaoGhiChiSo() {
		return ThongBaoGhiChiSo;
	}

	public void setThongBaoGhiChiSo(int thongBaoGhiChiSo) {
		ThongBaoGhiChiSo = thongBaoGhiChiSo;
	}
}
