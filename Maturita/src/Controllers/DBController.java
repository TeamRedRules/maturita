/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import maturita.Account;
import maturita.Trade;

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
    
 
    /** metoda   která nalezne ID aktivního účtu v DB
     * 
     * @return int ID  
     */
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

    /** 
     * metoda  Aktualizuje stav atributů Winrate,AVGWIN, RRR v DB
     * za použití metody getAccountTrades()
     */
    public void updateAcc() {
        ArrayList<String> array = this.getAccountStatistics();
        int id = this.findActiveAccID();
        PreparedStatement stm  = null;
       
        try {
            String sql = "Update Account SET Winrate = ? , AvgWin = ?, RRR = ?   WHERE ID = ?";
            stm = this.connection.prepareStatement(sql);
            String x;
            x = String.valueOf(array.get(2));
            boolean y = x.equals(String.valueOf(array.get(2)));
           
            // pokud hodnota array.get(0) je jiní než 0 ( WINRATE)
             if(!String.valueOf(array.get(0)).equals("null"))
            {
                System.out.println(array.get(1) + " " + array.get(0));
                stm.setFloat(1,100*(Float.parseFloat(array.get(1)) / Float.parseFloat(array.get(0))));
            }
             // když je hodnota 0, automaticky se vydělí 0, ošetření dělení 0,
            else
            {
               
                stm.setFloat(1,Float.parseFloat(array.get(1)) / 1);
            }
         
             // AVGWIN
            if (!String.valueOf(array.get(1)).equals("null"))
            {
                stm.setFloat(2,Float.parseFloat(array.get(3))/1);
            }
            else
            {
                stm.setFloat(2,Float.parseFloat(array.get(3))/Float.parseFloat(array.get(1)));
                
            }
            // RRR
            
            if(!String.valueOf(array.get(2)).equals("null"))
            {
                stm.setFloat(3,-1*(Float.parseFloat(array.get(3))/Float.parseFloat(array.get(2))));
            }
            else
            {
                stm.setFloat(3,Float.parseFloat(array.get(3))/1);
            }
            
            stm.setInt(4, id);
            System.out.println("update proběhl");
            stm.executeUpdate();
           
       
            
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    /**metoda vrátí ArrayList tradeList, který obsahuje všechny obchody daného účtu
     *
     * @return ArrayList tradeList
     */
    public ArrayList getTrades() {
          ResultSet rs = null;
          ArrayList<Trade> tradeList = new ArrayList();
        try {
            rs  = connection.createStatement().executeQuery("SELECT * FROM Trade WHERE ID = " + this.findActiveAccID());
       
            while(rs.next())
                tradeList.add(new Trade(rs.getInt(1),rs.getString(2),rs.getString(6),rs.getFloat(3),rs.getFloat(4),rs.getFloat(5),rs.getInt(7),rs.getInt(8),rs.getFloat(9), Date.valueOf(rs.getString(10))));
             rs.close();
       return tradeList;
            
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tradeList;
       
    }

    /**Metoda vrací ArrayList<String>  s obsahem  
     * -[0] = počet zavřených obchodů
     * -[1] = počet výdělečných obchodů
     * -[2] = bilance ztrátových obchodů
     * -[3] = bilance výherních obchodů
     * 
     * pro aktivní účet
     * @return ArrayList
     */
    public ArrayList<String> getAccountStatistics() {
          ResultSet rs = null;
          ArrayList<String> tradeList = new ArrayList();
       
             
        try {
            // počet všech zavřených obchodů
        rs =  connection.createStatement().executeQuery("Select Count(*) From Trade where EndDate !=  'null' and AccID = " + this.findActiveAccID());
        tradeList.add(rs.getString(1));
        
        //počet všech uspešných obchodů
         rs = connection.createStatement().executeQuery("Select count (*) from Trade where  AccID = " + this.findActiveAccID() + " and  Result > 0 ");
         tradeList.add(rs.getString(1));
         
          // bilance ztrátových obchodů
         rs=connection.createStatement().executeQuery("Select Sum(Result) from Trade where  AccID = " + this.findActiveAccID() + " and  Result < 0 ");
         tradeList.add(rs.getString(1));
         
         
         // bilance výherních obchodů
         rs=connection.createStatement().executeQuery("Select Sum(Result) from Trade where  AccID = " + this.findActiveAccID() + " and  Result > 0");
         tradeList.add(rs.getString(1));
         rs.close();
         
         
            
            
         
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         
     
     
     
        
        return tradeList;
            
    
        }
    
    public ArrayList updateTab()
    {
        ArrayList array = new ArrayList();
        ResultSet rs = null;
        
        try {
            
            // DAILY 
            //TAKEN
            rs  = connection.createStatement().executeQuery("Select Count(*) From Trade where AccID = " + this.findActiveAccID() + " and OpenDate = current_date");
            array.add(rs.getString(1));
            //zavřene 
            rs =  connection.createStatement().executeQuery("Select Count(*) From Trade where EndDate !=  'null' and AccID = " + this.findActiveAccID() + " and EndDate = current_date");
            array.add(rs.getString(1));
            //SUM celkově uzavřených
            rs = connection.createStatement().executeQuery("SELECT SUM(result) FROM Trade WHERE AccID= " + this.findActiveAccID() + " and EndDate =current_date");
            array.add(rs.getString(1));
            
            rs = connection.createStatement().executeQuery("Select count (*) from Trade where AccID = " + this.findActiveAccID() + " and  Result > 0  and EndDate = current_date");
            array.add(rs.getString(1));
            
           
            rs=connection.createStatement().executeQuery("Select Sum(Result) from Trade where  AccID = " + this.findActiveAccID() + " and  Result < 0 " + " and EndDate = current_date");
            array.add(rs.getString(1));
            
           
            rs=connection.createStatement().executeQuery("Select Sum(Result) from Trade where  AccID = " + this.findActiveAccID() + " and  Result > 0 " + " and EndDate = current_date");
            array.add(rs.getString(1));
            
            // WEEKLY
             rs  = connection.createStatement().executeQuery("Select Count(*) From Trade where AccID = "  + this.findActiveAccID() + "  and  OpenDate between datetime('now', '-6 days') AND datetime('now', 'localtime')");
             array.add(rs.getString(1));
         
             rs  = connection.createStatement().executeQuery("Select Count(*) From Trade where AccID = "  + this.findActiveAccID() + "  and  EndDate BETWEEN datetime('now', '-6 days') AND datetime('now', 'localtime')");
            array.add(rs.getString(1));
         
             rs = connection.createStatement().executeQuery("SELECT SUM(Result) FROM Trade WHERE AccID = " + this.findActiveAccID() + "  and  OpenDate BETWEEN datetime('now', '-6 days') AND datetime('now', 'localtime')");
             array.add(rs.getString(1));
         
             rs = connection.createStatement().executeQuery("Select count (*) from Trade where  AccID = " + this.findActiveAccID() + " and  Result > 0 " + " and EndDate BETWEEN datetime('now', '-6 days') AND datetime('now', 'localtime')");
             array.add(rs.getString(1));
         
         //bilance proherních obchodů 
             rs=connection.createStatement().executeQuery("Select Sum(Result) from Trade where  AccID = " + this.findActiveAccID() + " and  Result < 0 " + " and EndDate BETWEEN datetime('now', '-6 days') AND datetime('now', 'localtime')");
             array.add(rs.getString(1));
         
         
            rs=connection.createStatement().executeQuery("Select Sum(Result) from Trade where  AccID = " + this.findActiveAccID() + " and  Result > 0 " + " and EndDate BETWEEN datetime('now', '-6 days') AND datetime('now', 'localtime')");
            array.add(rs.getString(1));
            
            // MONTHLY
            
             rs  = connection.createStatement().executeQuery("Select Count(*) From Trade where AccID = "  + this.findActiveAccID() + "  and  EndDate between datetime('now', '-1 month') AND datetime('now', 'localtime')");
             array.add(rs.getString(1));
         
         // všechny uzavřené obchody
            rs  = connection.createStatement().executeQuery("Select Count(*) From Trade where AccID= "  + this.findActiveAccID() + "  and  EndDate BETWEEN datetime('now', '-1 month') AND datetime('now', 'localtime')");
            array.add(rs.getString(1));
         
         // součet všech obchodů
            rs = connection.createStatement().executeQuery("SELECT SUM(result) FROM Trade WHERE AccID = " + this.findActiveAccID() + "  and  EndDate BETWEEN datetime('now', '-1 month') AND datetime('now', 'localtime')");
            array.add(rs.getString(1));
         
         //počet všech uspešných obchodů
             rs = connection.createStatement().executeQuery("Select count (*) from Trade where  AccID = " + this.findActiveAccID() + " and  Result > 0 " + " and EndDate BETWEEN datetime('now', '-1 month') AND datetime('now', 'localtime')");
             array.add(rs.getString(1));
         
         // bilance ztrátových obchodů
            rs=connection.createStatement().executeQuery("Select Sum(result) from Trade where  AccID= " + this.findActiveAccID() + " and  Result < 0 " + " and EndDate BETWEEN datetime('now', '-1 month') AND datetime('now', 'localtime')");
             array.add(rs.getString(1));
         // bilance výherních obchodů
            rs=connection.createStatement().executeQuery("Select Sum(result) from Trade where  AccID = " + this.findActiveAccID() + " and  Result > 0 " + " and EndDate BETWEEN datetime('now', '-1 month') AND datetime('now', 'localtime')");
            array.add(rs.getString(1));
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return array;
    }
    
    
      
        
    }
