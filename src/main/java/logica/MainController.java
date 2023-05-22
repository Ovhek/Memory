/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import common.Utils;
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
        Utils.mostrarDialogoConfirmacion("¿Deseas salir del juego?");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
