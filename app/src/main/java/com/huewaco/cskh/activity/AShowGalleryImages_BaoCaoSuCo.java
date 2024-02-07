package com.huewaco.cskh.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Gallery;
import android.widget.ImageView;

import com.huewaco.cskh.adapter.BaoCaoSuCoGalaryAdapter;
import com.huewaco.cskh.adapter.BaoCaoSuCoGalaryFullAdapter;
import com.huewaco.cskh.helper.ImageLoaderOnline;
import com.huewaco.cskh.objects.ImgInPostObj;

import java.util.ArrayList;

public class AShowGalleryImages_BaoCaoSuCo extends AParent {
    private Gallery id_glr_imgs;
    public static ArrayList<ImgInPostObj> mArrImg;
    private BaoCaoSuCoGalaryFullAdapter glAdapter;
    private ImageView id_img_cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showgallery_images_baocaosuco);
        initComponent();
        addListener();
    }

    @Override
    protected void initComponent() {
        //initTopbarView();
        id_glr_imgs = (Gallery)findViewById(R.id.id_glr_imgs);
        id_img_cancel = (ImageView)findViewById(R.id.id_img_cancel);
        //id_tv_title.setText(getString(R.string.ttud_title));
        //id_btn_left.setBackgroundResource(R.drawable.btn_back);


        //mArrImg = new ArrayList<>();
        glAdapter = new BaoCaoSuCoGalaryFullAdapter(this, mArrImg,this);
        id_glr_imgs.setAdapter(glAdapter);
    }

    @Override
    protected void addListener() {
        id_img_cancel.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mArrImg=null;
        ImageLoaderOnline.getInstance().clearQueue();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_img_cancel:
                finish();
                break;
            default:
                break;
        }
    }
}
