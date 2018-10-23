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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Radouan
 */
public class Alimentation {
    
    Connection con = MyConnection.getConnection();
    PreparedStatement ps;
    
    public void alim(Double alim, String dateAlim, String motif)
    {
        try {
            ps = con.prepareStatement("INSERT INTO acosold_part1 (alim, date, motif) VALUES (?, ?, ?)");
            
            ps.setDouble(1, alim);
            ps.setString(2, dateAlim);
            ps.setString(3, motif);
            
            if(ps.executeUpdate() > 0)
            {
                JOptionPane.showMessageDialog(null, "Alimentation bien ajouter");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Alimentation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean verifyTextPart1()
    {
        if(AcoSold.jTextField_AlimSold.getText().equals("") || // AcoSold.jTextField_AlimSold.getText().equals("0.0") ||
                AcoSold.jDateChooser_Alim.getDate() == null ||
                AcoSold.jTextField_Motif.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Aucun champ vide n'est autorisé!");
            return false;
        }
        else if (AcoSold.jDateChooser_Alim.getDate().compareTo(new Date()) > 0)
                {
                    JOptionPane.showMessageDialog(null, "Vous ne pouvez pas ajouter une date du futur!");
           return false;
                }
        else
        {
          return true;  
        }
        
        
    }
    
    public void fillTableAlim(JTable table)
    {
        try {
            ps = con.prepareStatement("SELECT * FROM acosold_part1 "
                          + "WHERE MONTH(date) = MONTH(CURRENT_DATE()) "
                          + "AND YEAR(date) = YEAR(CURRENT_DATE())");            /*"SELECT * FROM acosold_part1"*/
            
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel)table.getModel();
            
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
            Logger.getLogger(Alimentation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Double totalAlim()
    {
        Double total = 0.0;
        try {
            ps = con.prepareStatement("SELECT * FROM acosold_part1 "
                          + "WHERE MONTH(date) = MONTH(CURRENT_DATE()) "
                          + "AND YEAR(date) = YEAR(CURRENT_DATE())");      //"SELECT * FROM acosold_part1"
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Double alim = rs.getDouble(2);
                //System.out.println(alim);
                total = total + alim;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Alimentation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }
    
     public boolean verifyTextPart2()
    {
        if(AcoSold.jComboBox_FullName.getSelectedItem().equals("") ||
                AcoSold.jComboBox_Client.getSelectedItem().equals("") ||
                AcoSold.jDateChooser_DateInt.getDate() == null ||
                AcoSold.jTextField_Frais.getText().equals("") ||
                AcoSold.jTextArea_MotifInt.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Aucun champ vide n'est autorisé!");
            return false;
        }
        else if (AcoSold.jDateChooser_DateInt.getDate().compareTo(new Date()) > 0)
                {
                    JOptionPane.showMessageDialog(null, "Vous ne pouvez pas ajouter une date du futur!");
           return false;
                }
        else
        {
          return true;  
        }
        
        
    }
    
    public void intervention(int technicien_id, String client, String date, Double frais, String motif)
    {
        try {
ps = con.prepareStatement("INSERT INTO acosold_part2"
                    + " (id_technician, nom_client, date_intervention, frais_divers, motif)"
                    + "VALUES (?, ?, ?, ?, ?)");
            
            ps.setInt(1, technicien_id);
            ps.setString(2, client);
            ps.setString(3, date);
            ps.setDouble(4, frais);
            ps.setString(5, motif);
            
            if(ps.executeUpdate() > 0)
            {
                JOptionPane.showMessageDialog(null, "Un déplacement est ajouté");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Alimentation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void fillComboBox()
    {
        
        try {
            ps = con.prepareStatement("SELECT * FROM signup");
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                String fName, lName;
                
                fName = rs.getString(2);
                lName = rs.getString(3);       
                AcoSold.jComboBox_FullName.addItem(fName + " " + lName);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Alimentation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public int returnUserId(String selectedItem)
    {
        int id = 1;
        try {
            ps = con.prepareStatement("SELECT * FROM signup WHERE nom = ?");
            ps.setString(1, selectedItem);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Alimentation.class.getName()).log(Level.SEVERE, null, ex);
        }
          return id;  
    }
    
    public void fillTableFrais(JTable table)
    {
        try {
            ps = con.prepareStatement("SELECT a.id, CONCAT_WS('  ',s.nom, s.prenom), a.nom_client,"
                    + " a.date_intervention, a.frais_divers, a.motif\n" +
                    "FROM signup AS s\n" +
                    "JOIN acosold_part2 AS a\n" +
                    "WHERE a.id_technician = s.id AND statut = 0");
            
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            
            Object[] row;
            while(rs.next())
            {
                row = new Object[6];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getDouble(5);
                row[5] = rs.getString(6);
                
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Alimentation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void fillComboBoxSolder()
    {
        
        try {
            ps = con.prepareStatement("SELECT * FROM signup");
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                String fName, lName;
                
                fName = rs.getString(2);
                lName = rs.getString(3);       
                AcoSold.jComboBox_FullNameTech.addItem(fName + " " + lName);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Alimentation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
     
    public int returnTechnicianId(String nom, String prenom)
    {
        int id = 1;
        try {
            ps = con.prepareStatement("SELECT * FROM signup WHERE nom = ? AND prenom = ?");
            ps.setString(1, nom);
            ps.setString(2, prenom);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Alimentation.class.getName()).log(Level.SEVERE, null, ex);
        }
          return id;  
    }
    
    public Double amountToBeGiven(int tenician_id)
    {
        Double amount = 0.0;
        try {
            ps = con.prepareStatement("SELECT frais_divers FROM"
                    + " acosold.acosold_part2 WHERE id_technician = ? AND statut = 0");
            ps.setInt(1, tenician_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Double firstDep = rs.getDouble(1);
                amount = amount + firstDep;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Alimentation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return amount;
    }
    
    public void convertStatusToOne(int techId)
    {
        try {
            ps = con.prepareStatement("UPDATE acosold_part2 SET statut = 1  WHERE id_technician = ?");
            ps.setInt(1, techId);
            
            if(ps.executeUpdate() > 0)
            {
                //JOptionPane.showMessageDialog(null, "Status updated to 1");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Alimentation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteInsertMoneyLeft(String date, Double money)
    {
        try {
            ps = con.prepareStatement("TRUNCATE TABLE money_left");
            ps.executeUpdate();
            
            ps = con.prepareStatement("INSERT INTO money_left (date,money_left) VALUES (?, ?)");
            ps.setString(1, date);
            ps.setDouble(2, money);
            
            ps.executeUpdate();
            //ResultSet rs = ps.executeQuery();
                    
        } catch (SQLException ex) {
            Logger.getLogger(Alimentation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void MoneyLeft()
    {
        try {
           // AcoSold aco = new AcoSold();
            ps = con.prepareStatement("SELECT * FROM money_left");
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                
                AcoSold.jTextField_NewSold.setText(String.valueOf(rs.getDouble(3)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Alimentation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Double moneyLeft()
    {
        Double mln = 0.0;
       try {
           
            ps = con.prepareStatement("SELECT * FROM money_left");
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                
                mln = rs.getDouble(3);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Alimentation.class.getName()).log(Level.SEVERE, null, ex);
        } 
       return mln;
    }
    
    public void lastMonthMoney()
    {
        try {
            Double lastMonthMoney = 0.0;
            ps = con.prepareStatement("TRUNCATE TABLE last_month_money");
            ps.executeUpdate();
            
            ps = con.prepareCall("SELECT * FROM money_left");
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                lastMonthMoney = rs.getDouble(3);
                
            }
            
            ps = con.prepareStatement("INSERT INTO last_month_money (last_month) VALUES (?)");
            ps.setDouble(1, lastMonthMoney);
            //ResultSet rs2 = ps.executeQuery();
            if(ps.executeUpdate() > 0)
            {
                AcoSold.jTextField_LastMonth.setText(String.valueOf(lastMonthMoney));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Alimentation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public Double returnLastMonthMoney()
    {
        Double lastMonthMoney = 0.0;
        try {
            
            ps = con.prepareStatement("SELECT * FROM last_month_money");
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                lastMonthMoney = rs.getDouble(2);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Alimentation.class.getName()).log(Level.SEVERE, null, ex);
        }
          return lastMonthMoney;  
    }
    
    
    
   public void grabLastMonthMoney()
   {
       try {
           ps = con.prepareStatement("SELECT * FROM last_month_money");
            
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                AcoSold.jTextField_LastMonth.setText(String.valueOf(rs.getDouble(2)));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Alimentation.class.getName()).log(Level.SEVERE, null, ex);
        }
       }
   
   public void truncateMoneyLeft()
   {
       
        try {
            ps = con.prepareStatement("TRUNCATE TABLE money_left");
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Alimentation.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
   
   public void insertFromNewMonth(Double newSold)
   {
        try {
            ps = con.prepareStatement("INSERT INTO money_left (money_left) VALUES (?)");
            ps.setDouble(1, newSold);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Alimentation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
   }
   
   public void delteteRowFrais(int id)
   {
        try {
            ps = con.prepareStatement("DELETE FROM acosold_part2 WHERE id = ?");
            ps.setInt(1, id);
            if(ps.executeUpdate() > 0)
            {
                JOptionPane.showMessageDialog(null, "Une ligne est supprimée");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Alimentation.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   }
   
   public Double totalFrais()
    {
        Double total = 0.0;
        try {
            ps = con.prepareStatement("SELECT * FROM acosold_part2 WHERE statut = 0");      //"SELECT * FROM acosold_part1"
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Double alim = rs.getDouble(5);
                //System.out.println(alim);
                total = total + alim;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Alimentation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }
   
//   public void soldNowTable(Double fullSoldNow)
//   {
//        try {
//            ps = con.prepareStatement("TRUNCATE TABLE sold_now_table");
//            ps.executeUpdate();
//            ps = con.prepareStatement("INSERT INTO sold_now_table (sold) VALUES (?)");
//            ps.setDouble(1, fullSoldNow);
//            ps.executeUpdate();
//            
//            
//        } catch (SQLException ex) {
//            Logger.getLogger(Alimentation.class.getName()).log(Level.SEVERE, null, ex);
//        }
//   }
    
    
//    public void insertIntoMoneyLeft(String soldNow)
//    {
//        try {
//            ps = con.prepareStatement("INSERT INTO money_left(money_left) VALUES (?)");
//            ps.setString(1, soldNow);
//            ps.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(Alimentation.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
}
