package Aplication;

import Entities.ShoppingCart;
import Entities.Product;
import Entities.ShoppingCart;

class Mall {
    public static void main(String[] args) {
        Product cheese = new Product("Cheese", 5);
        Product strawberry = new Product("Strawberry", 2.5);
        Product pen = new Product("Pen", 1);

        ShoppingCart myCart = new ShoppingCart(1);
        myCart.addProduct(cheese);
        myCart.addProduct(strawberry);
        myCart.addProduct(pen);

        System.out.println(myCart.getContents());
        System.out.println("Total amount: $" + String.format("%.2f", myCart.getTotalPrice()));

    }
}
