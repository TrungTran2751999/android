package com.huewaco.cskh.objects;

import org.json.JSONArray;

import java.util.ArrayList;

public class ThongTinKHObj {
    private String Idkh;
    private String TenKh;
    private String DiaChi;
    private String DienThoai;
    private String KyGhi;
    private int SoNk;
    private String TenMdsd;
    private String TenTthaiGhi;
    private String MaTthaiGhi;
    private String NgayNhap_Tt;
    private int ChiSoDau;

    private String NgayNhap;
    private String ChiSoCuoi="";
    // phan them vao
    private int KlTieuThu;
    private int M3TinhTien;
    private double TongTien;
    private String TongTienText;
    private String ChiSoCuoi_NhapTay="";
    private JSONArray jSONArray;

    public String getIdkh() {
        return Idkh;
    }

    public void setIdkh(String idkh) {
        Idkh = idkh;
    }

    public String getTenKh() {
        return TenKh;
    }

    public void setTenKh(String tenKh) {
        TenKh = tenKh;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getDienThoai() {
        return DienThoai;
    }

    public void setDienThoai(String dienThoai) {
        DienThoai = dienThoai;
    }

    public String getKyGhi() {
        return KyGhi;
    }

    public void setKyGhi(String kyGhi) {
        KyGhi = kyGhi;
    }

    public int getSoNk() {
        return SoNk;
    }

    public void setSoNk(int soNk) {
        SoNk = soNk;
    }

    public String getTenMdsd() {
        return TenMdsd;
    }

    public void setTenMdsd(String tenMdsd) {
        TenMdsd = tenMdsd;
    }

    public String getTenTthaiGhi() {
        return TenTthaiGhi;
    }

    public void setTenTthaiGhi(String tenTthaiGhi) {
        TenTthaiGhi = tenTthaiGhi;
    }

    public String getMaTthaiGhi() {
        return MaTthaiGhi;
    }

    public void setMaTthaiGhi(String maTthaiGhi) {
        MaTthaiGhi = maTthaiGhi;
    }

    public String getNgayNhap_Tt() {
        return NgayNhap_Tt;
    }

    public void setNgayNhap_Tt(String ngayNhap_Tt) {
        NgayNhap_Tt = ngayNhap_Tt;
    }

    public int getChiSoDau() {
        return ChiSoDau;
    }

    public void setChiSoDau(int chiSoDau) {
        ChiSoDau = chiSoDau;
    }
    // phan them vao

    public int getKlTieuThu() {
        return KlTieuThu;
    }

    public void setKlTieuThu(int klTieuThu) {
        KlTieuThu = klTieuThu;
    }

    public int getM3TinhTien() {
        return M3TinhTien;
    }

    public void setM3TinhTien(int m3TinhTien) {
        M3TinhTien = m3TinhTien;
    }

    public double getTongTien() {
        return TongTien;
    }

    public void setTongTien(double tongTien) {
        TongTien = tongTien;
    }

    public String getTongTienText() {
        return TongTienText;
    }

    public void setTongTienText(String tongTienText) {
        TongTienText = tongTienText;
    }

    public String getNgayNhap() {
        return NgayNhap;
    }

    public void setNgayNhap(String ngayNhap) {
        NgayNhap = ngayNhap;
    }

    public String getChiSoCuoi_NhapTay() {
        return ChiSoCuoi_NhapTay;
    }

    public void setChiSoCuoi_NhapTay(String chiSoCuoi_NhapTay) {
        ChiSoCuoi_NhapTay = chiSoCuoi_NhapTay;
    }

    public JSONArray getjSONArray() {
        return jSONArray;
    }

    public void setjSONArray(JSONArray jSONArray) {
        this.jSONArray = jSONArray;
    }

    public String getChiSoCuoi() {
        return ChiSoCuoi;
    }

    public void setChiSoCuoi(String chiSoCuoi) {
        ChiSoCuoi = chiSoCuoi;
    }
}
