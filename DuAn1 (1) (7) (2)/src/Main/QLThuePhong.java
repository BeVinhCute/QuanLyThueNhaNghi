/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Main;

import Dao.ChiTietDVDAO;
import Dao.DichVuDao;
import Dao.KhachHangDao;
import Dao.LoaiGiaDAO;
import Dao.LoaiPhongDAO;
import Dao.PhongTroDao;
import Dao.ThuePhongTroDao;
import Endity.ChiTietDV;
import Endity.DichVu;
import Endity.HoaDon;
import Endity.KhachHang;
import Endity.LoaiGia;
import Endity.LoaiPhong;
import Endity.PhongTro;
import Endity.ThuePhongTro;
import Helper.ShareHelper;
import helper.DateHelper;
import helper.DialogHelper;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admin
 */
public class QLThuePhong extends javax.swing.JDialog {

    int row = -1;
    DichVuDao dvd = new DichVuDao();
    ChiTietDVDAO ctdv = new ChiTietDVDAO();
    KhachHangDao khd = new KhachHangDao();
    ThuePhongTroDao dao = new ThuePhongTroDao();// lam viec voi csdl
    PhongTroDao ptdao = new PhongTroDao();
    LoaiGiaDAO giad = new LoaiGiaDAO();
    LoaiPhongDAO lpdao = new LoaiPhongDAO();
    List<DichVu> list_dv = new ArrayList<>();
    private Set<DichVu> set_dichvu = new HashSet<>();
    private DefaultTableModel tblModel, tblModel1;
    Date now = new Date();
    String maphong = null;

    /**
     * Creates new form QLNV
     */
    public QLThuePhong(java.awt.Frame parent, boolean modal, String maphong) {
        super(parent, modal);
        initComponents();
        init();
        this.maphong = maphong;
        setFormPhong(maphong);
    }

    private void initTable() {
        tblModel = new DefaultTableModel();
        Object[] Column_1 = new Object[]{"MaThuePhong", "Makh", "Họ tên", "MaPhong", "Ngày thuê",  "Trạng Thái "};
        tblModel.setColumnIdentifiers(Column_1);
        tblThuePhong.setModel(tblModel);

        tblModel1 = new DefaultTableModel();
        Object[] Column1 = new Object[]{"MaDV", "TenDV", "Gia", "Ghi chú"};
        tblModel1.setColumnIdentifiers(Column1);
        tbl_dichvu.setModel(tblModel1);

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

    private void init() {
        this.setIconImage(ShareHelper.getAppIcon());
        this.setLocationRelativeTo(null);
        title("Quản Lý Thuê Phòng Trọ");
        initTable();
        fillTable();
        fillcbbKhachHang();
        fillcbbPhong();
        fillcbbDichVu();
        this.row = -1;
    }

    private String convertIntToStatus(int tinhTrang) {
        switch (tinhTrang) {
            case 1:
                return "Đang thuê";
            case 2:
                return "Quá hạn";
            case 3:
                return "Đã trả phòng";
            default:
                return "Không xác định";
        }
    }

    private void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblThuePhong.getModel();
        model.setRowCount(0);
        // đăng nhập nếu là trưởng phòng thì có thể thấy đc mật khẩu
        List<ThuePhongTro> list = dao.selectAll();
        try {
            for (ThuePhongTro nv : list) {
                KhachHang kh = khd.selectByID(nv.getMaKH());
                Object[] row = {nv.getMaThuePhong(),
                    nv.getMaKH(),
                    kh.getHoTen(),
                    nv.getMaPhong(),
                    DateHelper.toString(nv.getNgayThue(), "y    yyy-MM-dd HH:mm:ss"),
                    convertIntToStatus(nv.getTrangThai())};
                model.addRow(row);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    public void fillcbbDichVu() {
        try {
            DefaultComboBoxModel model = (DefaultComboBoxModel) cbDV.getModel();
            model.removeAllElements();
            List<DichVu> list = dvd.selectAll();
            for (DichVu dv : list) {
                model.addElement(dv.getTenDV());
            }
            cbDV.setSelectedIndex(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void fillcbbKhachHang() {
        try {
            DefaultComboBoxModel model = (DefaultComboBoxModel) cbbKH.getModel();
            model.removeAllElements();
            List<KhachHang> list = khd.selectAll();
            for (KhachHang kh : list) {
                model.addElement(kh.getMaKH());
            }
            cbbKH.setSelectedIndex(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void fillcbbPhong() {
        try {
            DefaultComboBoxModel model = (DefaultComboBoxModel) cbbMaPhong.getModel();
            model.removeAllElements();
            List<PhongTro> list = ptdao.selectAll();
            for (PhongTro dv : list) {
                if (dv.getTinhTrang() == 1) {
                    model.addElement(dv.getMaPhong());
                }
            }
            cbbMaPhong.setSelectedIndex(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void filltabledichvu() {
        DefaultTableModel model = (DefaultTableModel) tbl_dichvu.getModel();
        model.setRowCount(0);
        for (DichVu dv : list_dv) {
            Object[] row = {dv.getMaDV(),
                dv.getTenDV(),
                dv.getGiaDV(),
                dv.getGhiChu()
            };
            model.addRow(row);
        }
    }

    public Set<DichVu> chonDichVu(String TenDV) {
        try {
            DichVu dv = dvd.selectByID_1(TenDV);
            boolean flag = false;
            for (DichVu dichVu : list_dv) {
                if (dichVu.getTenDV().equals(TenDV)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                list_dv.add(dv);

            } else {
                DialogHelper.alert(this, "Dịch vụ đã được chọn");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return set_dichvu;
    }

    public void them_dichvu() {
        try {
            ChiTietDV tdv = new ChiTietDV();
            for (DichVu dv : list_dv) {
                tdv.setMaThuePhong(txtMaTP.getText());
                tdv.setMaDV(dv.getMaDV());
                tdv.setNgayDK(DateHelper.now());
                ctdv.insert(tdv);
                DialogHelper.alert(this, "Thêm Thành Công");
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Thêm Thất Bại");
            System.out.println(e.getMessage());
        }
    }

    void clearForm() {//xoa trang form
        ThuePhongTro nv = new ThuePhongTro();
        this.setForm(nv);
        this.row = -1;
    }

    ThuePhongTro getForm() {// tao nhan vien tu form
        if (CheckInput()) {
            ThuePhongTro nv = new ThuePhongTro();
            nv.setMaThuePhong(txtMaTP.getText());
            String maKH = (String) cbbKH.getSelectedItem();
            nv.setMaKH(maKH);
            String maPhong = (String) cbbMaPhong.getSelectedItem();
            nv.setMaPhong(maPhong);
            nv.setNgayThue(DateHelper.now());
            return nv;
        } else {

            return null;
        }
    }
    public void xoa_dichvu(String TenDV) {
    // Assuming TenDV is the unique identifier (name) of the service to be removed
    for (int i = 0; i < list_dv.size(); i++) {
        if (list_dv.get(i).getTenDV().equals(TenDV)) {
            list_dv.remove(i);
            filltabledichvu(); // Refresh the table after removing the service
            DialogHelper.alert(this, "Đã xóa dịch vụ");
            return; // Exit the method after removing the service
        }
    }
    DialogHelper.alert(this, "Không tìm thấy dịch vụ cần xóa");
}


    void edit() {
        String manv = (String) tblThuePhong.getValueAt(this.row, 0);
        ThuePhongTro nv = dao.selectByID(manv);
        this.setForm(nv);
    }

    void insert() {

        ThuePhongTro tp = getForm();
        tp.setTrangThai(1);//mặc định là đang sử dụng khi thêm khách hàng vào phòng

        if (tp != null) {
            try {
                dao.insert(tp);
                this.fillTable();
                this.clearForm();
                DialogHelper.alert(this, "Thêm mới thành công !");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return;
        }

    }

    void update() {
        ThuePhongTro nv = getForm();

        if (nv != null) {
            try {
                dao.update(nv);
                this.fillTable();
                this.clearForm();
                DialogHelper.alert(this, "Thêm mới thành công !");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return;
        }
    }

    void delete() {
        if (!ShareHelper.isManager()) {
            DialogHelper.alert(this, "Bạn không có quyền xoá  ! ");
        } else {
            String manv = txtMaTP.getText();
            if (manv.equals(ShareHelper.user.getTenTk())) {
                DialogHelper.alert(this, "Bạn không thể xoá!");
            } else if (DialogHelper.confirm(this, "Bạn thực sự muốn xoá ?")) {
                try {
                    dao.delete(manv);
                    this.fillTable();
                    this.clearForm();
                } catch (Exception e) {
                    DialogHelper.alert(this, "Xoá thất bại!");
                }
            }
        }

    }

    ThuePhongTro getfrom() {
        //   verify();
        ThuePhongTro tp = new ThuePhongTro();
        txtMaTP.setText(tp.getMaThuePhong());
        cbbKH.setSelectedItem(String.valueOf(tp.getMaKH()));
        cbbMaPhong.setSelectedItem(String.valueOf(tp.getMaPhong()));
        tp.setNgayThue(DateHelper.now());
        return tp;
    }

    public void setForm(ThuePhongTro tp) {//,KhachHang kh,LoaiPhong lp,LoaiGia gia){
        try {
            KhachHang kh = khd.selectByID(tp.getMaKH());
            PhongTro p = ptdao.selectByID(tp.getMaPhong());
            LoaiGia gia = giad.selectByID(p.getMaLoaiGia());
            LoaiPhong lp = lpdao.selectByID(p.getMaLoaiPhong());

            cbbKH.setSelectedItem(String.valueOf(tp.getMaKH()));
            cbbMaPhong.setSelectedItem(String.valueOf(tp.getMaPhong()));
            txtLoaiPhong.setText(lp.getTenLoaiPhong());
            txtTenKH.setText(kh.getHoTen());
            txtGiaGio.setText(String.valueOf(gia.getGiaGio()));
            txtGiaNgay.setText(String.valueOf(gia.getGiaNgay()));
            txtMaTP.setText(String.valueOf(tp.getMaThuePhong()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void setFormPhong(String maphong) {//,KhachHang kh,LoaiPhong lp,LoaiGia gia){
        try {
            PhongTro p = ptdao.selectByID(maphong);
            LoaiGia gia1 = giad.selectByID(p.getMaLoaiGia());
            LoaiPhong lp1 = lpdao.selectByID(p.getMaLoaiPhong());
            cbbMaPhong.setSelectedItem(String.valueOf(p.getMaPhong()));
            txtLoaiPhong.setText(lp1.getTenLoaiPhong());
            txtGiaGio.setText(String.valueOf(gia1.getGiaGio()));
            txtGiaNgay.setText(String.valueOf(gia1.getGiaNgay()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void fillTableDV(ChiTietDV dv) {
        DefaultTableModel model = (DefaultTableModel) tbl_dichvu.getModel();
        model.setRowCount(0);

        try {
            List<ChiTietDV> list = ctdv.selectByKey(dv.getMaThuePhong());

            for (ChiTietDV ctdv : list) {
                DichVu dvv = dvd.selectByID(ctdv.getMaDV());
                Object[] row = {
                    dvv.getTenDV(),
                    dvv.getGiaDV(),
                    ctdv.getNgayDK(),};
                model.addRow(row);

            }

            // Set the total to txtTienDv
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void TTDangThue(String maphong) {
        String maPhong = (String) cbbMaPhong.getSelectedItem();
        if (maPhong == null) {
            DialogHelper.alert(this, "Vui Lòng Chọn Phòng");
        }
        PhongTro p = ptdao.selectByID(maphong);
        if (p.getTinhTrang() == 1) {
            PhongTro pt = new PhongTro();
            pt.setMaPhong(maphong);
            pt.setTinhTrang(2);
            ptdao.update_1(pt);
        }
    }

    public void openHoaDon(String mathuephong, String maphong) {
        try {
            HoaDonDialog hd = new HoaDonDialog(null, true, mathuephong, maphong);
            hd.setVisible(true);
            if (!hd.isVisible()) {
                fillTable();
            }
        } catch (Exception e) {
        }
    }

    private boolean CheckInput() {
        if (txtMaTP.getText().length() > 20) {
            DialogHelper.alert(this, " mã thuê phòng không nhập quá 20 ký tự");
            return false;
        }
        if (txtMaTP.getText().equals("")) {
            DialogHelper.alert(this, "mã thuê phòng không được để trống");
            return false;
        }
        if (txtMaTP.getText().equals("")) {
            DialogHelper.alert(this, "mã thuê phòng không được để trống");
            return false;
        }
        try {
            ThuePhongTro tp = dao.selectByID(txtMaTP.getText());
            if (tp != null) {
                DialogHelper.alert(this, "Mã thuê phòng đã tồn tại");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel18 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMaTP = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cbbKH = new javax.swing.JComboBox<>();
        cbbMaPhong = new javax.swing.JComboBox<>();
        txtTenKH = new javax.swing.JTextField();
        txtLoaiPhong = new javax.swing.JTextField();
        txtGiaGio = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtGiaNgay = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnMoi = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnTraPhong = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblThuePhong = new javax.swing.JTable();
        txt_TimKiem = new javax.swing.JTextField();
        btn_TimKiem = new javax.swing.JButton();
        rdTatCa = new javax.swing.JRadioButton();
        rdDangThue = new javax.swing.JRadioButton();
        rdDaTra = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_dichvu = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        cbDV = new javax.swing.JComboBox<>();
        btnThemDV = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));

        jLabel1.setBackground(new java.awt.Color(0, 51, 51));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 255, 255));
        jLabel1.setText("QUẢN LÝ THUÊ NHÀ NGHỈ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(378, 378, 378)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(20, 20, 20))
        );

        jPanel18.setBackground(new java.awt.Color(204, 255, 255));

        jPanel3.setBackground(new java.awt.Color(0, 102, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Đăng ký phòng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(204, 255, 255))); // NOI18N
        jPanel3.setForeground(new java.awt.Color(153, 255, 255));

        jLabel2.setForeground(new java.awt.Color(153, 255, 255));
        jLabel2.setText("Mã thuê phòng:");

        jLabel3.setForeground(new java.awt.Color(153, 255, 255));
        jLabel3.setText("Mã phòng:");

        jLabel5.setForeground(new java.awt.Color(153, 255, 255));
        jLabel5.setText("Khách Hàng");

        cbbKH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Trống" }));
        cbbKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbbKHMouseClicked(evt);
            }
        });
        cbbKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbKHActionPerformed(evt);
            }
        });

        cbbMaPhong.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Trống" }));
        cbbMaPhong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbbMaPhongMouseClicked(evt);
            }
        });
        cbbMaPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbMaPhongActionPerformed(evt);
            }
        });

        txtTenKH.setEditable(false);
        txtTenKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenKHActionPerformed(evt);
            }
        });

        txtLoaiPhong.setEditable(false);

        txtGiaGio.setEditable(false);
        txtGiaGio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaGioActionPerformed(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(153, 255, 255));
        jLabel4.setText("Giá giờ:");

        txtGiaNgay.setEditable(false);
        txtGiaNgay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaNgayActionPerformed(evt);
            }
        });

        jLabel6.setForeground(new java.awt.Color(153, 255, 255));
        jLabel6.setText("Giá ngày:");

        btnMoi.setText("Làm mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        btnThem.setText("Đăng ký");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnTraPhong.setText("Trả phòng");
        btnTraPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTraPhongActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel2)
                        .addComponent(jLabel5)
                        .addComponent(jLabel3))
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(cbbKH, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtMaTP, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(cbbMaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtLoaiPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtGiaGio, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGiaNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                    .addComponent(btnTraPhong, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                    .addComponent(btnMoi, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtMaTP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbbKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbbMaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(txtLoaiPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGiaGio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGiaNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTraPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(66, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));

        tblThuePhong.setModel(new javax.swing.table.DefaultTableModel(
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
        tblThuePhong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThuePhongMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tblThuePhong);

        txt_TimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TimKiemActionPerformed(evt);
            }
        });

        btn_TimKiem.setText("Tím Kiếm");
        btn_TimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TimKiemActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdTatCa);
        rdTatCa.setForeground(new java.awt.Color(204, 255, 255));
        rdTatCa.setText("Tất cả");
        rdTatCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdTatCaActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdDangThue);
        rdDangThue.setForeground(new java.awt.Color(204, 255, 255));
        rdDangThue.setText("Đang thuê");
        rdDangThue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdDangThueActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdDaTra);
        rdDaTra.setForeground(new java.awt.Color(204, 255, 255));
        rdDaTra.setText("Đã trả phòng");
        rdDaTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdDaTraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_TimKiem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rdTatCa, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdDangThue, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdDaTra, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_TimKiem))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdTatCa)
                    .addComponent(rdDangThue)
                    .addComponent(rdDaTra))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(0, 102, 102));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thuê dịch vụ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(204, 255, 255))); // NOI18N
        jPanel4.setForeground(new java.awt.Color(204, 255, 255));

        tbl_dichvu.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tbl_dichvu);

        jButton1.setText("ChọnDV");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        cbDV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnThemDV.setText("Thêm dịch vụ");
        btnThemDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemDVActionPerformed(evt);
            }
        });

        btnXoa.setText("Xóa dịch vụ");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(cbDV, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 255, Short.MAX_VALUE)
                        .addComponent(btnXoa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnThemDV)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(cbDV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemDV)
                    .addComponent(btnXoa))
                .addGap(71, 71, 71))
        );

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Đăng ký phòng", jPanel18);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        btnXoa.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedRowIndex = tbl_dichvu.getSelectedRow();
        
        if (selectedRowIndex != -1) { // Ensure a row is selected
            String tenDV = (String) tbl_dichvu.getValueAt(selectedRowIndex, 1); // Assuming the name is in column index 1

            // Call your delete method here passing the service name (tenDV) to delete it
            xoa_dichvu(tenDV);
        }
        
    }
});

    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        insert();
        TTDangThue(maphong);
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btn_TimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TimKiemActionPerformed
        String tenkh = '%' + txt_TimKiem.getText() + '%';
        DefaultTableModel model = (DefaultTableModel) tblThuePhong.getModel();
        model.setRowCount(0);
        try {
            List<ThuePhongTro> list = dao.selectByID_TenKH(tenkh);
            for (ThuePhongTro tpt : list) {
                KhachHang kh = khd.selectByID(tpt.getMaKH());
                Object[] row = {
                    tpt.getMaThuePhong(),
                    tpt.getMaKH(),
                    kh.getHoTen(),
                    tpt.getMaPhong(),
                    tpt.getNgayThue(),
                    convertIntToStatus(tpt.getTrangThai())
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }//GEN-LAST:event_btn_TimKiemActionPerformed

    private void txt_TimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TimKiemActionPerformed

    }//GEN-LAST:event_txt_TimKiemActionPerformed

    private void tblThuePhongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThuePhongMouseClicked
        // TODO add your handling code here:

        int row = tblThuePhong.getSelectedRow();
        String matp = (String) tblThuePhong.getValueAt(row, 0);
        try {
            ThuePhongTro tp = dao.selectByID(matp);
            KhachHang kh = khd.selectByID(tp.getMaKH());
            PhongTro p = ptdao.selectByID(tp.getMaPhong());
            LoaiGia gia = giad.selectByID(p.getMaLoaiGia());
            LoaiPhong lp = lpdao.selectByID(p.getMaLoaiPhong());
            ChiTietDV dv = ctdv.selectByID(matp);
            setForm(tp);
            txtGiaGio.setText(String.valueOf(gia.getGiaGio()));
            txtGiaNgay.setText(String.valueOf(gia.getGiaNgay()));
            txtLoaiPhong.setText(lp.getTenLoaiPhong());
            txtTenKH.setText(kh.getHoTen());
            fillTableDV(dv);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_tblThuePhongMouseClicked

    private void rdDangThueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdDangThueActionPerformed
        DefaultTableModel model = (DefaultTableModel) tblThuePhong.getModel();
        model.setRowCount(0);
        try {
            List<ThuePhongTro> list = dao.SELECT_BY_ID_SQL_DANGTHUE();
            for (ThuePhongTro tpt : list) {
                KhachHang kh = khd.selectByID(tpt.getMaKH());
                Object[] row = {
                    tpt.getMaThuePhong(),
                    tpt.getMaKH(),
                    kh.getHoTen(),
                    tpt.getMaPhong(),
                    tpt.getNgayThue(),
                    convertIntToStatus(tpt.getTrangThai())
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }//GEN-LAST:event_rdDangThueActionPerformed

    private void rdDaTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdDaTraActionPerformed
        DefaultTableModel model = (DefaultTableModel) tblThuePhong.getModel();
        model.setRowCount(0);
        try {
            List<ThuePhongTro> list = dao.SELECT_BY_ID_SQL_DATRA();
            for (ThuePhongTro tpt : list) {
                KhachHang kh = khd.selectByID(tpt.getMaKH());
                Object[] row = {
                    tpt.getMaThuePhong(),
                    tpt.getMaKH(),
                    kh.getHoTen(),
                    tpt.getMaPhong(),
                    tpt.getNgayThue(),
                    convertIntToStatus(tpt.getTrangThai())
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }//GEN-LAST:event_rdDaTraActionPerformed

    private void rdTatCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdTatCaActionPerformed
        DefaultTableModel model = (DefaultTableModel) tblThuePhong.getModel();
        model.setRowCount(0);
        try {
            List<ThuePhongTro> list = dao.SELECT_ALL_SQL();
            for (ThuePhongTro tpt : list) {
                KhachHang kh = khd.selectByID(tpt.getMaKH());
                Object[] row = {
                    tpt.getMaThuePhong(),
                    tpt.getMaKH(),
                    kh.getHoTen(),
                    tpt.getMaPhong(),
                    tpt.getNgayThue(),
                    convertIntToStatus(tpt.getTrangThai())
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }//GEN-LAST:event_rdTatCaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String tendv = cbDV.getSelectedItem().toString();
        chonDichVu(tendv);
        filltabledichvu();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cbbKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbKHActionPerformed

    private void cbbMaPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbMaPhongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbMaPhongActionPerformed

    private void txtGiaGioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaGioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaGioActionPerformed

    private void txtGiaNgayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaNgayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaNgayActionPerformed

    private void txtTenKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenKHActionPerformed

    private void cbbKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbbKHMouseClicked
        // TODO add your handling code here:
        cbbKH.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    String selectedMakh = cbbKH.getSelectedItem().toString();
                    // Sử dụng selectedMaTP để lấy thông tin từ cơ sở dữ liệu
                    // Ví dụ:
                    KhachHang kh = khd.selectByID(selectedMakh);
                    if (kh != null) {
                        txtTenKH.setText(kh.getHoTen());
                        // Cập nhật các text field khác tương tự

                        // Cập nhật các text field khác tương tự
                    }
                }
            }
        });
    }//GEN-LAST:event_cbbKHMouseClicked

    private void cbbMaPhongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbbMaPhongMouseClicked
        // TODO add your handling code here:
        cbbMaPhong.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    String selectedMakh = cbbMaPhong.getSelectedItem().toString();
                    // Sử dụng selectedMaTP để lấy thông tin từ cơ sở dữ liệu
                    // Ví dụ:
                    PhongTro kh = ptdao.selectByID(selectedMakh);
                    LoaiPhong lp = lpdao.selectByID(kh.getMaLoaiPhong());
                    LoaiGia lg = giad.selectByID(kh.getMaLoaiGia());
                    if (kh != null) {
                        txtLoaiPhong.setText(lp.getTenLoaiPhong());
                        txtGiaGio.setText(String.valueOf(lg.getGiaGio()));
                        txtGiaNgay.setText(String.valueOf(lg.getGiaNgay()));
                        // Cập nhật các text field khác tương tự

                        // Cập nhật các text field khác tương tự
                    }
                }
            }
        });
    }//GEN-LAST:event_cbbMaPhongMouseClicked

    private void btnThemDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemDVActionPerformed
        // TODO add your handling code here:
        them_dichvu();
    }//GEN-LAST:event_btnThemDVActionPerformed

    private void btnTraPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTraPhongActionPerformed
        int row = tblThuePhong.getSelectedRow();
        String mathuephong = (String) tblThuePhong.getValueAt(row, 0);
        String mp = (String) tblThuePhong.getValueAt(row, 3);
        String trangthai = (String) tblThuePhong.getValueAt(row, 5);
        System.out.println(trangthai);
        if (trangthai.equals("Đã trả phòng")) {
            DialogHelper.alert(this, "Phòng đã trả");
        } else {
            openHoaDon(mathuephong, mp);
        }
    }//GEN-LAST:event_btnTraPhongActionPerformed

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
            java.util.logging.Logger.getLogger(QLThuePhong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QLThuePhong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QLThuePhong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLThuePhong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QLThuePhong dialog = new QLThuePhong(new javax.swing.JFrame(), true, null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemDV;
    private javax.swing.JButton btnTraPhong;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btn_TimKiem;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbDV;
    private javax.swing.JComboBox<String> cbbKH;
    private javax.swing.JComboBox<String> cbbMaPhong;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JRadioButton rdDaTra;
    private javax.swing.JRadioButton rdDangThue;
    private javax.swing.JRadioButton rdTatCa;
    private javax.swing.JTable tblThuePhong;
    private javax.swing.JTable tbl_dichvu;
    private javax.swing.JTextField txtGiaGio;
    private javax.swing.JTextField txtGiaNgay;
    private javax.swing.JTextField txtLoaiPhong;
    private javax.swing.JTextField txtMaTP;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txt_TimKiem;
    // End of variables declaration//GEN-END:variables
}
