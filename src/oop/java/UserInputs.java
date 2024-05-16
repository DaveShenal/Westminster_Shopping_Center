package oop.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class UserInputs {
    private static final Scanner input = new Scanner(System.in);
    private static final String PROCESS_TERMINATING_VALUE = "#";
    private final ArrayList<Product> productList;

    public UserInputs(ArrayList<Product> productList) {
        this.productList = productList;
    }

    public String getProcessTerminatingValue(){
        return PROCESS_TERMINATING_VALUE;
    }

    public String getInput(String prompt, String inputName, boolean stringValue){
        boolean gotStringInput = false;
        String enteredInput;
        do {
            System.out.print(prompt);
            enteredInput = (input.nextLine()).trim();

            // if the method needed to be canceled -> this value can be entered if user needs to stop add product
            // process, therefore this value should be passed without going through any validation.
            if(enteredInput.equals(PROCESS_TERMINATING_VALUE) && !inputName.equals("menu option")) return enteredInput;

            else if (enteredInput.isEmpty()){
                System.out.println(inputName + " cannot be empty. Please enter a valid input.");
                continue;
            }
            else {
                if(!stringValue){

                    try {
                        if(!inputName.equals("price")){
                            Integer.parseInt(enteredInput);
                        }

                        double enteredNum = Double.parseDouble(enteredInput);

                        if (enteredNum == 0) {
                            System.out.println(inputName + " cannot be zero");
                            continue;
                        }
                        else if (enteredNum < 0) {
                            System.out.println(inputName + " cannot be negative");
                            continue;
                        }
                    }catch (NumberFormatException e){
                        System.out.println("Invalid number format for " + inputName);
                        continue;
                    }
                }
            }
            gotStringInput = true;
        } while (!gotStringInput);

        return enteredInput;
    }

    public String getProductId(boolean toAdd){
        boolean invalidID;
        String ID;

        do {
            invalidID = false;
            ID = getInput("Product ID : ","product ID",true);

            // if the method needed to be canceled (see "getInput" method for more details).
            if(ID.equals(PROCESS_TERMINATING_VALUE)) return ID;

            else if(ID.matches("^[A-Z][0-9]{4}$")){
                boolean duplicateID = false;
                String index = "0";
                for(Product checker : productList){
                    if (checker.getProductID().equals(ID)) {
                        duplicateID = true;
                        index = String.valueOf(productList.indexOf(checker));
                        break;
                    }
                }

                if (duplicateID && toAdd) {
                    System.out.println("Item with product ID \""+ID+"\"is already exist in the product list.");
                    invalidID = true;
                }
                else if(!toAdd && !duplicateID){
                    System.out.println("There is no listed product with product ID \""+ID+"\".");
                    invalidID = true;
                }
                else if(!toAdd){return index;}
            }
            else {
                System.out.println("Please enter a string starting with an uppercase letter followed by 4 digits (e.g.,A1234)");
                invalidID = true;
            }
        }while (invalidID);
        return ID;
    }

    // lists contains valid values for each input
    String[] validSizes = {"XS","S","M","L","XL","14","14.5","15","15.5","16","16.5","17"};
    String[] validWarrantyPeriods = {"1","2","3","4","6","12","24","36","48"};
    String[] validColours = {"White","Black","Red","Yellow","Blue","Gray","Pink","Green","Purple","Orange"};

    // to get custom inputs (size,warranty period,colour)
    // String[] validInputs -> to check the validity
    // String prompt -> to pass to getInput method
    // inputName -> to pass to getInput method
    public String getCustomInputs(String[] validInputs,String prompt,String inputName){
        boolean invalidInput = true;
        String enteredValue;

        do {
            enteredValue = getInput(prompt,inputName,true);

            // if the method needed to be canceled (see "getInput" method for more details).
            if(enteredValue.equals(PROCESS_TERMINATING_VALUE)) return enteredValue;

            for(String checker : validInputs){
                if(checker.equalsIgnoreCase(enteredValue)){
                    invalidInput = false;
                    break;
                }
            }
            if(invalidInput) System.out.println("Please enter a "+ inputName +" from "+ Arrays.toString(validInputs));

        }while (invalidInput);
        return enteredValue;
    }

    // to get all the required inputs and crate product object
    // boolean electronicItem -> to get correct information and call correct constructor.
    public void UpdateProductList(boolean isClothingItem){
        String productType = "electronic";
        if(isClothingItem){
            productType = "clothing";
        }
        System.out.println("\nEnter the " +productType+ " item product details\nEnter \"#\" to terminate the process");
        String[] info = new String[6];

        for(int i=0; i<6; i++){
            switch (i) {
                case 0 -> info[0] = getProductId(true);
                case 1 -> info[1] = getInput("Product Name : ", "product name", true);
                case 2 -> info[2] = getInput("Price : ", "price", false);
                case 3 -> info[3] = getInput("Number of Available Items : ", "number of available items", false);
                case 4 -> {
                    if (!isClothingItem) info[4] = getInput("Brand Name : ", "brand name", true);
                    else info[4] = getCustomInputs(validSizes, "Size : ", "size").toUpperCase();

                }
                case 5 -> {
                    if (isClothingItem) {
                        info[5] = getCustomInputs(validColours, "Colour : ", "colour");
                        productList.add(new Clothing(info[0],info[1],Double.parseDouble(info[2]),Integer.parseInt(info[3]),true,info[4],info[5]));
                    } else {
                        info[5] = getCustomInputs(validWarrantyPeriods, "Warranty period in months : ", "warranty period");
                        productList.add(new Electronics(info[0],info[1],Double.parseDouble(info[2]),Integer.parseInt(info[3]),false,info[4],info[5]));
                    }
                }
            }

            // if user decided to leave during the add product process (see "getInput" method for more details).
            if(info[i].equals(PROCESS_TERMINATING_VALUE)){
                System.out.println("Adding " +productType+ " product process has been cancelled.");
                return;
            }
        }
        System.out.println("Product has been added to the product list.");
    }

}
