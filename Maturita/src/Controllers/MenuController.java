/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import maturita.Account;
import maturita.WindowControll;

/**
 * FXML Controller class
 *
 * @author skolniPC
 */
public class MenuController extends WindowControll implements Initializable {

    @FXML private Label labBalance,labWinrate,labAvgWin,labRRR,labAcc;
    @FXML private Label dailyProfit,dailyWinrate,dailyClosed,dailyTaken;
    @FXML private Label weeklyProfit,weeklyWinrate,weeklyClosed,weeklyTaken;
    @FXML private Label monthlyProfit,monthlyWinrate,monthlyClosed,monthlyTaken;
    
    private Account acc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setAccount(Account acc)
    {
        this.acc = acc;
        this.setStatistics();
    }
    
    private void setStatistics()
    {
        this.labBalance.setText(String.valueOf(this.acc.getBalance()));
        this.labWinrate.setText(String.valueOf(this.acc.getWinrate()));
        this.labAvgWin.setText(String.valueOf(this.acc.getAvg_win()));
        this.labRRR.setText(String.valueOf(this.acc.getRRR()));
        this.labAcc.setText(String.valueOf(this.acc.getAccountNumber()));
    }

    void updateTab(ArrayList array) {
        //DAILY
        
        this.dailyProfit.setText((String) array.get(2));
        this.dailyWinrate.setText(String.valueOf(100*(Float.valueOf((String)array.get(3)) / Float.valueOf((String)array.get(1)))));
        this.dailyClosed.setText(String.valueOf(array.get(1)));
        this.dailyTaken.setText(String.valueOf(array.get(0)));
        
        //WEEKLY
        this.weeklyProfit.setText((String) array.get(8));
        this.weeklyWinrate.setText(String.valueOf(100*(Float.valueOf((String)array.get(9)) / Float.valueOf((String)array.get(7)))));
        this.weeklyClosed.setText(String.valueOf(array.get(7)));
        this.weeklyTaken.setText(String.valueOf(array.get(6)));
        
      
    }

 
    
}
