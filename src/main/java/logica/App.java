package logica;

import common.IJuego;
import common.Lookups;
import common.Utils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
<<<<<<< HEAD
    public void start(Stage stage) throws IOException {
        App.stage = stage;
        manager = Manager.getInstance();

        // Ventana Principal
        FXMLLoader fxmlPrimary;

        fxmlPrimary = loadFXML("main");
        Scene scene = new Scene(fxmlPrimary.load());

=======
    public void start(Stage stage) throws IOException, NamingException {
        scene = new Scene(loadFXML("login"));
>>>>>>> ad8e9a1d7f7c90b0ecf9e3a55dd3ca6bd3a80fa5
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        Utils.playMusic();
        juegoEJB = Lookups.juegoEJBRemoteLookup();
    }

<<<<<<< HEAD
   private void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml).load());
=======
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
>>>>>>> ad8e9a1d7f7c90b0ecf9e3a55dd3ca6bd3a80fa5
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
