/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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
    @FXML private Label customProfit,customWinrate,customClosed,customTaken;
    @FXML private DatePicker dateSince,dateTo;
    @FXML private LineChart<String,Float> chart;
    
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

    public void updateTab(ArrayList array) {
        //DAILY
        System.out.println(array.get(1)+ " " + array.get(3));
        this.dailyProfit.setText((String) array.get(2));
        this.dailyWinrate.setText(String.valueOf(100*(Float.valueOf((String)array.get(3)) / Float.valueOf((String)array.get(1)))));
        this.dailyClosed.setText(String.valueOf(array.get(1)));
        this.dailyTaken.setText(String.valueOf(array.get(0)));
        
        //WEEKLY
        this.weeklyProfit.setText((String) array.get(8));
        this.weeklyWinrate.setText(String.valueOf(100*(Float.valueOf((String)array.get(9)) / Float.valueOf((String)array.get(7)))));
        this.weeklyClosed.setText(String.valueOf(array.get(7)));
        this.weeklyTaken.setText(String.valueOf(array.get(6)));
        
        //MOMNTHLY
        this.monthlyProfit.setText((String)array.get(14));
        this.monthlyWinrate.setText(String.valueOf(100*(Float.valueOf((String)array.get(15)) / Float.valueOf((String)array.get(13)))));
        this.monthlyClosed.setText((String)array.get(13));
        this.monthlyTaken.setText((String)array.get(12));
        
      
    }
    
    @FXML
    private void setCustomStats()
    {
        ArrayList array = this.mainController.getCustomStats(this.dateSince.getValue(),this.dateTo.getValue());
        System.out.println(array.get(2) );
        this.customProfit.setText((String) array.get(2));
        this.customWinrate.setText(String.valueOf(100*(Float.valueOf((String)array.get(3)) / Float.valueOf((String)array.get(1)))));
        this.customClosed.setText(String.valueOf(array.get(1)));
        this.customTaken.setText(String.valueOf(array.get(0)));
        
    
    
    
    }
    
    @FXML 
    private void changeChart(ActionEvent event)
    {
       Button btn = (Button) event.getSource();
        ArrayList array = this.mainController.getChart(btn.getId());
        chart.getData().clear();
        XYChart.Series<String,Float>series = new XYChart.Series<>();
        
      
        float result =0;
        for(int i = 1; i <= Integer.parseInt((String)array.get(0)) ;i++)
        {
            result  += Float.parseFloat((String)array.get(i));
            series.getData().add(new XYChart.Data<String,Float>(String.valueOf(i),result));
        
        
        }
        
       
        chart.getData().add(series);
    }

 
    
}
