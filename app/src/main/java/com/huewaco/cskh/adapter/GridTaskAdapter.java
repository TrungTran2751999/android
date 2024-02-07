package com.huewaco.cskh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.objects.TaskObj;

import java.util.ArrayList;

/**
 * Created by Kiet on 01/12/2016.
 */
public class GridTaskAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<TaskObj> mArrTasks;

    public GridTaskAdapter(Context context, ArrayList<TaskObj> mArrTasks) {
        this.context = context;
        this.mArrTasks = mArrTasks;
    }

    @Override
    public int getCount() {
        return mArrTasks.size();
    }

    @Override
    public Object getItem(int i) {
        return mArrTasks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        private ImageView id_img_icon;
        private ViewGroup id_ly_notif;
        private TextView id_tv_number;
        private TextView id_tv_task_name;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        layoutInflater = LayoutInflater.from(context);
        ViewHolder viewholder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_task_grid,
                    null);
            viewholder = new ViewHolder();
            viewholder.id_img_icon = (ImageView) convertView
                    .findViewById(R.id.id_img_icon);
            viewholder.id_ly_notif = (ViewGroup) convertView
                    .findViewById(R.id.id_ly_notif);
            viewholder.id_tv_number = (TextView) convertView
                    .findViewById(R.id.id_tv_number);
            viewholder.id_tv_task_name = (TextView) convertView
                    .findViewById(R.id.id_tv_task_name);
            convertView.setTag(viewholder);
        }
        viewholder = (ViewHolder) convertView.getTag();
        TaskObj item = mArrTasks.get(i);
        viewholder.id_img_icon.setImageDrawable(item.getDrawable());
        viewholder.id_tv_task_name.setText(item.getNameTask());
        if(item.getNumberNotification() >0){
            viewholder.id_ly_notif.setVisibility(View.VISIBLE);
            viewholder.id_tv_number.setText(""+item.getNumberNotification());
        }else{
            viewholder.id_ly_notif.setVisibility(View.GONE);
        }
        return convertView;
    }
    public void refresh(ArrayList<TaskObj> mArrTask){
        mArrTasks = mArrTask;
        notifyDataSetChanged();
    }
}
