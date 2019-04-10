/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maturita;

import Controllers.MainController;
import java.sql.SQLException;
import java.text.ParseException;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author skolniPC
 */
public  abstract class WindowControll {
    
    protected double initialX, initialY;
    protected  AnchorPane x;
    public MainController mainController;
    protected Button button;
    public ImageView closeWindowImageView,minimizeWindowImageView;

     

    
    public  void closeWindow(MouseEvent event)
    {
        Stage stage = (Stage) closeWindowImageView.getScene().getWindow();
        stage.close();
    }
    
    public void minimizeWindow(MouseEvent event)
    {
     
        Stage stage = (Stage) minimizeWindowImageView.getScene().getWindow();
        stage.setIconified(true);
    }
    
   public void setMainController(MainController mainController)
    {
        System.out.println("metoda setMainController jede");
        this.mainController = mainController;
    }
    
    public void changeScene(ActionEvent event) throws SQLException, ParseException
    {
        button = (Button) event.getSource();
        
        this.mainController.changeScene(button.getId());
    }
    
    
    public void windowDragged(MouseEvent evt)
    {
        x=(AnchorPane) evt.getSource();
        x.getScene().getWindow().setX(evt.getScreenX()-initialX);
        x.getScene().getWindow().setY(evt.getScreenY()-initialY);
            
    }
    
    public void mousePressed(MouseEvent evt)
    {
     initialX = evt.getSceneX();
     initialY = evt.getSceneY();
    }
    
  
    
    
    
}
