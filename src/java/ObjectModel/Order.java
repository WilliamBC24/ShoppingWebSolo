package ObjectModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

    private int orderID;
    private String username;
    private Date orderDate;
    private Date receivedDate;
    private float totalAmount;
    private int status;

    // Constructor
    public Order(int orderID, String username, Date orderDate, Date receivedDate, float totalAmount, int status) {
        this.orderID = orderID;
        this.username = username;
        this.orderDate = orderDate;
        this.receivedDate = receivedDate;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    // Getter and Setter for userID
    public String getUsername() {
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    // Getter and Setter for orderDate
    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    // Getter and Setter for receivedDate
    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    // Getter and Setter for totalAmount
    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    // Getter and Setter for status
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public static List<Order> getOrder(ResultSet rs) throws SQLException {
        List<Order> orderList = new ArrayList<>();

        while (rs.next()) {
            int orderID = rs.getInt("orderID");
            String username = rs.getString("username");
            Date orderDate = rs.getDate("orderDate");
            Date receivedDate = rs.getDate("receivedDate");
            float totalAmount = rs.getFloat("totalAmount");
            int status = rs.getInt("status");

            Order order = new Order(orderID, username, orderDate, receivedDate, totalAmount, status);
            orderList.add(order);
        }

        return orderList;
    }
}
