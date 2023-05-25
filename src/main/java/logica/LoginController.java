/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import common.IJugador;
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

/**
 *
 * @author ivan
 */
public class LoginController implements Initializable {

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

    private IJugador jugadorEJB;

    @FXML
    void onActionLogin(ActionEvent event) throws IOException {
        try {
            Jugador jugador = new Jugador(txtUsuario.getText(), txtEmail.getText());
            jugadorEJB.getSesion(jugador);
            Utils.login = true;
            App.setRoot("main");
        } catch (Exception ex) {
            lvLogger.appendText("El usuario no ha podido loguearse. Revisa las credenciales.\n");
        }
    }

    @FXML
    void onActionRegistrar(ActionEvent event) throws IOException {
        App.setRoot("registro");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            jugadorEJB = Lookups.jugadorEJBRemoteLookup();
        } catch (NamingException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
