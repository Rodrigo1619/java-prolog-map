/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import mapviewer.MapViewer;

/**
 * FXML Controller class
 *
 * @author Zepeda22
 */
public class SplashController implements Initializable {
    private MapViewer mapviewer;
    @FXML
    private BorderPane borderpanelSplash;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
    }    

    @FXML
    private void handleSplashAction(MouseEvent event) {
        System.out.println("Se presiono el splash");
       // mapviewer.timer.schedule(, 0);
        mapviewer.tarea.run();
      
    }
    
    public void setMainClass(MapViewer controller){
      this.mapviewer = controller;
    }
    
}
