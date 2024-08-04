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
public class ThuePhongTro {
   private String MaThuePhong;
   private String MaKH;
   private String MaPhong;
   private Date NgayThue = new Date();
   private Date NgayTra = new Date();
   private int TrangThai;

    public ThuePhongTro() {
    }

    public ThuePhongTro(String MaThuePhong, String MaKH, String MaPhong, int TrangThai) {
        this.MaThuePhong = MaThuePhong;
        this.MaKH = MaKH;
        this.MaPhong = MaPhong;
        this.TrangThai = TrangThai;
    }

    public String getMaThuePhong() {
        return MaThuePhong;
    }

    public void setMaThuePhong(String MaThuePhong) {
        this.MaThuePhong = MaThuePhong;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }

    public String getMaPhong() {
        return MaPhong;
    }

    public void setMaPhong(String MaPhong) {
        this.MaPhong = MaPhong;
    }

    public Date getNgayThue() {
        return NgayThue;
    }

    public void setNgayThue(Date NgayThue) {
        this.NgayThue = NgayThue;
    }

    public Date getNgayTra() {
        return NgayTra;
    }

    public void setNgayTra(Date NgayTra) {
        this.NgayTra = NgayTra;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }

   
   
}
