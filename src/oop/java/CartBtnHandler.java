package oop.java;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CartBtnHandler implements ActionListener {
    ShoppingCartGUI shoppingCartGUI;

    public CartBtnHandler(ShoppingCartGUI shoppingCartGUI){
        this.shoppingCartGUI = shoppingCartGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!shoppingCartGUI.isActive()){
            shoppingCartGUI.setVisible(true);
        }
    }
}
