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
public class ChiTietDV {
    private String MaThuePhong;
    private String MaDV;
    private Date NgayDK = new Date();
    
    public ChiTietDV() {
    }

    public ChiTietDV(String MaThueDV, String MaDV) {
        this.MaThuePhong = MaThueDV;
        this.MaDV = MaDV;
    }

    public String getMaThuePhong() {
        return MaThuePhong;
    }

    public void setMaThuePhong(String MaThueDV) {
        this.MaThuePhong = MaThueDV;
    }

    public String getMaDV() {
        return MaDV;
    }

    public void setMaDV(String MaDV) {
        this.MaDV = MaDV;
    }

    public Date getNgayDK() {
        return NgayDK;
    }

    public void setNgayDK(Date NgayDK) {
        this.NgayDK = NgayDK;
    }

    
}
