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

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<?> lvScores;

    @FXML
    private Button btnMenuPrincipal;

    @FXML
    void onActionMenuPrincipal(ActionEvent event) throws IOException {
        App.setRoot("main");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
