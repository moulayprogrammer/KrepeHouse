package com.moulay.krepehouse.BddPackage;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectBD {

    public ConnectBD() {

    }

    public Connection connect() {
        Connection conn = null;
        String url = "jdbc:mariadb://localhost:3306/crepehouse";

        String user = "root";
        String password = "123456";
        String unicode= "?useUnicode=yes&characterEncoding=UTF-8";

        try {
            conn = DriverManager.getConnection(url,user,password);
            /*if (conn != null) {
                System.out.println("Connected to the database");
            }*/
        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        }
        return conn;
    }
}
