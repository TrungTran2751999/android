package com.huewaco.cskh.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.huewaco.cskh.activity.ABaoCaoSuCo_List;
import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.helper.ImageLoaderOnline;
import com.huewaco.cskh.objects.ImgInPostObj;

public class BaoCaoSuCoViewPagerDetail extends LinearLayout {
    private ABaoCaoSuCo_List parent;
    private Context context;
    private ImgInPostObj item;
    private ImageView id_img_sc;
    public BaoCaoSuCoViewPagerDetail(ABaoCaoSuCo_List parent, Context context, ImgInPostObj objKH){
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = (LinearLayout) inflater.inflate(R.layout.item_viewpager_baocaosuco, this);
        this.parent = parent;
        this.context = context;
        this.item = objKH;
        initCommponent(v);
    }
    private void initCommponent(View v ){
        id_img_sc = (ImageView)v.findViewById(R.id.id_img_sc);
        String imgpath = item.getTenFile();
        ImageLoaderOnline.getInstance().load(null,null,id_img_sc,imgpath,false);
    }
}
