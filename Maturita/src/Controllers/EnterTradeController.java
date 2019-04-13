/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class EnterTradeController extends WindowControll implements Initializable {

    @FXML
    private JFXTextField enterPriceText;
    @FXML
    private JFXTextField stopLossText;
    @FXML
    private JFXTextField positionSizeText;
    @FXML
    private JFXComboBox<String> currencyComboBox;
    @FXML
    private JFXComboBox<String> typeComboBox;
    
    private Date date;
    @FXML
    private JFXButton addTradeOK;
    @FXML
    private ImageView closeWindowImageView;
   
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ObservableList<String> currency = FXCollections.observableArrayList("EUR/USD","GBP/USD","AUD/USD","USD/USD","USD/CAD","GBP/JPY","GBP/NZD","EUR/AUD","GBP/CAD","GBP/AUD","DAX","EUR/CAD","EUR/NZD","EUR/JPY","NZD/USD","GOLD");
        currencyComboBox.setItems(currency);
        ObservableList<String> type = FXCollections.observableArrayList("BUY","SELL");
        typeComboBox.setItems(type); 
        date = new Date(System.currentTimeMillis());
    }    
    
    
    public Trade getTrade() 
    {
        
        try {
            return new Trade(0,currencyComboBox.getSelectionModel().getSelectedItem(),typeComboBox.getSelectionModel().getSelectedItem(),Float.valueOf(positionSizeText.getText()),Float.valueOf(stopLossText.getText()),Float.valueOf(enterPriceText.getText()),1,mainController.getActiveAccID(),0,this.date);
        } catch (ParseException ex) {
            Logger.getLogger(EnterTradeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
 
    
}
