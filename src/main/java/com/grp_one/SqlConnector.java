package com.grp_one;

import java.sql.*;

public class SqlConnector {
    public Connection dbConn() throws SQLException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://", "root", "creamPuffs69420");
            System.out.println("Connection successful");
            return conn;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
}
