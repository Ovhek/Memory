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
        if (!Utils.login) {
            Utils.alertLogin();
            App.setRoot("login");
        } else {
            App.setRoot("game");
        }
    }

    @FXML
    void onActionHallOfFame(ActionEvent event) throws IOException {
        if (!Utils.login) {
            Utils.alertLogin();
            App.setRoot("login");
        } else {
            App.setRoot("hallOfFame");
        }
    }

    @FXML
    void onActionExit(ActionEvent event) throws IOException {
        if (!Utils.login) {
            Utils.alertLogin();
            App.setRoot("login");
        } else {
            Utils.alertExit();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
