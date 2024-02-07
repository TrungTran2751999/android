package com.huewaco.cskh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.gms.common.util.JsonUtils;
import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.objects.KhachHangObj;
import com.huewaco.cskh.objects.QuanHuyenListItemObj;

import org.json.JSONArray;
import org.json.JSONStringer;

import java.util.ArrayList;

/**
 * Created by Kiet on 01/12/2016.
 */
public class ListViewCustomerGroupListItemAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<KhachHangObj> mArrCustomerGroupList = new ArrayList<KhachHangObj>();
    public ArrayList<String> getIdkhAll() {
        return idkhAll;
    }
    public Boolean isHasAll;
    private ArrayList<String> idkhAll;
    public ListViewCustomerGroupListItemAdapter(Context context,Boolean isHasAll) {
        this.context = context;
        this.isHasAll = isHasAll;
        if(GlobalVariable.mArrKHang.size() == 0){
            mArrCustomerGroupList.add(GlobalVariable.KHACH_HANG_CURRENT);
        }else{
            if(isHasAll){
                KhachHangObj khachhangAll = new KhachHangObj();
                khachhangAll.setDiaChiLD("Tất cả đồng hồ");

                mArrCustomerGroupList.add(khachhangAll);
                ArrayList<String> arr = new ArrayList<>();
                for (KhachHangObj item: GlobalVariable.mArrKHang
                ) {
                    arr.add(item.getIdKh());
                }

                idkhAll = arr;
            }
            mArrCustomerGroupList.addAll(GlobalVariable.mArrKHang) ;
        }
        GlobalVariable.KHACH_HANG_CURRENT = GlobalVariable.KHACH_HANG;
    }

    public int getIndexByIdkh(String idkh){
        for (int i = 0; i < mArrCustomerGroupList.size(); i++) {
            if (mArrCustomerGroupList.get(i).getIdKh().equals(idkh)) {
                return i;
            }
        }
        return -1;
    }
    @Override
    public int getCount() {
        return mArrCustomerGroupList.size();
    }

    @Override
    public Object getItem(int i) {
        return mArrCustomerGroupList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        private TextView id_tv_idkh;
        private TextView id_tv_diachiLD;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        layoutInflater = LayoutInflater.from(context);
        ViewHolder viewholder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_listview_customergroup_cmb,
                    null);
            viewholder = new ViewHolder();
            viewholder.id_tv_idkh = (TextView) convertView
                    .findViewById(R.id.id_tv_idkh);
            viewholder.id_tv_diachiLD = (TextView) convertView
                    .findViewById(R.id.id_tv_diachild);
            convertView.setTag(viewholder);
        }
        viewholder = (ViewHolder) convertView.getTag();
        KhachHangObj item = mArrCustomerGroupList.get(i);
        viewholder.id_tv_idkh.setText(item.getIdKh());
        viewholder.id_tv_diachiLD.setText(item.getDiaChiLD());
        return convertView;
    }

    public void refresh() {

        if(GlobalVariable.mArrKHang.size() == 0){
        }else{
            mArrCustomerGroupList = new ArrayList<>();
            if(isHasAll){
                KhachHangObj khachhangAll = new KhachHangObj();
                khachhangAll.setDiaChiLD("Tất cả đồng hồ");

                mArrCustomerGroupList.add(khachhangAll);
                ArrayList<String> arr = new ArrayList<>();
                for (KhachHangObj item: GlobalVariable.mArrKHang
                ) {
                    arr.add(item.getIdKh());
                }
                JSONArray json = new JSONArray(arr);
                idkhAll = arr;
            }
            mArrCustomerGroupList.addAll(GlobalVariable.mArrKHang) ;
        }
        notifyDataSetChanged();
    }
    public void refreshTT(String sumTotal) {

        if(GlobalVariable.mArrKHang.size() == 0){
        }else{
            mArrCustomerGroupList = new ArrayList<>();
            if(isHasAll){
                KhachHangObj khachhangAll = new KhachHangObj();
                khachhangAll.setDiaChiLD("Tất cả đồng hồ" + "("+ sumTotal + ")");
                mArrCustomerGroupList.add(khachhangAll);
                ArrayList<String> arr = new ArrayList<>();
                for (KhachHangObj item: GlobalVariable.mArrKHang
                ) {
                    arr.add(item.getIdKh());
                }
                JSONArray json = new JSONArray(arr);
                idkhAll = arr;
            }
            mArrCustomerGroupList.addAll(GlobalVariable.mArrKHang) ;
        }
        notifyDataSetChanged();
    }
}
