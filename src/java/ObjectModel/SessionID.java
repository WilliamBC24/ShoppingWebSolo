package ObjectModel;

public class SessionID {
    private int userID;
    private String sessionID;

    // Constructor
    public SessionID(int userID, String sessionID) {
        this.userID = userID;
        this.sessionID = sessionID;
    }

    // Getters and setters
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }
}
