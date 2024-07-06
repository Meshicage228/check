package ru.clevertec.check.config;

import org.postgresql.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConfig {
    private static String URL = "";
    private static String USER = "";
    private static String PASSWORD = "";

    static  {
        try {
            URL = System.getProperty("datasource.url");
            USER = System.getProperty("datasource.username");
            PASSWORD = System.getProperty("datasource.password");
            DriverManager.registerDriver(new Driver());
            System.out.println();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties info = new Properties();
            info.setProperty("user", USER);
            info.setProperty("password", PASSWORD);
            return DriverManager.getConnection (URL, info);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
