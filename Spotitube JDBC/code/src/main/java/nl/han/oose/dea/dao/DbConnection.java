package nl.han.oose.dea.dao;

import javax.enterprise.inject.Default;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Default
public class DbConnection {
    private static DbConnection instance;
    private Connection conn;

    static DbConnection getInstance() {
        if (instance != null) {
            return instance;
        } else {
            try {
                instance = new DbConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return instance;
        }
    }

    private DbConnection() {
    }


    private Connection openConnection() {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String connectionType = properties.getProperty("connectionType");
        String databaseType = properties.getProperty("databaseType");
        String serverName = properties.getProperty("serverName");
        int portNumber = Integer.parseInt(properties.getProperty("portNumber"));
        String userName = properties.getProperty("username");
        String password = properties.getProperty("password");
        String dbName = properties.getProperty("databaseName");

        conn = null;
        if (databaseType.equals("mysql")) {
            try {
                Class.forName(properties.getProperty("jdbc.driver"));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                conn = DriverManager.getConnection(connectionType + ":" + databaseType + "://" + serverName + ":" + portNumber + "/" + dbName + "?user=" + userName + "&password=" + password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (conn != null) {
                System.out.println("Succesfully connected to the database");
            }
        }
        return conn;
    }

    Connection getConnection() {
        Connection connection = conn;
        if (conn == null) {
            connection = openConnection();
        }
        return connection;
    }

    void closeConnection() {
        if (conn != null) {
            conn = null;
            System.out.println("Closed connection to the database");
        }
    }
}

