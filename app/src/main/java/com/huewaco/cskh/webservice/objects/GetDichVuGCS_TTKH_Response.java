/**
 * Created by: Kiet.Duong
 * Dec-29-2014
 **/
package com.huewaco.cskh.webservice.objects;


import android.content.Context;
import android.util.Log;

import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.objects.GhiChiSo4KiCuoi;
import com.huewaco.cskh.objects.ThongTinKHObj;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetDichVuGCS_TTKH_Response {




    private ThongTinKHObj ttKHOBj = new ThongTinKHObj();
    public GetDichVuGCS_TTKH_Response(Context context, String result) {

        if (CommonHelper.checkValidString(result)) {
            parseStringToJson((result));
        }
    }

    private void parseStringToJson(String str) {
         String Idkh = "";
         String TenKh= "";
         String DiaChi= "";
         String DienThoai= "";
         String KyGhi= "";
         int SoNk=0;
         String TenMdsd= "";
         String TenTthaiGhi= "";
         String MaTthaiGhi= "";
         String NgayNhap_Tt= "";
         int ChiSoDau=0;
        JSONObject obj = null;

        try {
            obj = new JSONObject(str);

        } catch (Exception e1) {
            System.out.print(e1.getMessage());
        }

                try {
                    if (obj != null) {

                        if(obj.has("Idkh") && !obj.isNull("Idkh")){
                            Idkh = obj.getString("Idkh");
                        }
                        if(obj.has("TenKh") && !obj.isNull("TenKh")){
                            TenKh = obj.getString("TenKh");
                        }
                        if(obj.has("DiaChi") && !obj.isNull("DiaChi")){
                            DiaChi = obj.getString("DiaChi");
                        }
                        if(obj.has("DienThoai") && !obj.isNull("DienThoai")){
                            DienThoai = obj.getString("DienThoai");
                        }
                        if(obj.has("KyGhi") && !obj.isNull("KyGhi")){
                            KyGhi = obj.getString("KyGhi");
                        }
                        if(obj.has("SoNk") && !obj.isNull("SoNk")){
                            SoNk = obj.getInt("SoNk");
                        }
                        if(obj.has("TenMdsd") && !obj.isNull("TenMdsd")){
                            TenMdsd = obj.getString("TenMdsd");
                        }
                        if(obj.has("TenTthaiGhi") && !obj.isNull("TenTthaiGhi")){
                            TenTthaiGhi = obj.getString("TenTthaiGhi");
                        }
                        if(obj.has("MaTthaiGhi") && !obj.isNull("MaTthaiGhi")){
                            MaTthaiGhi = obj.getString("MaTthaiGhi");
                        }
                        if(obj.has("NgayNhap_Tt") && !obj.isNull("NgayNhap_Tt")){
                            NgayNhap_Tt = obj.getString("NgayNhap_Tt");
                        }
                        if(obj.has("ChiSoDau") && !obj.isNull("ChiSoDau")){
                            ChiSoDau = obj.getInt("ChiSoDau");
                        }
                        ttKHOBj.setChiSoDau(ChiSoDau);
                        ttKHOBj.setNgayNhap_Tt(NgayNhap_Tt);
                        ttKHOBj.setMaTthaiGhi(MaTthaiGhi);
                        ttKHOBj.setTenTthaiGhi(TenTthaiGhi);
                        ttKHOBj.setTenMdsd(TenMdsd);
                        ttKHOBj.setSoNk(SoNk);
                        ttKHOBj.setKyGhi(KyGhi);
                        ttKHOBj.setDienThoai(DienThoai);
                        ttKHOBj.setDiaChi(DiaChi);
                        ttKHOBj.setTenKh(TenKh);
                        ttKHOBj.setIdkh(Idkh);
                    }
                } catch (Exception e) {
                    Log.e("abc", "Parse json thu " + "fail");
                }
    }


    public ThongTinKHObj getTtKHOBj() {
        return ttKHOBj;
    }

    public void setTtKHOBj(ThongTinKHObj ttKHOBj) {
        this.ttKHOBj = ttKHOBj;
    }
}
