/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Main;

import Dao.LoaiGiaDAO;
import Dao.LoaiPhongDAO;
import Dao.PhongTroDao;
import Endity.DichVu;
import Endity.LoaiGia;
import Endity.LoaiPhong;
import Endity.PhongTro;
import Helper.ShareHelper;
import helper.DialogHelper;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admin
 */
public class QLPhong extends javax.swing.JDialog {

    int row = -1;
    PhongTroDao dao = new PhongTroDao();// lam viec voi csdl
    LoaiPhongDAO lpdao = new LoaiPhongDAO();
    LoaiGiaDAO lgdao = new LoaiGiaDAO();
    private DefaultTableModel tblModel, tblModel1,tblModel2;

    /**
     * Creates new form QLPT
     */
    public QLPhong(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    private void initTable() {
        tblModel = new DefaultTableModel();
        Object[] Column = new Object[]{"MAPhong", "Mã loại phòng", "Mã giá phòng", "Ghi Chú", "Tình Trạng"};
        tblModel.setColumnIdentifiers(Column);
        TableNhanVien.setModel(tblModel);
        
        tblModel1 = new DefaultTableModel();
        Object[] Column1 = new Object[]{"Mã loại phòng", "Tên loại phòng", "Số giường"};
        tblModel1.setColumnIdentifiers(Column1);
        TableLoaiPhong.setModel(tblModel1);
        
        tblModel2 = new DefaultTableModel();
        Object[] Column2 = new Object[]{"Mã loại giá", "Giá theo giờ", "Giá ngày"};
        tblModel2.setColumnIdentifiers(Column2);
        TableLoaiGia.setModel(tblModel2);

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
        title("QLNN - Quản Lý Phòng");
        initTable();
        fillTable();
        fillTableLoaiPhong();
        fillTableLoaiGia();
        this.row = -1;
    }

    private String convertIntToStatus(int tinhTrang) {
        switch (tinhTrang) {
            case 1:
                return "Trống";
            case 2:
                return "Đang sử dụng";
            case 3:
                return "Dọn dẹp";
            case 4:
                return "Bảo trì";
            default:
                return "Không xác định";
        }
    }

    private void fillTable() {
        DefaultTableModel model = (DefaultTableModel) TableNhanVien.getModel();
        model.setRowCount(0);
        // đăng nhập nếu là trưởng phòng thì có thể thấy đc mật khẩu

        try {
            List<PhongTro> list = dao.selectAll();
            for (PhongTro kh : list) {
                Object[] row = {kh.getMaPhong(), kh.getMaLoaiPhong(), kh.getMaLoaiGia(), kh.getGhiChu(), convertIntToStatus(kh.getTinhTrang())};
                model.addRow(row);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    private void fillTableLoaiPhong() {
        DefaultTableModel model = (DefaultTableModel) TableLoaiPhong.getModel();
        model.setRowCount(0);
        // đăng nhập nếu là trưởng phòng thì có thể thấy đc mật khẩu

        try {
            List<LoaiPhong> list = lpdao.selectAll();
            for (LoaiPhong kh : list) {
                Object[] row = {kh.getMaLoaiPhong(), kh.getTenLoaiPhong(), kh.getSoGiuong()};
                model.addRow(row);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    private void fillTableLoaiGia() {
        DefaultTableModel model = (DefaultTableModel) TableLoaiGia.getModel();
        model.setRowCount(0);
        // đăng nhập nếu là trưởng phòng thì có thể thấy đc mật khẩu

        try {
            List<LoaiGia> list = lgdao.selectAll();
            for (LoaiGia kh : list) {
                Object[] row = {kh.getMaLoaiGia(), kh.getGiaGio(), kh.getGiaNgay()};
                model.addRow(row);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    private boolean check_input() {
        if (txtMaP.getText().length() > 4 || txtMaP.getText().equals("")) {
            System.out.println("mã");
            return false;
        }
        
        return true;
    }
  

    void setForm(PhongTro nv) {// load du lieu từ table len form

        txtMaP.setText(nv.getMaPhong());
        txtMaLP.setText(nv.getMaLoaiPhong());
        txtMaGP.setText(nv.getMaLoaiGia());
        txtGhiChu.setText(nv.getGhiChu());
        if (rdoTrong.isSelected()) {
            nv.setTinhTrang(1); // 1 tương ứng với trạng thái "Trống"
        } else if (rdoBaoChi.isSelected()) {
            nv.setTinhTrang(3); // 3 tương ứng với trạng thái "Bảo trì"
        }

    }
    void setFormLG(LoaiGia nv) {// load du lieu từ table len form
         txtMaLoaiGia.setText(nv.getMaLoaiGia());
        txtGiaGio.setText(String.valueOf(nv.getGiaGio()));
        txtGiaNgay.setText(String.valueOf(nv.getGiaNgay()));

    }
    void setFormLP(LoaiPhong nv) {// load du lieu từ table len form

        txtMaLoaiPhong.setText(nv.getMaLoaiPhong());
        txtTenLP.setText(nv.getTenLoaiPhong());
        txtSoGiuong.setText(String.valueOf(nv.getSoGiuong()));
    }

    PhongTro getForm() {
    if (check_input()) {
        PhongTro nv = new PhongTro();
        nv.setMaPhong(txtMaP.getText());
        nv.setMaLoaiPhong(txtMaLP.getText());
        nv.setMaLoaiGia(txtMaGP.getText());
        nv.setGhiChu(txtGhiChu.getText());
        if (rdoTrong.isSelected()) {
            nv.setTinhTrang(1); // Trạng thái "Trống"

        } else if (rdoBaoChi.isSelected()) {
            nv.setTinhTrang(3); // Trạng thái "Bảo trì"
        }
        
        return nv;
    } else {
        DialogHelper.alert(this, "Lỗi Dữ Liệu");
        return null;
    }
}
    LoaiPhong getFormLP(){
        if(check_input()){
            LoaiPhong lp = new LoaiPhong();
            lp.setMaLoaiPhong(txtMaLoaiPhong.getText());
            lp.setSoGiuong(Integer.valueOf(txtSoGiuong.getText()));
            lp.setTenLoaiPhong(txtTenLP.getText());
            return lp;
        }else{
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
            return null;
        }
        
    }
    LoaiGia getFormLG(){
        if(check_input()){
            LoaiGia lg = new LoaiGia();
            lg.setMaLoaiGia(txtMaLoaiGia.getText());
            lg.setGiaGio(Integer.valueOf(txtGiaGio.getText()));
            lg.setGiaNgay(Integer.valueOf(txtGiaGio.getText()));
            return lg;
        }else{
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
            return null;
        }
    }


    void clearForm() {//xoa trang form
        PhongTro nv = new PhongTro();
        LoaiGia lg = new LoaiGia();
        LoaiPhong lp = new LoaiPhong();
        this.setForm(nv);
        this.setFormLG(lg);
        this.setFormLP(lp);
        this.row = -1;
    }

    void edit() {
        String manv = (String) TableNhanVien.getValueAt(this.row, 0);
        PhongTro nv = dao.selectByID(manv);
        this.setForm(nv);
    }
    void editLP() {
        String manv = (String) TableLoaiPhong.getValueAt(this.row, 0);
        LoaiPhong nv = lpdao.selectByID(manv);
        this.setFormLP(nv);
    }
    void editLG() {
        String manv = (String) TableLoaiGia.getValueAt(this.row, 0);
        LoaiGia nv = lgdao.selectByID(manv);
        this.setFormLG(nv);
    }

    void insert() {
        PhongTro nv = getForm();

        // Thiết lập trạng thái mặc định cho phòng là "trống"
        nv.setTinhTrang(1); // 1 tương ứng với trạng thái "trống" 
        if (nv != null) {
            try {
                dao.insert(nv);
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
        PhongTro nv = getForm();

        if (nv != null) {
            try {
                // Kiểm tra RadioButton nào được chọn để cập nhật giá trị trạng thái mới vào đối tượng PhongTro

                dao.update(nv);
                this.fillTable();
                this.clearForm();
                DialogHelper.alert(this, "Sửa thành công !");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return;
        }
    }

    void delete() {
        if (!ShareHelper.isManager()) {
            DialogHelper.alert(this, "Bạn không có quyền xoá nv ! ");
        } else {
            String manv = txtMaP.getText();
            if (manv.equals(ShareHelper.user.getTenTk())) {
                DialogHelper.alert(this, "Bạn không thể xoá chính bản!");
            } else if (DialogHelper.confirm(this, "Bạn thực sự muốn xoá nhân viên này ?")) {
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
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableNhanVien = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtMaP = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        rdoTrong = new javax.swing.JRadioButton();
        rdoBaoChi = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        txtMaLP = new javax.swing.JTextField();
        txtMaGP = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TableLoaiPhong = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        txtMaLoaiPhong = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtTenLP = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtSoGiuong = new javax.swing.JTextField();
        btnMoiLP = new javax.swing.JButton();
        btnThemLP = new javax.swing.JButton();
        btnSuaLP = new javax.swing.JButton();
        txtXoaLP = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TableLoaiGia = new javax.swing.JTable();
        txtMaLoaiGia = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtGiaGio = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtGiaNgay = new javax.swing.JTextField();
        btnXoaLG = new javax.swing.JButton();
        btnSuaLG = new javax.swing.JButton();
        bnThemLG = new javax.swing.JButton();
        btnMoiLG = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

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

        jPanel1.setBackground(new java.awt.Color(149, 207, 202));

        TableNhanVien.setModel(new javax.swing.table.DefaultTableModel(
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
        TableNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableNhanVienMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TableNhanVien);

        jPanel3.setBackground(new java.awt.Color(0, 102, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin phòng ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(204, 255, 255))); // NOI18N

        jLabel3.setBackground(new java.awt.Color(0, 102, 102));
        jLabel3.setForeground(new java.awt.Color(204, 255, 255));
        jLabel3.setText("Mã Phòng ");

        jLabel8.setBackground(new java.awt.Color(0, 102, 102));
        jLabel8.setForeground(new java.awt.Color(204, 255, 255));
        jLabel8.setText("Mã loại phòng");

        jLabel4.setBackground(new java.awt.Color(0, 102, 102));
        jLabel4.setForeground(new java.awt.Color(204, 255, 255));
        jLabel4.setText("Mã loại giá");

        jLabel5.setBackground(new java.awt.Color(0, 102, 102));
        jLabel5.setForeground(new java.awt.Color(204, 255, 255));
        jLabel5.setText("Trạng thái");

        rdoTrong.setBackground(new java.awt.Color(0, 102, 102));
        buttonGroup2.add(rdoTrong);
        rdoTrong.setForeground(new java.awt.Color(204, 255, 255));
        rdoTrong.setText("Trống");

        rdoBaoChi.setBackground(new java.awt.Color(0, 102, 102));
        buttonGroup2.add(rdoBaoChi);
        rdoBaoChi.setForeground(new java.awt.Color(204, 255, 255));
        rdoBaoChi.setText("Đang bảo trì");

        jLabel9.setBackground(new java.awt.Color(0, 102, 102));
        jLabel9.setForeground(new java.awt.Color(204, 255, 255));
        jLabel9.setText("Ghi chú");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane3.setViewportView(txtGhiChu);

        jButton1.setText("Mới");

        btnThem.setText("Thêm");
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

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3)
                            .addComponent(jLabel8)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(rdoTrong, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(rdoBaoChi, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtMaGP, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                            .addComponent(txtMaLP)
                            .addComponent(txtMaP))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaLP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMaGP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoBaoChi)
                    .addComponent(rdoTrong))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(0, 102, 102));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Loại phòng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(204, 255, 255))); // NOI18N

        TableLoaiPhong.setModel(new javax.swing.table.DefaultTableModel(
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
        TableLoaiPhong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableLoaiPhongMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(TableLoaiPhong);

        jLabel7.setBackground(new java.awt.Color(0, 102, 102));
        jLabel7.setForeground(new java.awt.Color(204, 255, 255));
        jLabel7.setText("Mã loại phòng");

        jLabel10.setBackground(new java.awt.Color(0, 102, 102));
        jLabel10.setForeground(new java.awt.Color(204, 255, 255));
        jLabel10.setText("Tên loại phòng");

        jLabel11.setBackground(new java.awt.Color(0, 102, 102));
        jLabel11.setForeground(new java.awt.Color(204, 255, 255));
        jLabel11.setText("Số giường");

        btnMoiLP.setText("Mới");
        btnMoiLP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiLPActionPerformed(evt);
            }
        });

        btnThemLP.setText("Thêm");
        btnThemLP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemLPActionPerformed(evt);
            }
        });

        btnSuaLP.setText("Sửa");
        btnSuaLP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaLPActionPerformed(evt);
            }
        });

        txtXoaLP.setText("Xóa");
        txtXoaLP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtXoaLPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenLP, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                    .addComponent(txtSoGiuong)
                    .addComponent(txtMaLoaiPhong))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMoiLP)
                    .addComponent(btnThemLP)
                    .addComponent(btnSuaLP)
                    .addComponent(txtXoaLP))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMaLoaiPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTenLP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSoGiuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(21, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 7, Short.MAX_VALUE)
                        .addComponent(btnMoiLP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnThemLP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSuaLP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtXoaLP)
                        .addGap(48, 48, 48))))
        );

        jPanel5.setBackground(new java.awt.Color(0, 102, 102));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Giá phòng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(204, 255, 255))); // NOI18N

        jLabel12.setBackground(new java.awt.Color(0, 102, 102));
        jLabel12.setForeground(new java.awt.Color(204, 255, 255));
        jLabel12.setText("Mã loại giá");

        TableLoaiGia.setModel(new javax.swing.table.DefaultTableModel(
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
        TableLoaiGia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableLoaiGiaMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(TableLoaiGia);

        jLabel13.setBackground(new java.awt.Color(0, 102, 102));
        jLabel13.setForeground(new java.awt.Color(204, 255, 255));
        jLabel13.setText("Giá giờ");

        jLabel15.setBackground(new java.awt.Color(0, 102, 102));
        jLabel15.setForeground(new java.awt.Color(204, 255, 255));
        jLabel15.setText("Giá ngày");

        btnXoaLG.setText("Xóa");
        btnXoaLG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaLGActionPerformed(evt);
            }
        });

        btnSuaLG.setText("Sửa ");
        btnSuaLG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaLGActionPerformed(evt);
            }
        });

        bnThemLG.setText("Thêm");
        bnThemLG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bnThemLGActionPerformed(evt);
            }
        });

        btnMoiLG.setText("Mới");
        btnMoiLG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiLGActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtGiaGio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaLoaiGia, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtGiaNgay))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnXoaLG)
                    .addComponent(btnSuaLG)
                    .addComponent(bnThemLG)
                    .addComponent(btnMoiLG))
                .addGap(9, 9, 9))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMaLoaiGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtGiaGio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtGiaNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnMoiLG)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bnThemLG)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSuaLG)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoaLG)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(0, 1, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));

        jLabel1.setBackground(new java.awt.Color(0, 102, 102));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 255, 255));
        jLabel1.setText("QUẢN LÝ NHÀ NGHỈ ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(428, 428, 428)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(11, 11, 11))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void TableNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableNhanVienMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            this.row = TableNhanVien.getSelectedRow();
            this.edit();
        }
    }//GEN-LAST:event_TableNhanVienMouseClicked

    private void btnThemLPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemLPActionPerformed
        // TODO add your handling code here:
        LoaiPhong lp = getFormLP();

        if (lp != null) {
            try {
                lpdao.insert(lp);
                this.fillTableLoaiPhong();
                this.clearForm();
                DialogHelper.alert(this, "Thêm mới thành công !");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return;
        }
        
    }//GEN-LAST:event_btnThemLPActionPerformed

    private void bnThemLGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnThemLGActionPerformed
        // TODO add your handling code here:
        LoaiGia lg = getFormLG();
        
        if(lg != null){
            try {
                lgdao.insert(lg);
                this.fillTableLoaiGia();
                this.clearForm();
                DialogHelper.alert(this, "Thêm mới thành công");
            } catch (Exception e) {
                return;
            }
        }
    }//GEN-LAST:event_bnThemLGActionPerformed

    private void btnSuaLPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaLPActionPerformed
        // TODO add your handling code here:
        LoaiPhong lp = getFormLP();
        
        if(lp != null){
            try {
                lpdao.update(lp);
                this.fillTableLoaiPhong();
                this.clearForm();
                DialogHelper.alert(this, "Thêm mới thành công");
            } catch (Exception e) {
                return;
            }
        }
    }//GEN-LAST:event_btnSuaLPActionPerformed

    private void txtXoaLPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtXoaLPActionPerformed
        // TODO add your handling code here:
        if (!ShareHelper.isManager()) {
            DialogHelper.alert(this, "Bạn không có quyền xoá! ");
        } else {
            String manv = txtMaLoaiPhong.getText();
            if (manv.equals(ShareHelper.user.getTenTk())) {
                DialogHelper.alert(this, "Bạn không thể xoá chính bản!");
            } else if (DialogHelper.confirm(this, "Bạn thực sự muốn xoá nhân viên này ?")) {
                try {
                    lpdao.delete(manv);
                    this.fillTableLoaiPhong();
                    this.clearForm();
                } catch (Exception e) {
                    DialogHelper.alert(this, "Xoá thất bại!");
                }
            }
        }

    }//GEN-LAST:event_txtXoaLPActionPerformed

    private void btnMoiLPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiLPActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btnMoiLPActionPerformed

    private void btnSuaLGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaLGActionPerformed
        // TODO add your handling code here:
        LoaiGia lg = getFormLG();
        
        if(lg != null){
            try {
                lgdao.update(lg);
                this.fillTableLoaiGia();
                this.clearForm();
                DialogHelper.alert(this, "Thêm mới thành công");
            } catch (Exception e) {
                return;
            }
        }
        
    }//GEN-LAST:event_btnSuaLGActionPerformed

    private void btnXoaLGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaLGActionPerformed
        // TODO add your handling code here:
        if (!ShareHelper.isManager()) {
            DialogHelper.alert(this, "Bạn không có quyền xoá! ");
        } else {
            String manv = txtMaLoaiPhong.getText();
            if (manv.equals(ShareHelper.user.getTenTk())) {
                DialogHelper.alert(this, "Bạn không thể xoá chính bản!");
            } else if (DialogHelper.confirm(this, "Bạn thực sự muốn xoá nhân viên này ?")) {
                try {
                    lgdao.delete(manv);
                    this.fillTableLoaiGia();
                    this.clearForm();
                } catch (Exception e) {
                    DialogHelper.alert(this, "Xoá thất bại!");
                }
            }
        }
    }//GEN-LAST:event_btnXoaLGActionPerformed

    private void btnMoiLGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiLGActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btnMoiLGActionPerformed

    private void TableLoaiPhongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableLoaiPhongMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            this.row = TableLoaiPhong.getSelectedRow();
            this.editLP();
        }
    }//GEN-LAST:event_TableLoaiPhongMouseClicked

    private void TableLoaiGiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableLoaiGiaMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            this.row = TableLoaiGia.getSelectedRow();
            this.editLG();
        }
    }//GEN-LAST:event_TableLoaiGiaMouseClicked

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
            java.util.logging.Logger.getLogger(QLPhong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QLPhong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QLPhong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLPhong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QLPhong dialog = new QLPhong(new javax.swing.JFrame(), true);
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
    private javax.swing.JTable TableLoaiGia;
    private javax.swing.JTable TableLoaiPhong;
    private javax.swing.JTable TableNhanVien;
    private javax.swing.JButton bnThemLG;
    private javax.swing.JButton btnMoiLG;
    private javax.swing.JButton btnMoiLP;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnSuaLG;
    private javax.swing.JButton btnSuaLP;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemLP;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaLG;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable1;
    private javax.swing.JRadioButton rdoBaoChi;
    private javax.swing.JRadioButton rdoTrong;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtGiaGio;
    private javax.swing.JTextField txtGiaNgay;
    private javax.swing.JTextField txtMaGP;
    private javax.swing.JTextField txtMaLP;
    private javax.swing.JTextField txtMaLoaiGia;
    private javax.swing.JTextField txtMaLoaiPhong;
    private javax.swing.JTextField txtMaP;
    private javax.swing.JTextField txtSoGiuong;
    private javax.swing.JTextField txtTenLP;
    private javax.swing.JButton txtXoaLP;
    // End of variables declaration//GEN-END:variables
}
