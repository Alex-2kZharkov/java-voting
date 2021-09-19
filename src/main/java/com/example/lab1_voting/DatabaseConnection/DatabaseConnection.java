package com.example.lab1_voting.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getDatabaseLink() {
        String databaseName = "lab1_voting";
        String databaseUserName = "root";
        String databasePassword = "2000-2000";
        String databaseUrl = "jdbc:mysql://localhost/" + databaseName;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(databaseUrl, databaseUserName, databasePassword);

        } catch (Exception err) {
            err.printStackTrace();
        }
        return databaseLink;
    }
}
