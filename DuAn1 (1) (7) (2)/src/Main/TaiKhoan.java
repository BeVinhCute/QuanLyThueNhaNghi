/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/AWTForms/Dialog.java to edit this template
 */
package Main;

import Dao.TaiKhoanDao;
import Helper.ShareHelper;
import helper.DialogHelper;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Dell
 */
public class TaiKhoan extends java.awt.Dialog {
      int row = -1;
    TaiKhoanDao dao = new TaiKhoanDao();// lam viec voi csdl
    private DefaultTableModel tblModel;
    /**
     * Creates new form TaiKhoan
     */
    public TaiKhoan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }
     private void initTable() {
        tblModel = new DefaultTableModel();
        Object[] Column = new Object[]{"Mã TK", "Tên TK", "Mật Khẩu", "Vai Trò"};
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
        title("EduSys - Quản Lý Nhân Viên");
        initTable();
        fillTable();
        this.row = -1;
    }

    private void fillTable() {
        DefaultTableModel model = (DefaultTableModel) TableNhanVien.getModel();
        model.setRowCount(0);
        // đăng nhập nếu là trưởng phòng thì có thể thấy đc mật khẩu

        try {
            List<Endity.TaiKhoan> list = dao.selectAll();
            for (Endity.TaiKhoan nv : list) {
                Object[] row = {nv.getMaTK(), nv.getTenTk(), nv.getMatKhau(), nv.isVaiTro() ? "Quản Lý" : "Nhân viên"};
                model.addRow(row);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    private boolean check_input() {                                                                                                                                                                                                                                                                                         
        if (txtMaTK.getText().length() > 20 || txtMaTK.getText().equals("")) {
            System.out.println("mã");
            return false;
        }
        if (new String(txtMatKhau.getPassword()).equals("") || txtMatKhau.getPassword().length < 3) {
            System.out.println("Pass");
            return false;
        }

        return true;
    }

    void setForm(Endity.TaiKhoan tk) {// load du lieu từ table len form
        txtMaTK.setText(tk.getMaTK());
        txtTenTK.setText(tk.getTenTk());
        txtMatKhau.setText(tk.getMatKhau());
        rdQuanLy.setSelected(tk.isVaiTro());
        rdNhanVien.setSelected(!tk.isVaiTro());
    }

    void clearForm() {//xoa trang form
        Endity.TaiKhoan nv = new Endity.TaiKhoan();
        this.setForm(nv);
        this.row = -1;
    }

    Endity.TaiKhoan getForm() {// tao nhan vien tu form
        if (check_input()) {
            Endity.TaiKhoan nv = new Endity.TaiKhoan();
            nv.setMaTK(txtMaTK.getText());
            nv.setTenTk(txtTenTK.getText());
            nv.setMatKhau(new String(txtMatKhau.getPassword()));            
            nv.setVaiTro(rdQuanLy.isSelected());
            return nv;
        } else {
            DialogHelper.alert(this, "Lỗi Dữ Liệu");
            return null;
        }
    }

    void edit() {
        String manv = (String) TableNhanVien.getValueAt(this.row, 0);
        Endity.TaiKhoan nv = dao.selectByID(manv);
        this.setForm(nv);
    }

    void insert() {
        Endity.TaiKhoan tk = getForm();
        if (tk != null) {
            try {
                dao.insert(tk);
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
        Endity.TaiKhoan tk = getForm();

        if (tk != null) {
            try {
                dao.update(tk);
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
            String manv = txtMaTK.getText();
            if (manv.equals(ShareHelper.user.getMaTK())) {
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtMaTK = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        rdQuanLy = new javax.swing.JRadioButton();
        rdNhanVien = new javax.swing.JRadioButton();
        txtMatKhau = new javax.swing.JPasswordField();
        jLabel8 = new javax.swing.JLabel();
        txtTenTK = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableNhanVien = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(149, 207, 202));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Thông tin tài khoản ");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin tài khoản\n"));

        jLabel3.setText("Mã tài khoản");

        jLabel6.setText("Mật khẩu ");

        jLabel5.setText("Vai Trò");

        rdQuanLy.setText("Quản lý");

        rdNhanVien.setText("Nhân viên");

        jLabel8.setText("Tên tài khoản");

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
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaTK, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtMatKhau)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(rdQuanLy, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rdNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(btnThem)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnSua)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnXoa)))))
                        .addGap(0, 7, Short.MAX_VALUE))
                    .addComponent(txtTenTK))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdNhanVien)
                    .addComponent(rdQuanLy))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnXoa))
                .addGap(20, 20, 20))
        );

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
        jScrollPane1.setViewportView(TableNhanVien);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));

        jLabel1.setBackground(new java.awt.Color(0, 51, 51));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText(" TÀI KHOẢN");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(294, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(365, 365, 365))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(24, 24, 24))
        );

        add(jPanel2, java.awt.BorderLayout.NORTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TaiKhoan dialog = new TaiKhoan(new java.awt.Frame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdNhanVien;
    private javax.swing.JRadioButton rdQuanLy;
    private javax.swing.JTextField txtMaTK;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtTenTK;
    // End of variables declaration//GEN-END:variables
}
