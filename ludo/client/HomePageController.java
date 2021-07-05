package ludo.client;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * This is the home page for the Ludo game.
 * This page is the first page that the users see 
 * once they start open the game. It shows the status
 * of all the players along with the logo and images 
 * of the game
 * 
 */


public class HomePageController extends AnchorPane {

    //declaring global variables for the home screen
    private AnchorPane container;
    private ImageView logo;
    private ImageView dice1;
    private ImageView dice2;
    private ImageView dice3;
    private ImageView dice4;
    private ImageView dice5;
    private ImageView dice6;
    private Button greenStatus;
    private Button blueStatus;
    private Button redStatus;
    private Button yellowStatus;
    private Button statusIndicator;

    public HomePageController() {

        //initializing variables required for the home screen 
        container = new AnchorPane();

        //initializing imageviews to hold the dice and logo images
        logo = new ImageView();
        dice1 = new ImageView();
        dice2 = new ImageView();
        dice3 = new ImageView();
        dice4 = new ImageView();
        dice5 = new ImageView();
        dice6 = new ImageView();

        //creating buttons 
        greenStatus = new Button();
        blueStatus = new Button();
        redStatus = new Button();
        yellowStatus = new Button();
        statusIndicator = new Button();

        //setting size constraints
        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(607.0);
        setPrefWidth(911.0);

        container.setLayoutX(-6.0);
        container.setPrefHeight(621.0);
        container.setPrefWidth(922.0);
        container.setStyle("-fx-background-color: #2F2F2F;");

        //adjusting the position,size and image of the logo
        logo.setFitHeight(128.0);
        logo.setFitWidth(222.0);
        logo.setId("logo");
        logo.setLayoutX(340.0);
        logo.setLayoutY(136.0);
        logo.setPickOnBounds(true);
        logo.setPreserveRatio(true);
        logo.setImage(new Image(getClass().getResource("./resources/logo.png").toExternalForm()));

        //adjusting the position,size and image of dice1
        dice1.setFitHeight(41.0);
        dice1.setFitWidth(41.0);
        dice1.setId("dice1");
        dice1.setLayoutX(295.0);
        dice1.setLayoutY(244.0);
        dice1.setPickOnBounds(true);
        dice1.setPreserveRatio(true);
        dice1.setImage(new Image(getClass().getResource("./resources/dice-1.png").toExternalForm()));

        //adjusting the position,size and image of dice2
        dice2.setFitHeight(41.0);
        dice2.setFitWidth(41.0);
        dice2.setId("dice2");
        dice2.setLayoutX(355.0);
        dice2.setLayoutY(244.0);
        dice2.setPickOnBounds(true);
        dice2.setPreserveRatio(true);
        dice2.setImage(new Image(getClass().getResource("./resources/dice-2.png").toExternalForm()));

        //adjusting the position,size and image of dice3
        dice3.setFitHeight(41.0);
        dice3.setFitWidth(41.0);
        dice3.setId("dice3");
        dice3.setLayoutX(410.0);
        dice3.setLayoutY(244.0);
        dice3.setPickOnBounds(true);
        dice3.setPreserveRatio(true);
        dice3.setImage(new Image(getClass().getResource("./resources/dice-3.png").toExternalForm()));

        //adjusting the position,size and image of dice4
        dice4.setFitHeight(41.0);
        dice4.setFitWidth(41.0);
        dice4.setId("dice4");
        dice4.setLayoutX(466.0);
        dice4.setLayoutY(244.0);
        dice4.setPickOnBounds(true);
        dice4.setPreserveRatio(true);
        dice4.setImage(new Image(getClass().getResource("./resources/dice-4.png").toExternalForm()));

        //adjusting the position,size and image of dice5
        dice5.setFitHeight(41.0);
        dice5.setFitWidth(41.0);
        dice5.setId("dice5");
        dice5.setLayoutX(520.0);
        dice5.setLayoutY(244.0);
        dice5.setPickOnBounds(true);
        dice5.setPreserveRatio(true);
        dice5.setImage(new Image(getClass().getResource("./resources/dice-5.png").toExternalForm()));

        //adjusting the position,size and image of dice6
        dice6.setFitHeight(41.0);
        dice6.setFitWidth(41.0);
        dice6.setId("dice6");
        dice6.setLayoutX(576.0);
        dice6.setLayoutY(244.0);
        dice6.setPickOnBounds(true);
        dice6.setPreserveRatio(true);
        dice6.setImage(new Image(getClass().getResource("./resources/dice-6.png").toExternalForm()));

        //adjusting the position,size, and initial text of the button along with some other settings
        greenStatus.setId("greenStatus");
        greenStatus.setLayoutX(111.0);
        greenStatus.setLayoutY(341.0);
        greenStatus.setMnemonicParsing(false);
        greenStatus.setPrefHeight(42.0);
        greenStatus.setPrefWidth(160.0);
        greenStatus.setStyle("-fx-background-color: #0E9F37; -fx-text-fill: #FFFFFF; -fx-font-weight: bold;");
        greenStatus.setText("WAITING...");

        //adjusting the position,size, and initial text of the button along with some other settings
        blueStatus.setId("redStatus");
        blueStatus.setLayoutX(293.0);
        blueStatus.setLayoutY(342.0);
        blueStatus.setMnemonicParsing(false);
        blueStatus.setPrefHeight(41.0);
        blueStatus.setPrefWidth(160.0);
        blueStatus.setStyle("-fx-background-color: #188FD1; -fx-text-fill: #FFFFFF; -fx-font-weight: bold;");
        blueStatus.setText("WAITING...");

        //adjusting the position,size, and initial text of the button along with some other settings
        redStatus.setId("blueStatus");
        redStatus.setLayoutX(473.0);
        redStatus.setLayoutY(342.0);
        redStatus.setMnemonicParsing(false);
        redStatus.setPrefHeight(41.0);
        redStatus.setPrefWidth(160.0);
        redStatus.setStyle("-fx-background-color: #DA3F0F; -fx-text-fill: #FFFFFF; -fx-font-weight: bold;");
        redStatus.setText("WAITING...");

        //adjusting the position,size, and initial text of the button along with some other settings
        yellowStatus.setId("yellowStatus");
        yellowStatus.setLayoutX(652.0);
        yellowStatus.setLayoutY(342.0);
        yellowStatus.setMnemonicParsing(false);
        yellowStatus.setPrefHeight(41.0);
        yellowStatus.setPrefWidth(160.0);
        yellowStatus.setStyle("-fx-background-color: #EBB018; -fx-text-fill: #FFFFFF; -fx-font-weight: bold;");
        yellowStatus.setText("WAITING...");

        //adjusting the position,size, and initial text of the button along with some other settings
        statusIndicator.setId("statusButton");
        statusIndicator.setLayoutX(366.0);
        statusIndicator.setLayoutY(511.0);
        statusIndicator.setMnemonicParsing(false);
        statusIndicator.setPrefHeight(42.0);
        statusIndicator.setPrefWidth(190.0);
        statusIndicator.setStyle("-fx-background-color: #EC4B19; -fx-text-fill: #FFFFFF; -fx-font-weight: bold;");
        statusIndicator.setText("AWAITING PLAYERS");

        container.getChildren().addAll(logo, dice1, dice2, dice3, dice4, dice5, dice6,
                greenStatus, blueStatus, redStatus, yellowStatus, statusIndicator);
        getChildren().add(container);

    }

    public void setUserActiveStatus(String color) {
        Platform.runLater(() -> {
            switch (color.toUpperCase()) {
                case "RED":
                    redStatus.setText("READY");
                    break;
                case "YELLOW":
                    yellowStatus.setText("READY");
                    break;
                case "BLUE":
                    blueStatus.setText("READY");
                    break;
                case "GREEN":
                    greenStatus.setText("READY");
            }
        });
    }
}
