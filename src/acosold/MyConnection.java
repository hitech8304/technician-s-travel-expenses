/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acosold;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Radouan
 */
public class MyConnection {
    
    public static Connection getConnection()
    {
        Connection con = null;
        ConnectToBase ctb = new ConnectToBase();
        String adressIp = ctb.getAddress();
       // String adressIp = "localhost";
        String port = "3306";
        String db = "acosold";
        String login = "root";
        String pass = "root";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + adressIp  + ":" + port + "/" + db + "?useSSL=false", login, pass);

        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return con;
        
    }
    
}
