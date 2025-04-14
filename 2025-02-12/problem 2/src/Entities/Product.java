package Entities;

public class Product {
    private String brand;
    private double price;

    public Product(String brand, double price) {
        this.brand = brand;
        this.price = price;
    }

    public String getBrand(){
        return this.brand;
    }

    public double getPrice(){
        return this.price;
    }

    public String toString(){
        return this.brand + ", " + String.format("%.2f", this.price);
    }
}