package com.huewaco.cskh.objects;

import java.io.Serializable;

/**
 * Created by diepthaibaotoan on 3/1/2017.
 */

public class PhuongXaListItmObj implements Serializable {

    //
    private String MaPhuong;
    private String TenPhuong;

    //
    public String getTenPhuong() {
        return TenPhuong;
    }

    public void setTenPhuong(String tenPhuong) {
        TenPhuong = tenPhuong;
    }

    public String getMaPhuong() {
        return MaPhuong;
    }

    public void setMaPhuong(String maPhuong) {
        MaPhuong = maPhuong;
    }


}
