package com.huewaco.cskh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.huewaco.cskh.activity.ABaoCaoSuCo;
import com.huewaco.cskh.activity.ABaoCaoSuCo_List;
import com.huewaco.cskh.activity.AParent;
import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.objects.BaoCaoSuCoObj;
import com.huewaco.cskh.objects.BaoCaoSuCo_Post;
import com.huewaco.cskh.objects.ImgInPostObj;

import java.util.ArrayList;


public class ListViewBaoCaoSuCoListAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<BaoCaoSuCo_Post> mArrSuco;
    private AParent activity;

    public ListViewBaoCaoSuCoListAdapter(Context context, ArrayList<BaoCaoSuCo_Post> mArr, AParent activity) {
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
        private TextView id_tv_title_1,id_tv_centent_1,id_tv_date_1,id_tv_status_1;
        private Gallery id_glr_imgs;
        /*
        private ViewPager id_view_pg_main;
        private ViewPagerBaoCaoSuCoAdapter viewPagerBaoCaoSuCoAdapter;
        */
        private BaoCaoSuCoGalaryAdapter glAdapter;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        layoutInflater = LayoutInflater.from(context);
        ViewHolder viewholder = null;
        final BaoCaoSuCo_Post item = mArrSuco.get(i);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_listview_baocaosuco_list,
                    null);
            viewholder = new ViewHolder();
            viewholder.id_tv_title_1 = (TextView) convertView
                    .findViewById(R.id.id_tv_title_1);
            viewholder.id_tv_centent_1 = (TextView) convertView
                    .findViewById(R.id.id_tv_centent_1);
            viewholder.id_tv_date_1 = (TextView) convertView
                    .findViewById(R.id.id_tv_date_1);
            viewholder.id_tv_status_1 = (TextView) convertView
                    .findViewById(R.id.id_tv_status_1);
            viewholder.id_img_sc = (ImageView) convertView
                    .findViewById(R.id.id_img_sc);

            /*
            viewholder.id_view_pg_main = (ViewPager)convertView.findViewById(R.id.id_view_pg_main);
            viewholder.viewPagerBaoCaoSuCoAdapter = new ViewPagerBaoCaoSuCoAdapter(activity, context,item.getImages());
            viewholder.id_view_pg_main.setAdapter(viewholder.viewPagerBaoCaoSuCoAdapter);
            */
            viewholder.id_glr_imgs = (Gallery)convertView.findViewById(R.id.id_glr_imgs);
            viewholder.glAdapter = new BaoCaoSuCoGalaryAdapter(context,item.getImages(),activity);
            viewholder.id_glr_imgs.setAdapter(viewholder.glAdapter);
            convertView.setTag(viewholder);
        }
        viewholder = (ViewHolder) convertView.getTag();
        if(item != null){


            viewholder.id_tv_title_1.setText(item.getDiaChiSuCoTheoMobile());
            viewholder.id_tv_centent_1.setText(item.getNoiDung());
            viewholder.id_tv_date_1.setText(item.getNgayTao());
            viewholder.id_tv_status_1.setText(item.isDaXuLy()?context.getString(R.string.bcsc_daxuly):context.getString(R.string.bcsc_dangxuly));
//            ImageLoadert.getInstance(context).load(viewholder.id_img_sc,item.getImages().get(0).getBase64(),false);
            viewholder.id_tv_title_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.showMap(item);
                }
            });
            viewholder.id_glr_imgs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    activity.showGalleryImgs(item);
                }
            });
            /*
            int POSITION_IMG = checkValidImg(item);
            if(POSITION_IMG != -1){
                viewholder.id_view_pg_main.setVisibility(View.VISIBLE);

                //viewholder.id_view_pg_main.setOnPageChangeListener(activity);
                viewholder.id_view_pg_main.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                viewholder.id_view_pg_main.setCurrentItem(POSITION_IMG);

                viewholder.id_view_pg_main.removeAllViews();
                for (int k = 0; k < item.getImages().size(); k++) {
                    viewholder.id_view_pg_main.addView(new View(activity));
                }

            }else{
                viewholder.id_view_pg_main.setVisibility(View.GONE);
            }
            */
        }



        return convertView;
    }
    private int checkValidImg(BaoCaoSuCo_Post item ){
        int chk = -1;
        if(item.getImages() != null){
            for(int i = 0; i < item.getImages().size(); i++){
                ImgInPostObj img = item.getImages().get(i);
                if(CommonHelper.checkValidString(img.getTenFile())){
                    return i;
                }
            }
        }else{
            chk = -1;
        }
        return chk;
    }
    public void refresh(ArrayList<BaoCaoSuCo_Post> mArrGCS) {
        this.mArrSuco = mArrGCS;
        notifyDataSetChanged();
    }
}
