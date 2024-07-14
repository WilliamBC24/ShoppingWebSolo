package ObjectModel;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class Post {
    private int postID;
    private int userID;
    private Date updatedDate;
    private String title;
    private String detail;
    int category;
    private String postImg;
    public Post(){}
    public Post(int postID,int userID, Date updatedDate, String title, String detail, int category, String postImg) {
        this.postID=postID;
        this.userID = userID;
        this.updatedDate = updatedDate;
        this.title = title;
        this.detail = detail;
        this.category=category;
        this.postImg = postImg;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    
    public int getCategory(){
        return category;
    }
    
    public void setCategory(int category){
        this.category=category;
    }

    public String getPostImg() {
        return postImg;
    }

    public void setPostImg(String postImg) {
        this.postImg = postImg;
    }
    
    public static List<Post> getPost(ResultSet rs) throws SQLException {
        List<Post> postList = new ArrayList<>();

        while (rs.next()) {
            int postID = rs.getInt("postID");
            int userID = rs.getInt("userID");
            Date updatedDate = rs.getDate("updatedDate");
            String title = rs.getString("title");
            String detail = rs.getString("detail");
            int category = rs.getInt("category");
            String postImg = rs.getString("postImg");

            Post post = new Post(postID, userID, updatedDate, title, detail, category, postImg);
            postList.add(post);
        }

        return postList;
    }
    public void onePost(ResultSet rs) throws SQLException{
        this.postID=rs.getInt("postID");
        this.userID=rs.getInt("userID");
        this.title=rs.getString("title");
        this.updatedDate=rs.getDate("updatedDate");
        this.detail=rs.getString("detail");
        this.category=rs.getInt("category");
        this.postImg=rs.getString("postImg");
    }
}
