/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Endity.HoaDon;
import Endity.HoaDon;
import Endity.ThuePhongTro;
import helper.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dell
 */
public class HoaDonDAO extends MainDao<HoaDon, Object>{
    String INSERT_SQL = "INSERT INTO QLHoaDon values(?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE QLHoaDon SET MaTK=?,MaTP=?,ThanhTien=?,MaVC=?,NgayTao=?,TrangThai=?WHERE MaHD =?";
    String DELETE_SQL = "DELETE FROM QLHoaDon WHERE MaHD=?";
    String SELECT_ALL_SQL = "SELECT * FROM QLHoaDon";
    String SELECT_BY_ID_SQL = "SELECT*FROM QLHoaDon WHERE MaHD=?";
    String SELECT_BY_ID_SQL_1 = "select * from QLHoaDon where MaHD =?";
    
    @Override
    public void insert(HoaDon entity) {
      JdbcHelper.executeUpdate(INSERT_SQL, entity.getMaHD(),entity.getMaTK(), entity.getMaThuePhong(), entity.getThanhTien(), entity.getMaVC(), entity.getNgayTao());    }

    @Override
    public void update(HoaDon entity) {
       JdbcHelper.executeUpdate(UPDATE_SQL, entity.getMaTK(),entity.getMaThuePhong(), entity.getKhachTra(), entity.getMaHD());      }

    @Override
    public void update_1(HoaDon entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Object id) {
       JdbcHelper.executeUpdate(DELETE_SQL, id);    }

    @Override
    public List<HoaDon> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public HoaDon selectByID(Object key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public HoaDon selectByID_1(Object key) {
         List<HoaDon> list = this.selectBySQL(SELECT_BY_ID_SQL, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<HoaDon> selectByKey(Object key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected List<HoaDon> selectBySQL(String sql, Object... args) {
                       List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(sql, args);
            while (rs.next()) {
                HoaDon cthd = new HoaDon();
                cthd.setMaHD(rs.getString("MaHD"));
                cthd.setMaTK(rs.getString("MaTK")); 
                cthd.setMaThuePhong(rs.getString("MaThuePhong"));
                cthd.setThanhTien(rs.getInt("ThanhTien"));
                cthd.setMaVC(rs.getString("MaVC"));
                cthd.setNgayTao(rs.getDate("NgayTao"));
                list.add(cthd);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            e.getMessage();
            throw new RuntimeException(e);
        }     }
    
        public HoaDon selectByID_mahd(String key) {
         List<HoaDon> list = this.selectBySQL(SELECT_BY_ID_SQL, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
