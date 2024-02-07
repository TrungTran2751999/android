package com.huewaco.cskh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatCheckBox;

import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.objects.KhachHangObj;
import com.huewaco.cskh.objects.TToanTienNuocListItemObj;

import java.util.ArrayList;


public class ListViewTToanTNuocAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<TToanTienNuocListItemObj> mArrTToanTienNuoc;

    public ListViewTToanTNuocAdapter(Context context, ArrayList<TToanTienNuocListItemObj> mArr) {
        this.context = context;
        this.mArrTToanTienNuoc = mArr;
    }

    @Override
    public int getCount() {
        return mArrTToanTienNuoc.size();
    }

    @Override
    public Object getItem(int i) {
        return mArrTToanTienNuoc.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        private View id_seperateline;
        private TextView id_tv_thang_detail;
        private TextView id_tv_nam_detail;
        private TextView id_tv_tinhtien_detail;
        private TextView id_tv_ttien_detail;
        private CheckBox id_cb_tthai_detail;
        private TextView id_tv_idkh;
        private TextView id_tv_sum;
        private LinearLayout id_ly_title;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        layoutInflater = LayoutInflater.from(context);
        ViewHolder viewholder = null;
        TToanTienNuocListItemObj item = mArrTToanTienNuoc.get(i);
        if(item.getIdkh() != null){
            //Header
            convertView = layoutInflater.inflate(R.layout.item_hzv_ttoan_tnuoc_title,
                    null);
            viewholder = new ViewHolder();
            viewholder.id_seperateline = (View) convertView
                    .findViewById(R.id.id_seperateline);
            if(i == 0){
                viewholder.id_seperateline.setVisibility(View.GONE);
            }else{
                viewholder.id_seperateline.setVisibility(View.VISIBLE);
            }
            viewholder.id_tv_idkh = (TextView) convertView
                    .findViewById(R.id.id_tv_idkh);
            viewholder.id_tv_idkh.setVisibility(View.VISIBLE);
            viewholder.id_tv_sum = (TextView) convertView
                    .findViewById(R.id.id_tv_sum);
            viewholder.id_tv_sum.setVisibility(View.VISIBLE);
            for (KhachHangObj x : GlobalVariable.mArrKHang) {
                if (x.getIdKh().equals(item.getIdkh())) {
                    viewholder.id_tv_idkh.setText(item.getIdkh()+ "  " + x.getDiaChiLD());
                    viewholder.id_tv_sum.setText("Tổng tiền : " + item.getSumIdkh() +" (VNĐ)");
                    break;
                }
                if(item.getSumIdkh().equals("0")){
                    viewholder.id_ly_title = (LinearLayout) convertView
                            .findViewById(R.id.id_ly_title);
                    viewholder.id_ly_title.setVisibility(View.GONE);
                }
            }
            convertView.setTag(viewholder);
            viewholder = (ViewHolder) convertView.getTag();
        }else{
            convertView = layoutInflater.inflate(R.layout.item_listview_ttoan_tnuoc_list,
                    null);
            viewholder = new ViewHolder();
            viewholder.id_tv_nam_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_nam_detail);
            viewholder.id_tv_thang_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_thang_detail);
            viewholder.id_tv_tinhtien_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_tinhtien_detail);
            viewholder.id_tv_ttien_detail = (TextView) convertView
                    .findViewById(R.id.id_tv_ttien_detail);
            viewholder.id_cb_tthai_detail = (CheckBox) convertView
                    .findViewById(R.id.id_cb_tthai_detail);
            viewholder.id_cb_tthai_detail.setText("");

            convertView.setTag(viewholder);
            viewholder = (ViewHolder) convertView.getTag();
            if(mArrTToanTienNuoc != null && mArrTToanTienNuoc.size()>0){

                viewholder.id_tv_nam_detail.setText(item.getNam());
                viewholder.id_tv_thang_detail.setText(item.getThang());
                viewholder.id_tv_ttien_detail.setText(item.getTongTien());
                viewholder.id_tv_tinhtien_detail.setText(item.getM3TinhTien());
                viewholder.id_tv_ttien_detail.setText(item.getTongTien());
                viewholder.id_cb_tthai_detail.setVisibility(View.VISIBLE);
                if(item.getDaTra().equalsIgnoreCase("true")) {
                    viewholder.id_cb_tthai_detail.setChecked(true);
                } else if( item.getDaTra().equalsIgnoreCase("false")) {
                    viewholder.id_cb_tthai_detail.setChecked(false);

                } else {
                    viewholder.id_cb_tthai_detail.setVisibility(View.GONE);
                }
            }
        }


        return convertView;
    }
    public void refresh(ArrayList<TToanTienNuocListItemObj> mArrTToanTienn){
        mArrTToanTienNuoc = mArrTToanTienn;
        notifyDataSetChanged();
    }
}
