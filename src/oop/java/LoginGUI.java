package oop.java;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoginGUI extends JFrame implements ActionListener {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private  final JLabel message;
    private final ArrayList<User> usersList;
    private final ArrayList<Product> productsList;
    private final Color correctIndicatorColour = new Color(100, 200, 200);
    private final Color errorIndicatorColour = new Color(255,100,100);

    public LoginGUI(ArrayList<User> users,ArrayList<Product> productsList) {
        this.usersList = users;
        this.productsList = productsList;

        Font headerFont = new Font("Arial", Font.BOLD, 13);
        // Set JFrame properties
        setTitle("Login or Register");
        setSize(500, 500);
        setResizable(false);
        setVisible(true);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(2,2,25,20));
        topPanel.setBorder(new EmptyBorder(110,0,40,95));
        JLabel username = new JLabel("Username :");
        username.setFont(headerFont);
        username.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel password = new JLabel("Password :");
        password.setFont(headerFont);
        password.setHorizontalAlignment(SwingConstants.RIGHT);
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        usernameField.setFont(headerFont);
        passwordField.setFont(headerFont);
        usernameField.setHorizontalAlignment(SwingConstants.CENTER);
        passwordField.setHorizontalAlignment(SwingConstants.CENTER);


        topPanel.add(username);
        topPanel.add(usernameField);
        topPanel.add(password);
        topPanel.add(passwordField);

        JButton loginButton = new JButton("Sign In");
        JButton registerButton = new JButton("Register");

        message = new JLabel("    ");
        message.setBorder(new EmptyBorder(20,0,0,0));
        message.setFont(headerFont);
        message.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1,2,20,20));
        buttonPanel.setBorder(new EmptyBorder(0,150,20,150));
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(new EmptyBorder(0,0,150,0));

        bottomPanel.add(buttonPanel,BorderLayout.CENTER);
        bottomPanel.add(message,BorderLayout.SOUTH);

        // Add action listeners
        loginButton.addActionListener(this);
        registerButton.addActionListener(this);

        // Add components to the JFrame
        add(topPanel,BorderLayout.CENTER);
        add(bottomPanel,BorderLayout.SOUTH);

    }


    public void actionPerformed(ActionEvent event) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword()); // Convert char[] to String for password

        if (event.getActionCommand().equals("Sign In")) signInHandle(username,password);
        else if (event.getActionCommand().equals("Register")) registerHandle(username,password);


    }
    private void signInHandle(String username, String password){
        for(User user: usersList){
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){

                this.dispose();
                ShoppingCart shoppingCart = new ShoppingCart(user);
                ShoppingCentreGUI shoppingCentreGUI = new ShoppingCentreGUI(productsList,shoppingCart);
                shoppingCentreGUI.setTitle("Westminster Shopping Center");
                shoppingCentreGUI.setSize(700,700);
                shoppingCentreGUI.setMinimumSize(new Dimension(600, 400));
                shoppingCentreGUI.setVisible(true);
                return;
            }
            else if(user.getUsername().equals(username)){
                message.setText("Password is incorrect");
                passwordField.setText("");
                passwordField.setBackground(errorIndicatorColour);
                this.repaint();
                return;
            }
        }
        usernameField.setText("");
        passwordField.setText("");
        usernameField.setBackground(errorIndicatorColour);
        passwordField.setBackground(errorIndicatorColour);
        message.setText("Incorrect username");

        this.repaint();
    }


    private void registerHandle(String username, String password){
        boolean newUser = true;
        for(User user: usersList){
            if(user.getUsername().equals(username)){
                newUser = false;
                break;
            }
        }

        if(!username.isEmpty() && !password.isEmpty() && newUser){
            try{
                usersList.add(new User(username,password));
            }catch (Exception error){
                System.out.println("Error : "+error);
            }
            usernameField.setBackground(correctIndicatorColour);
            passwordField.setBackground(correctIndicatorColour);
            message.setText("Registered");
        } else if (!newUser) {
            usernameField.setBackground(errorIndicatorColour);
            message.setText("This username is already taken");
        }
        else if(username.isEmpty() && password.isEmpty()){
            message.setText("Please enter the username and password");
            usernameField.setBackground(errorIndicatorColour);
            passwordField.setBackground(errorIndicatorColour);

        } else if (password.isEmpty()) {
            message.setText("Please enter the password");
            passwordField.setBackground(errorIndicatorColour);

        }
        else {
            message.setText("Please enter the username");
            usernameField.setBackground(errorIndicatorColour);
        }
    }
}

