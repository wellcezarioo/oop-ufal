package Entities;
import java.util.ArrayList;

public class ShoppingCart {
    private int costumerId;
    private ArrayList<Product> productList;


    public ShoppingCart(int costumerId) {
        this.costumerId = costumerId;
        this.productList = new ArrayList<Product>();
    }

    public int getCostumerId() {
        return costumerId;
    }

    public void addProduct(Product p){
        if(p instanceof Product){
            this.productList.add(p);
        }
    }

    public void remove(Product p){
        if(p instanceof Product) {
            this.productList.remove(p);
        }
    }

    public String getContents() {
        if(this.productList.size() == 0){
            return "The cart is empty!";
        }

        StringBuilder sb = new StringBuilder("Products in the cart:\n");
        for (Product p : productList) {
            sb.append(p.toString()).append("\n");
        }
        return sb.toString();
    }

    public int getCustumerId(){
        return this.costumerId;
    }

    public int getItemCount(){
        return this.productList.size();
    }


    public double getTotalPrice(){
        double sum = 0;
        for(int i = 0; i < this.productList.size(); i++){
            sum += this.productList.get(i).getPrice();
        }
        return sum;
    }
}