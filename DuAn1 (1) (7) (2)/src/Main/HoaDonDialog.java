/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Main;

import Dao.ChiTietDVDAO;
import Dao.DichVuDao;
import Dao.HoaDonDAO;
import Dao.KhachHangDao;
import Dao.KhuyenMaiDao;
import Dao.LoaiGiaDAO;
import Dao.LoaiPhongDAO;
import Dao.PhongTroDao;
import Dao.PhuPhiDAO;
import Dao.ThuePhongTroDao;
import Endity.ChiTietDV;
import Endity.DichVu;
import Endity.HoaDon;
import Endity.KhachHang;
import Endity.KhuyenMai;
import Endity.LoaiGia;
import Endity.LoaiPhong;
import Endity.NhanVien;
import Endity.PhongTro;
import Endity.PhuPhi;
import Endity.TaiKhoan;
import Endity.ThuePhongTro;
import Helper.ShareHelper;
import helper.DateHelper;
import helper.DialogHelper;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admin
 */
public class HoaDonDialog extends javax.swing.JDialog {

    int row = -1;
    PhuPhiDAO ppd = new PhuPhiDAO();
    HoaDonDAO hdd = new HoaDonDAO();
    ThuePhongTroDao tpd = new ThuePhongTroDao();
    KhachHangDao khd = new KhachHangDao();
    DichVuDao dvd = new DichVuDao();
    ChiTietDVDAO ctdv = new ChiTietDVDAO();
    ThuePhongTroDao dao = new ThuePhongTroDao();// lam viec voi csdl
    PhongTroDao ptdao = new PhongTroDao();
    LoaiGiaDAO giad = new LoaiGiaDAO();
    LoaiPhongDAO lpdao = new LoaiPhongDAO();
    KhuyenMaiDao kmd = new KhuyenMaiDao();
    DichVu dvv = new DichVu();
    Date now = new Date();
    List<PhuPhi> list_pp = new ArrayList<>();
    private DefaultTableModel tblModel, tblModel1;
    private Set<PhuPhi> set_phuphi = new HashSet<>();
    int tongtienphong = 0;
    int tiendichvu = 0;
    int tienphuphi = 0;
    String mathuephong = null;
    String maphong = null;

    /**
     * Creates new form HoaDon
     */
    public HoaDonDialog(java.awt.Frame parent, boolean modal, String mathuephong, String maphong) {
        super(parent, modal);
        initComponents();
        init();
        this.mathuephong = mathuephong;
        this.maphong = maphong;
        ThuePhongTro tp = dao.selectByID(mathuephong);
        setForm(tp);
    }

    private void initTable() {
        tblModel = new DefaultTableModel();
        Object[] Column = new Object[]{"MAHD", "Mã nhân viên", "Mã thuê phòng", "Thành tiền", "MaVC", "Ngày Tạo"};
        tblModel.setColumnIdentifiers(Column);
        tblHoaDon.setModel(tblModel);

        tblModel1 = new DefaultTableModel();
        Object[] Column1 = new Object[]{"MaPP", "TenPP", "Gia"};
        tblModel1.setColumnIdentifiers(Column1);
        tblDV1.setModel(tblModel1);

    }

    private void init() {
        this.setIconImage(ShareHelper.getAppIcon());
        this.setLocationRelativeTo(null);
        title("EduSys - Quản Lý Dịch Vụ");
        initTable();
        fillcbbPP();
        fillTable();
//        fillcbbThuePhong();
        this.row = -1;
    }

    public void title(String Title) {
        setFont(new Font("System", Font.PLAIN, 14));
        Font f = getFont();
        FontMetrics fm = getFontMetrics(f);
        int x = fm.stringWidth(Title);
        int y = fm.stringWidth(" ");
        int z = getWidth() / 2 - (x / 20);
        int w = z / y;
        String pad = "";
        pad = String.format("%" + w + "s", pad);
        setTitle(pad + Title);
    }

    private String convertIntToStatus(int tinhTrang) {
        switch (tinhTrang) {
            case 1:
                return "Chưa thanh toán";
            case 2:
                return "Còn thiếu";
            case 3:
                return "Đã thanh toán";
            default:
                return "Không xác định";
        }
    }

    private void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        // đăng nhập nếu là trưởng phòng thì có thể thấy đc mật khẩu
        List<Endity.HoaDon> list = hdd.selectAll();
        try {
            for (Endity.HoaDon kh : list) {
                Object[] row = {kh.getMaHD(), kh.getMaTK(), kh.getMaThuePhong(), kh.getThanhTien(), kh.getMaVC(), kh.getNgayTao()};
                model.addRow(row);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
//    public void fillcbbThuePhong() {
//        try {
//            DefaultComboBoxModel model = (DefaultComboBoxModel) cbbTP.getModel();
//            model.removeAllElements();
//            List<ThuePhongTro> list = tpd.selectAll();
//            for (ThuePhongTro dv : list) {
//                if (dv.getTrangThai() == 1) {
//                model.addElement(dv.getMaThuePhong());
//            }
//            }
//            cbbTP.setSelectedIndex(0);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }

    public void fillcbbPP() {
        try {
            DefaultComboBoxModel model = (DefaultComboBoxModel) cbbPP.getModel();
            model.removeAllElements();
            List<PhuPhi> list = ppd.selectAll();
            for (PhuPhi pp : list) {
                if (pp.isTrangThai() == true) {
                    model.addElement(pp.getTenPP());
                }
            }
            cbbPP.setSelectedIndex(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Set<PhuPhi> chonPhuPhi(String TenPhuPhi) {
        try {
            PhuPhi dv = ppd.selectByID_1(TenPhuPhi);
            boolean flag = false;
            for (PhuPhi dichVu : list_pp) {
                if (dichVu.getTenPP().equals(TenPhuPhi)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                list_pp.add(dv);

            } else {
                DialogHelper.alert(this, "Phụ phí đã được chọn");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return set_phuphi;
    }

    public void filltablePhuPhi() {
        DefaultTableModel model = (DefaultTableModel) tblDV1.getModel();
        model.setRowCount(0);
        try {

            for (PhuPhi dv : list_pp) {
                Object[] row = {dv.getMaPP(),
                    dv.getTenPP(),
                    dv.getGiaTri(),};
                model.addRow(row);
                tienphuphi += dv.getGiaTri();
            }
            txtTienPhụPhi.setText(String.valueOf(tienphuphi));
        } catch (Exception e) {
        }
    }

    public void setForm(ThuePhongTro tp) {//,KhachHang kh,LoaiPhong lp,LoaiGia gia){
        try {
            KhachHang kh = khd.selectByID(tp.getMaKH());
            PhongTro p = ptdao.selectByID(tp.getMaPhong());
            LoaiGia gia = giad.selectByID(p.getMaLoaiGia());
            LoaiPhong lp = lpdao.selectByID(p.getMaLoaiPhong());
            txtMaPhong.setText(p.getMaPhong());
            txtTenLP.setText(lp.getTenLoaiPhong());
            txtGiaGio.setText(String.valueOf(gia.getGiaGio()));
            txtGiaNgay.setText(String.valueOf(gia.getGiaNgay()));
            txtMaTP.setText(tp.getMaThuePhong());
            txtMaKH.setText(kh.getMaKH());
            txttenKH.setText(kh.getHoTen());
            txtSDT.setText(String.valueOf(kh.getSDT()));
            txtNgayThue.setText(DateHelper.toString(tp.getNgayThue(), "yyyy-MM-dd HH:mm:ss"));
            txtNgayTra.setText(DateHelper.toString(tp.getNgayTra(), "yyyy-MM-dd HH:mm:ss"));
            long diffInMillies = Math.abs(tp.getNgayTra().getTime() - tp.getNgayThue().getTime());
            long diffHours = diffInMillies / (60 * 60 * 1000);
            System.out.println(gia.getGiaGio());
            if (diffHours > 24) {
                int ngay = 0;
                int sogio = 0;
                if (diffHours % 24 == 0) {
                    ngay = (int) diffHours / 24;
                } else {
                    ngay = (int) diffHours / 24;
                    sogio = (int) diffHours % 24;
                }
                txtTGThue.setText(String.valueOf(ngay) + "Ngày" + sogio + "Giờ");
                tongtienphong = (int) ngay * gia.getGiaNgay() + sogio * gia.getGiaGio();
                txtTienPhong.setText(String.valueOf(tongtienphong));
            } else {
                txtTGThue.setText(String.valueOf(diffHours) + "Giờ");
                tongtienphong = (int) diffHours * gia.getGiaGio();
                txtTienPhong.setText(String.valueOf(tongtienphong));

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void fillTableDV(ChiTietDV dv) {
        DefaultTableModel model = (DefaultTableModel) tblDV.getModel();
        model.setRowCount(0);

        try {

            List<ChiTietDV> list = ctdv.selectByKey(dv.getMaThuePhong());
            if (list != null) {
                for (ChiTietDV ctdv : list) {
                    DichVu dvv = dvd.selectByID(ctdv.getMaDV());
                    Object[] row = {
                        dvv.getTenDV(),
                        dvv.getGiaDV(),
                        ctdv.getNgayDK(),};
                    model.addRow(row);

                    // Accumulate the total
                    tiendichvu += dvv.getGiaDV();
                }

                // Set the total to txtTienDv
                txtTienDv.setText(String.valueOf(tiendichvu));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void ThanhTien() {
        int tongTien = 0; // Biến để tính tổng tiền từ các trường
        tongTien = tongtienphong + tiendichvu + tienphuphi;
        // Set the total to the text field outside the try-catch block
        txtThanhTien.setText(String.valueOf(tongTien));
    }

    Endity.HoaDon getForm() {
        if(check_input()){
         Endity.HoaDon hd = new Endity.HoaDon();
        TaiKhoan tk = Helper.ShareHelper.user;
        hd.setMaHD(txtMaHD.getText());
        hd.setMaThuePhong(txtMaTP.getText());
        hd.setMaTK(tk.getMaTK());
        hd.setThanhTien(Integer.valueOf(txtThanhTien.getText()));
        hd.setMaVC(txtMaVC.getText());
        hd.setNgayTao(DateHelper.now());
        return hd;
        } else {
            DialogHelper.alert(this, "Lỗi Dữ Liệu");
            return null;
        }
    }

    void clearForm() {//xoa trang form
        ThuePhongTro nv = new ThuePhongTro();
        this.setForm(nv);
        this.row = -1;
    }

    void insert() {
        Endity.HoaDon hd = getForm();
        PhongTro pt = new PhongTro();
        ThuePhongTro tp = new ThuePhongTro();
        if (tp != null) {
            try {
                hdd.insert(hd);
                this.fillTable();
                pt.setTinhTrang(4);
                tp.setTrangThai(3);
                this.clearForm();
                DialogHelper.alert(this, "Thêm mới thành công !");
            }
            catch (Exception e) {
            }
        } else {
            return;
        }
    }
    
           private boolean check_input() {                                                                                                                                                                                                                                                                                         
        if (txtMaHD.getText().length() > 20) {
            DialogHelper.alert(this, " mã hóa đơn Không nhập quá 20 ký tự");
            return false;
        }if(txtMaHD.getText().equals("")){
               DialogHelper.alert(this, "mã hóa đơn Không được để trống");
            return false; 
        }
               try {
                   
                   HoaDon hd = hdd.selectByID_mahd(txtMaHD.getText());
                   if(hd != null){
                       DialogHelper.alert(this, "Mã hóa đơn đã tồn tại");
                       return false;
                   } 
                   
               } catch (Exception e) {
                   e.printStackTrace();
               }
               try {
                   HoaDon tp = hdd.selectByID(txtMaTP.getText());
                   if(tp != null){
                       DialogHelper.alert(this, "Mã thuê phòng đã được thanh toán");
                       return false;
                   }  
               } catch (Exception e) {
                  e.printStackTrace();
               }
        return true;
    }
    

    public void updateTraPhong(String mtp) {
        String matp = txtMaTP.getText();
        if (matp == null) {
            DialogHelper.alert(this, "Vui Lòng Chọn Mã thuê Phòng");
        }
        ThuePhongTro tp = tpd.selectByID(matp);
        if (tp.getTrangThai() == 1 || tp.getTrangThai() == 2) {
            System.out.println("1");
            ThuePhongTro tpt = new ThuePhongTro();
            tpt.setMaThuePhong(matp);
            tpt.setTrangThai(3);
            tpd.update_1(tpt);
        }
    }

    public void TTDonDep(String mp) {
        PhongTro p = ptdao.selectByID(mp);
        if (p.getTinhTrang() == 2) {
            PhongTro pt = new PhongTro();
            pt.setMaPhong(mp);
            pt.setTinhTrang(3);
            ptdao.update_1(pt);
        }
    }
    public void voucher(){
       String mavc=txtMaVC.getText();
        try {
            KhuyenMai km = kmd.selectByID(mavc);
            txtTienGiam.setText(String.valueOf(km.getGiaTri()));
        } catch (Exception e) {
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtMaTP = new javax.swing.JTextField();
        txtMaHD = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtMaPhong = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtGiaGio = new javax.swing.JTextField();
        txtTenLP = new javax.swing.JTextField();
        txtGiaNgay = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        txtMaKH = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txttenKH = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        txtNgayThue = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtNgayTra = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtTGThue = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtTienGiam = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtThanhTien = new javax.swing.JTextField();
        txtTienDv = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTienPhong = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtTienPhụPhi = new javax.swing.JTextField();
        txtMaVC = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDV = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDV1 = new javax.swing.JTable();
        cbbPP = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        btnThanhToan = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 255, 255));
        jLabel1.setText("HÓA ĐƠN ");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(483, 0, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1919, -1));

        jPanel3.setBackground(new java.awt.Color(204, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin hóa đơn\n"));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Mã Hóa Đơn:");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 27, -1, -1));

        jLabel9.setText("Mã thuê phòng:");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 55, -1, -1));
        jPanel3.add(txtMaTP, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 52, 140, -1));
        jPanel3.add(txtMaHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 24, 200, -1));

        btnTimKiem.setText("Tìm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });
        jPanel3.add(btnTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 50, 50, -1));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin phòng"));

        jLabel11.setText("Giá 1 ngày:");

        txtMaPhong.setEnabled(false);

        jLabel12.setText("Mã phòng:");

        jLabel13.setText("Loại phòng:");

        jLabel10.setText("Giá 1 giờ:");

        txtGiaGio.setEnabled(false);

        txtTenLP.setEnabled(false);

        txtGiaNgay.setEnabled(false);
        txtGiaNgay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaNgayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel12)
                    .addComponent(jLabel10))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(txtGiaGio, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtGiaNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 47, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaPhong, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                            .addComponent(txtTenLP))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12)
                    .addComponent(txtMaPhong))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(txtTenLP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtGiaGio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtGiaNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        jPanel3.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 87, 290, -1));

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin khách hàng"));

        txtMaKH.setEnabled(false);
        txtMaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaKHActionPerformed(evt);
            }
        });

        jLabel17.setText("Mã khách hàng:");

        jLabel18.setText("Họ Tên:");

        txttenKH.setEnabled(false);

        jLabel25.setText("SĐT:");

        txtSDT.setEnabled(false);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel25)
                    .addComponent(jLabel18)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txttenKH, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                    .addComponent(txtMaKH)
                    .addComponent(txtSDT, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txttenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 290, -1));

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin thuê phòng"));

        txtNgayThue.setEnabled(false);
        txtNgayThue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayThueActionPerformed(evt);
            }
        });

        jLabel23.setText("Ngày thuê:");

        jLabel24.setText("Ngày trả:");

        txtNgayTra.setEnabled(false);

        jLabel19.setText("Thời gian thuê:");

        txtTGThue.setEnabled(false);
        txtTGThue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTGThueActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel19)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNgayTra, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                    .addComponent(txtNgayThue, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTGThue, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtNgayThue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNgayTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(txtTGThue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 290, -1));

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Thành tiên"));

        jLabel5.setText("Voucher");

        txtTienGiam.setEnabled(false);
        txtTienGiam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienGiamActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel14.setText("Thành tiền:");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setText("VNĐ");

        txtThanhTien.setEditable(false);
        txtThanhTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtThanhTienActionPerformed(evt);
            }
        });

        txtTienDv.setEditable(false);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Tổng tiền dịch vụ:");

        txtTienPhong.setEnabled(false);
        txtTienPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienPhongActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setText("Tổng tiền phòng:");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setText("Tổng tiền phụ phí:");

        txtTienPhụPhi.setEnabled(false);
        txtTienPhụPhi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienPhụPhiActionPerformed(evt);
            }
        });

        txtMaVC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaVCActionPerformed(evt);
            }
        });

        jLabel6.setText("Số tiền giảm:");

        jButton5.setText("Tìm");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTienDv))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(txtMaVC)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtTienGiam)
                            .addComponent(txtTienPhụPhi)
                            .addComponent(txtTienPhong, javax.swing.GroupLayout.Alignment.LEADING))))
                .addGap(50, 50, 50))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaVC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTienGiam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(txtTienPhụPhi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTienPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTienDv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15)))
                .addGap(0, 40, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 400, 343, 230));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Thuê dịch vụ"));

        tblDV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Tên dịch vụ", "Giá ", "Ngày đăng ký"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblDV);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
        );

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, 330, 171));

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Phụ phí"));

        tblDV1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tblDV1);

        cbbPP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton4.setText("Thêm phụ phí");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(cbbPP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addGap(0, 149, Short.MAX_VALUE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbPP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 220, 340, -1));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách hóa đơn"));

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblHoaDon);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 579, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(103, 103, 103))
        );

        jPanel3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 30, 600, 500));

        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnThanhToan.setText("Thanh Toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });
        jPanel3.add(btnThanhToan, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 540, 140, 90));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 1400, 680));

        jButton1.setText("Sửa HD");
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1673, 1307, 111, 40));

        jButton2.setText("Thêm HD");
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1544, 1307, 111, 40));

        jButton3.setText("Xóa HD");
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1802, 1307, 111, 40));
        jPanel1.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1451, 1240, 151, -1));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Thành tiền");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1375, 1243, 64, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1270, 710));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed

        String matp = txtMaTP.getText();
        try {
            ThuePhongTro tp = dao.selectByID(matp);
            ChiTietDV dv = ctdv.selectByID(tp.getMaThuePhong());
            KhachHang kh = khd.selectByID(tp.getMaKH());
            PhongTro p = ptdao.selectByID(tp.getMaPhong());
            LoaiGia gia = giad.selectByID(p.getMaLoaiGia());
            LoaiPhong lp = lpdao.selectByID(p.getMaLoaiPhong());
            setForm(tp);
            txtMaPhong.setText(p.getMaPhong());
            txtGiaGio.setText(String.valueOf(gia.getGiaGio()));
            txtGiaNgay.setText(String.valueOf(gia.getGiaNgay()));
            txtTenLP.setText(lp.getTenLoaiPhong());
            txttenKH.setText(kh.getHoTen());
            txtSDT.setText(String.valueOf(kh.getSDT()));
            txtMaKH.setText(kh.getMaKH());
            fillTableDV(dv);
            ThanhTien();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void txtGiaNgayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaNgayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaNgayActionPerformed

    private void txtMaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaKHActionPerformed

    private void txtNgayThueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayThueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayThueActionPerformed

    private void txtTGThueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTGThueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTGThueActionPerformed

    private void txtTienPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienPhongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienPhongActionPerformed

    private void txtThanhTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtThanhTienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtThanhTienActionPerformed

    private void txtTienGiamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienGiamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienGiamActionPerformed

    private void txtTienPhụPhiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienPhụPhiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienPhụPhiActionPerformed

    private void txtMaVCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaVCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaVCActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        voucher();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed

        insert();
        updateTraPhong(mathuephong);
        TTDonDep(maphong);
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        String tenpp = cbbPP.getSelectedItem().toString();
        chonPhuPhi(tenpp);
        filltablePhuPhi();
        ThanhTien();
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbbPP;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTable tblDV;
    private javax.swing.JTable tblDV1;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTextField txtGiaGio;
    private javax.swing.JTextField txtGiaNgay;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtMaPhong;
    private javax.swing.JTextField txtMaTP;
    private javax.swing.JTextField txtMaVC;
    private javax.swing.JTextField txtNgayThue;
    private javax.swing.JTextField txtNgayTra;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTGThue;
    private javax.swing.JTextField txtTenLP;
    private javax.swing.JTextField txtThanhTien;
    private javax.swing.JTextField txtTienDv;
    private javax.swing.JTextField txtTienGiam;
    private javax.swing.JTextField txtTienPhong;
    private javax.swing.JTextField txtTienPhụPhi;
    private javax.swing.JTextField txttenKH;
    // End of variables declaration//GEN-END:variables
}
