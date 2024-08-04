/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;



import helper.JdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dell
 */
public class ThongKeDAO  {
    
        public List<Object[]> getListOfArray(String sql, String[] cols, Object... args) {
        try {
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = JdbcHelper.executeQuery(sql, args);
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public static ResultSet rs = null;


    public List<Object[]> getDoanhThu(int thang,int nam) {
        String sql = "{CALL sp_DoanhThuTheoThang(?,?)}";
        String[] cols = {"Số Hóa Đơn", "Doanh thu tháng"};
        return this.getListOfArray(sql, cols,thang,nam);
    }
    
        public List<Object[]> getHoaDon(int thang,int nam) {
        String sql = "{CALL sp_HoaDonTheoThang(?,?)}";
        String[] cols = {"MaHD", "HoTen","MaPhong","ThanhTien","NgayTao"};
        return this.getListOfArray(sql, cols,thang,nam);
    }
    
       public List<Object[]> getDoanhThuNam(int nam) {
        String sql = "{CALL sp_DoanhThuTheoNam(?)}";
        String[] cols = {"Số Hóa Đơn", "Doanh thu năm"};
        return this.getListOfArray(sql, cols,nam);
    }
   

    public List<Integer> selectMonth() {
        String sql = "SELECT DISTINCT Month(NgayTao) month FROM QLHoaDon ORDER BY month ASC";
        List<Integer> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(sql);
            while (rs.next()) {
                list.add(rs.getInt(1));
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
        public List<Integer> selectYear() {
        String sql = "SELECT DISTINCT Year(NgayTao) year FROM QLHoaDon ORDER BY year ASC";
        List<Integer> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(sql);
            while (rs.next()) {
                list.add(rs.getInt(1));
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
