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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        this.connection = null;
        Class.forName("org.sqlite.JDBC");
        this.connection = DriverManager.getConnection("jdbc:sqlite:TradeDB.db");
        
        }
       
        catch(ClassNotFoundException | SQLException e)
        {
        System.out.println(e);
        }
     }
     public void refresh()
     {
        
         
        try {
            if(this.connection.isClosed())
                try {
                    this.ConnectToDB();
                    System.out.println("spojeni obnoveno");
                } catch (SQLException ex) {
                    Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
                }
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
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

    public boolean doesAccExist() {
     
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
        System.out.println(id + " ID ");
     
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
    public int findActiveAccID() {
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
        
        int id = this.findActiveAccID();
        
        ArrayList<String> array = this.getAccountStatistics();
        PreparedStatement stm  = null;
    
        Account acc = this.getActiveAccount();
  
        // Když se uprava obchodu při změne  již zadaného resulut !!!
       
        try {
            
            String sql = "Update Account SET Winrate = ? , AvgWin = ?, RRR = ? WHERE ID = ?";
            stm = this.connection.prepareStatement(sql);
            String x;
            System.out.println(array.get(3) + "test");
           
           
            // pokud hodnota array.get(0) je jiní než 0 ( WINRATE)
            if(String.valueOf(array.get(0)).equals("null"))
            {
                
                stm.setFloat(1,100*(Float.parseFloat(array.get(1)) / Float.parseFloat(array.get(0))));
            }
             // když je hodnota 0, automaticky se vydělí 0, ošetření dělení 0,
            else
            {
               stm.setFloat(1,100*(Float.parseFloat(array.get(1)) / Float.parseFloat(array.get(0))));
                
            }
             
             
             
         
             // AVGWIN
            if (String.valueOf(array.get(1)).equals("null"))
            {
                stm.setFloat(2,Float.parseFloat(array.get(3))/1);
            }
            else if(String.valueOf(array.get(3)).equals("null"))
            {
                stm.setFloat(2,0);
            }
            else
            {
                stm.setFloat(2,Float.parseFloat(array.get(3))/Float.parseFloat(array.get(1)));
                
            }
            
            
            
            
            
            
            // RRR
            
            if(String.valueOf(array.get(2)).equals("null") && !String.valueOf(array.get(3)).equals("null"))
            {
                stm.setFloat(3,Float.parseFloat(array.get(3))/1);
                
            }
            else if (String.valueOf(array.get(3)).equals("null") && String.valueOf(array.get(2)).equals("null") )
            {
                stm.setFloat(3,0);
                
            }
            else if ((String.valueOf(array.get(3)).equals("null") && !String.valueOf(array.get(2)).equals("null")))
            {
                stm.setFloat(3, 0);
                
                    
            }
            else
            {
                stm.setFloat(3,-1*(Float.parseFloat(array.get(3))/Float.parseFloat(array.get(2))));
                
            }
            
            
           
            
            
            stm.setInt(4, id);
            System.out.println("update proběhl");
            stm.executeUpdate();
            
            stm.close();
            
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
          int id = this.findActiveAccID();
        try {
            rs  = connection.createStatement().executeQuery("SELECT * FROM Trade WHERE AccID = " + id);
       
            while(rs.next())
                tradeList.add(new Trade(rs.getInt(1),rs.getString(2),rs.getString(6),rs.getFloat(3),rs.getFloat(4),rs.getFloat(5),rs.getInt(7),rs.getInt(8),rs.getFloat(9), Date.valueOf(rs.getString(10))));
            rs.close();
       return tradeList;
            
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(tradeList.get(0).getCurrency());
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
          int id = this.findActiveAccID();
        try {
            // počet všech zavřených obchodů
        rs =  connection.createStatement().executeQuery("Select Count(*) From Trade where EndDate !=  'null' and AccID = " + id);
        tradeList.add(rs.getString(1));
        
       
        
        //počet všech uspešných obchodů
         rs = connection.createStatement().executeQuery("Select count (*) from Trade where  AccID = " + id + " and  Result > 0 ");
         tradeList.add(rs.getString(1));
         
         
          // bilance ztrátových obchodů
         rs=connection.createStatement().executeQuery("Select Sum(Result) from Trade where  AccID = " + id + " and  Result < 0 ");
         tradeList.add(rs.getString(1));
         
         
         // bilance výherních obchodů
         rs=connection.createStatement().executeQuery("Select Sum(Result) from Trade where  AccID = " + id + " and  Result > 0");
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
        int id = this.findActiveAccID();
        
        try {
            
            // DAILY 
            //TAKEN
            rs  = connection.createStatement().executeQuery("Select Count(*) From Trade where AccID = " +id + " and OpenDate = current_date");
            array.add(rs.getString(1));
            rs.close();
            //zavřene 
            rs =  connection.createStatement().executeQuery("Select Count(*) From Trade where EndDate !=  'null' and AccID = " + id + " and EndDate = current_date");
            array.add(rs.getString(1));
            rs.close();
            //SUM celkově uzavřených
            rs = connection.createStatement().executeQuery("SELECT SUM(result) FROM Trade WHERE AccID= " + id + " and EndDate =current_date");
            array.add(rs.getString(1));
            rs.close();
            
            rs = connection.createStatement().executeQuery("Select count (*) from Trade where AccID = " + id + " and  Result > 0  and EndDate = current_date");
            array.add(rs.getString(1));
            rs.close();
            
           
            rs=connection.createStatement().executeQuery("Select Sum(Result) from Trade where  AccID = " + id + " and  Result < 0 " + " and EndDate = current_date");
            array.add(rs.getString(1));
            rs.close();
            
           
            rs=connection.createStatement().executeQuery("Select Sum(Result) from Trade where  AccID = " + id + " and  Result > 0 " + " and EndDate = current_date");
            array.add(rs.getString(1));
            rs.close();
            
            // WEEKLY
             rs  = connection.createStatement().executeQuery("Select Count(*) From Trade where AccID = "  + id + "  and  OpenDate between datetime('now', '-6 days') AND datetime('now', 'localtime')");
             array.add(rs.getString(1));
             rs.close();
         
             rs  = connection.createStatement().executeQuery("Select Count(*) From Trade where AccID = "  + id + "  and  EndDate BETWEEN datetime('now', '-6 days') AND datetime('now', 'localtime')");
            array.add(rs.getString(1));
            rs.close();
         
             rs = connection.createStatement().executeQuery("SELECT SUM(Result) FROM Trade WHERE AccID = " + id + "  and  OpenDate BETWEEN datetime('now', '-6 days') AND datetime('now', 'localtime')");
             array.add(rs.getString(1));
             rs.close();
         
             rs = connection.createStatement().executeQuery("Select count (*) from Trade where  AccID = " + id + " and  Result > 0 " + " and EndDate BETWEEN datetime('now', '-6 days') AND datetime('now', 'localtime')");
             array.add(rs.getString(1));
             rs.close();
         
         //bilance proherních obchodů 
             rs=connection.createStatement().executeQuery("Select Sum(Result) from Trade where  AccID = " + id + " and  Result < 0 " + " and EndDate BETWEEN datetime('now', '-6 days') AND datetime('now', 'localtime')");
             array.add(rs.getString(1));
             rs.close();
         
         
            rs=connection.createStatement().executeQuery("Select Sum(Result) from Trade where  AccID = " + id + " and  Result > 0 " + " and EndDate BETWEEN datetime('now', '-6 days') AND datetime('now', 'localtime')");
            array.add(rs.getString(1));
            rs.close();
            
            // MONTHLY
            
             rs  = connection.createStatement().executeQuery("Select Count(*) From Trade where AccID = "  + id + "  and  EndDate between datetime('now', '-1 month') AND datetime('now', 'localtime')");
             array.add(rs.getString(1));
             rs.close();
         
         // všechny uzavřené obchody
            rs  = connection.createStatement().executeQuery("Select Count(*) From Trade where AccID= "  + id + "  and  EndDate BETWEEN datetime('now', '-1 month') AND datetime('now', 'localtime')");
            array.add(rs.getString(1));
            rs.close();
         
         // součet všech obchodů
            rs = connection.createStatement().executeQuery("SELECT SUM(result) FROM Trade WHERE AccID = " + id + "  and  EndDate BETWEEN datetime('now', '-1 month') AND datetime('now', 'localtime')");
            array.add(rs.getString(1));
            rs.close();
         
         //počet všech uspešných obchodů
             rs = connection.createStatement().executeQuery("Select count (*) from Trade where  AccID = " + id + " and  Result > 0 " + " and EndDate BETWEEN datetime('now', '-1 month') AND datetime('now', 'localtime')");
             array.add(rs.getString(1));
             rs.close();
         
         // bilance ztrátových obchodů
            rs=connection.createStatement().executeQuery("Select Sum(result) from Trade where  AccID= " +id + " and  Result < 0 " + " and EndDate BETWEEN datetime('now', '-1 month') AND datetime('now', 'localtime')");
             array.add(rs.getString(1));
             rs.close();
         // bilance výherních obchodů
            rs=connection.createStatement().executeQuery("Select Sum(result) from Trade where  AccID = " + id + " and  Result > 0 " + " and EndDate BETWEEN datetime('now', '-1 month') AND datetime('now', 'localtime')");
            array.add(rs.getString(1));
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return array;
    }

   public  ArrayList getCustomStats(LocalDate since,LocalDate to) {
       ArrayList array = new ArrayList();
       ResultSet rs= null; 
       System.out.println(since + " " + to);
       try {
            int id =this.findActiveAccID();
            
            rs  = connection.createStatement().executeQuery("Select Count(*) From Trade where AccID = "  + id + "  and  OpenDate between '"+since+"' AND '" + to+"'");
            array.add(rs.getString(1));
            
            rs  = connection.createStatement().executeQuery("Select Count(*) From Trade where AccID = "  + id + "  and  EndDate between '"+since+"' AND '" + to+"'");
            array.add(rs.getString(1));
            
            rs = connection.createStatement().executeQuery("SELECT SUM(Result) FROM Trade WHERE AccID = " + id + "  and  OpenDate between '"+since+"' AND '" + to+"'");
            array.add(rs.getString(1));
            
            rs = connection.createStatement().executeQuery("Select count (*) from Trade where  AccID = " + id + " and  Result > 0 " + " and EndDate between '"+since+"' AND '" + to+"'");
            array.add(rs.getString(1));
            
            rs=connection.createStatement().executeQuery("Select Sum(Result) from Trade where  AccID = " + id + " and  Result < 0 " + " and EndDate between '"+since+"' AND '" + to+"'");
            array.add(rs.getString(1));
         
         
            rs=connection.createStatement().executeQuery("Select Sum(Result) from Trade where  AccID = " + id + " and  Result > 0 " + " and EndDate between '"+since+"' AND '" + to+"'");
            array.add(rs.getString(1));
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
        return array;
    }

    public ArrayList getDailyChart() {
        
        return null;
    }

    public void createAccount(Account acc) {
        try {
            String sql = "INSERT INTO Account (AccNumber,Acc_balance,IsActive,Winrate,AvgWin,RRR) values (?,?,?,?,?,?)";
            PreparedStatement stm = this.connection.prepareStatement(sql);
            stm.setString(1,acc.getAccountNumber());
            stm.setFloat(2, acc.getBalance());
            stm.setFloat(3, acc.getIsActive());
            stm.setFloat(4,acc.getWinrate());
            stm.setFloat(5, acc.getAvg_win());
            stm.setFloat(6, acc.getRRR());   
            stm.executeUpdate();
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAccountInactive() {
        try {
            /* metoda nastaví parametr  účtu is_active na inactive */
            String sql = "Update Account SET IsActive = ?  WHERE ID = ?";
            PreparedStatement stm = this.connection.prepareStatement(sql);
            int id = this.findActiveAccID();
            stm.setInt(1,0);
            stm.setInt(2,id);
            stm.executeUpdate();
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setAccountActive(Account acc)
    {
        try {
            String sql = "Update Account SET IsActive = ?  WHERE AccNumber = ?";
            PreparedStatement stm = this.connection.prepareStatement(sql);
            stm.setInt(1,1);
            stm.setString(2, acc.getAccountNumber());
            stm.executeUpdate();
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    
    }
    
     public ObservableList<Account> getAllAccounts() 
   {
       ObservableList<Account> accList = FXCollections.observableArrayList();
        try {
            /* metoda vytvoří List<Account>  do kterého se nahrají všechny účty z DB, tento list se poté použije v JFXComboBoxu */
            
            ResultSet resultSet =connection.createStatement().executeQuery("SELECT * FROM Account");
            System.out.println("ACCOUNT JEDE ");
            while(resultSet.next())
            {
                Account userAccount = new Account(resultSet.getString(2),resultSet.getInt(3),resultSet.getInt(4),resultSet.getInt(5),resultSet.getInt(6),resultSet.getFloat(7));
                accList.add(userAccount);
                System.out.println(userAccount.getAccountNumber()+ " account");
            }
            resultSet.close();
            return accList;
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return accList;
   }

    public void createTrade(Trade trade) {
        
        try {
            String sql = "INSERT INTO Trade (Currency,PositionSize,SL,EnterPrice,Type,IsActive,AccID,Result,OpenDate) values (?,?,?,?,?,?,?,?,?)";
            PreparedStatement stm = this.connection.prepareStatement(sql);
            stm.setString(1,trade.getCurrency());
            stm.setFloat(2, trade.getPositionSize());
            stm.setFloat(3, trade.getSl());
            stm.setFloat(4, trade.getEnterPrice());
            stm.setString(5, trade.getType());
            stm.setInt(6,1);
            stm.setInt(7, trade.getAccID());
            stm.setFloat(8, trade.getResult());
            stm.setString(9,trade.getDate());
            
            
            stm.executeUpdate();
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateTrade(Trade trade) 
    {
       
    
         String sql;
        PreparedStatement stm = null;
        if(trade.getResult() != 0)
        {   
            
             try {
                 sql = "Update Trade SET Currency = ?,PositionSize = ? , SL = ? ,EnterPrice = ? ,Type = ? , Result = ? ,IsActive=? , EndDate = ? WHERE ID = ?";
                 stm = this.connection.prepareStatement(sql);
                 stm.setString(1,trade.getCurrency());
                 stm.setFloat(2,trade.getPositionSize());
                 stm.setFloat(3,trade.getSl());
                 stm.setFloat(4,trade.getEnterPrice());
                 stm.setString(5,trade.getType());
                 stm.setFloat(6,trade.getResult());
                 stm.setInt(7, 0);
                 stm.setFloat(9,trade.getID());
                 stm.setString(8,String.valueOf(new Date(System.currentTimeMillis())));
                 System.out.println("dokonceny trade");
                 System.out.println("Novy trade" + trade.getResult());
             } catch (SQLException ex) {
                 Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
             }
             
            
        
        
        }
        else{
             try {
                 sql = "Update Trade SET Currency = ?,PositionSize = ? , SL = ? ,EnterPrice = ? ,Type = ? , Result = ?  WHERE ID = ?";
                 stm = this.connection.prepareStatement(sql);
                 stm.setString(1,trade.getCurrency());
                 stm.setFloat(2,trade.getPositionSize());
                 stm.setFloat(3,trade.getSl());
                 stm.setFloat(4,trade.getEnterPrice());
                 stm.setString(5,trade.getType());
                 stm.setFloat(6,trade.getResult());
                 stm.setFloat(7,trade.getID());
                 System.out.println( " ID  "   + trade.getID() + " position size  " +   trade.getPositionSize());
             } catch (SQLException ex) {
                 Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
             }
            
        }
    
       
        try {
            stm.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
      
        
       }

    public void updateAccBalance(Trade trade) {
        try {
            String sql = "Update Account SET Acc_balance = ?   WHERE ID = ?";
            Account  acc = this.getActiveAccount();
            PreparedStatement stm = this.connection.prepareStatement(sql);
            
            stm.setInt(1, (int) ((acc.getBalance() + (trade.getResult() - trade.getOriginalResult()))));
            stm.setInt(2, this.findActiveAccID());
            stm.executeUpdate();
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
