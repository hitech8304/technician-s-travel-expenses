/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acosold;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Radouan
 */
public class ConnectToBase {
    
    public void setAddress(String address)
    {
        FileWriter fw;
        try {
            fw = new FileWriter("rad.txt");
            PrintWriter pw = new PrintWriter(fw);
            
            pw.println(address);
            pw.close();
             
        } catch (IOException ex) {
            Logger.getLogger(ConnectToBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getAddress()
    {
        String address = null;
        FileReader fr;
        try {
            fr = new FileReader("rad.txt");
            BufferedReader br = new BufferedReader(fr);
            String str;
            while((str = br.readLine()) != null)
            {
                address = str;
            }
        } catch (IOException ex ) {
            JOptionPane.showMessageDialog(null, "Erreur de connexion!");
        }
    return address;
    }
    
}
