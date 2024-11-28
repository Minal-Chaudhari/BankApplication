package com.bankApp.util;

import com.bankApp.base.BaseClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtils {

    // Method to establish and return a database connection
    public static Connection getConnection(String dbUrl, String username, String password) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbUrl, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // Method to execute a query and return whether a record exists
    public static boolean recordExists(String dbUrl, String query) {

        String username = BaseClass.getProperty("dbUserName");
        String password = BaseClass.getProperty("dbPassword");
        boolean exists = false;
        try (Connection connection = getConnection(dbUrl, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Check if the query result has at least one record
            exists = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }
}
