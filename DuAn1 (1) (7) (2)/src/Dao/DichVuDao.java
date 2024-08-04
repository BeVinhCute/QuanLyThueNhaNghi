/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Endity.DichVu;
import helper.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class DichVuDao extends MainDao<DichVu, String>{
    String INSERT_SQL = "INSERT INTO QLDichVu (MaDV,TenDV,GiaDV,Ghichu) values(?,?,?,?)";
    String UPDATE_SQL = "UPDATE QLDichVu SET TenDV=?,GiaDV=?,Ghichu=? WHERE MaDV =?";
    String DELETE_SQL = "DELETE FROM QLDichVu WHERE MaDV=?";
    String SELECT_ALL_SQL = "SELECT * FROM QLDichVu";
    String SELECT_BY_ID_SQL = "SELECT*FROM QLDichVu WHERE MaDV = ?";
    String SELECT_BY_ID_SQL_1 = "SELECT*FROM QLDichVu WHERE TenDV like ?";
    @Override
    public void insert(DichVu entity) {
        JdbcHelper.executeUpdate(INSERT_SQL, entity.getMaDV(), entity.getTenDV(), entity.getGiaDV(), entity.getGhiChu());
    }

    @Override
    public void update(DichVu entity) {
         JdbcHelper.executeUpdate(UPDATE_SQL, entity.getTenDV(), entity.getGiaDV(), entity.getGhiChu(), entity.getMaDV());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public List<DichVu> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public DichVu selectByID(String id) {
        List<DichVu> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }
    public List<DichVu> selectByKey(String key) {
        // Tạo một danh sách các đối tượng TaiKhoan từ kết quả truy vấn SQL
        List<DichVu> list = selectBySQL(SELECT_BY_ID_SQL_1, key);
        // Kiểm tra xem danh sách có trống không
        if (list.isEmpty()) {
            // Nếu danh sách trống, trả về null
            return null;
        }

        // Nếu không, trả về phần tử đầu tiên trong danh sách
        return list;
    }

    @Override
    protected List<DichVu> selectBySQL(String sql, Object... args) {
        List<DichVu> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(sql, args);
            while (rs.next()) {
                DichVu entity = new DichVu();
                entity.setMaDV(rs.getString("MaDV"));
                entity.setTenDV(rs.getString("TenDV"));
                entity.setGiaDV(rs.getInt("GiaDV"));
                entity.setGhiChu(rs.getString("GhiChu"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update_1(DichVu entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public DichVu selectByID_1(String tendv) {
            // Tạo một danh sách các đối tượng TaiKhoan từ kết quả truy vấn SQL
        List<DichVu> list = selectBySQL(SELECT_BY_ID_SQL_1, tendv);
        // Kiểm tra xem danh sách có trống không
        if (list.isEmpty()) {
            // Nếu danh sách trống, trả về null
            return null;
        }

        // Nếu không, trả về phần tử đầu tiên trong danh sách
        return list.get(0);
    }
    
}
    

