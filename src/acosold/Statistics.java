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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Radouan
 */
public class Statistics {
    
    Connection con = MyConnection.getConnection();
    PreparedStatement  ps;
    
    public void returnAPeriod(JTable table, String begining, String ending)
    {
        try {
            ps = con.prepareStatement("SELECT * FROM acosold_part1 WHERE date >= ? AND date <= ?");
            ps.setString(1, begining);
            ps.setString(2, ending);
            
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            
            while(rs.next())
            {
                row = new Object[3];
                row[0] = rs.getDouble(2);
                row[1] = rs.getString(3);
                row[2] = rs.getString(4);
                
                model.addRow(row);
                
            }
       
            
        } catch (SQLException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean isAlimTableEmpty(String begining, String ending)
    {
        boolean isThere = false;
        try {
            ps = con.prepareStatement("SELECT * FROM acosold_part1 WHERE date >= ? AND date <= ?");
            ps.setString(1, begining);
            ps.setString(2, ending);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                isThere = true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isThere;
    }
    
    public void returnAPeriodFrais(JTable table, String begining, String ending)
    {
        try {
            ps = con.prepareStatement("SELECT s.nom, s.prenom, a.nom_client,"
                                    + " a.date_intervention, a.frais_divers,"
                                    + " a.motif FROM acosold_part2 a, signup s"
                                    + " WHERE a.id_technician = s.id AND"
                                    + " date_intervention >= ? AND"
                                    + " date_intervention <= ? ORDER BY"
                                    + " date_intervention");
            ps.setString(1, begining);
            ps.setString(2, ending);
            
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            
            while(rs.next())
            {
                row = new Object[6];
                row[0] = rs.getString("nom");
                row[1] = rs.getString("prenom");
                row[2] = rs.getString("nom_client");
                row[3] = rs.getString("date_intervention");
                row[4] = rs.getString("frais_divers");
                row[5] = rs.getString("motif");
                
                model.addRow(row);
                
            }
       
            
        } catch (SQLException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean isFraisTableEmpty(String begining, String ending)
    {
        boolean isThere = false;
        try {
            ps = con.prepareStatement("SELECT s.nom, s.prenom, a.nom_client,"
                                    + " a.date_intervention, a.frais_divers,"
                                    + " a.motif FROM acosold_part2 a, signup s"
                                    + " WHERE a.id_technician = s.id AND"
                                    + " date_intervention >= ? AND"
                                    + " date_intervention <= ? ORDER BY"
                                    + " date_intervention");
            ps.setString(1, begining);
            ps.setString(2, ending);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                isThere = true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isThere;
    }
    
    public void fillComboBoxTechnicianStats()
    {
        
        try {
            ps = con.prepareStatement("SELECT * FROM signup");
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                String fName, lName;
                
                fName = rs.getString(2);
                lName = rs.getString(3);       
                StatForm.jComboBox_Technicien.addItem(fName + " " + lName);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Alimentation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void fillClientComboBoxStats()
    {
        try {
            ps = con.prepareStatement("SELECT * FROM add_client");
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                StatForm.jComboBox_Client.addItem(rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void fillTableWithSpesificClient(JTable table,String client, String begining, String ending)
    {
        try {
            ps = con.prepareStatement("SELECT s.nom, s.prenom, a.nom_client,"
                                    + " a.date_intervention, a.frais_divers,"
                                    + " a.motif FROM acosold_part2 a, signup s"
                                    + " WHERE a.id_technician = s.id AND"
                                    + " nom_client = ? AND"
                                    + " date_intervention >= ? AND"
                                    + " date_intervention <= ? ORDER BY"
                                    + " date_intervention");
            ps.setString(1, client);
            ps.setString(2, begining);
            ps.setString(3, ending);
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            
            while(rs.next())
            {
                row = new Object[6];
                
                row[0] = rs.getString("nom");
                row[1] = rs.getString("prenom");
                row[2] = rs.getString("nom_client");
                row[3] = rs.getString("date_intervention");
                row[4] = rs.getString("frais_divers");
                row[5] = rs.getString("motif");
                
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean isFraisTableEmptyClient(String client,  String begining, String ending)
    {
        boolean isThere = false;
        try {
            ps = con.prepareStatement("SELECT s.nom, s.prenom, a.nom_client,"
                                    + " a.date_intervention, a.frais_divers,"
                                    + " a.motif FROM acosold_part2 a, signup s"
                                    + " WHERE a.id_technician = s.id AND"
                                    + " nom_client = ? AND"
                                    + " date_intervention >= ? AND"
                                    + " date_intervention <= ? ORDER BY"
                                    + " date_intervention");
            ps.setString(1, client);
            ps.setString(2, begining);
            ps.setString(3, ending);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                isThere = true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isThere;
    }
    
    public void fillTableWithSpesificTechnicien(JTable table,String nom, String prenom,String begining, String ending)
    {
        try {
            ps = con.prepareStatement("SELECT s.nom, s.prenom, a.nom_client,"
                                    + " a.date_intervention, a.frais_divers,"
                                    + " a.motif FROM acosold_part2 a, signup s"
                                    + " WHERE a.id_technician = s.id AND"
                                    + " nom = ? AND prenom = ? AND"
                                    + " date_intervention >= ? AND"
                                    + " date_intervention <= ? ORDER BY"
                                    + " date_intervention");
            ps.setString(1, nom);
            ps.setString(2, prenom);
            ps.setString(3, begining);
            ps.setString(4, ending);
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            
            while(rs.next())
            {
                row = new Object[6];
                
                row[0] = rs.getString("nom");
                row[1] = rs.getString("prenom");
                row[2] = rs.getString("nom_client");
                row[3] = rs.getString("date_intervention");
                row[4] = rs.getString("frais_divers");
                row[5] = rs.getString("motif");
                
                model.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "try again");
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean isFraisTableEmptyFullName(String nom, String prenom,String begining, String ending)
    {
        boolean isThere = false;
        try {
            ps = con.prepareStatement("SELECT s.nom, s.prenom, a.nom_client,"
                                    + " a.date_intervention, a.frais_divers,"
                                    + " a.motif FROM acosold_part2 a, signup s"
                                    + " WHERE a.id_technician = s.id AND"
                                    + " nom = ? AND prenom = ? AND"
                                    + " date_intervention >= ? AND"
                                    + " date_intervention <= ? ORDER BY"
                                    + " date_intervention");
            ps.setString(1, nom);
            ps.setString(2, prenom);
            ps.setString(3, begining);
            ps.setString(4, ending);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                isThere = true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isThere;
    }
    
    public Double totalAlimTechnicien(String nom, String prenom, String begining, String ending)
    {
        Double total = 0.0;
        try {
            ps = con.prepareStatement("SELECT s.nom, s.prenom, a.nom_client,"
                                    + " a.date_intervention, a.frais_divers,"
                                    + " a.motif FROM acosold_part2 a, signup s"
                                    + " WHERE a.id_technician = s.id AND"
                                    + " nom = ? AND prenom = ?AND"
                                    + " date_intervention >= ? AND"
                                    + " date_intervention <= ? ORDER BY"
                                    + " date_intervention");      //"SELECT * FROM acosold_part2"
            ps.setString(1, nom);
            ps.setString(2, prenom);
            ps.setString(3, begining);
            ps.setString(4, ending);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Double alim = rs.getDouble("frais_divers");
                //System.out.println(alim);
                total = total + alim;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Alimentation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }
    
    public Double totalAlimDate(String begining, String ending)
    {
        Double total = 0.0;
        try {
            ps = con.prepareStatement("SELECT s.nom, s.prenom, a.nom_client,"
                                    + " a.date_intervention, a.frais_divers,"
                                    + " a.motif FROM acosold_part2 a, signup s"
                                    + " WHERE a.id_technician = s.id AND"
                                    + " date_intervention >= ? AND"
                                    + " date_intervention <= ? ORDER BY"
                                    + " date_intervention");
            ps.setString(1, begining);
            ps.setString(2, ending);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Double alim = rs.getDouble("frais_divers");
                //System.out.println(alim);
                total = total + alim;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Alimentation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }
    
    public Double totalAlimDateAlim(String begining, String ending)
    {
        Double total = 0.0;
        try {
            ps = con.prepareStatement("SELECT * FROM acosold_part1 WHERE date >= ? AND date <= ?");
            ps.setString(1, begining);
            ps.setString(2, ending);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Double alim = rs.getDouble("alim");
                //System.out.println(alim);
                total = total + alim;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Alimentation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }
    
    public Double totalAlimClient(String client, String begining, String ending)
    {
        Double total = 0.0;
        try {
            ps = con.prepareStatement("SELECT s.nom, s.prenom, a.nom_client,"
                                    + " a.date_intervention, a.frais_divers,"
                                    + " a.motif FROM acosold_part2 a, signup s"
                                    + " WHERE a.id_technician = s.id AND"
                                    + " nom_client = ? AND"
                                    + " date_intervention >= ? AND"
                                    + " date_intervention <= ? ORDER BY"
                                    + " date_intervention");      //"SELECT * FROM acosold_part2"
            ps.setString(1, client);
            ps.setString(2, begining);
            ps.setString(3, ending);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Double alim = rs.getDouble("frais_divers");
                //System.out.println(alim);
                total = total + alim;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Alimentation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }
}
