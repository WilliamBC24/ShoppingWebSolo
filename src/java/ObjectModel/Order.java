package ObjectModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

    private int orderID;
    private int userID;
    private Date orderDate;
    private Date receivedDate;
    private float totalAmount;
    private int status;

    // Constructor
    public Order(int orderID, int userID, Date orderDate, Date receivedDate, float totalAmount, int status) {
        this.orderID = orderID;
        this.userID = userID;
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
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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
            int userID = rs.getInt("userID");
            Date orderDate = rs.getDate("orderDate");
            Date receivedDate = rs.getDate("receivedDate");
            float totalAmount = rs.getFloat("totalAmount");
            int status = rs.getInt("status");

            Order order = new Order(orderID, userID, orderDate, receivedDate, totalAmount, status);
            orderList.add(order);
        }

        return orderList;
    }
}
