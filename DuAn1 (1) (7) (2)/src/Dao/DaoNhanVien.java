/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Endity.NhanVien;
import helper.JdbcHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class DaoNhanVien extends MainDao<NhanVien, String>{
    
    String INSERT_SQL = "INSERT INTO NhanVien (MaNV,MaTk,HoTen,SDT,CCCD,NgaySinh,DiaChi) values(?,?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE NhanVien SET MaTK=?,HoTen=?,SDT=?,NgaySinh=?,NgaySinh=?,DiaChi=? WHERE MaNV =?";
    String DELETE_SQL = "DELETE FROM NhanVien WHERE MaNV=?";
    String SELECT_ALL_SQL = "SELECT * FROM NhanVien";
    String SELECT_BY_ID_SQL = "SELECT*FROM NhanVien WHERE MaNV=?";

    @Override
    public void insert(NhanVien entity) {
        JdbcHelper.executeUpdate(INSERT_SQL, entity.getMaNV(), entity.getMaTK(),entity.getHoTen(), entity.getSDT(), entity.getCCCD(),entity.getNgaySinh(), entity.getDiaChi());
    }

    @Override
    public void update(NhanVien entity) {
      JdbcHelper.executeUpdate(UPDATE_SQL, entity.getMaNV(), entity.getMaTK(),entity.getHoTen(), entity.getSDT(), entity.getCCCD(),entity.getNgaySinh(), entity.getDiaChi());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public List<NhanVien> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public NhanVien selectByID(String id) {
         List<NhanVien> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<NhanVien> selectBySQL(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(sql, args);
            while (rs.next()) {
                NhanVien entity = new NhanVien();
                entity.setMaNV(rs.getString("MaNV"));
                entity.setMaTK(rs.getString("MaTK"));
                entity.setHoTen(rs.getString("HoTen"));
                entity.setSDT(rs.getInt("SDT"));
                entity.setCCCD(rs.getString("CCCD"));
                entity.setNgaySinh(rs.getDate("NgaySinh"));
                entity.setDiaChi(rs.getString("DiaChi"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update_1(NhanVien entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public NhanVien selectByID_1(String key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<NhanVien> selectByKey(String key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
