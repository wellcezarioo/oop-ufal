package Entities;

public class Stove extends Product{
    private int burners;

    public Stove(String brand, double price, int burnes) {
        super(brand, price);
        this.burners = burnes;
    }

    public int getBurners(){
        return burners;
    }
}