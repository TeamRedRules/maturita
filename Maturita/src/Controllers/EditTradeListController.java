/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import maturita.WindowControll;

/**
 * FXML Controller class
 *
 * @author skolniPC
 */
public class EditTradeListController extends WindowControll implements Initializable {

    @FXML
    private ImageView closeWindowImageView;
    @FXML
    private TableView<?> tradeTable;
    @FXML
    private TableColumn<?, ?> currency;
    @FXML
    private TableColumn<?, ?> positionSize;
    @FXML
    private TableColumn<?, ?> type;
    @FXML
    private TableColumn<?, ?> result;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

  
    
}
