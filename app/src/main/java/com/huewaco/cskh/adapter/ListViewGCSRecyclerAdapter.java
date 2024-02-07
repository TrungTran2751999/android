package com.huewaco.cskh.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huewaco.cskh.activity.AParent;
import com.huewaco.cskh.activity.AShowGalleryImages_BaoCaoSuCo;
import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.ImageLoaderOnline;
import com.huewaco.cskh.objects.BaoCaoSuCo_Post;
import com.huewaco.cskh.objects.GhiChiSo4KiCuoi;
import com.huewaco.cskh.objects.ImgInPostObj;

import java.util.ArrayList;

import static android.text.TextUtils.isEmpty;


public class ListViewGCSRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<GhiChiSo4KiCuoi> mArrSuco;
    private AParent activity;

    public ListViewGCSRecyclerAdapter(Context context, ArrayList<GhiChiSo4KiCuoi> mArr, AParent activity) {
        this.context = context;
        this.mArrSuco = mArr;
        this.activity = activity;
    }
    @Override
    public long getItemId(int i) {
        return i;
    }

    public void refresh(ArrayList<GhiChiSo4KiCuoi> mArrGCS) {
        this.mArrSuco = mArrGCS;
        notifyDataSetChanged();
    }
    public void refresh() {
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_listview_gcs_4kycuoi, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof ItemViewHolder) {

            populateItemRows((ItemViewHolder) viewHolder, position);
        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }

    }

    @Override
    public int getItemCount() {
        return this.mArrSuco == null ? 0 : this.mArrSuco.size();
    }

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return this.mArrSuco.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder {

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

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            id_ly_item_gcs = (ViewGroup) itemView
                    .findViewById(R.id.id_ly_item_gcs);

            id_tv_thang_nam_gcs_title = (TextView) itemView
                    .findViewById(R.id.id_tv_thang_nam_gcs_title);
            id_tv_lichghinuoc_gcs_csd = (TextView) itemView
                    .findViewById(R.id.id_tv_lichghinuoc_gcs_csd);
            id_tv_lichghinuoc_gcs_csc = (TextView) itemView
                    .findViewById(R.id.id_tv_lichghinuoc_gcs_csc);
            id_tv_lichghinuoc_gcs_kltt = (TextView) itemView
                    .findViewById(R.id.id_tv_lichghinuoc_gcs_kltt);
            id_tv_dichvu_gcs_tt = (TextView) itemView
                    .findViewById(R.id.id_tv_dichvu_gcs_tt);
            id_cb_tthai_detail_daxacthuc = (CheckBox) itemView
                    .findViewById(R.id.id_cb_tthai_detail_daxacthuc);
            id_tv_dichvu_gcs_ngayxacthuc = (TextView) itemView
                    .findViewById(R.id.id_tv_dichvu_gcs_ngayxacthuc);

            id_pg = (ProgressBar) itemView
                    .findViewById(R.id.id_pg);
            id_img_sc = (ImageView) itemView
                    .findViewById(R.id.id_img_sc);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }

    private void populateItemRows(ItemViewHolder viewholder, final int position) {
        final GhiChiSo4KiCuoi item = mArrSuco.get(position);
        if(item != null){
            viewholder.id_tv_thang_nam_gcs_title.setText("Kỳ ghi: " + item.getThang() + "/" + item.getNam() + " ("+item.getNgayNhap()+")");

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
    void setValue(Object value, TextView textView, TableRow row, View seperator) {
        if (value != null) {
            if (!isEmpty(value.toString())) {
                textView.setText(String.valueOf(value));
                showViews(row, seperator);
            }
        } else
            hideViews(row, seperator);
    }

    private void showViews(TableRow row, View seperator) {
        row.setVisibility(View.VISIBLE);
        seperator.setVisibility(View.VISIBLE);
    }

    private void hideViews(TableRow row, View seperator) {
        row.setVisibility(View.INVISIBLE); // if there is a empty space change it with View.GONE
        seperator.setVisibility(View.INVISIBLE);
    }
}
