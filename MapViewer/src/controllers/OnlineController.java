/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import com.graphhopper.util.PointList;
import com.graphhopper.util.shapes.GHPoint;
import controllers.jxmapviewer.waypoint.EventWaypoint;
import controllers.jxmapviewer.waypoint.MyWaypoint;
//import controllers.pointlist.PointListCustom;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.JButton;
//import com.graphhopper.util.PointList;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

import models.Coordenadas;
import models.Place;
import models.RoutingData;
import models.StreetPoint;
import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import repositories.PlaceRepositoryImpl;
import services.PlaceService;
import services.PlaceServiceImpl;
import utils.AccessWifi;


/**
 * FXML Controller class
 *
 * @author Zepeda22
 */
public class OnlineController implements Initializable {
    
    private final Set<MyWaypoint> waypoints = new HashSet<>();
    private List<RoutingData> routingData;
    private EventWaypoint event;
    private Point mousePosition;
    private List<RoutingData> dataTemp = new ArrayList<>();
    //private List<PointList> pointListTemp = new ArrayList<>();
    private List<GHPoint> allPoints = Arrays.asList(new GHPoint(49.6724, 11.3494), new GHPoint(49.6550, 11.4180));
    //private PointListCustom pointListTemp = new PointList();
    private boolean toogleSidePanelVisible = true;
    public    SwingNode swingNode = new SwingNode();
    private MainController mainController = new MainController();
    public FXMLLoader viewInternetDisconnected;  
            //= new FXMLLoader(getClass().getResource("/views/InternetDisconnectedView.fxml"));
    //Controller 
    //private FXMLLoader viewMain = new FXMLLoader(getClass().getResource("/views/MainView.fxml"));
   // private MainController mainController = new MainController();
    public  List<String> Places = new ArrayList<String>();

    
    //private final org.jxmapviewer.JXMapViewer jXMapViewer = new org.jxmapviewer.JXMapViewer();  
    private final controllers.jxmapviewer.JXMapViewerCustom jXMapViewer = new controllers.jxmapviewer.JXMapViewerCustom();
    @FXML
    public StackPane panelOnline;
    @FXML
    private ComboBox<String> cmbMapType;
    @FXML
    private ListView<String> listviewPlaces;
    @FXML
    private Button btnSidePanel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<String> MapTypes = FXCollections.observableArrayList("Open Stree","Virtual Earth","Hybrid","Satellite");
        
        // TODO
        panelOnline.setMaxSize(32767, 32767);
        panelOnline.setMinSize(0, 0);
        

                panelOnline.getChildren().add(swingNode);
                createSwingContent(swingNode);
                
                jXMapViewer.addMouseListener(new java.awt.event.MouseAdapter() {
                     @Override
                     public void mouseReleased(java.awt.event.MouseEvent evt) {
                        jXMapViewerMouseReleased(evt);
                     }
                     
                     @Override
                     public void mousePressed(java.awt.event.MouseEvent evt) {
                        jXMapViewerMousePressed(evt);
                     }
                });
                

                
                btnSidePanel.toFront();
                cmbMapType.toFront();
                cmbMapType.setItems(MapTypes);
                listviewPlaces.toFront();
                listviewPlaces.setMaxSize(300, 400);
                
 
             
    }    
    
    public void createSwingContent( SwingNode swingNode) {
       SwingUtilities.invokeLater(() -> {
           
       
          // jXMapViewer = new org.jxmapviewer.JXMapViewer();
            jXMapViewer.setMinimumSize(new Dimension(100, 100));
            jXMapViewer.setMaximumSize(new Dimension(32767, 32767));
            
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        jXMapViewer.setTileFactory(tileFactory);
        GeoPosition geo = new GeoPosition(13.6765, -89.2885);
        jXMapViewer.setAddressLocation(geo);
        jXMapViewer.setZoom(4);
        
        
        //  Create event mouse move
        MouseInputListener mm = new PanMouseInputListener(jXMapViewer);
        jXMapViewer.addMouseListener(mm);
        jXMapViewer.addMouseMotionListener(mm);
        jXMapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(jXMapViewer));
         

//
        swingNode.setContent(jXMapViewer);
        });
            

    }

    @FXML
    private void handleMapTypeAction(ActionEvent event) {
        
        TileFactoryInfo info;
        int index = cmbMapType.getSelectionModel().getSelectedIndex();
        if (index == 0) {
            info = new OSMTileFactoryInfo();
        } else if (index == 1) {
            info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
        } else if (index == 2) {
            info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID);
        } else {
            info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.SATELLITE);
        }
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        jXMapViewer.setTileFactory(tileFactory);
        jXMapViewer.setZoom(4);
    }
    
    private void jXMapViewerMouseReleased(java.awt.event.MouseEvent evt) {                                          
        if (SwingUtilities.isLeftMouseButton(evt)) {
            mousePosition = evt.getPoint();
            System.out.println("Funca");
            //jPopupMenu1.show(jXMapViewer, evt.getX(), evt.getY());
        }
       // System.out.println("Evento del id "+evt.getID());
        //System.out.print("Boton  "+evt.getButton());
       // System.out.print(evt.getID() == MouseEvent.MOUSE_CLICKED );
        if (evt.getID() == MouseEvent.MOUSE_CLICKED && evt.getButton() == 1) {
            mousePosition = evt.getPoint();
           // System.out.println("Funca");
            //System.out.println(mousePosition.x);
            //System.out.println(jXMapViewer.getWidth()-20);
            float temp = jXMapViewer.getWidth()-20;
            System.out.println(mousePosition.x);
            float set = temp - mousePosition.x;
            if(mousePosition.x< temp && mousePosition.x>20 ){
                listviewPlaces.setMaxWidth(set);
            }
            
            
             //listviewPlaces.setMaxSize(100+50, 100+50);
            System.out.println(listviewPlaces.getLayoutBounds());
            
            //jPopupMenu1.show(jXMapViewer, evt.getX(), evt.getY());
        }
    }
    
    private void jXMapViewerMousePressed(java.awt.event.MouseEvent evt) {   
        if (evt.getID() == MouseEvent.MOUSE_CLICKED && evt.getButton() == 1) {
            mousePosition = evt.getPoint();
            System.out.println(evt.getX());
           // System.out.println("Funca");
            //System.out.println(mousePosition.x);
            //System.out.println(jXMapViewer.getWidth()-20);
            float temp = jXMapViewer.getWidth()-20;
            System.out.println(mousePosition.x);
            float set = temp - mousePosition.x;
            if(mousePosition.x< temp && mousePosition.x>20 ){
                listviewPlaces.setMaxWidth(set);
            }
            
            
             //listviewPlaces.setMaxSize(100+50, 100+50);
            System.out.println(listviewPlaces.getLayoutBounds());
            
            //jPopupMenu1.show(jXMapViewer, evt.getX(), evt.getY());
        }
    } 
    
        private void jXMapViewerMouseDragged(java.awt.event.MouseEvent evt) {   
       
        /*if (evt.getButton() == 1) {
            mousePosition = evt.getPoint();
            System.out.println(evt.getX());
           // System.out.println("Funca");
            //System.out.println(mousePosition.x);
            //System.out.println(jXMapViewer.getWidth()-20);
            float temp = jXMapViewer.getWidth()-20;
            System.out.println(mousePosition.x);
            float set = temp - mousePosition.x;
            if(mousePosition.x< temp && mousePosition.x>20 ){
                listviewPlaces.setMaxWidth(set);
            }
            
            
             //listviewPlaces.setMaxSize(100+50, 100+50);
            System.out.println(listviewPlaces.getLayoutBounds());
            
            //jPopupMenu1.show(jXMapViewer, evt.getX(), evt.getY());
        }*/
    } 
    
    public boolean reconnect() throws IOException{
         //Node wifiout = panelOnline.getChildren().get(1);
        if(AccessWifi.isInternetAvailable()){
            /*System.out.println("Cambiando el nodo a falso");
            nodeInternetDisconnected.setVisible(false);
             swingNode.setVisible(true);
                cmbMapType.setVisible(true);
                //swingNode.toFront();
            
               
                */
             // createSwingContent(swingNode);
            //panelOnline.getChildren().add(swingNode);
            //boolean a = swingNode == null ? true : false;
          
                 panelOnline.getChildren().add(swingNode);
            System.out.println(panelOnline.getChildren().size());
           
            for ( Node nodohijo :  panelOnline.getChildren()){                  
             System.out.println ("Nodo: " + nodohijo.getId());                      
    } 
             
            //System.out.print(panelOnline);
            return true;
         }else{
                 return false;
                  //nodeInternetDisconnected.setVisible(true);
        }
    
    }
    
    private PointList prologToRoutingData(StreetPoint puntos){
       
        PointList pointList = new PointList();
        
        /*for (StreetPoint place: puntos){
         pointList.add(place.getX(), place.getY());
         
        }*/
        
        pointList.add(puntos.getX(), puntos.getY());
        
        return pointList;
    }
    
   /* public void setMainViewController(MainController mainController) {
        this.mainController = mainController ;
    }*/
    
    public void setPlaces(List<String> lista) {
        //System.out.println("Desde la funcion " + lista);
         listviewPlaces.getItems().addAll( lista);
        this.Places = lista ;
    }

    @FXML
    private void expandir(javafx.scene.input.MouseEvent event) {

        /*System.out.print("X:  "+event.getX());
        System.out.println(" Y: "+event.getY());
        System.out.print("Escena Y: "+event.getSceneX());
        System.out.println(" Escena Y: "+event.getSceneY());
        System.out.println(" Layout : "+ listviewPlaces.getLayoutBounds());
        //listviewPlaces.resize(100, 100);
        panelOnline.addEventHandler(MouseEvent.MOUSE_CLICKED, eh);
        listviewPlaces.setMaxSize(100+50, 100+50);*/
        /* panelOnline.addEventFilter(MouseEvent.MOUSE_MOVED, e -> {
                line.setEndX(e.getSceneX());
                line.setEndY(e.getSceneY());
            });*/
       // panelOnline.addEventFilter(MouseEvent.MOUSE_CLICKED, e->{System.out.print("test");});
        
       // System.out.println(event.getX());
       // System.out.println(event.getX());
    }

    @FXML
    private void mover(javafx.scene.input.MouseEvent event) {
        
        if (event.getButton() == MouseButton.PRIMARY) {
            double mouseX;
            if (toogleSidePanelVisible){
                 mouseX = event.getSceneX()-281;
            }else{
                 mouseX = event.getSceneX();
            }
            
            double mouseY = jXMapViewer.getHeight() -event.getSceneY();
            System.out.println("Y: "+mouseY);
            System.out.println("X: "+mouseX);
            //double sobra = mouseX-jXMapViewer.getWidth();
            //System.out.println(mouseX);
            //System.out.println(evt.getX());
           // System.out.println("Funca");
            //System.out.println(mousePosition.x);
            //System.out.println(jXMapViewer.getWidth()-20);
            float temp = (float) (jXMapViewer.getWidth()-20);
            //System.out.println(mousePosition.x);
            float set = (float) (temp-mouseX);
            //System.out.println(set);
            if(mouseX< temp && mouseX>20 ){
                listviewPlaces.setMaxWidth(set);
            }
            if(mouseY< (jXMapViewer.getHeight()-86) && mouseY>20){
                listviewPlaces.setMaxHeight(mouseY);
            }
            
            
             //listviewPlaces.setMaxSize(100+50, 100+50);
            //System.out.println(listviewPlaces.getLayoutBounds());
            
            //jPopupMenu1.show(jXMapViewer, evt.getX(), evt.getY());
        }
    }

    @FXML
    private void handleSidePanelAction(ActionEvent event) throws IOException {
       /* FXMLLoader viewMain = new FXMLLoader(getClass().getResource("/views/MainView.fxml"));
        viewMain.load();
            MainController esoera = viewMain.getController();
            esoera.toogleSidePanel();*/
       toogleSidePanelVisible = !toogleSidePanelVisible;
       mainController.toogleSidePanel(toogleSidePanelVisible);
    }
    
    public void mainController(MainController controller){
     this.mainController = controller;
    }
    
    public void setRoad(List<StreetPoint> pointsStreet ){
        System.out.println("Tamaño:" + pointsStreet.size());
        routingData = new ArrayList<>();
         this.listviewPlaces.getItems().clear();
           this.listviewPlaces.getItems().add("CAMINO QUE SIGUIÓ: ");
      for (StreetPoint street : pointsStreet){
          
          this.listviewPlaces.getItems().add(street.getName());
          routingData.add(new RoutingData(0, street.getName(), prologToRoutingData(street)));
      }
      jXMapViewer.setRoutingData(routingData);
    
    }
    
    public void setSwingNode(SwingNode node){
     this.swingNode = node;
     panelOnline.getChildren().add(swingNode);
     createSwingContent(swingNode);
     
    }
    
    public void setViewInternetDisconnected(FXMLLoader loader){
     this.viewInternetDisconnected = loader;
    }

}
