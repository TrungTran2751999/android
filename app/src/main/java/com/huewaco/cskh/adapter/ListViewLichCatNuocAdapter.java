package com.huewaco.cskh.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.objects.LichCatNuocListItemObj;
import com.huewaco.cskh.objects.ThongBaoListItemObj;

import java.util.ArrayList;


public class ListViewLichCatNuocAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<LichCatNuocListItemObj> mArrLichCatNuoc;

    public ListViewLichCatNuocAdapter(Context context, ArrayList<LichCatNuocListItemObj> mArr) {
        this.context = context;
        this.mArrLichCatNuoc = mArr;
    }

    public void refresh(ArrayList<LichCatNuocListItemObj> mArrLichCatNuoc) {
        this.mArrLichCatNuoc = mArrLichCatNuoc;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mArrLichCatNuoc.size();
    }

    @Override
    public Object getItem(int i) {
        return mArrLichCatNuoc.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        private TextView id_tv_donvi;
        private TextView id_tv_date;
        private TextView id_tv_content;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        layoutInflater = LayoutInflater.from(context);
        ViewHolder viewholder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_listview_thongbao_list,
                    null);
            viewholder = new ViewHolder();
            viewholder.id_tv_donvi = (TextView) convertView
                    .findViewById(R.id.id_tv_donvi);
            viewholder.id_tv_date = (TextView) convertView
                    .findViewById(R.id.id_tv_date);
            viewholder.id_tv_content = (TextView) convertView
                    .findViewById(R.id.id_tv_content);
            convertView.setTag(viewholder);
        }
        viewholder = (ViewHolder) convertView.getTag();
        LichCatNuocListItemObj item = mArrLichCatNuoc.get(i);

        //CommonHelper.setTextHtml( viewholder.id_tv_content, item.getNoiDung());
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            viewholder.id_tv_donvi.setText(Html.fromHtml(item.getLyDo(), Html.FROM_HTML_MODE_COMPACT));
            viewholder.id_tv_content.setText(Html.fromHtml(item.getNgayBatDau()+ " -> " + item.getNgayKetThuc(), Html.FROM_HTML_MODE_COMPACT));
        }else{
            viewholder.id_tv_donvi.setText(Html.fromHtml(item.getLyDo()));
            viewholder.id_tv_content.setText(Html.fromHtml(item.getNgayBatDau()+ " -> " + item.getNgayKetThuc()));
        }
        viewholder.id_tv_donvi.setTextColor(Color.BLACK);
        viewholder.id_tv_content.setTextColor(Color.BLACK);
        //viewholder.id_tv_date.setText(item.getDate());
        //viewholder.id_tv_donvi.setText(item.getTitle());
        return convertView;
    }

}
