package ObjectModel;

import java.util.Date;

public class Feedback {
    private int feedbackID;
    private int userID;
    private int productID;
    private String feedbackDetail;
    private int star;
    private String attachedImg;
    private Date feedbackDate;

    public Feedback(int userID, int productID, String feedbackDetail, int star, String attachedImg, Date feedbackDate) {
        this.userID = userID;
        this.productID = productID;
        this.feedbackDetail = feedbackDetail;
        this.star = star;
        this.attachedImg = attachedImg;
        this.feedbackDate = feedbackDate;
    }

    public int getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(int feedbackID) {
        this.feedbackID = feedbackID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getFeedbackDetail() {
        return feedbackDetail;
    }

    public void setFeedbackDetail(String feedbackDetail) {
        this.feedbackDetail = feedbackDetail;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getAttachedImg() {
        return attachedImg;
    }

    public void setAttachedImg(String attachedImg) {
        this.attachedImg = attachedImg;
    }

    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }
}
