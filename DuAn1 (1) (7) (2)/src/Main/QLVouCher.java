/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Main;

import Dao.DichVuDao;
import Dao.KhuyenMaiDao;
import Endity.DichVu;
import Endity.KhuyenMai;
import Helper.ShareHelper;
import helper.DateHelper;
import helper.DialogHelper;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admin
 */
public class QLVouCher extends javax.swing.JDialog {
    int row = -1;
    KhuyenMaiDao dao = new KhuyenMaiDao();// lam viec voi csdl
    private DefaultTableModel tblModel;
    /**
     * Creates new form QLNV
     */
    public QLVouCher(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }
private void initTable() {
        tblModel = new DefaultTableModel();
        Object[] Column = new Object[]{"MAKM", "Tên Khuyến mại", "Giá dịch vụ", "Ngày hết hạn","Trạng thái"};
        tblModel.setColumnIdentifiers(Column);
        TableNhanVien.setModel(tblModel);

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
        title("QLNN - Quản Lý Khuyến Mại");
        initTable();
        fillTable();
        this.row = -1;
    }

    private void fillTable() {
        DefaultTableModel model = (DefaultTableModel) TableNhanVien.getModel();
        model.setRowCount(0);
        // đăng nhập nếu là trưởng phòng thì có thể thấy đc mật khẩu

        try {
            List<KhuyenMai> list = dao.selectAll();
            for (KhuyenMai kh : list) {
                Object[] row = {kh.getMaVoucher(), kh.getTenVoucher(), kh.getGiaTri() ,kh.isTrangThai() ? "Còn Sử dụng" : "Hết hạn"};
                model.addRow(row);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
       private boolean check_input() {                                                                                                                                                                                                                                                                                         
        if (txtMaKM.getText().length() > 20 || txtMaKM.getText().equals("")) {
            System.out.println("mã");
            return false;
        }
        if (txtTenKM.getText().length()>20 || txtTenKM.getText().equals("") ) {
            System.out.println("Pass");
            return false;
        }
        return true;
    }

    void setForm(KhuyenMai nv) {// load du lieu từ table len form
        txtMaKM.setText(nv.getMaVoucher());
        txtTenKM.setText(nv.getTenVoucher());
        txtGiaTri.setText(String.valueOf(nv.getGiaTri()));
        rdSD.setSelected(nv.isTrangThai());
        rdHH.setSelected(!nv.isTrangThai());
    }

    void clearForm() {//xoa trang form
        KhuyenMai nv = new KhuyenMai();
        this.setForm(nv);
        this.row = -1;
    }

        KhuyenMai getForm() {// tao nhan vien tu form
        if (check_input()) {
            KhuyenMai nv = new KhuyenMai();
            nv.setMaVoucher(txtMaKM.getText());
            nv.setTenVoucher(txtTenKM.getText());
            nv.setGiaTri(Integer.valueOf(txtGiaTri.getText()));
            nv.setTrangThai(rdSD.isSelected());
            return nv;
        } else {
            DialogHelper.alert(this, "Lỗi Dữ Liệu");
            return null;
        }
    }

    void edit() {
        String manv = (String) TableNhanVien.getValueAt(this.row, 0);
        KhuyenMai nv = dao.selectByID(manv);
        this.setForm(nv);
    }

    void insert() {
         KhuyenMai nv = getForm();

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
        KhuyenMai nv = getForm();

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
            DialogHelper.alert(this, "Bạn không có quyền xoá nv ! ");
        } else {
            String manv = txtMaKM.getText();
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableNhanVien = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMaKM = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTenKM = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtGiaTri = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtNgayHetHan = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        rdSD = new javax.swing.JRadioButton();
        rdHH = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

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
        jScrollPane2.setViewportView(TableNhanVien);

        jPanel3.setBackground(new java.awt.Color(0, 102, 102));

        jLabel2.setBackground(new java.awt.Color(0, 102, 102));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 255, 255));
        jLabel2.setText("Thông tin khuyến mại");

        jLabel3.setBackground(new java.awt.Color(0, 102, 102));
        jLabel3.setForeground(new java.awt.Color(204, 255, 255));
        jLabel3.setText("Mã khuyến mại");

        jLabel4.setBackground(new java.awt.Color(0, 102, 102));
        jLabel4.setForeground(new java.awt.Color(204, 255, 255));
        jLabel4.setText("Tên khuyến mại");

        jLabel5.setBackground(new java.awt.Color(0, 102, 102));
        jLabel5.setForeground(new java.awt.Color(204, 255, 255));
        jLabel5.setText("Giá trị");

        jLabel6.setBackground(new java.awt.Color(0, 102, 102));
        jLabel6.setForeground(new java.awt.Color(204, 255, 255));
        jLabel6.setText("Ngày hết hạn");

        jLabel7.setBackground(new java.awt.Color(0, 102, 102));
        jLabel7.setForeground(new java.awt.Color(204, 255, 255));
        jLabel7.setText("Trạng thái");

        rdSD.setBackground(new java.awt.Color(0, 102, 102));
        rdSD.setForeground(new java.awt.Color(204, 255, 255));
        rdSD.setText("Còn sử dụng");

        rdHH.setBackground(new java.awt.Color(0, 102, 102));
        rdHH.setForeground(new java.awt.Color(204, 255, 255));
        rdHH.setText("Hết hạn");

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
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(rdSD, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdHH, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtNgayHetHan, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(txtGiaTri)
                            .addComponent(txtTenKM)
                            .addComponent(txtMaKM)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoa)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaKM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenKM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtGiaTri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addGap(7, 7, 7)
                .addComponent(txtNgayHetHan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdSD)
                    .addComponent(rdHH))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnXoa))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));

        jLabel1.setBackground(new java.awt.Color(0, 102, 102));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 255, 255));
        jLabel1.setText("Quản lý khuyến mại");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(285, 285, 285)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addContainerGap(25, Short.MAX_VALUE))
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here\
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
            java.util.logging.Logger.getLogger(QLVouCher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QLVouCher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QLVouCher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLVouCher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QLVouCher dialog = new QLVouCher(new javax.swing.JFrame(), true);
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
    private javax.swing.JTable TableNhanVien;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rdHH;
    private javax.swing.JRadioButton rdSD;
    private javax.swing.JTextField txtGiaTri;
    private javax.swing.JTextField txtMaKM;
    private javax.swing.JTextField txtNgayHetHan;
    private javax.swing.JTextField txtTenKM;
    // End of variables declaration//GEN-END:variables
}
