package Aplication;

import Entities.Refrigerator;
import Entities.ShoppingCart;
import Entities.Stove;
import Entities.Tv;

public class Mall {

    public static void main(String[] args) {
        Tv s25t = new Tv("Samsung", 500, 32);
        Refrigerator e12 = new Refrigerator("Eletrolux", 750, 12);
        Stove f21 = new Stove("Eletrolux", 500, 7);

        ShoppingCart myCart = new ShoppingCart(1);
        myCart.addProduct(s25t);
        myCart.addProduct(e12);
        myCart.addProduct(f21);

        System.out.println(myCart.getContents());
        System.out.println("Total amount: $" + String.format("%.2f", myCart.getTotalPrice()));

    }
}