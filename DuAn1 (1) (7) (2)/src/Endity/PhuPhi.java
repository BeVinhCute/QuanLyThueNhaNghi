/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Endity;

/**
 *
 * @author admin
 */
public class PhuPhi {
    private String MaPP;
    private String TenPP;
    private int GiaTri;
    private String GhiChu;
    private boolean TrangThai=true;

    public PhuPhi() {
    }

    public PhuPhi(String MaPP, String TenPP, int GiaTri, String GhiChu) {
        this.MaPP = MaPP;
        this.TenPP = TenPP;
        this.GiaTri = GiaTri;
        this.GhiChu = GhiChu;
    }

    public String getMaPP() {
        return MaPP;
    }

    public void setMaPP(String MaPP) {
        this.MaPP = MaPP;
    }

    public String getTenPP() {
        return TenPP;
    }

    public void setTenPP(String TenPP) {
        this.TenPP = TenPP;
    }

    public int getGiaTri() {
        return GiaTri;
    }

    public void setGiaTri(int GiaTri) {
        this.GiaTri = GiaTri;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }

    public boolean isTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(boolean TrangThai) {
        this.TrangThai = TrangThai;
    }

  

    
    
}
