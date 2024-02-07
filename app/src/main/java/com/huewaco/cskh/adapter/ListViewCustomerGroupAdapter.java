package com.huewaco.cskh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.objects.KhachHangObj;
import com.huewaco.cskh.objects.TToanTienNuocListItemObj;

import java.util.ArrayList;


public class ListViewCustomerGroupAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<KhachHangObj> mArrKhachHang;
    private ArrayList<String> mArrIdkhList;

    public ListViewCustomerGroupAdapter(Context context, ArrayList<KhachHangObj> mArr) {
        this.context = context;
        this.mArrKhachHang = mArr;
        if (mArr != null) {
            mArrIdkhList = new ArrayList<>();
            for (KhachHangObj object : mArrKhachHang) {
                mArrIdkhList.add(object.getIdKh());
            }
        }
    }
    public ArrayList<String>  getIdkhList(){return mArrIdkhList;}
    @Override
    public int getCount() {
        return mArrKhachHang.size();
    }

    @Override
    public Object getItem(int i) {
        return mArrKhachHang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        private CheckBox id_cb_tthai_detail;
        private TextView id_idkh_detail;
        private TextView id_tv_dcld_detail;
        private TextView id_tv_tenkh_detail;

    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        layoutInflater = LayoutInflater.from(context);
        ViewHolder viewholder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_listview_customergroup_list,
                    null);
            viewholder = new ViewHolder();
            viewholder.id_cb_tthai_detail = (CheckBox) convertView
                    .findViewById(R.id.id_cb_tthai_detail);
            viewholder.id_idkh_detail = (TextView) convertView
                    .findViewById(R.id.id_idkh_detail);
            viewholder.id_tv_dcld_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_dcld_detail);
            viewholder.id_tv_tenkh_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_tenkh_detail);
            viewholder.id_cb_tthai_detail.setText("");
            convertView.setTag(viewholder);
        }
        viewholder = (ViewHolder) convertView.getTag();
        if(mArrKhachHang != null && mArrKhachHang.size()>0){
            KhachHangObj item = mArrKhachHang.get(i);
            viewholder.id_idkh_detail.setText(item.getIdKh());
            viewholder.id_tv_dcld_detail.setText(item.getDiaChiLD());
            viewholder.id_tv_tenkh_detail.setText(item.getTenKhachHang());
            if(item.getIdKh().equalsIgnoreCase(GlobalVariable.KHACH_HANG_CURRENT.getIdKh())) {
                viewholder.id_cb_tthai_detail.setChecked(true);
            }  else {
                viewholder.id_cb_tthai_detail.setChecked(false);
            }
        }

        return convertView;
    }
    public void refresh(ArrayList<KhachHangObj> mArr){
        mArrKhachHang = mArr;
        notifyDataSetChanged();
    }

}
