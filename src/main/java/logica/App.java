package logica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    private static Manager manager;
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        App.stage = stage;
        manager = Manager.getInstance();

        // Ventana Principal
        FXMLLoader fxmlPrimary;

        fxmlPrimary = loadFXML("main");
        Scene scene = new Scene(fxmlPrimary.load());

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
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
