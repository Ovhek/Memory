/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import common.Utils;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

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
    private TextArea lvLogger;

    @FXML
    void onActionMenuPrincipal(ActionEvent event) throws IOException {
        App.setRoot("main");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //cargar puntuaciones de la BBDD
            lvLogger.appendText("Las puntuaciones están actualizadas a " + Utils.getCurrentDateTime() + "\n");
        } catch (Exception ex) {
            lvLogger.appendText("No se han podido cargar las puntuaciones.\n");
        }
    }

}
