package oop.java;

import javax.swing.*;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductCategoryComboBoxHandler implements ActionListener {
    JComboBox<String> selectProductCategoryComboBox;
    JTable productsTable;
    TableRowSorter<ProductTableModel> sorter;

    public ProductCategoryComboBoxHandler(JComboBox<String> selectProductCategoryComboBox,JTable productsTable,TableRowSorter<ProductTableModel> sorter){
        this.selectProductCategoryComboBox = selectProductCategoryComboBox;
        this.productsTable = productsTable;
        this.sorter = sorter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedIndex = selectProductCategoryComboBox.getSelectedIndex();
        if(selectedIndex == 1){
            sorter.setRowFilter(RowFilter.regexFilter("^[E]", 2)); // Hide clothing items
        } else if (selectedIndex == 2) {
            sorter.setRowFilter(RowFilter.regexFilter("^[C]",2));
        }
        else sorter.setRowFilter(null);
    }
}
