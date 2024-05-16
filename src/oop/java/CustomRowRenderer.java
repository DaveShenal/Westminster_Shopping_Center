package oop.java;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;

class CustomRowRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cellRenderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Convert view row index to model row index
        TableModel model = table.getModel();
        int modelRow = table.convertRowIndexToModel(row);

        // Retrieve the product from the table model based on the model row index
        ProductTableModel productModel = (ProductTableModel) model;
        Product product = productModel.getRowObject(modelRow);


        // Check if the product's availability
        if (product != null && product.getNumOfAvailable() < 4) {
            cellRenderer.setForeground(Color.RED); // Set background color to red
        } else {
            cellRenderer.setForeground(Color.BLACK);
        }
        this.setHorizontalAlignment(JLabel.CENTER);

        return cellRenderer;
    }
}

