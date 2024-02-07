/**
 * Created by: Kiet.Duong
 * 
 **/
package com.huewaco.cskh.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

//import android.support.v4.view.ViewPager;

public class GraphicView extends View  {
    public GraphicView(Context context) {
        this(context, null);
    }

    public GraphicView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GraphicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Draw component in here
    }
}