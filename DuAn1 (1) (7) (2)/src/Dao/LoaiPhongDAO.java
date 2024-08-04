/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Endity.LoaiPhong;
import helper.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dell
 */
public class LoaiPhongDAO extends MainDao<LoaiPhong, String>{
    String INSERT_SQL = "INSERT INTO LoaiPhong (MaLoaiPhong, TenLoaiPhong,Sogiuong) values(?,?,?)";
    String UPDATE_SQL = "UPDATE LoaiPhong SET TenLoaiPhong=?,Sogiuong=? WHERE MaLoaiPhong =?";
    String DELETE_SQL = "DELETE FROM LoaiPhong WHERE MaLoaiPhong=?";
    String SELECT_ALL_SQL = "SELECT * FROM LoaiPhong";
    String SELECT_BY_ID_SQL = "SELECT*FROM LoaiPhong WHERE MaLoaiPhong=?";   
    
    
    @Override
    public void insert(LoaiPhong entity) {
        JdbcHelper.executeUpdate(INSERT_SQL, entity.getMaLoaiPhong(),entity.getTenLoaiPhong(),entity.getSoGiuong());
    }

    @Override
    public void update(LoaiPhong entity) {
        JdbcHelper.executeUpdate(UPDATE_SQL, entity.getTenLoaiPhong(),entity.getSoGiuong(),entity.getMaLoaiPhong());
    }

    @Override
    public void update_1(LoaiPhong entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String id) {
        JdbcHelper.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public List<LoaiPhong> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

        @Override
    public LoaiPhong selectByID(String id) {
              List<LoaiPhong> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public LoaiPhong selectByID_1(String key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<LoaiPhong> selectByKey(String key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected List<LoaiPhong> selectBySQL(String sql, Object... args) {
              List<LoaiPhong> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(sql, args);
            while (rs.next()) {
                LoaiPhong entity = new LoaiPhong();
                entity.setMaLoaiPhong(rs.getString("MaLoaiPhong"));
                entity.setTenLoaiPhong(rs.getString("TenLoaiPhong"));
                entity.setSoGiuong(rs.getInt("SoGiuong"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }    }
    
}
