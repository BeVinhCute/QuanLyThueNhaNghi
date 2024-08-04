/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Endity.ChiTietDV;
import Endity.DichVu;
import helper.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class ChiTietDVDAO extends MainDao<ChiTietDV, String> {

    String INSERT_SQL = "INSERT INTO QLChiTietThueDV ("
            + "MaThuephong,"
            + "MaDV,"
            + "NgayDK) "
            + "values(?,?,?)";
    
    String UPDATE_SQL = "UPDATE QLChiTietThueDV "
            + "SET  MaDV=? ,"
            + "NgayDK=? "
            + "WHERE MaThuephong=?";
    String DELETE_SQL = "DELETE FROM QLChiTietThueDV "
            
            + "WHERE MaThuephong=? and madv=?";
    
    String SELECT_ALL_SQL = "SELECT * FROM QLChiTietThueDV";
    
    String SELECT_BY_ID_SQL = "SELECT * from QLChiTietThueDV WHERE MaThuephong=? ";
    
    String SELECT_BY_ID_SQL_1= "SELECT tp.mathuephong,tp.maphong,dv.tendv,GiaDV,kh.hoten,sdt,ngaydk,trangthai "
            + "FROM QLChiTietThueDV ctdv  "
            + "join QLThuePhong tp on tp.mathuephong=ctdv.mathuephong  "
            + "join qldichvu dv on dv.madv=ctdv.madv "
            + "join QLKhachHang kh on kh.makh=tp.makh "
            + "WHERE trangthai='' ";

    @Override
    public void insert(ChiTietDV entity) {
         JdbcHelper.executeUpdate(INSERT_SQL, entity.getMaThuePhong(), entity.getMaDV(), entity.getNgayDK());
    }

    @Override
    public void update(ChiTietDV entity) {
        JdbcHelper.executeUpdate(INSERT_SQL, entity.getMaDV(), entity.getNgayDK(), entity.getMaThuePhong());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public List<ChiTietDV> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public ChiTietDV selectByID(String id) {
        List<ChiTietDV> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<ChiTietDV> selectBySQL(String sql, Object... args) {
        List<ChiTietDV> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(sql, args);
            while (rs.next()) {
                ChiTietDV entity = new ChiTietDV();
                 entity.setMaThuePhong(rs.getString("MaThuePhong"));
                entity.setMaDV(rs.getString("MaDV"));
                entity.setNgayDK(rs.getDate("NgayDk"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update_1(ChiTietDV entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ChiTietDV selectByID_1(String key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ChiTietDV> selectByKey(String key) {
        List<ChiTietDV> list = this.selectBySQL(SELECT_BY_ID_SQL, key);
        if(list.isEmpty()){
            return null;
        }
        return list;
    }
    
}
