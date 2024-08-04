/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Endity.PhongTro;
import helper.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class PhongTroDao extends MainDao<PhongTro, String> {

    String INSERT_SQL = "INSERT INTO QLPhong ("
            + "MaPhong,"
            + "MaLoaiPhong,"
            + "MaLoaiGia,"
            + "GhiChu,"
            + "TinhTrang) "
            + "values(?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE QLPhong SET maloaiphong=?,maloaigia=?,ghichu=?,TinhTrang=? WHERE MaPhong =?";
    String DELETE_SQL = "DELETE FROM QLPhong WHERE MaPhong=?";
    String SELECT_ALL_SQL = "select * from QLPhong";
    String SELECT_BY_ID_SQL = "SELECT*FROM QLPhong WHERE MaPhong=?";
    String SELECT_BY_ID_SQL_1 = "Update QLPhong set TrangThai = 'Trống' where TinhTrang = 1";
    String SELECT_BY_ID_SQL_PHONGTRONG = "SELECT*FROM QLPhong WHERE Tinhtrang=1";
    String SELECT_BY_ID_SQL_DATHUE = "SELECT*FROM QLPhong WHERE Tinhtrang=2";
    String SELECT_BY_ID_SQL_ChuaDon = "SELECT*FROM QLPhong WHERE Tinhtrang=3";
    String SELECT_BY_ID_SQL_Baotri = "SELECT*FROM QLPhong WHERE Tinhtrang=4";
    String UPDATE_TRANGTHAI = "UPDATE QLPhong SET tinhtrang = ? WHERE MaPhong= ?";


    @Override
    public void insert(PhongTro entity) {
        JdbcHelper.executeUpdate(INSERT_SQL, entity.getMaPhong(), entity.getMaLoaiPhong(), entity.getMaLoaiGia(), entity.getGhiChu(), entity.getTinhTrang());
    }

    @Override
    public void update(PhongTro entity) {
        JdbcHelper.executeUpdate(UPDATE_SQL,entity.getMaLoaiPhong(),entity.getMaLoaiGia(),entity.getGhiChu(), entity.getTinhTrang(), entity.getMaPhong());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public List<PhongTro> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public PhongTro selectByID(String id) {
        List<PhongTro> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<PhongTro> selectBySQL(String sql, Object... args) {
        List<PhongTro> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(sql, args);
            while (rs.next()) {
                PhongTro entity = new PhongTro();
                entity.setMaPhong(rs.getString("MaPhong"));
                entity.setMaLoaiPhong(rs.getString("MaLoaiPhong"));
                entity.setMaLoaiGia(rs.getString("MaLoaiGia"));
                entity.setGhiChu(rs.getString("GhiChu"));
                entity.setTinhTrang(rs.getInt("TinhTrang"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update_1(PhongTro entity) {
         JdbcHelper.executeUpdate(UPDATE_TRANGTHAI,
                entity.getTinhTrang(),
                entity.getMaPhong()
        );
    }
    
//       public void update_trangthaiDonDep(PhongTro entity){
//        JdbcHelper.executeUpdate(UPDATE_TRANGTHAI,
//                entity.getTinhTrang(),
//                entity.getMaPhong()
//        );

    @Override
    public PhongTro selectByID_1(String key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<PhongTro> selectByKey(String key) {
        List<PhongTro> list = this.selectBySQL(SELECT_BY_ID_SQL_1, key);
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }

    public List<PhongTro> selectByID_MaPhong(String MAPHONG) {
        // Tạo một danh sách các đối tượng TaiKhoan từ kết quả truy vấn SQL
        List<PhongTro> list = selectBySQL(SELECT_BY_ID_SQL, MAPHONG);
        // Kiểm tra xem danh sách có trống không
        if (list.isEmpty()) {
            // Nếu danh sách trống, trả về null
            return null;
        }
        return list;
    }

    public List<PhongTro> SELECT_BY_ID_SQL_PHONGTRONG() {
        // Tạo một danh sách các đối tượng TaiKhoan từ kết quả truy vấn SQL
        List<PhongTro> list = selectBySQL(SELECT_BY_ID_SQL_PHONGTRONG);
        // Kiểm tra xem danh sách có trống không
        if (list.isEmpty()) {
            // Nếu danh sách trống, trả về null
            return null;
        }
        return list;
    }

    public List<PhongTro> SELECT_BY_ID_SQL_DATHUE() {
        // Tạo một danh sách các đối tượng TaiKhoan từ kết quả truy vấn SQL
        List<PhongTro> list = selectBySQL(SELECT_BY_ID_SQL_DATHUE);
        // Kiểm tra xem danh sách có trống không
        if (list.isEmpty()) {
            // Nếu danh sách trống, trả về null
            return null;
        }
        return list;
    }
    
        public List<PhongTro> SELECT_BY_ID_SQL_ChuaDon() {
        // Tạo một danh sách các đối tượng TaiKhoan từ kết quả truy vấn SQL
        List<PhongTro> list = selectBySQL(SELECT_BY_ID_SQL_ChuaDon);
        // Kiểm tra xem danh sách có trống không
        if (list.isEmpty()) {
            // Nếu danh sách trống, trả về null
            return null;
        }
        return list;
    }
        
                public List<PhongTro> SELECT_BY_ID_SQL_baotri() {
        // Tạo một danh sách các đối tượng TaiKhoan từ kết quả truy vấn SQL
        List<PhongTro> list = selectBySQL(SELECT_BY_ID_SQL_Baotri);
        // Kiểm tra xem danh sách có trống không
        if (list.isEmpty()) {
            // Nếu danh sách trống, trả về null
            return null;
        }
        return list;
    }

}
