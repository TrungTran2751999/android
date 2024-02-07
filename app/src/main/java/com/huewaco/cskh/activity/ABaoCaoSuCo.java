package com.huewaco.cskh.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.customview.edittext.CustomEditText;
import com.huewaco.cskh.adapter.ListViewBaoCaoSuCoAdapter;

import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;

import com.huewaco.cskh.interfacex.ITFShowlocation;
import com.huewaco.cskh.interfacex.ITFTakePhotoGallery;
import com.huewaco.cskh.interfacex.ITF_DialogButtonProcessing;
import com.huewaco.cskh.objects.BaoCaoSuCoObj;

import com.huewaco.cskh.takephoto.AlbumStorageDirFactory;
import com.huewaco.cskh.takephoto.BaseAlbumDirFactory;
import com.huewaco.cskh.takephoto.FroyoAlbumDirFactory;
import com.huewaco.cskh.ui.CustomListviewGCS;
import com.huewaco.cskh.webservice.objects.GetAllBaoCaoSuCoResponse;
import com.huewaco.cskh.webservice.objects.GetCreateBaoCaoSuCoResponse;
import com.huewaco.cskh.webservice.processing.ResultFromWebservice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ABaoCaoSuCo extends AParent implements ITF_DialogButtonProcessing, LocationListener, ITFShowlocation, ITFTakePhotoGallery {

    enum TYPE_CAM_GAL{ CAMERA, GALLERY}
    TYPE_CAM_GAL WhatType;

    private Button id_btn_send_bcsc_data, id_btn_call_bcsc;
    private ObjectSent2Server objectSent2Server = new ObjectSent2Server();
    int WIDTHX,HEIGHTX;
    public ITF_DialogButtonProcessing itfDialogButtonProcessing = this;
    private String sucoText;
    private CustomListviewGCS id_lv_list_suco;
    private ArrayList<BaoCaoSuCoObj> mArrSuco = new ArrayList<>();
    private ListViewBaoCaoSuCoAdapter listViewBaoCaoSuCoAdapter;
    private CustomEditText id_edt_suco;
    private BaoCaoSuCoObj baoCaoSuCoObjCurrent;
    //private View id_ly_dt;
    //public ITFShowlocation itfShowlocation = null;
    private TextView id_tv_address;
    private ViewGroup id_ly_map_location_x;

    public static boolean COME_BACK_FROM_AMAP = false;

    public static String DIACHI_KH_CHON = "";
    public static double VIDO_KH_CHON = -1;
    public static double KINHDO_KH_CHON = -1;

    public static String DIACHI_MOBILE = "";
    public static double VIDO_MOBILE = -1;
    public static double KINHDO_MOBILE = -1;

    private ITFTakePhotoGallery iTFTakePhotoGallery= this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        itfShowlocation = this;
        GlobalVariable.IS_SUCO = true;
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        WIDTHX = displaymetrics.widthPixels;
        HEIGHTX = displaymetrics.heightPixels;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baocaosuco);
        initTopbarView();
        initCommonView(this);
        initComponent();
        addListener();

        askPermissionsAndShowMyLocation();

//        int hour = CommonHelper.getHourNow();
//        if(hour < 6 || hour >=18) {
//            CommonHelper.showWarning(this, getString(R.string.time_out_call));
//        }
    }

    @Override
    public void takePhotoX( BaoCaoSuCoObj baoCaoSuCoObj){
        WhatType = TYPE_CAM_GAL.CAMERA;
        baoCaoSuCoObjCurrent = baoCaoSuCoObj;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }
        if(CommonHelper.isCameraPermissionGranted(this)){
            if(CommonHelper.isStoragePermissionGranted(this)) {
                takePhoto();
            }
        }
    }
    @Override
    public void galleryX( BaoCaoSuCoObj baoCaoSuCoObj){
        WhatType = TYPE_CAM_GAL.GALLERY;
        baoCaoSuCoObjCurrent = baoCaoSuCoObj;
        if(CommonHelper.isStoragePermissionGranted(this)) {

            Intent i = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(i, GlobalVariable.GALERRY);
        }
    }
    @Override
    public void cancelImg( BaoCaoSuCoObj baoCaoSuCoObj){
        baoCaoSuCoObj.setBm(null);
//        mArrSuco.remove(baoCaoSuCoObj);
        listViewBaoCaoSuCoAdapter.refresh(mArrSuco);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        itfShowlocation = null;
        GlobalVariable.IS_SUCO = false;


        ABaoCaoSuCo.KINHDO_KH_CHON = -1;
        ABaoCaoSuCo.VIDO_KH_CHON = -1;
        ABaoCaoSuCo.DIACHI_KH_CHON = "";

        ABaoCaoSuCo.KINHDO_MOBILE = -1;
        ABaoCaoSuCo.VIDO_MOBILE = -1;
        ABaoCaoSuCo.DIACHI_MOBILE = "";

        COME_BACK_FROM_AMAP = false;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(COME_BACK_FROM_AMAP){
            COME_BACK_FROM_AMAP = false;
            if(CommonHelper.checkValidString(ABaoCaoSuCo.DIACHI_KH_CHON)){
                if(!ABaoCaoSuCo.DIACHI_KH_CHON.equalsIgnoreCase(ABaoCaoSuCo.DIACHI_MOBILE)){
                    CommonHelper.showWarning(ABaoCaoSuCo.this,getString(R.string.changed_address));
                    id_tv_address.setText(ABaoCaoSuCo.DIACHI_KH_CHON);
                }
            }
        }


    }

    @Override
    protected void initComponent() {
        id_ly_map_location_x = (ViewGroup)findViewById(R.id.id_ly_map_location_x);
        id_tv_address = (TextView)findViewById(R.id.id_tv_address);
        id_btn_send_bcsc_data = (Button)findViewById(R.id.id_btn_send_bcsc_data);
        id_btn_call_bcsc = (Button)findViewById(R.id.id_btn_call_bcsc);

        //Them List 3 camera cho chup anhr
        for(int i = 0 ; i< 3; i++){
            BaoCaoSuCoObj sc = new BaoCaoSuCoObj();
            sc.setLink(""+i);
//            if(i == 1){
//                sc.setCancel(true);
//            }else{
//                sc.setCancel(false);
//            }
            mArrSuco.add(sc);
        }
        id_edt_suco = (CustomEditText)findViewById(R.id.id_edt_suco);

        id_lv_list_suco = (CustomListviewGCS)findViewById(R.id.id_lv_list_suco);
        listViewBaoCaoSuCoAdapter = new ListViewBaoCaoSuCoAdapter(ABaoCaoSuCo.this, mArrSuco, iTFTakePhotoGallery);
        id_lv_list_suco.setAdapter(listViewBaoCaoSuCoAdapter);

        //id_ly_dt = (View)findViewById(R.id.id_ly_dt);

        if(GlobalVariable.KHACH_HANG != null){//login
            if(CommonHelper.checkValidString(GlobalVariable.KHACH_HANG.getDiDong())){
                //id_ly_dt.setVisibility(View.GONE);
            }else{
                //id_ly_dt.setVisibility(View.VISIBLE);
            }
        }else{//not login

        }
    }

    @Override
    protected void addListener() {
        id_ly_map_location_x.setOnClickListener(this);
        final Drawable right = this.getResources().getDrawable(android.R.drawable.ic_delete);
        right.setBounds(0, 0, right.getIntrinsicHeight(), right.getIntrinsicHeight());
        setEditTextListener(id_edt_suco, null, right);
        id_btn_send_bcsc_data.setOnClickListener(this);
        id_btn_call_bcsc.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_ly_map_location_x:

                if(CommonHelper.checkValidString(id_tv_address.getText().toString().trim())){
                    AMap.ALLOW_ADD_MARKER = true;
                    if(CommonHelper.checkValidString(ABaoCaoSuCo.DIACHI_KH_CHON)){
                        AMap.kinhDo = ABaoCaoSuCo.KINHDO_KH_CHON;
                        AMap.vido = ABaoCaoSuCo.VIDO_KH_CHON;
                        AMap.title = ABaoCaoSuCo.DIACHI_KH_CHON;
                    }else{
                        AMap.kinhDo = ABaoCaoSuCo.KINHDO_MOBILE;
                        AMap.vido = ABaoCaoSuCo.VIDO_MOBILE;
                        AMap.title = ABaoCaoSuCo.DIACHI_MOBILE;
                    }
                    startActivity(new Intent(getApplicationContext(),AMap.class));
                }
                break;

            case R.id.id_btn_right:
                if(isValid()) {
                    CommonHelper.showDAGBaoCaoSuCo(this,"",GlobalVariable.BUTTON_OK_SHOW,itfDialogButtonProcessing,GlobalVariable.SEND_DATA_SUCO,GlobalVariable.SEND_DATA_SUCO);
                }
                break;

            case R.id.id_btn_send_bcsc_data:
                    int hour = CommonHelper.getHourNow();
                    if(hour < 6 || hour > 18){
//                        CommonHelper.showAlertDialog(ABaoCaoSuCo.this,CommonHelper.convertSignHTML(getString(R.string.send_data_suco_not_success_3)));
                        CommonHelper.showDialogCallPhone(ABaoCaoSuCo.this, getString(R.string.hotline), getString(R.string.send_data_suco_not_success_3));
                        return;
                    }
                    if(isValid()) {
                        CommonHelper.showDAGBaoCaoSuCo(this,"",GlobalVariable.BUTTON_OK_SHOW,itfDialogButtonProcessing,GlobalVariable.SEND_DATA_SUCO,GlobalVariable.SEND_DATA_SUCO);
                    }
                break;

            case R.id.id_btn_call_bcsc:
                if (CommonHelper.isCallPhonePermissionGranted(ABaoCaoSuCo.this)) {
                    CommonHelper.callPhone(ABaoCaoSuCo.this, getString(R.string.hotline));
                } else {

                }
                break;

            default:
                break;
        }
    }


    private boolean isValid(){
        boolean isChk = true;
        sucoText = id_edt_suco.getText().toString().trim();
        if(CommonHelper.checkValidString(sucoText)){
            id_edt_suco.setBackgroundResource(R.drawable.border_bg_edit);
        }
        if(!CommonHelper.checkValidString(sucoText)){// da nhap noi dung
            isChk = false;
            CommonHelper.showWarning(this, getResources().getString(R.string.chua_nhap_suco));
            id_edt_suco.setBackgroundResource(R.drawable.border_bg_red2);
        }

        if(isChk && !hasImg()){
            isChk = false;
            CommonHelper.showWarning(this, getResources().getString(R.string.chua_dinh_kem_anh));
        }
        return isChk;
    }

    private boolean hasImg(){
        for(BaoCaoSuCoObj sc : mArrSuco){
            if(sc.getBm() != null){
                return true;
            }
        }
        return false;
    }
    @Override
    protected void takePhoto() {

        PackageManager packageManager;
        packageManager = getApplicationContext().getPackageManager();
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            dispatchTakePictureIntent(GlobalVariable.TAKE_PHOTO);
        } else {
            CommonHelper.showToast(
                    getApplicationContext(),
                    getResources().getString(R.string.device_hasnot_camera));
        }
    }
    private String PATH_FIRST = "";


    private void dispatchTakePictureIntent(int actionCode) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File f = null;

        try {
            f = createImageFile();
            PATH_FIRST = f.getAbsolutePath();

        } catch (IOException e) {
            e.printStackTrace();
            f = null;
            PATH_FIRST = "";
        }
        if( f != null){
            try{
                Uri photoURI = FileProvider.getUriForFile(ABaoCaoSuCo.this,
                        BuildConfig.APPLICATION_ID + ".provider",f);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                createPhoto(PATH_FIRST);
                startActivityForResult(takePictureIntent, actionCode);
            }catch (Exception e){
                String er = e.getMessage();
            }

        }
    }
    private void createPhoto(String PATH) {
        CommonHelper.galleryAddPic(
                getApplicationContext(), PATH);
    }
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";
    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX,
                albumF);
        return imageF;
    }


    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {

            storageDir = mAlbumStorageDirFactory
                    .getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (!storageDir.mkdirs()) {
                    if (!storageDir.exists()) {
                        String str_message = getString(R.string.failed_create_folder);
                        CommonHelper.showWarning(ABaoCaoSuCo.this, str_message);
                        return null;
                    }
                }
            }

        } else {
            // showDialogExternal("");
            String str_message = getString(R.string.not_mounted);
            CommonHelper.showToast(getApplicationContext(), str_message);
        }

        return storageDir;
    }

    private String getAlbumName() {
//        return GlobalVariable.FOLDERCMR;
        return GlobalVariable.FOLDER;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = CommonHelper
                .tabletOrSmartphone(getApplicationContext());
        if (resultCode == RESULT_OK) {
            Bitmap bm;
            if(requestCode == GlobalVariable.TAKE_PHOTO){
                handleBigCameraPhoto();
            }else if (requestCode == GlobalVariable.GALERRY) {
                Uri selectedImage=null ;//= data.getData();
                if((data!=null) && ((requestCode == GlobalVariable.GALERRY))){
                    selectedImage = data.getData();
                }
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor;
                try {
                    String picturePath="";
                    if((requestCode == GlobalVariable.GALERRY)&&(selectedImage!=null)){
                        cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        picturePath = cursor.getString(columnIndex);
                        cursor.close();
                    }
                    bm = (BitmapFactory.decodeFile(picturePath, options));




                    if (bm != null) {

                        //start rotate image

                        try{
                            ExifInterface exif;
                            String exifOrientation = "";
                            try {
                                exif = new ExifInterface(picturePath);
                                exifOrientation = exif
                                        .getAttribute(ExifInterface.TAG_ORIENTATION);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Matrix matrix = new Matrix();
                            if (exifOrientation.equalsIgnoreCase("6")) {
                                matrix.postRotate(90);
                                bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                                        bm.getHeight(), matrix, true);
                            } else if (exifOrientation.equalsIgnoreCase("3")) {
                                matrix.postRotate(180);
                                bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                                        bm.getHeight(), matrix, true);
                            } else if (exifOrientation.equalsIgnoreCase("8")) {
                                matrix.postRotate(270);
                                bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                                        bm.getHeight(), matrix, true);
                            }
                        }catch (Exception e){

                        }

                        //end rotate image
                        System.gc();
                        Runtime.getRuntime().gc();
                        showImage(bm);

                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }else if (resultCode == RESULT_CANCELED) {
            if(PATH_FIRST!= null && (!PATH_FIRST.equalsIgnoreCase("") ||(PATH_FIRST!=null))){
                System.gc();
                deleteFileAndroid(PATH_FIRST);
//                finish();
            }
        }
    }
    private void showImage(Bitmap bm){
        baoCaoSuCoObjCurrent.setBm(bm);
        listViewBaoCaoSuCoAdapter.refresh(mArrSuco);
    }
    private void handleBigCameraPhoto() {

        if (PATH_FIRST != null) {
            setPic();
            galleryAddPic();
            PATH_FIRST = null;
        }

    }
    private void setPic() {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(PATH_FIRST, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        /* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((WIDTHX > 0) || (HEIGHTX > 0)) {
            scaleFactor = Math.min(photoW/WIDTHX, photoH/HEIGHTX);
        }

        /* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        //Bitmap bm = null;
        Bitmap bm = BitmapFactory.decodeFile(PATH_FIRST, bmOptions);

        //start rotate image

        int rotationAngle = 0;
        if (bm != null) {
            ExifInterface exif;
            String exifOrientation = "";
            try {
                exif = new ExifInterface(PATH_FIRST);
                exifOrientation = exif.getAttribute(ExifInterface.TAG_ORIENTATION);

                int orientation = exifOrientation != null ? Integer.parseInt(exifOrientation) : ExifInterface.ORIENTATION_NORMAL;
                if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                    rotationAngle = 90;
                }
                if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                    rotationAngle = 180;
                }
                if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                    rotationAngle = 270;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Matrix matrix = new Matrix();
            matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);

            int WR = bm.getWidth();
            int HR = bm.getHeight();
            if(photoW > WR){
                photoW = WR;
            }
            if(photoH > HR){
                photoH = HR;
            }

            bm = Bitmap.createBitmap(bm, 0, 0, photoW, photoH, matrix, true);
            showImage(bm);
        }

        //end rotate image
        showImage(bm);
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(PATH_FIRST);
        //Uri contentUri = Uri.fromFile(f);
        Uri contentUri =  FileProvider.getUriForFile(ABaoCaoSuCo.this,
                BuildConfig.APPLICATION_ID + ".provider",
                f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
    private void deleteFileAndroid(String path) {
//        CommonHelper.deleteFileOne(path);
        File f = new File(path);
        if(f.exists()){
            deleteFile(f.getName());
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == GlobalVariable.REQUEST_WRITE_STORAGE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                if(GlobalVariable.IS_SUCO){
                    if(WhatType == TYPE_CAM_GAL.CAMERA){
                        if(CommonHelper.isCameraPermissionGranted(this)){
                            takePhoto();
                        }
                    }else if(WhatType == TYPE_CAM_GAL.GALLERY){
                        Intent i = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        startActivityForResult(i, GlobalVariable.GALERRY);
                    }

                }
            } else
            {

                //id_btn_downup.setEnabled(false);
//                CommonHelper.showWarning(this,getString(R.string.not_allow_access_sdcard));
                CommonHelper.showDAG(this,null,getString(R.string.not_allow_access_sdcard),GlobalVariable.BUTTON_OK_NOT_SHOW,itfDialogButtonProcessing,GlobalVariable.DO_NOTHING,GlobalVariable.DO_NOTHING);
            }
        }else if(requestCode == GlobalVariable.REQUEST_CAMERA){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                if(GlobalVariable.IS_SUCO){
                    if(CommonHelper.isStoragePermissionGranted(this)){
                        takePhoto();
                    }
                }

            } else
            {
                //hideView();
                //id_btn_downup.setEnabled(false);
                CommonHelper.showDAG(this,null,getString(R.string.not_allow_access_camera),GlobalVariable.BUTTON_OK_NOT_SHOW,itfDialogButtonProcessing,GlobalVariable.DO_NOTHING,GlobalVariable.DO_NOTHING);
            }
        }
    }

    @Override
    public void okBtn(int type) {
        if(type == GlobalVariable.DO_NOTHING){

        }else if(type == GlobalVariable.SEND_DATA_SUCO){
            //new GCSTaskSendData().execute();
        }
    }

    @Override
    public void cancelBtn(int type) {
        if(type == GlobalVariable.DO_NOTHING){
            //finish();
        }else if(type == GlobalVariable.SEND_DATA_SUCO){

        }
    }

    @Override
    public void okBtn(int type, String tenKh, String sdt, String diachi) {


    }

    @Override
    public void okBtn(int type, String tenKh, String sdt, String diachi, String hdtimvitrisuco) {
        if(type == GlobalVariable.DO_NOTHING){

        }else if(type == GlobalVariable.SEND_DATA_SUCO){
            if(GlobalVariable.KHACH_HANG != null){

                new GCSTaskSendData(GlobalVariable.KHACH_HANG.getTenKhachHang(),GlobalVariable.KHACH_HANG.getDiDong(),diachi,hdtimvitrisuco).execute();
            }else{
                new GCSTaskSendData(tenKh,sdt,diachi,hdtimvitrisuco).execute();
            }

        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }




    @Override
    public void showLocation() {
        getLocation();
    }

    public class GCSTaskSendData extends AsyncTask<String, Void, Integer> {
        String tenKH, sdt, diachi_khach_vang_lai;
        String hdtimvitrisuco;
        GCSTaskSendData(String tenKH, String sdt, String diachi,String hdtimvitrisuco){
            this.tenKH = tenKH;
            this.sdt = sdt;
            this.diachi_khach_vang_lai = diachi;
            this.hdtimvitrisuco = hdtimvitrisuco;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();

        }

        public Integer doInBackground(String... params) {
            int vl=-1;
            if(GlobalVariable.KHACH_HANG != null){
                objectSent2Server.setIdKH(GlobalVariable.KHACH_HANG.getMa_khang());
                objectSent2Server.setSoDienThoai(GlobalVariable.KHACH_HANG.getDiDong());
                objectSent2Server.setTenKhachHang(GlobalVariable.KHACH_HANG.getTenKhachHang());
            }else{
                objectSent2Server.setIdKH("");
                objectSent2Server.setSoDienThoai(sdt);
                objectSent2Server.setTenKhachHang(tenKH);
            }

            objectSent2Server.setHuongDanTimViTriSuCo(hdtimvitrisuco);
            objectSent2Server.setNoiDung(sucoText);
            objectSent2Server.setDiaChiKhachHang(diachi_khach_vang_lai);


            if(CommonHelper.checkValidString(DIACHI_KH_CHON)){
                objectSent2Server.setDiaChiSuCoKhachHangChon(DIACHI_KH_CHON);
                if(VIDO_KH_CHON != -1){
                    objectSent2Server.setViDoKhachHangChon(VIDO_KH_CHON);
                }
                if(KINHDO_KH_CHON != -1){
                    objectSent2Server.setKinhDoKhachHangChon(KINHDO_KH_CHON);
                }

            }

            ArrayList<String> mArrayBase64 = new ArrayList<>();
            for(BaoCaoSuCoObj obj : mArrSuco){
                if(obj != null){
                    if(obj.getBm() != null){
                        String x64 = CommonHelper.bitmapToBase64(obj.getBm());
                        mArrayBase64.add(x64);
                    }
                }
            }
            JSONObject jsonObj = new JSONObject();
            try {
                jsonObj.put("IdKH",objectSent2Server.getIdKH());
                jsonObj.put("DiaChiKhachHang",objectSent2Server.getDiaChiKhachHang());
                jsonObj.put("TenKhachHang",objectSent2Server.getTenKhachHang());
                jsonObj.put("SoDienThoai",objectSent2Server.getSoDienThoai());
                jsonObj.put("NoiDung",objectSent2Server.getNoiDung());
                jsonObj.put("DiaChiSuCoTheoMobile",objectSent2Server.getDiaChiSuCoTheoMobile());
                jsonObj.put("DiaChiSuCoKhachHangChon",objectSent2Server.getDiaChiSuCoKhachHangChon());
                jsonObj.put("KinhDoTheoMobile",String.valueOf(objectSent2Server.getKinhDoTheoMobile()));
                jsonObj.put("ViDoTheoMobile",String.valueOf(objectSent2Server.getViDoTheoMobile()));
                jsonObj.put("KinhDoKhachHangChon",String.valueOf(objectSent2Server.getKinhDoKhachHangChon()));
                jsonObj.put("ViDoKhachHangChon",String.valueOf(objectSent2Server.getViDoKhachHangChon()));
                jsonObj.put("HuongDanTimViTriSuCo",objectSent2Server.getHuongDanTimViTriSuCo());
                jsonObj.put("Images",new JSONArray(mArrayBase64));


                GetCreateBaoCaoSuCoResponse getCreateBaoCaoSuCoResponse = new ResultFromWebservice().getCreateBaoCaoSuCoResponse(getApplicationContext(),jsonObj.toString());
                vl = getCreateBaoCaoSuCoResponse.getResult();
            } catch (JSONException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

//            try {
//                Thread.currentThread();
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }


            return vl;
        }

        public void onPostExecute(Integer result) {
            disMissLoading();

            //CommonHelper.showToast(getApplicationContext(),"ten: "+tenKH+"   sdt: "+sdt);
            if(result == 1){
                CommonHelper.showToast(getApplicationContext(),getString(R.string.send_data_suco_success));
                finish();
            }else if(result == 2){
                CommonHelper.showToast(getApplicationContext(),getString(R.string.send_data_suco_not_success_2)+result);
            }else if(result ==0){
                CommonHelper.showToast(getApplicationContext(),getString(R.string.send_data_suco_not_success_0)+result);
            }else if(result == 3){
                CommonHelper.showDialogCallPhone(ABaoCaoSuCo.this, getString(R.string.hotline), getString(R.string.send_data_suco_not_success_3));
//                CommonHelper.showAlertDialog(ABaoCaoSuCo.this, CommonHelper.convertSignHTML(getString(R.string.send_data_suco_not_success_3)));
            }

        }
    }
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                ABaoCaoSuCo.this);
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            id_tv_address.setText(locationAddress);
        }
    }
    private void askPermissionsAndShowMyLocation() {
        // Với API >= 23, bạn phải hỏi người dùng cho phép xem vị trí của họ.
        if (Build.VERSION.SDK_INT >= 23) {
            int accessCoarsePermission
                    = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            int accessFinePermission
                    = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);


            if (accessCoarsePermission != PackageManager.PERMISSION_GRANTED
                    || accessFinePermission != PackageManager.PERMISSION_GRANTED) {

                // Các quyền cần người dùng cho phép.
                String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION};

                // Hiển thị một Dialog hỏi người dùng cho phép các quyền trên.
                ActivityCompat.requestPermissions(this, permissions,
                        GlobalVariable.REQUEST_ID_ACCESS_COURSE_FINE_LOCATION);

                return;
            }
        }

        // Hiển thị vị trí hiện thời trên bản đồ.
        //this.showMyLocation();
        getLocation();
    }
    Location location = null;
    public Location getLocation() {
        //boolean canGetLocation = false;

        //double latitude, longitude;
        // Millisecond
        final long MIN_TIME_BW_UPDATES = 1000;
        // Met
        final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;
        try {
            LocationManager locationManager = (LocationManager) ABaoCaoSuCo.this.getSystemService(LOCATION_SERVICE);

            // getting GPS status
            boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                CommonHelper.showWarning(this,getString(R.string.not_gps_network));
            } else {
                //canGetLocation = true;
                if (isNetworkEnabled) {

                    if (ActivityCompat.checkSelfPermission(ABaoCaoSuCo.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ABaoCaoSuCo.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        return null;
                    }
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, ABaoCaoSuCo.this);
                    Log.d("Network", "Network Enabled");
                    if (locationManager != null) {
/*                        location = new Location(LocationManager.NETWORK_PROVIDER);//provider name is unecessary
                        location.setLatitude(16.4571399);//your coords of course
                        location.setLongitude(107.5745091);*/
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
/*                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }*/
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
/*                            location = new Location(LocationManager.GPS_PROVIDER);//provider name is unecessary
                            location.setLatitude(16.4571399);//your coords of course
                            location.setLongitude(107.5745091);*/
/*                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }*/
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(location != null) {
            //CommonHelper.showWarning(fpActivity,"Lat: "+location.getLatitude()+" Long: "+location.getLongitude());
//            Log.d("abc=============", "longlat: " + location + "   long: " + location.getLongitude() + "  lat: " + location.getLatitude());
            showAddress();
        }

        return location;
    }
    private void showAddress(){

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            /*
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // On
            String txt = "";
            for (int i = 0; i < addresses.get(0).getMaxAddressLineIndex(); i++) {
                txt += addresses.get(0).getAddressLine(i)+"\n";

            }
            */
//            id_tv_address.setText(txt);
            if(CommonHelper.checkValidString(ABaoCaoSuCo.DIACHI_KH_CHON)){
                id_tv_address.setText(ABaoCaoSuCo.DIACHI_KH_CHON);
            }else{
                id_tv_address.setText(address);
            }



            DIACHI_MOBILE = address;
            VIDO_MOBILE = location.getLatitude();
            KINHDO_MOBILE = location.getLongitude();
            objectSent2Server.setDiaChiSuCoTheoMobile(DIACHI_MOBILE);
            objectSent2Server.setKinhDoTheoMobile(KINHDO_MOBILE);
            objectSent2Server.setViDoTheoMobile(VIDO_MOBILE);


//            objectSent2Server.setDiaChiSuCoTheoMobile(address);
//            objectSent2Server.setKinhDoTheoMobile(location.getLongitude());
//            objectSent2Server.setViDoTheoMobile(location.getLatitude());

            objectSent2Server.setDiaChiSuCoKhachHangChon(address);
            objectSent2Server.setKinhDoKhachHangChon(location.getLongitude());
            objectSent2Server.setViDoKhachHangChon(location.getLatitude());
//            id_tv_address.setText("address: "+address+"\n"+"city: "+city+" \n"+"state: "+state+"\n"+"country: "+country+"\n"+"postalCode: "+postalCode+"\n"+"knownName: "+knownName);
        } catch (IOException e) {
            e.printStackTrace();
            CommonHelper.showWarning(ABaoCaoSuCo.this,getString(R.string.error_get_location)+e.getMessage());
        }


    }
    class ObjectSent2Server {
        private String IdKH;
        private String DiaChiKhachHang;
        private String TenKhachHang;
        private String SoDienThoai;
        private String NoiDung;
        private String DiaChiSuCoTheoMobile;
        private String DiaChiSuCoKhachHangChon;
        private double KinhDoTheoMobile;
        private double ViDoTheoMobile;
        private double KinhDoKhachHangChon;
        private double ViDoKhachHangChon;
        private ArrayList<String> Images = new ArrayList<>();
        private String HuongDanTimViTriSuCo;

        public String getIdKH() {
            return IdKH;
        }

        public void setIdKH(String idKH) {
            IdKH = idKH;
        }

        public String getDiaChiKhachHang() {
            return DiaChiKhachHang;
        }

        public void setDiaChiKhachHang(String diaChiKhachHang) {
            DiaChiKhachHang = diaChiKhachHang;
        }

        public String getTenKhachHang() {
            return TenKhachHang;
        }

        public void setTenKhachHang(String tenKhachHang) {
            TenKhachHang = tenKhachHang;
        }

        public String getSoDienThoai() {
            return SoDienThoai;
        }

        public void setSoDienThoai(String soDienThoai) {
            SoDienThoai = soDienThoai;
        }

        public String getNoiDung() {
            return NoiDung;
        }

        public void setNoiDung(String noiDung) {
            NoiDung = noiDung;
        }

        public String getDiaChiSuCoTheoMobile() {
            return DiaChiSuCoTheoMobile;
        }

        public void setDiaChiSuCoTheoMobile(String diaChiSuCoTheoMobile) {
            DiaChiSuCoTheoMobile = diaChiSuCoTheoMobile;
        }

        public String getDiaChiSuCoKhachHangChon() {
            return DiaChiSuCoKhachHangChon;
        }

        public void setDiaChiSuCoKhachHangChon(String diaChiSuCoKhachHangChon) {
            DiaChiSuCoKhachHangChon = diaChiSuCoKhachHangChon;
        }

        public double getKinhDoTheoMobile() {
            return KinhDoTheoMobile;
        }

        public void setKinhDoTheoMobile(double kinhDoTheoMobile) {
            KinhDoTheoMobile = kinhDoTheoMobile;
        }

        public double getViDoTheoMobile() {
            return ViDoTheoMobile;
        }

        public void setViDoTheoMobile(double viDoTheoMobile) {
            ViDoTheoMobile = viDoTheoMobile;
        }

        public double getKinhDoKhachHangChon() {
            return KinhDoKhachHangChon;
        }

        public void setKinhDoKhachHangChon(double kinhDoKhachHangChon) {
            KinhDoKhachHangChon = kinhDoKhachHangChon;
        }

        public double getViDoKhachHangChon() {
            return ViDoKhachHangChon;
        }

        public void setViDoKhachHangChon(double viDoKhachHangChon) {
            ViDoKhachHangChon = viDoKhachHangChon;
        }

        public ArrayList<String> getImages() {
            return Images;
        }

        public void setImages(ArrayList<String> images) {
            Images = images;
        }

        public String getHuongDanTimViTriSuCo() {
            return HuongDanTimViTriSuCo;
        }

        public void setHuongDanTimViTriSuCo(String huongDanTimViTriSuCo) {
            HuongDanTimViTriSuCo = huongDanTimViTriSuCo;
        }
    }
}
