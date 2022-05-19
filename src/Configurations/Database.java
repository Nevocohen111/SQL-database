package Configurations;

import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public abstract class Database {

    protected Connection connection;

    protected Database() {
        connection = null;
    }



    public Boolean checkConnection() throws SQLException {
        return connection != null && !connection.isClosed();
    }

    public abstract Connection getConnection() throws SQLException;

    public void closeConnection() throws SQLException {
        connection.close();
    }

    public ResultSet query(@Language("SQL") String query) throws SQLException {
        if(!checkConnection())
             connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeQuery();
    }

    public void query(@Language("SQL") String query, Consumer<ResultSet> consumer) throws SQLException {
        ResultSet rs = query(query);
        consumer.accept(rs);
        rs.close();
    }

    public void perform(@Language("SQL") String query) throws SQLException {
        if(!checkConnection())
             connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.execute();
    }




}
