package com.bank;
 import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
 
public class DBConnection {
 
    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/employee_db",
                "root",
                "Root123$"
            );
        } catch (Exception e) {
            System.out.println("DB Error: " + e.getMessage());
        }
        return con;
    }
}
 
