/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acosold;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Radouan
 */
//this methode is used to signup a new user
public class Signin {
    
    Connection con = MyConnection.getConnection();
    PreparedStatement ps;
    
    public void addUser(String fName, String lName, String password)
    {
        try {
            ps = con.prepareStatement("INSERT INTO signup (nom, prenom, password) VALUES (?, ?, ?)");

            
            ps.setString(1, fName);
            ps.setString(2, lName);
            ps.setString(3, password);
            
            if(ps.executeUpdate() > 0)
            {
                JOptionPane.showMessageDialog(null, "Un utilisateur a été ajouté");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Signin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    // this methode  will be returning true or false value, if user existe true will be returned, else false
    // this methode is used to login
    public boolean logIn(String login, String pass)
    {
        boolean doesDataExiste = false;
        try {
            ps = con.prepareStatement("SELECT * FROM signup WHERE nom = ? AND password = ?");
            
            ps.setString(1, login);
            ps.setString(2, pass);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                doesDataExiste = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Signin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return doesDataExiste;
    }
}
