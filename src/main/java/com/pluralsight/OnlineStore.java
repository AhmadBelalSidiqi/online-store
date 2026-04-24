package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class OnlineStore {
    static HashMap<String, Product> inventory = loadInventory("src/main/resources/products.csv");
    static HashMap<String, ShoppingCart> cart = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        mainMenu();
        System.out.println("Thank for you using the app.");

    }

    // main menu and sub menus
    public static void mainMenu() {
        String menu = """
                ---------- Hello welcome to your shop --------
                Please chose one of the following option:
                A. Display Products
                B. Display Cart
                C. Exit - Closes out of the application
                Please Enter (A-B-C).
                """;
        boolean running = true;
        do {
            System.out.println(menu);
            String input = scanner.nextLine();
            switch (input.toLowerCase()) {
                case "a" -> {
                    displayAllProducts();
                    displayProductMenu();
                }
                case "b" -> {
                    displayCart();
                    displayCartMenu();
                }
                case "c" -> running = false;

            }
        } while (running);
    }

    public static void displayProductMenu() {
        Scanner scanner = new Scanner(System.in);
        String menu = """
                \n
                Please chose one of the following options:
                A- Search a product
                B- Add a product
                C- Go back
                """;
        boolean running = true;
        do {
            System.out.println(menu);
            String input = scanner.nextLine();
            switch (input.toLowerCase()) {
                case ("a") -> searchProduct();

                case ("b") -> addProduct();

                case ("c") -> running = false;

                default -> System.out.println("Wrong Input");

            }
        } while (running);
    }

    public static void displayCartMenu() {
        String menu = """
                Please chose one of the following:
                A) Check out
                B) Remove the Product
                C) display cart
                D) Go back
                """;
        boolean running = true;
        do {System.out.println(menu);
            String input = scanner.nextLine();
            switch (input.toLowerCase()) {
                case "a" -> {
                    checkOut();
                }
                case "b" -> {
                    System.out.println("Please enter the SKI");
                    remove(scanner.nextLine());
                }
                case "c" ->{
                    displayCart();
                }
                case "d" ->{
                    running=false;
                }
            }
        } while (running);

    }
    // TODO: Create check out system
    public static void displayProduct(String sku) {
        Product product = inventory.get(sku);
        String result = "SKU- " + product.getSku()
                + "\t\tName- " + product.getName()
                + "\nPrice- " + product.getPrice()
                + "\tDepartment- " + product.getDepartment();
        System.out.println(result);
    }

    public static void displayAllProducts() {
        System.out.println("SKI \t\tName \t\t\t\t\t\t\t\t\tPrice  \t Department ");
        for (Product product : inventory.values()) {
            System.out.printf("%-9s\t%-35s\t %8.2f\t%-15s%n",product.getSku(),product.getName(),product.getPrice(),product.getDepartment());
        }
    }

    // Search the products
    public static void searchProduct() {
        String menu = """
                Please chose one of the following:
                A- Search by SKU
                B- Search by name
                C- Search by price
                D- Search by department
                E- exit
                """;
        boolean running = true;
        do {
            System.out.println(menu);
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            switch (input.toLowerCase()) {
                case ("a") -> {
                    System.out.println("Please enter the SKI:");
                    findBySku(scanner.nextLine());

                }
                case ("b") -> {
                    System.out.println("Please enter the Name:");
                    findByName(scanner.nextLine());
                }
                case ("c") -> {
                    System.out.println("Please enter the price:");
                    findByPrice(Double.parseDouble(scanner.nextLine()));
                }
                case ("d") -> {
                    System.out.println("Please enter the department:");
                    findByDepartment(scanner.nextLine());
                }
                case ("e") -> running = false;

            }

        } while (running);
    }

    public static void addToCart(String sku) {
        if (isThere(sku)) {
            cart.get(sku).addOne();
        } else {
            String name = inventory.get(sku).getName();
            double price = inventory.get(sku).getPrice();
            cart.put(sku, new ShoppingCart(sku, name, price, 1));
        }

    }

    public static void findBySku(String sku) {
        displayProduct(sku);
    }

    public static void findByName(String name) {
        for (Product product : inventory.values()) {
            if (product.getName().toLowerCase().contains(name.toLowerCase())) {
                String sku = product.getSku();
                displayProduct(sku);
            }
        }
    }

    public static void findByPrice(double price) {
        for (Product product : inventory.values()) {
            if (product.getPrice() == price) {
                String sku = product.getSku();
                displayProduct(sku);
            }
        }
    }

    public static void findByDepartment(String dept) {
        int count = 1;
        for (Product product : inventory.values()) {
            if (product.getDepartment().equalsIgnoreCase(dept)) {
                System.out.println(count++ + "#");
                String sku = product.getSku();
                displayProduct(sku);
            }
        }
    }

    public static void addProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the SKU of the product");
        String sku = scanner.nextLine();
        addToCart(sku);
    }

    // Check out
    public static void displayCart() {
        System.out.println("here is your cart");
        double total = 0;
        for (ShoppingCart s : cart.values()) {
            System.out.println("Name: " + s.getName()
                    + "\tPrice: " + s.getPrice()
                    + "\tCount: " + s.getCount());
            total += s.getPrice();
        }
        System.out.println("total: " + total);

    }

    public static boolean isThere(String sku) {
        for (String key : cart.keySet()) {
            if (sku.equalsIgnoreCase(key)) {
                return true;
            }
        }
        return false;
    }

    public static void remove(String sku) {
        if (isThere(sku)) {
            cart.get(sku).removeOne();
        } else {
            cart.remove(sku);
        }

    }

    public static void checkOut() {
        System.out.println("Check out successfully ");
        displayCart();
        System.out.println("Your payment was successful");
    }

    public static HashMap<String, Product> loadInventory(String fileLocation) {
        HashMap<String, Product> inventory = new HashMap<>();
        try {
            FileReader fileReader = new FileReader(fileLocation);
            BufferedReader reader = new BufferedReader(fileReader);
            String currentLine;
            String headers = "SKU|Product Name|Price|Department";
            while ((currentLine = reader.readLine()) != null) {
                if (!currentLine.equalsIgnoreCase(headers)) {
                    String[] currentLineSpilt = currentLine.split("\\|");
                    String sku = currentLineSpilt[0];
                    String name = currentLineSpilt[1];
                    double price = Double.parseDouble(currentLineSpilt[2]);
                    String dept = currentLineSpilt[3];
                    inventory.put(sku, new Product(sku, name, price, dept));
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found:" + fileLocation);
        } catch (IOException e) {
            System.err.println("IO Exception");
        }
        return inventory;
    }
}

