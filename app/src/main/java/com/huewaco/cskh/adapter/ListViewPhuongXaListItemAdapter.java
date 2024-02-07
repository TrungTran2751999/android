package com.huewaco.cskh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.objects.PhuongXaListItmObj;
import com.huewaco.cskh.objects.PhuongXaListItmObj;

import java.util.ArrayList;

/**
 * Created by Kiet on 01/12/2016.
 */
public class ListViewPhuongXaListItemAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<PhuongXaListItmObj> mArrPhuongXaList;

    public ListViewPhuongXaListItemAdapter(Context context, ArrayList<PhuongXaListItmObj> mArrTasks) {
        this.context = context;
        this.mArrPhuongXaList = mArrTasks;
    }

    @Override
    public int getCount() {
        return mArrPhuongXaList.size();
    }

    @Override
    public Object getItem(int i) {
        return mArrPhuongXaList.get(i);
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
        PhuongXaListItmObj item = mArrPhuongXaList.get(i);
        viewholder.id_tv_ma.setText(item.getMaPhuong());
        viewholder.id_tv_diachinh.setText(item.getTenPhuong());
        return convertView;
    }

    public void refresh(ArrayList<PhuongXaListItmObj> mArrPhuongXaList) {
        this.mArrPhuongXaList = mArrPhuongXaList;
        notifyDataSetChanged();
    }
}
