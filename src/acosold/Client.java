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
public class Client {
    
    Connection con = MyConnection.getConnection();
    PreparedStatement ps;
    
    // ajouteer des clients au table add_client de la base de donnee
    public void addClient(String client)
    {
        try {
            ps = con.prepareStatement("INSERT INTO add_client (nom_client) VALUES(?)");
            ps.setString(1, client);
            
            if(ps.executeUpdate() > 0)
            {
                 JOptionPane.showMessageDialog(null, "Un nouveau client a été ajouté");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // grabing clients from the table add_client and put it in a ComboBox
    public void fillClientComboBox()
    {
        try {
            ps = con.prepareStatement("SELECT * FROM add_client");
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                AcoSold.jComboBox_Client.addItem(rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
