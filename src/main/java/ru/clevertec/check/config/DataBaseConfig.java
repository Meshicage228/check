package ru.clevertec.check.config;

import org.postgresql.Driver;
import java.sql.*;
import java.util.Properties;

public class DataBaseConfig {
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    public static void startDb(){
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setProperties(String ... properties){
        URL = properties[0];
        USER = properties[1];
        PASSWORD = properties[2];
    }

    public static Connection getConnection() {
        try {
            Properties info = new Properties();
            info.setProperty("user", USER);
            info.setProperty("password",PASSWORD);
            return DriverManager.getConnection (URL, info);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
