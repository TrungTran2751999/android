package com.huewaco.cskh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.objects.KhachHangObj;

import java.util.ArrayList;


public class ListViewThongTinChungAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<KhachHangObj> mArrKHangList;

    public ListViewThongTinChungAdapter(Context context, ArrayList<KhachHangObj> mArr) {
        this.context = context;
        this.mArrKHangList = mArr;
    }

    @Override
    public int getCount() {
        return mArrKHangList.size();
    }

    @Override
    public Object getItem(int i) {
        return mArrKHangList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    public void refresh(ArrayList<KhachHangObj> mArrKhachHang) {
        this.mArrKHangList = mArrKHangList;
        notifyDataSetChanged();
    }

    private class ViewHolder {
        private TextView id_tv_ten_khang_detail;
        private TextView id_tv_ma_khang_detail;
        private TextView id_tv_dia_chi_detail;
        private TextView id_tv_dien_thoai_detail;
        private TextView id_tv_email_detail;

    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        layoutInflater = LayoutInflater.from(context);
        ViewHolder viewholder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_listview_thong_tin_chung_list,
                    null);
            viewholder = new ViewHolder();
            viewholder.id_tv_ten_khang_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_ten_khang_detail);
            viewholder.id_tv_ma_khang_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_ma_khang_detail);
            viewholder.id_tv_dia_chi_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_dia_chi_detail);
            viewholder.id_tv_dien_thoai_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_dien_thoai_detail);
            viewholder.id_tv_email_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_email_detail);
            convertView.setTag(viewholder);
        }
        viewholder = (ViewHolder) convertView.getTag();
        KhachHangObj item = mArrKHangList.get(i);
        viewholder.id_tv_ten_khang_detail.setText(item.getTenKhachHang());
        viewholder.id_tv_ma_khang_detail.setText(item.getMa_khang());
        viewholder.id_tv_dia_chi_detail.setText(item.getSoNha() +" "+ item.getDuongPho()+", "+ item.getPhuongXa()+", "+item.getQuanHuyen());
        viewholder.id_tv_dien_thoai_detail.setText(item.getDiDong());
        viewholder.id_tv_email_detail.setText(item.getEmail());
        return convertView;
    }
}
