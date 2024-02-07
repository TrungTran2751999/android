package com.huewaco.cskh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.objects.KhuVucListItemObj;
import com.huewaco.cskh.objects.QuanHuyenListItemObj;

import java.util.ArrayList;

/**
 * Created by Kiet on 01/12/2016.
 */
public class ListViewQuanHuyenListItemAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<QuanHuyenListItemObj> mArrQuanHuyenList;

    public ListViewQuanHuyenListItemAdapter(Context context, ArrayList<QuanHuyenListItemObj> mArrTasks) {
        this.context = context;
        this.mArrQuanHuyenList = mArrTasks;
    }

    @Override
    public int getCount() {
        return mArrQuanHuyenList.size();
    }

    @Override
    public Object getItem(int i) {
        return mArrQuanHuyenList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        private TextView id_tv_ma;
        private TextView id_tv_diachinh;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        layoutInflater = LayoutInflater.from(context);
        ViewHolder viewholder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_listview_diachinh_list,
                    null);
            viewholder = new ViewHolder();
            viewholder.id_tv_ma = (TextView) convertView
                    .findViewById(R.id.id_tv_ma);
            viewholder.id_tv_diachinh = (TextView) convertView
                    .findViewById(R.id.id_tv_diachinh);
            convertView.setTag(viewholder);
        }
        viewholder = (ViewHolder) convertView.getTag();
        QuanHuyenListItemObj item = mArrQuanHuyenList.get(i);
        viewholder.id_tv_ma.setText(item.getMaQuan());
        viewholder.id_tv_diachinh.setText(item.getTenQuan());
        return convertView;
    }

    public void refresh(ArrayList<QuanHuyenListItemObj> mArrQuanHuyenList) {
        this.mArrQuanHuyenList = mArrQuanHuyenList;
        notifyDataSetChanged();
    }
}
