
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.User;
import util.AppointmentDB;
import util.DBConnection;

/**
 * FXML Controller class
 *
 * @author Shannon Glass
 */
public class LoginController implements Initializable {

    @FXML
    private TextField passwordField;
    @FXML
    private TextField userNameField;
    
    public static User loggedIn = new User();
    Stage stage;
    Parent scene;
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    Alert alert;
 
    public LoginController() {
        conn = DBConnection.getConnection();
    }

     // Initializes Log-in screen  
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
    }
    
     // Checks if the entered username and password exists and is valid   
    @FXML
    public void handleLogin(ActionEvent event) throws SQLException, IOException {
        String userName = userNameField.getText();
        String password = passwordField.getText();
        
        String selectStatement = "SELECT * FROM user WHERE userName = ? AND password = ?";
        ps = conn.prepareStatement(selectStatement);
        ps.setString(1, userName);
        ps.setString(2, password);
        rs = ps.executeQuery();
        if (rs.next()) {
            appointmentAlert();
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            scene.getStylesheets().add("/util/scheduler.css");          
            stage.setScene(new Scene(scene));
           
            stage.show();
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert!");
            alert.setContentText("Invalid User Name or Password!");
            alert.showAndWait();
        }
    }

    // Alerts user when an upcoming appointment is within 15 minutes of log-in
    private void appointmentAlert() {
        try {
            ObservableList<Appointment> alerts = AppointmentDB.getAlertAppointment();
            if (alerts != null) {

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment Reminder");
                alert.setHeaderText("Upcoming Appointment!");
                alert.setContentText("There is an upcoming appointment within the next 15 minutes.");
                alert.showAndWait();
            }
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    // Cancels log-in and exits program
    @FXML
    public void handleExit(ActionEvent event) {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert!");
        alert.setHeaderText("Confirm");
        alert.setContentText("Are you sure you want to cancel logging in and exit program?");
        alert.showAndWait().ifPresent((response -> { //Lambda expression to handle alert when cancelling log-in, exits program efficiently 
            if (response == ButtonType.OK) {
                System.exit(0);
            }
        }));
    }
}
