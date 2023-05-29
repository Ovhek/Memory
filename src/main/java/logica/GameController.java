
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import common.Carta;
import common.CartaMemory;
import common.MazoDeCartas;
import common.Utils;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;
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

    @FXML
    private Button btn_salirPartida;

    private LoadFXML loadFXML = new LoadFXML();

    private int cuentaAtras;

    private Timeline timeline;
    private int numColumnas, numFilas;

    private static final int NUM_COLUMNAS = 3;
    private MazoDeCartas mazoDeCartas;
    private ArrayList<CartaMemory> listaCartas;
    private Image imagen;
    private Image imagenBack;
    private int count;
    private int index_1;
    private int aciertos, intentos;
    private ImageView backImageView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Manager.getInstance().addController(this);
            count = 0;
            confDificultad();
            index_1 = -1;
            mazoDeCartas = new MazoDeCartas();
            mazoDeCartas.mezclar();

            listaCartas = (ArrayList<CartaMemory>) mazoDeCartas.getCartas();

            imagenBack = setByteToImage(listaCartas.get(0).getBackOfCardImage());

            // Reiniciar los valores
            aciertos = 0;
            intentos = 0;

            lb_intento.setText(String.valueOf(intentos));
            lb_aciertos.setText(String.valueOf(aciertos));

            confGrid();

            cuentaAtras();
        } catch (IOException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void confGrid() {
        int numCartas = mazoDeCartas.getNumeroCartas();
        int numColumnas = calculateNumColumns(numCartas); // Calcular el número de columnas en función del número de cartas
        int numFilas = (int) Math.ceil((double) numCartas / numColumnas); // Calcular el número de filas

        int rowIndex = 0; // Índice de fila actual
        int columnIndex = 0; // Índice de columna actual

        for (CartaMemory carta : listaCartas) {
            // Crear el ImageView para la carta
            ImageView imageView = new ImageView(imagenBack);
            imageView.setFitWidth(100); // Ajusta el ancho según tus necesidades
            imageView.setPreserveRatio(true);

            // Agregar el ImageView al GridPane en la posición correspondiente
            gt_tablero.add(imageView, columnIndex, rowIndex);

            imageView.setOnMouseClicked(event -> {
                int index = listaCartas.indexOf(carta);
                try {
                    if (count == 0) {
                        backImageView = imageView;
                    }
                    flipCard(index, imageView);
                } catch (IOException ex) {
                    Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                }
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

    private void flipCard(int index, ImageView imageView) throws IOException, InterruptedException {
        // Obtener la carta correspondiente al índice
        CartaMemory carta = listaCartas.get(index);
        
        if (!carta.isGirada()) {

            if (count == 0) {
                imagen = setByteToImage(carta.getImage());
                imageView.setImage(imagen);
                carta.setGirada(true);
                count = 1;
                index_1 = index;
            } else if (count == 1) {
                imagen = setByteToImage(carta.getImage());
                imageView.setImage(imagen);
                carta.setGirada(true);
                CartaMemory carta_1 = listaCartas.get(index_1);
                intentos++;
                if (carta.isMismaCarta(carta_1)) {
                    aciertos++;
                    lb_aciertos.setText(String.valueOf(aciertos));
                    carta.setMatched(true);
                    carta_1.setMatched(true);

                } else {

                    Task<Void> task = new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            Thread.sleep(1000); // Pausa durante 1 segundo
                            return null;
                        }
                    };

                    task.setOnSucceeded(e -> {
                        carta.setGirada(false);
                        carta_1.setGirada(false);
                        imageView.setImage(imagenBack);
                        backImageView.setImage(imagenBack);
                    });

                    new Thread(task).start();

                }
                lb_intento.setText(String.valueOf(intentos));
                count = 0;
            }

        }

    }

    /**
     * Inicializar la cuenta atrás
     */
    private void cuentaAtras() {

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), event -> {
                    lb_tiempo.setText(Utils.formatTime(cuentaAtras));
                    cuentaAtras--;
                }),
                new KeyFrame(Duration.seconds(1))
        );

        timeline.setCycleCount(cuentaAtras + 1);
        timeline.setOnFinished(event -> {
            Utils.alertTime();
            loadFXML.changeScreen("logica/hallOfFame.fxml", btn_salirPartida);

        });

        timeline.play();
    }

    @FXML
    void onActionSalirPartida(ActionEvent event) {
        timeline.stop();
        loadFXML.changeScreen("logica/main.fxml", btn_salirPartida);
    }

    private Image setByteToImage(byte[] imageBytes) throws IOException {

        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);

        // Lee la imagen desde el ByteArrayInputStream
        BufferedImage bufferedImage = ImageIO.read(bis);

        // Convierte el BufferedImage a un objeto javafx.scene.image.Image
        Image image = convertToJavaFXImage(bufferedImage);

        return image;

    }

    private static Image convertToJavaFXImage(BufferedImage bufferedImage) {
        WritableImage wr = null;
        if (bufferedImage != null) {
            wr = new WritableImage(bufferedImage.getWidth(), bufferedImage.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < bufferedImage.getWidth(); x++) {
                for (int y = 0; y < bufferedImage.getHeight(); y++) {
                    pw.setArgb(x, y, bufferedImage.getRGB(x, y));
                }
            }
        }
        return wr;
    }

    private void confDificultad() {
        switch (Utils.dificultad) {
            case 0:
                cuentaAtras = 180;
                break;
            case 1:
                cuentaAtras = 120;
                break;
            case 2:
                cuentaAtras = 60;
                break;
        }
    }

    @Override
    public void close() {

    }
}
