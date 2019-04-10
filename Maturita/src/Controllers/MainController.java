/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author skolniPC
 */
public class MainController {
    
    private LoginController loginController;
    private MenuController menuController;
    private EnterTradeController enterTradeController;
    private EditTradeListController editTradeListController;
    private EditTradeController editTradeController;
    private CreateAccountController createAccountController;
    private ChangePasswordController changePasswordController;
    private ChangeAccountController changeAccountController;
    private  Stage secondaryStage;
    
    private FXMLLoader loginFXML,menuFXML,enterTradeFXML,editTradeListFXML,editTradeFXML,createAccountFXML,changePasswordFXML,changeAccountFXML;
    private Parent loginParent,menuParent,enterTradeParent,editTradeListParent,editTradeParent,createAccountParent,changePasswordParent,changeAccountParent;
    private Scene loginScene,menuScene,enterTradeScene,editTradeListScene,editTradeScene,createAccountScene,changePasswordScene,changeAccountScene;
    @FXML private Stage stage;
    
    public MainController(Stage stage) throws IOException
    {
        secondaryStage = new Stage();
        secondaryStage.initStyle(StageStyle.UNDECORATED);
        //Set LOGIN
        this.loginFXML = new FXMLLoader(getClass().getResource("/FXML/Login.fxml"));
        this.loginParent = loginFXML.load();
        this.loginController =(LoginController) this.loginFXML.getController();
        this.loginScene = new Scene(loginParent);
        this.loginController.setMainController(this);
        
        //SET MENU
        this.menuFXML = new FXMLLoader(getClass().getResource("/FXML/Menu.fxml"));
        this.menuParent = menuFXML.load();
        this.menuController =(MenuController) this.menuFXML.getController();
        this.menuScene = new Scene(menuParent);
        this.menuController.setMainController(this);
        
        //SET enterTrade
        this.enterTradeFXML = new FXMLLoader(getClass().getResource("/FXML/EnterTrade.fxml"));
        this.enterTradeParent = enterTradeFXML.load();
        this.enterTradeController =(EnterTradeController) this.enterTradeFXML.getController();
        this.enterTradeScene = new Scene(enterTradeParent);
        this.enterTradeController.setMainController(this);
        
        //SET editTradeList
        this.editTradeListFXML = new FXMLLoader(getClass().getResource("/FXML/EditTradeList.fxml"));
        this.editTradeListParent = editTradeListFXML.load();
        this.editTradeListController =(EditTradeListController) this.editTradeListFXML.getController();
        this.editTradeListScene = new Scene(editTradeListParent);
        this.editTradeListController.setMainController(this);
        
        //SET editTrade
        this.editTradeFXML = new FXMLLoader(getClass().getResource("/FXML/EditTrade.fxml"));
        this.editTradeParent = editTradeFXML.load();
        this.editTradeController =(EditTradeController) this.editTradeFXML.getController();
        this.editTradeScene = new Scene(editTradeParent);
        this.editTradeController.setMainController(this);
        
        //SET createAccount
        this.createAccountFXML = new FXMLLoader(getClass().getResource("/FXML/CreateAccount.fxml"));
        this.createAccountParent = createAccountFXML.load();
        this.createAccountController =(CreateAccountController) this.createAccountFXML.getController();
        this.createAccountScene = new Scene(createAccountParent);
        this.createAccountController.setMainController(this);
        
        //SET changePassword
        this.changePasswordFXML = new FXMLLoader(getClass().getResource("/FXML/ChangePassword.fxml"));
        this.changePasswordParent = changePasswordFXML.load();
        this.changePasswordController =(ChangePasswordController) this.changePasswordFXML.getController();
        this.changePasswordScene = new Scene(changePasswordParent);
        this.changePasswordController.setMainController(this);
        
        //SET changeAccount
        this.changeAccountFXML = new FXMLLoader(getClass().getResource("/FXML/ChangeAccount.fxml"));
        this.changeAccountParent = changeAccountFXML.load();
        this.changeAccountController =(ChangeAccountController) this.changeAccountFXML.getController();
        this.changeAccountScene = new Scene(changeAccountParent);
        this.changeAccountController.setMainController(this);
        
        
        
        
        
        
        this.stage = stage;
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(loginScene);
        
        stage.show();
    }
    
    
    public void changeScene(String id)
    {
        switch(id)
        {
            // potvrzení hesla -> menu
            case"login":
                this.stage.setScene(menuScene);
                break;
            // změnění hesla -> changePasswordScene
            case"changePsw":
                this.stage.setScene(this.changePasswordScene);
                break;
            // potvrzení změněného hesla
            case"newPsw":
                this.stage.setScene(loginScene);
                break;
           // vytvořit nový učet -> createAcc     
            case"addAccount":
                this.secondaryStage.setScene(this.createAccountScene);
                this.secondaryStage.show();
                break;
            case"changeAcc":
                this.secondaryStage.setScene(this.changeAccountScene);
                this.secondaryStage.show();
                break;
            case"addTrade":
                this.secondaryStage.setScene(this.enterTradeScene);
                this.secondaryStage.show();
                break;
            case"editTradeList":
                this.secondaryStage.setScene(this.editTradeListScene);
                this.secondaryStage.show();
                break;
            case"confirmEdit":
                break;
            case"confirmChangeAcc":
                break;
            case"createAcc":
                break;
                
            
        
        
        
        }
    
    
    }
}
