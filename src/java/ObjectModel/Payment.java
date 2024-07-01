package ObjectModel;

import java.util.Date;

public class Payment {
    private int paymentID;
    private int userID;
    private int orderID;
    private int paymentStatus;
    private double paymentAmount;
    private Date paymentTime;

    // Constructor
    public Payment(int userID, int orderID, int paymentStatus, double paymentAmount, Date paymentTime) {
        this.userID = userID;
        this.orderID = orderID;
        this.paymentStatus = paymentStatus;
        this.paymentAmount = paymentAmount;
        this.paymentTime = paymentTime;
    }

    // Getters and setters
    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }
}
