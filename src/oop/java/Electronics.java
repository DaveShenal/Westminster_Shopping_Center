package oop.java;

public class Electronics extends Product {
    private final String brand;
    private final String warranty;

    // constructor
    public Electronics(String productID,String productName,double price,int numOfAvailable,boolean clothingItem,String brand,String warranty) {
        super(productID,productName,price,numOfAvailable,clothingItem);
        this.brand = brand;
        this.warranty = warranty;
    }

    // getter for brand
    public String getBrand() {
        return brand;
    }

    // getter for warranty
    public String getWarranty() {
        return warranty;
    }


}
