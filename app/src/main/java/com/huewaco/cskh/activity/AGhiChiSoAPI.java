package com.huewaco.cskh.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.common.modeldownload.FirebaseLocalModel;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.common.modeldownload.FirebaseRemoteModel;
import com.google.firebase.ml.custom.FirebaseModelDataType;
import com.google.firebase.ml.custom.FirebaseModelInputOutputOptions;
import com.google.firebase.ml.custom.FirebaseModelInputs;
import com.google.firebase.ml.custom.FirebaseModelInterpreter;
import com.google.firebase.ml.custom.FirebaseModelOptions;
import com.google.firebase.ml.custom.FirebaseModelOutputs;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentText;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentTextRecognizer;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.huewaco.cskh.adapter.ListViewGCSAdapter;
import com.huewaco.cskh.gcs.CloudTextGraphic;
import com.huewaco.cskh.gcs.GraphicOverlay;
import com.huewaco.cskh.gcs.LabelGraphic;
import com.huewaco.cskh.gcs.TextGraphic;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.interfacex.ITF_3Btn_AI;
import com.huewaco.cskh.interfacex.ITF_DialogButtonProcessing;
import com.huewaco.cskh.objects.GcsObject;
import com.huewaco.cskh.objects.Img2TextObj;
import com.huewaco.cskh.objects.TypeDialog;
import com.huewaco.cskh.takephoto.AlbumStorageDirFactory;
import com.huewaco.cskh.takephoto.BaseAlbumDirFactory;
import com.huewaco.cskh.takephoto.FroyoAlbumDirFactory;
import com.huewaco.cskh.ui.CustomListviewGCS;
import com.huewaco.cskh.webservice.objects.GetTextFromBitmapAPIResponse;
import com.huewaco.cskh.webservice.objects.GetTextFromBitmapAPI_HWC_Response;
import com.huewaco.cskh.webservice.processing.ResultFromWebservice;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

//import android.support.v4.content.FileProvider;


public class AGhiChiSoAPI extends AParent /*AppCompatActivity*/ implements ITF_3Btn_AI,ITF_DialogButtonProcessing, AdapterView.OnItemClickListener, View.OnClickListener {

    private View id_ly_cancel,id_ly_group;
    private Button id_btn_downup,id_btn_ml;
    private int WIDTHX,HEIGHTX;
    public ITF_DialogButtonProcessing itfDialogButtonProcessing = this;
    public ITF_3Btn_AI itf3BtnAI = this;
//    private TextView id_tv_text3,id_tv_text2,id_tv_text1;
    private CustomListviewGCS id_lv_list_gcs;
    private ArrayList<GcsObject> mArrGCS = new ArrayList<>();
    private ListViewGCSAdapter listViewGCSAdapter;
    private GcsObject gcsObjectChosen;
    public GraphicOverlay mGraphicOverlay;
    private ImageView mImageView;
    private Bitmap mSelectedImage;
    private static final int RESULTS_TO_SHOW = 3;
    private final PriorityQueue<Map.Entry<String, Float>> sortedLabels =
            new PriorityQueue<>(
                    RESULTS_TO_SHOW,
                    new Comparator<Map.Entry<String, Float>>() {
                        @Override
                        public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float>
                                o2) {
                            return (o1.getValue()).compareTo(o2.getValue());
                        }
                    });
    /* Preallocated buffers for storing image data. */
    private final int[] intValues = new int[DIM_IMG_SIZE_X * DIM_IMG_SIZE_Y];

    String failedStr = "";
    public boolean FOR_TEST = false;
    public boolean FOR_TEST2 = false;
    private String ChiSo_AI_Got = ""; // processing when AI got incorrect number
    String numberAIRead = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GlobalVariable.IS_GCS = true;
//        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        WIDTHX = displaymetrics.widthPixels;
        HEIGHTX = displaymetrics.heightPixels;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gcs);
        initComponent();
        addListener();
        setAnimation();
        initCustomModel();

        failedStr = getString(R.string.gcs_pre_cannot_readimg);
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
    protected void onDestroy(){
        super.onDestroy();
        GlobalVariable.IS_GCS = false;
    }

//    @Override
    protected void initComponent() {
        mImageView = findViewById(R.id.image_view);
        mGraphicOverlay = (GraphicOverlay)findViewById(R.id.graphic_overlay);
        id_btn_ml = (Button)findViewById(R.id.id_btn_ml);
        id_btn_downup = (Button) findViewById(R.id.id_btn_downup);
        id_ly_cancel = (View) findViewById(R.id.id_ly_cancel);
        id_ly_group = (View) findViewById(R.id.id_ly_group);
//        id_ly_img = (View)findViewById(R.id.id_ly_img);
        id_lv_list_gcs = (CustomListviewGCS)findViewById(R.id.id_lv_list_gcs);
        listViewGCSAdapter = new ListViewGCSAdapter(AGhiChiSoAPI.this, mArrGCS);
        id_lv_list_gcs.setAdapter(listViewGCSAdapter);

    }

//    @Override
    protected void addListener() {

        id_btn_downup.setOnClickListener(this);
        id_ly_cancel.setOnClickListener( this);
        id_lv_list_gcs.setOnItemClickListener(this);
        id_btn_ml.setOnClickListener(this);
    }

//    boolean chkShowDialog = true;
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        gcsObjectChosen = mArrGCS.get(position);

        if(gcsObjectChosen.isValue()){
            //CommonHelper.showDAG(this,getString(R.string.water_number)+gcsObjectChosen.getText(),getString(R.string.choose_gcs_number),GlobalVariable.BUTTON_OK_SHOW,itfDialogButtonProcessing,GlobalVariable.SEND_DATA_GCS,GlobalVariable.SEND_DATA_GCS);
            IS_AI_READ_SCCESS = true;
            showWhenAIReadSuccess(getString(R.string.water_number)+gcsObjectChosen.getText());
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_btn_ml:
                runTextRecognition();
                break;
            case R.id.id_btn_downup:
                processUI();
                break;
            case R.id.id_ly_cancel:
                AGhiChiSoPrepare.BCSC.setBm(null);
                AGhiChiSoPrepare.BCSC.setAiAPIText("");
                finish();
                break;
            default:
                break;
        }
    }

    boolean isShowView = true;
    private Animation bottom_top, top_bottom;
    private void setAnimation() {
        bottom_top = AnimationUtils.loadAnimation(this,
                R.anim.slide_in_right);
        top_bottom = AnimationUtils.loadAnimation(this,
                R.anim.slide_out_left);
        isShowView = (id_ly_group.getVisibility() == View.VISIBLE);
        if(isShowView){
            id_btn_downup
                    .setBackgroundResource(R.drawable.ic_cancel);
        }else {
            id_btn_downup
                    .setBackgroundResource(R.drawable.ic_up_data);
        }
    }

    void processUI(){
        if (isShowView) {
            hideView();
        } else {
            showView();
        }
    }

    private void showView() {
        id_ly_group.startAnimation(bottom_top);
        isShowView = true;
        id_ly_group.setVisibility(View.VISIBLE);
        id_btn_downup.setBackgroundResource(R.drawable.ic_cancel);
    }

    private void hideView() {
        id_ly_group.startAnimation(top_bottom);
        isShowView = false;
        id_ly_group.setVisibility(View.GONE);
        id_btn_downup.setBackgroundResource(R.drawable.ic_up_data);
    }
//    @Override
    protected void takePhoto() {
        AGhiChiSoPrepare.BCSC.setBm(null);
        mSelectedImage = null;
        System.gc();

        hideView();//hiden
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
                Uri photoURI = FileProvider.getUriForFile(AGhiChiSoAPI.this,
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
                        CommonHelper
                                .showWarning(AGhiChiSoAPI.this, str_message);
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

//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = CommonHelper
//                .tabletOrSmartphone(getApplicationContext());
        if(requestCode == GlobalVariable.TAKE_PHOTO){
            if (resultCode == RESULT_OK) {
                //Bitmap bm;

                handleBigCameraPhoto();

            }else if (resultCode == RESULT_CANCELED) {
                if(PATH_FIRST!= null && (!PATH_FIRST.equalsIgnoreCase("") ||(PATH_FIRST!=null))){
                    System.gc();
                    deleteFileAndroid(PATH_FIRST);
                    finish();
                }
            }
        }

    }
    private void handleBigCameraPhoto() {

        if (PATH_FIRST != null) {
            setPic();
//            setPic2();
            galleryAddPic();
            PATH_FIRST = null;
        }

    }

    private void setPic() {
        mGraphicOverlay.clear();
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


        int rotationAngle = 0;
        if (bm != null) {

            try{
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
                //Log.d("xxxxx" + "",""+bm.getWidth());
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
            }catch (Exception e){
                Log.d("xxxxx",e.getMessage());
            }

            mSelectedImage = bm;


            //Start Google ML Processing

            if (mSelectedImage != null) {
                // Get the dimensions of the View
                /*
                Pair<Integer, Integer> targetedSize = getTargetedWidthHeight();

                int targetWidth = targetedSize.first;
                int maxHeight = targetedSize.second;

                // Determine how much to scale down the image
                float scaleFactor2 =
                        Math.max(
                                (float) mSelectedImage.getWidth() / (float) targetWidth,
                                (float) mSelectedImage.getHeight() / (float) maxHeight);

                Bitmap resizedBitmap =
                        Bitmap.createScaledBitmap(
                                mSelectedImage,
                                (int) (mSelectedImage.getWidth() / scaleFactor2),
                                (int) (mSelectedImage.getHeight() / scaleFactor2),
                                true);
*/
                //mSelectedImage = CommonHelper.getBitmapFromAsset(getApplicationContext(),"text_img.png");
                mImageView.setImageBitmap(mSelectedImage);
                //mSelectedImage = resizedBitmap;

                //mSelectedImage = getBitmapFromAsset(getApplicationContext(),"text_img.png");
                //runTextRecognition();
                //showView();
//                AGhiChiSoPrepare.BCSC.setBm(mSelectedImage);
                if(AGhiChiSoPrepare.BCSC.isProcessAI()){
                    processBitmapAPI();
                }else{
                    new GCSTaskTextNo_AI(mSelectedImage).execute();
//                    Intent returnIntent = new Intent();
//                    returnIntent.putExtra(GlobalVariable.KEY_GET_VALUE_GCS_FAILED,"");
//                    setResult(Activity.RESULT_CANCELED,returnIntent);
//                    finish();
                }



            }

        }
    }

    private void processBitmapAPI(){
//        new GCSTaskTextAPI(mSelectedImage).execute();
        new GCSTaskTextAPIHWC(mSelectedImage).execute();
    }
    public class GCSTaskTextNo_AI extends AsyncTask<String, Void, Void> {
        Bitmap bm;
        GCSTaskTextNo_AI(Bitmap bm){
            this.bm = bm;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        public Void doInBackground(String... params) {
            bm = CommonHelper.scaleImgDown(bm,GlobalVariable.MAXIMAGESIZE_AI,true);
            AGhiChiSoPrepare.BCSC.setBm(bm);
            return null;
        }

        public void onPostExecute(Void result) {
            disMissLoading();
            Intent returnIntent = new Intent();
            returnIntent.putExtra(GlobalVariable.KEY_GET_VALUE_GCS_FAILED,"");
            setResult(Activity.RESULT_CANCELED,returnIntent);
            finish();
        }
    }
    private String getCorrectNumber(Img2TextObj img2TextObj){
        String vl = "";
        if(img2TextObj.getNumber_shapes() != null){
            for (Map.Entry<String, ArrayList<String>> entry : img2TextObj.getNumber_shapes().entrySet()) {
                String k = entry.getKey();
                //ArrayList<String> v = entry.getValue();
                //System.out.println("Key: " + k + ", Value: " + v);
                vl+=k;
            }
        }
        return vl;
    }
    public class GCSTaskTextAPI extends AsyncTask<String, Void, Void> {
        Bitmap bm;
        Img2TextObj img2TextObj;
        boolean isSuccess = false;

        //String xxx = "";
        GCSTaskTextAPI(Bitmap bm){
            this.bm = bm;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
            mArrGCS.clear();
        }


        public Void doInBackground(String... params) {
            bm = CommonHelper.scaleImgDown(bm,GlobalVariable.MAXIMAGESIZE_AI,true);
            AGhiChiSoPrepare.BCSC.setBm(bm);
            String base64 = CommonHelper.bitmapToBase64(bm);


            GetTextFromBitmapAPIResponse getTextFromBitmapAPIResponse = new ResultFromWebservice().getTextFromBitmapAPIResponse(getApplicationContext(),base64,"");
            img2TextObj = getTextFromBitmapAPIResponse.getImg2TextObj();
            isSuccess = getTextFromBitmapAPIResponse.isSuccess();
            failedStr = getTextFromBitmapAPIResponse.getReturnString();

            return null;
        }

        public void onPostExecute(Void result) {
            disMissLoading();
            if(FOR_TEST){
                isSuccess = true; //for test
            }
            if(isSuccess){
                if(img2TextObj!=null){


                    /*
                    String readNumber = img2TextObj.getRead_number();
                    if(CommonHelper.checkValidString(readNumber)){
                        GcsObject o1 = new GcsObject();
                        o1.setValue(true);
                        o1.setText(img2TextObj.getRead_number());
                        ChiSo_AI_Got = o1.getText();
                        mArrGCS.add(o1);
                    }else {
                        // AI khong doc duoc chi so
                        ChiSo_AI_Got = "";
                    }
                    */
                    String readNumber = getCorrectNumber(img2TextObj);
                    if(CommonHelper.checkValidString(readNumber)){
                        GcsObject o1 = new GcsObject();
                        o1.setValue(true);
                        o1.setText(readNumber);
                        ChiSo_AI_Got = o1.getText();
                        mArrGCS.add(o1);
                    }else {
                        // AI khong doc duoc chi so
                        ChiSo_AI_Got = "";
                    }
                    if(FOR_TEST) {
                        test(); //for test
                    }
                    if(FOR_TEST2){
                        test2();
                    }
                    if(mArrGCS != null){
                        if(mArrGCS.size() >0){
                            listViewGCSAdapter.refresh(mArrGCS);
                            showView();
                            if(mArrGCS.size()==1){
                                gcsObjectChosen = mArrGCS.get(0);
                                if(gcsObjectChosen.isValue()){
                                    IS_AI_READ_SCCESS = true;
                                    showWhenAIReadSuccess(getString(R.string.water_number)+gcsObjectChosen.getText());
                                    //CommonHelper.showDAG(AGhiChiSoAPI.this,getString(R.string.water_number)+gcsObjectChosen.getText(),getString(R.string.choose_gcs_number),GlobalVariable.BUTTON_OK_SHOW,itfDialogButtonProcessing,GlobalVariable.SEND_DATA_GCS,GlobalVariable.SEND_DATA_GCS);
                                }
                            }
                        }else{
                            IS_AI_READ_SCCESS = false;
                            GcsObject o1 = new GcsObject();
                            o1.setValue(false);
                            o1.setText(getString(R.string.ko_doc_dc_text));
                            mArrGCS.add(o1);

                            failed(failedStr);
                        }

                    }else{

                    }

                }else{
//                CommonHelper.showWarning(AGhiChiSoAPI.this,getString(R.string.ko_doc_dc_text));
                    IS_AI_READ_SCCESS = false;
                    failed(failedStr);
                }
            }else{
                IS_AI_READ_SCCESS = false;
                failed(failedStr);
            }

        }
    }
    public class GCSTaskTextAPIHWC  extends AsyncTask<String, Void, Void> {
        Bitmap bm;
        Img2TextObj img2TextObj;
        boolean isSuccess = false;

        //String xxx = "";
        GCSTaskTextAPIHWC(Bitmap bm){
            this.bm = bm;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
            mArrGCS.clear();
        }


        public Void doInBackground(String... params) {
            bm = CommonHelper.scaleImgDown(bm,GlobalVariable.MAXIMAGESIZE_AI,true);
            AGhiChiSoPrepare.BCSC.setBm(bm);
            String base64 = CommonHelper.bitmapToBase64(bm);

            GetTextFromBitmapAPI_HWC_Response getTextFromBitmapAPIHWCResponse = new ResultFromWebservice().getTextFromBitmapAPI_HWC_Response(getApplicationContext(),base64, GlobalVariable.LOGIN_TOKEN_TYPE , GlobalVariable.LOGIN_ACCESS_TOKEN,AGhiChiSoAPI.this);

            isSuccess = getTextFromBitmapAPIHWCResponse.isSuccess();
            numberAIRead = getTextFromBitmapAPIHWCResponse.getReturnString();
            /*
            GetTextFromBitmapAPIResponse getTextFromBitmapAPIResponse = new ResultFromWebservice().getTextFromBitmapAPIResponse(getApplicationContext(),base64,"");
            img2TextObj = getTextFromBitmapAPIResponse.getImg2TextObj();
            isSuccess = getTextFromBitmapAPIResponse.isSuccess();
            failedStr = getTextFromBitmapAPIResponse.getReturnString();
            */
            return null;
        }

        public void onPostExecute(Void result) {
            disMissLoading();
            if(FOR_TEST){
                isSuccess = true; //for test
            }
            if(isSuccess){

                    if(CommonHelper.checkValidString(numberAIRead)){
                        GcsObject o1 = new GcsObject();
                        o1.setValue(true);
                        o1.setText(numberAIRead);
                        ChiSo_AI_Got = o1.getText();
                        mArrGCS.add(o1);
                    }else {
                        // AI khong doc duoc chi so
                        ChiSo_AI_Got = "";
                    }
                    if(FOR_TEST) {
                        test(); //for test
                    }
                    if(FOR_TEST2){
                        test2();
                    }
                    if(mArrGCS != null){
                        if(mArrGCS.size() >0){
                            listViewGCSAdapter.refresh(mArrGCS);
                            showView();
                            if(mArrGCS.size()==1){
                                gcsObjectChosen = mArrGCS.get(0);
                                if(gcsObjectChosen.isValue()){
                                    IS_AI_READ_SCCESS = true;
                                    showWhenAIReadSuccess(getString(R.string.water_number)+gcsObjectChosen.getText());
                                    //CommonHelper.showDAG(AGhiChiSoAPI.this,getString(R.string.water_number)+gcsObjectChosen.getText(),getString(R.string.choose_gcs_number),GlobalVariable.BUTTON_OK_SHOW,itfDialogButtonProcessing,GlobalVariable.SEND_DATA_GCS,GlobalVariable.SEND_DATA_GCS);
                                }
                            }
                        }else{
                            IS_AI_READ_SCCESS = false;
                            GcsObject o1 = new GcsObject();
                            o1.setValue(false);
                            o1.setText(getString(R.string.ko_doc_dc_text));
                            mArrGCS.add(o1);

                            failed(failedStr);
                        }

                    }else{

                    }


            }else{
                IS_AI_READ_SCCESS = false;
                failed(failedStr);
            }

        }
    }
    private void failed(String str){
        showWhenAIReadFail();
        /*
        Intent returnIntent = new Intent();
        returnIntent.putExtra(GlobalVariable.KEY_GET_VALUE_GCS_FAILED,str);
        setResult(Activity.RESULT_CANCELED,returnIntent);
        finish();
        CommonHelper.showToast(getApplicationContext(),getString(R.string.ko_doc_dc_text));
        */
    }
    private boolean IS_AI_READ_SCCESS = false;
    private void showWhenAIReadSuccess(String str){
        ArrayList<TypeDialog> mArrtypes = new ArrayList<>();
        TypeDialog t1 = new TypeDialog();
        t1.setType(1);
        t1.setTitle(getString(R.string.ok));

        TypeDialog t2 = new TypeDialog();
        t2.setType(2);
        t2.setTitle(getString(R.string.gcs_btn1));

        TypeDialog t3 = new TypeDialog();
        t3.setType(3);
        t3.setTitle(getString(R.string.gcs_btn2));

        mArrtypes.add(t1);
        mArrtypes.add(t2);
        mArrtypes.add(t3);
        CommonHelper.showDAG3Btn_AI(AGhiChiSoAPI.this,CommonHelper.checkValidString(str)?str:getString(R.string.app_name),getString(R.string.gcs_ai_can_read),mArrtypes,itf3BtnAI);
    }
    private void showWhenAIReadFail(){
        ArrayList<TypeDialog> mArrtypes = new ArrayList<>();
        TypeDialog t1 = new TypeDialog();
        t1.setType(1);
        t1.setTitle(getString(R.string.gcs_btn1));

        TypeDialog t2 = new TypeDialog();
        t2.setType(2);
        t2.setTitle(getString(R.string.gcs_btn2));
        mArrtypes.add(t1);
        mArrtypes.add(t2);
        CommonHelper.showDAG3Btn_AI(AGhiChiSoAPI.this,getString(R.string.app_name),getString(R.string.gcs_ai_can_not_read),mArrtypes,itf3BtnAI);
    }

    private void test(){
        /*
        Img2TextObj img2TextObj = new Img2TextObj();
        String key1 = "3";
        String key2 = "5";
        ArrayList<String> mArrx1 = new ArrayList<>();
        ArrayList<String> mArrx2 = new ArrayList<>();
        img2TextObj.getNumber_shapes().put(key1,mArrx1);
        img2TextObj.getNumber_shapes().put(key2,mArrx2);

        String correctNumber = getCorrectNumber(img2TextObj);
        */
        mArrGCS.clear();
        GcsObject o1 = new GcsObject();
        o1.setValue(true);
        //o1.setText(correctNumber);
        o1.setText("00123xx79d");
        mArrGCS.add(o1);
        ChiSo_AI_Got = o1.getText();
    }
    private void test2(){
        mArrGCS.clear();
        GcsObject o1 = new GcsObject();
        o1.setValue(true);
        o1.setText("00123xx58d");
        mArrGCS.add(o1);
        ChiSo_AI_Got = o1.getText();
    }
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(PATH_FIRST);
        //Uri contentUri = Uri.fromFile(f);
        Uri contentUri =  FileProvider.getUriForFile(AGhiChiSoAPI.this,
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
                if(GlobalVariable.IS_GCS){
                    if(CommonHelper.isCameraPermissionGranted(this)){
                        takePhoto();
                    }
                }
            } else
            {
                hideView();
                id_btn_downup.setEnabled(false);
//                CommonHelper.showWarning(this,getString(R.string.not_allow_access_sdcard));
                CommonHelper.showDAG(this,null,getString(R.string.not_allow_access_sdcard),GlobalVariable.BUTTON_OK_NOT_SHOW,itfDialogButtonProcessing,GlobalVariable.DO_NOTHING,GlobalVariable.DO_NOTHING);
            }
        }else if(requestCode == GlobalVariable.REQUEST_CAMERA){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                if(GlobalVariable.IS_GCS){
                    if(CommonHelper.isStoragePermissionGranted(this)){
                        takePhoto();
                    }
                }

            } else
            {
                hideView();
                id_btn_downup.setEnabled(false);
                CommonHelper.showDAG(this,null,getString(R.string.not_allow_access_camera),GlobalVariable.BUTTON_OK_NOT_SHOW,itfDialogButtonProcessing,GlobalVariable.DO_NOTHING,GlobalVariable.DO_NOTHING);
            }
        }
    }

    @Override
    public void okBtn(int type) {

        if(type == GlobalVariable.DO_NOTHING){

        }else if(type == GlobalVariable.SEND_DATA_GCS){

            success();
            //new GCSTaskSendData().execute();
        }
    }

    private void success(){
        if(CommonHelper.isNumeric(gcsObjectChosen.getText())){

            Intent returnIntent = new Intent();
            returnIntent.putExtra(GlobalVariable.KEY_GET_VALUE_GCS,gcsObjectChosen.getText());
            setResult(Activity.RESULT_OK,returnIntent);

            AGhiChiSoPrepare.BCSC.setAiAPIText(gcsObjectChosen.getText());
            finish();
        }else{
            CommonHelper.showWarning(AGhiChiSoAPI.this,getString(R.string.gcs_gcs_invalid_number_api));
        }

    }
    @Override
    public void cancelBtn(int type) {
//        chkShowDialog = true;
        if(type == GlobalVariable.DO_NOTHING){
            finish();
        }else if(type == GlobalVariable.SEND_DATA_GCS){

        }
    }

    @Override
    public void okBtn(int type, String tenKh, String sdt, String diachi) {

    }

    @Override
    public void okBtn(int type, String tenKh, String sdt, String diachi, String hdtimvitrisuco) {

    }

    public class GCSTask extends AsyncTask<String, Void, Void> {
        Bitmap bm;
        GCSTask(Bitmap bm){
            this.bm = bm;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
            mArrGCS.clear();
        }

        public Void doInBackground(String... params) {
//            try {
//                Thread.currentThread();
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            runTextRecognition(bm);

            return null;
        }

        public void onPostExecute(Void result) {
            disMissLoading();
//            showBitmapImage(bm);
            for(int i = 0; i<3;i++){
                GcsObject o1 = new GcsObject();
                if(i==0){
                    o1.setText("54347");
                }else if(i==1){
                    o1.setText("47880989");
                }else if(i==2){
                    o1.setText("543gfd47");
                }
                mArrGCS.add(o1);
            }
            listViewGCSAdapter.refresh(mArrGCS);
        }
    }
    public class GCSTaskSendData extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();

        }

        public Void doInBackground(String... params) {
            try {
                Thread.currentThread();
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            return null;
        }

        public void onPostExecute(Void result) {
            disMissLoading();
            CommonHelper.showToast(getApplicationContext(),getString(R.string.send_data_gcs_success));
            finish();
        }
    }
    // Max width (portrait mode)
    private Integer mImageMaxWidth;
    // Max height (portrait mode)
    private Integer mImageMaxHeight;


    private static final int DIM_BATCH_SIZE = 1;
    private static final int DIM_PIXEL_SIZE = 3;
    private static final int DIM_IMG_SIZE_X = 224;
    private static final int DIM_IMG_SIZE_Y = 224;
    private List<String> mLabelList;
    private static final String LABEL_PATH = "labels.txt";

    private FirebaseModelInputOutputOptions mDataOptions;
    private static final String HOSTED_MODEL_NAME = "cloud_model_1";
    private static final String LOCAL_MODEL_ASSET = "mobilenet_v1_1.0_224_quant.tflite";
    private FirebaseModelInterpreter mInterpreter;


    private ProgressDialog dialogLoading;
    public void showLoading() {
        if (dialogLoading != null) {
            dialogLoading.dismiss();
        }
        if (dialogLoading == null) {
            dialogLoading = new ProgressDialog(this);
        }
        dialogLoading.setTitle(this.getResources().getString(R.string.app_name));
        dialogLoading.setMessage(getString(R.string.processing));
        dialogLoading.setCanceledOnTouchOutside(false);
        dialogLoading.setCancelable(false);
        dialogLoading.show();
    }

    public void disMissLoading() {

        if (dialogLoading != null) {
            dialogLoading.dismiss();
        }
    }
    private void runTextRecognition() {
        if(mSelectedImage != null){
            FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(mSelectedImage);
            FirebaseVisionTextRecognizer recognizer = FirebaseVision.getInstance()
                    .getOnDeviceTextRecognizer();
//        mTextButton.setEnabled(false);
            recognizer.processImage(image)
                    .addOnSuccessListener(
                            new OnSuccessListener<FirebaseVisionText>() {
                                @Override
                                public void onSuccess(FirebaseVisionText texts) {
//                                mTextButton.setEnabled(true);
                                    processTextRecognitionResult(texts);
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Task failed with an exception
//                                mTextButton.setEnabled(true);
                                    e.printStackTrace();
                                }
                            });
        }

    }
    private void processTextRecognitionResult(FirebaseVisionText texts) {
        List<FirebaseVisionText.TextBlock> blocks = texts.getTextBlocks();
        if (blocks.size() == 0) {
            showToast("No text found");
            return;
        }
        mGraphicOverlay.clear();
        mArrGCS.clear();
        for (int i = 0; i < blocks.size(); i++) {
            List<FirebaseVisionText.Line> lines = blocks.get(i).getLines();
            for (int j = 0; j < lines.size(); j++) {
                List<FirebaseVisionText.Element> elements = lines.get(j).getElements();
                for (int k = 0; k < elements.size(); k++) {
                    GraphicOverlay.Graphic textGraphic = new TextGraphic(mGraphicOverlay, elements.get(k));
//                    Log.d("XXXXXXXXXX====>>>>>",""+elements.get(k).getText());

                        GcsObject o1 = new GcsObject();
                        o1.setText(elements.get(k).getText());
                        mArrGCS.add(o1);
//                        if(CommonHelper.validateGCS(o1.getText())){
//
//                        }

                    listViewGCSAdapter.refresh(mArrGCS);

                    mGraphicOverlay.add(textGraphic);

                }
            }
        }
    }



        private void initCustomModel() {
        // Replace with code from the codelab to initialize your custom model
        mLabelList = loadLabelList(this);

        int[] inputDims = {DIM_BATCH_SIZE, DIM_IMG_SIZE_X, DIM_IMG_SIZE_Y, DIM_PIXEL_SIZE};
        int[] outputDims = {DIM_BATCH_SIZE, mLabelList.size()};
        try {
            mDataOptions =
                    new FirebaseModelInputOutputOptions.Builder()
                            .setInputFormat(0, FirebaseModelDataType.BYTE, inputDims)
                            .setOutputFormat(0, FirebaseModelDataType.BYTE, outputDims)
                            .build();
            FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions
                    .Builder()
                    .requireWifi()
                    .build();
            FirebaseRemoteModel remoteModel = new FirebaseRemoteModel.Builder
                    (HOSTED_MODEL_NAME)
                    .enableModelUpdates(true)
                    .setInitialDownloadConditions(conditions)
                    .setUpdatesDownloadConditions(conditions)  // You could also specify
                    // different conditions
                    // for updates
                    .build();
            FirebaseLocalModel localModel =
                    new FirebaseLocalModel.Builder("asset")
                            .setAssetFilePath(LOCAL_MODEL_ASSET).build();
            FirebaseModelManager manager = FirebaseModelManager.getInstance();
            manager.registerRemoteModel(remoteModel);
            manager.registerLocalModel(localModel);
            FirebaseModelOptions modelOptions =
                    new FirebaseModelOptions.Builder()
                            .setRemoteModelName(HOSTED_MODEL_NAME)
                            .setLocalModelName("asset")
                            .build();
            mInterpreter = FirebaseModelInterpreter.getInstance(modelOptions);
        } catch (FirebaseMLException e) {
            showToast("Error while setting up the model");
            e.printStackTrace();
        }
    }

    private void runModelInference() {
        // Replace with code from the codelab to run inference using your custom model.
        if (mInterpreter == null) {
//            Log.e(TAG, "Image classifier has not been initialized; Skipped.");
            return;
        }
        // Create input data.
        ByteBuffer imgData = convertBitmapToByteBuffer(mSelectedImage, mSelectedImage.getWidth(),
                mSelectedImage.getHeight());

        try {
            FirebaseModelInputs inputs = new FirebaseModelInputs.Builder().add(imgData).build();
            // Here's where the magic happens!!
            mInterpreter
                    .run(inputs, mDataOptions)
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                            showToast("Error running model inference");
                        }
                    })
                    .continueWith(
                            new Continuation<FirebaseModelOutputs, List<String>>() {
                                @Override
                                public List<String> then(Task<FirebaseModelOutputs> task) {
                                    byte[][] labelProbArray = task.getResult()
                                            .<byte[][]>getOutput(0);
                                    List<String> topLabels = getTopLabels(labelProbArray);
                                    mGraphicOverlay.clear();
                                    GraphicOverlay.Graphic labelGraphic = new LabelGraphic(mGraphicOverlay, topLabels);
                                    mGraphicOverlay.add(labelGraphic);
                                    return topLabels;
                                }
                            });
        } catch (FirebaseMLException e) {
            e.printStackTrace();
            showToast("Error running model inference");
        }

    }

    private void runCloudTextRecognition() {
        // Replace with code from the codelab to run cloud text recognition.
//        mCloudButton.setEnabled(false);
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(mSelectedImage);
        FirebaseVisionDocumentTextRecognizer recognizer = FirebaseVision.getInstance()
                .getCloudDocumentTextRecognizer();
        recognizer.processImage(image)
                .addOnSuccessListener(
                        new OnSuccessListener<FirebaseVisionDocumentText>() {
                            @Override
                            public void onSuccess(FirebaseVisionDocumentText texts) {
//                                mCloudButton.setEnabled(true);
                                processCloudTextRecognitionResult(texts);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception
//                                mCloudButton.setEnabled(true);
                                e.printStackTrace();
                            }
                        });
    }

    private void processCloudTextRecognitionResult(FirebaseVisionDocumentText text) {
        // Replace with code from the codelab to process the cloud text recognition result.
        // Task completed successfully
        if (text == null) {
            showToast("No text found");
            return;
        }
        mGraphicOverlay.clear();
        List<FirebaseVisionDocumentText.Block> blocks = text.getBlocks();
        for (int i = 0; i < blocks.size(); i++) {
            List<FirebaseVisionDocumentText.Paragraph> paragraphs = blocks.get(i).getParagraphs();
            for (int j = 0; j < paragraphs.size(); j++) {
                List<FirebaseVisionDocumentText.Word> words = paragraphs.get(j).getWords();
                for (int l = 0; l < words.size(); l++) {
                    CloudTextGraphic cloudDocumentTextGraphic = new CloudTextGraphic(mGraphicOverlay,
                            words.get(l));
                    mGraphicOverlay.add(cloudDocumentTextGraphic);
                }
            }
        }
    }

    /**
     * Gets the top labels in the results.
     */
    private synchronized List<String> getTopLabels(byte[][] labelProbArray) {
        for (int i = 0; i < mLabelList.size(); ++i) {
            sortedLabels.add(
                    new AbstractMap.SimpleEntry<>(mLabelList.get(i), (labelProbArray[0][i] &
                            0xff) / 255.0f));
            if (sortedLabels.size() > RESULTS_TO_SHOW) {
                sortedLabels.poll();
            }
        }
        List<String> result = new ArrayList<>();
        final int size = sortedLabels.size();
        for (int i = 0; i < size; ++i) {
            Map.Entry<String, Float> label = sortedLabels.poll();
            result.add(label.getKey() + ":" + label.getValue());
        }
//        Log.d(TAG, "labels: " + result.toString());
        return result;
    }

    /**
     * Reads label list from Assets.
     */
    private List<String> loadLabelList(Activity activity) {
        List<String> labelList = new ArrayList<>();
        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(activity.getAssets().open
                             (LABEL_PATH)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                labelList.add(line);
            }
        } catch (IOException e) {
//            Log.e(TAG, "Failed to read label list.", e);
        }
        return labelList;
    }

    /**
     * Writes Image data into a {@code ByteBuffer}.
     */
    private synchronized ByteBuffer convertBitmapToByteBuffer(
            Bitmap bitmap, int width, int height) {
        ByteBuffer imgData =
                ByteBuffer.allocateDirect(
                        DIM_BATCH_SIZE * DIM_IMG_SIZE_X * DIM_IMG_SIZE_Y * DIM_PIXEL_SIZE);
        imgData.order(ByteOrder.nativeOrder());
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, DIM_IMG_SIZE_X, DIM_IMG_SIZE_Y,
                true);
        imgData.rewind();
        scaledBitmap.getPixels(intValues, 0, scaledBitmap.getWidth(), 0, 0,
                scaledBitmap.getWidth(), scaledBitmap.getHeight());
        // Convert the image to int points.
        int pixel = 0;
        for (int i = 0; i < DIM_IMG_SIZE_X; ++i) {
            for (int j = 0; j < DIM_IMG_SIZE_Y; ++j) {
                final int val = intValues[pixel++];
                imgData.put((byte) ((val >> 16) & 0xFF));
                imgData.put((byte) ((val >> 8) & 0xFF));
                imgData.put((byte) (val & 0xFF));
            }
        }
        return imgData;
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    // Functions for loading images from app assets.

    // Returns max image width, always for portrait mode. Caller needs to swap width / height for
    // landscape mode.
    private Integer getImageMaxWidth() {
        if (mImageMaxWidth == null) {
            // Calculate the max width in portrait mode. This is done lazily since we need to
            // wait for
            // a UI layout pass to get the right values. So delay it to first time image
            // rendering time.
            mImageMaxWidth = mImageView.getWidth();
        }

        return mImageMaxWidth;
    }

    // Returns max image height, always for portrait mode. Caller needs to swap width / height for
    // landscape mode.
    private Integer getImageMaxHeight() {
        if (mImageMaxHeight == null) {
            // Calculate the max width in portrait mode. This is done lazily since we need to
            // wait for
            // a UI layout pass to get the right values. So delay it to first time image
            // rendering time.
            mImageMaxHeight =
                    mImageView.getHeight();
        }

        return mImageMaxHeight;
    }

    // Gets the targeted width / height.
    private Pair<Integer, Integer> getTargetedWidthHeight() {
        int targetWidth;
        int targetHeight;
        int maxWidthForPortraitMode = getImageMaxWidth();
        int maxHeightForPortraitMode = getImageMaxHeight();
        targetWidth = maxWidthForPortraitMode;
        targetHeight = maxHeightForPortraitMode;
        return new Pair<>(targetWidth, targetHeight);
    }

    @Override
    public void clickBtn(int index) {
        if(IS_AI_READ_SCCESS){
            if(index == 1){
                success();
            }else if(index ==2){
                //FOR_TEST2 = true;
                //FOR_TEST = false;
                takePhoto();
            }else if(index ==3){
                Intent returnIntent = new Intent();
                returnIntent.putExtra(GlobalVariable.KEY_GET_VALUE_GCS_FAILED,failedStr);
                returnIntent.putExtra(GlobalVariable.KEY_GET_VALUE_GCS_INCORRECT, ChiSo_AI_Got);
                setResult(Activity.RESULT_CANCELED,returnIntent);
                finish();
                CommonHelper.showToast(getApplicationContext(),getString(R.string.ko_doc_dc_text));
            }
        }else{
            if(index == 1){
                //FOR_TEST = false;
                takePhoto();
            }else if(index ==2){
                //
                Intent returnIntent = new Intent();
                returnIntent.putExtra(GlobalVariable.KEY_GET_VALUE_GCS_FAILED,failedStr);
                returnIntent.putExtra(GlobalVariable.KEY_GET_VALUE_GCS_INCORRECT, ChiSo_AI_Got);
                setResult(Activity.RESULT_CANCELED,returnIntent);
                finish();
                CommonHelper.showToast(getApplicationContext(),getString(R.string.ko_doc_dc_text));
            }
        }
    }
}
