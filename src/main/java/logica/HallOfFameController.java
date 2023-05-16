/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

/**
 *
 * @author ivan
 */
public class HallOfFameController implements Initializable {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="lvScore"
    private ListView<?> lvScore; // Value injected by FXMLLoader

    @FXML // fx:id="btnMenuPrincipal"
    private Button btnMenuPrincipal; // Value injected by FXMLLoader

    @FXML
    void onActionMenuPrincipal(ActionEvent event) throws IOException {
        App.setRoot("main");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
