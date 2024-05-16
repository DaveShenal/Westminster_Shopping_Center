package oop.java;

import java.util.ArrayList;

public class User {
    private final String username;
    private final String password;
    private final ArrayList<Purchase> purchaseHistory;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.purchaseHistory = new ArrayList<>(3);
    }

    // getter for username
    public String getUsername() {
        return username;
    }

    // getter for password
    public String getPassword() {
        return password;
    }

    public ArrayList<Purchase> getPurchaseHistory() {
        return purchaseHistory;
    }

    public boolean firstPurchase() {
        return this.purchaseHistory.isEmpty();
    }

    public void addPurchase(Purchase purchase) {
        if (purchaseHistory.size() == 3) {
            purchaseHistory.remove(0); // Remove the first element
            purchaseHistory.add(purchase);   // Add a new element at the end
        } else {
            purchaseHistory.add(purchase);
        }
    }
}
