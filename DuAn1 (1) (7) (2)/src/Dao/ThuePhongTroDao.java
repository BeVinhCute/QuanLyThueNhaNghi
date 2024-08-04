/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Endity.PhongTro;
import Endity.ThuePhongTro;
import helper.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class ThuePhongTroDao extends MainDao<ThuePhongTro, String> {

     String INSERT_SQL = "INSERT INTO QLThuePhong (MaThuePhong,MaKH,MaPhong,NgayThue,trangthai) values(?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE QLThuePhong SET MaKH=?,MaPhong=?,NgayThue=?,TrangThai=? WHERE MaThuePhong =?";
    String DELETE_SQL = "DELETE FROM ThuePhong WHERE MaThuePhong=?";
    String SELECT_ALL_SQL = "SELECT * FROM QLThuePhong";
    String SELECT_BY_ID_SQL = "SELECT*FROM QLThuePhong WHERE MaThuePhong=?";
    String SELECT_BY_TenKH_SQL_ = "SELECT MaThuePhong,kh.Makh,hoten,maphong,ngaythue,trangthai FROM QLThuePhong TP JOIN QLKhachHang KH ON TP.MaKH = KH.Makh WHERE KH.HoTen LIKE ?";
    String SELECT_BY_ID_SQL_DANGTHUE = "SELECT MaThuePhong,kh.Makh,hoten,maphong,ngaythue,trangthai FROM QLThuePhong TP JOIN QLKhachHang KH ON TP.MaKH = KH.Makh WHERE trangthai = '1'";
    String SELECT_BY_ID_SQL_DATRA = "SELECT MaThuePhong,kh.Makh,hoten,maphong,ngaythue,trangthai FROM QLThuePhong TP JOIN QLKhachHang KH ON TP.MaKH = KH.Makh WHERE trangthai ='3' ";
    String SELECT_BY_ID_SQL_HETNGAYTHUE = "SELECT MaThuePhong,kh.Makh,hoten,maphong,ngaythue,trangthai FROM QLThuePhong TP JOIN QLKhachHang KH ON TP.MaKH = KH.Makh WHERE  trangthai='2'";
    String SELECT_BY_ID_SQL_hhachHang = "SELECT MaKH FROM KhachHang WHERE MaKH NOT IN (SELECT DISTINCT MaKH FROM ThuePhongTro)";
    String UPDATE_TRANGTHAI = "UPDATE QLThuePhong SET TrangThai = ? WHERE MaThuePhong= ?";
    @Override
    public void insert(ThuePhongTro entity) {
        JdbcHelper.executeUpdate(INSERT_SQL, entity.getMaThuePhong(), entity.getMaKH(), entity.getMaPhong(),entity.getNgayThue(),entity.getTrangThai()); //,entity.getNgayThue());
    }

    @Override
    public void update(ThuePhongTro entity) {
        JdbcHelper.executeUpdate(UPDATE_SQL, entity.getMaKH(), entity.getMaPhong(), entity.getNgayThue(), entity.getNgayTra(), entity.getTrangThai(), entity.getMaThuePhong());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public List<ThuePhongTro> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public ThuePhongTro selectByID(String id) {
        List<ThuePhongTro> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<ThuePhongTro> selectBySQL(String sql, Object... args) {
        List<ThuePhongTro> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(sql, args);
            while (rs.next()) {
                ThuePhongTro entity = new ThuePhongTro();
                entity.setMaThuePhong(rs.getString("MaThuePhong"));
                entity.setMaKH(rs.getString("MaKH"));
                entity.setMaPhong(rs.getString("MaPhong"));
                entity.setNgayThue(rs.getTimestamp("NgayThue"));
                entity.setTrangThai(rs.getInt("TrangThai"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update_1(ThuePhongTro entity) {
             JdbcHelper.executeUpdate(UPDATE_TRANGTHAI,
                entity.getTrangThai(),
                entity.getMaThuePhong()
        );
    }

    @Override
    public ThuePhongTro selectByID_1(String tenkhachhang) {
        List<ThuePhongTro> list = selectBySQL(SELECT_BY_TenKH_SQL_, "%" + tenkhachhang + "%");
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<ThuePhongTro> selectByKey(String tenKhachHang) {
        String SELECT_BY_NAME_SQL = "SELECT TDV.* FROM QLThuePhong TDV JOIN KhachHang KH ON TDV.MaKH = KH.MaKH WHERE KH.HoTen LIKE ?";

        return this.selectBySQL(SELECT_BY_NAME_SQL, "%" + tenKhachHang + "%");
    }

    public List<ThuePhongTro> selectByID_TenKH(String TENKH) {
        // Tạo một danh sách các đối tượng TaiKhoan từ kết quả truy vấn SQL
        List<ThuePhongTro> list = selectBySQL(SELECT_BY_TenKH_SQL_, TENKH);
        // Kiểm tra xem danh sách có trống không
        if (list.isEmpty()) {
            // Nếu danh sách trống, trả về null
            return null;
        }
        return list;
    }
    
            public List<ThuePhongTro> SELECT_ALL_SQL() {
        // Tạo một danh sách các đối tượng TaiKhoan từ kết quả truy vấn SQL
        List<ThuePhongTro> list = selectBySQL(SELECT_ALL_SQL);
        // Kiểm tra xem danh sách có trống không
        if (list.isEmpty()) {
            // Nếu danh sách trống, trả về null
            return null;
        }
        return list;
    }
    
        public List<ThuePhongTro> SELECT_BY_ID_SQL_DANGTHUE() {
        // Tạo một danh sách các đối tượng TaiKhoan từ kết quả truy vấn SQL
        List<ThuePhongTro> list = selectBySQL(SELECT_BY_ID_SQL_DANGTHUE);
        // Kiểm tra xem danh sách có trống không
        if (list.isEmpty()) {
            // Nếu danh sách trống, trả về null
            return null;
        }
        return list;
    }
        
        public List<ThuePhongTro> SELECT_BY_ID_SQL_DATRA() {
        // Tạo một danh sách các đối tượng TaiKhoan từ kết quả truy vấn SQL
        List<ThuePhongTro> list = selectBySQL(SELECT_BY_ID_SQL_DATRA);
        // Kiểm tra xem danh sách có trống không
        if (list.isEmpty()) {
            // Nếu danh sách trống, trả về null
            return null;
        }
        return list;
    }
        
        public List<ThuePhongTro> SELECT_BY_ID_SQL_HETNGAYTHUE() {
        // Tạo một danh sách các đối tượng TaiKhoan từ kết quả truy vấn SQL
        List<ThuePhongTro> list = selectBySQL(SELECT_BY_ID_SQL_HETNGAYTHUE);
        // Kiểm tra xem danh sách có trống không
        if (list.isEmpty()) {
            // Nếu danh sách trống, trả về null
            return null;
        }
        return list;
    }
        public List<ThuePhongTro> SELECT_BY_ID_SQL_KH() {
        // Tạo một danh sách các đối tượng TaiKhoan từ kết quả truy vấn SQL
        List<ThuePhongTro> list = selectBySQL(SELECT_BY_ID_SQL_hhachHang);
        // Kiểm tra xem danh sách có trống không
        if (list.isEmpty()) {
            // Nếu danh sách trống, trả về null
            return null;
        }
        return list;
    }

}
