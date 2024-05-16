package oop.java;

import java.util.ArrayList;
import java.util.Comparator;

public class WestminsterShoppingManager implements ShoppingManager {
    private final ArrayList<Product> productList = new ArrayList<>(50);
    private final ArrayList<User> usersList = new ArrayList<>(20);
    private final UserInputs getUserInputs = new UserInputs(productList);
    FileHandler fileHandler = new FileHandler();


    // main method
    public static void main(String[] args) {
        WestminsterShoppingManager WShoppingManager = new WestminsterShoppingManager();
        WShoppingManager.readFromFile();
        WShoppingManager.displayMenu();
    }


    @Override
    public void displayMenu() {
        int mainOption;

        String menu = """
             \n-------------------------------------------
              Main Menu
             -------------------------------------------
             1. Add a new product
             2. Delete a product
             3. Print the list of the products
             4. Save in a file
             5. Read from a file
             6. Open a Graphical User Interface (GUI)
             7. Quit program
             \nEnter a menu option :\s""";

        mainOption =  Integer.parseInt(getUserInputs.getInput(menu, "menu option", false));

        switch (mainOption) {
            case 1 -> addNewProduct();
            case 2 -> deleteProduct();
            case 3 -> printProductList();
            case 4 -> saveToFile(false);
            case 5 -> readFromFile();
            case 6 -> openGUI();
            case 7 -> exitProgram();
            default -> System.out.println("Please enter option from the menu.");
            }
        displayMenu();
    }


    @Override
    public void addNewProduct() {
        int option;

        do {
            // if product list already contains maximum number of products on it.
            if (productList.size() == 50) {
                System.out.println("Products cannot be added as the maximum limit has been reached.");
                option = 3;
            }
            else {
                String addProductMenu = """
                        \n1. Add a new Electronics product
                        2. Add a new Clothing product
                        3. Return to main menu
                        \nEnter a menu option :\s""";

                option = Integer.parseInt(getUserInputs.getInput(addProductMenu,"menu option",false));

                switch (option){
                    case 3 -> {return;}
                    case 1 -> getUserInputs.UpdateProductList(false);
                    case 2 -> getUserInputs.UpdateProductList(true);
                    default -> System.out.println("Please enter option from the menu.");
                }
            }
        }while (option != 3);
    }


    @Override
    public void deleteProduct() {
        String indexToDelete;
        int option;

        do {
            if (productList.isEmpty()) {
                System.out.println("Product list is empty.");
                option = 3;
            }
            else {
                String deleteProductMenu = """                        
                            \n1. Enter product ID to delete a product
                            2. Return to main menu
                            \nEnter a menu option :\s""";
                option = Integer.parseInt(getUserInputs.getInput(deleteProductMenu, "menu option", false));

                switch (option) {
                    case 2 -> {return;}
                    case 1 -> {
                        System.out.println("Enter \"#\" to terminate the process");
                        // getProductID method will return the index of a product with entered product ID.
                        indexToDelete = getUserInputs.getProductId(false);

                        if(indexToDelete.equals(getUserInputs.getProcessTerminatingValue())){
                            System.out.println("Deleting product process has been cancelled.");
                            return;
                        }
                        else {
                            Product product = productList.get(Integer.parseInt(indexToDelete));
                            System.out.println(toString(product));

                            String decisionPrompt = """
                                \nDelete the product with product ID \"""" + product.getProductID() + "\"?\n" + """
                                Enter "y" for yes or "n" for no :\s""";

                            boolean invalidDecision;
                            do {
                                String decision = getUserInputs.getInput(decisionPrompt,"decision",true).toLowerCase();
                                if (decision.equals("y")) {
                                    productList.remove(product);
                                    System.out.println("""
                                            Product with product ID \"""" + product.getProductID() + "\"" + """
                                            has been deleted"
                                            Number of products left in the system :\s""" + productList.size());
                                    invalidDecision = false;

                                } else if (!decision.equals("n")) {
                                    System.out.println("Please enter \"y\" or \"n\"");
                                    invalidDecision = true;
                                }
                                else invalidDecision = false;
                            }while (invalidDecision);
                        }
                    }
                    default -> System.out.println("Please enter option from the menu.");
                }
            }
        }while (option != 3);
    }



    @Override
    public void printProductList() {
        if (productList.isEmpty()) {
            System.out.println("Product list is empty.");
            return;
        }
        productList.sort(Comparator.comparing(Product::getProductID));

        // Print the sorted productList
        for (Product product : productList) {
            System.out.println(toString(product));
        }
    }



    private String toString(Product product) {
        String additionalDetails;
        String productType = "Electronics";
        if (product.isClothingItem()) {
            Clothing toDelete = (Clothing) product;
            productType = "Clothing";
            additionalDetails = """
                \nSize                      :\s""" + toDelete.getSize() + """
                \nColour                    :\s""" + toDelete.getColour();

        } else {
            Electronics toDelete = (Electronics) product;
            additionalDetails = """
                \nBrand Name                :\s""" + toDelete.getBrand() + """
                \nWarranty Period           :\s""" + toDelete.getWarranty().concat(" months");
        }
        String productDetails = """
            ----------------------------------------
            Product Details of Product Id :\s"""+product.getProductID()+ """
            \n----------------------------------------
            Product Type              :\s"""+productType+ """
            \nProduct Name              :\s""" + product.getProductName() + """
            \nPrice                     :\s""" + product.getPrice() + """
            \nNumber of Available Items :\s""" + product.getNumOfAvailable();

        return productDetails+additionalDetails;
    }



    @Override
    public void saveToFile(boolean isToAppend) {
        fileHandler.saveProductsInFile(productList,isToAppend);
        fileHandler.saveUsersInFile(usersList,isToAppend);
    }



    @Override
    public void readFromFile() {
        fileHandler.readProductsFile(productList);
        fileHandler.readUsersFile(usersList);
    }

    private void openGUI(){
        LoginGUI loginGUI = new LoginGUI(usersList,productList);
        loginGUI.setTitle("Login or Register");
        loginGUI.setSize(500, 500);
        loginGUI.setResizable(false);
        loginGUI.setVisible(true);
    }

    private void exitProgram() {
        saveToFile(false);
        System.out.println("Closed");
        System.exit(0);
    }
}
