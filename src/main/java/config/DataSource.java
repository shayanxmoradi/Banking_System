package config;

import util.ApplicationProperties;

import java.sql.Connection;
import java.sql.*;

public class DataSource {
    private static Connection connection;
    static {
        try {
            connection = DriverManager.getConnection(
                    ApplicationProperties.DB_URL,
                    ApplicationProperties.DB_USERNAME,
                    ApplicationProperties.DB_PASSWORD
            );
        } catch (SQLException e) {
            System.out.println("Error In DataSource Attribute!");
            throw new RuntimeException(e);

        }
    }
    public static Connection getConnection() { return connection;}
}
