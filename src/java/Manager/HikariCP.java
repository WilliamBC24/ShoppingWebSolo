
package Manager;


import java.sql.Connection;
import java.sql.SQLException;

public class HikariCP {

    public static Connection getConnection() throws SQLException {
        return Hikari.getDataSource().getConnection();
    }
}
