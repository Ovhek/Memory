/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import common.Carta;
import common.MazoDeCartas;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import logica.utils.LoadFXML;
import presentacion.PresentationLayer;

/**
 *
 * @author ivan
 */
public class GameController extends PresentationLayer implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private GridPane gt_tablero;

    @FXML
    private URL location;

    @FXML
    private Label lb_aciertos;

    @FXML
    private Label lb_intento;

    @FXML
    private Label lb_tiempo;

    private LoadFXML loadFXML = new LoadFXML();

    private static int cuentaAtras = 300;
    private int numColumnas, numFilas;
    private ArrayList<Integer> mazo = new ArrayList<>();
    private static final int NUM_COLUMNAS = 3;
    private MazoDeCartas mazoDeCartas;
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mazoDeCartas = new MazoDeCartas();
        mazoDeCartas.mezclar();
        
        Manager.getInstance().addController(this);

        // Reiniciar los valores
        lb_intento.setText("0");
        lb_aciertos.setText("0");
        for (int i = 0; i < 26; i++) {
            mazo.add(i);
        }

        confGrid();

        cuentaAtras();
    }

    private void confGrid() {
        int numCartas = mazo.size();
        int numColumnas = calculateNumColumns(numCartas); // Calcular el número de columnas en función del número de cartas
        int numFilas = (int) Math.ceil((double) numCartas / numColumnas); // Calcular el número de filas

        int rowIndex = 0; // Índice de fila actual
        int columnIndex = 0; // Índice de columna actual

        for (Integer carta : mazo) {
            // Crear el ImageView para la carta
            ImageView imageView = new ImageView(new Image("/images/back_of_card.png"));
            imageView.setFitWidth(100); // Ajusta el ancho según tus necesidades
            imageView.setPreserveRatio(true);

            // Agregar el ImageView al GridPane en la posición correspondiente
            gt_tablero.add(imageView, columnIndex, rowIndex);

            imageView.setOnMouseClicked(event -> {
                int index = mazo.indexOf(carta);
                flipCard(index, imageView);
            });

            // Actualizar los índices de fila y columna
            columnIndex++;
            if (columnIndex == numColumnas) {
                columnIndex = 0; // Reiniciar la columna
                rowIndex++; // Avanzar a la siguiente fila
            }
        }

        // Ajustar las restricciones de columnas y filas del GridPane
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHgrow(Priority.ALWAYS);
        columnConstraints.setFillWidth(true);
        gt_tablero.getColumnConstraints().addAll(Collections.nCopies(numColumnas, columnConstraints));

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.ALWAYS);
        rowConstraints.setFillHeight(true);
        gt_tablero.getRowConstraints().addAll(Collections.nCopies(numFilas, rowConstraints));

        // Ajustar los márgenes del GridPane
        Insets gridPaneInsets = new Insets(10);
        GridPane.setMargin(gt_tablero, gridPaneInsets);
    }

    private int calculateNumColumns(int numCartas) {
        int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
        int columnWidth = 100; // Ancho de cada carta en píxeles (ajustar según tus necesidades)
        int numColumnas = screenWidth / columnWidth;
        return Math.min(numColumnas, numCartas);
    }

    private void flipCard(int index, ImageView imageView) {
        // Obtener la carta correspondiente al índice

        // Obtener la imagen correspondiente a la carta volteada
        Image frontImage = new Image("/images/10_of_clubs.png");

        // Girar la carta mostrando la imagen frontal
        imageView.setImage(frontImage);

    }

    // Aquí debes implementar la lógica para obtener la imagen correspondiente
    // a la carta específica. Puedes usar un mapa, una lista, o cualquier otra
    // estructura de datos que asocie cada carta con su imagen frontal.
    /**
     * Inicializar la cuenta atrás
     */
    private void cuentaAtras() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(10), event -> {
                    lb_tiempo.setText(formatTimeMillis(cuentaAtras * 1000));
                    cuentaAtras--;
                }),
                new KeyFrame(Duration.seconds(1))
        );

        timeline.setCycleCount(cuentaAtras + 1);
        timeline.setOnFinished(event -> {
            lb_tiempo.setText("¡Tiempo finalizado!");
            // Añadir el código para ejecutar acciones al finalizar la cuenta atrás
        });

        timeline.play();
    }

    /**
     * A partir de segundos, formatear en minutos y segundos
     *
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
     *
     * @param milliseconds
     * @return
     */
    private String formatTimeMillis(int milliseconds) {
        int minutes = (milliseconds / 1000) / 60;
        int seconds = (milliseconds / 1000) % 60;
        int millis = milliseconds % 1000;
        return String.format("%02d:%02d:%03d", minutes, seconds, millis);
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
    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
