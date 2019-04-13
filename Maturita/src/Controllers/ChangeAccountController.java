/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import maturita.Account;
import maturita.WindowControll;

/**
 * FXML Controller class
 *
 * @author skolniPC
 */
public class ChangeAccountController extends WindowControll implements Initializable {

    
    private @FXML JFXComboBox comboAcc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
     public void setAccList(ObservableList<Account> accounts)
    {/* metoda má za úkol vytvořit seznam všech účtů z DB a uložit je do comboBoxu */
        
     
        this.comboAcc.setItems(accounts);
    
    
    }

    public Account getSelectedAccount() {
        return (Account) comboAcc.getSelectionModel().getSelectedItem();
        
    }

  
    
}
