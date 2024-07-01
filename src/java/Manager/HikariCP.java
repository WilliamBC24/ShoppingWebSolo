/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Manager;

/**
 *
 * @author SonBui
 */
import java.sql.Connection;
import java.sql.SQLException;

public class HikariCP {

    public static Connection getConnection() throws SQLException {
        return Hikari.getDataSource().getConnection();
    }
}
