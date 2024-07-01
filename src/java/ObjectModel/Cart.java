package ObjectModel;

public class Cart {
    private int cartID;
    private int userID;
    private String itemList;

    public Cart(int userID, String itemList) {
        this.userID = userID;
        this.itemList = itemList;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getItemList() {
        return itemList;
    }

    public void setItemList(String itemList) {
        this.itemList = itemList;
    }
}
