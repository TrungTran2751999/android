package com.huewaco.cskh.adapter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huewaco.cskh.activity.AParent;
import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.objects.BaoCaoSuCo_Post;
import com.huewaco.cskh.objects.ImgInPostObj;

import java.util.ArrayList;

import static android.text.TextUtils.isEmpty;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


public class ListViewBaoCaoSuCoRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<BaoCaoSuCo_Post> mArrSuco;
    private AParent activity;

    public ListViewBaoCaoSuCoRecyclerAdapter(Context context, ArrayList<BaoCaoSuCo_Post> mArr, AParent activity) {
        this.context = context;
        this.mArrSuco = mArr;
        this.activity = activity;
    }
    @Override
    public long getItemId(int i) {
        return i;
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
    public void refresh() {
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_listview_baocaosuco_list, parent, false);
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

        private TextView id_tv_title_1,id_tv_centent_1,id_tv_date_1,id_tv_status_1,id_tv_phanhoicho_kh;
        private Gallery id_glr_imgs;
        private BaoCaoSuCoGalaryAdapter glAdapter;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            id_tv_title_1 = (TextView) itemView.findViewById(R.id.id_tv_title_1);
            id_tv_centent_1 = (TextView)itemView.findViewById(R.id.id_tv_centent_1);
            id_tv_date_1 = (TextView)itemView.findViewById(R.id.id_tv_date_1);
            id_tv_status_1 = (TextView)itemView.findViewById(R.id.id_tv_status_1);
            id_glr_imgs = (Gallery) itemView.findViewById(R.id.id_glr_imgs);
            id_tv_phanhoicho_kh = (TextView)itemView.findViewById(R.id.id_tv_phanhoicho_kh);


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

    private void populateItemRows(ItemViewHolder viewHolder, final int position) {
        final BaoCaoSuCo_Post item = mArrSuco.get(position);
        if(item != null){
            if(item.isShowPhanHoiChoKh()){
                viewHolder.id_tv_phanhoicho_kh.setVisibility(View.VISIBLE);
            }else{
                viewHolder.id_tv_phanhoicho_kh.setVisibility(View.GONE);
            }
            viewHolder.glAdapter = new BaoCaoSuCoGalaryAdapter(context,item.getImages1(),activity);
            viewHolder.id_glr_imgs.setAdapter(viewHolder.glAdapter);
            viewHolder.id_tv_title_1.setText(item.getDiaChiSuCoTheoMobile());
            viewHolder.id_tv_centent_1.setText(item.getNoiDung());
            if(CommonHelper.checkValidString(item.getThongTinPhanHoiChoKhachHang())){
                viewHolder.id_tv_phanhoicho_kh.setText(item.getThongTinPhanHoiChoKhachHang());
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    viewHolder.id_tv_phanhoicho_kh.setText(Html.fromHtml(item.getThongTinPhanHoiChoKhachHang(), Html.FROM_HTML_MODE_COMPACT));
//                } else {
//                    viewHolder.id_tv_phanhoicho_kh.setText(Html.fromHtml(item.getThongTinPhanHoiChoKhachHang()));
//                }
            }else{
                viewHolder.id_tv_phanhoicho_kh.setText(context.getString(R.string.no_response));
            }

//            viewHolder.id_tv_centent_1.setEllipsize(TextUtils.TruncateAt.END);
            //setValue(item.getNoiDung(),viewHolder.id_tv_centent_1,item,item);
            viewHolder.id_tv_date_1.setText(item.getNgayTao());
            viewHolder.id_tv_status_1.setText(item.isDaXuLy()?context.getString(R.string.bcsc_daxuly):context.getString(R.string.bcsc_dangxuly));
//            ImageLoadert.getInstance(context).load(viewholder.id_img_sc,item.getImages().get(0).getBase64(),false);
            viewHolder.id_tv_title_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.showMap(item);
                }
            });
            viewHolder.id_glr_imgs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    activity.showGalleryImgs(item);
                }
            });
            viewHolder.id_tv_centent_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.showPhanHoiKh(item,position);
                }
            });
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
