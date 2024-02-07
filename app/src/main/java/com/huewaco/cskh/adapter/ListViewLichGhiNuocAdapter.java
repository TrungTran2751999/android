package com.huewaco.cskh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.objects.LichGhiNuocListItemObj;

import java.util.ArrayList;


public class ListViewLichGhiNuocAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<LichGhiNuocListItemObj> mArrLichGhiNuocList;

    public ListViewLichGhiNuocAdapter(Context context, ArrayList<LichGhiNuocListItemObj> mArr) {
        this.context = context;
        this.mArrLichGhiNuocList = mArr;
    }

    @Override
    public int getCount() {
        return mArrLichGhiNuocList.size();
    }

    @Override
    public Object getItem(int i) {
        return mArrLichGhiNuocList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        private TextView id_tv_ngay_ghi_detail;
        private TextView id_tv_thang_detail;
        private TextView id_tv_nam_detail;
        private TextView id_tv_ky_detail;
        private TextView id_tv_idkh_detail;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        layoutInflater = LayoutInflater.from(context);
        ViewHolder viewholder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_listview_lich_ghi_nuoc_list,
                    null);
            viewholder = new ViewHolder();
            viewholder.id_tv_idkh_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_idkh_detail);
            viewholder.id_tv_ngay_ghi_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_ngay_ghi_detail);
            viewholder.id_tv_thang_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_thang_detail);
            viewholder.id_tv_nam_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_nam_detail);
            viewholder.id_tv_ky_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_ky_detail);
            convertView.setTag(viewholder);
        }
        viewholder = (ViewHolder) convertView.getTag();
        LichGhiNuocListItemObj item = mArrLichGhiNuocList.get(i);
        viewholder.id_tv_idkh_detail.setText(item.getIDKH());
        viewholder.id_tv_ngay_ghi_detail.setText(item.getNgayghinuoc());
        viewholder.id_tv_thang_detail.setText(item.getThang());
        viewholder.id_tv_nam_detail.setText(item.getNam());
        viewholder.id_tv_ky_detail.setText("Tháng "+item.getThang()+" năm "+item.getNam());
        return convertView;
    }
    public void refresh(ArrayList<LichGhiNuocListItemObj> marr){
        mArrLichGhiNuocList = marr;
        notifyDataSetChanged();
    }
}
