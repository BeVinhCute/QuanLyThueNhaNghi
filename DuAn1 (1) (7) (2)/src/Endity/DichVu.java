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
public class DichVu {
    private String MaDV;
    private String TenDV;
    private int GiaDV;
    private String GhiChu;



    public DichVu() {
    }

    public DichVu(String MaDV, String TenDV, int GiaDV, String GhiChu) {
        this.MaDV = MaDV;
        this.TenDV = TenDV;
        this.GiaDV = GiaDV;
        this.GhiChu = GhiChu;
    }

    public String getMaDV() {
        return MaDV;
    }

    public void setMaDV(String MaDV) {
        this.MaDV = MaDV;
    }

    public String getTenDV() {
        return TenDV;
    }

    public void setTenDV(String TenDV) {
        this.TenDV = TenDV;
    }

    public int getGiaDV() {
        return GiaDV;
    }

    public void setGiaDV(int GiaDV) {
        this.GiaDV = GiaDV;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }


    
}
