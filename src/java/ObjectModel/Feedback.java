package ObjectModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Feedback {

    private int feedbackID;
    private String username;
    private String productName;
    private String feedbackDetail;
    private int star;
    private String attachedImg;
    private Date feedbackDate;

    public Feedback(String username, String productName, String feedbackDetail, int star, String attachedImg, Date feedbackDate) {
        this.username = username;
        this.productName = productName;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public static List<Feedback> getFeedback(ResultSet rs) throws SQLException {
        List<Feedback> feedbackList = new ArrayList<>();

        while (rs.next()) {
            String username = rs.getString("username");
            String productName = rs.getString("productName");
            String feedbackDetail = rs.getString("feedbackDetail");
            int star = rs.getInt("star");
            String attachedImg = rs.getString("attachedImg");
            Date feedbackDate = rs.getDate("feedbackDate");

            Feedback feedback = new Feedback(username, productName, feedbackDetail, star, attachedImg, feedbackDate);
            feedbackList.add(feedback);
        }

        return feedbackList;
    }
}
