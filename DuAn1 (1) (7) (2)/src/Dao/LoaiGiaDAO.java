/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Endity.LoaiGia;
import helper.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dell
 */
public class LoaiGiaDAO extends MainDao<LoaiGia, String> {

    String INSERT_SQL = "INSERT INTO LoaiGia (MaLoaiGia, GiaGio,GiaNgay) values(?,?,?)";
    String UPDATE_SQL = "UPDATE LoaiGia SET GiaGio=?,GiaNgay=? WHERE MaLoaiGia =?";
    String DELETE_SQL = "DELETE FROM LoaiGia WHERE MaLoaiGia=?";
    String SELECT_ALL_SQL = "SELECT * FROM LoaiGia";
    String SELECT_BY_ID_SQL = "SELECT*FROM LoaiGia WHERE MaLoaiGia=?";

    @Override
    public void insert(LoaiGia entity) {
        JdbcHelper.executeUpdate(INSERT_SQL, entity.getMaLoaiGia(), entity.getGiaGio(), entity.getGiaNgay());
    }

    @Override
    public void update(LoaiGia entity) {
        JdbcHelper.executeUpdate(UPDATE_SQL, entity.getGiaGio(), entity.getGiaNgay(), entity.getMaLoaiGia());
    }

    @Override
    public void update_1(LoaiGia entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String id) {
        JdbcHelper.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public List<LoaiGia> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public LoaiGia selectByID(String id) {
        List<LoaiGia> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public LoaiGia selectByID_1(String key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<LoaiGia> selectByKey(String key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected List<LoaiGia> selectBySQL(String sql, Object... args) {
        List<LoaiGia> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(sql, args);
            while (rs.next()) {
                LoaiGia entity = new LoaiGia();
                entity.setMaLoaiGia(rs.getString("MaLoaiGia"));
                entity.setGiaGio(rs.getInt("GiaGio"));
                entity.setGiaNgay(rs.getInt("GiaNgay"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
