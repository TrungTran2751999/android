package com.huewaco.cskh.objects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by diepthaibaotoan on 3/1/2017.
 */

public class QuanHuyenListItemObj  implements Serializable {

    //
    private String MaQuan;
    private String TenQuan;
    private ArrayList<PhuongXaListItmObj> mArrPhuongXa;

    //
    public String getMaQuan() {
        return MaQuan;
    }

    public void setMaQuan(String maQuan) {
        MaQuan = maQuan;
    }

    public String getTenQuan() {
        return TenQuan;
    }

    public void setTenQuan(String tenQuan) {
        TenQuan = tenQuan;
    }

    public ArrayList<PhuongXaListItmObj> getmArrPhuongXa() {
        return mArrPhuongXa;
    }

    public void setmArrPhuongXa(ArrayList<PhuongXaListItmObj> mArrPhuongXa) {
        this.mArrPhuongXa = mArrPhuongXa;
    }
}
