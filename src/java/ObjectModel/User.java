package ObjectModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class User {
    private int userID;
    private String username;
    private String password;
    private String salt;
    private String email;
    private String gender;
    private String phoneNumber;
    private String avatarImg;
    private String firstName;
    private String lastName;
    private String verifyToken;
    private int verifiedStatus;
    private int accessLevel;
    private String googleID;
    private String OAuthProvider;
    private String OAuthToken;

    // Constructors
    public User() {
        // Default constructor
    }

    public User(int userID, String username, String password, String salt, String email,
                String gender, String phoneNumber, String avatarImg, String firstName,
                String lastName, String verifyToken, int verifiedStatus, int accessLevel,
                String googleID, String OAuthProvider, String OAuthToken) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.email = email;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.avatarImg = avatarImg;
        this.firstName = firstName;
        this.lastName = lastName;
        this.verifyToken = verifyToken;
        this.verifiedStatus = verifiedStatus;
        this.accessLevel = accessLevel;
        this.googleID = googleID;
        this.OAuthProvider = OAuthProvider;
        this.OAuthToken = OAuthToken;
    }

    // Getters and Setters
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatarImg() {
        return avatarImg;
    }

    public void setAvatarImg(String avatarImg) {
        this.avatarImg = avatarImg;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getVerifyToken() {
        return verifyToken;
    }

    public void setVerifyToken(String verifyToken) {
        this.verifyToken = verifyToken;
    }

    public int getVerifiedStatus() {
        return verifiedStatus;
    }

    public void setVerifiedStatus(int verifiedStatus) {
        this.verifiedStatus = verifiedStatus;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getGoogleID() {
        return googleID;
    }

    public void setGoogleID(String googleID) {
        this.googleID = googleID;
    }

    public String getOAuthProvider() {
        return OAuthProvider;
    }

    public void setOAuthProvider(String OAuthProvider) {
        this.OAuthProvider = OAuthProvider;
    }

    public String getOAuthToken() {
        return OAuthToken;
    }

    public void setOAuthToken(String OAuthToken) {
        this.OAuthToken = OAuthToken;
    }
    
    public static List<User> getUser(ResultSet rs) throws SQLException {
        List<User> userList = new ArrayList<>();

        while (rs.next()) {
            int userID = rs.getInt("userID");
            String username = rs.getString("username");
            String password = rs.getString("password");
            String salt = rs.getString("salt");
            String email = rs.getString("email");
            String gender = rs.getString("gender");
            String phoneNumber = rs.getString("phoneNumber");
            String avatarImg = rs.getString("avatarImg");
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            String verifyToken = rs.getString("verifyToken");
            int verifiedStatus = rs.getInt("verifiedStatus");
            int accessLevel = rs.getInt("accessLevel");
            String googleID = rs.getString("googleID");
            String OAuthProvider = rs.getString("OAuthProvider");
            String OAuthToken = rs.getString("OAuthToken");

            User user = new User(userID, username, password, salt, email, gender, phoneNumber,
                    avatarImg, firstName, lastName, verifyToken, verifiedStatus, accessLevel,
                    googleID, OAuthProvider, OAuthToken);
            userList.add(user);
        }

        return userList;
    }
    public void summonUser(ResultSet rs) throws SQLException {
        this.userID = rs.getInt("userID");
        this.username = rs.getString("username");
        this.password = rs.getString("password");
        this.salt = rs.getString("salt");
        this.email = rs.getString("email");
        this.gender = rs.getString("gender");
        this.phoneNumber = rs.getString("phoneNumber");
        this.avatarImg = rs.getString("avatarImg");
        this.firstName = rs.getString("firstName");
        this.lastName = rs.getString("lastName");
        this.verifyToken = rs.getString("verifyToken");
        this.verifiedStatus = rs.getInt("verifiedStatus");
        this.accessLevel = rs.getInt("accessLevel");
        this.googleID = rs.getString("googleID");
        this.OAuthProvider = rs.getString("OAuthProvider");
        this.OAuthToken = rs.getString("OAuthToken");
    }
}
