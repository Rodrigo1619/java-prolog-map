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
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.Place;
import models.StreetPoint;
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
    public FXMLLoader viewOnline;
    public FXMLLoader viewInternetDisconnected;
    
    
    public final List<String> Places = new ArrayList<String>();
    
    public PlaceService placeService = new PlaceServiceImpl(new PlaceRepositoryImpl());
    
    private OnlineController onlineController;
    private InternetDisconnectedController internetDisconnectedController;
    //private BorderPane viewBorderPanel;
    @FXML
    public StackPane viewStackPanel;
    @FXML
    private ComboBox<String> cmbStart;
    @FXML
    private ComboBox<String> cmbEnd;
    @FXML
    public BorderPane mainBorderPane;
    @FXML
    private VBox vboxSelectionPlaces;
    @FXML
    private ImageView imgviewIcon;
    @FXML
    private Button btnSearch;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        

        Query q1 = 
        new Query( 
            "consult", 
           new Term[] {new Atom("src\\res\\prologfiles\\tarea.pl")}
        );
        
        System.out.println( "consult " + (q1.hasSolution() ? "succeeded" : "failed"));
        
        
         
        for (Place tempPlace: placeService.getAllPlaces() ){
            Places.add(tempPlace.getName()) ;
        }
        
        
        ObservableList<String> lPlaces = FXCollections.observableList(Places);
        cmbStart.setItems(lPlaces);
        cmbEnd.setItems(lPlaces);
         
        System.out.println("java version: "+System.getProperty("java.version"));
        System.out.println("javafx.version: " + System.getProperty("javafx.version"));

        
        viewOnline = new FXMLLoader(getClass().getResource("/views/OnlineView.fxml"));
        viewInternetDisconnected = new FXMLLoader(getClass().getResource("/views/InternetDisconnectedView.fxml"));

        
        try {
            //Create
            System.out.println("INTERNET STATE:" + AccessWifi.isInternetAvailable());
               if(AccessWifi.isInternetAvailable()){
                   setOnlienViewForce();
               }else{
                   setInternetDisconnectedForce();
               
               }

        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
    public void toogleSidePanel(boolean state){
        if(state==true){
            vboxSelectionPlaces.setMaxWidth(281);
            imgviewIcon.setFitWidth(96);
        }else{
            vboxSelectionPlaces.setMaxWidth(0);
            imgviewIcon.setFitWidth(0);
        }
     
    }

    @FXML
    private void handleSearchAction(ActionEvent event) {
      
        onlineController.setRoad(  placeService.getRoad(cmbStart.getValue(), cmbEnd.getValue()));
    }
    
    public void setOnlienViewForce() throws IOException{
        viewStackPanel.getChildren().add(viewOnline.load());
        onlineController = viewOnline.getController();
        onlineController.setViewInternetDisconnected(viewInternetDisconnected);
        onlineController.setPlaces(Places);
        onlineController.mainController(this);
        onlineController.setRoad(placeService.getRoad("primeraAvenidaNorte_Init", "septimaAvenidaNorte_End"));
        btnSearch.setDisable(false);
    
    }
    
    public void setInternetDisconnectedForce() throws IOException{
         viewStackPanel.getChildren().add(viewInternetDisconnected.load());
         InternetDisconnectedController controller = viewInternetDisconnected.getController();
         controller.setMainController(this);
         btnSearch.setDisable(true);
    
    }
    
    public void setOnlineViewReconnect() throws IOException{
        if(AccessWifi.isInternetAvailable()){
                   setOnlienViewForce();
        }
    }
    
}
