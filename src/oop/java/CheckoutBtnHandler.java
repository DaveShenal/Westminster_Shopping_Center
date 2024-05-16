package oop.java;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

public class CheckoutBtnHandler implements ActionListener {
    private final ShoppingCart shoppingCart;
    private final ArrayList<Product> productList;
    private final ShoppingCentreGUI shoppingCentreGUI;
    private final ShoppingCartGUI shoppingCartGUI;

    public CheckoutBtnHandler(ShoppingCart shoppingCart, ArrayList<Product> productList, ShoppingCentreGUI shoppingCentreGUI,
                              ShoppingCartGUI shoppingCartGUI) {
        this.shoppingCart = shoppingCart;
        this.productList = productList;
        this.shoppingCentreGUI = shoppingCentreGUI;
        this.shoppingCartGUI = shoppingCartGUI;


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to proceed with purchasing the selected products?",
                "Purchase Confirmation", JOptionPane.YES_NO_OPTION);

        if(result == 0) {
            JOptionPane.showMessageDialog(null, "Thank you for confirming. Processing your purchase...");
            Map<Product, Integer> quantityOnCart = shoppingCart.getQuantityOnCart();
            for(Product productOnCart : shoppingCart.getProductsOnCart()){
                if(productOnCart.getNumOfAvailable() == quantityOnCart.get(productOnCart)){
                    productList.remove(productOnCart);
                }
                else {
                    int numOfAvailable = productOnCart.getNumOfAvailable() - quantityOnCart.get(productOnCart);
                    productOnCart.setNumOfAvailable(numOfAvailable);
                }
            }

            User user = shoppingCart.getUser();
            user.addPurchase(new Purchase(shoppingCart.getFinalPrice(), LocalDateTime.now()));
            shoppingCart.clearCart();
            shoppingCartGUI.dispose();
            shoppingCentreGUI.dispose();
        }
        else {
            JOptionPane.showMessageDialog(null, "Your purchase has been canceled.");
            shoppingCentreGUI.repaint();
        }
    }
}
