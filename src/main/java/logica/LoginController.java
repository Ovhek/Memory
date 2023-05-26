/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import common.IJuego;
import common.Jugador;
import common.Lookups;
import common.Utils;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.naming.NamingException;
import static logica.App.juegoEJB;
import logica.utils.LoadFXML;
import presentacion.PresentationLayer;

/**
 *
 * @author ivan
 */
public class LoginController extends PresentationLayer implements Initializable {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnLogin"
    private Button btnLogin; // Value injected by FXMLLoader

    @FXML // fx:id="txtEmail"
    private TextField txtEmail; // Value injected by FXMLLoader

    @FXML // fx:id="btnRegistro"
    private Button btnRegistro; // Value injected by FXMLLoader

    @FXML // fx:id="txtUsuario"
    private TextField txtUsuario; // Value injected by FXMLLoader

    @FXML
    private TextArea lvLogger;

    private LoadFXML loadFXML = new LoadFXML();

    @FXML
    void onActionLogin(ActionEvent event) throws IOException {
        try {
            Jugador jugador = new Jugador(txtUsuario.getText(), txtEmail.getText());
            juegoEJB.getSesion(jugador);
            Utils.login = true;
            loadFXML.changeScreen("logica/main.fxml", btnLogin);
        } catch (Exception ex) {
            lvLogger.appendText("El usuario no ha podido loguearse. Revisa las credenciales.\n");
        }
    }

    @FXML
    void onActionRegistrar(ActionEvent event) throws IOException {
        loadFXML.changeScreen("registro", btnLogin);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Manager.getInstance().addController(this);
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
