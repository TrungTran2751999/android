/**
 * Created by: Kiet.Duong
 * Dec-29-2014
 **/
package com.huewaco.cskh.webservice.objects;


import android.content.Context;
import android.util.Log;

import com.huewaco.cskh.activity.AParent;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.objects.GhiChiSo4KiCuoi;
import com.huewaco.cskh.objects.LichGhiNuocListItemObj;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetDichVuGCSResponse extends  CommonResponse{

    private int Id;
    private int ChiSoDau;
    private int ChiSoCuoi;
    private String NgayNhap;
    private int KhoiLuongTieuThu;
    private double TongTien;
    private boolean IsDaXacThuc;
    private String NgayXacThuc;
    ArrayList<GhiChiSo4KiCuoi> mArrItem;
    private int Thang;
    private int Nam;

    public GetDichVuGCSResponse(Context context, String result, AParent currentActivity) {
        super(context,result,currentActivity);
        if (CommonHelper.checkValidString(result)) {
            parseStringToJson((result));
        }
    }

    private ArrayList<GhiChiSo4KiCuoi> parseStringToJson(String str) {
        JSONArray jsonArr = null;
        JSONObject obj = null;
        mArrItem = new ArrayList<GhiChiSo4KiCuoi>();
        try {
            jsonArr = new JSONArray(str);

        } catch (Exception e1) {
            System.out.print(e1.getMessage());
        }
        if (jsonArr != null && jsonArr.length() > 0) {
            for (int i = 0; i < jsonArr.length(); i++) {
                GhiChiSo4KiCuoi tcThongTinDiemThuListItemObj = new GhiChiSo4KiCuoi();
                try {
                    obj = jsonArr.getJSONObject(i);
                    if (obj != null) {
                        if(obj.has("Id") && !obj.isNull("Id")){
                            Id = obj.getInt("Id");
                        }
                        if(obj.has("ChiSoDau") && !obj.isNull("ChiSoDau")){
                            ChiSoDau = obj.getInt("ChiSoDau");
                        }
                        if(obj.has("ChiSoCuoi") && !obj.isNull("ChiSoCuoi")){
                            ChiSoCuoi = obj.getInt("ChiSoCuoi");
                        }
                        if(obj.has("NgayNhap") && !obj.isNull("NgayNhap")){
                            NgayNhap = obj.getString("NgayNhap");
                        }

                        if(obj.has("KhoiLuongTieuThu") && !obj.isNull("KhoiLuongTieuThu")){
                            KhoiLuongTieuThu = obj.getInt("KhoiLuongTieuThu");
                        }

                        if(obj.has("Thang") && !obj.isNull("Thang")){
                            Thang = obj.getInt("Thang");
                        }

                        if(obj.has("Nam") && !obj.isNull("Nam")){
                            Nam = obj.getInt("Nam");
                        }

                        if(obj.has("TongTien") && !obj.isNull("TongTien")){
                            TongTien = obj.getDouble("TongTien");
                        }
                        if(obj.has("IsDaXacThuc") && !obj.isNull("IsDaXacThuc")){
                            IsDaXacThuc = obj.getBoolean("IsDaXacThuc");
                        }else{
                            IsDaXacThuc = false;
                        }
                        if(obj.has("NgayXacThuc") && !obj.isNull("NgayXacThuc")){
                            NgayXacThuc = obj.getString("NgayXacThuc");
                        }else{
                            NgayXacThuc ="";
                        }
                        ArrayList<String> mArrFiles = new ArrayList<>();
                        if(obj.has("TenFiles") && !obj.isNull("TenFiles")){
                            JSONArray TenFiles = obj.getJSONArray("TenFiles");
                            if(TenFiles != null && TenFiles.length() >0){
                                for(int t = 0; t< TenFiles.length(); t++){
                                    String jsotStr = TenFiles.getString(t);
                                    if(CommonHelper.checkValidString(jsotStr)){
                                        String url2 = GlobalVariable.DOMAIN_IMAGE_URL_GCS +jsotStr;
                                        mArrFiles.add(url2);
                                    }
                                }
                            }
                        }

                        tcThongTinDiemThuListItemObj.setmArrFiles(mArrFiles);

                        if (CommonHelper.checkValidString(NgayNhap)) {
                            tcThongTinDiemThuListItemObj.setNgayNhap(NgayNhap);
                        }
                        if (CommonHelper.checkValidString(NgayXacThuc)) {
                            tcThongTinDiemThuListItemObj.setNgayXacThuc(NgayXacThuc);
                        }
                        tcThongTinDiemThuListItemObj.setDaXacThuc(IsDaXacThuc);
                        tcThongTinDiemThuListItemObj.setKhoiLuongTieuThu(KhoiLuongTieuThu);
                        tcThongTinDiemThuListItemObj.setChiSoCuoi(ChiSoCuoi);
                        tcThongTinDiemThuListItemObj.setChiSoDau(ChiSoDau);
                        tcThongTinDiemThuListItemObj.setTongTien(TongTien);
                        tcThongTinDiemThuListItemObj.setThang(Thang);
                        tcThongTinDiemThuListItemObj.setNam(Nam);
                        tcThongTinDiemThuListItemObj.setId(Id);
                        mArrItem.add(tcThongTinDiemThuListItemObj);
                    }
                } catch (Exception e) {
                    Log.e("abc", "Parse json thu " + i + "fail");
                }
            }
        } else {
            Log.e("abc", "Get GCS 4 ky cuoi Fail");
        }

        return mArrItem;
    }

   //


    public int getChiSoDau() {
        return ChiSoDau;
    }

    public void setChiSoDau(int chiSoDau) {
        ChiSoDau = chiSoDau;
    }

    public int getChiSoCuoi() {
        return ChiSoCuoi;
    }

    public void setChiSoCuoi(int chiSoCuoi) {
        ChiSoCuoi = chiSoCuoi;
    }

    public String getNgayNhap() {
        return NgayNhap;
    }

    public void setNgayNhap(String ngayNhap) {
        NgayNhap = ngayNhap;
    }

    public boolean isDaXacThuc() {
        return IsDaXacThuc;
    }

    public void setDaXacThuc(boolean daXacThuc) {
        IsDaXacThuc = daXacThuc;
    }

    public String getNgayXacThuc() {
        return NgayXacThuc;
    }

    public void setNgayXacThuc(String ngayXacThuc) {
        NgayXacThuc = ngayXacThuc;
    }

    public int getKhoiLuongTieuThu() {
        return KhoiLuongTieuThu;
    }

    public void setKhoiLuongTieuThu(int khoiLuongTieuThu) {
        KhoiLuongTieuThu = khoiLuongTieuThu;
    }

    public double getTongTien() {
        return TongTien;
    }

    public void setTongTien(double tongTien) {
        TongTien = tongTien;
    }

    public ArrayList<GhiChiSo4KiCuoi> getmArrItem() {
        return mArrItem;
    }

    public void setmArrItem(ArrayList<GhiChiSo4KiCuoi> mArrItem) {
        this.mArrItem = mArrItem;
    }

    public int getThang() {
        return Thang;
    }

    public void setThang(int thang) {
        Thang = thang;
    }

    public int getNam() {
        return Nam;
    }

    public void setNam(int nam) {
        Nam = nam;
    }
}
