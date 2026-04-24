package com.pluralsight;

public class ShoppingCart {
    private String sku;
    private String name;
    private double price;
    private int count;

    public ShoppingCart(String sku, String name, double price, int count) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.count = count;
    }
    public void addOne (){
        count++;
    }
    public void removeOne(){
        count--;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }
}
