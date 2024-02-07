package com.huewaco.cskh.objects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by diepthaibaotoan on 3/1/2017.
 */

public class KhuVucListItemObj implements Serializable {

   //
    private String MaKv;
    private String TenKv;
    private ArrayList<QuanHuyenListItemObj> mArrItemQuanHuyen;

    //
    public String getTenKv() {
        return TenKv;
    }
    public void setTenKv(String tenKv) {
        TenKv = tenKv;
    }
    public String getMaKv() {
        return MaKv;
    }
    public void setMaKv(String maKv) {
        MaKv = maKv;
    }
    public ArrayList<QuanHuyenListItemObj> getmArrItemQuanHuyen() {
        return mArrItemQuanHuyen;
    }
    public void setmArrItemQuanHuyen(ArrayList<QuanHuyenListItemObj> mArrItemQuanHuyen) {
        this.mArrItemQuanHuyen = mArrItemQuanHuyen;
    }

}
