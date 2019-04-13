/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import maturita.Trade;
import maturita.WindowControll;

/**
 * FXML Controller class
 *
 * @author skolniPC
 */
public class EditTradeController extends WindowControll implements Initializable {

     private Trade choosenTrade;
    @FXML private JFXComboBox currencyCombo,typeCombo;
    @FXML private JFXTextField enterPriceText,stopLossText,positionSizeText,result;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

     public void setTrade(Trade selectedItem) {
        this.choosenTrade = selectedItem;
        System.out.println("trad jee");
        this.intializeTrade();
        
    }
    
    public void intializeTrade()
    {   
      
       ObservableList<String> currency = FXCollections.observableArrayList("EUR/USD","GBP/USD","AUD/USD","USD/USD","USD/CAD","GBP/JPY","GBP/NZD","EUR/AUD","GBP/CAD","GBP/AUD","DAX","EUR/CAD","EUR/NZD","EUR/JPY","NZD/USD","GOLD");
       this.currencyCombo.getItems().addAll(currency);
       
       ObservableList<String> type = FXCollections.observableArrayList("BUY","SELL");
       this.typeCombo.setItems(type);
       
       this.typeCombo.setValue(choosenTrade.getType());
        this.currencyCombo.setValue(choosenTrade.getCurrency());
       this.enterPriceText.setText(String.valueOf(choosenTrade.getEnterPrice()));
       this.stopLossText.setText(String.valueOf(choosenTrade.getSl()));
       this.positionSizeText.setText(String.valueOf(choosenTrade.getPositionSize()));
       this.result.setText(String.valueOf(this.choosenTrade.getResult()));
       
       choosenTrade.setOriginalResult(Float.valueOf(this.result.getText()));
    }
    
       public Trade update()  
    {
       
            choosenTrade.setCurrency(currencyCombo.getSelectionModel().getSelectedItem().toString());
            choosenTrade.setEnterPrice(Float.valueOf(this.enterPriceText.getText()));
            choosenTrade.setResult(Float.valueOf(this.result.getText()));
            
            choosenTrade.setPositionSize(Float.valueOf(this.positionSizeText.getText()));
           
            choosenTrade.setSl(Float.valueOf(this.stopLossText.getText()));
            choosenTrade.setType(this.typeCombo.getSelectionModel().getSelectedItem().toString()); 
            return choosenTrade;
    }
}
