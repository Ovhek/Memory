/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import common.Jugador;
import common.Utils;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import main.JugadorEJB;

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
    private TextField txtEmail; // Value injected by FXMLLoader

    @FXML
    private Button btnRegistro; // Value injected by FXMLLoader

    @FXML
    private TextField txtUsuario; // Value injected by FXMLLoader

    @FXML
    private TextArea lvLogger;

    private JugadorEJB jugadorEJB;
    @PersistenceContext(unitName = "Exemple1PersistenceUnit")
    private EntityManager em;
    
    @Inject
    private UserTransaction userTransaction;

    @FXML
    void onActionRegistrar(ActionEvent event) throws IOException, Exception {
        try {
            Jugador jugador = new Jugador(txtUsuario.getText(), txtEmail.getText());
            jugadorEJB.registrarUsuario(jugador);
            Utils.persisteixAmbTransaccio(jugador, userTransaction, em, Logger.global);
            lvLogger.appendText("El usuario se ha registrado correctamente.\n");
            App.setRoot("main");
        } catch (Exception ex){
            lvLogger.appendText("El usuario no se ha podido registrar.\n");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
