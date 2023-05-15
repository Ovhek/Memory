/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author ivan
 */
public class PruebaController implements Initializable {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML
    private HBox hbox1;
    
    @FXML
    private FlowPane flowPane;

    @FXML // fx:id="carta9"
    private ImageView carta9; // Value injected by FXMLLoader

    @FXML // fx:id="carta5"
    private ImageView carta5; // Value injected by FXMLLoader

    @FXML // fx:id="carta6"
    private ImageView carta6; // Value injected by FXMLLoader

    @FXML // fx:id="carta7"
    private ImageView carta7; // Value injected by FXMLLoader

    @FXML // fx:id="carta8"
    private ImageView carta8; // Value injected by FXMLLoader

    @FXML // fx:id="carta10"
    private ImageView carta10; // Value injected by FXMLLoader

    @FXML // fx:id="carta1"
    private ImageView carta1; // Value injected by FXMLLoader

    @FXML // fx:id="carta2"
    private ImageView carta2; // Value injected by FXMLLoader

    @FXML // fx:id="carta3"
    private ImageView carta3; // Value injected by FXMLLoader

    @FXML // fx:id="carta4"
    private ImageView carta4; // Value injected by FXMLLoader

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //initializeImageView();
    }
    
     private void initializeImageView()
    {
        for (int i=0; i<flowPane.getChildren().size();i++)
        {
            //"cast" the Node to be of type ImageView
            ImageView imageView = (ImageView) flowPane.getChildren().get(i);
            imageView.setImage(new Image("images/back_of_card.png"));
            imageView.setUserData(i);

            //register a click listener
            /*imageView.setOnMouseClicked(event -> {
                flipCard((int) imageView.getUserData());
            });*/
        }
    }

}
