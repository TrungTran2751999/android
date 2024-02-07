package com.huewaco.cskh.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.objects.DvCauHoisListItemObj;

import java.util.ArrayList;


public class ListViewCauHoiTraLoiAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<DvCauHoisListItemObj> mArrCauHois;

    public ListViewCauHoiTraLoiAdapter(Context context, ArrayList<DvCauHoisListItemObj> mArr) {
        this.context = context;
        this.mArrCauHois = mArr;
    }

    @Override
    public int getCount() {
        return mArrCauHois.size();
    }

    @Override
    public Object getItem(int i) {
        return mArrCauHois.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        private TextView id_tv_cauhoi_tieude;
        private TextView id_tv_cauhoi_noidung;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        layoutInflater = LayoutInflater.from(context);
        ViewHolder viewholder = null;
        DvCauHoisListItemObj item = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_listview_cauhoitraloi_list,
                    null);
            viewholder = new ViewHolder();
            viewholder.id_tv_cauhoi_tieude = (TextView) convertView
                    .findViewById(R.id.id_tv_cauhoi_tieude);
            viewholder.id_tv_cauhoi_noidung = (TextView) convertView
                    .findViewById(R.id.id_tv_cauhoi_noidung);
            convertView.setTag(viewholder);
        }
        viewholder = (ViewHolder) convertView.getTag();
        if(mArrCauHois != null && mArrCauHois.size()>0){
            item = mArrCauHois.get(i);
            viewholder.id_tv_cauhoi_tieude.setText(Html.fromHtml(item.getTieuDe()));
            viewholder.id_tv_cauhoi_noidung.setText(Html.fromHtml(item.getNoiDung()));
        }

        return convertView;
    }

    public void refresh(ArrayList<DvCauHoisListItemObj> mArrCauHois) {
        this.mArrCauHois = mArrCauHois;
        notifyDataSetChanged();
    }
}
