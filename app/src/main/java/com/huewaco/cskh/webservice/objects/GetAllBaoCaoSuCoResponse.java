/**
 * Created by: Kiet.Duong
 * Dec-29-2014
 **/
package com.huewaco.cskh.webservice.objects;


import android.content.Context;
import android.util.Log;

import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.objects.BaoCaoSuCo_Post;
import com.huewaco.cskh.objects.ImgInPostObj;
import com.huewaco.cskh.objects.ThongBaoListItemObj;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetAllBaoCaoSuCoResponse /*extends CommonResponse*/ {
	private ArrayList<BaoCaoSuCo_Post> mArrItem;
//	public GetAllBaoCaoSuCoResponse(Context context, String result) {
//		super(context,result);
//		if (CommonHelper.checkValidString(result) && (!this.hasError())) {
//			parseStringToJson((result));
//		}
//	}
public GetAllBaoCaoSuCoResponse(Context context, String result) {
	if (CommonHelper.checkValidString(result)) {
		parseStringToJson((result));
	}
}


	private ArrayList<BaoCaoSuCo_Post> parseStringToJson(String str) {
		JSONArray jsonArr = null;
		JSONObject obj = null;
		mArrItem = new ArrayList<BaoCaoSuCo_Post>();
		try {
			jsonArr = new JSONArray(str);

		} catch (Exception e1) {
			System.out.print(e1.getMessage());
		}
		if (jsonArr != null && jsonArr.length() > 0) {
			for (int i = 0; i < jsonArr.length(); i++) {
				BaoCaoSuCo_Post baoCaoSuCo_Post = new BaoCaoSuCo_Post();
				try {
					obj = jsonArr.getJSONObject(i);
					if (obj != null) {
						long Id = obj.getLong("Id");
						String IdKH = obj.getString("IdKH");
						String TenKhachHang = obj.getString("TenKhachHang");
						String SoDienThoai = obj.getString("SoDienThoai");
						String DiaChiKhachHang = obj.getString("DiaChiKhachHang");
						String NoiDung = obj.getString("NoiDung");
						String NgayTao = obj.getString("NgayTao");
						String DiaChiSuCoTheoMobile = obj.getString("DiaChiSuCoTheoMobile");
						String DiaChiSuCoKhachHangChon = obj.getString("DiaChiSuCoKhachHangChon");
						String DiaChiDaXacNhan = obj.getString("DiaChiDaXacNhan");
						double KinhDoTheoMobile = obj.getDouble("KinhDoTheoMobile");
						double ViDoTheoMobile = obj.getDouble("ViDoTheoMobile");
						double KinhDoKhachHangChon = obj.getDouble("KinhDoKhachHangChon");
						double ViDoKhachHangChon = obj.getDouble("ViDoKhachHangChon");
						int DaKiemTra = obj.getInt("DaKiemTra");
						boolean DaXuLy = obj.getBoolean("DaXuLy");
						boolean LaTieuBieu = obj.getBoolean("LaTieuBieu");
						String ThongTinPhanHoiChoKhachHang = obj.getString("ThongTinPhanHoiChoKhachHang");
						JSONArray objImgs = obj.getJSONArray("Images");

						ArrayList<ImgInPostObj> mArrImgs = new ArrayList<>();
						ArrayList<ImgInPostObj> mArrImgs1 = new ArrayList<>();

						if (objImgs != null && objImgs.length() > 0) {
							for (int j = 0; j < objImgs.length(); j++) {
								ImgInPostObj imgObj = new ImgInPostObj();
								JSONObject jb = objImgs.getJSONObject(j);
								if(jb != null){
									double Id2 = jb.getDouble("Id");
									String TenFile = jb.getString("TenFile");
									long IdBaoCaoSuCo = jb.getLong("IdBaoCaoSuCo");
									String Base64 = jb.getString("Base64");

									imgObj.setId(Id2);
									imgObj.setTenFile(GlobalVariable.DOMAIN_IMAGE_URL_BCSC+TenFile);
									imgObj.setIdBaoCaoSuCo(IdBaoCaoSuCo);
									imgObj.setBase64(Base64);
								}
								if(j == 0){
									mArrImgs1.add(imgObj);
								}
								mArrImgs.add(imgObj);
							}
						}

						baoCaoSuCo_Post.setId(Id);
						baoCaoSuCo_Post.setIdKH(IdKH);
						baoCaoSuCo_Post.setTenKhachHang(TenKhachHang);
						baoCaoSuCo_Post.setSoDienThoai(SoDienThoai);
						baoCaoSuCo_Post.setDiaChiKhachHang(DiaChiKhachHang);
						baoCaoSuCo_Post.setNoiDung(NoiDung);
						baoCaoSuCo_Post.setNgayTao(NgayTao);
						baoCaoSuCo_Post.setDiaChiSuCoTheoMobile(DiaChiSuCoTheoMobile);
						baoCaoSuCo_Post.setDiaChiSuCoKhachHangChon(DiaChiSuCoKhachHangChon);
						baoCaoSuCo_Post.setDiaChiDaXacNhan(DiaChiDaXacNhan);
						baoCaoSuCo_Post.setKinhDoTheoMobile(KinhDoTheoMobile);
						baoCaoSuCo_Post.setViDoTheoMobile(ViDoTheoMobile);
						baoCaoSuCo_Post.setKinhDoKhachHangChon(KinhDoKhachHangChon);
						baoCaoSuCo_Post.setViDoKhachHangChon(ViDoKhachHangChon);
						baoCaoSuCo_Post.setDaKiemTra(DaKiemTra);
						baoCaoSuCo_Post.setDaXuLy(DaXuLy);
						baoCaoSuCo_Post.setLaTieuBieu(LaTieuBieu);
						baoCaoSuCo_Post.setThongTinPhanHoiChoKhachHang(ThongTinPhanHoiChoKhachHang);
						baoCaoSuCo_Post.setImages(mArrImgs);
						baoCaoSuCo_Post.setImages1(mArrImgs1);


						mArrItem.add(baoCaoSuCo_Post);
					}
				} catch (Exception e) {
					Log.e("abc", "Parse json thu " + i + "fail");
				}
			}
		} else {
			Log.e("abc", "Get ThongBaoThanhToan false");
		}

		return mArrItem;
	}

	public ArrayList<BaoCaoSuCo_Post> getmArrItem() {
		return mArrItem;
	}

	public void setmArrItem(ArrayList<BaoCaoSuCo_Post> mArrItem) {
		this.mArrItem = mArrItem;
	}
}
