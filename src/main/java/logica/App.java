package logica;

import common.IJuego;
import common.Lookups;
import common.Utils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javax.naming.NamingException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static IJuego juegoEJB;

    private static Manager manager;
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException, NamingException {
        App.stage = stage;
        manager = Manager.getInstance();

        // Ventana Principal
        FXMLLoader fxmlPrimary;

        fxmlPrimary = loadFXML("login");
        Scene scene = new Scene(fxmlPrimary.load());

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        Utils.playMusic();
        //juegoEJB = Lookups.juegoEJBRemoteLookup();

    }

    private void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml).load());
    }

    private FXMLLoader loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getClassLoader().getResource("logica/" + fxml + ".fxml"));
        return fxmlLoader;
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() {

        System.exit(0);

    }

}
