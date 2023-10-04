/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Zepeda22
 */
public class InternetDisconnectedController implements Initializable {

    
    @FXML
    private Button btnReconnect;
    @FXML
    private Button btnForce;
    @FXML
    private BorderPane panelInternetDisconnected;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        panelInternetDisconnected.setMaxSize(32767, 32767);
        //panelInternetDisconnected.setMaxSize(500, 500);
        panelInternetDisconnected.setMinSize(0, 0);
        panelInternetDisconnected.setManaged(true);
         
     
    }    

    @FXML
    private void handleReconnectAction(ActionEvent event) throws IOException {
        /* FXMLLoader viewOnline = new FXMLLoader(getClass().getResource("/views/OnlineView.fxml"));
        OnlineController a = viewOnline.getController();
        a.reconnect();*/
        FXMLLoader viewOnline = new FXMLLoader(getClass().getResource("/views/OnlineView.fxml"));
         Parent root = (Parent) viewOnline.load();
          OnlineController onlineController = viewOnline.getController();
        if(onlineController.reconnect()){
             Node father = this.panelInternetDisconnected.getParent();
            if(father instanceof StackPane pane){
            pane.getChildren().remove(panelInternetDisconnected);
            
            }
        }
       
       /*if(viewOnline.getController().equals(null)){
           System.out.println("ES NULO");
       }*/
    }

    @FXML
    private void handleForceAction(ActionEvent event) {
    }
    
}
