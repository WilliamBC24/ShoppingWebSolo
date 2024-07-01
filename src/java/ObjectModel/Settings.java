/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ObjectModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Settings {
    private int settingsID;
    private String settingsType;
    private String settingsValue;
    private int settingsStatus;

    // Constructor
    public Settings(int settingsID, String settingsType, String settingsValue, int settingsStatus) {
        this.settingsID = settingsID;
        this.settingsType = settingsType;
        this.settingsValue = settingsValue;
        this.settingsStatus = settingsStatus;
    }

    // Getters and Setters
    public int getSettingsID() {
        return settingsID;
    }

    public void setSettingsID(int settingsID) {
        this.settingsID = settingsID;
    }

    public String getSettingsType() {
        return settingsType;
    }

    public void setSettingsType(String settingsType) {
        this.settingsType = settingsType;
    }

    public String getSettingsValue() {
        return settingsValue;
    }

    public void setSettingsValue(String settingsValue) {
        this.settingsValue = settingsValue;
    }

    public int getSettingsStatus() {
        return settingsStatus;
    }

    public void setSettingsStatus(int settingsStatus) {
        this.settingsStatus = settingsStatus;
    }

    public static List<Settings> getSettings(ResultSet rs) throws SQLException {
        List<Settings> settingsList = new ArrayList<>();

        while (rs.next()) {
            int settingsID = rs.getInt("settingsID");
            String settingsType = rs.getString("settingsType");
            String settingsValue = rs.getString("settingsValue");
            int settingsStatus = rs.getInt("settingsStatus");

            Settings settings = new Settings(settingsID, settingsType, settingsValue, settingsStatus);
            settingsList.add(settings);
        }

        return settingsList;
    }

}
