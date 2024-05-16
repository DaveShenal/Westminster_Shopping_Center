package oop.java;

public abstract class Product  {
    private final String productID;
    private final String productName;
    private final double price;
    private int numOfAvailable;
    private final boolean clothingItem;

    public Product(String productId, String productName, double price,int numOfAvailable, boolean clothingItem) {
        this.productID = productId;
        this.productName = productName;
        this.price = price;
        this.numOfAvailable = numOfAvailable;
        this.clothingItem = clothingItem;
    }

    // getter for productID
    public String getProductID() {
        return productID;
    }

    // getter for productName
    public String getProductName() {
        return productName;
    }


    // getter for price
    public double getPrice() {
        return price;
    }


    // getter and setter for numOfAvailable
    public int getNumOfAvailable() {
        return numOfAvailable;
    }
    public void setNumOfAvailable(int numOfAvailable) {
        this.numOfAvailable = numOfAvailable;
    }

    // getter for clothingItem
    public boolean isClothingItem() {
        return clothingItem;
    }



}
