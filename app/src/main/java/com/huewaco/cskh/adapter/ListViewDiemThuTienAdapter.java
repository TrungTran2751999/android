package com.huewaco.cskh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.objects.DiemThuTienListItemObj;

import java.util.ArrayList;


public class ListViewDiemThuTienAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<DiemThuTienListItemObj> mArrDiemThuTien;

    public ListViewDiemThuTienAdapter(Context context, ArrayList<DiemThuTienListItemObj> mArr) {
        this.context = context;
        this.mArrDiemThuTien = mArr;
    }

    @Override
    public int getCount() {
        return mArrDiemThuTien.size();
    }

    @Override
    public Object getItem(int i) {
        return mArrDiemThuTien.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        private TextView id_tv_madiemthu_detail;
        private TextView id_tv_tenquay_detail;
        private TextView id_tv_dia_chi_detail;
        private TextView id_tv_tgian_lviec_detail;
        private TextView id_tv_makhuvuc_detail;
        private TextView id_tv_kinhdo_detail;
        private TextView id_tv_vido_detail;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        layoutInflater = LayoutInflater.from(context);
        ViewHolder viewholder = null;
        DiemThuTienListItemObj item = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_listview_diem_thu_tien_list,
                    null);
            viewholder = new ViewHolder();
            viewholder.id_tv_madiemthu_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_madiemthu_detail);
            viewholder.id_tv_tenquay_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_tenquay_detail);
            viewholder.id_tv_dia_chi_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_dia_chi_detail);
            viewholder.id_tv_tgian_lviec_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_tgian_lviec_detail);
            viewholder.id_tv_makhuvuc_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_makhuvuc_detail);
            viewholder.id_tv_kinhdo_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_kinhdo_detail);
            viewholder.id_tv_vido_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_vido_detail);
            convertView.setTag(viewholder);
        }
        viewholder = (ViewHolder) convertView.getTag();
        if(mArrDiemThuTien != null && mArrDiemThuTien.size()>0){
            item = mArrDiemThuTien.get(i);
            viewholder.id_tv_madiemthu_detail.setText(item.getID());
            viewholder.id_tv_tenquay_detail.setText(item.getTenQuayThu());
            viewholder.id_tv_dia_chi_detail.setText(item.getDiaChi());
            viewholder.id_tv_tgian_lviec_detail.setText(item.getGhiChu());
            viewholder.id_tv_makhuvuc_detail.setText(item.getMaKV());
            viewholder.id_tv_kinhdo_detail.setText(item.getKinhDo());
            viewholder.id_tv_vido_detail.setText(item.getViDo());
        }

        return convertView;
    }

    public void refresh(ArrayList<DiemThuTienListItemObj> mArrDiemThuTien) {
        this.mArrDiemThuTien = mArrDiemThuTien;
        notifyDataSetChanged();
    }
}
