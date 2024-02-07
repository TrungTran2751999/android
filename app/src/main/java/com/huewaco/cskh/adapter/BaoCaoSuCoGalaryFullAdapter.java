package com.huewaco.cskh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.huewaco.cskh.activity.AParent;
import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.ImageLoaderOnline;
import com.huewaco.cskh.objects.ImgInPostObj;

import java.util.List;

public class BaoCaoSuCoGalaryFullAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;

    private List<ImgInPostObj> mArrItems;
    private Context mContext;
    private AParent activity;
    public BaoCaoSuCoGalaryFullAdapter(Context context, List<ImgInPostObj> mArrItems, AParent activity) {
        this.mContext = context;
        this.mArrItems = mArrItems;
        this.activity = activity;
    }
    public void refresh(List<ImgInPostObj> mArrItems){
        this.mArrItems = mArrItems;
        notifyDataSetChanged();
    }
    public int getCount() {
        return mArrItems.size();
    }

    public Object getItem(int position) {
        return mArrItems.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        private ImageView id_img_sc;
        private ProgressBar id_pg;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        layoutInflater = LayoutInflater.from(mContext);
        ViewHolder viewholder = null;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_gallery_full_baocaosuco,
                    null);
            viewholder = new ViewHolder();
            viewholder.id_img_sc = (ImageView) convertView
                    .findViewById(R.id.id_img_sc);

            //
//            final Matrix matrix = viewholder.id_img_sc.getImageMatrix();
//            final float imageWidth = viewholder.id_img_sc.getDrawable().getIntrinsicWidth();
//            final int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
//            final float scaleRatio = screenWidth / imageWidth;
//            matrix.postScale(scaleRatio, scaleRatio);
//            viewholder.id_img_sc.setImageMatrix(matrix);
            //

            viewholder.id_pg = (ProgressBar)convertView.findViewById(R.id.id_pg);
            convertView.setTag(viewholder);

        }
        viewholder = (ViewHolder) convertView.getTag();

        ImgInPostObj b = mArrItems.get(position);
        if(b != null){
            if(CommonHelper.checkValidString(b.getTenFile())){
                //String imgpath = GlobalVariable.DOMAIN_IMAGE_URL + b.getTenFile();
                ImageLoaderOnline.getInstance().load(null, viewholder.id_pg, viewholder.id_img_sc, b.getTenFile(),true);
            }else{
//                viewholder.id_img_sc.setImageDrawable(mContext.getResources().getDrawable(b.getImageId()));
            }
        }

        return convertView;
    }
}
