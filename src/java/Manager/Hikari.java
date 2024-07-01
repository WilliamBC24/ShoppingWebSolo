/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Manager;

/**
 *
 * @author SonBui
 */
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class Hikari {

    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource dataSource;

    static {
        config.setDriverClassName("com.mysql.cj.jdbc.Driver"); 
        config.setJdbcUrl("jdbc:mysql://localhost:3306/stbcStore");
        config.setUsername("root"); // 
        config.setPassword("sonbui");

        dataSource = new HikariDataSource(config);
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
