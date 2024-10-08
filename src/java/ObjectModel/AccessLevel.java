package ObjectModel;

public class AccessLevel {
    private int accessLevelID;
    private String accessLevel;

    public AccessLevel(int accessLevelID, String accessLevel) {
        this.accessLevelID = accessLevelID;
        this.accessLevel = accessLevel;
    }

    public int getAccessLevelID() {
        return accessLevelID;
    }

    public void setAccessLevelID(int accessLevelID) {
        this.accessLevelID = accessLevelID;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }
}
