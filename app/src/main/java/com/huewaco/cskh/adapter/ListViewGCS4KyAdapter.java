package com.huewaco.cskh.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.huewaco.cskh.activity.AShowGalleryImages_BaoCaoSuCo;
import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.ImageLoaderOnline;
import com.huewaco.cskh.objects.CSTieuThuListItemObj;
import com.huewaco.cskh.objects.GhiChiSo4KiCuoi;
import com.huewaco.cskh.objects.ImgInPostObj;

import java.util.ArrayList;


public class ListViewGCS4KyAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<GhiChiSo4KiCuoi> mArrCSTieuThu;

    public ListViewGCS4KyAdapter(Context context, ArrayList<GhiChiSo4KiCuoi> mArr) {
        this.context = context;
        this.mArrCSTieuThu = mArr;
    }

    @Override
    public int getCount() {
        return mArrCSTieuThu.size();
    }

    @Override
    public Object getItem(int i) {
        return mArrCSTieuThu.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        private TextView id_tv_thang_nam_gcs_title;
        private TextView id_tv_lichghinuoc_gcs_csd;
        private TextView id_tv_lichghinuoc_gcs_csc;
        private TextView id_tv_lichghinuoc_gcs_kltt;
        private TextView id_tv_dichvu_gcs_tt;
        private TextView id_tv_dichvu_gcs_ngayxacthuc;
        private CheckBox id_cb_tthai_detail_daxacthuc;
        private ProgressBar id_pg;
        private ImageView id_img_sc;
        private ViewGroup id_ly_item_gcs;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        layoutInflater = LayoutInflater.from(context);
        ViewHolder viewholder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_listview_gcs_4kycuoi,
                    null);
            viewholder = new ViewHolder();

            viewholder.id_ly_item_gcs = (ViewGroup) convertView
                    .findViewById(R.id.id_ly_item_gcs);

            viewholder.id_tv_thang_nam_gcs_title = (TextView) convertView
                    .findViewById(R.id.id_tv_thang_nam_gcs_title);
            viewholder.id_tv_lichghinuoc_gcs_csd = (TextView) convertView
                    .findViewById(R.id.id_tv_lichghinuoc_gcs_csd);
            viewholder.id_tv_lichghinuoc_gcs_csc = (TextView) convertView
                    .findViewById(R.id.id_tv_lichghinuoc_gcs_csc);
            viewholder.id_tv_lichghinuoc_gcs_kltt = (TextView) convertView
                    .findViewById(R.id.id_tv_lichghinuoc_gcs_kltt);
            viewholder.id_tv_dichvu_gcs_tt = (TextView) convertView
                    .findViewById(R.id.id_tv_dichvu_gcs_tt);
            viewholder.id_cb_tthai_detail_daxacthuc = (CheckBox) convertView
                    .findViewById(R.id.id_cb_tthai_detail_daxacthuc);
            viewholder.id_tv_dichvu_gcs_ngayxacthuc = (TextView) convertView
                    .findViewById(R.id.id_tv_dichvu_gcs_ngayxacthuc);

            viewholder.id_pg = (ProgressBar) convertView
                    .findViewById(R.id.id_pg);
            viewholder.id_img_sc = (ImageView) convertView
                    .findViewById(R.id.id_img_sc);
            convertView.setTag(viewholder);
        }
        viewholder = (ViewHolder) convertView.getTag();
        if(mArrCSTieuThu != null && mArrCSTieuThu.size()>0){
            final GhiChiSo4KiCuoi item = mArrCSTieuThu.get(i);
            //viewholder.id_tv_thang_nam_gcs_title.setText(CommonHelper.getDateString2FromStringDate1(item.getNgayNhap(),"dd-MM-yyyy"));
            viewholder.id_tv_thang_nam_gcs_title.setText("Kỳ Ghi: " + item.getThang() + "/" + item.getNam() + " ("+item.getNgayNhap()+")");

            viewholder.id_tv_lichghinuoc_gcs_csd.setText(" "+CommonHelper.getStringWithSeparatorThousand(item.getChiSoDau()));
            viewholder.id_tv_lichghinuoc_gcs_csc.setText(" "+CommonHelper.getStringWithSeparatorThousand(item.getChiSoCuoi()));
            viewholder.id_tv_lichghinuoc_gcs_kltt.setText(" "+CommonHelper.getStringWithSeparatorThousand(item.getKhoiLuongTieuThu()));
            viewholder.id_tv_dichvu_gcs_tt.setText(" "+CommonHelper.getStringWithSeparatorThousand(item.getTongTien()));
            if(CommonHelper.checkValidString(item.getNgayXacThuc())){
                viewholder.id_tv_dichvu_gcs_ngayxacthuc.setText(" "+CommonHelper.getDateString2FromStringDate2(item.getNgayXacThuc(),"dd-MM-yyyy"));
            }else{
                viewholder.id_tv_dichvu_gcs_ngayxacthuc.setText("");
            }
            if(item.isDaXacThuc()){
               // viewholder.id_cb_tthai_detail_daxacthuc.setVisibility(View.VISIBLE);
                viewholder.id_cb_tthai_detail_daxacthuc.setChecked(true);
            }else{
                viewholder.id_cb_tthai_detail_daxacthuc.setChecked(false);
               // viewholder.id_cb_tthai_detail_daxacthuc.setVisibility(View.GONE);
            }
            if(item.getmArrFiles() != null && item.getmArrFiles().size()>0){
                if(CommonHelper.checkValidString(item.getmArrFiles().get(0))){
                    ImageLoaderOnline.getInstance().load(null, viewholder.id_pg, viewholder.id_img_sc, item.getmArrFiles().get(0),false);
                }
            }
            viewholder.id_ly_item_gcs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showImg(item);
                }
            });
        }

        return convertView;
    }
    public void showImg(GhiChiSo4KiCuoi item){
        if(item.getmArrFiles()!= null && item.getmArrFiles().size()>0){
            ArrayList<ImgInPostObj> mArrPosts = new ArrayList<>();
            for(String str : item.getmArrFiles()){
                ImgInPostObj po = new ImgInPostObj();
                po.setTenFile(str);
                mArrPosts.add(po);
            }
            AShowGalleryImages_BaoCaoSuCo.mArrImg = mArrPosts;
            context.startActivity(new Intent(context, AShowGalleryImages_BaoCaoSuCo.class));
        }

    }
    public void refresh(ArrayList<GhiChiSo4KiCuoi> mArrCSTieuT){
        mArrCSTieuThu = mArrCSTieuT;
        notifyDataSetChanged();
    }
}
