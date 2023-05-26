/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import common.Jugador;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import static logica.App.juegoEJB;

/**
 *
 * @author ivan
 */
public class RegistroController implements Initializable {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    private TextField txtEmail;

    @FXML
    private Button btnRegistro;
    
    @FXML
    private Button btnAtras;

    @FXML
    private TextField txtUsuario;

    @FXML
    private TextArea lvLogger;

    @FXML
    void onActionRegistrar(ActionEvent event) throws IOException, Exception {
        try {
            Jugador jugador = new Jugador(txtUsuario.getText(), txtEmail.getText());
            juegoEJB.registrarUsuario(jugador);
            lvLogger.appendText("El usuario se ha registrado correctamente.\n");
            App.setRoot("login");
        } catch (Exception ex){
            lvLogger.appendText("El usuario no se ha podido registrar.\n");
        }
    }
    
    @FXML
    void onActionBack(ActionEvent event) throws IOException {
        App.setRoot("login");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
