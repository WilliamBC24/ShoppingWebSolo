package ObjectModel;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDetails {
    private int orderID;
    private String productName;
    private int amount;
    private double price;

    public OrderDetails() {}

    public OrderDetails(int orderID, String productName, int amount, double price) {
        this.orderID = orderID;
        this.productName = productName;
        this.amount = amount;
        this.price = price;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static List<OrderDetails> getOrderDetails(ResultSet rs) throws SQLException {
        List<OrderDetails> orderDetailsList = new ArrayList<>();

        while (rs.next()) {
            int orderID = rs.getInt("orderID");
            String productName = rs.getString("productName");
            int amount = rs.getInt("amount");
            double price = rs.getDouble("price");

            OrderDetails orderDetails = new OrderDetails(orderID, productName, amount, price);
            orderDetailsList.add(orderDetails);
        }

        return orderDetailsList;
    }
}
