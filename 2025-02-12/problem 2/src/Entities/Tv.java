package Entities;

public class Tv extends Product{
    private int inches;

    public Tv(String brand, double price, int inches) {
        super(brand, price);
        this.inches = inches;
    }

    public int getInches(){
        return inches;
    }
}
