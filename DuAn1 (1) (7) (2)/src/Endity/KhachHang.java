/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Endity;

import java.util.Date;

/**
 *
 * @author admin
 */
public class KhachHang {
    private String MaKH;
    private String HoTen;
    private String DiaChi;
    private String CCCD;
    private int SDT;
    private Date NgaySinh = new Date();
    private String Email;

    public KhachHang() {
    }

    public KhachHang(String MaKH, String HoTen, String DiaChi, String CCCD, int SDT, String Email) {
        this.MaKH = MaKH;
        this.HoTen = HoTen;
        this.DiaChi = DiaChi;
        this.CCCD = CCCD;
        this.SDT = SDT;
        this.Email = Email;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public int getSDT() {
        return SDT;
    }

    public void setSDT(int SDT) {
        this.SDT = SDT;
    }

    public Date getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(Date NgaySinh) {
        this.NgaySinh = NgaySinh;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }
     @Override
    public String toString() {
        return HoTen; // Hiển thị tên trong combobox
    }

    
    }
    
    

