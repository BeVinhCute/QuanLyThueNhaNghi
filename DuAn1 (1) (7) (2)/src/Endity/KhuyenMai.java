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
public class KhuyenMai {
    private String MaVoucher;
    private String TenVoucher;
    private int GiaTri;
    private boolean TrangThai=false;

    public KhuyenMai() {
    }

    public KhuyenMai(String MaVoucher, String TenVoucher, int GiaTri) {
        this.MaVoucher = MaVoucher;
        this.TenVoucher = TenVoucher;
        this.GiaTri = GiaTri;
    }

    public String getMaVoucher() {
        return MaVoucher;
    }

    public void setMaVoucher(String MaVoucher) {
        this.MaVoucher = MaVoucher;
    }

    public String getTenVoucher() {
        return TenVoucher;
    }

    public void setTenVoucher(String TenVoucher) {
        this.TenVoucher = TenVoucher;
    }

    public int getGiaTri() {
        return GiaTri;
    }

    public void setGiaTri(int GiaTri) {
        this.GiaTri = GiaTri;
    }


    public boolean isTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(boolean TrangThai) {
        this.TrangThai = TrangThai;
    }

    
    
    
}
