package com.huewaco.cskh.adapter;

import android.content.Context;
import android.view.View;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.huewaco.cskh.activity.ABaoCaoSuCo_List;
import com.huewaco.cskh.objects.ImgInPostObj;
import com.huewaco.cskh.view.BaoCaoSuCoViewPagerDetail;

import java.util.ArrayList;

public class ViewPagerBaoCaoSuCoAdapter extends PagerAdapter {
    private Context context;
    private ABaoCaoSuCo_List mParent;
    private ArrayList<ImgInPostObj> mArrNhans = new ArrayList<ImgInPostObj>();
    private boolean isDestroy = false;



    public ViewPagerBaoCaoSuCoAdapter(ABaoCaoSuCo_List parent, Context context,
                                          ArrayList<ImgInPostObj> mArrNhans) {
        this.mParent = parent;
        this.mArrNhans = mArrNhans;
        this.context = context;
    }

    public Object instantiateItem(View collection, int position) {

        View view = ((ViewPager) collection).getChildAt(position);
        // Check is pagerLookupMarkDetail
        if (view instanceof BaoCaoSuCoViewPagerDetail) {
            //((AOneKHOneQuyenViewPagerDetail) view).changeColor();
            return view;
        } else {
            BaoCaoSuCoViewPagerDetail detail = new BaoCaoSuCoViewPagerDetail(this.mParent,
                    context, mArrNhans.get(position));
            ((ViewPager) collection).removeViewAt(position);
            ((ViewPager) collection).addView(detail, position);
            return detail;
        }
    }

    @Override
    public int getCount() {
        return mArrNhans.size();
    }

    public void refresh(ArrayList<ImgInPostObj> mArrNhans) {
        this.mArrNhans = mArrNhans;
        notifyDataSetChanged();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View) arg1);
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        if (isDestroy) {
            ((ViewPager) arg0).removeView((View) arg2);
        }

    }
}
