/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helper;

import Endity.TaiKhoan;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * @NhanVien User: Biến user kiểu Dữ Liệu Nhân Viên
 * @logoff(): Xóa Thông Tin Đăng Nhập
 * @authenticated(): Kiểm tra đăng nhập hay chưa
 * @getAPP_Icon(): Biểu Tượng Ứng Dụng
 * @saveLogo(): Lưu ảnh Logo chuyên Đề
 * @readLogo(): Đọc ảnh Logo chuyên đề
 *
 */
public class ShareHelper {
    
    public static TaiKhoan user = null;

    //Xóa user khi người dùng đăng xuất
    public static void logoff() {
        ShareHelper.user = null;
    }

    //Kiểm tra đăng nhập hay chưa?
    public static boolean authenticated() {
        return ShareHelper.user != null;//user khác null =>Đã đăng nhập
    }

    //Kiểm tra vai trò trưởng phòng hay nhân viên 
    public static boolean isManager() {
        return ShareHelper.authenticated() && user.isVaiTro();
    }

    //học lại static vs non static
    //access modifiers: private, default, protected, public
    public static Image getAppIcon() {
        try {

            File file = new File("src\\Hinh\\fpt.png");
            return ImageIO.read(file);
        } catch (IOException ex) {
            Logger.getLogger(ShareHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
//        return new ImageIcon(url).getImage();
        return null;
    }

    public static void saveLogo(File src) {
        File dst = new File("logos", src.getName());
        if (!dst.getParentFile().exists()) {
            dst.getParentFile().mkdirs();
        }
        try {
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(dst.getAbsolutePath());
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static ImageIcon readLogo(String fileName) {
        File path = new File("logos", fileName);
        return new ImageIcon(path.getAbsolutePath());
    }
}
