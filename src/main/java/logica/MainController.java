/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

/**
 *
 * @author ivan
 */
public class MainController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnHallOfFame;

    @FXML
    private Button btnNewGame;

    @FXML
    private Button btnExit;

    @FXML
    void onActionNewGame(ActionEvent event) throws IOException {
        App.setRoot("game");
    }

    @FXML
    void onActionHallOfFame(ActionEvent event) throws IOException {
        App.setRoot("hallOfFame");
    }

    @FXML
    void onActionExit(ActionEvent event) {
        mostrarDialogoConfirmacion("¿Deseas salir del juego?");
    }
    
    // Alerta de confirmación al salir de la app
    public void mostrarDialogoConfirmacion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Diálogo de confirmación...");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.isPresent()) {
            if (resultado.get() == ButtonType.OK) {
                Platform.exit();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
