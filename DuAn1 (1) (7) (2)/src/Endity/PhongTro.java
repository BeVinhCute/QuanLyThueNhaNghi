/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Endity;

/**
 *
 * @author admin
 */
public class PhongTro {
    private String MaPhong;
    private String MaLoaiPhong;
    private String MaLoaiGia;
    private String GhiChu;
    private int TinhTrang;

    public PhongTro() {
    }

    public PhongTro(String MaPhong, String MaLoaiPhong, String MaLoaiGia, String GhiChu, int TinhTrang) {
        this.MaPhong = MaPhong;
        this.MaLoaiPhong = MaLoaiPhong;
        this.MaLoaiGia = MaLoaiGia;
        this.GhiChu = GhiChu;
        this.TinhTrang = TinhTrang;
    }

    public String getMaPhong() {
        return MaPhong;
    }

    public void setMaPhong(String MaPhong) {
        this.MaPhong = MaPhong;
    }

    public String getMaLoaiPhong() {
        return MaLoaiPhong;
    }

    public void setMaLoaiPhong(String MaLoaiPhong) {
        this.MaLoaiPhong = MaLoaiPhong;
    }

    public String getMaLoaiGia() {
        return MaLoaiGia;
    }

    public void setMaLoaiGia(String MaLoaiGia) {
        this.MaLoaiGia = MaLoaiGia;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }

    public int getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(int TinhTrang) {
        this.TinhTrang = TinhTrang;
    }
    
   
}


