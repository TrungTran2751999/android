package com.huewaco.cskh.helper;
import android.view.MotionEvent;
import android.view.View;
public abstract class DoubleClickListener implements View.OnClickListener {
    private Long lastClickTime = 0L;

    @Override
    public void onClick(View view) {
        Long clickTime = System.currentTimeMillis();
        if(clickTime - lastClickTime < 300){
            onDoubleClick(view);
            lastClickTime = 0L;
        }else{
            lastClickTime = clickTime;
        }
    }
    abstract public void onDoubleClick(View view);

}
