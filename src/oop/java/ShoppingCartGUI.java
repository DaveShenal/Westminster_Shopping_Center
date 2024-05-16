package oop.java;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;

public class ShoppingCartGUI extends JFrame {
    JLabel totalPrice,firstPurchaseDiscountPrice,threeItemsDiscountPrice,finalPrice;
    private final ShoppingCart shoppingCart;

    public ShoppingCartGUI(CartTableModel cartTableModel, ShoppingCart shoppingCart, ArrayList<Product> productList,
                           ShoppingCentreGUI shoppingCentreGUI){
        this.shoppingCart = shoppingCart;
        Font bodyFont = new Font("Arial", Font.PLAIN, 12);
        Font headerFont = new Font("Arial", Font.BOLD, 12);

        JPanel tablePanel = createTablePanel(cartTableModel,headerFont);
        JPanel bottomPanel = createBottomPanel(productList,shoppingCentreGUI,bodyFont);

        add(tablePanel,BorderLayout.CENTER);
        add(bottomPanel,BorderLayout.SOUTH);

    }
    private JPanel createTablePanel(CartTableModel cartTableModel,Font headerFont) {
        JPanel tablePanel = new JPanel();
        tablePanel.setBorder(new EmptyBorder(40,20,10,20));
        tablePanel.setLayout(new BorderLayout());

        JTable cartTable = new JTable(cartTableModel);
        JScrollPane scrollPane = new JScrollPane(cartTable);

        //set alignment of cell items to center
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for(int i=0; i<3; i++){
            cartTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        cartTable.setRowHeight(30); // set row height
        cartTable.getColumnModel().getColumn(0).setMinWidth(150); // set first column width

        // set header attributes
        JTableHeader tableHeader = cartTable.getTableHeader();
        tableHeader.setReorderingAllowed(false); // Disable reordering
        tableHeader.setFont(headerFont); // Set font to bold
        tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(), 35)); //Set size

        tablePanel.add(scrollPane,BorderLayout.CENTER);

        return tablePanel;
    }

    private JPanel createBottomPanel(ArrayList<Product> productList,ShoppingCentreGUI shoppingCentreGUI,Font bodyFont){
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(5,1,0,0));
        bottomPanel.setBorder(new EmptyBorder(10,20,10,100));

        //panels for 4 rows - each panel has 2 labels
        JPanel totalPanel,firstPurchasePanel,threeItemsPanel,finalTotalPanel,checkoutBtnPanel;

        totalPanel = new JPanel(new BorderLayout());
        firstPurchasePanel = new JPanel(new BorderLayout());
        threeItemsPanel = new JPanel(new BorderLayout());
        finalTotalPanel = new JPanel(new BorderLayout());

        // labels for fill the
        JLabel totalLabel,firstPurchaseLabel, threeItemsLabel,finalTotalLabel;

        // labels for prices description
        totalLabel = new JLabel("Total      ");
        firstPurchaseLabel = new JLabel("First Purchase Discount (10%)      ");
        threeItemsLabel = new JLabel("Three Items in same Category (20%)      ");
        finalTotalLabel = new JLabel("Final Total      ");

        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        firstPurchaseLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        threeItemsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        finalTotalLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // labels for prices values
        totalPrice = new JLabel("0 £");
        firstPurchaseDiscountPrice = new JLabel("-0 £");
        threeItemsDiscountPrice = new JLabel("-0 £");
        finalPrice = new JLabel("0 £");

        totalLabel.setFont(bodyFont);
        firstPurchaseLabel.setFont(bodyFont);
        threeItemsLabel.setFont(bodyFont);
        totalPrice.setFont(bodyFont);
        firstPurchaseDiscountPrice.setFont(bodyFont);
        threeItemsDiscountPrice.setFont(bodyFont);


        Dimension priceLabelsSize = new Dimension(100,20);
        totalPrice.setPreferredSize(priceLabelsSize);
        firstPurchaseDiscountPrice.setPreferredSize(priceLabelsSize);
        threeItemsDiscountPrice.setPreferredSize(priceLabelsSize);
        finalPrice.setPreferredSize(priceLabelsSize);

        //checkout button
        checkoutBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        checkoutBtnPanel.setBorder(new EmptyBorder(5,0,0,40));
        JButton checkoutBtn = new JButton("Checkout");
        checkoutBtn.addActionListener(new CheckoutBtnHandler(shoppingCart,productList,shoppingCentreGUI,this));
        checkoutBtnPanel.add(checkoutBtn);

        totalPanel.add(totalLabel,BorderLayout.CENTER);
        totalPanel.add(totalPrice,BorderLayout.EAST);
        firstPurchasePanel.add(firstPurchaseLabel,BorderLayout.CENTER);
        firstPurchasePanel.add(firstPurchaseDiscountPrice,BorderLayout.EAST);
        threeItemsPanel.add(threeItemsLabel,BorderLayout.CENTER);
        threeItemsPanel.add(threeItemsDiscountPrice,BorderLayout.EAST);
        finalTotalPanel.add(finalTotalLabel,BorderLayout.CENTER);
        finalTotalPanel.add(finalPrice,BorderLayout.EAST);

        bottomPanel.add(totalPanel);
        bottomPanel.add(firstPurchasePanel);
        bottomPanel.add(threeItemsPanel);
        bottomPanel.add(finalTotalPanel);
        bottomPanel.add(checkoutBtnPanel);
        return bottomPanel;
    }


    public void updatePrices(){
        this.totalPrice.setText(shoppingCart.calculateTotal()+" £");
        this.firstPurchaseDiscountPrice.setText("-" + shoppingCart.getFirstPurchaseDiscount() + " £");
        this.threeItemsDiscountPrice.setText("-"+ shoppingCart.getThreeSameItemsDiscount() +" £");
        this.finalPrice.setText(shoppingCart.getFinalPrice() +" £");
    }

}
