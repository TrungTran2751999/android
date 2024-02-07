/**
 * Created by: Kiet.Duong
 * Dec-29-2014
 **/
package com.huewaco.cskh.webservice.objects;


import android.content.Context;
import android.util.Log;

import com.huewaco.cskh.activity.AParent;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.objects.ThongTinKHObj;

import org.json.JSONObject;

public class GetDichVuGCS_TinhTien_Response extends CommonResponse{
    String IDKH = "";
    int KlTieuThu=0;
    int M3TinhTien;
    double TongTien;
    String TongTienText= "";

    //String errorMsg="";
//    public GetDichVuGCS_TinhTien_Response(Context context, String result) {
//
//        if (CommonHelper.checkValidString(result)) {
//            parseStringToJson((result));
//        }
//    }
    public GetDichVuGCS_TinhTien_Response(Context context, String result, AParent currentActivity) {
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

                try {
                    if (obj != null) {
//                        if(obj.has("Message") && !obj.isNull("Message")){
//                            errorMsg = obj.getString("Message");
//                        }else
                            {
                            if(obj.has("IDKH") && !obj.isNull("IDKH")){
                                IDKH = obj.getString("IDKH");
                            }
                            if(obj.has("TongTienText") && !obj.isNull("TongTienText")){
                                TongTienText = obj.getString("TongTienText");
                            }

                            if(obj.has("KlTieuThu") && !obj.isNull("KlTieuThu")){
                                KlTieuThu = obj.getInt("KlTieuThu");
                            }
                            if(obj.has("M3TinhTien") && !obj.isNull("M3TinhTien")){
                                M3TinhTien = obj.getInt("M3TinhTien");
                            }
                            if(obj.has("TongTien") && !obj.isNull("TongTien")){
                                TongTien = obj.getDouble("TongTien");
                            }
                        }



                    }
                } catch (Exception e) {
                    Log.e("abc", "Parse json thu " + "fail");
                }
    }

//    public String getErrorMsg() {
//        return errorMsg;
//    }
//
//    public void setErrorMsg(String errorMsg) {
//        this.errorMsg = errorMsg;
//    }

    public String getIDKH() {
        return IDKH;
    }

    public void setIDKH(String IDKH) {
        this.IDKH = IDKH;
    }

    public int getKlTieuThu() {
        return KlTieuThu;
    }

    public void setKlTieuThu(int klTieuThu) {
        KlTieuThu = klTieuThu;
    }

    public int getM3TinhTien() {
        return M3TinhTien;
    }

    public void setM3TinhTien(int m3TinhTien) {
        M3TinhTien = m3TinhTien;
    }

    public double getTongTien() {
        return TongTien;
    }

    public void setTongTien(double tongTien) {
        TongTien = tongTien;
    }

    public String getTongTienText() {
        return TongTienText;
    }

    public void setTongTienText(String tongTienText) {
        TongTienText = tongTienText;
    }
}
