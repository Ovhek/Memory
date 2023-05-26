/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import common.Utils;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 *
 * @author ivan
 */
public class GameController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView carta1, carta2, carta3, carta4, carta5, carta6, carta7, carta8, carta9, carta10;

    @FXML
    private Label lbAciertos;

    @FXML
    private Label lbIntentos;

    @FXML
    private Label lbTiempo;
    
    @FXML
    private Button btnExitGame;

    private int cuentaAtras;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Reiniciamos los valores
        lbIntentos.setText("0");
        lbAciertos.setText("0");
        cuentaAtras();

    }
    
    /**
     * Inicializar la cuenta atrás
     */
    private void cuentaAtras() {
        cuentaAtras = 10;
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(10), event -> {
                    lbTiempo.setText(formatTimeMillis(cuentaAtras * 1000));
                    cuentaAtras--;
                }),
                new KeyFrame(Duration.seconds(1))
        );

        timeline.setCycleCount(cuentaAtras + 1);
        timeline.setOnFinished(event -> {
            Utils.alertTime();
            try {
                App.setRoot("hallOfFame");
            } catch (IOException ex) {
                Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        timeline.play();
    }
    
    /**
     * A partir de segundos, formatear en minutos y segundos
     * @param seconds
     * @return 
     */
    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int segundosRestantes = seconds % 60;
        return String.format("%02d:%02d", minutes, segundosRestantes);
    }

    /**
     * A partir de milisegundos, formatear en minutos, segundos y milisegundos
     * @param milliseconds
     * @return 
     */
    private String formatTimeMillis(int milliseconds) {
        int minutes = (milliseconds / 1000) / 60;
        int seconds = (milliseconds / 1000) % 60;
        int millis = milliseconds % 1000;
        return String.format("%02d:%02d:%03d", minutes, seconds, millis);
    }
    
    @FXML
    void onActionExitGame(ActionEvent event) throws IOException {
        App.setRoot("main");
    }


    /*private void initializeImageView() {
        for (int i=0; i<hbox1.getChildren().size();i++) {
            //"cast" the Node to be of type ImageView
            ImageView imageView = (ImageView) hbox1.getChildren().get(i);
            imageView.setImage(new Image("images/back_of_card.png"));
            imageView.setUserData(i);

            //register a click listener
            imageView.setOnMouseClicked(event -> {
                flipCard((int) imageView.getUserData());
            });
        }
    }*/
}
