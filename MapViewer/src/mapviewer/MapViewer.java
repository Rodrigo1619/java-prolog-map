/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mapviewer;

import controllers.SplashController;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Zepeda22
 */
public class MapViewer extends Application {
    private Scene scene ;
    public Timer timer; 
    public TimerTask tarea;
    @Override
    public void start(Stage stage) throws Exception {
        timer = new Timer();
        
         tarea = new TimerTask() {
            @Override
            public void run() {
                try {
                    Parent temp = FXMLLoader.load(getClass().getResource("/views/MainView.fxml"));
                    scene.setRoot(temp);
                    timer.cancel();
                    timer.purge();
                } catch (IOException ex) {
                    Logger.getLogger(MapViewer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        timer.schedule(tarea, 1000*10);
        //timer.cancel();
        //System.out.println("Fue purgado " + timer.purge());

        FXMLLoader viewSplash = new FXMLLoader(getClass().getResource("/views/SplashView.fxml"));
        Parent root = (Parent) viewSplash.load();
        SplashController splashController =  viewSplash.getController();
        splashController.setMainClass(this);
         scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        
        System.out.println("Se ha seteado la tarea");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    

    
}
