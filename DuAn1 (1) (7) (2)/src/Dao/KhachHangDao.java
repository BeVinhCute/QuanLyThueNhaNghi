/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Endity.KhachHang;
import helper.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class KhachHangDao extends MainDao<KhachHang, String> {

    String INSERT_SQL = "INSERT INTO QLKhachHang ("
            + "MaKH,"
            + "HoTen,"
            + "DiaChi,"
            + "CCCD,"
            + "SDT,"
            + "NgaySinh,"
            + "Email) "
            + "values(?,?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE QLKhachHang SET HoTen=?,DiaChi=?,CCCD=?,SDT=?,NgaySinh=?,Email=? WHERE MaKH =?";
    String DELETE_SQL = "DELETE FROM QLKhachHang WHERE MaKH=?";
    String SELECT_ALL_SQL = "SELECT * FROM QLKhachHang";
    String SELECT_BY_ID_SQL = "SELECT*FROM QLKhachHang WHERE MaKH=?";
    String SELECT_BY_ID_SQL_1 = "SELECT*FROM QLKhachHang WHERE HoTen like?";

    @Override
    public void insert(KhachHang entity) {
        JdbcHelper.executeUpdate(INSERT_SQL, entity.getMaKH(), entity.getHoTen(), entity.getDiaChi(), entity.getCCCD(), entity.getSDT(), entity.getNgaySinh(),entity.getEmail());
    }

    @Override
    public void update(KhachHang entity) {
        JdbcHelper.executeUpdate(UPDATE_SQL, entity.getHoTen(), entity.getDiaChi(), entity.getCCCD(), entity.getSDT(),entity.getNgaySinh(),entity.getEmail(), entity.getMaKH() );
    }

    @Override
    public void delete(String id) {
        JdbcHelper.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public List<KhachHang> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public KhachHang selectByID(String id) {
        List<KhachHang> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<KhachHang> selectBySQL(String sql, Object... args) {
        List<KhachHang> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(sql, args);
            while (rs.next()) {
                KhachHang entity = new KhachHang();
                entity.setMaKH(rs.getString("MaKH"));
                entity.setHoTen(rs.getString("HoTen"));
                entity.setDiaChi(rs.getString("DiaChi"));
                entity.setCCCD(rs.getString("CCCD"));
                entity.setSDT(rs.getInt("SDT"));
                entity.setNgaySinh(rs.getDate("NgaySinh"));
                entity.setEmail(rs.getString("Email"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update_1(KhachHang entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public KhachHang selectByID_1(String tenkh) {
        // Sửa câu truy vấn để tìm kiếm dựa trên tên khách hàng
        String SELECT_BY_NAME_SQL = "SELECT * FROM QLKhachHang WHERE HoTen LIKE ?";

        List<KhachHang> list = this.selectBySQL(SELECT_BY_NAME_SQL, "%" + tenkh + "%");
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<KhachHang> selectByKey(String tenKhachHang) {
        List<KhachHang> list = new ArrayList<>();
        try {
            String SELECT_BY_KEY_SQL = "SELECT * FROM QLKhachHang WHERE HoTen LIKE ?";
            ResultSet rs = JdbcHelper.executeQuery(SELECT_BY_KEY_SQL, "%" + tenKhachHang + "%");

            while (rs.next()) {
                KhachHang entity = new KhachHang();
                entity.setMaKH(rs.getString("MaKH"));
                entity.setHoTen(rs.getString("HoTen"));
                entity.setDiaChi(rs.getString("DiaChi"));
                entity.setCCCD(rs.getString("CCCD"));
                entity.setSDT(rs.getInt("SDT"));
                entity.setNgaySinh(rs.getDate("NgaySinh"));
                entity.setEmail(rs.getString("Email"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}