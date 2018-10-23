/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acosold;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Radouan
 */
public class MainAcoClass {
    
    public static Connection getConnection()
    {
        Connection con = null;
        
        ConnectToBase ctb = new ConnectToBase();
        String adressIp;
        
        adressIp = ctb.getAddress();
        String port = "3306";
        String db = "acosold";
        String login = "root";
        String pass = "root";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + adressIp  + ":" + port + "/" + db + "?useSSL=false", login, pass);
            Login ul = new Login();
            ul.pack();
            ul.setVisible(true);
            ul.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            ul.setLocationRelativeTo(null);
            
           
        } catch (ClassNotFoundException | SQLException ex) {
            //System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Assurez-vous d'avoir entré la bonne adresse ip!");
            ConnectDB cdb = new ConnectDB();
            cdb.pack();
            cdb.setVisible(true);
            cdb.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            cdb.setLocationRelativeTo(null);
           
        }
        
        return con;
  }
    
    public static void main(String[] args)
    {
         MainAcoClass.getConnection();
       
        
    }
}
