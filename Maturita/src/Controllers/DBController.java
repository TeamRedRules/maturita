/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import maturita.Account;

/**
 *
 * @author skolniPC
 */
public class DBController {
    private Connection connection;
    private Date date;
    
    public DBController () throws SQLException {
        this.ConnectToDB();
     }     
     public void ConnectToDB() throws SQLException
     {
       try {
        connection = null;
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:TradeDB.db");
        System.out.println("db jede");
        }
       
        catch(ClassNotFoundException | SQLException e)
        {
        System.out.println(e);
        }
     }
     
     public boolean login(String psw)
     {
        ResultSet rs = null;
        try {
            rs = connection.createStatement().executeQuery("Select * from Login");
            if(psw.equals(rs.getString(1)))
            {
                rs.close();
                return true;
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return false;
     }

    boolean doesAccExist() {
        ResultSet rs = null;
        try {
            rs = connection.createStatement().executeQuery("Select * from Account");
            if(rs.next())
            {
                rs.close();
                return true;
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return false;
        
    }
    
    public Account getActiveAccount()
    {
        int id = this.findActiveAccID();
         ResultSet rs = null;
         Account acc = null;
         // aktualizovat stav učtu v DB 
         
         // vytvoření účtu z DB 
        try {
            
            rs = connection.createStatement().executeQuery("Select * from Account where ID ="+id);
            acc = new Account(rs.getString(2),rs.getFloat(3),rs.getInt(4),rs.getFloat(5),rs.getFloat(6),rs.getFloat(7));
            rs.close();
            return acc;
            
        
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        try {    
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    return acc;
    // vrátí objekt Account s parametry z DB
    }

    private int findActiveAccID() {
        // najde ID aktivního účtu
           ResultSet rs = null;
        try {
            rs = connection.createStatement().executeQuery("Select ID from Account where IsActive = 1 ");
            int id = rs.getInt(1);
            
            rs.close();
            return id;
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return 0;
    }
}