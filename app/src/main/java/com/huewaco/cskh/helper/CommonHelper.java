/**
 * Created by: Kiet.Duong
 * September-11-2015
 **/
package com.huewaco.cskh.helper;

import static android.provider.Settings.System.getString;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
//import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.customview.edittext.CustomEditText;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.huewaco.cskh.activity.ABaoCaoSuCo;
import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.fragment.FParent;
import com.huewaco.cskh.fragment.FTabTraCuu0LichGhiNuoc;
import com.huewaco.cskh.interfacex.ITF_3Btn_AI;
import com.huewaco.cskh.interfacex.ITF_DialogButtonHddtProcessing;
import com.huewaco.cskh.interfacex.ITF_DialogButtonProcessing;
import com.huewaco.cskh.objects.DiemThuTienListItemObj;
import com.huewaco.cskh.objects.KhachHangObj;
import com.huewaco.cskh.objects.KhuVucListItemObj;
import com.huewaco.cskh.objects.KhuVucObj;
import com.huewaco.cskh.objects.PhuongXaListItmObj;
import com.huewaco.cskh.objects.QuanHuyenListItemObj;
import com.huewaco.cskh.objects.TypeDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;

public class CommonHelper {
    public static String getStringWithSeparatorThousand(double number) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);
        return formatter.format(number);
        //System.out.println(formatter.format(bd.longValue()));
    }

    public static Bitmap scaleImgDown(Bitmap realImage, float maxImageSize,
                                      boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

    public static Bitmap convertBase64ToBitmap_old(String base64) {
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public static Bitmap base64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

    public static String bitmapToBase64(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }
    public static byte[] pdfBase64ToByte(String b64){
        return Base64.decode(b64.getBytes(), Base64.DEFAULT);
    }
    public static Bitmap getBitmapFromAsset(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();

        InputStream is;
        Bitmap bitmap = null;
        try {
            is = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static boolean validateGCS(String str) {
        boolean chk = true;
        if (str.length() >= 5) {
            char[] strC = str.toCharArray();
            for (char c : strC) {
                if (!Character.isDigit(c)) {
                    return false;
                }
            }
        } else {
            chk = false;
        }
        return chk;
    }

    public static boolean isShowingDialogSuCo = false;

    public static void showDAGBaoCaoSuCo(Activity context, String title, int type, final ITF_DialogButtonProcessing itfDialogButtonProcessing, final int typeOk, final int typeCancel) {

        if (!isShowingDialogSuCo) {
            isShowingDialogSuCo = true;
            final Dialog dialogSL = new Dialog(
                    context,
                    android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);

            LayoutInflater inf = LayoutInflater.from(context);
            View layout = inf.inflate(R.layout.dialog_baocaosuco,
                    null);


            //layout.setFocusableInTouchMode(false);
            dialogSL.setContentView(layout);
            dialogSL.setCanceledOnTouchOutside(false);
            dialogSL.setCancelable(false);
            TextView id_tv_title = (TextView) layout.findViewById(R.id.id_tv_title);
            if (checkValidString(title)) {
                id_tv_title.setText(title);
            }

            final CustomEditText id_edt_tenkh = (CustomEditText) layout.findViewById(R.id.id_edt_tenkh_vlname);
            final CustomEditText id_edt_dtlienhe = (CustomEditText) layout.findViewById(R.id.id_edt_dtlienhe_kh_vl);
            final CustomEditText id_edt_diachi_vanglai = (CustomEditText) layout.findViewById(R.id.id_edt_diachi_vanglai);
            final CustomEditText id_edt_bcsc_hdtvitrisuco = (CustomEditText) layout.findViewById(R.id.id_edt_bcsc_hdtvitrisuco);
            View id_ly_tenkh = (View) layout.findViewById(R.id.id_ly_tenkh_vl);
            View id_ly_dt = (View) layout.findViewById(R.id.id_ly_dt_kh_vl);
            LinearLayout id_ly_diachi_vanglai = (LinearLayout) layout.findViewById(R.id.id_ly_diachi_vanglai);

            /*
            if(GlobalVariable.KHACH_HANG != null){
                id_ly_diachi_vanglai.setVisibility(View.GONE);
                if(!CommonHelper.checkValidString(GlobalVariable.KHACH_HANG.getDiDong())){
                    id_ly_tenkh.setVisibility(View.GONE);
                    id_edt_dtlienhe.requestFocus();
                }else{
                    id_edt_tenkh.requestFocus();

                }
            }else{
                id_ly_diachi_vanglai.setVisibility(View.VISIBLE);
            }
            */
            id_ly_diachi_vanglai.setVisibility(View.VISIBLE);
            if (GlobalVariable.KHACH_HANG != null) {
                if (CommonHelper.checkValidString(GlobalVariable.KHACH_HANG.getTenKhachHang())) {
                    id_edt_tenkh.setText(GlobalVariable.KHACH_HANG.getTenKhachHang());
                }
                if (CommonHelper.checkValidString(GlobalVariable.KHACH_HANG.getDiDong())) {
                    id_edt_dtlienhe.setText(GlobalVariable.KHACH_HANG.getDiDong());
                }
                String diachix = GlobalVariable.KHACH_HANG.getSoNha() + ", " + GlobalVariable.KHACH_HANG.getDuongPho() + ", " + GlobalVariable.KHACH_HANG.getPhuongXa() + ", " + GlobalVariable.KHACH_HANG.getQuanHuyen();
                id_edt_diachi_vanglai.setText(diachix);
            }

            final Button id_btn_ok = (Button) layout.findViewById(R.id.id_btn_close_dialog_bcsc_ng);

            final Button id_btn_cancel = (Button) layout.findViewById(R.id.id_btn_cancel);


            if (type == GlobalVariable.BUTTON_OK_NOT_SHOW) {
                id_btn_ok.setVisibility(View.GONE);
            } else if (type == GlobalVariable.BUTTON_OK_SHOW) {
                id_btn_ok.setVisibility(View.VISIBLE);
            }


            id_btn_ok.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String sdt = "", tenhk = "", diachi = "", hdtimViTriSuCo = "";
                    sdt = id_edt_dtlienhe.getText().toString().trim();
                    tenhk = id_edt_tenkh.getText().toString().trim();
                    diachi = id_edt_diachi_vanglai.getText().toString().trim();
                    hdtimViTriSuCo = id_edt_bcsc_hdtvitrisuco.getText().toString().trim();
                    boolean chk = true;
                    /*
                    if(GlobalVariable.KHACH_HANG != null){
                        if(!CommonHelper.checkValidString(sdt)){
                            chk = false;
                            id_edt_dtlienhe.setBackgroundResource(R.drawable.border_bg_red2);
                        }else{
                            id_edt_dtlienhe.setBackgroundResource(R.drawable.border_bg_edit);
                        }
                    }else{
                        if(!CommonHelper.checkValidString(sdt)){
                            chk = false;
                            id_edt_dtlienhe.setBackgroundResource(R.drawable.border_bg_red2);
                        }else{
                            id_edt_dtlienhe.setBackgroundResource(R.drawable.border_bg_edit);
                        }
                        if(!CommonHelper.checkValidString(tenhk)){
                            chk = false;
                            id_edt_tenkh.setBackgroundResource(R.drawable.border_bg_red2);
                        }else{
                            id_edt_tenkh.setBackgroundResource(R.drawable.border_bg_edit);
                        }
                    }
                    */
                    if (!CommonHelper.checkValidString(sdt)) {
                        chk = false;
                        id_edt_dtlienhe.setBackgroundResource(R.drawable.border_bg_red2);
                    } else {
                        id_edt_dtlienhe.setBackgroundResource(R.drawable.border_bg_edit);
                    }
                    if (!CommonHelper.checkValidString(tenhk)) {
                        chk = false;
                        id_edt_tenkh.setBackgroundResource(R.drawable.border_bg_red2);
                    } else {
                        id_edt_tenkh.setBackgroundResource(R.drawable.border_bg_edit);
                    }
                    if (chk) {
                        if (GlobalVariable.KHACH_HANG != null) {
                            GlobalVariable.KHACH_HANG.setDiDong(sdt);
                        }
                        dialogSL.dismiss();
                        isShowingDialogSuCo = false;
                        itfDialogButtonProcessing.okBtn(typeOk, tenhk, sdt, diachi, hdtimViTriSuCo);
                    } else {

                    }
                }

            });
            id_btn_cancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialogSL.dismiss();
                    isShowingDialogSuCo = false;
                    itfDialogButtonProcessing.cancelBtn(typeCancel);

                }

            });
            dialogSL.show();
        }

    }

    public static void showDAGKyHddt(Activity context, final ITF_DialogButtonHddtProcessing itfDialogButtonProcessing) {

        final Dialog dialogSL = new Dialog(
                context,
                android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);

        LayoutInflater inf = LayoutInflater.from(context);
        View layout = inf.inflate(R.layout.dialog_kyhddt,
                null);


        //layout.setFocusableInTouchMode(false);
        dialogSL.setContentView(layout);
        dialogSL.setCanceledOnTouchOutside(true);
        dialogSL.setCancelable(true);


        final Button id_btn_verify = (Button) layout.findViewById(R.id.id_btn_close_dialog_bcsc_ng);
        final Button id_btn_resend = (Button) layout.findViewById(R.id.id_btn_goi_tong_dai_bcsc_ng);
        final com.customview.edittext.CustomEditText id_verifyCode = (com.customview.edittext.CustomEditText) layout.findViewById(R.id.id_verifycode);
        final ImageView id_btn_close = (ImageView) layout.findViewById(R.id.imageView_close);
        id_verifyCode.requestFocus();


        id_btn_verify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialogSL.dismiss();


                itfDialogButtonProcessing.verifyBtn(id_verifyCode.getText().toString());
            }

        });
        id_btn_resend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                itfDialogButtonProcessing.resendBtn();

            }

        });
        id_btn_close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialogSL.dismiss();
            }

        });
        dialogSL.show();

    }
    public static boolean isShowingDialog = false;

    public static void showDAG(Activity context, String title, String str_message, int type, final ITF_DialogButtonProcessing itfDialogButtonProcessing, final int typeOk, final int typeCancel) {

        if (!isShowingDialog) {
            isShowingDialog = true;
            final Dialog dialogSL = new Dialog(
                    context,
                    android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);

            LayoutInflater inf = LayoutInflater.from(context);
            View layout = inf.inflate(R.layout.layout_dialog_write_hhc,
                    null);


            layout.setFocusableInTouchMode(false);
            dialogSL.setContentView(layout);
            dialogSL.setCanceledOnTouchOutside(false);
            dialogSL.setCancelable(false);
            TextView id_tv_title = (TextView) layout.findViewById(R.id.id_tv_title);
            TextView id_tv_message = (TextView) layout.findViewById(R.id.id_tv_message);
            id_tv_message.setText(str_message);
            if (checkValidString(title)) {
                id_tv_title.setText(title);
            }
            final Button id_btn_ok = (Button) layout.findViewById(R.id.id_btn_close_dialog_bcsc_ng);

            final Button id_btn_cancel = (Button) layout.findViewById(R.id.id_btn_cancel);



            if (type == GlobalVariable.BUTTON_OK_NOT_SHOW) {
                id_btn_ok.setVisibility(View.GONE);
            } else if (type == GlobalVariable.BUTTON_OK_SHOW) {
                id_btn_ok.setVisibility(View.VISIBLE);
            }



            id_btn_ok.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialogSL.dismiss();
                    isShowingDialog = false;
                    itfDialogButtonProcessing.okBtn(typeOk);

                }

            });
            id_btn_cancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialogSL.dismiss();
                    isShowingDialog = false;
                    itfDialogButtonProcessing.cancelBtn(typeCancel);

                }

            });
            dialogSL.show();
        }

    }

    public static boolean isShowingDialog3Btn_AI = false;

    public static void showDAG3Btn_AI(Activity context, String title, String str_message, ArrayList<TypeDialog> mArrTypesShow, final ITF_3Btn_AI itfDialogButtonProcessing) {

        if (!isShowingDialog3Btn_AI) {
            isShowingDialog3Btn_AI = true;
            final Dialog dialogSL = new Dialog(
                    context,
                    android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);

            LayoutInflater inf = LayoutInflater.from(context);
            View layout = inf.inflate(R.layout.layout_dialog_write_hhc_3,
                    null);


            layout.setFocusableInTouchMode(false);
            dialogSL.setContentView(layout);
            dialogSL.setCanceledOnTouchOutside(false);
            dialogSL.setCancelable(false);
            TextView id_tv_title = (TextView) layout.findViewById(R.id.id_tv_title);
            TextView id_tv_message = (TextView) layout.findViewById(R.id.id_tv_message);
            id_tv_message.setText(str_message);
            if (checkValidString(title)) {
                id_tv_title.setText(title);
            }
            final Button id_btn_1 = (Button) layout.findViewById(R.id.id_btn_1);
            id_btn_1.setVisibility(View.GONE);
            final Button id_btn_2 = (Button) layout.findViewById(R.id.id_btn_2);
            id_btn_2.setVisibility(View.GONE);
            final Button id_btn_3 = (Button) layout.findViewById(R.id.id_btn_3);
            id_btn_3.setVisibility(View.GONE);
            Map<Integer, Button> mbtn = new HashMap<>();
            mbtn.put(0, id_btn_1);
            mbtn.put(1, id_btn_2);
            mbtn.put(2, id_btn_3);
            if (mArrTypesShow.size() > 0) {
                for (int i = 0; i < mArrTypesShow.size(); i++) {
                    mArrTypesShow.get(i).setButton(mbtn.get(Integer.valueOf(i)));
                }
                for (TypeDialog t : mArrTypesShow) {
                    t.getButton().setText(t.getTitle());
                    t.getButton().setVisibility(View.VISIBLE);//just show button needed
                }
            }

            id_btn_1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialogSL.dismiss();
                    isShowingDialog3Btn_AI = false;
                    itfDialogButtonProcessing.clickBtn(1);

                }

            });
            id_btn_2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialogSL.dismiss();
                    isShowingDialog3Btn_AI = false;
                    itfDialogButtonProcessing.clickBtn(2);

                }

            });
            id_btn_3.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialogSL.dismiss();
                    isShowingDialog3Btn_AI = false;
                    itfDialogButtonProcessing.clickBtn(3);

                }

            });
            dialogSL.show();
        }
    }

    public static void deleteFileOne(String path) {
        if (path != null) {
            try {
                File f = new File(path);
                if (f.delete()) {
                    try {
                        String parentPath = f.getParent();
                        File f2 = new File(parentPath);
                        f.renameTo(f2);
                    } catch (Exception e) {

                    }
                }
            } catch (Exception e) {
                try {
                    File f = new File(path);
                    if (f.delete()) {
                        try {
                            String parentPath = f.getParent();
                            File f2 = new File(parentPath);
                            f.renameTo(f2);
                        } catch (Exception e1) {

                        }
                    }
                } catch (Exception e1) {

                }
            } finally {
                File f = new File(path);
                if (f.delete()) {
                    try {
                        String parentPath = f.getParent();
                        File f2 = new File(parentPath);
                        f.renameTo(f2);
                    } catch (Exception e1) {

                    }
                }
            }
        }
    }

    public static int tabletOrSmartphone(Context context) {
        int vl = 1;
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (manager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
            vl = 1;
        } else {
            vl = 2;
        }
        return vl;
    }


    public static void galleryAddPic(Context ct, String PATH) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(PATH);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        ct.sendBroadcast(mediaScanIntent);
    }

    public static boolean isStoragePermissionGranted(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (activity.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, GlobalVariable.REQUEST_WRITE_STORAGE);
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean isCameraPermissionGranted(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (activity.checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, GlobalVariable.REQUEST_CAMERA);
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean isLocationGPSPermissionGranted(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, GlobalVariable.REQUEST_LOCATION);
                return false;
            }
        } else {
            return true;
        }
    }

    public static void setTextHtml(TextView tv, String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tv.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT));
        } else {
            tv.setText(Html.fromHtml(text));
        }
    }

    public static void openFile(Context ct, String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        ct.startActivity(browserIntent);
    }

    public static void sortDistanceNearest2CurrentCoordinate(ArrayList<DiemThuTienListItemObj> list) {
        if ((list != null) && (list.size() > 0)) {
            Collections.sort(list, new Comparator<DiemThuTienListItemObj>() {
                public int compare(DiemThuTienListItemObj o1, DiemThuTienListItemObj o2) {
                    return Double.compare(o1.getDistance2CurrentCoordinate(), o2.getDistance2CurrentCoordinate());
                }
            });
        }
    }

    /*    public static float distance2Coordinate(double latCurrent, double lngCurrent, double latDes, double lngDes) {
            double earthRadius = 6371000; //meters
            double dLat = Math.toRadians(latDes-latCurrent);
            double dLng = Math.toRadians(lngDes-lngCurrent);
            double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                    Math.cos(Math.toRadians(latCurrent)) * Math.cos(Math.toRadians(latDes)) *
                            Math.sin(dLng/2) * Math.sin(dLng/2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
            float dist = (float) (earthRadius * c);

            return dist;
        }*/
    public static double distance2Coordinate(/*LatLng StartP, LatLng EndP*/double lat1, double lon1, double lat2, double lon2) {
        int Radius = 6371;// radius of earth in Km
/*        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;*/
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        //Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec + " Meter   " + meterInDec);

        return Radius * c;
    }

    //==================END FOR QUAN HUYEN OBJECT===================
    //start read/write quan huyen data from cache
    public static ArrayList<QuanHuyenListItemObj> readQuanHuyenCache(Context ct, String fileName) {
        ArrayList<QuanHuyenListItemObj> getQuanHuyenListItemObj = CommonHelper.readQuanHuyenObjects(ct, fileName);//readKhuVucObjects(ct, fileName);
        if (getQuanHuyenListItemObj != null && getQuanHuyenListItemObj.size() > 0) {
            for (int n = 0; n < getQuanHuyenListItemObj.size(); n++) {
                QuanHuyenListItemObj qh = getQuanHuyenListItemObj.get(n);
                ArrayList<PhuongXaListItmObj> getPhuongXaListItmObj = CommonHelper.readPhuongXaObjects(ct, "PXFILE" + n);// readQuanHuyenObjects(ct, "KVFILE" + n);
                if (getPhuongXaListItmObj != null && getPhuongXaListItmObj.size() > 0) {
                    qh.setmArrPhuongXa(getPhuongXaListItmObj);
                }
            }
        }
        return getQuanHuyenListItemObj;
    }

    public static void writeQuanHuyenCache(Context ct, ArrayList<QuanHuyenListItemObj> mArr, final String fileName) {
        if (mArr != null && mArr.size() > 0) {
            writeQuanHuyenObjects(ct, mArr, fileName);
            for (int i = 0; i < mArr.size(); i++) {
                QuanHuyenListItemObj qh = mArr.get(i);
                if (qh != null) {
                    ArrayList<PhuongXaListItmObj> mArrPX = qh.getmArrPhuongXa();
                    if (mArrPX != null && mArrPX.size() > 0) {
                        writePhuongXaObjects(ct, mArrPX, "PXFILE" + i);
                    }
                }
            }
        }
    }

    //end read/write quan huyen data from cache
    //start load Quan Huyen data from assets
    public static ArrayList<QuanHuyenListItemObj> getArrayQuanHuyenObjFromAssetJsonFile(Context ct, String fileName) {
        ArrayList<QuanHuyenListItemObj> getQuanHuyenListItemObj = null;
        String json = loadJSONFromAsset(ct, fileName);
        getQuanHuyenListItemObj = CommonHelper.getArrayQuanHuyenObjFromJsonString(json);
        return getQuanHuyenListItemObj;
    }

    public static ArrayList<QuanHuyenListItemObj> getArrayQuanHuyenObjFromJsonString(String json) {
        JSONArray jsonArr = null;
        ArrayList<QuanHuyenListItemObj> getQuanHuyenListItemObj = new ArrayList<QuanHuyenListItemObj>();
        try {
            jsonArr = new JSONArray(json);

        } catch (Exception e1) {
            System.out.print(e1.getMessage());
        }
        if (jsonArr != null && jsonArr.length() > 0) {
            for (int n = 0; n < jsonArr.length(); n++) {
                QuanHuyenListItemObj qh = new QuanHuyenListItemObj();
                try {
                    JSONObject obj = jsonArr.getJSONObject(n);
                    if (obj != null) {
                        String MaQuan = "";
                        if (obj.has("MaQuan")) {
                            MaQuan = obj.getString("MaQuan");
                        }
                        String TenQuan = "";//obj.getString("TenQuan");
                        if (obj.has("TenQuan")) {
                            TenQuan = obj.getString("TenQuan");
                        }
                        if (!CommonHelper.checkValidString(MaQuan)) {
                            MaQuan = "";
                        }
                        if (!CommonHelper.checkValidString(TenQuan)) {
                            TenQuan = "";
                        }
                        qh.setMaQuan(MaQuan);
                        qh.setTenQuan(TenQuan);
                        JSONArray jsonArrayPX = null;//obj.getJSONArray("ListOfQuanPhuong");
                        if (obj.has("ListOfPhuong")) {
                            jsonArrayPX = obj.getJSONArray("ListOfPhuong");
                        }
                        ArrayList<PhuongXaListItmObj> mArrPX = new ArrayList<>();
                        if (jsonArrayPX != null && jsonArrayPX.length() > 0) {
                            for (int j = 0; j < jsonArrayPX.length(); j++) {

                                PhuongXaListItmObj px = new PhuongXaListItmObj();
                                try {
                                    JSONObject objPX = jsonArrayPX.getJSONObject(j);
                                    if (objPX != null) {
                                        String MaPhuong = objPX.getString("MaPhuong");
                                        String TenPhuong = objPX.getString("TenPhuong");
                                        if (!CommonHelper.checkValidString(MaPhuong)) {
                                            MaPhuong = "";
                                        }
                                        if (!CommonHelper.checkValidString(TenPhuong)) {
                                            TenPhuong = "";
                                        }
                                        px.setTenPhuong(TenPhuong);
                                        px.setMaPhuong(MaPhuong);

                                    }
                                } catch (Exception e) {
                                    Log.d("abc", " error: " + e.getMessage());
                                }
                                mArrPX.add(px);
                            }

                        }
                        qh.setmArrPhuongXa(mArrPX);
                    }
                } catch (Exception e) {
                    Log.e("abc", "Parse json thu " + n + "fail");
                }

                getQuanHuyenListItemObj.add(qh);
            }
        } else {
            Log.e("abc", "jsonArray null");
        }

        return getQuanHuyenListItemObj;
    }

    //end load Quan Huyen data from assets
    //==================END FOR QUAN HUYEN OBJECT===================

    //==================START FOR KHU VUC OBJECT===================
    //start read/write khuVuc obj to cache
    public static ArrayList<KhuVucObj> readKhuVucObjs(Context ct, String fileName) {
        ArrayList<KhuVucObj> mArr = null;
        try {
            String path = ct.getFilesDir() + "/" + fileName + GlobalVariable.FILE_TYPE;
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            mArr = (ArrayList<KhuVucObj>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return null;
        }
        return mArr;
    }

    public static void writeKhuVucObjs(Context ct, ArrayList<KhuVucObj> mArr, final String fileName) {
        try {
            String path = ct.getFilesDir() + "/" + fileName + GlobalVariable.FILE_TYPE;
            File file = null;
            File[] files = ct.getFilesDir().listFiles();
            if (files == null) {
                file = new File(path);
                file.createNewFile();
            } else {
                boolean chk = false;
                for (File f : files) {
                    if (f.getAbsolutePath().equals(path)) {
                        file = f;
                        chk = true;
                        break;
                    }
                }
                if (!chk) {
                    file = new File(path);
                    file.createNewFile();
                }
            }
            FileOutputStream fileOut =
                    new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(mArr);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //end read/write khuVuc obj to cache
    //start load data khu vuc obj from asset
    public static ArrayList<KhuVucObj> getArrayKhuVucObjFromAssetJsonFile(Context ct, String fileName) {
        ArrayList<KhuVucObj> getKhuVucObjs = null;
        String json = loadJSONFromAsset(ct, fileName);
        getKhuVucObjs = CommonHelper.getArrayKhuVucObjFromJsonString(json);
        return getKhuVucObjs;
    }

    public static ArrayList<KhuVucObj> getArrayKhuVucObjFromJsonString(String json) {
        JSONArray jsonArr = null;
        JSONObject obj = null;
        ArrayList<KhuVucObj> getKhuVucObjs = new ArrayList<>();
        try {
            jsonArr = new JSONArray(json);

        } catch (Exception e1) {
            System.out.print(e1.getMessage());
        }
        if (jsonArr != null && jsonArr.length() > 0) {
            for (int n = 0; n < jsonArr.length(); n++) {
                KhuVucObj kv = new KhuVucObj();
                try {
                    obj = jsonArr.getJSONObject(n);
                    if (obj != null) {
                        String MaKv = "";
                        String TenKv = "";

                        if (obj.has("MaKv")) {
                            MaKv = obj.getString("MaKv");
                        }
                        if (obj.has("TenKv")) {
                            TenKv = obj.getString("TenKv");
                        }
                        if (!CommonHelper.checkValidString(MaKv)) {
                            MaKv = "";
                        }
                        if (!CommonHelper.checkValidString(TenKv)) {
                            TenKv = "";
                        }
                        kv.setMaKv(MaKv);
                        kv.setTenKv(TenKv);
                    }
                } catch (Exception e) {

                }
                getKhuVucObjs.add(kv);
            }
        }
        return getKhuVucObjs;
    }
    //end load data khu vuc obj from asset
    //==================END FOR KHU VUC OBJECT===================

    public static ArrayList<KhuVucListItemObj> getArrayKhuVucFromAssetJsonFile(Context ct, String fileName) {
        ArrayList<KhuVucListItemObj> getKhuVucListItemObjs = null;
        String json = loadJSONFromAsset(ct, fileName);
        getKhuVucListItemObjs = CommonHelper.getArrayKhuVucFromJsonString(json);
        return getKhuVucListItemObjs;
    }

    public static ArrayList<KhuVucListItemObj> getArrayKhuVucFromJsonString(String json) {
        JSONArray jsonArr = null;
        JSONObject obj = null;
        ArrayList<KhuVucListItemObj> getKhuVucListItemObjs = new ArrayList<KhuVucListItemObj>();
        try {
            jsonArr = new JSONArray(json);

        } catch (Exception e1) {
            System.out.print(e1.getMessage());
        }
        if (jsonArr != null && jsonArr.length() > 0) {
            for (int n = 0; n < jsonArr.length(); n++) {
                KhuVucListItemObj kv = new KhuVucListItemObj();
                try {
                    obj = jsonArr.getJSONObject(n);
                    if (obj != null) {
                        String MaKv = obj.getString("MaKv");
                        String TenKv = obj.getString("TenKv");
                        if (!CommonHelper.checkValidString(MaKv)) {
                            MaKv = "";
                        }
                        if (!CommonHelper.checkValidString(TenKv)) {
                            TenKv = "";
                        }
                        kv.setMaKv(MaKv);
                        kv.setTenKv(TenKv);
                        JSONArray jsonArrayQH = null;//obj.getJSONArray("ListOfQuanPhuong");
                        if (obj.has("ListOfQuanPhuong")) {
                            jsonArrayQH = obj.getJSONArray("ListOfQuanPhuong");
                        }
                        ArrayList<QuanHuyenListItemObj> mArrQH = new ArrayList<>();
                        if (jsonArrayQH != null && jsonArrayQH.length() > 0) {
                            for (int i = 0; i < jsonArrayQH.length(); i++) {
                                QuanHuyenListItemObj qh = new QuanHuyenListItemObj();
                                try {
                                    JSONObject objQH = jsonArrayQH.getJSONObject(i);
                                    if (objQH != null) {
                                        String MaQuan = objQH.getString("MaQuan");
                                        String TenQuan = objQH.getString("TenQuan");
                                        if (!CommonHelper.checkValidString(MaQuan)) {
                                            MaQuan = "";
                                        }
                                        if (!CommonHelper.checkValidString(TenQuan)) {
                                            TenQuan = "";
                                        }
                                        qh.setMaQuan(MaQuan);
                                        qh.setTenQuan(TenQuan);

                                        JSONArray jsonArrayPX = null;
                                        if (objQH.has("ListOfPhuong")) {
                                            jsonArrayPX = objQH.getJSONArray("ListOfPhuong");
                                        }

                                        ArrayList<PhuongXaListItmObj> mArrPX = new ArrayList<>();
                                        if (jsonArrayPX != null && jsonArrayPX.length() > 0) {
                                            for (int j = 0; j < jsonArrayPX.length(); j++) {

                                                PhuongXaListItmObj px = new PhuongXaListItmObj();
                                                try {
                                                    JSONObject objPX = jsonArrayPX.getJSONObject(j);
                                                    if (objPX != null) {
                                                        String MaPhuong = objPX.getString("MaPhuong");
                                                        String TenPhuong = objPX.getString("TenPhuong");
                                                        if (!CommonHelper.checkValidString(MaPhuong)) {
                                                            MaPhuong = "";
                                                        }
                                                        if (!CommonHelper.checkValidString(TenPhuong)) {
                                                            TenPhuong = "";
                                                        }
                                                        px.setTenPhuong(TenPhuong);
                                                        px.setMaPhuong(MaPhuong);

                                                    }
                                                } catch (Exception e) {
                                                    Log.d("abc", " error: " + e.getMessage());
                                                }
                                                mArrPX.add(px);
                                            }

                                        }
                                        qh.setmArrPhuongXa(mArrPX);
                                    }
                                } catch (Exception e) {
                                    Log.d("abc", " error: " + e.getMessage());
                                }

                                mArrQH.add(qh);
                            }

                        }
                        kv.setmArrItemQuanHuyen(mArrQH);
                    }
                } catch (Exception e) {
                    Log.e("abc", "Parse json thu " + n + "fail");
                }
                getKhuVucListItemObjs.add(kv);
            }
        } else {
            Log.e("abc", "jsonArray null");
        }

        return getKhuVucListItemObjs;
    }

    public static String loadJSONFromAsset(Context ct, String fileName) {
        String json = null;
        try {
            InputStream is = ct.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    // Doc tu Cache
    public static ArrayList<KhuVucListItemObj> readKhuVucObjAll(Context ct, String fileName) {
        ArrayList<KhuVucListItemObj> getKhuVucListItemObjs = readKhuVucObjects(ct, fileName);
        if (getKhuVucListItemObjs != null && getKhuVucListItemObjs.size() > 0) {
            for (int n = 0; n < getKhuVucListItemObjs.size(); n++) {
                KhuVucListItemObj kv = getKhuVucListItemObjs.get(n);
                ArrayList<QuanHuyenListItemObj> getQuanHuyenListItemObjs = readQuanHuyenObjects(ct, "KVFILE" + n);
                if (getQuanHuyenListItemObjs != null && getQuanHuyenListItemObjs.size() > 0) {
                    kv.setmArrItemQuanHuyen(getQuanHuyenListItemObjs);
                    for (int i = 0; i < getQuanHuyenListItemObjs.size(); i++) {
                        QuanHuyenListItemObj qh = getQuanHuyenListItemObjs.get(i);
                        ArrayList<PhuongXaListItmObj> getPhuongXaListItmObjArrayList = readPhuongXaObjects(ct, "QHFILE" + n + i);
                        if (getPhuongXaListItmObjArrayList != null && getPhuongXaListItmObjArrayList.size() > 0) {
                            qh.setmArrPhuongXa(getPhuongXaListItmObjArrayList);
                        }
                    }
                }
            }
        }
        return getKhuVucListItemObjs;
    }

    public static void writeKhuVucObjAll(Context ct, ArrayList<KhuVucListItemObj> mArr, final String fileName) {
        if (mArr != null && mArr.size() > 0) {
            writeKhuVucObjects(ct, mArr, fileName);
            for (int n = 0; n < mArr.size(); n++) {
                KhuVucListItemObj kv = mArr.get(n);
                if (kv != null) {
                    ArrayList<QuanHuyenListItemObj> mArrQH = kv.getmArrItemQuanHuyen();
                    if (mArrQH != null && mArrQH.size() > 0) {
                        writeQuanHuyenObjects(ct, mArrQH, "KVFILE" + n);
                        for (int i = 0; i < mArrQH.size(); i++) {
                            QuanHuyenListItemObj qh = mArrQH.get(i);
                            if (qh != null) {
                                ArrayList<PhuongXaListItmObj> getmArrPhuongXa = qh.getmArrPhuongXa();
                                if (getmArrPhuongXa != null && getmArrPhuongXa.size() > 0) {
                                    writePhuongXaObjects(ct, getmArrPhuongXa, "QHFILE" + n + i);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static void writeKhuVucObjects(Context ct, ArrayList<KhuVucListItemObj> mArr, final String fileName) {
        try {
            String path = ct.getFilesDir() + "/" + fileName + GlobalVariable.FILE_TYPE;
            File file = null;
            File[] files = ct.getFilesDir().listFiles();
            if (files == null) {
                file = new File(path);
                file.createNewFile();
            } else {
                boolean chk = false;
                for (File f : files) {
                    if (f.getAbsolutePath().equals(path)) {
                        file = f;
                        chk = true;
                        break;
                    }
                }
                if (!chk) {
                    file = new File(path);
                    file.createNewFile();
                }
            }
            FileOutputStream fileOut =
                    new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(mArr);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    private static ArrayList<KhuVucListItemObj> readKhuVucObjects(Context ct, String fileName) {
        ArrayList<KhuVucListItemObj> mArr = null;
        try {
            String path = ct.getFilesDir() + "/" + fileName + GlobalVariable.FILE_TYPE;
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            mArr = (ArrayList<KhuVucListItemObj>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return null;
        }
        return mArr;
    }

    /////////QUAN HUYEN//////////
    private static void writeQuanHuyenObjects(Context ct, ArrayList<QuanHuyenListItemObj> mArr, final String fileName) {
        try {
            String path = ct.getFilesDir() + "/" + fileName + GlobalVariable.FILE_TYPE;
            File file = null;
            File[] files = ct.getFilesDir().listFiles();
            if (files == null) {
                file = new File(path);
                file.createNewFile();
            } else {
                boolean chk = false;
                for (File f : files) {
                    if (f.getAbsolutePath().equals(path)) {
                        file = f;
                        chk = true;
                        break;
                    }
                }
                if (!chk) {
                    file = new File(path);
                    file.createNewFile();
                }
            }
            FileOutputStream fileOut =
                    new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(mArr);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    private static ArrayList<QuanHuyenListItemObj> readQuanHuyenObjects(Context ct, String fileName) {
        ArrayList<QuanHuyenListItemObj> mArr = null;
        try {
            String path = ct.getFilesDir() + "/" + fileName + GlobalVariable.FILE_TYPE;
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            mArr = (ArrayList<QuanHuyenListItemObj>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return null;
        }
        return mArr;
    }

    /////////PHUONG XA//////////
    private static void writePhuongXaObjects(Context ct, ArrayList<PhuongXaListItmObj> mArr, final String fileName) {
        try {
            String path = ct.getFilesDir() + "/" + fileName + GlobalVariable.FILE_TYPE;
            File file = null;
            File[] files = ct.getFilesDir().listFiles();
            if (files == null) {
                file = new File(path);
                file.createNewFile();
            } else {
                boolean chk = false;
                for (File f : files) {
                    if (f.getAbsolutePath().equals(path)) {
                        file = f;
                        chk = true;
                        break;
                    }
                }
                if (!chk) {
                    file = new File(path);
                    file.createNewFile();
                }
            }
            FileOutputStream fileOut =
                    new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(mArr);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    private static ArrayList<PhuongXaListItmObj> readPhuongXaObjects(Context ct, String fileName) {
        ArrayList<PhuongXaListItmObj> mArr = null;
        try {
            String path = ct.getFilesDir() + "/" + fileName + GlobalVariable.FILE_TYPE;
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            mArr = (ArrayList<PhuongXaListItmObj>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return null;
        }
        return mArr;
    }

    ////////KHACH HANG//////////
    public static void writeKhachHangObjects(Context ct, ArrayList<KhachHangObj> mArr, final String fileName) {
        try {
            String path = ct.getFilesDir() + "/" + fileName + GlobalVariable.FILE_TYPE;
            File file = null;
            File[] files = ct.getFilesDir().listFiles();
            if (files == null) {
                file = new File(path);
                file.createNewFile();
            } else {
                boolean chk = false;
                for (File f : files) {
                    if (f.getAbsolutePath().equals(path)) {
                        file = f;
                        chk = true;
                        break;
                    }
                }
                if (!chk) {
                    file = new File(path);
                    file.createNewFile();
                }
            }
            FileOutputStream fileOut =
                    new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(mArr);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    public static ArrayList<KhachHangObj> readKhachHangObjects(Context ct, String fileName) {
        ArrayList<KhachHangObj> mArr = null;
        try {
            String path = ct.getFilesDir() + "/" + fileName + GlobalVariable.FILE_TYPE;
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            mArr = (ArrayList<KhachHangObj>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return null;
        }
        return mArr;
    }

    public static Bitmap showCode128(String data, int width, int height, ImageView mImageView) {
        Map<EncodeHintType, Object> hints = null;
        //String encoding = guessAppropriateEncoding(contentsToEncode);
        if (data != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, data);
        }
        com.google.zxing.Writer code = new Code128Writer();
        Bitmap mBitmap = null;
        try {
            BitMatrix bm = code.encode(data, BarcodeFormat.CODE_128, width, height, hints);
            mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {

                    mBitmap.setPixel(i, j, bm.get(i, j) ? Color.BLACK : Color.WHITE);
                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
        if (mBitmap != null) {
            if (mImageView != null) {
                mImageView.setImageBitmap(mBitmap);
            }
        }
        return mBitmap;
    }

    public static void callPhone(Context context, String phone) {
        try {
            if (CommonHelper.checkValidString(phone)) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone)));
            } else {
                CommonHelper.showWarning(context, context.getString(R.string.khong_phone));
            }
        } catch (Exception e) {
            CommonHelper.showWarning(context, e.getMessage());
        }
    }

    public static boolean isCallPhonePermissionGranted(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (activity.checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        activity,
                        new String[]{Manifest.permission.CALL_PHONE}, GlobalVariable.REQUEST_CALL_PHONE);
                return false;
            } else {
                return true;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.d("abc", "Permission is granted");
            return true;
        }
    }

    public static void openWebCSKH(Context ct, String urlX) {
        if (!urlX.startsWith("http://") && !urlX.startsWith("https://"))
            urlX = "http://" + urlX;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlX));
        ct.startActivity(browserIntent);
    }

    public static void openGooglePlayApp(Context ct) {
        final String appPackageName = ct.getPackageName(); // getPackageName() from Context or Activity object
        try {
            ct.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            ct.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public static String getVersionName(Context ct) {
        String version = "1.0.0";
        try {
            version = ct.getPackageManager().getPackageInfo(ct.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            version = "1.0.0";
            e.printStackTrace();
        }
        return version;
    }

    public static void changeColorForViewPage(View v) {
        v.setBackgroundColor(GlobalVariable.COLOR_BACKGROUND);
    }

    public static void changeColorForViewPage(TextView tv) {
        tv.setTextColor(GlobalVariable.COLOR_TEXT);
    }

    @SuppressWarnings("deprecation")
    public static void changeColorForAdapter(Context context, View v,
                                             int bgColor, int textColor, int type) {
        switch (type) {
            case 0:
                if (((v instanceof TextView) || (v instanceof EditText))) {
                    v.setBackgroundDrawable(CommonHelper.getDrawableChangeColor(
                            context, bgColor));
                    ((TextView) v).setTextColor(textColor);
                }
                break;
            case 1:
                if ((v instanceof ViewGroup)) {
                    v.setBackgroundDrawable(CommonHelper.getDrawableChangeColor(
                            context, bgColor));
                }
                break;
            case 2:
                if (((v instanceof TextView) || (v instanceof EditText))) {
                    ((TextView) v).setTextColor(textColor);
                }
                break;

            default:
                break;
        }
    }

    public static Drawable getDrawableChangeColor(Context context, int color) {
        @SuppressWarnings("deprecation")
        Drawable sampleDrawable = context.getResources().getDrawable(
                R.drawable.border_bg_grey);
        sampleDrawable.setColorFilter(new PorterDuffColorFilter(color,
                PorterDuff.Mode.MULTIPLY));
        return sampleDrawable;
    }


    public static String getTypicalNumberFromString(String number) {
        if (CommonHelper.isNumeric(number)) {
            return new DecimalFormat("0.###").format(Double.parseDouble(number));
        } else {
            return "";
        }
    }

    public static SpannableStringBuilder changeColorError(Context ct, String str) {
        int ecolor = ct.getResources().getColor(R.color.red);
        ForegroundColorSpan fgcspan = new ForegroundColorSpan(ecolor);
        SpannableStringBuilder ssbuilder = new SpannableStringBuilder(str);
        ssbuilder.setSpan(fgcspan, 0, str.length(), 0);
        return ssbuilder;
    }

    public static boolean isNumeric(String str) {
        if (CommonHelper.checkValidString(str)) {
            try {
                Double.parseDouble(str);
            } catch (NumberFormatException nfe) {
                return false;
            }
            return true;
        } else {
            return false;
        }

    }

    public static boolean deleteFile(File file) {
        return file.delete();
    }

    public static boolean deleteFileName(String fileNamePath) {
        File file = new File(fileNamePath);
        return file.delete();
    }


    private static void copyFile(InputStream in, OutputStream out)
            throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }


    public static String getFileName(String url) {
        return URLUtil.guessFileName(url, null, null);
    }

    public static ArrayList<String> getAllFilesOfFolder(Context context,
                                                        File directory) {
        ArrayList<String> mArrs = new ArrayList<String>();
        if (isExternalDiskMounted(context)) {
            mArrs.addAll(getAllFilesOfFoder(directory));
        }
        return mArrs;
    }

    private static ArrayList<String> getAllFilesOfFoder(File directory) {
        ArrayList<String> mArrs = new ArrayList<String>();
        final File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file != null) {
                    if (file.isDirectory()) { // it is a folder...
                        getAllFilesOfFoder(file);
                    } else { // it is a file...
                        mArrs.add(file.getAbsolutePath());
                    }
                }
            }
        }
        return mArrs;
    }

    public static void hideKeyBoardWithEditext(Context context, EditText edt) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edt.getWindowToken(), 0);
    }

    public static void showKeyBoardForce(Activity fpActivity) {
        InputMethodManager imm = (InputMethodManager) fpActivity.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void showKeyBoardWithEditext(Context context, EditText edt) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInputFromInputMethod(edt.getWindowToken(), 0);
    }

    public static void hideKeyboardFrom(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void hiddenKeyBoardOkX(final Activity fpActivity) {
        fpActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fpActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            }
        }, 100);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void writeToSDFile() {
        File root = android.os.Environment.getExternalStorageDirectory();

        File dir = new File(root.getAbsolutePath() + "/"
                + GlobalVariable.FOLDER);
        dir.mkdirs();
        File file = new File(dir, "myData.txt");

        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);
            pw.println("line1 \r\n");
            pw.println("line2");
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isExternalDiskMounted(Context context) {
        boolean chk = true;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            chk = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            chk = false;
        } else {
            chk = false;
        }
        return chk;
    }

    public static void showWarning(Context context, String str_message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.app_name))
                .setMessage(str_message)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false).setPositiveButton("OK", null);
        AlertDialog alert = builder.create();
        alert.show();
        TextView messageView = (TextView) alert
                .findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);
    }

    public static void showToast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean checkValidString(String strx) {
        if (strx == null) {
            return false;
        } else {
            String str = strx.trim();
            return ((!str.equalsIgnoreCase("")) && (!str.isEmpty())
                    && (!str.equalsIgnoreCase("null")) && (!str
                    .equalsIgnoreCase("NULL")));
        }
    }

    public static Date getDateNow() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Calendar c = Calendar.getInstance();
        String date = df.format(c.getTime());
        return getDateFromString(date, "");
    }

    public static int getHourNow() {
        Calendar rightNow = Calendar.getInstance();
        // return the hour in 24 hrs format (ranging from 0-23)
        int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);
        //int currentHourIn12Format = rightNow.get(Calendar.HOUR); // return the hour in 12 hrs format (ranging from 0-11)
        return currentHourIn24Format;
    }

    public static Date getDateFromString(String dateStr, String formatType) {
        DateFormat df;
        if (CommonHelper.checkValidString(formatType)) {
            df = new SimpleDateFormat(formatType);
        } else {
            df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        }

        Date date = new Date();
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getDateString2FromStringDate1(String dateStrIn, String dateStrOut) {
        DateFormat df;
        if (CommonHelper.checkValidString(dateStrOut)) {
            df = new SimpleDateFormat(dateStrOut);
        } else {
            df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        }

        Date date = new Date();
        try {
            date = df.parse(dateStrIn);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return df.format(date);
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
//        String strDate = dateFormat.format(date);
    }

    //convert "yyyy-MM-dd'T'HH:mm:ss" to dateStrOut
    public static String getDateString2FromStringDate2(String dateStrIn, String dateStrOut) {
        DateFormat df;
        if (CommonHelper.checkValidString(dateStrOut)) {
            df = new SimpleDateFormat(dateStrOut, Locale.getDefault());
        } else {
            df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        }

        String inPattern = "yyyy-MM-dd'T'HH:mm:ss";
        DateFormat inFormat = new SimpleDateFormat(inPattern, Locale.getDefault());

        try {
            Date date = inFormat.parse(dateStrIn);
            return df.format(date);
        } catch (ParseException e) {
            return "";
        }
    }

    public static String getTimeNow() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss, dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        return df.format(c.getTime());
    }

    public static boolean isContainString(String str1, String str2) {
        if (str1.indexOf(str2) != -1) {
            return true;
        } else {
            return false;
        }
    }

    // Lấy lịch
    public static boolean isShowDated = false;

    public static void datePicker(final Context context, final CustomEditText edt, final Drawable draw, final FParent fp) {

        if (!isShowDated) {
            DatePickerDialog.OnDateSetListener mListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String str = dayOfMonth + "-" + (monthOfYear + 1) + "-"

                            + year + " ";
                    edt.setText(str);
                    isShowDated = false;
                    if (fp instanceof FTabTraCuu0LichGhiNuoc) {
                        ((FTabTraCuu0LichGhiNuoc) fp).onFinisedGetDatePicker(str);
                    }
                }
            };

            String currentDate =  edt.getText().toString();
            final Calendar c = Calendar.getInstance();
            if(!currentDate.isEmpty()){
                SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy");
                try {
                    Date date = format.parse(currentDate);
                    c.setTime(date);
                } catch (ParseException e) {

                }
            }
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = 1;// c.get(Calendar.DAY_OF_MONTH);
            //DatePickerDialog dpd = new DatePickerDialog(context, R.style.CustomTheme, mListener, mYear, mMonth, mDay);
            DatePickerDialog dpd = new DatePickerDialog(context, R.style.CustomTheme, mListener, mYear, mMonth, mDay){
                final int month = getContext().getResources().getIdentifier("android:id/month", null, null);
                final String[] monthNumbers = new String[]{ "01","02","03","04","05","06","07","08","09","10","11","12"};
                @Override
                public void onDateChanged(@NonNull DatePicker view, int y, int m, int d) {
                    super.onDateChanged(view, y, m, d);
                    // Since DatePickerCalendarDelegate updates the month spinner too, we need to change months as numbers here also
                    if(month != 0){
                        NumberPicker monthPicker = findViewById(month);
                        if(monthPicker != null){
                            monthPicker.setDisplayedValues(monthNumbers);
                        }
                    }
                }
                @Override
                protected void onCreate(Bundle savedInstanceState)
                {
                    super.onCreate(savedInstanceState);
                    // Hide day spinner
                    int day = getContext().getResources().getIdentifier("android:id/day", null, null);
                    if(day != 0){
                        NumberPicker dayPicker = findViewById(day);
                        if(dayPicker != null){
                            dayPicker.setVisibility(View.GONE);
                        }
                    }
                    // Show months as Numbers
                    if(month != 0){
                        NumberPicker monthPicker = findViewById(month);
                        if(monthPicker != null){
                            monthPicker.setDisplayedValues(monthNumbers);
                        }
                    }
                }
            };

            ((ViewGroup) dpd.getDatePicker()).findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);

            dpd.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.tab_tracuu_chon), dpd);
            dpd.setButton(DialogInterface.BUTTON_NEGATIVE, context.getString(R.string.tab_tracuu_huy), dpd);
            dpd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    isShowDated = false;
                }
            });
            dpd.show();
        }
    }

    // Lấy định dạng ngày tháng năm theo yyyymmdd
    public static String getNamThangNgay(String str) {
        String[] arrstr = str.split("-");
        try {
            int nam = Integer.parseInt(arrstr[2].trim());
            int thang = Integer.parseInt(arrstr[1].trim());
            int ngay = Integer.parseInt(arrstr[0].trim());
            return (String) "" + (nam * 10000 + thang * 100 + ngay);
        } catch (Exception e) {
            return "false";
        }

    }

    // Lấy định dạng ngày tháng năm theo yyyymmdd
    public static String getNamThangNgay(String m_nam, String m_thang, String m_ngay) {
        long namthangngay = 0;
        try {
            int nam = Integer.parseInt(m_nam.trim());
            int thang = Integer.parseInt(m_thang.trim());
            int ngay = Integer.parseInt(m_ngay.trim());
            namthangngay = nam * 10000 + thang * 100 + ngay;
            return "" + (namthangngay);
        } catch (Exception e) {
            return "" + (namthangngay);
        }

    }

    public static String keepOnlyNumberInString(String chuoi) {
        String str = chuoi.replaceAll("[^0-9]", "");
        return str;
    }
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean isEmailValidator(String email)
    {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }
    public static void showAlertDialog(Context context, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Thông báo")
                .setIcon(android.R.drawable.ic_dialog_alert);
        WebView webView = new WebView(context);
        webView.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);

        builder.setView(webView);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public static String convertSignHTML(String str) {
        if (str != null) {
            str = str.replace("[", "<");
            str = str.replace("]", ">");
        }
        return str;
    }
    public static void showDialogCallPhone(Activity context, String phoneNumber, String content){
        Dialog dialog = new Dialog(
                context,
                android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        LayoutInflater inf = LayoutInflater.from(context);
        View layout = inf.inflate(R.layout.dialog_baocaosuco_ngoaigio,
                null);
        layout.setFocusableInTouchMode(true);
        dialog.setContentView(layout);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

        final Button id_btn_call = (Button) layout.findViewById(R.id.id_btn_call_bcsc_ng);
        final WebView id_content = layout.findViewById(R.id.id_content_dialog_bcsc_ng);
        final Button id_close = layout.findViewById(R.id.id_btn_close_bcsc_ng);
        String str = CommonHelper.convertSignHTML(content);
        id_content.loadData(str,"text/html", "UTF-8");
        id_btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonHelper.isCallPhonePermissionGranted(context)) {
                    CommonHelper.callPhone(context, phoneNumber);
                    dialog.dismiss();
                } else {

                }
            }
        });
        id_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
