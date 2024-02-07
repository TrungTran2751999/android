package com.huewaco.cskh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.objects.CSTieuThuListItemObj;

import java.util.ArrayList;


public class ListViewCSTieuThuAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<CSTieuThuListItemObj> mArrCSTieuThu;

    public ListViewCSTieuThuAdapter(Context context, ArrayList<CSTieuThuListItemObj> mArr) {
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
        private TextView id_tv_thang_nam_title;
        private TextView id_tv_lichghinuoc_chisodau_detail;
        private TextView id_tv_lichghinuoc_chisocuoi_detail;
        private TextView id_tv_lichghinuoc_tinhtien_detail;
        private TextView id_tv_lichghinuoc_thangnamtruoc_detail;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        layoutInflater = LayoutInflater.from(context);
        ViewHolder viewholder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_listview_cs_tieu_thu_list,
                    null);
            viewholder = new ViewHolder();

            viewholder.id_tv_thang_nam_title = (TextView) convertView
                    .findViewById(R.id.id_tv_thang_nam_title);
            viewholder.id_tv_lichghinuoc_chisodau_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_lichghinuoc_chisodau_detail);
            viewholder.id_tv_lichghinuoc_chisocuoi_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_lichghinuoc_chisocuoi_detail);
            viewholder.id_tv_lichghinuoc_tinhtien_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_lichghinuoc_tinhtien_detail);
            viewholder.id_tv_lichghinuoc_thangnamtruoc_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_lichghinuoc_thangnamtruoc_detail);
            convertView.setTag(viewholder);
        }
        viewholder = (ViewHolder) convertView.getTag();
        if(mArrCSTieuThu != null && mArrCSTieuThu.size()>0){
            CSTieuThuListItemObj item = mArrCSTieuThu.get(i);
            viewholder.id_tv_thang_nam_title.setText("Tháng "+item.getThang() +" Năm "+item.getNam());
            viewholder.id_tv_lichghinuoc_chisodau_detail.setText(" "+item.getChiSoDau());
            viewholder.id_tv_lichghinuoc_chisocuoi_detail.setText(" "+item.getChiSoCuoi());
            viewholder.id_tv_lichghinuoc_tinhtien_detail.setText(" "+item.getM3TinhTien());
            viewholder.id_tv_lichghinuoc_thangnamtruoc_detail.setText(" "+item.getM3TieuThuCungThangNamTruoc());
        }

        return convertView;
    }
    public void refresh(ArrayList<CSTieuThuListItemObj> mArrCSTieuT){
        mArrCSTieuThu = mArrCSTieuT;
        notifyDataSetChanged();
    }
}
