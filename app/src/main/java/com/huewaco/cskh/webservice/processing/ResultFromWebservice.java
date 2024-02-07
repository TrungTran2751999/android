/**
 * Created by: Kiet.Duong
 * Dec-29-2014
 **/
package com.huewaco.cskh.webservice.processing;


import static com.huewaco.cskh.helper.GlobalVariable.PARAM_TB2_NOI_DUNG_ALL_THEO_LOAI;

import android.content.Context;

import com.huewaco.cskh.activity.AParent;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.objects.ThongTinKHObj;
import com.huewaco.cskh.webservice.objects.GetAllBaoCaoSuCoResponse;
import com.huewaco.cskh.webservice.objects.GetChangePasswordResponse;
import com.huewaco.cskh.webservice.objects.GetCreateBaoCaoSuCoResponse;
import com.huewaco.cskh.webservice.objects.GetDangKyResponse;
import com.huewaco.cskh.webservice.objects.GetDeviceTokenResponse;
import com.huewaco.cskh.webservice.objects.GetDichVu0DanhMucDiaChinh_KhuVucResponse;
import com.huewaco.cskh.webservice.objects.GetDichVu0DanhMucDiaChinh_QuanHuyenPhuongXaResponse;
import com.huewaco.cskh.webservice.objects.GetDichVu0DvYeuCausResponse;
import com.huewaco.cskh.webservice.objects.GetDichVu1ThayDoiTTKHResponse;
import com.huewaco.cskh.webservice.objects.GetDichVu3DvCauHoisResponse;
import com.huewaco.cskh.webservice.objects.GetDichVu3DvCauHois_PostResponse;
import com.huewaco.cskh.webservice.objects.GetDichVuGCSResponse;
import com.huewaco.cskh.webservice.objects.GetDichVuGCS_KH_ThuocDienGCS_Response;
import com.huewaco.cskh.webservice.objects.GetDichVuGCS_KH_TimeServer_Response;
import com.huewaco.cskh.webservice.objects.GetDichVuGCS_Send_Data_Response;
import com.huewaco.cskh.webservice.objects.GetDichVuGCS_TTKH_Response;
import com.huewaco.cskh.webservice.objects.GetDichVuGCS_TinhTien_Response;
import com.huewaco.cskh.webservice.objects.GetHddtDetailResponse;
import com.huewaco.cskh.webservice.objects.GetHddtStatusResponse;
import com.huewaco.cskh.webservice.objects.GetLogOutResponse;
import com.huewaco.cskh.webservice.objects.GetLoginResponse;
import com.huewaco.cskh.webservice.objects.GetResetPasswordResponse;
import com.huewaco.cskh.webservice.objects.GetTextFromBitmapAPIResponse;
import com.huewaco.cskh.webservice.objects.GetTextFromBitmapAPI_HWC_Response;
import com.huewaco.cskh.webservice.objects.GetThongBaoAllTheoLoaiResponse;
import com.huewaco.cskh.webservice.objects.GetThongBaoDeleteResponse;
import com.huewaco.cskh.webservice.objects.GetThongBaoReadResponse;
import com.huewaco.cskh.webservice.objects.GetThongBaoSoTinChuaDocResponse;
import com.huewaco.cskh.webservice.objects.GetThongBaoTheoLoaiResponse;
import com.huewaco.cskh.webservice.objects.GetTraCuu0LichGhiNuocResponse;
import com.huewaco.cskh.webservice.objects.GetTraCuu1ThongTinDiemThuResponse;
import com.huewaco.cskh.webservice.objects.GetTraCuu2LichCatNuocResponse;
import com.huewaco.cskh.webservice.objects.GetTraCuu3ChiSoNuocTieuThuResponse;
import com.huewaco.cskh.webservice.objects.GetTraCuu4ThongTinThanhToanAllResponse;
import com.huewaco.cskh.webservice.objects.GetTraCuu4ThongTinThanhToanTienNuocResponse;
import com.huewaco.cskh.webservice.objects.GetTraCuu5TCuHDonResponse;
import com.huewaco.cskh.webservice.objects.GetUserInfoResponse;
import com.huewaco.cskh.webservice.objects.PostHddtResponse;
import com.huewaco.cskh.webservice.objects.PostRegisterNotificationReceiveResponse;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ResultFromWebservice {
    public GetDichVuGCS_KH_ThuocDienGCS_Response getDichVuGCS_KH_ThuocDienGCS_Response(Context context, String tokenType, String token){
        GetDichVuGCS_KH_ThuocDienGCS_Response getDichVuGCS_KH_ThuocDienGCS_Response = null;
        try {
            String strEntity = tokenType + " " + token;
            WebserviceProcessing WP = new WebserviceProcessing();
            getDichVuGCS_KH_ThuocDienGCS_Response = new GetDichVuGCS_KH_ThuocDienGCS_Response(context, WP.doGetString(GlobalVariable.URL_DICHVU_GCS_GET_KH_THUOCDIEN_GCS+"?idkh="+ GlobalVariable.KHACH_HANG_CURRENT.getIdKh(), strEntity));

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getDichVuGCS_KH_ThuocDienGCS_Response;
    }
    public GetDichVuGCS_TinhTien_Response getDichVuGCS_TinhTien_Response(Context context, String tokenType, String token,JSONObject paramsX , AParent currentActivity){
        GetDichVuGCS_TinhTien_Response getDichVuGCS_TinhTien_Response = null;
        try {
            String strEntity = tokenType + " " + token;
            WebserviceProcessing WP = new WebserviceProcessing();
            getDichVuGCS_TinhTien_Response = new GetDichVuGCS_TinhTien_Response( context, WP.doGetStringMapParameters(GlobalVariable.URL_DICHVU_GCS_GET_TINHTIEN,paramsX,strEntity),currentActivity);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getDichVuGCS_TinhTien_Response;
    }
    public GetDichVuGCS_TTKH_Response getDichVuGCS_TTKH_Response(Context context, String tokenType, String token){
        GetDichVuGCS_TTKH_Response getDichVuGCS_TTKH_Response = null;
        try {
            String strEntity = tokenType + " " + token;
            WebserviceProcessing WP = new WebserviceProcessing();
            getDichVuGCS_TTKH_Response = new GetDichVuGCS_TTKH_Response(context, WP.doGetString(GlobalVariable.URL_DICHVU_GCS_GET_THONGTIN_KH+ "?idkh=" +GlobalVariable.KHACH_HANG_CURRENT.getIdKh(), strEntity));

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getDichVuGCS_TTKH_Response;
    }
    public GetDichVuGCS_KH_TimeServer_Response getDichVuGCS_KH_TimeServer_Response(Context context, String tokenType, String token){
        GetDichVuGCS_KH_TimeServer_Response getDichVuGCS_KH_TimeServer_Response = null;
        try {
            String strEntity = tokenType + " " + token;
            WebserviceProcessing WP = new WebserviceProcessing();
            getDichVuGCS_KH_TimeServer_Response = new GetDichVuGCS_KH_TimeServer_Response(context, WP.doGetString(GlobalVariable.URL_DICHVU_GCS_GET_TIME_SERVER_NOW, strEntity));

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getDichVuGCS_KH_TimeServer_Response;
    }
    public GetAllBaoCaoSuCoResponse get10OldBaoCaoSuCoResponsePOST(Context context,long OldestId, String add_filter){
        GetAllBaoCaoSuCoResponse getAllBaoCaoSuCoResponse = null;
        try {
            int Page = 10;
            String strEntityLogin = "OldestId="+OldestId+"&Page="+Page;
            String contentType = "application/x-www-form-urlencoded";
            if(CommonHelper.checkValidString(add_filter)){
                strEntityLogin+=add_filter;
            }

            WebserviceProcessing WP = new WebserviceProcessing();
            getAllBaoCaoSuCoResponse = new GetAllBaoCaoSuCoResponse( context, WP.doPostString(GlobalVariable.URL_BAOCAOSUCO_GETALL,strEntityLogin,contentType,""));
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getAllBaoCaoSuCoResponse;
    }
    public GetAllBaoCaoSuCoResponse get10NewBaoCaoSuCoResponsePOST(Context context,long LatestId, String add_filter){
        GetAllBaoCaoSuCoResponse getAllBaoCaoSuCoResponse = null;
        try {

            int Page = 10;
            String strEntityLogin = "LatestId="+LatestId+"&Page="+Page;
            String contentType = "application/x-www-form-urlencoded";
            if(CommonHelper.checkValidString(add_filter)){
                strEntityLogin+=add_filter;
            }
            WebserviceProcessing WP = new WebserviceProcessing();
            getAllBaoCaoSuCoResponse = new GetAllBaoCaoSuCoResponse( context, WP.doPostString(GlobalVariable.URL_BAOCAOSUCO_GETALL,strEntityLogin,contentType,""));

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getAllBaoCaoSuCoResponse;
    }
    public GetAllBaoCaoSuCoResponse get10FirstBaoCaoSuCoResponsePOST(Context context, long LatestId, String add_filter){
        GetAllBaoCaoSuCoResponse getAllBaoCaoSuCoResponse = null;
        try {
            int Page = 10;
            String strEntityLogin = "LatestId="+LatestId+"&Page="+Page;
            String contentType = "application/x-www-form-urlencoded";

            if(CommonHelper.checkValidString(add_filter)){
                strEntityLogin+=add_filter;
            }
            WebserviceProcessing WP = new WebserviceProcessing();
            getAllBaoCaoSuCoResponse = new GetAllBaoCaoSuCoResponse( context, WP.doPostString(GlobalVariable.URL_BAOCAOSUCO_GETALL,strEntityLogin,contentType,""));

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getAllBaoCaoSuCoResponse;
    }
    public GetCreateBaoCaoSuCoResponse getCreateBaoCaoSuCoResponse(Context context, String dataJson){
        GetCreateBaoCaoSuCoResponse getCreateBaoCaoSuCoResponse = null;
        try {
            WebserviceProcessing WP = new WebserviceProcessing();
            String contentType = "application/json";
            getCreateBaoCaoSuCoResponse = new GetCreateBaoCaoSuCoResponse( context, WP.doPostString(GlobalVariable.URL_BAOCAOSUCO_CREATE,dataJson,contentType,""));

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getCreateBaoCaoSuCoResponse;
    }
    public GetAllBaoCaoSuCoResponse get10OldBaoCaoSuCoResponse(Context context,long OldestId){
        GetAllBaoCaoSuCoResponse getAllBaoCaoSuCoResponse = null;
        try {

            int Page = 10;
            List<NameValuePair> params = new LinkedList<NameValuePair>();
            params.add(new BasicNameValuePair("OldestId", String.valueOf(OldestId)));
            params.add(new BasicNameValuePair("Page", String.valueOf(Page)));
            WebserviceProcessing WP = new WebserviceProcessing();
            getAllBaoCaoSuCoResponse = new GetAllBaoCaoSuCoResponse( context, WP.doGetStringParameters(GlobalVariable.URL_BAOCAOSUCO_GETALL,params));

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getAllBaoCaoSuCoResponse;
    }
    public GetAllBaoCaoSuCoResponse get10NewBaoCaoSuCoResponse(Context context,long LatestId){
        GetAllBaoCaoSuCoResponse getAllBaoCaoSuCoResponse = null;
        try {

            int Page = 10;
            List<NameValuePair> params = new LinkedList<NameValuePair>();
            params.add(new BasicNameValuePair("LatestId", String.valueOf(LatestId)));
            params.add(new BasicNameValuePair("Page", String.valueOf(Page)));
            WebserviceProcessing WP = new WebserviceProcessing();
            getAllBaoCaoSuCoResponse = new GetAllBaoCaoSuCoResponse( context, WP.doGetStringParameters(GlobalVariable.URL_BAOCAOSUCO_GETALL,params));

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getAllBaoCaoSuCoResponse;
    }
    public GetAllBaoCaoSuCoResponse get10FirstBaoCaoSuCoResponse(Context context){
        GetAllBaoCaoSuCoResponse getAllBaoCaoSuCoResponse = null;
        try {
            //String strEntity10 = "?LatestId=0&Page=10";
            //String contentType = "application/x-www-form-urlencoded";
            int LatestId = 0;
            int Page = 10;
            List<NameValuePair> params = new LinkedList<NameValuePair>();
            params.add(new BasicNameValuePair("LatestId", String.valueOf(LatestId)));
            params.add(new BasicNameValuePair("Page", String.valueOf(Page)));
            WebserviceProcessing WP = new WebserviceProcessing();
            getAllBaoCaoSuCoResponse = new GetAllBaoCaoSuCoResponse( context, WP.doGetStringParameters(GlobalVariable.URL_BAOCAOSUCO_GETALL,params));

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getAllBaoCaoSuCoResponse;
    }
    public GetAllBaoCaoSuCoResponse getAllBaoCaoSuCoResponse(Context context){
        GetAllBaoCaoSuCoResponse getAllBaoCaoSuCoResponse = null;
        try {
            WebserviceProcessing WP = new WebserviceProcessing();
            getAllBaoCaoSuCoResponse = new GetAllBaoCaoSuCoResponse( context, WP.doGetStringPublic(GlobalVariable.URL_BAOCAOSUCO_GETALL));

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getAllBaoCaoSuCoResponse;
    }
    public GetDeviceTokenResponse getDeviceTokenResponse(Context context,String maKh, String devicetoken, String deviceType,String authorization, AParent currentActivity){
        GetDeviceTokenResponse getDeviceTokenResponse = null;
        try {
            String strEntityLogin = "Idkh="+maKh+"&TokenName=" + devicetoken + "&DevideType=" + deviceType;
            String contentType = "application/x-www-form-urlencoded";
            WebserviceProcessing WP = new WebserviceProcessing();
            getDeviceTokenResponse = new GetDeviceTokenResponse( context, WP.doPostString(GlobalVariable.URL_DEVICE_TOKEN_REGISTER, strEntityLogin, contentType,authorization),currentActivity);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getDeviceTokenResponse;
    }
    public PostRegisterNotificationReceiveResponse getRegisterNotificationReceiveResponse(Context context, String tokenType, String token, String devicetoken, String deviceType, ArrayList<String> idkh, AParent currentActivity){
        PostRegisterNotificationReceiveResponse getRegisterNotificationReceiveResponse = null;
        try {
            String authorization = tokenType + " " + token;
            String contentType = "application/json";
            JSONObject c = new JSONObject();
            try {
                c.put("token",devicetoken);
                c.put("deviceType",deviceType);
                c.put("arrIdKH", new JSONArray(idkh));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            WebserviceProcessing WP = new WebserviceProcessing();
            getRegisterNotificationReceiveResponse = new PostRegisterNotificationReceiveResponse(context,WP.doPostString(GlobalVariable.URL_DEVICE__REGISTER_NOTIFICATIONRECEIVE, c.toString(), contentType,authorization),currentActivity);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getRegisterNotificationReceiveResponse;
    }
    public GetLoginResponse getLogin(Context context, String userName, String passWord,String authorization, AParent currentActivity) {
        GetLoginResponse loginResponse = null;
        try {
            String strEntityLogin = "grant_type=password&username=" + userName + "&password=" + passWord;
            String contentType = "application/x-www-form-urlencoded";
            WebserviceProcessing WP = new WebserviceProcessing();
            loginResponse = new GetLoginResponse(context,WP.doPostString(GlobalVariable.URL_LOGIN, strEntityLogin, contentType,authorization),currentActivity);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loginResponse;
    }
    public GetLogOutResponse getLogOutResponse(Context context, String maKh, String devicetoken, String deviceType, String authorization, AParent currentActivity){
        GetLogOutResponse getLogOutResponse = null;
        try {
            String strEntityLogin = "idkh="+maKh+"&tokenName=" + devicetoken + "&devideType=" + deviceType;
            String contentType = "application/x-www-form-urlencoded";
            WebserviceProcessing WP = new WebserviceProcessing();
            getLogOutResponse = new GetLogOutResponse(context, WP.doPostString(GlobalVariable.URL_CAIDAT_LOGOUT, strEntityLogin, contentType,authorization),currentActivity);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getLogOutResponse;
    }

    public GetChangePasswordResponse getChangePasswordResponse(Context context, String OldPassword, String NewPassword, String ConfirmPassword, String authorization, AParent currentActivity){
        GetChangePasswordResponse getChangePasswordResponse = null;
        try {
            String strEntityLogin = "OldPassword="+OldPassword+"&NewPassword=" + NewPassword + "&ConfirmPassword=" + ConfirmPassword;
            String contentType = "application/x-www-form-urlencoded";
            WebserviceProcessing WP = new WebserviceProcessing();
            getChangePasswordResponse = new GetChangePasswordResponse(context, WP.doPostString(GlobalVariable.URL_CAIDAT_CHANGE_PASSWORD, strEntityLogin, contentType,authorization),currentActivity);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getChangePasswordResponse;
    }

    public GetTraCuu2LichCatNuocResponse getLichCatNuoc(Context context, String tokenType, String token, AParent currentActivity) {
        GetTraCuu2LichCatNuocResponse catnuocResponse = null;
        try {
            String strEntity = tokenType + " " + token;
            WebserviceProcessing WP = new WebserviceProcessing();
            catnuocResponse = new GetTraCuu2LichCatNuocResponse(context,WP.doGetString(GlobalVariable.URL_TC2_LICH_CAT_NUOC+ "?idkh=" +GlobalVariable.KHACH_HANG_CURRENT.getIdKh(), strEntity),currentActivity);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return catnuocResponse;
    }

    //
    public GetUserInfoResponse getUserInfo(Context context, String tokenType, String token, AParent currentActivity) {
        GetUserInfoResponse userinfoResponse = null;
        try {
            String strEntity = tokenType + " " + token;
            WebserviceProcessing WP = new WebserviceProcessing();
            userinfoResponse = new GetUserInfoResponse(context, WP.doGetString(GlobalVariable.URL_GET_USER_INFO, strEntity),currentActivity);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userinfoResponse;
    }
    public GetThongBaoReadResponse getThongBaoReadResponse(Context context, String id, String tokenType, String token, AParent currentActivity){
        GetThongBaoReadResponse getThongBaoReadResponse = null;
        try {
            String strEntity = "";
            String contentType = "application/x-www-form-urlencoded";
            String authorization = tokenType + " " + token;
            WebserviceProcessing WP = new WebserviceProcessing();
            getThongBaoReadResponse = new GetThongBaoReadResponse(context, WP.doPostString(GlobalVariable.URL_TB3_READ+id, strEntity, contentType,authorization),currentActivity);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getThongBaoReadResponse;
    }
    public GetThongBaoDeleteResponse getThongBaoDeleteResponse(Context context, String id, String tokenType, String token, AParent currentActivity){
        GetThongBaoDeleteResponse getThongBaoDeleteResponse = null;
        try {
            String strEntity = "";
            String contentType = "application/x-www-form-urlencoded";
            String authorization = tokenType + " " + token;
            WebserviceProcessing WP = new WebserviceProcessing();
            getThongBaoDeleteResponse = new GetThongBaoDeleteResponse(context, WP.doPostString(GlobalVariable.URL_TB3_DELETE+id, strEntity, contentType,authorization),currentActivity);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getThongBaoDeleteResponse;
    }
    //
    public GetThongBaoTheoLoaiResponse getThongBaoTheoLoaiResponse(Context context, String tokenType, String token, int position,String idkh, AParent currentActivity) {
        GetThongBaoTheoLoaiResponse thongBaoTheoLoaiResponse = null;
        try {
            String strEntity = tokenType + " " + token;
            WebserviceProcessing WP = new WebserviceProcessing();
            thongBaoTheoLoaiResponse = new GetThongBaoTheoLoaiResponse(context,WP.doGetString(GlobalVariable.URL_TB2_NOI_DUNG_THEO_LOAI(position)+ "?idkh=" + idkh, strEntity),currentActivity);


        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return thongBaoTheoLoaiResponse;
    }
    public GetThongBaoAllTheoLoaiResponse getThongBaoAllTheoLoaiResponse(Context context, String tokenType, String token, int position,ArrayList<String> idkh, AParent currentActivity) {
        GetThongBaoAllTheoLoaiResponse thongBaoTheoLoaiResponse = null;

        try{
            String authorization = tokenType + " " + token;
            String contentType = "application/json";
            JSONObject c = new JSONObject();
            try {
                c.put("loaiThongBao",PARAM_TB2_NOI_DUNG_ALL_THEO_LOAI(position));
                c.put("arrIdKH", new JSONArray(idkh));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            WebserviceProcessing WP = new WebserviceProcessing();
            thongBaoTheoLoaiResponse = new GetThongBaoAllTheoLoaiResponse(context,WP.doPostString(GlobalVariable.URL_TB2_NOI_DUNG__ALLTHEO_LOAI, c.toString(), contentType,authorization),currentActivity);
        } catch (Exception e){
            e.printStackTrace();
        }
        return thongBaoTheoLoaiResponse;
    }
    public GetThongBaoSoTinChuaDocResponse getThongBaoSoTinChuaDocResponse(Context context, String tokenType, String token, AParent currentActivity){
        GetThongBaoSoTinChuaDocResponse getThongBaoSoTinChuaDocResponse = null;
        try {
            String strEntity = tokenType + " " + token;
            WebserviceProcessing WP = new WebserviceProcessing();
            getThongBaoSoTinChuaDocResponse = new GetThongBaoSoTinChuaDocResponse(context,WP.doGetString(GlobalVariable.URL_TB1_SO_TIN_CHUA_DOC, strEntity),currentActivity);


        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getThongBaoSoTinChuaDocResponse;
    }
    //
    public GetTraCuu1ThongTinDiemThuResponse getTCThongTinDiemThuResponse(Context context, String tokenType, String token, AParent currentActivity) {
        GetTraCuu1ThongTinDiemThuResponse getTraCuu1ThongTinDiemThuResponse = null;
        try {
            String strEntity = tokenType + " " + token;
            WebserviceProcessing WP = new WebserviceProcessing();
            getTraCuu1ThongTinDiemThuResponse = new GetTraCuu1ThongTinDiemThuResponse(context,WP.doGetString(GlobalVariable.URL_TC1_THONG_TIN_DIEM_THU, strEntity),currentActivity);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getTraCuu1ThongTinDiemThuResponse;
    }
    public GetTraCuu0LichGhiNuocResponse getTCLichGhiNuocResponse(Context context, String tokenType, String token, AParent currentActivity) {
        GetTraCuu0LichGhiNuocResponse getTCLichGhiNuocResponse = null;
        try {
            String strEntity = tokenType + " " + token;
            WebserviceProcessing WP = new WebserviceProcessing();
            String url = GlobalVariable.URL_TC0_LICH_GHI_NUOC;
            getTCLichGhiNuocResponse = new GetTraCuu0LichGhiNuocResponse(context,WP.doGetString(url, strEntity),currentActivity);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getTCLichGhiNuocResponse;
    }
    public GetTraCuu3ChiSoNuocTieuThuResponse getTCChiSoNuocTieuThuResponse(Context context, String tokenType, String token, AParent currentActivity) {
        GetTraCuu3ChiSoNuocTieuThuResponse getTraCuu3ChiSoNuocTieuThuResponse = null;
        try {
            String strEntity = tokenType + " " + token;
            WebserviceProcessing WP = new WebserviceProcessing();
            getTraCuu3ChiSoNuocTieuThuResponse = new GetTraCuu3ChiSoNuocTieuThuResponse(context,WP.doGetString(GlobalVariable.URL_TC3_CHI_SO_NUOC_TIEU_THU +"?idkh="+ GlobalVariable.KHACH_HANG_CURRENT.getIdKh(), strEntity),currentActivity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getTraCuu3ChiSoNuocTieuThuResponse;
    }
    public GetTraCuu4ThongTinThanhToanTienNuocResponse getTCThongTinThanhToanTienNuocResponse(Context context, String tokenType, String token, AParent currentActivity) {
        GetTraCuu4ThongTinThanhToanTienNuocResponse getTraCuu4ThongTinThanhToanTienNuocResponse = null;
        try {
            String strEntity = tokenType + " " + token;
            WebserviceProcessing WP = new WebserviceProcessing();
            getTraCuu4ThongTinThanhToanTienNuocResponse = new GetTraCuu4ThongTinThanhToanTienNuocResponse(context,WP.doGetString(GlobalVariable.URL_TC4_THONG_TIN_THANH_TOAN_TIEN_NUOC, strEntity),currentActivity);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getTraCuu4ThongTinThanhToanTienNuocResponse;
    }
    public GetTraCuu4ThongTinThanhToanAllResponse getTCThongTinThanhToanTienNuocAllResponse(Context context, String tokenType, String token, AParent currentActivity, String fromDate, String toDate,ArrayList<String> idkh) {
        GetTraCuu4ThongTinThanhToanAllResponse getTraCuu4ThongTinThanhToanTienNuocResponse = null;
        try{
            String authorization = tokenType + " " + token;
            String contentType = "application/json";
            JSONObject c = new JSONObject();
            try {
                c.put("fromdate",fromDate);
                c.put("todate",toDate);
                c.put("arrIdKH", new JSONArray(idkh));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            WebserviceProcessing WP = new WebserviceProcessing();
            getTraCuu4ThongTinThanhToanTienNuocResponse = new GetTraCuu4ThongTinThanhToanAllResponse(context,WP.doPostString(GlobalVariable.URL_TC4_THONG_TIN_THANH_TOAN_TIEN_NUOC_TUNGAY_DENNGAYALL, c.toString(), contentType,authorization),currentActivity);
        } catch (Exception e){
            e.printStackTrace();
        }
        return getTraCuu4ThongTinThanhToanTienNuocResponse;
    }
    public GetTraCuu5TCuHDonResponse getTraCuu5TCuHDonResponse(Context context, String tokenType, String token, AParent currentActivity) {
        GetTraCuu5TCuHDonResponse getTraCuu5TCuHDonResponse = null;
        try {
            String strEntity = tokenType + " " + token;
            WebserviceProcessing WP = new WebserviceProcessing();
            getTraCuu5TCuHDonResponse = new GetTraCuu5TCuHDonResponse(context,WP.doGetString(GlobalVariable.URL_TC5_HOADON, strEntity),currentActivity);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getTraCuu5TCuHDonResponse;
    }

    // Tab dich vu
    public GetDichVu0DanhMucDiaChinh_KhuVucResponse getDichVu0DanhMucDiaChinhResponse(Context context, String tokenType, String token, AParent currentActivity) {
        GetDichVu0DanhMucDiaChinh_KhuVucResponse dv0DanhMucDiaChinhResponse = null;
        try {
            String strEntity = tokenType + " " + token;
            WebserviceProcessing WP = new WebserviceProcessing();
            dv0DanhMucDiaChinhResponse = new GetDichVu0DanhMucDiaChinh_KhuVucResponse(context,WP.doGetString(GlobalVariable.URL_GET_DV_DANH_MUC_DIA_CHINH_KHUVUC, strEntity),currentActivity);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dv0DanhMucDiaChinhResponse;
    }

    public GetDichVu0DanhMucDiaChinh_QuanHuyenPhuongXaResponse getDichVu0DanhMucDiaChinh_QuanHuyenPhuongXaResponse(Context context, String tokenType, String token, AParent currentActivity) {
        GetDichVu0DanhMucDiaChinh_QuanHuyenPhuongXaResponse dichVu0DanhMucDiaChinh_QuanHuyenPhuongXaResponse = null;
        try {
            String strEntity = tokenType + " " + token;
            WebserviceProcessing WP = new WebserviceProcessing();
            dichVu0DanhMucDiaChinh_QuanHuyenPhuongXaResponse = new GetDichVu0DanhMucDiaChinh_QuanHuyenPhuongXaResponse(context,WP.doGetString(GlobalVariable.URL_GET_DV_DANH_MUC_DIA_CHINH_QUANHUYENPHUONGXA, strEntity),currentActivity);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dichVu0DanhMucDiaChinh_QuanHuyenPhuongXaResponse;
    }

    public GetDichVu1ThayDoiTTKHResponse getDvThayDoiTTKHResponse(Context context, String DiDong1, String Email, String IdKH, String authorization, AParent currentActivity){
        GetDichVu1ThayDoiTTKHResponse getDvThayDoiTTKHResponse = null;
        try {
            String strEntityLogin = "DiDong1="+DiDong1+"&Email=" + Email+"&IdKH=" + IdKH;
            String contentType = "application/x-www-form-urlencoded";
            WebserviceProcessing WP = new WebserviceProcessing();
            getDvThayDoiTTKHResponse = new GetDichVu1ThayDoiTTKHResponse(context, WP.doPostString(GlobalVariable.URL_GET_DV_DANH_MUC_THAYDOI_TTKH, strEntityLogin, contentType,authorization),currentActivity);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getDvThayDoiTTKHResponse;
    }

    public GetDichVu0DvYeuCausResponse getDichVu0DvYeuCausResponse(Context context, String LoaiYC,String TenKH,String CMND,String Email, String SDT,String SoNha,String DuongPho,String MaPhuong,String MaKV,String NoiDung, String authorization, AParent currentActivity) {

        GetDichVu0DvYeuCausResponse dichvu0DvYeuCausResponse = null;
        try {
            String strEntityLogin = "LoaiYC="+LoaiYC+"&TenKH="+ TenKH+"&CMND=" + CMND+"&Email=" + Email+"&SDT="+SDT+"&SoNha=" + SoNha  +"&DuongPho=" + DuongPho +"&MaPhuong=" + MaPhuong+"&MaKV=" + MaKV+"&NoiDung=" + NoiDung;
            String contentType = "application/x-www-form-urlencoded";
            WebserviceProcessing WP = new WebserviceProcessing();
            dichvu0DvYeuCausResponse = new GetDichVu0DvYeuCausResponse(context, WP.doPostString(GlobalVariable.URL_GET_DV_DANH_MUC_DV_YEUCAUS, strEntityLogin, contentType,authorization),currentActivity);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dichvu0DvYeuCausResponse;
    }

    public GetDichVu3DvCauHoisResponse getDichVu3DvCauHois(Context context, String tokenType, String token, AParent currentActivity) {
        GetDichVu3DvCauHoisResponse dichVu3DvCauHoisResponse = null;
        try {
            String strEntity = tokenType + " " + token;
            WebserviceProcessing WP = new WebserviceProcessing();
            dichVu3DvCauHoisResponse = new GetDichVu3DvCauHoisResponse(context,WP.doGetString(GlobalVariable.URL_GET_DV_DANH_MUC_DV_CAUHOIS_GETALL, strEntity),currentActivity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dichVu3DvCauHoisResponse;
    }

    public GetDichVu3DvCauHois_PostResponse getDichVu3DvCauHois_PostResponse(Context context, String TieuDe, String NoiDung, String authorization, AParent currentActivity) {

        GetDichVu3DvCauHois_PostResponse dichvu3DvCauHois_PostResponse = null;
        try {
            String strEntityLogin = "TieuDe="+TieuDe+"&NoiDung="+ NoiDung;
            String contentType = "application/x-www-form-urlencoded";
            WebserviceProcessing WP = new WebserviceProcessing();
            dichvu3DvCauHois_PostResponse = new GetDichVu3DvCauHois_PostResponse(context, WP.doPostString(GlobalVariable.URL_GET_DV_DANH_MUC_DV_CAUHOIS_CREATE, strEntityLogin, contentType,authorization),currentActivity);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dichvu3DvCauHois_PostResponse;
    }

    public GetDangKyResponse getDangKyResponse(Context aDangKy, String maKhachHang, String username, String password, String confirmpassword, String authorization, AParent currentActivity) {
        GetDangKyResponse getDangKyResponse = null;
        try{
            String strEntityDangKy = "idkh=" + maKhachHang + "&username=" + username + "&password=" + password + "&confirmpassword=" + confirmpassword ;
            String contentType = "application/x-www-form-urlencoded";
            WebserviceProcessing WP = new WebserviceProcessing();
            getDangKyResponse = new GetDangKyResponse(aDangKy, WP.doPostString(GlobalVariable.URL_DANGKY, strEntityDangKy,contentType,authorization),currentActivity);
        } catch (ClientProtocolException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return getDangKyResponse;
    }

    public GetResetPasswordResponse getResetPassword(Context context, String username, String authorization, AParent currentActivity){
        GetResetPasswordResponse getResetPasswordResponse = null;
        try{
            String strEntityResetPassword = "username=" + username;
            String contentType = "application/x-www-form-urlencoded";
            WebserviceProcessing WP = new WebserviceProcessing();
            getResetPasswordResponse = new GetResetPasswordResponse(context, WP.doPostString(GlobalVariable.URL_RESETPASSWORD, strEntityResetPassword,contentType,authorization),currentActivity);
        } catch (ClientProtocolException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return getResetPasswordResponse;
    }

    public GetTextFromBitmapAPIResponse getTextFromBitmapAPIResponse(Context context, String base64, String authorization){
        GetTextFromBitmapAPIResponse getTextFromBitmapAPIResponse = null;
        try{
            JSONObject c = new JSONObject();
            String imagename = "imagename.jpg";
            String number_actual = "";
            String imagedata = base64;
            try {
                c.put("imagename",imagename);
                c.put("number_actual",number_actual);
                c.put("imagedata",imagedata);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String contentType = "application/json";

            ArrayList postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("token", "huewaco_4Gu8VUEpTF3tBYx935Nz"));

            WebserviceProcessing WP = new WebserviceProcessing();
            getTextFromBitmapAPIResponse = new GetTextFromBitmapAPIResponse(context, WP.doPostStringAPITextImg(GlobalVariable.URL_GET_TEXT_FROM_IMG, c,contentType,authorization,postParameters));
            //getTextFromBitmapAPIResponse = new GetTextFromBitmapAPIResponse(context, WP.doPostAPIAIIMG_NEW(GlobalVariable.URL_GET_TEXT_FROM_IMG, c));
        } catch (Exception e){
            e.printStackTrace();
        }
        return getTextFromBitmapAPIResponse;
    }
    public GetDichVuGCSResponse getDichVuGCSResponse(Context context, String tokenType, String token, AParent currentActivity) {
        GetDichVuGCSResponse getDichVuGCSResponse = null;
        try {
            String strEntity = tokenType + " " + token;
            WebserviceProcessing WP = new WebserviceProcessing();
            String url = GlobalVariable.URL_DICHVU_GCS_4KYCUOI;
            getDichVuGCSResponse = new GetDichVuGCSResponse(context,WP.doGetString(url, strEntity),currentActivity);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getDichVuGCSResponse;
    }
    public GetDichVuGCSResponse getDichVuGCSResponse2(Context context, String tokenType, String token, AParent currentActivity, String ids, String isolder, String record,String idkh) {
        GetDichVuGCSResponse getDichVuGCSResponse = null;
        try {
            List<NameValuePair> params = new LinkedList<NameValuePair>();
            if(CommonHelper.checkValidString(ids)){
                params.add(new BasicNameValuePair("id", String.valueOf(ids)));
            }
            if(CommonHelper.checkValidString(isolder)){
                params.add(new BasicNameValuePair("isolder", String.valueOf(isolder)));
            }
            if(CommonHelper.checkValidString(record)){
                params.add(new BasicNameValuePair("record", String.valueOf(record)));
            }
            params.add(new BasicNameValuePair("idkh", idkh));
            String strEntity = tokenType + " " + token;
            WebserviceProcessing WP = new WebserviceProcessing();
            getDichVuGCSResponse = new GetDichVuGCSResponse(context,WP.doGetString2(GlobalVariable.URL_DICHVU_GCS_4KYCUOI, strEntity,params),currentActivity);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getDichVuGCSResponse;
    }
    public GetDichVuGCSResponse getDichVuGCSLoadMoreResponse(Context context, String tokenType, String token, AParent currentActivity, int ids, boolean isolder, int record){
        GetDichVuGCSResponse getDichVuGCSResponse = null;

        try {
            List<NameValuePair> params = new LinkedList<NameValuePair>();
            params.add(new BasicNameValuePair("id", String.valueOf(ids)));
            params.add(new BasicNameValuePair("isolder", String.valueOf(isolder)));
            params.add(new BasicNameValuePair("record", String.valueOf(record)));

            String strEntity = tokenType + " " + token;
            WebserviceProcessing WP = new WebserviceProcessing();
            getDichVuGCSResponse = new GetDichVuGCSResponse(context,WP.doGetStringParameters(GlobalVariable.URL_DICHVU_GCS_4KYCUOI,params),currentActivity);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getDichVuGCSResponse;
    }
    public GetTextFromBitmapAPI_HWC_Response getTextFromBitmapAPI_HWC_Response(Context context, String base64, String tokenType, String token, AParent currentActivity){
        GetTextFromBitmapAPI_HWC_Response getTextFromBitmapAPIResponse = null;
        try{
            String authorization = tokenType + " " + token;
            String contentType = "application/json";
            JSONObject c = new JSONObject();
            String imagename = "imagename.jpg";
            String number_actual = "";
            String imagedata = base64;
            try {
                c.put("imagename",imagename);
                c.put("number_actual",number_actual);
                c.put("imagedata",imagedata);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            ArrayList postParameters = null;


            WebserviceProcessing WP = new WebserviceProcessing();
            getTextFromBitmapAPIResponse = new GetTextFromBitmapAPI_HWC_Response(context, WP.doPostStringAPITextImg(GlobalVariable.URL_GET_TEXT_FROM_IMG_HWC, c,contentType,authorization,postParameters),currentActivity);
        } catch (Exception e){
            e.printStackTrace();
        }
        return getTextFromBitmapAPIResponse;
    }
    public GetDichVuGCS_Send_Data_Response getDichVuGCS_Send_Data_Response(Context context, ThongTinKHObj thongTinKHObj, String tokenType, String token){
        GetDichVuGCS_Send_Data_Response getDichVuGCS_Send_Data_Response = null;
        try{
            String strEntity = tokenType + " " + token;
            String contentType = "application/json";
            JSONObject c = new JSONObject();

            try {
                c.put("Idkh",thongTinKHObj.getIdkh());
                c.put("NgayNhap_Tt",thongTinKHObj.getNgayNhap_Tt());
                c.put("NgayNhap",thongTinKHObj.getNgayNhap());
                c.put("ChiSoDau",thongTinKHObj.getChiSoDau());
                c.put("ChiSoCuoi",null);
                if(CommonHelper.checkValidString(thongTinKHObj.getChiSoCuoi_NhapTay())){
                    c.put("ChiSoCuoi_NhapTay",thongTinKHObj.getChiSoCuoi_NhapTay());
                }else{
                    c.put("ChiSoCuoi_NhapTay",null);
                }
                if(CommonHelper.checkValidString(thongTinKHObj.getChiSoCuoi())){
                    c.put("ChiSoCuoi_Ai",thongTinKHObj.getChiSoCuoi());
                }else{
                    c.put("ChiSoCuoi_Ai",null);
                }

                c.put("KhoiLuongTieuThu",thongTinKHObj.getKlTieuThu());
                c.put("TongTien",thongTinKHObj.getTongTien());
                c.put("Image_Url_1",null);
                c.put("Image_Url_2",null);
                c.put("Image_Url_3",null);
                c.put("IsDaXacThuc",null);
                c.put("MaNvXacThuc",null);
                c.put("NgayXacThuc",null);
                c.put("IsDaMangTinhTien",null);
                c.put("MaNVMangTinhTien",null);
                c.put("NgayMangTinHTien",null);
                c.put("GhiChiSoAppCskhImage",thongTinKHObj.getjSONArray());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            WebserviceProcessing WP = new WebserviceProcessing();
            getDichVuGCS_Send_Data_Response = new GetDichVuGCS_Send_Data_Response(context, WP.doPostStringAPITextImg(GlobalVariable.URL_DICHVU_GCS_SEND_DATA, c,contentType,strEntity,null));
        } catch (ClientProtocolException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return getDichVuGCS_Send_Data_Response;
    }
    //HDDT
    public PostHddtResponse postOTP(Context context, String maDDK, String mobile, String image,String signerName, String signerNote, String authorization, AParent currentActivity) {
        PostHddtResponse postOTPResponse = null;
        try{
            //String strEntityOTP = "maDDK=" + maDDK + "&mobile=" + mobile  + "&imageData=" + image ;
            JSONObject c = new JSONObject();

            try {
                c.put("maDDK",maDDK);
                c.put("mobile",mobile);
                c.put("imageData",image);
                c.put("customerSignerName",signerName);
                c.put("customerSignerNote",signerNote);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String contentType = "application/json";
            ArrayList postParameters = null;
            WebserviceProcessing WP = new WebserviceProcessing();
            postOTPResponse = new PostHddtResponse(context, WP.doPostStringAPITextImg(GlobalVariable.URL_POST_DV_HDDT_OTP, c,contentType,authorization,postParameters),currentActivity);
        } catch (ClientProtocolException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return postOTPResponse;
    }
    public PostHddtResponse postResendOTP(Context context, String maDDK, String mobile, String authorization, AParent currentActivity) {
        PostHddtResponse postResendOTPResponse = null;
        try{
            String strEntityOTP = "maDDK=" + maDDK + "&mobile=" + mobile ;
            String contentType = "application/x-www-form-urlencoded";
            WebserviceProcessing WP = new WebserviceProcessing();
            postResendOTPResponse = new PostHddtResponse(context, WP.doPostString(GlobalVariable.URL_POST_DV_HDDT_REOTP, strEntityOTP,contentType,authorization),currentActivity);
        } catch (ClientProtocolException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return postResendOTPResponse;
    }
    public PostHddtResponse postCheckOTP(Context context, String maDDK, String otpString, String authorization, AParent currentActivity) {
        PostHddtResponse postCheckOTPResponse = null;
        try{
            String strEntityOTP = "maDDK=" + maDDK + "&otpString=" + otpString ;
            String contentType = "application/x-www-form-urlencoded";
            WebserviceProcessing WP = new WebserviceProcessing();
            postCheckOTPResponse = new PostHddtResponse(context, WP.doPostString(GlobalVariable.URL_POST_DV_HDDT_CHECKOTP, strEntityOTP,contentType,authorization),currentActivity);
        } catch (ClientProtocolException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return postCheckOTPResponse;
    }
    public GetHddtStatusResponse getCheckSignedContract(Context context, String maDDK, String authorization, AParent currentActivity){
        GetHddtStatusResponse getCheckHddtResponse = null;
        try{
            List<NameValuePair> params = new LinkedList<NameValuePair>();
            if(CommonHelper.checkValidString(maDDK)){
                params.add(new BasicNameValuePair("maDDK", String.valueOf(maDDK)));
            }


            WebserviceProcessing WP = new WebserviceProcessing();
            getCheckHddtResponse = new GetHddtStatusResponse(context,WP.doGetString2(GlobalVariable.URL_GET_DV_HDDT_CHECKHDDT, authorization,params),currentActivity);

        } catch (ClientProtocolException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return getCheckHddtResponse;
    }
    public GetHddtDetailResponse getHddtDetail(Context context, String maDDK, String authorization, AParent currentActivity){
        GetHddtDetailResponse getCheckHddtResponse = null;
        try{
            List<NameValuePair> params = new LinkedList<NameValuePair>();
            if(CommonHelper.checkValidString(maDDK)){
                params.add(new BasicNameValuePair("maDDK", String.valueOf(maDDK)));
                params.add(new BasicNameValuePair("hasBase64", "true"));
            }


            WebserviceProcessing WP = new WebserviceProcessing();
            getCheckHddtResponse = new GetHddtDetailResponse(context,WP.doGetString2(GlobalVariable.URL_GET_DV_HDDT_DETAIL, authorization,params),currentActivity);

        } catch (ClientProtocolException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return getCheckHddtResponse;
    }
}
