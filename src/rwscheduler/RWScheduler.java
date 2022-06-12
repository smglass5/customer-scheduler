/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rwscheduler;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import util.DBConnection;

/**
 *
 * @author Shannon Glass
 */
public class RWScheduler extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
        root = loader.load();           
        Scene scene = new Scene(root);   
        scene.getStylesheets().add("/util/scheduler.css");
        stage.setScene(scene);      
        stage.getIcons().add(new Image("/rwscheduler/Icon.png"));
        stage.show();
    }

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    }

}
