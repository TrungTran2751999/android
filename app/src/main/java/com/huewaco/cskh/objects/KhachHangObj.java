package com.huewaco.cskh.objects;

import java.io.Serializable;
import java.util.List;

public class KhachHangObj implements Serializable {
    //
    String Username ="";
    String IdKh = "";
    String TenKhachHang = "";
    String SoNha ="";
    String DuongPho = "";
    String PhuongXa = "";
    String QuanHuyen = "";
    String DiaChiLD = "";
    private String Email = "";
    String DiDong = "";
    String MaDDK = "";
    String MACQTT = "";
    Boolean IsInGroup = false;
    private String ma_khang;
    //infor for token
    private String deviceToken;
    private String deviceType;
    private String username_login;
    private String password_login;


    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getMa_khang() {
        return ma_khang;
    }

    public void setMa_khang(String ma_khang) {
        this.ma_khang = ma_khang;
    }


    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getUsername_login() {
        return username_login;
    }

    public void setUsername_login(String username_login) {
        this.username_login = username_login;
    }

    public String getPassword_login() {
        return password_login;
    }

    public void setPassword_login(String password_login) {
        this.password_login = password_login;
    }

    public String getIdKh() {
        return IdKh;
    }

    public void setIdKh(String idKh) {
        IdKh = idKh;
    }

    public String getTenKhachHang() {
        return TenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        TenKhachHang = tenKhachHang;
    }

    public String getDuongPho() {
        return DuongPho;
    }

    public void setDuongPho(String duongPho) {
        DuongPho = duongPho;
    }

    public String getPhuongXa() {
        return PhuongXa;
    }

    public void setPhuongXa(String phuongXa) {
        PhuongXa = phuongXa;
    }

    public String getQuanHuyen() {
        return QuanHuyen;
    }

    public void setQuanHuyen(String quanHuyen) {
        QuanHuyen = quanHuyen;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDiDong() {
        return DiDong;
    }

    public void setDiDong(String diDong) {
        DiDong = diDong;
    }

    public String getSoNha() {
        return SoNha;
    }

    public void setSoNha(String soNha) {
        SoNha = soNha;
    }
    public String getMaDDK() {
      return MaDDK;
    }
    public void setMaDDK(String maDDK) {
        MaDDK = maDDK;
    }

    public String getDiaChiLD() {
        return DiaChiLD;
    }

    public void setDiaChiLD(String diaChiLD) {
        DiaChiLD = diaChiLD;
    }

    public String getMACQTT() {
        return MACQTT;
    }

    public void setMACQTT(String MACQTT) {
        this.MACQTT = MACQTT;
    }

    public Boolean getInGroup() {
        return IsInGroup;
    }

    public void setInGroup(Boolean inGroup) {
        IsInGroup = inGroup;
    }

    @Override
    public String toString() {
        return String.format("%s:%s",this.getIdKh(),this.getDiaChiLD()); // What to display in the Spinner list.
    }

}
