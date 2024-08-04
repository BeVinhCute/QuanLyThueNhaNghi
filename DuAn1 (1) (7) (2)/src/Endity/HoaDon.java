/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Endity;

import java.util.Date;

/**
 *
 * @author Dell
 */
public class HoaDon {
    private String MaHD;
    private String MaTK;
    private String MaThuePhong;
    private int KhachTra;
    private int ThanhTien;
    private String MaVC;
    private Date NgayTao;

    public HoaDon() {
    }

    public HoaDon(String MaHD, String MaTK, String MaThuePhong, int KhachTra, int ThanhTien, String MaVC, Date NgayTao) {
        this.MaHD = MaHD;
        this.MaTK = MaTK;
        this.MaThuePhong = MaThuePhong;
        this.KhachTra = KhachTra;
        this.ThanhTien = ThanhTien;
        this.MaVC = MaVC;
        this.NgayTao = NgayTao;
    }

    public String getMaHD() {
        return MaHD;
    }

    public void setMaHD(String MaHD) {
        this.MaHD = MaHD;
    }

    public String getMaTK() {
        return MaTK;
    }

    public void setMaTK(String MaTK) {
        this.MaTK = MaTK;
    }

    public String getMaThuePhong() {
        return MaThuePhong;
    }

    public void setMaThuePhong(String MaThuePhong) {
        this.MaThuePhong = MaThuePhong;
    }

    public int getKhachTra() {
        return KhachTra;
    }

    public void setKhachTra(int KhachTra) {
        this.KhachTra = KhachTra;
    }

    public int getThanhTien() {
        return ThanhTien;
    }

    public void setThanhTien(int ThanhTien) {
        this.ThanhTien = ThanhTien;
    }

    public String getMaVC() {
        return MaVC;
    }

    public void setMaVC(String MaVC) {
        this.MaVC = MaVC;
    }

    public Date getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(Date NgayTao) {
        this.NgayTao = NgayTao;
    }

    

}