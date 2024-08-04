/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Endity.TaiKhoan;
import helper.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class TaiKhoanDao extends MainDao<TaiKhoan, String>{
    String INSERT_SQL = "INSERT INTO QLTaiKhoan (MaTK,TenTK,MatKhau,VaiTro) values(?,?,?,?)";
    String UPDATE_SQL = "UPDATE QLTaiKhoan SET TenTK=?,MatKhau=?,VaiTro=? WHERE MaTK =?";
    String DELETE_SQL = "DELETE FROM QLTaiKhoan WHERE MaTK=?";
    String SELECT_ALL_SQL = "SELECT * FROM QLTaiKhoan";
    String SELECT_BY_ID_SQL = "SELECT*FROM QLTaiKhoan WHERE TenTK=?";

    @Override
    public void insert(TaiKhoan entity) {
        JdbcHelper.executeUpdate(INSERT_SQL, entity.getMaTK(), entity.getTenTk(), entity.getMatKhau(), entity.isVaiTro());
    }

    @Override
    public void update(TaiKhoan entity) {
         JdbcHelper.executeUpdate(UPDATE_SQL, entity.getTenTk(), entity.getMatKhau(), entity.isVaiTro(), entity.getMaTK());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public List<TaiKhoan> selectAll() {
         return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public TaiKhoan selectByID(String id) {
         List<TaiKhoan> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    
    }

    @Override
    protected List<TaiKhoan> selectBySQL(String sql, Object... args) {
        List<TaiKhoan> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(sql, args);
            while (rs.next()) {
                TaiKhoan tk = new TaiKhoan();
                tk.setMaTK(rs.getString("MaTK"));  
                tk.setTenTk(rs.getString("TenTK"));
                tk.setMatKhau(rs.getString("MatKhau"));
                tk.setVaiTro(rs.getBoolean("VaiTro"));
                list.add(tk);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update_1(TaiKhoan entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public TaiKhoan selectByID_1(String key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<TaiKhoan> selectByKey(String key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
