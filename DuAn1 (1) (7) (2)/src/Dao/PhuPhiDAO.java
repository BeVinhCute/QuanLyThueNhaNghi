/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Endity.DichVu;
import Endity.PhuPhi;
import helper.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class PhuPhiDAO extends MainDao<PhuPhi, String>{
 String INSERT_SQL = "INSERT INTO QLPhuPhi ("
            + "MaPP,"
            + "TenPP,"
            + "GiaTri"
            +"GhiChu"
            +"TrangThai)"
            + "values(?,?,?,?,?)";
    
    String UPDATE_SQL = "UPDATE QLPhuPhi "
            + "SET  MaPP=? ,"
            + "TenPP=? "
            + "GiaTri=? "
            + "GhiChu=? "
             + "TrangThai=? "
            + "WHERE MaThuephong=?";
    String DELETE_SQL = "DELETE FROM QLPhuPhi "
            
            + "WHERE MaHD=? and MaPP=?";
    
    String SELECT_ALL_SQL = "SELECT * FROM QLPhuPhi";
    
    String SELECT_BY_ID_SQL = "SELECT * from QLPhuPhi WHERE MaPP=? ";
    String SELECT_BY_ID_SQL_1 = "SELECT*FROM QLPhuPhi WHERE TenPP like ?";
    @Override
    public void insert(PhuPhi entity) {
        JdbcHelper.executeUpdate(INSERT_SQL, entity.getMaPP(), entity.getMaPP(), entity.getGiaTri(),entity.getGhiChu(),entity.isTrangThai());
    }

    @Override
    public void update(PhuPhi entity) {
        JdbcHelper.executeUpdate(UPDATE_SQL, entity.getMaPP(), entity.getGiaTri(),entity.getGhiChu(),entity.isTrangThai(), entity.getMaPP());
    }

    @Override
    public void update_1(PhuPhi entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String id) {
        JdbcHelper.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public List<PhuPhi> selectAll() {
       return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public PhuPhi selectByID(String id) {
        List<PhuPhi> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public PhuPhi selectByID_1(String tenpp) {
        List<PhuPhi> list = selectBySQL(SELECT_BY_ID_SQL_1, tenpp);
        // Kiểm tra xem danh sách có trống không
        if (list.isEmpty()) {
            // Nếu danh sách trống, trả về null
            return null;
        }

        // Nếu không, trả về phần tử đầu tiên trong danh sách
        return list.get(0);
    }
    


    @Override
    public List<PhuPhi> selectByKey(String key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected List<PhuPhi> selectBySQL(String sql, Object... args) {
        List<PhuPhi> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(sql, args);
            while (rs.next()) {
                PhuPhi entity = new PhuPhi();
                 entity.setMaPP(rs.getString("MaPP"));
                entity.setTenPP(rs.getString("TenPP"));
                entity.setGiaTri(rs.getInt("GiaTri"));
                entity.setGhiChu(rs.getString("GhiChu"));
                entity.setTrangThai(rs.getBoolean("TrangThai"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
