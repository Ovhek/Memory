/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import common.Partida;
import common.Utils;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import static logica.App.juegoEJB;
import logica.utils.LoadFXML;
import presentacion.PresentationLayer;

/**
 *
 * @author ivan
 */
public class HallOfFameController extends PresentationLayer implements Initializable {

     @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuButton btnDificultad;

    @FXML
    private TableView<Partida> tvScores;
    
    @FXML
    private TableColumn<?, ?> colJugador;
    
    @FXML
    private TableColumn<?, ?> colIntentos;

    @FXML
    private TableColumn<?, ?> colTiempo;

    @FXML
    private TableColumn<?, ?> colPuntuacion;

    @FXML
    private Button btnMenuPrincipal;
    
    @FXML
    private TextArea lvLogger;

    private LoadFXML loadFXML = new LoadFXML();
    
    @FXML
    void onActionPuntuacionFacil(ActionEvent event) throws Exception {
        btnDificultad.setText("Fácil");
        tvScores.getItems().clear();
        tvScores.getItems().addAll(juegoEJB.getHallOfGame(0));
    }

    @FXML
    void onActionPuntuacionNormal(ActionEvent event) throws Exception {
        btnDificultad.setText("Normal");
        tvScores.getItems().clear();
        tvScores.getItems().addAll(juegoEJB.getHallOfGame(1));
    }

    @FXML
    void onActionPuntuacionDificil(ActionEvent event) throws Exception {
        btnDificultad.setText("Difícil");
        tvScores.getItems().clear();
        tvScores.getItems().addAll(juegoEJB.getHallOfGame(2));
    }

    @FXML
    void onActionMenuPrincipal(ActionEvent event) throws IOException {
        if (!Utils.login) {
            Utils.alertLogin();
            loadFXML.changeScreen("logica/login.fxml", btnMenuPrincipal);
        } else {
            loadFXML.changeScreen("logica/main.fxml", btnMenuPrincipal);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Manager.getInstance().addController(this);
        
        // Cargamos todas las puntuaciones del Hall Of Fame e indicamos el momento exacto de la actualización de los resultados
        try {
            tvScores.getItems().addAll(juegoEJB.getHallOfGame());
            lvLogger.appendText("Las puntuaciones están actualizadas a " + Utils.getCurrentDateTime() + "\n");
        } catch (Exception ex) {
            lvLogger.appendText("No se han podido cargar las puntuaciones.\n");
        }
    }

    @Override
    public void close() {
    }

}
