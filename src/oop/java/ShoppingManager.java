package oop.java;

import java.util.ArrayList;

public interface ShoppingManager {
    public abstract void displayMenu();
    public abstract void addNewProduct();
    public abstract void deleteProduct();
    public abstract void printProductList();
    public abstract void saveToFile(boolean append);
    public abstract void readFromFile();

}
