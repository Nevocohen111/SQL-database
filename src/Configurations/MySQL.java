package Configurations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL extends Database {
    public MySQL() throws SQLException {
        setConnection();
    }

    public void setConnection() throws SQLException {
        connection = DriverManager.getConnection(
                DBConfig.getProperty("url"),
                DBConfig.getProperty("user"),
                DBConfig.getProperty("pass"));
    }

    @Override
    public Connection getConnection() throws SQLException {
        if(!checkConnection())
            setConnection();
        return connection;
    }
}
