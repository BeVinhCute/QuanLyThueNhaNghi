/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Endity;

/**
 *
 * @author Dell
 */
public class LoaiGia {
    private String MaLoaiGia;
    private int GiaGio;
    private int GiaNgay;

    public LoaiGia() {
    }

    public LoaiGia(String MaLoaiGia, int GiaGio, int GiaNgay) {
        this.MaLoaiGia = MaLoaiGia;
        this.GiaGio = GiaGio;
        this.GiaNgay = GiaNgay;
    }

    public String getMaLoaiGia() {
        return MaLoaiGia;
    }

    public void setMaLoaiGia(String MaLoaiGia) {
        this.MaLoaiGia = MaLoaiGia;
    }

    public int getGiaGio() {
        return GiaGio;
    }

    public void setGiaGio(int GiaGio) {
        this.GiaGio = GiaGio;
    }

    public int getGiaNgay() {
        return GiaNgay;
    }

    public void setGiaNgay(int GiaNgay) {
        this.GiaNgay = GiaNgay;
    }



    
    
}
