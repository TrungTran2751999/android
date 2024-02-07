package com.huewaco.cskh.adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.objects.KhachHangObj;
import com.huewaco.cskh.objects.ThongBaoListItemObj;

import java.util.ArrayList;
import java.util.Optional;


/**
 * Created by Kiet on 01/12/2016.
 */
public class ListViewThongBaoListItemAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<ThongBaoListItemObj> mArrThongbaoList;


    public ListViewThongBaoListItemAdapter(Context context, ArrayList<ThongBaoListItemObj> mArrTasks) {
        this.context = context;
        this.mArrThongbaoList = mArrTasks;
    }

    @Override
    public int getCount() {
        return mArrThongbaoList.size();
    }

    @Override
    public Object getItem(int i) {
        return mArrThongbaoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        private TextView id_tv_content;
        private TextView id_tv_idkh;
        private ImageView id_tv_image;
        private LinearLayout id_tv_linear;
        private ViewGroup id_ly_notif;
        private TextView id_tv_number;
        private TextView id_tv_donvi;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        layoutInflater = LayoutInflater.from(context);
        ViewHolder viewholder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_listview_thongbao_list,
                    null);
            viewholder = new ViewHolder();

            viewholder.id_tv_content = (TextView) convertView
                    .findViewById(R.id.id_tv_content);
            viewholder.id_tv_idkh = (TextView) convertView
                    .findViewById(R.id.id_tv_idkh);
            viewholder.id_tv_image = (ImageView) convertView
                    .findViewById(R.id.id_tv_image);
            viewholder.id_tv_linear = (LinearLayout) convertView
                    .findViewById(R.id.id_tv_linear);
            viewholder.id_ly_notif = (ViewGroup) convertView
                    .findViewById(R.id.id_ly_notif);
            viewholder.id_tv_number = (TextView) convertView
                    .findViewById(R.id.id_tv_number);
            viewholder.id_tv_donvi = (TextView) convertView
                    .findViewById(R.id.id_tv_donvi);
            convertView.setTag(viewholder);
        }
        viewholder = (ViewHolder) convertView.getTag();
        ThongBaoListItemObj item = mArrThongbaoList.get(i);

        if(item.getId() == null){
            // List View
            if(item.getCountThongBao() >0){
                viewholder.id_ly_notif.setVisibility(View.VISIBLE);
                viewholder.id_tv_number.setText(""+item.getCountThongBao());
            }else{
                viewholder.id_ly_notif.setVisibility(View.GONE);
            }

            viewholder.id_tv_image.setVisibility(View.GONE);
            viewholder.id_tv_linear.setVisibility(View.GONE);
            viewholder.id_tv_idkh.setVisibility(View.VISIBLE);

            for (KhachHangObj x : GlobalVariable.mArrKHang) {
                if (x.getIdKh().equals(item.getIdKh())) {
                    viewholder.id_tv_idkh.setText(item.getIdKh()+ "  " + x.getDiaChiLD());
                    break;
                }
            }
        }else{
            viewholder.id_tv_image.setVisibility(View.VISIBLE);
            viewholder.id_tv_linear.setVisibility(View.VISIBLE);
            viewholder.id_tv_idkh.setVisibility(View.GONE);
            viewholder.id_ly_notif.setVisibility(View.GONE);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                viewholder.id_tv_content.setText(Html.fromHtml(handleContent(item.getNoiDung()), Html.FROM_HTML_MODE_COMPACT));
                viewholder.id_tv_donvi.setText(Html.fromHtml(handleTitle(item.getNoiDung()), Html.FROM_HTML_MODE_COMPACT));
            }else{
                viewholder.id_tv_content.setText(Html.fromHtml(handleContent(item.getNoiDung())));
                viewholder.id_tv_donvi.setText(Html.fromHtml(handleTitle(item.getNoiDung())));
            }

            if(item.isDaDoc()){
                viewholder.id_tv_content.setTextColor(Color.BLACK);
            }else{
                viewholder.id_tv_content.setTextColor(Color.BLUE);
            }
        }

        return convertView;
    }
    private String handleTitle(String tb){
        String[] arrStr = tb.split("]")[0].split("-");
        if(arrStr.length > 1){
            return arrStr[1];
        }
        return "HUEWACO";
    }
    private String handleContent(String tb){
        String[] arrStr = tb.split("]");
        String idKh = arrStr[0].split("-")[0];
        String result = "";
        if(arrStr[1]!=null){
            result = idKh + "] " + arrStr[1];
        }else{
            result = tb;
        }
        return result;
    }
    public void refresh(ArrayList<ThongBaoListItemObj> mArrThongbaoList) {
        this.mArrThongbaoList = mArrThongbaoList;
        notifyDataSetChanged();
    }
}
