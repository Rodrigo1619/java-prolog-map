/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import controllers.jxmapviewer.waypoint.EventWaypoint;
import controllers.jxmapviewer.waypoint.MyWaypoint;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.ComboBox;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.JButton;

import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;
import models.RoutingData;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import utils.AccessWifi;


/**
 * FXML Controller class
 *
 * @author Zepeda22
 */
public class OnlineController implements Initializable {
    
    private final Set<MyWaypoint> waypoints = new HashSet<>();
    private List<RoutingData> routingData = new ArrayList<>();
    private EventWaypoint event;
    private Point mousePosition;
    
    public static final  SwingNode swingNode = new SwingNode();
    
    public FXMLLoader viewInternetDisconnected  = new FXMLLoader(getClass().getResource("/views/InternetDisconnectedView.fxml"));
    private Node nodeInternetDisconnected;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private final org.jxmapviewer.JXMapViewer jXMapViewer = new org.jxmapviewer.JXMapViewer();  
    @FXML
    private StackPane panelOnline;
    @FXML
    private ComboBox<String> cmbMapType;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          
        
        ObservableList<String> MapTypes = FXCollections.observableArrayList("Open Stree","Virtual Earth","Hybrid","Satellite");

        // TODO
        panelOnline.setMaxSize(32767, 32767);
        panelOnline.setMinSize(0, 0);
        
        
         System.out.println("Se oprimio el return");
        try {
           nodeInternetDisconnected = viewInternetDisconnected.load();
           //viewInternetDisconnected = new FXMLLoader(getClass().getResource("/views/InternetDisconnectedView.fxml"));
           
            // System.out.println(nodo.visibleProperty().getValue());
            System.out.println(AccessWifi.isInternetAvailable());
            if(AccessWifi.isInternetAvailable()){
            
            //panelOnline.getChildren().get(1).setVisible(false);
           
                 panelOnline.getChildren().add(swingNode);
                createSwingContent(swingNode);
                jXMapViewer.addMouseListener(new java.awt.event.MouseAdapter() {
                     @Override
                     public void mouseReleased(java.awt.event.MouseEvent evt) {
                        jXMapViewerMouseReleased(evt);
                     }
                });
                //swingNode.toFront();
                cmbMapType.toFront();
                cmbMapType.setItems(MapTypes);
                // System.err.println(panelOnline.getParent().getBoundsInParent());
                System.out.println("Cambiando");
                

                //swingNode.toFront();
                //panelOnline.getChildren().removeAll(nodeInternetDisconnected);
                //nodeInternetDisconnected.setVisible(false);
               
                
            }else{
               // panelOnline.getChildren().clear();
                
            panelOnline.getChildren().add(nodeInternetDisconnected);
             nodeInternetDisconnected.toFront();
                
               // root = viewInternetDisconnected.load();
               // stage =(Stage)((Node) getSource);
                
            }
            /* panelOnline.addEventHandler(ScrollEvent.SCROLL, e -> {
            System.out.println(jXMapViewer.getZoom());
            
            });*/
        } catch (IOException ex) {
            Logger.getLogger(OnlineController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
             
    }    
    
    private void createSwingContent(final SwingNode swingNode) {
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
        if (SwingUtilities.isRightMouseButton(evt)) {
            mousePosition = evt.getPoint();
            System.out.println("Funca");
            //jPopupMenu1.show(jXMapViewer, evt.getX(), evt.getY());
        }
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
}
