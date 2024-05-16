package oop.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private final ArrayList<Product> productsOnCart = new ArrayList<>(50);
    private final Map<Product, Integer> quantityOnCart = new HashMap<>(50);
    private final User user;
    private static int numberOfElectronicsProducts = 0;
    private static int numberOfClothingProducts = 0;

    public ShoppingCart(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public ArrayList<Product> getProductsOnCart() {
        return productsOnCart;
    }

    public Map<Product, Integer> getQuantityOnCart() {
        return quantityOnCart;
    }

    public void addToCart(Product prodToAdd){
        if(prodToAdd.isClothingItem()) numberOfClothingProducts++;
        else numberOfElectronicsProducts++;

        if(productsOnCart.contains(prodToAdd)) {
            quantityOnCart.put(prodToAdd, quantityOnCart.get(prodToAdd) + 1);
        }
        else{
            productsOnCart.add(prodToAdd);
            quantityOnCart.put(prodToAdd,1);
        }
    }

    public void removeFromCart(Product productToRemove){
        if (productToRemove.isClothingItem()) numberOfClothingProducts--;
        else numberOfElectronicsProducts--;

        if(quantityOnCart.get(productToRemove) == 1) {
            productsOnCart.remove(productToRemove);
            quantityOnCart.remove(productToRemove);
        }
        else{
            quantityOnCart.put(productToRemove,quantityOnCart.get(productToRemove)-1);
        }

    }

    public double calculateTotal(){
        double total = 0;
        double itemsPrice;
        for(Product iterate : this.productsOnCart){
            itemsPrice = iterate.getPrice() * quantityOnCart.get(iterate);
            total += itemsPrice;
        }
        return Math.round(total * 100.0) / 100.0;
    }

    public double getThreeSameItemsDiscount(){
        double discount = 0;
        if(numberOfElectronicsProducts > 2 || numberOfClothingProducts > 2){
            discount = calculateTotal() * 0.2;
        }
        return Math.round(discount * 100.0) / 100.0;
    }

    public double getFirstPurchaseDiscount(){
        if (!user.firstPurchase()) return 0;
        else {
            return Math.round((calculateTotal()*0.1) * 100.0) / 100.0;
        }
    }

    public double getFinalPrice(){
        double finalAmount = calculateTotal()-(getFirstPurchaseDiscount()+getThreeSameItemsDiscount());
        return Math.round(finalAmount * 100.0) / 100.0;
    }

    public void clearCart(){
        productsOnCart.clear();
        quantityOnCart.clear();
    }


}
