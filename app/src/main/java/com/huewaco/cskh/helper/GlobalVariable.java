/**
 * Created by: Kiet.Duong
 * September-11-2015
 **/
package com.huewaco.cskh.helper;

import android.content.Context;
import android.graphics.Color;

import com.huewaco.cskh.objects.KhachHangObj;

import java.util.ArrayList;

public class GlobalVariable {
    public static final String KEY_GET_VALUE_GCS = "KEY_GET_VALUE_GCS";
    public static final String KEY_GET_VALUE_GCS_FAILED = "KEY_GET_VALUE_GCS_FAILED";
    public static final String KEY_GET_VALUE_GCS_INCORRECT = "KEY_GET_VALUE_GCS_INCORRECT";
    public static final String MY_CHANEL_ID = "MY_CHANEL_ID_HUEWACO";
    public static final String DON_LAP_DAT_URL = "http://huewaco.net.vn/VanBan/donlapdat.pdf";
    public static final int REQUEST_ID_ACCESS_COURSE_FINE_LOCATION = 100;
    public static boolean EXPIRED = false;
    public static String FILE_TYPE = ".txt";
    public static int REQUEST_CALL_PHONE = 0;
    public static String PREFERENCES = "HueWACOCSKH";
    public static final String FOLDERCMR = "HueWaCo_GhiChiSo";
    public static boolean LOG = true;
    public static final String FOLDER = "HueWACOCSKH";
    public static final String KHACH_HANG_FILE = "khachHangFile";
    public static final String KHU_VUC_FILE = "khuVucFile";
    public static final String QUAN_HUYEN_FILE = "quanHuyenFile";
    public static final String KHU_VUC_ASSETS = "khuvuc_obj.json";
    public static final String QUAN_HUYEN_ASSETS = "quan_huyen_obj.json";
    public static int POSITION = 0;
    public static int COLOR_BAR = Color.BLUE;
    public static boolean IS_ANIMATION = true;
    public static boolean IS_ANIMATION_ACTIVITY = true;

    public static String LOGIN_ACCESS_TOKEN = "";
    public static String LOGIN_TOKEN_TYPE = "";

    public static int COLOR_BACKGROUND = Color.WHITE;//0xAA000000;//
    public static int COLOR_TEXT = 0xAA000000;
    public static final String LINK_WEB_CSKH = "http://huewaco.net.vn";

    public static KhachHangObj KHACH_HANG = null;
    public static ArrayList<KhachHangObj> mArrKHang = new ArrayList<KhachHangObj>();
    public static KhachHangObj KHACH_HANG_CURRENT = null;
    public static final String DEVICE_TYPE = "Android";
    public static String DEVICE_TOKEN = "";

//    public static final String DOMAIN = "http://huewaco.net.vn:2121/";//
    //public static final String DOMAIN = "http://huewaco.net.vn:8012/";//
    public static final String DOMAIN = "http://192.168.10.88:4992/";

    public static final String URL_DEVICE_TOKEN_REGISTER = DOMAIN+ "api/token/create";//"api/deviceTokens";//"api/Tokens/{idkh}/{tokenName}/{devideType}";
    public static final String URL_DEVICE__REGISTER_NOTIFICATIONRECEIVE = DOMAIN+ "api/thongbao/register-notification-receive";
    public static final String URL_GET_USER_INFO = DOMAIN+"api/Account/UserInfo";
    public static final String URL_LOGIN = DOMAIN+"api/security/token";

    //tab thong bao
    public static final String URL_TB1_SO_TIN_CHUA_DOC = DOMAIN+"api/thongbao/getsotinchuadoc";
    public static String URL_TB2_NOI_DUNG_THEO_LOAI(int position){
        String url = DOMAIN+"api/thongbao/getnoidungbyloai/";
        switch (position){
            case 0: url+="TBLCN"; break;
            case 1: url+="TTSCMN";break;
            case 2: url+="TTTN";break;
            case 3: url+="TBTT";break;
            case 4: url+="TBNTN";break;
            case 5: url+="TBGCS";break;//ghi chi so
            case 6: url+="TBK";break;
            default: break;
        }
        return url;
    }
    public static final String URL_TB2_NOI_DUNG__ALLTHEO_LOAI = DOMAIN+"api/thongbao/getallnoidungbyloai";
    public static String PARAM_TB2_NOI_DUNG_ALL_THEO_LOAI(int position){
        String paramname = "";
        switch (position){
            case 0: paramname="TBLCN"; break;
            case 1: paramname="TTSCMN";break;
            case 2: paramname="TTTN";break;
            case 3: paramname="TBTT";break;
            case 4: paramname="TBNTN";break;
            case 5: paramname="TBGCS";break;//ghi chi so
            case 6: paramname="TBK";break;
            default: break;
        }
        return paramname;
    }
    public static int TONG_TIN_CHUA_DOC = 0;
    public static final String URL_TB3_READ = DOMAIN+"api/thongbao/modify/readalready/";
    public static final String URL_TB3_DELETE = DOMAIN+"api/thongbao/modify/deletealready/";

    //tab tra cuu
    public static String URL_TC0_LICH_GHI_NUOC = "";
    public static final String URL_TC0_LICH_GHI_NUOC_4KY_GANNHAT = DOMAIN+"api/tracuu/lichghichiso/getlast4ky";
    public static final String URL_TC0_LICH_GHI_NUOC_TUNGAY_DENNGAY = DOMAIN+"api/tracuu/lichghichiso/getbydaterange";
    //
    public static final String URL_TC1_THONG_TIN_DIEM_THU = DOMAIN+"api/tracuu/diemthu/getall";
    public static final String URL_TC2_LICH_CAT_NUOC = DOMAIN+"api/tracuu/lichcatnuoc/getall";//"api/lichcatnuoc";
    public static final String URL_TC3_CHI_SO_NUOC_TIEU_THU = DOMAIN+"api/tracuu/khoiluongtieuthu/getlast4ky";//"api/luongnuoctieuthu";
    //
    public static String URL_TC4_THONG_TIN_THANH_TOAN_TIEN_NUOC ="";
    public static final String URL_TC4_THONG_TIN_THANH_TOAN_TIEN_NUOC_4KY = DOMAIN+"api/tracuu/thanhtoan/getlast4ky";//"api/thongtintiennuoc";
    public static final String URL_TC4_THONG_TIN_THANH_TOAN_TIEN_NUOC_TUNGAY_DENNGAY = DOMAIN+"api/tracuu/thanhtoan/getbydaterange";//"api/thongtintiennuoc";
    public static final String URL_TC4_THONG_TIN_THANH_TOAN_TIEN_NUOC_TUNGAY_DENNGAYALL = DOMAIN+"api/tracuu/thanhtoan/getallbydaterange";//"api/thongtintiennuoc";
    //
    public static String URL_TC5_HOADON="";
    public static final String URL_TC5_HOADON_LAST = DOMAIN+"api/tracuu/hoadon/getlast";//"api/thongtintiennuoc";
    public static final String URL_TC5_HOADON_BYKY = DOMAIN+"api/tracuu/hoadon/getbyky";//"api/thongtintiennuoc";
    //tab dich vu
    public static final String URL_GET_DV_DANH_MUC_DIA_CHINH_KHUVUC = DOMAIN+"api/dichvu/danhmucdiachinh/khuvuc/getall";
    public static final String URL_GET_DV_DANH_MUC_DIA_CHINH_QUANHUYENPHUONGXA = DOMAIN+"api/dichvu/danhmucdiachinh/quanphuong/getall";
    public static final String URL_GET_DV_DANH_MUC_THAYDOI_TTKH = DOMAIN+"api/dichvu/thongtinkhachhang/modify";
    public static final String URL_GET_DV_DANH_MUC_DV_YEUCAUS = DOMAIN+"api/dichvu/yeucau/create";
    public static final String URL_GET_DV_DANH_MUC_DV_CAUHOIS_GETALL = DOMAIN+"api/dichvu/cauhoi/getall";
    public static final String URL_GET_DV_DANH_MUC_DV_CAUHOIS_CREATE = DOMAIN+"api/dichvu/cauhoi/create";
    public static final String URL_POST_DV_HDDT_OTP= DOMAIN + "api/dichvu/econtract/get-otp";
    public static final String URL_POST_DV_HDDT_REOTP= DOMAIN + "api/dichvu/econtract/resend-otp";
    public static final String URL_POST_DV_HDDT_CHECKOTP= DOMAIN + "api/dichvu/econtract/check-otp";
    public static final String URL_GET_DV_HDDT_CHECKHDDT= DOMAIN + "api/dichvu/econtract/check-econtract-signed";
    public static final String URL_GET_DV_HDDT_DETAIL= DOMAIN + "api/dichvu/econtract/get-econtract-detail";
    // tab cai dat
    public static final String URL_CAIDAT_CHANGE_PASSWORD = DOMAIN+"api/Account/ChangePassword";
    public static final String URL_CAIDAT_LOGOUT = DOMAIN+"api/Account/Logout";
    public static final String URL_RESETPASSWORD = DOMAIN+"api/Account/Resetpassword";
    public static final String URL_DANGKY = DOMAIN+"api/Account/Register";

    //GD2
    public static final String VERSION_API = "1";
    public static final String URL_GET_TEXT_FROM_IMG = "http://aiapi.ekgis.vn/watermeter/1/reading";// "http://ai.ekgis.vn/watermeter/"+VERSION_API+"/reading";
    public static final String URL_GET_TEXT_FROM_IMG_HWC = DOMAIN + "api/dichvu/khachhangghichiso/getchisotuai";
    public static final String ORIGIN_DOMAIN_SHOW_IMG = "http://ai.ekgis.vn";

    public static final String DOMAIN_IMAGE_URL_BCSC = DOMAIN + "BaoCaoSuCoImage/";
    public static final String DOMAIN_IMAGE_URL_GCS = DOMAIN + "GhiChiSoAppCSKHImage/";
    public static final String CAP_MOI = "YCCM";
    public static final String CAP_MOI_NH = "YCNH";
    public static final String CAP_MOI_NANG_DOI = "YCSC";

//    public static final String URL_BAOCAOSUCO_GETALL = "http://huewaco.net.vn:2121/api/baocaosuco/getlist";
//    public static final String URL_BAOCAOSUCO_CREATE = "http://huewaco.net.vn:2121/api/baocaosuco/create";

    public static final String URL_BAOCAOSUCO_GETALL = DOMAIN+"api/baocaosuco/getlist";
    public static final String URL_BAOCAOSUCO_CREATE = DOMAIN+"api/baocaosuco/create";
    public static final String URL_DICHVU_GCS_4KYCUOI = DOMAIN+"api/dichvu/khachhangghichiso/getlast4ky";

    public static final String URL_DICHVU_GCS_GET_TIME_SERVER_NOW = DOMAIN+"api/dichvu/khachhangghichiso/getserverdatetimenow";

    public static final String URL_DICHVU_GCS_GET_THONGTIN_KH = DOMAIN+"api/dichvu/khachhangghichiso/thongtinkhachhangcanghichiso";
    public static final String URL_DICHVU_GCS_GET_TINHTIEN = DOMAIN+"api/dichvu/khachhangghichiso/getketquaghichiso";
    public static final String URL_DICHVU_GCS_GET_KH_THUOCDIEN_GCS = DOMAIN+"api/dichvu/khachhangghichiso/getkhachhangthuocdiencanghikhong";
    public static final String URL_DICHVU_GCS_SEND_DATA = DOMAIN+"api/dichvu/khachhangghichiso/savethongtinghichiso";
    public static void logOut(Context context){
        ShareReferenceConfig.instance(context).setAccessToken("");
        ShareReferenceConfig.instance(context).setTokenType("");
//        ShareReferenceConfig.instance(context).setDeviceToken("");
//        ShareReferenceConfig.instance(context).setPassWord("");
//        ShareReferenceConfig.instance(context).setUserName("");
        CommonHelper.writeKhachHangObjects(context,null,GlobalVariable.KHACH_HANG_FILE);
        KHACH_HANG = null;
        EXPIRED = false;
        LOGIN_ACCESS_TOKEN = "";
        LOGIN_TOKEN_TYPE = "";
    }
//    public static String USERNAME = "";//for auto set at login screen
//    public static String PASSWORD = "";//for auto set at login screen
    public static int REQUEST_LOCATION = 999;
    public static int REQUEST_WRITE_STORAGE = 1000;
    public static int REQUEST_CAMERA = 1001;
    public static int TAKE_PHOTO = 1002;
    public static int GALERRY = 1003;
    public static final int BUTTON_OK_NOT_SHOW = 1004;
    public static final int BUTTON_OK_SHOW = 1005;
    public static final int DO_NOTHING = -1000;
    public static final int SEND_DATA_GCS = 1006;
    public static final int SEND_DATA_SUCO = 1007;
    public static final int DO_INPUTVERIFY = 1008;
    public static boolean IS_GCS = false;
    public static boolean IS_SUCO = false;
    public static int MAXIMAGESIZE = 1024;
    public static int MAXIMAGESIZE_AI = 700;

    public static final String TRANGTHAI_GCS_BT = "GDH_BT";
}
