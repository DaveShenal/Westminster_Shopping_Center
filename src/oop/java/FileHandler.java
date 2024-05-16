package oop.java;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
    String productsFileName = "product_data.txt";
    String usersFileName = "users_data.txt";

    public void saveProductsInFile(ArrayList<Product> productList,boolean append) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(productsFileName,append));
            String productType; // "C" for clothing and "E" for electronics.
            for (Product product : productList) {
                if(product.isClothingItem()){
                    Clothing cProduct = (Clothing) product;
                    productType = "C";// Clothing
                    writer.write(productType+"#"+cProduct.getProductID()+"#"+cProduct.getProductName()+"#"+cProduct.getPrice()+"#"+
                            cProduct.getNumOfAvailable()+"#"+cProduct.getSize()+"#"+cProduct.getColour());
                }
                else {
                    Electronics eProduct = (Electronics) product;
                    productType = "E"; // Electronics
                    writer.write(productType+"#"+eProduct.getProductID()+"#"+eProduct.getProductName()+"#"+eProduct.getPrice()+"#"+
                            eProduct.getNumOfAvailable()+"#"+eProduct.getBrand()+"#"+eProduct.getWarranty());

                }
                writer.write("\n"); //move to the next line.
            }
            writer.close();
            System.out.println("Successfully saved products to a file \""+productsFileName+"\"");
        } catch (Exception e) { //if file can not create for some reason.
            System.out.println("Error : "+e);
        }

    }

    public void readProductsFile(ArrayList<Product> productList) {
        try {
            File file = new File(productsFileName); //new file obj.
            Scanner reader = new Scanner(file); //new scanner obj.


            if(!reader.hasNextLine()) {
                System.out.println("File \"" + productsFileName + "\" is empty.");
                reader.close();
                return;
            }

            while (reader.hasNextLine()) { //if there is no more lines to read, loop will end.
                String line = reader.nextLine(); //assign whole line in to a String variable.
                String[] attributes = line.split("#"); //create array using split items of line variable.

                if(attributes[0].equals("E")){
                    productList.add(new Electronics(attributes[1],attributes[2],Double.parseDouble(attributes[3]),
                            Integer.parseInt(attributes[4]),false,attributes[5],attributes[6]));
                }
                else {
                    productList.add(new Clothing(attributes[1],attributes[2],Double.parseDouble(attributes[3]),
                            Integer.parseInt(attributes[4]),true,attributes[5],attributes[6]));
                }
            }
            reader.close();
            System.out.println("Successfully loaded products from a file \""+productsFileName+"\".");
        } catch (FileNotFoundException e1) {
            System.out.println("Loading failed: Cannot find the file \""+productsFileName+"\".");

        }
    }

    public void saveUsersInFile(ArrayList<User> usersList,boolean append){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(usersFileName,append));
            for (User user : usersList) {
                ArrayList<Purchase> purchaseHistory = user.getPurchaseHistory();
                writer.write(user.getUsername()+"|"+user.getPassword());

                for (Purchase purchase : purchaseHistory){
                    writer.write("|"+purchase.amount()+"|"+purchase.date());
                }

                writer.write("\n"); //move to the next line.
            }
            writer.close();
            System.out.println("Successfully saved users to a file \""+usersFileName+"\"");

        } catch (Exception e) { //if file can not create for some reason.
            System.out.println("Error : "+e);
        }
    }

    public void readUsersFile(ArrayList<User> usersList) {
        try {
            File file = new File(usersFileName); //new file obj.
            Scanner reader = new Scanner(file); //new scanner obj.

            if(!reader.hasNextLine()) {
                System.out.println("File \"" + usersFileName + "\" is empty.");
                reader.close();
                return;
            }

            while (reader.hasNextLine()) { //if there is no more lines to read, loop will end.
                String line = reader.nextLine(); //assign whole line in to a String variable.
                String[] attributes = line.split("\\|"); //create array using split items of line variable.
                String username = attributes[0];
                String password = attributes[1];

                User user = new User(username, password); // Create a User object

                for (int i = 2; i < attributes.length; i += 2) {
                    double amount = Double.parseDouble(attributes[i]);
                    LocalDateTime date = LocalDateTime.parse(attributes[i + 1]);
                    user.getPurchaseHistory().add(new Purchase(amount, date)); // Add a Purchase object
                }

                usersList.add(user);
            }
            reader.close();
            System.out.println("Successfully loaded users from a file \""+usersFileName+"\".");
        } catch (FileNotFoundException e1) {
            System.out.println("Loading failed: Cannot find the file \""+usersFileName+"\".");

        }
    }
}
