package com.huewaco.cskh.objects;

import java.io.Serializable;

/**
 * Created by Kiet on 06/03/2017.
 */

public class KhuVucObj implements Serializable {
    private String MaKv;
    private String TenKv;

    public String getMaKv() {
        return MaKv;
    }

    public void setMaKv(String maKv) {
        MaKv = maKv;
    }

    public String getTenKv() {
        return TenKv;
    }

    public void setTenKv(String tenKv) {
        TenKv = tenKv;
    }
}
