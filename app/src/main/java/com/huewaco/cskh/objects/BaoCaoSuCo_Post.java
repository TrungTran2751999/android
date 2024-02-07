package com.huewaco.cskh.objects;

import com.huewaco.cskh.helper.CommonHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class BaoCaoSuCo_Post {
//    private String img_url;
//    private String title;
//    private String content;
//    private String date;
//    private int status; //1: tieu bieu, 2 daxuly, 3 dangxuly
//    private boolean isMyPost;

    private long Id;
    private String IdKH;
    private String TenKhachHang;
    private String SoDienThoai;
    private String DiaChiKhachHang;
    private String NoiDung;
    private String NgayTao;
    private String DiaChiSuCoTheoMobile;
    private String DiaChiSuCoKhachHangChon;
    private String DiaChiDaXacNhan;
    private double KinhDoTheoMobile;
    private double ViDoTheoMobile;
    private double KinhDoKhachHangChon;
    private double ViDoKhachHangChon;
    private  int DaKiemTra;
    private boolean DaXuLy;
    private boolean LaTieuBieu;
    private ArrayList<ImgInPostObj> Images1;
    private ArrayList<ImgInPostObj> Images;
    private String ThongTinPhanHoiChoKhachHang;
    private boolean isShowPhanHoiChoKh = false;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getIdKH() {
        return IdKH;
    }

    public void setIdKH(String idKH) {
        IdKH = idKH;
    }

    public String getTenKhachHang() {
        return TenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        TenKhachHang = tenKhachHang;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        SoDienThoai = soDienThoai;
    }

    public String getDiaChiKhachHang() {
        return DiaChiKhachHang;
    }

    public void setDiaChiKhachHang(String diaChiKhachHang) {
        DiaChiKhachHang = diaChiKhachHang;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }

    public String getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(String ngayTao) {
        NgayTao = ngayTao;
    }

    public String getDiaChiSuCoTheoMobile() {
        return DiaChiSuCoTheoMobile;
    }

    public void setDiaChiSuCoTheoMobile(String diaChiSuCoTheoMobile) {
        DiaChiSuCoTheoMobile = diaChiSuCoTheoMobile;
    }

    public String getDiaChiSuCoKhachHangChon() {
        return DiaChiSuCoKhachHangChon;
    }

    public void setDiaChiSuCoKhachHangChon(String diaChiSuCoKhachHangChon) {
        DiaChiSuCoKhachHangChon = diaChiSuCoKhachHangChon;
    }

    public String getDiaChiDaXacNhan() {
        return DiaChiDaXacNhan;
    }

    public void setDiaChiDaXacNhan(String diaChiDaXacNhan) {
        DiaChiDaXacNhan = diaChiDaXacNhan;
    }

    public double getKinhDoTheoMobile() {
        return KinhDoTheoMobile;
    }

    public void setKinhDoTheoMobile(double kinhDoTheoMobile) {
        KinhDoTheoMobile = kinhDoTheoMobile;
    }

    public double getViDoTheoMobile() {
        return ViDoTheoMobile;
    }

    public void setViDoTheoMobile(double viDoTheoMobile) {
        ViDoTheoMobile = viDoTheoMobile;
    }

    public double getKinhDoKhachHangChon() {
        return KinhDoKhachHangChon;
    }

    public void setKinhDoKhachHangChon(double kinhDoKhachHangChon) {
        KinhDoKhachHangChon = kinhDoKhachHangChon;
    }

    public double getViDoKhachHangChon() {
        return ViDoKhachHangChon;
    }

    public void setViDoKhachHangChon(double viDoKhachHangChon) {
        ViDoKhachHangChon = viDoKhachHangChon;
    }

    public int getDaKiemTra() {
        return DaKiemTra;
    }

    public void setDaKiemTra(int daKiemTra) {
        DaKiemTra = daKiemTra;
    }

    public boolean isDaXuLy() {
        return DaXuLy;
    }

    public void setDaXuLy(boolean daXuLy) {
        DaXuLy = daXuLy;
    }

    public boolean isLaTieuBieu() {
        return LaTieuBieu;
    }

    public void setLaTieuBieu(boolean laTieuBieu) {
        LaTieuBieu = laTieuBieu;
    }

    public ArrayList<ImgInPostObj> getImages() {
        return Images;
    }

    public void setImages(ArrayList<ImgInPostObj> images) {
        Images = images;
    }

    public String getThongTinPhanHoiChoKhachHang() {
        return ThongTinPhanHoiChoKhachHang;
    }

    public void setThongTinPhanHoiChoKhachHang(String thongTinPhanHoiChoKhachHang) {
        ThongTinPhanHoiChoKhachHang = thongTinPhanHoiChoKhachHang;
    }

    public boolean isShowPhanHoiChoKh() {
        return isShowPhanHoiChoKh;
    }

    public void setShowPhanHoiChoKh(boolean showPhanHoiChoKh) {
        isShowPhanHoiChoKh = showPhanHoiChoKh;
    }

    public static Comparator<BaoCaoSuCo_Post> sort_baoCaoSuCo_Post_DESC = new Comparator<BaoCaoSuCo_Post>() {

        public int compare(BaoCaoSuCo_Post s1, BaoCaoSuCo_Post s2) {
            /*
            Date date1 = CommonHelper.getDateFromString(s1.getNgayTao(),"");
            Date date2 = CommonHelper.getDateFromString(s2.getNgayTao(),"");

            //ascending order
            return date2.compareTo(date1);
            */

            return (int)(s2.getId()-s1.getId());
        }};

    public ArrayList<ImgInPostObj> getImages1() {
        return Images1;
    }

    public void setImages1(ArrayList<ImgInPostObj> images1) {
        Images1 = images1;
    }
}
