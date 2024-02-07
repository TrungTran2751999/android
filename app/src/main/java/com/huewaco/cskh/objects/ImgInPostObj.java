package com.huewaco.cskh.objects;

import com.huewaco.cskh.helper.GlobalVariable;

public class ImgInPostObj {
    private double Id;
    private String TenFile;
    private long IdBaoCaoSuCo;
    private String Base64;

    public double getId() {
        return Id;
    }

    public void setId(double id) {
        Id = id;
    }

    public String getTenFile() {
        return TenFile;
    }

    public void setTenFile(String tenFile) {
        TenFile = tenFile;
    }

    public long getIdBaoCaoSuCo() {
        return IdBaoCaoSuCo;
    }

    public void setIdBaoCaoSuCo(long idBaoCaoSuCo) {
        IdBaoCaoSuCo = idBaoCaoSuCo;
    }

    public String getBase64() {
        return Base64;
    }

    public void setBase64(String base64) {
        Base64 = base64;
    }

}
