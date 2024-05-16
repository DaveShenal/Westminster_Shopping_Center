package oop.java;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class ShoppingCentreGUI extends JFrame {
    private  JLabel id,category,name,additional1,additional2,availability;
    private JTable productsTable;
    private TableRowSorter<ProductTableModel> sorter;
    private  final Font headerFont = new Font("Arial", Font.BOLD, 12);
    private final Font bodyFont = new Font("Arial", Font.PLAIN, 12);


    public ShoppingCentreGUI(ArrayList<Product> productList, ShoppingCart shoppingCart) {
        CartTableModel cartTableModel = new CartTableModel(shoppingCart);
        initializeComponents(productList, shoppingCart,cartTableModel);

    }

    private void initializeComponents(ArrayList<Product> productList, ShoppingCart shoppingCart, CartTableModel cartTableModel) {
        // Initialize Cart GUI
        ShoppingCartGUI shoppingCartGUI = initializeCartGUI(cartTableModel,shoppingCart, productList);

        // Create and add components to sub panels of Top and Bottom panels (based on the oder to variables should be initialized);
        JPanel productDetails = createProductDetailsPanel(bodyFont);
        JPanel tablePanel = createTablePanel(productList,headerFont);
        JPanel buttonsPanel = createButtonsPanel(shoppingCartGUI,shoppingCart,productList,cartTableModel);
        JPanel selectProductPanel = createSelectProductPanel();
        JPanel cartButtonPanel = createCartButtonPanel(shoppingCartGUI);
        JPanel selectProductAndCartBtn = createSelectProductAndCartBtnPanel(selectProductPanel,cartButtonPanel);

        // Create and add components to Top and Bottom Panels
        JPanel topPanel = createTopPanel(tablePanel,selectProductAndCartBtn);
        JPanel bottomPanel = createBottomPanel(productDetails,buttonsPanel);

        add(topPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        addEventListenerToMainGUI(shoppingCartGUI);

    }

    private void addEventListenerToMainGUI(ShoppingCartGUI shoppingCartGUI) {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                shoppingCartGUI.dispose();
            }
        });


    }

    private ShoppingCartGUI initializeCartGUI(CartTableModel cartTableModel,ShoppingCart shoppingCart,
                                              ArrayList<Product> productList) {
        ShoppingCartGUI shoppingCartGUI = new ShoppingCartGUI(cartTableModel,shoppingCart,productList,this);
        shoppingCartGUI.setTitle("Shopping Cart");
        shoppingCartGUI.setSize(700, 500);
        shoppingCartGUI.setVisible(false);

        return shoppingCartGUI;
    }

    private JPanel createTopPanel(JPanel tablePanel, JPanel selectProductAndCartBtn) {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        topPanel.add(selectProductAndCartBtn,BorderLayout.NORTH);

        topPanel.add(tablePanel,BorderLayout.CENTER);

        return topPanel;
    }

    private JPanel createBottomPanel(JPanel productDetails,JPanel buttonsPanel) {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        // add components to bottomPanel
        bottomPanel.add(productDetails,BorderLayout.CENTER);
        bottomPanel.add(buttonsPanel,BorderLayout.SOUTH);

        return bottomPanel;
    }

    private JPanel createProductDetailsPanel(Font bodyFont) {
        JPanel productDetails = new JPanel();
        productDetails.setLayout(new GridLayout(7,1,10,10));
        productDetails.setBorder(new EmptyBorder(0, 30, 15, 10));

        JLabel productDetailsHeader = new JLabel("Selected Product - Details");

        id = new JLabel("");
        category = new JLabel("");
        name = new JLabel("");
        additional1 = new JLabel("");
        additional2 = new JLabel("");
        availability = new JLabel("");

        id.setFont(bodyFont);
        category.setFont(bodyFont);
        name.setFont(bodyFont);
        additional1.setFont(bodyFont);
        additional2.setFont(bodyFont);
        availability.setFont(bodyFont);

        productDetails.add(productDetailsHeader);
        productDetails.add(id);
        productDetails.add(category);
        productDetails.add(name);
        productDetails.add(additional1);
        productDetails.add(additional2);
        productDetails.add(availability);

        return productDetails;
    }

    private JPanel createButtonsPanel(ShoppingCartGUI shoppingCartGUI,ShoppingCart shoppingCart,
                                      ArrayList<Product> productList,CartTableModel cartTableModel) {
        JPanel buttonsPanel = new JPanel(new GridLayout(2,1,0,5));
        buttonsPanel.setBorder(new EmptyBorder(0,240,10,240));
        // components

        JButton addToCartBtn = new JButton("Add to Shopping Cart");
        JButton removeFromCartBtn = new JButton("Remove");
        removeFromCartBtn.setForeground(Color.GRAY);

        // adding action listener to addToCart button
        AddAndRemoveBtnHandler addAndRemoveBtnHandler = new AddAndRemoveBtnHandler(productsTable,
                productList, shoppingCart,cartTableModel,shoppingCartGUI, addToCartBtn, removeFromCartBtn,availability);
        addToCartBtn.addActionListener(addAndRemoveBtnHandler);
        // adding action listener to removeFromCart button
        removeFromCartBtn.addActionListener(addAndRemoveBtnHandler);

        productsTable.getSelectionModel().addListSelectionListener(new ProductTableListener(productsTable,
                productList,shoppingCart.getQuantityOnCart(),id,category,name,additional1,additional2,availability,
                removeFromCartBtn, addToCartBtn));

        buttonsPanel.add(addToCartBtn);
        buttonsPanel.add(removeFromCartBtn);

        return buttonsPanel;
    }



    private JPanel createSelectProductAndCartBtnPanel(JPanel selectProductPanel,JPanel cartButtonPanel) {
        JPanel selectProductAndCartBtn = new JPanel();
        selectProductAndCartBtn.setLayout(new BorderLayout());
        // adding components to selectProductAndCartBtn panel
        selectProductAndCartBtn.add(selectProductPanel,BorderLayout.CENTER);
        selectProductAndCartBtn.add(cartButtonPanel,BorderLayout.EAST);

        return selectProductAndCartBtn;
    }

    private JPanel createSelectProductPanel() {
        JPanel selectProductPanel = new JPanel();
        selectProductPanel.setLayout(new FlowLayout(FlowLayout.LEFT,20,30));
        // components of selectProductPanel
        JLabel selectProductCategory = new JLabel("  Select Product Category");
        String[] categories = {"All","Electronics","Clothing"};
        JComboBox<String> selectProductCategoryComboBox = new JComboBox<>(categories);
        selectProductCategoryComboBox.setPreferredSize(new Dimension(120,20));

        // Action Listener to filtering products on products table
        selectProductCategoryComboBox.addActionListener(new ProductCategoryComboBoxHandler
                (selectProductCategoryComboBox,productsTable,sorter));

        // adding components to selectProductPanel
        selectProductPanel.add(selectProductCategory);
        selectProductPanel.add(selectProductCategoryComboBox);

        return selectProductPanel;
    }

    private JPanel createCartButtonPanel(ShoppingCartGUI shoppingCartGUI) {
        JPanel cartButtonPanel = new JPanel();
        cartButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton cartBtn = new JButton("Shopping Cart");
        cartBtn.setPreferredSize(new Dimension(120,30));
        // adding action listener to cart button
        cartBtn.addActionListener(new CartBtnHandler(shoppingCartGUI));
        cartButtonPanel.add(cartBtn);

        return cartButtonPanel;
    }

    private JPanel createTablePanel(ArrayList<Product> productList,Font headerFont) {
        JPanel tablePanel = new JPanel();
        tablePanel.setBorder(new EmptyBorder(40,0,10,0));
        tablePanel.setLayout(new BorderLayout());
        // components of table panel
        ProductTableModel tableModel = new ProductTableModel(productList);
        productsTable = new JTable(tableModel);

        // set header attributes
        JTableHeader tableHeader = productsTable.getTableHeader();
        tableHeader.setReorderingAllowed(false); // disable reordering
        tableHeader.setFont(headerFont); // set font to bold
        tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(), 35)); // set header height

        // custom renderer to each column of the table
        //    -to change the colour of the cell if number of available items is lower than 3
        //    -to set the alignment center for each cell
        for (int i = 0; i < productsTable.getColumnCount(); i++) {
            productsTable.getColumnModel().getColumn(i).setCellRenderer(new CustomRowRenderer());
        }

        productsTable.setRowHeight(30); // set row height
        productsTable.getColumnModel().getColumn(4).setMinWidth(150); // set min width for "Info"(5th) column

        sorter = new TableRowSorter<>(tableModel);
        productsTable.setRowSorter(sorter);

        // add components to table panel
        tablePanel.add(new JScrollPane(productsTable),BorderLayout.CENTER);

        return tablePanel;
    }
}

