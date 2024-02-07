package com.huewaco.cskh.objects;

import android.graphics.Bitmap;

import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;

public class BaoCaoSuCoObj {
    private String aiAPIText="";
    private String link;
    //private boolean isCancel;
    private Bitmap bm;
    private String path;

    private boolean isEnableCamera=true;
    private boolean isProcessAI = false;
    public Bitmap getBm() {
        return bm;
    }

    public void setBm(Bitmap bm) {
        if(bm != null){
            this.bm = CommonHelper.scaleImgDown(bm, GlobalVariable.MAXIMAGESIZE,true);
        }else{
            this.bm = bm;
        }
    }

    public String getAiAPIText() {
        return aiAPIText;
    }

    public void setAiAPIText(String aiAPIText) {
        this.aiAPIText = aiAPIText;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

//    public boolean isCancel() {
//        return isCancel;
//    }

//    public void setCancel(boolean cancel) {
//        isCancel = cancel;
//    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isEnableCamera() {
        return isEnableCamera;
    }

    public void setEnableCamera(boolean enableCamera) {
        isEnableCamera = enableCamera;
    }

    public boolean isProcessAI() {
        return isProcessAI;
    }

    public void setProcessAI(boolean processAI) {
        isProcessAI = processAI;
    }
}
