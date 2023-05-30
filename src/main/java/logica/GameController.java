
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import common.CartaMemory;
import common.MazoDeCartas;
import common.Partida;
import common.PartidaException;
import common.Utils;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import static logica.App.juegoEJB;
import static logica.App.jugadorApp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
    private Label lb_intento;

    @FXML
    private Label lb_tiempo;

    @FXML
    private Button btn_salirPartida;

    private LoadFXML loadFXML = new LoadFXML();

    private Timeline timeline;
    private int numColumnas, numFilas;

    private static final int NUM_COLUMNAS = 3;
    private MazoDeCartas mazoDeCartas;
    private ArrayList<CartaMemory> listaCartas;
    private Image imagen;
    private Image imagenBack;
    private int index_1;
    private int intentos;
    private ImageView backImageView;
    private Partida partida;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Manager.getInstance().addController(this);

            partida = new Partida(jugadorApp, Utils.dificultad);
            //Empezar partida
            juegoEJB.empezarPartida(partida);

            index_1 = -1;
            mazoDeCartas = juegoEJB.obtenerMazoMezclado();

            listaCartas = (ArrayList<CartaMemory>) mazoDeCartas.getCartas();

            imagenBack = setByteToImage(listaCartas.get(0).getBackOfCardImage());

            // Reiniciar los valores
            intentos = 0;

            lb_intento.setText(String.valueOf(intentos));

            confGrid();

            cuentaAtras();
        } catch (IOException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PartidaException ex) {
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
                    if (juegoEJB.getVoleo()) {
                        backImageView = imageView;
                    }
                    flipCard(index, imageView);
                } catch (IOException ex) {
                    Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
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

    private void flipCard(int index, ImageView imageView) throws IOException, InterruptedException, Exception {
        // Obtener la carta correspondiente al índice
        CartaMemory carta = listaCartas.get(index);

        if (!carta.isGirada()) {

            if (juegoEJB.getVoleo()) {
                imagen = setByteToImage(carta.getImage());
                imageView.setImage(imagen);
                carta.setGirada(true);
                juegoEJB.voltearCarta();
                index_1 = index;
            } else if (!juegoEJB.getVoleo()) {
                imagen = setByteToImage(carta.getImage());
                imageView.setImage(imagen);
                carta.setGirada(true);
                CartaMemory carta_1 = listaCartas.get(index_1);

                intentos = juegoEJB.sumarIntentos();
                if (!juegoEJB.cartasConciden(carta, carta_1)) {

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

                } else if (juegoEJB.comprobarVictoria()) {
                    timeline.stop();
                    juegoEJB.terminarPartida();
                    loadFXML.changeScreen("logica/hallOfFame.fxml", btn_salirPartida);
                }
                lb_intento.setText(String.valueOf(intentos));
                juegoEJB.voltearCarta();
            }

        }

    }

    /**
     * Inicializar la cuenta atrás
     */
    private void cuentaAtras() {

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    int cuentaAtras = juegoEJB.obtenerTiempoPartida();
                    System.out.println(cuentaAtras);
                    lb_tiempo.setText(Utils.formatTime(cuentaAtras));
                })
        );
        timeline.setCycleCount(juegoEJB.getTiempoMaximo());
        timeline.setOnFinished(event -> {
            Utils.alertTime();
            try {
                juegoEJB.terminarPartida();
            } catch (Exception ex) {
                Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
            }
            loadFXML.changeScreen("logica/hallOfFame.fxml", btn_salirPartida);

        });

        timeline.play();
        
    }

    @FXML
    void onActionSalirPartida(ActionEvent event) throws Exception {
        timeline.stop();
        juegoEJB.terminarPartida();
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

    @Override
    public void close() {

    }
}
