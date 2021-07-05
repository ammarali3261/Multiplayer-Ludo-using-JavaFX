package ludo.client;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * This is the client class for the Ludo game. This is the class that connects
 * to the server and communicates with it.
 *
 */

public class Client extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //loads the icon image from the resources folder
        Image icon = new Image("file:./resources/logo.png");
        
        //creates a stackpane to hold all the components
        Scene scene = new Scene(new StackPane());

        //Creating an object of the client manager class
        ClientManager clientManager = new ClientManager(scene, stage);

        // Set the scene and display it
        stage.setScene(scene);
        stage.getIcons().add(icon);
        stage.show();

        //The game window closes when the user presses the close button on the window
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.exit(0);
            }
        });

    }

}
