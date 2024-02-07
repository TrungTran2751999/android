package com.huewaco.cskh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatCheckBox;

import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.objects.HDonTNuocListItemObj;
import com.huewaco.cskh.objects.KhachHangObj;

import java.util.ArrayList;


public class ListViewHDonTNuocAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<HDonTNuocListItemObj> mArrHDonTNuocList;

    public ListViewHDonTNuocAdapter(Context context, ArrayList<HDonTNuocListItemObj> mArr) {
        this.context = context;
        this.mArrHDonTNuocList = mArr;
    }

    @Override
    public int getCount() {
        return mArrHDonTNuocList.size();
    }

    @Override
    public Object getItem(int i) {
        return mArrHDonTNuocList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    public void refresh(ArrayList<HDonTNuocListItemObj> mArrHDonTNuocList) {
        this.mArrHDonTNuocList = mArrHDonTNuocList;
        notifyDataSetChanged();
    }

    private class ViewHolder {
        private TextView id_tv_idkh_detail;
        private TextView id_tv_mb_detail;
        private TextView id_tv_nam_detail;
        private TextView id_tv_thang_detail;
        private TextView id_tv_chisodau_detail;
        private TextView id_tv_chisocuoi_detail;
        private TextView id_tv_kluongtthuttien_detail;
        private TextView id_tv_m3sh1_detail;
        private TextView id_tv_m3sh2_detail;
        private TextView id_tv_m3sh3_detail;
        private TextView id_tv_m3sh4_detail;
        private TextView id_tv_m3sn_detail;
        private TextView id_tv_m3kd_detail;
        private TextView id_tv_m3nd_detail;

        private TextView id_tv_m3sh1cu_detail;
        private TextView id_tv_m3sh2cu_detail;
        private TextView id_tv_m3sh3cu_detail;
        private TextView id_tv_m3sh4cu_detail;
        private TextView id_tv_m3sncu_detail;
        private TextView id_tv_m3kdcu_detail;
        private TextView id_tv_m3ndcu_detail;
        private TextView id_tv_tiennuoc_detail;
        private TextView id_tv_tienphitn_detail;
        private TextView id_tv_tienphimt_detail;
        private TextView id_tv_tienthue_detail;
        private TextView id_tv_tongtien_detail;
        private CheckBox id_cb_datra_detail;
        //
        private TextView id_tv_m3sh1_title;
        private TextView id_tv_m3sh2_title;
        private TextView id_tv_m3sh3_title;
        private TextView id_tv_m3sh4_title;
        private TextView id_tv_m3sn_title;
        private TextView id_tv_m3kd_title;
        private TextView id_tv_m3nd_title;

        private TextView id_tv_m3sh1cu_title;
        private TextView id_tv_m3sh2cu_title;
        private TextView id_tv_m3sh3cu_title;
        private TextView id_tv_m3sh4cu_title;
        private TextView id_tv_m3sncu_title;
        private TextView id_tv_m3kdcu_title;
        private TextView id_tv_m3ndcu_title;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        layoutInflater = LayoutInflater.from(context);
        ViewHolder viewholder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_listview_hoa_don_tien_nuoc_list,
                    null);
            viewholder = new ViewHolder();
            viewholder.id_tv_idkh_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_idkh_detail);
            viewholder.id_tv_mb_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_mb_detail);
            viewholder.id_tv_nam_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_nam_detail);
            viewholder.id_tv_thang_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_thang_detail);
            viewholder.id_tv_chisodau_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_chisodau_detail);
            viewholder.id_tv_chisocuoi_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_chisocuoi_detail);
            viewholder.id_tv_kluongtthuttien_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_kluongtthuttien_detail);
            viewholder.id_tv_m3sh1_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_m3sh1_detail);
            viewholder.id_tv_m3sh2_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_m3sh2_detail);
            viewholder.id_tv_m3sh3_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_m3sh3_detail);
            viewholder.id_tv_m3sh4_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_m3sh4_detail);
            viewholder.id_tv_m3sn_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_m3sn_detail);
            viewholder.id_tv_m3kd_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_m3kd_detail);
            viewholder.id_tv_m3nd_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_m3nd_detail);
            viewholder.id_tv_m3sh1cu_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_m3sh1cu_detail);
            viewholder.id_tv_m3sh2cu_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_m3sh2cu_detail);
            viewholder.id_tv_m3sh3cu_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_m3sh3cu_detail);
            viewholder.id_tv_m3sh4cu_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_m3sh4cu_detail);
            viewholder.id_tv_m3sncu_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_m3sncu_detail);
            viewholder.id_tv_m3kdcu_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_m3kdcu_detail);
            viewholder.id_tv_m3ndcu_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_m3ndcu_detail);
            viewholder.id_tv_tiennuoc_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_tiennuoc_detail);
            viewholder.id_tv_tienphitn_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_tienphitn_detail);
            viewholder.id_tv_tienphimt_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_tienphimt_detail);
            viewholder.id_tv_tienthue_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_tienthue_detail);
            viewholder.id_tv_tongtien_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_tongtien_detail);
            viewholder.id_cb_datra_detail = (CheckBox) convertView
                    .findViewById(R.id.id_cb_datra_detail);
            //
            viewholder.id_tv_m3sh1_title = (TextView) convertView
                    .findViewById(R.id.id_tv_m3sh1_title);
            viewholder.id_tv_m3sh2_title = (TextView) convertView
                    .findViewById(R.id.id_tv_m3sh2_title);
            viewholder.id_tv_m3sh3_title = (TextView) convertView
                    .findViewById(R.id.id_tv_m3sh3_title);
            viewholder.id_tv_m3sh4_title = (TextView) convertView
                    .findViewById(R.id.id_tv_m3sh4_title);
            viewholder.id_tv_m3sn_title = (TextView) convertView
                    .findViewById(R.id.id_tv_m3sn_title);
            viewholder.id_tv_m3kd_title = (TextView) convertView
                    .findViewById(R.id.id_tv_m3kd_title);
            viewholder.id_tv_m3nd_title = (TextView) convertView
                    .findViewById(R.id.id_tv_m3nd_title);
            viewholder.id_tv_m3sh1cu_title = (TextView) convertView
                    .findViewById(R.id.id_tv_m3sh1cu_title);
            viewholder.id_tv_m3sh2cu_title = (TextView) convertView
                    .findViewById(R.id.id_tv_m3sh2cu_title);
            viewholder.id_tv_m3sh3cu_title = (TextView) convertView
                    .findViewById(R.id.id_tv_m3sh3cu_title);
            viewholder.id_tv_m3sh4cu_title = (TextView) convertView
                    .findViewById(R.id.id_tv_m3sh4cu_title);
            viewholder.id_tv_m3sncu_title = (TextView) convertView
                    .findViewById(R.id.id_tv_m3sncu_title);
            viewholder.id_tv_m3kdcu_title = (TextView) convertView
                    .findViewById(R.id.id_tv_m3kdcu_title);
            viewholder.id_tv_m3ndcu_title = (TextView) convertView
                    .findViewById(R.id.id_tv_m3ndcu_title);
            convertView.setTag(viewholder);
        }
        viewholder = (ViewHolder) convertView.getTag();
        HDonTNuocListItemObj item = mArrHDonTNuocList.get(i);
        viewholder.id_tv_idkh_detail.setText(item.getIDKH());
        viewholder.id_tv_mb_detail.setText(item.getMaDb());
        viewholder.id_tv_nam_detail.setText(item.getNam());
        viewholder.id_tv_thang_detail.setText(item.getThang());
        viewholder.id_tv_chisodau_detail.setText(item.getChiSoDau());
        viewholder.id_tv_chisocuoi_detail.setText(item.getChiSoCuoi());
        viewholder.id_tv_kluongtthuttien_detail.setText(item.getKhoiLuongTieuThuTinhhTien());
        //
        if(Float.parseFloat(item.getM3Sh1())== 0) {
            viewholder.id_tv_m3sh1_detail.setVisibility(View.GONE);
            viewholder.id_tv_m3sh1_title.setVisibility(View.GONE);
        } else {
            viewholder.id_tv_m3sh1_detail.setText(item.getM3Sh1());
            viewholder.id_tv_m3sh1_detail.setVisibility(View.VISIBLE);
            viewholder.id_tv_m3sh1_title.setVisibility(View.VISIBLE);
        }
        if(Float.parseFloat(item.getM3Sh2())== 0) {
            viewholder.id_tv_m3sh2_detail.setVisibility(View.GONE);
            viewholder.id_tv_m3sh2_title.setVisibility(View.GONE);
        } else {
            viewholder.id_tv_m3sh2_detail.setText(item.getM3Sh2());
            viewholder.id_tv_m3sh2_detail.setVisibility(View.VISIBLE);
            viewholder.id_tv_m3sh2_title.setVisibility(View.VISIBLE);
        }
        if(Float.parseFloat(item.getM3Sh3())== 0) {
            viewholder.id_tv_m3sh3_detail.setVisibility(View.GONE);
            viewholder.id_tv_m3sh3_title.setVisibility(View.GONE);
        } else {
            viewholder.id_tv_m3sh3_detail.setText(item.getM3Sh3());
            viewholder.id_tv_m3sh3_detail.setVisibility(View.VISIBLE);
            viewholder.id_tv_m3sh3_title.setVisibility(View.VISIBLE);
        }
        if(Float.parseFloat(item.getM3Sh4())== 0) {
            viewholder.id_tv_m3sh4_detail.setVisibility(View.GONE);
            viewholder.id_tv_m3sh4_title.setVisibility(View.GONE);
        } else {
            viewholder.id_tv_m3sh4_detail.setText(item.getM3Sh4());
            viewholder.id_tv_m3sh4_detail.setVisibility(View.VISIBLE);
            viewholder.id_tv_m3sh4_title.setVisibility(View.VISIBLE);
        }
        if(Float.parseFloat(item.getM3Sn())== 0) {
            viewholder.id_tv_m3sn_detail.setVisibility(View.GONE);
            viewholder.id_tv_m3sn_title.setVisibility(View.GONE);
        } else {
            viewholder.id_tv_m3sn_detail.setText(item.getM3Sn());
            viewholder.id_tv_m3sn_detail.setVisibility(View.VISIBLE);
            viewholder.id_tv_m3sn_title.setVisibility(View.VISIBLE);
        }
        if(Float.parseFloat(item.getM3Kd())== 0) {
            viewholder.id_tv_m3kd_detail.setVisibility(View.GONE);
            viewholder.id_tv_m3kd_title.setVisibility(View.GONE);
        } else {
            viewholder.id_tv_m3kd_detail.setText(item.getM3Kd());
            viewholder.id_tv_m3kd_detail.setVisibility(View.VISIBLE);
            viewholder.id_tv_m3kd_title.setVisibility(View.VISIBLE);
        }
        if(Float.parseFloat(item.getM3Nd())== 0) {
            viewholder.id_tv_m3nd_detail.setVisibility(View.GONE);
            viewholder.id_tv_m3nd_title.setVisibility(View.GONE);
        } else {
            viewholder.id_tv_m3nd_detail.setText(item.getM3Nd());
            viewholder.id_tv_m3nd_detail.setVisibility(View.VISIBLE);
            viewholder.id_tv_m3nd_title.setVisibility(View.VISIBLE);
        }

        //
        if(Float.parseFloat(item.getM3Sh1Cu())== 0) {
            viewholder.id_tv_m3sh1cu_detail.setVisibility(View.GONE);
            viewholder.id_tv_m3sh1cu_title.setVisibility(View.GONE);
        } else {
            viewholder.id_tv_m3sh1cu_detail.setText(item.getM3Sh1Cu());
            viewholder.id_tv_m3sh1cu_detail.setVisibility(View.VISIBLE);
            viewholder.id_tv_m3sh1cu_title.setVisibility(View.VISIBLE);
        }
        if(Float.parseFloat(item.getM3Sh2Cu())== 0) {
            viewholder.id_tv_m3sh2cu_detail.setVisibility(View.GONE);
            viewholder.id_tv_m3sh2cu_title.setVisibility(View.GONE);
        } else {
            viewholder.id_tv_m3sh2cu_detail.setText(item.getM3Sh2Cu());
            viewholder.id_tv_m3sh2cu_detail.setVisibility(View.VISIBLE);
            viewholder.id_tv_m3sh2cu_title.setVisibility(View.VISIBLE);
        }
        if(Float.parseFloat(item.getM3Sh3Cu())== 0) {
            viewholder.id_tv_m3sh3cu_detail.setVisibility(View.GONE);
            viewholder.id_tv_m3sh3cu_title.setVisibility(View.GONE);
        } else {
            viewholder.id_tv_m3sh3cu_detail.setText(item.getM3Sh3Cu());
            viewholder.id_tv_m3sh3cu_detail.setVisibility(View.VISIBLE);
            viewholder.id_tv_m3sh3cu_title.setVisibility(View.VISIBLE);
        }
        if(Float.parseFloat(item.getM3Sh4Cu())== 0) {
            viewholder.id_tv_m3sh4cu_detail.setVisibility(View.GONE);
            viewholder.id_tv_m3sh4cu_title.setVisibility(View.GONE);
        } else {
            viewholder.id_tv_m3sh4cu_detail.setText(item.getM3Sh4Cu());
            viewholder.id_tv_m3sh4cu_detail.setVisibility(View.VISIBLE);
            viewholder.id_tv_m3sh4cu_title.setVisibility(View.VISIBLE);
        }
        if(Float.parseFloat(item.getM3SnCu())== 0) {
            viewholder.id_tv_m3sncu_detail.setVisibility(View.GONE);
            viewholder.id_tv_m3sncu_title.setVisibility(View.GONE);
        } else {
            viewholder.id_tv_m3sncu_detail.setText(item.getM3SnCu());
            viewholder.id_tv_m3sncu_detail.setVisibility(View.VISIBLE);
            viewholder.id_tv_m3sncu_title.setVisibility(View.VISIBLE);
        }
        if(Float.parseFloat(item.getM3KdCu())== 0) {
            viewholder.id_tv_m3kdcu_detail.setVisibility(View.GONE);
            viewholder.id_tv_m3kdcu_title.setVisibility(View.GONE);
        } else {
            viewholder.id_tv_m3kdcu_detail.setText(item.getM3KdCu());
            viewholder.id_tv_m3kdcu_detail.setVisibility(View.VISIBLE);
            viewholder.id_tv_m3kdcu_title.setVisibility(View.VISIBLE);
        }
        if(Float.parseFloat(item.getM3NdCu())== 0) {
            viewholder.id_tv_m3ndcu_detail.setVisibility(View.GONE);
            viewholder.id_tv_m3ndcu_title.setVisibility(View.GONE);
        } else {
            viewholder.id_tv_m3ndcu_detail.setText(item.getM3NdCu());
            viewholder.id_tv_m3ndcu_detail.setVisibility(View.VISIBLE);
            viewholder.id_tv_m3ndcu_title.setVisibility(View.VISIBLE);
        }
        viewholder.id_tv_tiennuoc_detail.setText(item.getTienNuoc());
        viewholder.id_tv_tienphitn_detail.setText(item.getTienPhiTn());
        viewholder.id_tv_tienphimt_detail.setText(item.getTienPhiMt());
        viewholder.id_tv_tienthue_detail.setText(item.getTienThue());
        viewholder.id_tv_tongtien_detail.setText(item.getTongTien());
        if(item.getDaTra().equalsIgnoreCase("true")) {
            viewholder.id_cb_datra_detail.setChecked(true);
        } else if( item.getDaTra().equalsIgnoreCase("false")) {
            viewholder.id_cb_datra_detail.setChecked(false);

        } else {
            viewholder.id_cb_datra_detail.setChecked(false);
        }
        return convertView;
    }
}
