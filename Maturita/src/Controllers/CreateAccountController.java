/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import maturita.Account;
import maturita.WindowControll;

/**
 * FXML Controller class
 *
 * @author skolniPC
 */
public class CreateAccountController extends WindowControll implements Initializable {

    @FXML
    private ImageView closeWindowImageView;
    @FXML
    JFXTextField accNumber,accBalance;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public Account createAccount() 
    {
        return new Account(accNumber.getText(),Integer.valueOf(this.accBalance.getText()),1,0,0,0);
    }

    
}
