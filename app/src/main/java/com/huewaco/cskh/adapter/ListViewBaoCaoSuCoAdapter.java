package com.huewaco.cskh.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.huewaco.cskh.activity.ABaoCaoSuCo;
import com.huewaco.cskh.activity.AGhiChiSoPrepare;
import com.huewaco.cskh.activity.AParent;
import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.interfacex.ITFTakePhotoGallery;
import com.huewaco.cskh.objects.BaoCaoSuCoObj;
import com.huewaco.cskh.objects.GcsObject;

import java.util.ArrayList;


public class ListViewBaoCaoSuCoAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<BaoCaoSuCoObj> mArrSuco;
    private ITFTakePhotoGallery activity;

    public ListViewBaoCaoSuCoAdapter(Context context, ArrayList<BaoCaoSuCoObj> mArr, ITFTakePhotoGallery activity) {
        this.context = context;
        this.mArrSuco = mArr;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return mArrSuco.size();
    }

    @Override
    public Object getItem(int i) {
        return mArrSuco.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        private ImageView id_img_sc;
        private Button id_btn_takephoto,id_btn_gallary,id_btn_cancel;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        layoutInflater = LayoutInflater.from(context);
        ViewHolder viewholder = null;
        final BaoCaoSuCoObj item = mArrSuco.get(i);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_listview_baocaosuco,
                    null);
            viewholder = new ViewHolder();
            viewholder.id_img_sc = (ImageView) convertView
                    .findViewById(R.id.id_img_sc);
            viewholder.id_btn_takephoto = (Button) convertView
                    .findViewById(R.id.id_btn_takephoto);
            viewholder.id_btn_gallary = (Button) convertView
                    .findViewById(R.id.id_btn_gallary);
            viewholder.id_btn_cancel = (Button) convertView
                    .findViewById(R.id.id_btn_cancel);
            convertView.setTag(viewholder);
        }
        viewholder = (ViewHolder) convertView.getTag();
        if(activity instanceof AGhiChiSoPrepare){
            viewholder.id_btn_gallary.setVisibility(View.GONE);
        }else{
            viewholder.id_btn_gallary.setVisibility(View.VISIBLE);
        }
        if(item != null){
            if(item.getBm() != null){
                viewholder.id_img_sc.setImageBitmap(item.getBm());
                viewholder.id_btn_cancel.setVisibility(View.VISIBLE);
            }else{
                viewholder.id_img_sc.setImageResource(R.drawable.no_img);
                viewholder.id_btn_cancel.setVisibility(View.GONE);
            }


            viewholder.id_btn_takephoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.takePhotoX(item);
                }
            });
            viewholder.id_btn_gallary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.galleryX(item);
                }
            });
            viewholder.id_btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.cancelImg(item);
                }
            });
        }



        return convertView;
    }

    public void refresh(ArrayList<BaoCaoSuCoObj> mArrGCS) {
        this.mArrSuco = mArrGCS;
        notifyDataSetChanged();
    }
}
