package com.huewaco.cskh.objects;

import android.graphics.drawable.Drawable;

/**
 * Created by Kiet on 01/12/2016.
 */
public class TaskObj {
    private String nameTask;
    private int numberNotification;
    private Drawable drawable;
    private String link;

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public int getNumberNotification() {
        return numberNotification;
    }

    public void setNumberNotification(int numberNotification) {
        this.numberNotification = numberNotification;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
