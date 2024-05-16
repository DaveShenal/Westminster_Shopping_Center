package oop.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class AddAndRemoveBtnHandler implements ActionListener {
    private final JTable productsTable;
    private final ArrayList<Product> productList;
    private final ShoppingCart shoppingCart;
    private final CartTableModel cartTableModel;
    private final ShoppingCartGUI shoppingCartGUI;
    private final JButton addToCartBtn;
    private final JButton removeFromCartBtn;
    private final JLabel availability;

    public AddAndRemoveBtnHandler(JTable productsTable,ArrayList<Product> productList,ShoppingCart shoppingCart,
                               CartTableModel cartTableModel,ShoppingCartGUI shoppingCartGUI, JButton addToCartBtn,
                               JButton removeFromCartBtn, JLabel availability){
        this.productsTable = productsTable;
        this.productList = productList;
        this.shoppingCart = shoppingCart;
        this.cartTableModel = cartTableModel;
        this.shoppingCartGUI = shoppingCartGUI;
        this.addToCartBtn = addToCartBtn;
        this.removeFromCartBtn = removeFromCartBtn;
        this.availability = availability;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getActionCommand().equals("Add to Shopping Cart")){
            int selectedRow = productsTable.getSelectedRow();
            if(selectedRow != -1) {
                String selectedProductId = productsTable.getValueAt(selectedRow, 0).toString();
                Map<Product, Integer> quantityOnCart = shoppingCart.getQuantityOnCart();

                // get the selected product and check its availability
                for (Product checker : productList) {
                    if (checker.getProductID().equals(selectedProductId) && checker.getNumOfAvailable()>0) {
                        int availableItems;
                        if(quantityOnCart.containsKey(checker)){
                            availableItems = checker.getNumOfAvailable() - quantityOnCart.get(checker);
                        }
                        else availableItems = checker.getNumOfAvailable();

                        if (availableItems > 0) {
                            if (availableItems == 1){
                                JButton addToCartBtn = (JButton)event.getSource();
                                addToCartBtn.setForeground(Color.GRAY);
                            }

                            shoppingCart.addToCart(checker);
                            shoppingCartGUI.updatePrices();
                            cartTableModel.fireTableDataChanged();
                            removeFromCartBtn.setForeground(Color.BLACK);
                            availability.setText("Items Available: "+ (availableItems-1));
                        }
                        break;
                    }
                }

            }
        }

        if(event.getActionCommand().equals("Remove")){
            int selectedRow = productsTable.getSelectedRow();
            if(selectedRow != -1) {
                String selectedProductId = productsTable.getValueAt(selectedRow, 0).toString();
                Map<Product, Integer> quantityOnCart = shoppingCart.getQuantityOnCart();

                // get the selected product and check its selected quantity on cart
                for (Product checker : productList) {
                    if (checker.getProductID().equals(selectedProductId) && quantityOnCart.containsKey(checker)) {

                        int quantity = quantityOnCart.get(checker);
                        if(quantity == 1){
                            removeFromCartBtn.setForeground(Color.GRAY);
                        }
                        shoppingCart.removeFromCart(checker);
                        addToCartBtn.setForeground(Color.BLACK);
                        availability.setText("Items Available: "+ (checker.getNumOfAvailable() - (quantity-1)));
                        shoppingCartGUI.updatePrices();
                        cartTableModel.fireTableDataChanged();

                        break;
                    }
                }

            }
        }
    }
}
