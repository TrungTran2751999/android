package com.huewaco.cskh.objects;

import java.util.ArrayList;
import java.util.Comparator;

public class GhiChiSo4KiCuoi {
    private int Id;
    private int ChiSoDau;
    private int ChiSoCuoi;
    private String NgayNhap;
    private int KhoiLuongTieuThu;
    private double TongTien;
    private boolean IsDaXacThuc;
    private String NgayXacThuc;
    private int Nam;
    private int Thang;
    private ArrayList<String> mArrFiles;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getChiSoDau() {
        return ChiSoDau;
    }

    public void setChiSoDau(int chiSoDau) {
        ChiSoDau = chiSoDau;
    }

    public int getChiSoCuoi() {
        return ChiSoCuoi;
    }

    public void setChiSoCuoi(int chiSoCuoi) {
        ChiSoCuoi = chiSoCuoi;
    }

    public String getNgayNhap() {
        return NgayNhap;
    }

    public void setNgayNhap(String ngayNhap) {
        NgayNhap = ngayNhap;
    }

    public int getKhoiLuongTieuThu() {
        return KhoiLuongTieuThu;
    }

    public void setKhoiLuongTieuThu(int khoiLuongTieuThu) {
        KhoiLuongTieuThu = khoiLuongTieuThu;
    }

    public double getTongTien() {
        return TongTien;
    }

    public void setTongTien(double tongTien) {
        TongTien = tongTien;
    }

    public boolean isDaXacThuc() {
        return IsDaXacThuc;
    }

    public void setDaXacThuc(boolean daXacThuc) {
        IsDaXacThuc = daXacThuc;
    }

    public String getNgayXacThuc() {
        return NgayXacThuc;
    }

    public void setNgayXacThuc(String ngayXacThuc) {
        NgayXacThuc = ngayXacThuc;
    }

    public ArrayList<String> getmArrFiles() {
        return mArrFiles;
    }

    public void setmArrFiles(ArrayList<String> mArrFiles) {
        this.mArrFiles = mArrFiles;
    }

    public int getNam() {
        return Nam;
    }

    public void setNam(int nam) {
        Nam = nam;
    }

    public int getThang() {
        return Thang;
    }

    public void setThang(int thang) {
        Thang = thang;
    }
    public static Comparator<GhiChiSo4KiCuoi> sort_GhiChiSo4KiCuoi_DESC = new Comparator<GhiChiSo4KiCuoi>() {

        public int compare(GhiChiSo4KiCuoi s1, GhiChiSo4KiCuoi s2) {
            return (int)(s2.getId()-s1.getId());
        }};
}
