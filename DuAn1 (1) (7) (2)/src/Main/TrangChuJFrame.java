/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Main;

import Dao.LoaiGiaDAO;
import Dao.LoaiPhongDAO;
import Dao.PhongTroDao;
import Endity.LoaiGia;
import Endity.LoaiPhong;
import Endity.PhongTro;
import Endity.ThuePhongTro;
import Helper.ShareHelper;
import helper.DialogHelper;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admin
 */
public class TrangChuJFrame extends javax.swing.JFrame {

    int row = -1;
    PhongTroDao dao = new PhongTroDao();
    LoaiPhongDAO lpd = new LoaiPhongDAO();
    LoaiGiaDAO gia = new LoaiGiaDAO();
    PhongTro pt = new PhongTro();

    private DefaultTableModel tblModel;

    /**
     * Creates new form NewJFrame
     */
    public TrangChuJFrame() {
        initComponents();
        init();
    }

    private void initTable() {
        tblModel = new DefaultTableModel();
        Object[] Column = new Object[]{"Mã Phòng", "Loại Phòng", "Giá giờ", "Giá ngày", "Ghi chú", "Tình Trạng"};
        tblModel.setColumnIdentifiers(Column);
        tblPhong.setModel(tblModel);

    }

    private void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblPhong.getModel();
        model.setRowCount(0);
        // đăng nhập nếu là trưởng phòng thì có thể thấy đc mật khẩu
        List<PhongTro> list = dao.selectAll();
        try {
            for (PhongTro pt : list) {
                LoaiPhong lp = lpd.selectByID(pt.getMaLoaiPhong());
                LoaiGia g = gia.selectByID(pt.getMaLoaiGia());
                Object[] row = {pt.getMaPhong(), lp.getTenLoaiPhong(), g.getGiaGio(), g.getGiaNgay(), pt.getGhiChu(), convertIntToStatus(pt.getTinhTrang())};
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    private void fillTablePhongChuaDon() {
        DefaultTableModel model = (DefaultTableModel) tblPhong.getModel();
        model.setRowCount(0);
        try {
            List<PhongTro> list = dao.SELECT_BY_ID_SQL_ChuaDon();
            for (PhongTro pt : list) {
                LoaiPhong lp = lpd.selectByID(pt.getMaLoaiPhong());
                LoaiGia g = gia.selectByID(pt.getMaLoaiGia());
                Object[] row = {pt.getMaPhong(),
                    lp.getTenLoaiPhong(),
                    g.getGiaGio(),
                    g.getGiaNgay(),
                    pt.getGhiChu(),
                    convertIntToStatus(pt.getTinhTrang())
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    private void fillTablePhongBaoTri() {
        DefaultTableModel model = (DefaultTableModel) tblPhong.getModel();
        model.setRowCount(0);
        try {
            List<PhongTro> list = dao.SELECT_BY_ID_SQL_baotri();
            for (PhongTro pt : list) {
                LoaiPhong lp = lpd.selectByID(pt.getMaLoaiPhong());
                LoaiGia g = gia.selectByID(pt.getMaLoaiGia());
                Object[] row = {pt.getMaPhong(),
                    lp.getTenLoaiPhong(),
                    g.getGiaGio(),
                    g.getGiaNgay(),
                    pt.getGhiChu(),
                    convertIntToStatus(pt.getTinhTrang())
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    private String convertIntToStatus(int tinhTrang) {
        switch (tinhTrang) {
            case 1:
                return "Trống";
            case 2:
                return "Đang sử dụng";
            case 3:
                return "Chưa dọn dẹp";
            case 4:
                return "Bảo trì";
            default:
                return "Không xác định";
        }
    }

    public void get_Times() {
        new Timer(1000, new ActionListener() {
            SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss a");

            @Override
            public void actionPerformed(ActionEvent e) {
                lblDongHo.setText(format.format(new Date()));
            }
        }).start();
    }

    public void title(String Title) {
        setFont(new Font("System", Font.PLAIN, 14));
        Font f = getFont();
        FontMetrics fm = getFontMetrics(f);
        int x = fm.stringWidth(Title);
        int y = fm.stringWidth(" ");
        int z = getWidth() / 2 - (x / 7);
        int w = z / y;
        String pad = "";
        pad = String.format("%" + w + "s", pad);
        setTitle(pad + Title);
    }

    private void init() {
        this.setIconImage(ShareHelper.getAppIcon());
        this.setLocationRelativeTo(null);
        title("Hệ Thống Quản Lý Nhà Nghỉ");
        get_Times();
        initTable();
        fillTable();
        new Chao(this, true).setVisible(true);
        new DangNhap(this, true).setVisible(true);

    }

    void dangXuat() {// xoa thong tin dang nhap khi log out
        ShareHelper.logoff();
        new DangNhap(this, true).setVisible(true);
    }

    void openQuanLyTaiKhoan() {
        if (ShareHelper.authenticated()) {
            if (!ShareHelper.isManager()) {
                DialogHelper.alert(this, "Bạn không có quyền truy cập");
            } else {
                TaiKhoan tkwin = new TaiKhoan(this, true);
                tkwin.setVisible(true);
//            tkwin.selectTab(index);
            }
        } else {
            DialogHelper.alert(this, "Vui lòng đăng nhập");
        }
    }

    void openQuanLyNhanVien() {
        if (ShareHelper.authenticated()) {
            new QLNhanVien(this, true).setVisible(true);
        } if (!ShareHelper.isManager()) {
                DialogHelper.alert(this, "Bạn không có quyền truy cập");
            }else {
            DialogHelper.alert(this, "Vui lòng đăng nhập");
        }
    }

    void openQuanLyKhachHang() {
        if (ShareHelper.authenticated()) {
            new QLKhachHang(this, true).setVisible(true);
        } else {
            DialogHelper.alert(this, "Vui lòng đăng nhập");
        }
    }

    void openQuanLyPhong() {
        if (ShareHelper.authenticated()) {
            new QLPhong(this, true).setVisible(true);
        }if (!ShareHelper.isManager()) {
                DialogHelper.alert(this, "Bạn không có quyền truy cập");
            } else {
            DialogHelper.alert(this, "Vui lòng đăng nhập");
        }
    }
    void openQuanLyPhuPhi() {
        if (ShareHelper.authenticated()) {
            new QLPhuPhi(this, true).setVisible(true);
        }if (!ShareHelper.isManager()) {
                DialogHelper.alert(this, "Bạn không có quyền truy cập");
            } else {
            DialogHelper.alert(this, "Vui lòng đăng nhập");
        }
    }

    void openQuanLyDichVu() {
        if (ShareHelper.authenticated()) {
            new QLDichVu(this, true).setVisible(true);
        }if (!ShareHelper.isManager()) {
                DialogHelper.alert(this, "Bạn không có quyền truy cập");
            } else {
            DialogHelper.alert(this, "Vui lòng đăng nhập");
        }
    }



    void openQuanLyKhuyenMai() {
        if (ShareHelper.authenticated()) {
            new QLVouCher(this, true).setVisible(true);
        }if (!ShareHelper.isManager()) {
                DialogHelper.alert(this, "Bạn không có quyền truy cập");
            } else {
            DialogHelper.alert(this, "Vui lòng đăng nhập");
        }
    }
    void openThongKe() {
        if (ShareHelper.authenticated()) {
            new ThongKe(this, true).setVisible(true);
        } else {
            DialogHelper.alert(this, "Vui lòng đăng nhập");
        }
    }
//    void openQuanLyHoaDon() {
//        if (ShareHelper.authenticated()) {
//            new HoaDonDialog(this, true,mathuephong).setVisible(true);
//        } else {
//            DialogHelper.alert(this, "Vui lòng đăng nhập");
//        }
//    }



    public void openThuePhong(String maPhong) {
        try {
            QLThuePhong qltp = new QLThuePhong(null, true, maPhong);
            qltp.setVisible(true);
            if (!qltp.isVisible()) {
                rdTatCa.setSelected(true);
                fillTable();
            }
        } catch (Exception e) {
        }
    }

    public void buttom() {
        rdTrong = new JRadioButton("Đang thuê");
        rdChuaDon = new JRadioButton("");
        rdBaoTri = new JRadioButton();

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(rdTrong);
        buttonGroup.add(rdChuaDon);
        buttonGroup.add(rdBaoTri);

        rdTrong.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // Chỉ thực hiện hành động khi rdDangthue được chọn
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    tblPhongMouseClicked(null);
                }
            }
        });
        rdChuaDon.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // Chỉ thực hiện hành động khi rdDangthue được chọn
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    TTDonPhong(null);
                }
            }
        });
                rdBaoTri.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // Chỉ thực hiện hành động khi rdDangthue được chọn
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    TTSuaPhong(null);
                }
            }
        });

    }

    public void TTDonPhong(String maP) {

        if (maP == null) {
            DialogHelper.alert(this, "Vui Lòng Chọn Phòng Cần dọn dẹp");
        }
        if (pt.getTinhTrang() == 1 || pt.getTinhTrang() == 2 || pt.getTinhTrang() == 4) {
            DialogHelper.alert(this, "Phòng không cần dọn");
        } else {
            PhongTro ptt = new PhongTro();
            ptt.setMaPhong(maP);
            ptt.setTinhTrang(1);
            dao.update_1(ptt);
            DialogHelper.alert(this, "Đã dọn phòng " + ptt.getMaPhong());
            fillTablePhongChuaDon();
        }
    }

    public void TTSuaPhong(String maP) {

        if (maP == null) {
            DialogHelper.alert(this, "Vui Lòng Chọn Phòng Cần Sửa");
        }
        if (pt.getTinhTrang() == 1 || pt.getTinhTrang() == 2 || pt.getTinhTrang() == 3) {
            DialogHelper.alert(this, "Phòng không cần dọn");
        } else {
            PhongTro ptt = new PhongTro();
            ptt.setMaPhong(maP);
            ptt.setTinhTrang(1);
            dao.update_1(ptt);
            DialogHelper.alert(this, "Đã sửa phòng " + ptt.getMaPhong());
            fillTablePhongBaoTri();
        }
    }
    
     public void openQuanLyHoaDon(){
                 try {
            HoaDonDialog hd = new HoaDonDialog(null, true,"","");
            hd.setVisible(true);
            if(!hd.isVisible()){
                fillTable();
            }
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
        lblDongHo = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        btnQLThuePhong = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        btnHoaDon = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPhong = new javax.swing.JTable();
        txt_TimKiem = new javax.swing.JTextField();
        btn_TimKiem = new javax.swing.JButton();
        rdTatCa = new javax.swing.JRadioButton();
        rdTrong = new javax.swing.JRadioButton();
        rdDaThue = new javax.swing.JRadioButton();
        rdChuaDon = new javax.swing.JRadioButton();
        rdBaoTri = new javax.swing.JRadioButton();
        btnDonPhong = new javax.swing.JButton();
        btnSuaPhong = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));
        jPanel2.setForeground(new java.awt.Color(0, 51, 51));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("PHẦN MỀM QUẢN LÝ NHÀ NGHỈ");

        lblDongHo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblDongHo.setForeground(new java.awt.Color(255, 255, 255));
        lblDongHo.setText("Time");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblDongHo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(221, 221, 221))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 26, Short.MAX_VALUE)
                .addComponent(lblDongHo))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton1.setText("Quản lý phòng");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Quản lý dịch vụ");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Quản lý nhân viên");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Quản lý khách hàng");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        btnQLThuePhong.setText("Quản lý thuê phòng");
        btnQLThuePhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLThuePhongActionPerformed(evt);
            }
        });

        jButton7.setText("Quản lý tài khoản");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        btnHoaDon.setText("Quản lý hóa đơn");
        btnHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoaDonActionPerformed(evt);
            }
        });

        jButton9.setText("Khuyến mại");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setText("Đăng Xuất");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton5.setText("Quản lý phụ phí");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Thống kê");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnQLThuePhong, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(btnQLThuePhong)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addGap(18, 18, 18)
                .addComponent(jButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnHoaDon)
                .addGap(18, 18, 18)
                .addComponent(jButton9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                .addComponent(jButton10)
                .addContainerGap())
        );

        tblPhong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblPhong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPhongMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPhong);

        btn_TimKiem.setText("Tìm phòng");
        btn_TimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TimKiemActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdTatCa);
        rdTatCa.setForeground(new java.awt.Color(255, 255, 255));
        rdTatCa.setSelected(true);
        rdTatCa.setText("Tất cả");
        rdTatCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdTatCaActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdTrong);
        rdTrong.setForeground(new java.awt.Color(255, 255, 255));
        rdTrong.setText("Phòng trống");
        rdTrong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdTrongActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdDaThue);
        rdDaThue.setForeground(new java.awt.Color(255, 255, 255));
        rdDaThue.setText("Phòng đã thuê");
        rdDaThue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdDaThueActionPerformed(evt);
            }
        });

        rdChuaDon.setBackground(new java.awt.Color(0, 51, 51));
        buttonGroup1.add(rdChuaDon);
        rdChuaDon.setForeground(new java.awt.Color(255, 255, 255));
        rdChuaDon.setText("Phòng chưa dọn dẹp");
        rdChuaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdChuaDonActionPerformed(evt);
            }
        });

        rdBaoTri.setBackground(new java.awt.Color(0, 51, 51));
        buttonGroup1.add(rdBaoTri);
        rdBaoTri.setForeground(new java.awt.Color(255, 255, 255));
        rdBaoTri.setText("Phòng bảo trì");
        rdBaoTri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdBaoTriActionPerformed(evt);
            }
        });

        btnDonPhong.setText("Dọn phòng");
        btnDonPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDonPhongActionPerformed(evt);
            }
        });

        btnSuaPhong.setText("Sửa Phòng");
        btnSuaPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaPhongActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rdTatCa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdTrong)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdDaThue, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdChuaDon)
                        .addGap(18, 18, 18)
                        .addComponent(rdBaoTri)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txt_TimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_TimKiem)
                        .addGap(353, 353, 353)
                        .addComponent(btnDonPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSuaPhong)
                        .addGap(11, 11, 11))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdTatCa)
                            .addComponent(rdTrong)
                            .addComponent(rdDaThue)
                            .addComponent(rdChuaDon)
                            .addComponent(rdBaoTri))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnDonPhong)
                            .addComponent(txt_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_TimKiem)
                            .addComponent(btnSuaPhong))
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:q
        this.openQuanLyTaiKhoan();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        this.openQuanLyNhanVien();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.openQuanLyDichVu();

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.openQuanLyPhong();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        this.openQuanLyKhachHang();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        this.openQuanLyKhuyenMai();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        dangXuat();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void btnQLThuePhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLThuePhongActionPerformed
        openThuePhong("p001");
    }//GEN-LAST:event_btnQLThuePhongActionPerformed

    private void tblPhongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhongMouseClicked

         if (rdTrong.isSelected()) {
            int row = tblPhong.getSelectedRow();
            if (row >= 0) {
                String maphon = (String) tblPhong.getValueAt(row, 0);
                System.out.println(maphon);
                openThuePhong(maphon);
            }
        }
    }//GEN-LAST:event_tblPhongMouseClicked

    private void btn_TimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TimKiemActionPerformed
        String maphong = txt_TimKiem.getText();
        DefaultTableModel model = (DefaultTableModel) tblPhong.getModel();
        model.setRowCount(0);
        try {
            List<PhongTro> list = dao.selectByID_MaPhong(maphong);
            for (PhongTro pt : list) {
                LoaiPhong lp = lpd.selectByID(pt.getMaLoaiPhong());
                LoaiGia g = gia.selectByID(pt.getMaLoaiGia());
                Object[] row = {pt.getMaPhong(),
                    lp.getTenLoaiPhong(),
                    g.getGiaGio(),
                    g.getGiaNgay(),
                    pt.getGhiChu(),
                    convertIntToStatus(pt.getTinhTrang())
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }//GEN-LAST:event_btn_TimKiemActionPerformed

    private void rdTatCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdTatCaActionPerformed
        fillTable();
    }//GEN-LAST:event_rdTatCaActionPerformed

    private void rdTrongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdTrongActionPerformed
        DefaultTableModel model = (DefaultTableModel) tblPhong.getModel();
        model.setRowCount(0);
        try {
            List<PhongTro> list = dao.SELECT_BY_ID_SQL_PHONGTRONG();
            for (PhongTro pt : list) {
                LoaiPhong lp = lpd.selectByID(pt.getMaLoaiPhong());
                LoaiGia g = gia.selectByID(pt.getMaLoaiGia());
                Object[] row = {pt.getMaPhong(),
                    lp.getTenLoaiPhong(),
                    g.getGiaGio(),
                    g.getGiaNgay(),
                    pt.getGhiChu(),
                    convertIntToStatus(pt.getTinhTrang())
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }//GEN-LAST:event_rdTrongActionPerformed

    private void rdDaThueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdDaThueActionPerformed
        DefaultTableModel model = (DefaultTableModel) tblPhong.getModel();
        model.setRowCount(0);
        try {
            List<PhongTro> list = dao.SELECT_BY_ID_SQL_DATHUE();
            for (PhongTro pt : list) {
                LoaiPhong lp = lpd.selectByID(pt.getMaLoaiPhong());
                LoaiGia g = gia.selectByID(pt.getMaLoaiGia());
                Object[] row = {pt.getMaPhong(),
                    lp.getTenLoaiPhong(),
                    g.getGiaGio(),
                    g.getGiaNgay(),
                    pt.getGhiChu(),
                    convertIntToStatus(pt.getTinhTrang())
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }//GEN-LAST:event_rdDaThueActionPerformed

    private void rdChuaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdChuaDonActionPerformed
        fillTablePhongChuaDon();
    }//GEN-LAST:event_rdChuaDonActionPerformed

    private void btnHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoaDonActionPerformed

    openQuanLyHoaDon();
    }//GEN-LAST:event_btnHoaDonActionPerformed

    private void rdBaoTriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdBaoTriActionPerformed
        try {
            fillTablePhongBaoTri();
        } catch (Exception e) {
            DialogHelper.alert(this, "Vui lòng chọn phòng");
        }
    }//GEN-LAST:event_rdBaoTriActionPerformed

    private void btnDonPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDonPhongActionPerformed
         if (rdChuaDon.isSelected()) {
            int row = tblPhong.getSelectedRow();
            if (row >= 0) {
                String maphon = (String) tblPhong.getValueAt(row, 0);
                System.out.println(maphon);
                TTDonPhong(maphon);
            }
         }else{
             JOptionPane.showMessageDialog(this, "Chức năng dành cho những phòng chưa dọn dẹp");
         }
    }//GEN-LAST:event_btnDonPhongActionPerformed

    private void btnSuaPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaPhongActionPerformed
         if (rdBaoTri.isSelected()) {
            int row = tblPhong.getSelectedRow();
            if (row >= 0) {
                String maphon = (String) tblPhong.getValueAt(row, 0);
                System.out.println(maphon);
                TTSuaPhong(maphon);
            }
        }         else{
             JOptionPane.showMessageDialog(this, "Chức năng dành cho những phòng đang bảo trì");
         }
    }//GEN-LAST:event_btnSuaPhongActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        openQuanLyPhuPhi();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        openThongKe();
    }//GEN-LAST:event_jButton6ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TrangChuJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TrangChuJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TrangChuJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TrangChuJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TrangChuJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDonPhong;
    private javax.swing.JButton btnHoaDon;
    private javax.swing.JButton btnQLThuePhong;
    private javax.swing.JButton btnSuaPhong;
    private javax.swing.JButton btn_TimKiem;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDongHo;
    private javax.swing.JRadioButton rdBaoTri;
    private javax.swing.JRadioButton rdChuaDon;
    private javax.swing.JRadioButton rdDaThue;
    private javax.swing.JRadioButton rdTatCa;
    private javax.swing.JRadioButton rdTrong;
    private javax.swing.JTable tblPhong;
    private javax.swing.JTextField txt_TimKiem;
    // End of variables declaration//GEN-END:variables
}
