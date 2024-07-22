package ObjectModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cart {
    private int userID;
    private int productID;
    private int quantity;
    
    public Cart(int userID, int productID, int quantity){
        this.userID=userID;
        this.productID=productID;
        this.quantity=quantity;
    }
    //getter and settier
    public int getUserID(){
        return userID;
    }
    public void setUserID(int userID){
        this.userID=userID;
    }
    public int getProductID(){
        return productID;
    }
    public void setProductID(int productID){
        this.productID=productID;
    }
    public int getQuantity(){
        return quantity;
    }
    public void setQuantity(int quantity){
        this.quantity=quantity;
    }
    public static List<Cart> getCart(ResultSet rs) throws SQLException {
        List<Cart> cartList = new ArrayList<>();

        while (rs.next()) {
            int userID = rs.getInt("userID");
            int productID = rs.getInt("productID");
            int quantity = rs.getInt("quantity");

            Cart cart = new Cart(userID, productID, quantity);
            cartList.add(cart);
        }

        return cartList;
    }
}
