/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import helper.DateHelper;
import java.util.List;

/**
 *
 * @author admin
 */


import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class test {
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class FilterTableExample extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private ButtonGroup radioButtonGroup;
    private JComboBox<String> comboBox;

    public FilterTableExample() {
        // Initialize the frame
        setTitle("Filter Table Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create sample data
        Vector<Vector<Object>> data = new Vector<>();
        Vector<String> columnNames = new Vector<>();
        columnNames.add("ID");
        columnNames.add("Name");
        columnNames.add("Type");

        Vector<Object> row1 = new Vector<>();
        row1.add(1);
        row1.add("Item 1");
        row1.add("Type A");

        Vector<Object> row2 = new Vector<>();
        row2.add(2);
        row2.add("Item 2");
        row2.add("Type B");

        Vector<Object> row3 = new Vector<>();
        row3.add(3);
        row3.add("Item 3");
        row3.add("Type A");

        data.add(row1);
        data.add(row2);
        data.add(row3);

        // Create the table model
        tableModel = new DefaultTableModel(data, columnNames);

        // Create the table
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Create radio buttons
        JRadioButton typeARadioButton = new JRadioButton("Type A");
        JRadioButton typeBRadioButton = new JRadioButton("Type B");

        // Group the radio buttons
        radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(typeARadioButton);
        radioButtonGroup.add(typeBRadioButton);

        // Create a combo box
        comboBox = new JComboBox<>();
        comboBox.addItem("All");
        comboBox.addItem("Type A");
        comboBox.addItem("Type B");

        // Add radio buttons and combo box to a panel
        JPanel filterPanel = new JPanel();
        filterPanel.add(typeARadioButton);
        filterPanel.add(typeBRadioButton);
        filterPanel.add(comboBox);

        // Add the panel to the frame
        add(filterPanel, BorderLayout.SOUTH);

        // Add ItemListeners to radio buttons
        typeARadioButton.addItemListener(new RadioButtonListener("Type A"));
        typeBRadioButton.addItemListener(new RadioButtonListener("Type B"));

        // Add ActionListener to combo box
        comboBox.addActionListener(new ComboBoxListener());

        // Set up the initial filter
        filterData("All");

        // Pack and display the frame
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Helper method to filter data based on the selected radio button or combo box item
    private void filterData(String filter) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        List<RowFilter<Object, Object>> filters = new ArrayList<>();

        // Add radio button filter
        Enumeration<AbstractButton> buttons = radioButtonGroup.getElements();
        while (buttons.hasMoreElements()) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                String buttonText = button.getText();
                if (!filter.equals("All") && !buttonText.equals(filter)) {
                    // If the selected radio button doesn't match the combo box filter, return
                    return;
                }
                filters.add(RowFilter.regexFilter(buttonText, columnNames.indexOf("Type")));
            }
        }

        // Add combo box filter
        if (!filter.equals("All")) {
            filters.add(RowFilter.regexFilter(filter, columnNames.indexOf("Type")));
        }

        RowFilter<Object, Object> combinedFilter = RowFilter.andFilter(filters);
        sorter.setRowFilter(combinedFilter);
    }

    // ItemListener for radio buttons
    private class RadioButtonListener implements ItemListener {
        private String filter;

        public RadioButtonListener(String filter) {
            this.filter = filter;
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                filterData(filter);
            }
        }
    }

    // ActionListener for combo box
    private class ComboBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedFilter = (String) comboBox.getSelectedItem();
            filterData(selectedFilter);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FilterTableExample());
    }
}

}