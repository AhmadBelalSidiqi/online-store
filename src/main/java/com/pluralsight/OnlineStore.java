package com.pluralsight;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class OnlineStore {
    public static void main(String[] args) {
        HashMap<String, Product> inventory = loadInventory("src/main/resources/products.csv");

    }


    public static HashMap<String,Product> loadInventory (String fileLocation)  {
        HashMap<String,Product> inventory = new HashMap<>();
        try {
            FileReader fileReader = new FileReader(fileLocation);
            BufferedReader reader = new BufferedReader(fileReader);
            String currentLine;
            String headers = "SKU|Product Name|Price|Department";
            while ((currentLine = reader.readLine()) != null) {
                if (!currentLine.equalsIgnoreCase(headers)){
                    String[] currentLineSpilt = currentLine.split("\\|");
                    String sku = currentLineSpilt[0];
                    String name = currentLineSpilt[1];
                    double price = Double.parseDouble(currentLineSpilt[2]);
                    String dept = currentLineSpilt[3];
                    inventory.put(sku,new Product(sku,name,price,dept));
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
    public static void homeScreen(){
        Scanner scanner = new Scanner(System.in);
        String menu = """
                ---------- Hello welcome to your shop --------
                Please chose one of the following option:
                1. Display Products
                2. Display Cart
                3. Exit - Closes out of the application
                Please Enter (1-3).
                """;
        int input = Integer.parseInt(scanner.nextLine());
    }

    public static void displayProucts(HashMap<String,Product> inventory){
        for (Product product : inventory.values()){
            System.out.println("SKI -\tName -\t price - \t department ");

        }


    }

}
