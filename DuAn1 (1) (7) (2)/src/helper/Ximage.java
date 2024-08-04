/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import Endity.TaiKhoan;
import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;

/**
 *
 * @author truon
 */
public class Ximage {
        public static Image getAppIcon(){
            URL url = Ximage.class.getResource("/Hinh/fpt.png");
            return new ImageIcon(url).getImage();
        }
        public static boolean save(File src){
            File dst =  new File("src\\Logo", src.getName());
            if(!dst.getParentFile().exists()){
                dst.getParentFile().mkdirs();// tao thu muc
            }
            try {
                Path from = Paths.get(src.getAbsolutePath());
                Path to = Paths.get(dst.getAbsolutePath());
                Files.copy(from, to,StandardCopyOption.REPLACE_EXISTING);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        public static TaiKhoan user=null;
        
}
