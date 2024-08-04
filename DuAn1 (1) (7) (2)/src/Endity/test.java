/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Endity;

/**
 *
 * @author admin
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class test {
    private JFrame frame;
    private JPanel mainPanel;

    
public class DateTimeDifferenceExample {

private void btnTraPhongActionPerformed(java.awt.event.ActionEvent evt) {
    int row = tblThuePhong.getSelectedRow();

    // Check if any row is selected
    if (row >= 0) {
        String mathuephong = (String) tblThuePhong.getValueAt(row, 0);
        String mp = (String) tblThuePhong.getValueAt(row, 3);
        
        // Check if column 6 has the value "3"
        Object statusValue = tblThuePhong.getValueAt(row, 5); // Assuming index 5 for column 6
        if (statusValue != null && statusValue.toString().equals("3")) {
            openHoaDon(mathuephong, mp);
        } else {
            // Handle the case where the status is not "3"
            JOptionPane.showMessageDialog(this, "Cannot proceed: Status is not 3.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        // Handle the case where no row is selected
        JOptionPane.showMessageDialog(this, "No row selected.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}


}


