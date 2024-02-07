package com.huewaco.cskh.objects;

/**
 * Created by Kiet on 09/12/2016.
 */
public class ThongBaoListItemObj {
    private String Id;
    private String NoiDung;
    private boolean DaDoc;
    private String IdKh;
    private Integer CountThongBao;
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }

    public boolean isDaDoc() {
        return DaDoc;
    }

    public void setDaDoc(boolean daDoc) {
        DaDoc = daDoc;
    }
    public String getIdKh() {
        return IdKh;
    }

    public void setIdKh(String idKh) {
        IdKh = idKh;
    }
    public Integer getCountThongBao() {
        return CountThongBao;
    }

    public void setCountThongBao(Integer countThongBao) {
        CountThongBao = countThongBao;
    }
}
