/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import util.AppointmentDB;

/**
 * FXML Controller class
 *
 * @author Shannon Glass
 */
public class ReportsController {

    @FXML
    private TableView<Appointment> reportTable;
    @FXML
    private TableColumn<Integer, Appointment> appointmentIdColumn;
    @FXML
    private TableColumn<String, Customer> customerColumn;
    @FXML
    private TableColumn<String, Appointment> typeColumn;
    @FXML
    private TableColumn<String, Appointment> startColumn;
    @FXML
    private TableColumn<String, Appointment> endColumn;
    @FXML
    private ComboBox reportCombo;
    @FXML
    private Label reportTitle;

    private final ObservableList<String> reportTypes = FXCollections.observableArrayList("month", "week");
    
    Stage stage;
    Parent scene;

    // Initializes Report scene and sets the table and combo box values
    public void initialize() {
        setTableValues();
        reportCombo.setItems(reportTypes);
    }

    public void setTableValues() {
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
    }

    // When reportCombo option is selected, shows table with appointments for the next 30 or 7 days
    @FXML
    public void setReport() {
        String selectedReport = reportCombo.getSelectionModel().getSelectedItem().toString();
        switch (selectedReport) {
            case "month":
                reportTitle.setText("Appointments this Month");
                reportTable.setItems(AppointmentDB.getAppointmentsByMonth());
                break;
            case "week":
                reportTitle.setText("Appointments this Week");
                reportTable.setItems(AppointmentDB.getAppointmentsByWeek());
        }
    }

    // Returns to the main menu
    @FXML
    public void handleBack(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        scene.getStylesheets().add("/util/scheduler.css");
        stage.setScene(new Scene(scene));
        stage.show();
    }

}
