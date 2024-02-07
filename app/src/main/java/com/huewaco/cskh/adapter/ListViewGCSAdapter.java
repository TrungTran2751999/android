package com.huewaco.cskh.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.objects.GcsObject;

import java.util.ArrayList;


public class ListViewGCSAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<GcsObject> mArrGCS;

    public ListViewGCSAdapter(Context context, ArrayList<GcsObject> mArr) {
        this.context = context;
        this.mArrGCS = mArr;
    }

    @Override
    public int getCount() {
        return mArrGCS.size();
    }

    @Override
    public Object getItem(int i) {
        return mArrGCS.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        private View id_ly_t1;
        private TextView id_tv_text1;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        layoutInflater = LayoutInflater.from(context);
        ViewHolder viewholder = null;
        GcsObject item = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_listview_gcs,
                    null);
            viewholder = new ViewHolder();
            viewholder.id_ly_t1 = (View) convertView
                    .findViewById(R.id.id_ly_t1);
            viewholder.id_tv_text1 = (TextView) convertView
                    .findViewById(R.id.id_tv_text1);
            convertView.setTag(viewholder);
        }
        viewholder = (ViewHolder) convertView.getTag();
        if(mArrGCS != null && mArrGCS.size()>0){
            item = mArrGCS.get(i);
            if(item != null) {
                viewholder.id_tv_text1.setText(Html.fromHtml(item.getText()));
//                viewholder.id_tv_text1.setText(item.getText());
            }
        }

        return convertView;
    }

    public void refresh(ArrayList<GcsObject> mArrGCS) {
        this.mArrGCS = mArrGCS;
        notifyDataSetChanged();
    }
}
