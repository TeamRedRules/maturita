/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import maturita.Trade;
import maturita.WindowControll;

/**
 * FXML Controller class
 *
 * @author skolniPC
 */
public class EditTradeListController extends WindowControll implements Initializable {

    @FXML private TableView<Trade> tradeTable;
    @FXML private TableColumn <Trade,String> currency;
    @FXML private TableColumn <Trade,Float> positionSize;
    @FXML private TableColumn <Trade,String> type;
    @FXML private TableColumn <Trade,Float>  result;
    
    /**
     * Initializes the controller class.
     */
    
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        currency.setCellValueFactory(new PropertyValueFactory<>("currency"));
        positionSize.setCellValueFactory(new PropertyValueFactory<>("PositionSize"));
        type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        result.setCellValueFactory(new PropertyValueFactory<>("Result"));
    }    
    
    
    
     public void updateTableView(ArrayList array) throws ParseException
    {   
        tradeTable.getItems().setAll(array);
        
        
    }
    
     public Trade sendChoosenTrade() throws SQLException, ParseException
     {
        
         return tradeTable.getSelectionModel().getSelectedItem();
         
     
     }
  
    
}
