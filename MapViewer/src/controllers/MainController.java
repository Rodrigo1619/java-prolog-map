/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.File;
import java.io.IOException;
import java.lang.String;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import models.Place;
import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jxmapviewer.viewer.GeoPosition;
import repositories.PlaceRepositoryImpl;
import services.PlaceService;
import services.PlaceServiceImpl;
import utils.AccessWifi;

/**
 * FXML Controller class
 *
 * @author Zepeda22
 */
public class MainController implements Initializable {
    FXMLLoader viewOnline;
    FXMLLoader viewOffline;
    FXMLLoader viewInternetDisconnected;
    
    
    List<String> Places = new ArrayList<String>();
    
    //private BorderPane viewBorderPanel;
    private ComboBox<String> cmbArrive;
    @FXML
    private StackPane viewStackPanel;
    @FXML
    private ComboBox<String> cmbStart;
    @FXML
    private ComboBox<String> cmbEnd;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       // System.out.println("File path: " + );
        String path = new File("res/prologfiles/tarea.pl").getAbsolutePath().replace("\\", "\\\\");
      //  String[] splitPath = pathWithoutSplit.split("\\\\", -2);
        //System.err.println(splitPath.length);
        
        /* for (String a : splitPath)
             path.concat( a +" \\");
         */
 
        //System.out.println(a);
       
        Query q1 = 
        new Query( 
            "consult", 
            //new Term[] {new Atom("C:\\Users\\Zepeda22\\Documents\\NetBeansProjects\\MapViewer\\src\\res\\prologfiles\\tarea.pl")} 
           new Term[] {new Atom("src\\res\\prologfiles\\tarea.pl")}
        );
        //Query q1 = new Query("consult('"+ path +"')");
        
        System.out.println( "consult " + (q1.hasSolution() ? "succeeded" : "failed"));
        
        PlaceService placeService = new PlaceServiceImpl(new PlaceRepositoryImpl());
         
        for (Place tempPlace: placeService.getAllPlaces() ){
            Places.add(tempPlace.getName()) ;
        }
        
        //System.out.println(Places.size());
        ObservableList<String> lPlaces = FXCollections.observableList(Places);
         cmbStart.setItems(lPlaces);
         cmbEnd.setItems(lPlaces);
         
        System.out.println("java version: "+System.getProperty("java.version"));
        System.out.println("javafx.version: " + System.getProperty("javafx.version"));
         /*for(String a : placeService.getPlaces())
         Places = ;*/
         
         /*Places.forEach( place -> {
          place.getName();
         
         });*/
         //cmbArrive.setItems(Places.);
         //for(Place tempPlace : Places)
         //System.out.println( Places.);
        
      
        try {
            //Create
            viewOnline = new FXMLLoader(getClass().getResource("/views/OnlineView.fxml"));
            viewStackPanel.getChildren().add(0,viewOnline.load());
            viewOffline = new FXMLLoader(getClass().getResource("/views/OfflineView.fxml"));
            viewStackPanel.getChildren().add(1,viewOffline.load());
             viewStackPanel.getChildren().get(1).setVisible(false);
            //viewBorderPanel.setCenter(FXMLLoader.load(getClass().getResource("/views/OnlineView.fxml")));

          
                
            //viewStackPanel.getChildren().add( FXMLLoader.load(getClass().getResource("/views/OnlineView.fxml")));
    
             //viewStackPanel.getChildren().add( FXMLLoader.load(getClass().getResource("/views/InternetDisconnectedView.fxml")));

            //viewStackPanel.getChildren().add( FXMLLoader.load(getClass().getResource("/views/OfflineView.fxml")));
            
           
             //System.err.println( viewStackPanel.getChildren().get(0).getParent().getBoundsInLocal());
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  

    private void handleOnlineAction(ActionEvent event) throws IOException {
       
        //viewBorderPanel.getChildren().clear();
      
        viewStackPanel.getChildren().get(1).translateYProperty().set(0);
           viewStackPanel.getChildren().get(1).translateXProperty().set(0);
         
        viewStackPanel.getChildren().get(1).setVisible(false);
        viewStackPanel.getChildren().get(0).setVisible(true);
         
          //viewStackPanel.getChildren().get(0).setLayoutY(0);
       
        //viewBorderPanel.getChildren().add( FXMLLoader.load(getClass().getResource("/views/OnlineView.fxml")));
        //viewBorderPanel.setCenter(FXMLLoader.load(getClass().getResource("/views/OnlineView.fxml")));
    }

    private void handleOfflineAction(ActionEvent event) throws IOException {
       
        viewStackPanel.getChildren().get(0).translateYProperty().set(0);
           viewStackPanel.getChildren().get(0).translateXProperty().set(0);
        //viewBorderPanel.getChildren().clear();
        viewStackPanel.getChildren().get(0).setVisible(false);
        viewStackPanel.getChildren().get(1).setVisible(true);
        //viewBorderPanel.getChildren().add( FXMLLoader.load(getClass().getResource("/views/OfflineView.fxml")));
        //viewBorderPanel.setCenter(FXMLLoader.load(getClass().getResource("/views/OfflineView.fxml")));
    }

    @FXML
    private void handleStartAction(ActionEvent event) {
       /* GeoPosition geo = new GeoPosition(11.4873739, 105.0147696);
         MyWaypoint wayPoint = new MyWaypoint("Start Location", MyWaypoint.PointType.START, event, new GeoPosition(geop.getLatitude(), geop.getLongitude()));
        addWaypoint(wayPoint);*/
    }

    @FXML
    private void handleEndAction(ActionEvent event) {
    }
    
    
}
