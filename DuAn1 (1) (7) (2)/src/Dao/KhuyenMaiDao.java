/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Endity.DichVu;
import Endity.KhuyenMai;
import helper.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class KhuyenMaiDao extends MainDao<KhuyenMai, String>{
    String INSERT_SQL = "INSERT INTO Voucher (MaVoucher,TenVoucher,GiaTri,TrangThai) values(?,?,?,?)";
    String UPDATE_SQL = "UPDATE Voucher SET TenVoucher=?,GiaTri=?,TrangThai=? WHERE MaKM =?";
    String DELETE_SQL = "DELETE FROM Voucher WHERE MaKM=?";
    String SELECT_ALL_SQL = "SELECT * FROM Voucher";
    String SELECT_BY_ID_SQL = "SELECT*FROM Voucher WHERE MaKM=?";
    @Override
    public void insert(KhuyenMai entity) {
        JdbcHelper.executeUpdate(INSERT_SQL, entity.getMaVoucher(), entity.getTenVoucher(), entity.getGiaTri(),entity.isTrangThai());
    }

    @Override
    public void update(KhuyenMai entity) {
        JdbcHelper.executeUpdate(INSERT_SQL, entity.getTenVoucher(), entity.getGiaTri(), entity.isTrangThai(), entity.getMaVoucher());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public List<KhuyenMai> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public KhuyenMai selectByID(String id) {
         List<KhuyenMai> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<KhuyenMai> selectBySQL(String sql, Object... args) {
        List<KhuyenMai> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(sql, args);
            while (rs.next()) {
                KhuyenMai entity = new KhuyenMai();
                entity.setMaVoucher(rs.getString("MaVoucher"));
                entity.setTenVoucher(rs.getString("TenVoucher"));
                entity.setGiaTri(rs.getInt("GiaTri"));
                entity.setTrangThai(rs.getBoolean("TrangThai"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update_1(KhuyenMai entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public KhuyenMai selectByID_1(String key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<KhuyenMai> selectByKey(String key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
