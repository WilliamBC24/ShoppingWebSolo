package ObjectModel;

public class UserPaymentInfo {
    private int paymentID;
    private int userID;
    private int creditCardNumber;
    private String deliveryLocation;
    private int phoneNumber;
    private String receiverName;

    public UserPaymentInfo(int userID, int creditCardNumber, String deliveryLocation, int phoneNumber, String receiverName) {
        this.userID = userID;
        this.creditCardNumber = creditCardNumber;
        this.deliveryLocation = deliveryLocation;
        this.phoneNumber = phoneNumber;
        this.receiverName = receiverName;
    }

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

    public int getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(int creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
}
