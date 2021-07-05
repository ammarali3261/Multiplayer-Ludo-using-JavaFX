package ludo.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class LudoController extends AnchorPane {

    //Boolean to indicate whether its my turn
    private boolean myTurn = false;

    //integer to store the dice roll
    private int dice;

    //Declaring global variables that will be used in the class
    private ImageView imageView;
    private Button gameBtn;
    private Circle redToken1;
    private Circle redToken2;
    private Circle redToken3;
    private Circle redToken4;
    private Circle blueToken1;
    private Circle blueToken2;
    private Circle blueToken3;
    private Circle blueToken4;
    private Circle greenToken1;
    private Circle greenToken2;
    private Circle greenToken3;
    private Circle greenToken4;
    private Circle yellowToken1;
    private Circle yellowToken2;
    private Circle yellowToken3;
    private Circle yellowToken4;
    private Ellipse turnIndicator;
    private DropShadow dropShadow;
    private ImageView dice1;
    private ImageView dice2;
    private ImageView dice3;
    private ImageView dice4;
    private ImageView dice5;
    private ImageView dice6;

//    private Coordinates[] fields;
    private Coordinates[] indicator;

    //Constructor to initialize variable declared above
    public LudoController() {
        imageView = new ImageView();
        gameBtn = new Button();
        redToken1 = new Circle();
        redToken2 = new Circle();
        redToken3 = new Circle();
        redToken4 = new Circle();
        blueToken1 = new Circle();
        blueToken2 = new Circle();
        blueToken3 = new Circle();
        blueToken4 = new Circle();
        greenToken1 = new Circle();
        greenToken2 = new Circle();
        greenToken3 = new Circle();
        greenToken4 = new Circle();
        yellowToken1 = new Circle();
        yellowToken2 = new Circle();
        yellowToken3 = new Circle();
        yellowToken4 = new Circle();
        turnIndicator = new Ellipse();
        dropShadow = new DropShadow();
        dice1 = new ImageView();
        dice2 = new ImageView();
        dice3 = new ImageView();
        dice4 = new ImageView();
        dice5 = new ImageView();
        dice6 = new ImageView();

        //setting custom size and applying CSS
        setPrefHeight(630.0);
        setPrefWidth(981.0);
        setStyle("-fx-background-color: #2F2F2F;");
        
        //setting up the background for the game screen
        imageView.setDisable(true);
        imageView.setFitHeight(633.0);
        imageView.setFitWidth(630.0);
        imageView.setLayoutX(-1.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResource("./resources/ludo.jpg").toExternalForm()));
        imageView.setCursor(Cursor.NONE);

        //Setting location, CSS, and size to the roll dice button
        gameBtn.setLayoutX(701.0);
        gameBtn.setLayoutY(532.0);
        gameBtn.setMnemonicParsing(false);
        gameBtn.setPrefHeight(49.0);
        gameBtn.setPrefWidth(222.0);
        gameBtn.setStyle("-fx-background-color: #EC4B19; -fx-text-fill: #FFFFFF; -fx-font-weight: bold;");
        gameBtn.setText("AWAITING TURN...");
        gameBtn.setFont(new Font(18.0));

        //setting red color
        redToken1.setFill(javafx.scene.paint.Color.valueOf("#ff1b00"));
        //setting the location of the token
        redToken1.setLayoutX(125.0);
        redToken1.setLayoutY(84.0);
        redToken1.setRadius(17.0);
        redToken1.setStroke(javafx.scene.paint.Color.BLACK);
        redToken1.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

        //setting red color
        redToken2.setFill(javafx.scene.paint.Color.valueOf("#ff1b00"));
        //setting the location of the token
        redToken2.setLayoutX(83.0);
        redToken2.setLayoutY(126.0);
        redToken2.setRadius(17.0);
        redToken2.setStroke(javafx.scene.paint.Color.BLACK);
        redToken2.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

        //setting red color
        redToken3.setFill(javafx.scene.paint.Color.valueOf("#ff1b00"));
        //setting the location of the token
        redToken3.setLayoutX(167.0);
        redToken3.setLayoutY(126.0);
        redToken3.setRadius(17.0);
        redToken3.setStroke(javafx.scene.paint.Color.BLACK);
        redToken3.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

        //setting red color
        redToken4.setFill(javafx.scene.paint.Color.valueOf("#ff1b00"));
        //setting the location of the token
        redToken4.setLayoutX(125.0);
        redToken4.setLayoutY(168.0);
        redToken4.setRadius(17.0);
        redToken4.setStroke(javafx.scene.paint.Color.BLACK);
        redToken4.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

        //setting blue color
        blueToken1.setFill(javafx.scene.paint.Color.valueOf("#00aaff"));
        //setting the location of the token
        blueToken1.setLayoutX(503.0);
        blueToken1.setLayoutY(84.0);
        blueToken1.setRadius(17.0);
        blueToken1.setStroke(javafx.scene.paint.Color.BLACK);
        blueToken1.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

        //setting blue color
        blueToken2.setFill(javafx.scene.paint.Color.valueOf("#00aaff"));
        //setting the location of the token
        blueToken2.setLayoutX(461.0);
        blueToken2.setLayoutY(126.0);
        blueToken2.setRadius(17.0);
        blueToken2.setStroke(javafx.scene.paint.Color.BLACK);
        blueToken2.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

        //setting blue color
        blueToken3.setFill(javafx.scene.paint.Color.valueOf("#00aaff"));
        //setting the location of the token
        blueToken3.setLayoutX(544.0);
        blueToken3.setLayoutY(126.0);
        blueToken3.setRadius(17.0);
        blueToken3.setStroke(javafx.scene.paint.Color.BLACK);
        blueToken3.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

        //setting blue color
        blueToken4.setFill(javafx.scene.paint.Color.valueOf("#00aaff"));
        //setting the location of the token
        blueToken4.setLayoutX(503.0);
        blueToken4.setLayoutY(168.0);
        blueToken4.setRadius(17.0);
        blueToken4.setStroke(javafx.scene.paint.Color.BLACK);
        blueToken4.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

        //setting green color
        greenToken1.setFill(javafx.scene.paint.Color.valueOf("#29ff00"));
        //setting the location of the token
        greenToken1.setLayoutX(125.0);
        greenToken1.setLayoutY(461.0);
        greenToken1.setRadius(17.0);
        greenToken1.setStroke(javafx.scene.paint.Color.BLACK);
        greenToken1.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

        //setting green color
        greenToken2.setFill(javafx.scene.paint.Color.valueOf("#29ff00"));
        //setting the location of the token
        greenToken2.setLayoutX(83.0);
        greenToken2.setLayoutY(503.0);
        greenToken2.setRadius(17.0);
        greenToken2.setStroke(javafx.scene.paint.Color.BLACK);
        greenToken2.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

        //setting green color
        greenToken3.setFill(javafx.scene.paint.Color.valueOf("#29ff00"));
        //setting the location of the token
        greenToken3.setLayoutX(167.0);
        greenToken3.setLayoutY(503.0);
        greenToken3.setRadius(17.0);
        greenToken3.setStroke(javafx.scene.paint.Color.BLACK);
        greenToken3.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

        //setting green color
        greenToken4.setFill(javafx.scene.paint.Color.valueOf("#29ff00"));
        //setting the location of the token
        greenToken4.setLayoutX(125.0);
        greenToken4.setLayoutY(545.0);
        greenToken4.setRadius(17.0);
        greenToken4.setStroke(javafx.scene.paint.Color.BLACK);
        greenToken4.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

        //setting yellow color
        yellowToken1.setFill(javafx.scene.paint.Color.valueOf("#f5ff00"));
        //setting the location of the token
        yellowToken1.setLayoutX(503.0);
        yellowToken1.setLayoutY(461.0);
        yellowToken1.setRadius(17.0);
        yellowToken1.setStroke(javafx.scene.paint.Color.BLACK);
        yellowToken1.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

        //setting yellow color
        yellowToken2.setFill(javafx.scene.paint.Color.valueOf("#f5ff00"));
        //setting the location of the token
        yellowToken2.setLayoutX(461.0);
        yellowToken2.setLayoutY(503.0);
        yellowToken2.setRadius(17.0);
        yellowToken2.setStroke(javafx.scene.paint.Color.BLACK);
        yellowToken2.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

        //setting yellow color
        yellowToken3.setFill(javafx.scene.paint.Color.valueOf("#f5ff00"));
        //setting the location of the token
        yellowToken3.setLayoutX(544.0);
        yellowToken3.setLayoutY(503.0);
        yellowToken3.setRadius(17.0);
        yellowToken3.setStroke(javafx.scene.paint.Color.BLACK);
        yellowToken3.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

        //setting yellow color
        yellowToken4.setFill(javafx.scene.paint.Color.valueOf("#f5ff00"));
        //setting the location of the token
        yellowToken4.setLayoutX(503.0);
        yellowToken4.setLayoutY(545.0);
        yellowToken4.setRadius(17.0);
        yellowToken4.setStroke(javafx.scene.paint.Color.BLACK);
        yellowToken4.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

        //creating an indicator to tell whose turn it is
        turnIndicator.setFill(javafx.scene.paint.Color.WHITE);
        turnIndicator.setLayoutX(225.0);
        turnIndicator.setLayoutY(225.0);
        turnIndicator.setRadiusX(17.0);
        turnIndicator.setRadiusY(17.0);
        turnIndicator.setStroke(javafx.scene.paint.Color.BLACK);
        turnIndicator.setStrokeMiterLimit(0.0);
        turnIndicator.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        turnIndicator.setStrokeWidth(0.0);

        //adding drop shadow effect to the indicator
        turnIndicator.setEffect(dropShadow);

        //setting up the dice1
        dice1.setFitHeight(41.0);
        dice1.setFitWidth(41.0);
        dice1.setId("dice1");
        dice1.setLayoutX(791.0);
        dice1.setLayoutY(91.0);
        dice1.setOpacity(0.5);
        dice1.setPickOnBounds(true);
        dice1.setPreserveRatio(true);
        dice1.setImage(new Image(getClass().getResource("./resources/dice-1.png").toExternalForm()));

        //setting up the dice2
        dice2.setFitHeight(41.0);
        dice2.setFitWidth(41.0);
        dice2.setId("dice2");
        dice2.setLayoutX(791.0);
        dice2.setLayoutY(152.0);
        dice2.setOpacity(0.5);
        dice2.setPickOnBounds(true);
        dice2.setPreserveRatio(true);
        dice2.setImage(new Image(getClass().getResource("./resources/dice-2.png").toExternalForm()));

        //setting up the dice3
        dice3.setFitHeight(41.0);
        dice3.setFitWidth(41.0);
        dice3.setId("dice3");
        dice3.setLayoutX(791.0);
        dice3.setLayoutY(218.0);
        dice3.setOpacity(0.5);
        dice3.setPickOnBounds(true);
        dice3.setPreserveRatio(true);
        dice3.setImage(new Image(getClass().getResource("./resources/dice-3.png").toExternalForm()));

        //setting up the dice4
        dice4.setFitHeight(41.0);
        dice4.setFitWidth(41.0);
        dice4.setId("dice4");
        dice4.setLayoutX(791.0);
        dice4.setLayoutY(285.0);
        dice4.setOpacity(0.5);
        dice4.setPickOnBounds(true);
        dice4.setPreserveRatio(true);
        dice4.setImage(new Image(getClass().getResource("./resources/dice-4.png").toExternalForm()));

        //setting up the dice5
        dice5.setFitHeight(41.0);
        dice5.setFitWidth(41.0);
        dice5.setId("dice5");
        dice5.setLayoutX(791.0);
        dice5.setLayoutY(353.0);
        dice5.setOpacity(0.5);
        dice5.setPickOnBounds(true);
        dice5.setPreserveRatio(true);
        dice5.setImage(new Image(getClass().getResource("./resources/dice-5.png").toExternalForm()));

        //setting up the dice6
        dice6.setFitHeight(41.0);
        dice6.setFitWidth(41.0);
        dice6.setId("dice6");
        dice6.setLayoutX(791.0);
        dice6.setLayoutY(420.0);
        dice6.setOpacity(0.5);
        dice6.setPickOnBounds(true);
        dice6.setPreserveRatio(true);
        dice6.setImage(new Image(getClass().getResource("./resources/dice-6.png").toExternalForm()));

        getChildren().addAll(imageView, gameBtn, redToken1, redToken2, redToken3, redToken4, blueToken1, blueToken2, 
                blueToken3, blueToken4, greenToken1, greenToken2, greenToken3, greenToken4, yellowToken1, yellowToken2, 
                yellowToken3, yellowToken4, turnIndicator, dice1, dice2, dice3, dice4, dice5, dice6);
    }

    

    //This method shows an alert to notify the user about their turn and also handles the evnts on the tokens 
    public void turnHandler(PrintWriter out) {

        // Click gameBtn to roll the dice
        gameBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                if (myTurn) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("");
                    sb.append(dice);

                    
                    setTurnDiceIndicator(dice);
                    
                    //show alert informing the user to roll a dice
                    Alert a = new Alert(Alert.AlertType.NONE);
                    a.setAlertType(Alert.AlertType.INFORMATION);
                    a.setContentText("It's your turn! Move a token" + " " + sb.toString() + " " + "steps");
                    a.show();
                    myTurn = false;

                }

            }
        });

        // Methods to detect which red token is pressed and move it accordingly
        redToken1.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                out.println("MOVE 1 red");
            }
        });
        redToken2.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                out.println("MOVE 2 red");
            }
        });
        redToken3.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                out.println("MOVE 3 red");
            }
        });
        redToken4.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                out.println("MOVE 4 red");
            }
        });

        // Methods to detect which Blue token is pressed and move it accordingly
        blueToken1.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                out.println("MOVE 1 blue");
            }
        });
        blueToken2.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                out.println("MOVE 2 blue");
            }
        });
        blueToken3.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                out.println("MOVE 3 blue");
            }
        });
        blueToken4.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                out.println("MOVE 4 blue");
            }
        });

        // // Methods to detect which yellow token is pressed and move it accordingly
        yellowToken1.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                out.println("MOVE 1 yellow");
            }
        });
        yellowToken2.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                out.println("MOVE 2 yellow");
            }
        });
        yellowToken3.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                out.println("MOVE 3 yellow");
            }
        });
        yellowToken4.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                out.println("MOVE 4 yellow");
            }
        });

        // Methods to detect which green token is pressed and move it accordingly
        greenToken1.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                out.println("MOVE 1 green");
            }
        });
        greenToken2.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                out.println("MOVE 2 green");
            }
        });
        greenToken3.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                out.println("MOVE 3 green");
            }
        });
        greenToken4.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                out.println("MOVE 4 green");
            }
        });
    }

    
    //Method to get the coordinates for all the possible positions on the Ludo Board
    public Coordinates[] getCoordinates() {
        Coordinates[] mapArray = new Coordinates[76 + 1];
        mapArray[1] = new Coordinates(63, 272); // Red field
        mapArray[2] = new Coordinates(105, 272);
        mapArray[3] = new Coordinates(146, 272);
        mapArray[4] = new Coordinates(188, 272);
        mapArray[5] = new Coordinates(230, 272);
        mapArray[6] = new Coordinates(272, 230);
        mapArray[7] = new Coordinates(272, 189);
        mapArray[8] = new Coordinates(272, 147);
        mapArray[9] = new Coordinates(272, 105);
        mapArray[10] = new Coordinates(272, 63);
        mapArray[11] = new Coordinates(272, 22);
        mapArray[12] = new Coordinates(314, 22);
        mapArray[13] = new Coordinates(356, 22);
        mapArray[14] = new Coordinates(356, 63); // Blue field
        mapArray[15] = new Coordinates(356, 105);
        mapArray[16] = new Coordinates(356, 147);
        mapArray[17] = new Coordinates(356, 189);
        mapArray[18] = new Coordinates(356, 230);
        mapArray[19] = new Coordinates(398, 272);
        mapArray[20] = new Coordinates(440, 272);
        mapArray[21] = new Coordinates(482, 272);
        mapArray[22] = new Coordinates(524, 272);
        mapArray[23] = new Coordinates(566, 272);
        mapArray[24] = new Coordinates(608, 272);
        mapArray[25] = new Coordinates(608, 315);
        mapArray[26] = new Coordinates(608, 357);
        mapArray[27] = new Coordinates(566, 357); // Yellow field
        mapArray[28] = new Coordinates(524, 357);
        mapArray[29] = new Coordinates(482, 357);
        mapArray[30] = new Coordinates(440, 357);
        mapArray[31] = new Coordinates(398, 357);
        mapArray[32] = new Coordinates(356, 400);
        mapArray[33] = new Coordinates(356, 441);
        mapArray[34] = new Coordinates(356, 483);
        mapArray[35] = new Coordinates(356, 525);
        mapArray[36] = new Coordinates(356, 567);
        mapArray[37] = new Coordinates(356, 608);
        mapArray[38] = new Coordinates(314, 608);
        mapArray[39] = new Coordinates(272, 608);
        mapArray[40] = new Coordinates(272, 567); // Green field
        mapArray[41] = new Coordinates(272, 525);
        mapArray[42] = new Coordinates(272, 483);
        mapArray[43] = new Coordinates(272, 441);
        mapArray[44] = new Coordinates(272, 440);
        mapArray[45] = new Coordinates(230, 357);
        mapArray[46] = new Coordinates(188, 357);
        mapArray[47] = new Coordinates(146, 357);
        mapArray[48] = new Coordinates(105, 357);
        mapArray[49] = new Coordinates(63, 357);
        mapArray[50] = new Coordinates(21, 357);
        mapArray[51] = new Coordinates(21, 315);
        mapArray[52] = new Coordinates(21, 272);

        // Color field after normal road - Red
        mapArray[53] = new Coordinates(63, 315); // Color field for red second field
        mapArray[54] = new Coordinates(105, 315);
        mapArray[55] = new Coordinates(146, 315);
        mapArray[56] = new Coordinates(188, 315);
        mapArray[57] = new Coordinates(230, 315);
        mapArray[58] = new Coordinates(270, 332);

        // Color field after normal road - Blue
        mapArray[59] = new Coordinates(314, 63); // Color field for blue second field
        mapArray[60] = new Coordinates(314, 105);
        mapArray[61] = new Coordinates(314, 147);
        mapArray[62] = new Coordinates(314, 189);
        mapArray[63] = new Coordinates(314, 230);
        mapArray[64] = new Coordinates(297, 271);

        // Color field after normal road - Yellow
        mapArray[65] = new Coordinates(566, 315); // Color field for yellow second field
        mapArray[66] = new Coordinates(524, 315);
        mapArray[67] = new Coordinates(482, 315);
        mapArray[68] = new Coordinates(440, 315);
        mapArray[69] = new Coordinates(398, 315);
        mapArray[70] = new Coordinates(558, 298);

        // Color field after normal road - Yellow
        mapArray[71] = new Coordinates(314, 567); // Color field for yellow second field
        mapArray[72] = new Coordinates(314, 525);
        mapArray[73] = new Coordinates(314, 483);
        mapArray[74] = new Coordinates(314, 441);
        mapArray[75] = new Coordinates(314, 400);
        mapArray[76] = new Coordinates(331, 359);

        return mapArray;
    }

    //Method that returns the X and Y coordinates of the Indicator tokens
    public Coordinates[] getIndicators() {
        Coordinates[] indicatorArray = new Coordinates[4 + 1];
        indicatorArray[1] = new Coordinates(225, 225); // Red
        indicatorArray[2] = new Coordinates(405, 225); // Blue
        indicatorArray[3] = new Coordinates(405, 405); // Yellow
        indicatorArray[4] = new Coordinates(225, 405); // Green
        return indicatorArray;
    }

    //Method that returs the list of all tokens in the game
    public List<Circle> getTokens() {

        // List of tokens
        List<Circle> tokens = new ArrayList<Circle>();

        // Red Tokens
        tokens.add(redToken1);
        tokens.add(redToken2);
        tokens.add(redToken3);
        tokens.add(redToken4);

        // Blue Tokens
        tokens.add(blueToken1);
        tokens.add(blueToken2);
        tokens.add(blueToken3);
        tokens.add(blueToken4);

        // Yellow Tokens
        tokens.add(yellowToken1);
        tokens.add(yellowToken2);
        tokens.add(yellowToken3);
        tokens.add(yellowToken4);

        // Green Tokens
        tokens.add(greenToken1);
        tokens.add(greenToken2);
        tokens.add(greenToken3);
        tokens.add(greenToken4);

        return tokens;

    }

    //returns the indicator
    public Ellipse getIndicator() {
        return turnIndicator;
    }

    
    //Method to show an alert when the game starts
    //This alert tells the user about their color
    public void gameHasStarted(String color) {
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.INFORMATION);

            a.setTitle("Game Started");
            a.setContentText("Game Started!! You are " + ": " + color.substring(0, 1).toUpperCase()
                    + color.substring(1));
            a.show();
        });

    }

    //Display a fancy popup to end the game and display the winner name 
    public void endGame(String winner) {
        Platform.runLater(() -> {
            popup(winner);
        });

    }

    //If its the current user's turn, changes the gameBtn text and style to "ROLL DICE"
    public void itsYourTurn(int dice) {
        Platform.runLater(() -> {
            this.dice = dice;
            this.myTurn = true;

            gameBtn.setText("ROLL DICE");
            gameBtn.setStyle("-fx-background-color: #08CA3F; -fx-text-fill: #FFFFFF; -fx-font-weight: bold;");
        });
    }

    //If its not the current user's turn, changes the gameBtn text and style to "AWAITING TURN..."
    public void itsNotMyTurn(int dice) {
        Platform.runLater(() -> {
            this.dice = dice;
            this.myTurn = false;

            gameBtn.setText("AWAITING TURN...");
            gameBtn.setStyle("-fx-background-color: #EC4B19; -fx-text-fill: #FFFFFF; -fx-font-weight: bold;");
        });
    }

    //Method that highlights which dice number is rolled
    public void setTurnDiceIndicator(int value) {
        Platform.runLater(() -> {
            dice1.setOpacity(0.5);
            dice2.setOpacity(0.5);
            dice3.setOpacity(0.5);
            dice4.setOpacity(0.5);
            dice5.setOpacity(0.5);
            dice6.setOpacity(0.5);

            switch (value) {
                case 1:
                    dice1.setOpacity(1);
                    break;
                case 2:
                    dice2.setOpacity(1);
                    break;
                case 3:
                    dice3.setOpacity(1);
                    break;
                case 4:
                    dice4.setOpacity(1);
                    break;
                case 5:
                    dice5.setOpacity(1);
                    break;
                case 6:
                    dice6.setOpacity(1);
                    break;
                default:
                    break;
            }

        });
    }

    //Show alert if the move is not valid/possible
    public void moveDenied() {
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.WARNING);

            a.setTitle("Warning");
            a.setContentText("You can't move this token");
            a.show();
        });
    }

    //popup method that shows the winner 
    public void popup(String winner) {
        InputStream stream = null;
        try {
            final Stage dialog = new Stage();
            dialog.setTitle("Winner!");
            dialog.initModality(Modality.NONE);

            stream = new FileInputStream("./resources/congrats.jpg");
            Image image = new Image(stream);
            // Creating the image view
            ImageView imageView = new ImageView();
            // Setting image to the image view
            imageView.setImage(image);
            // Setting the image view parameters
            imageView.setX(10);
            imageView.setY(10);
            imageView.setFitWidth(700);
            imageView.setFitHeight(500);
            // imageView.setPreserveRatio(true);

            Text winnerMsg = new Text(winner + " has won the game!");
            winnerMsg.setFont(Font.font(null, FontWeight.BOLD, 25));
            winnerMsg.setFill(Color.CYAN);

            BorderPane borderPane = new BorderPane();
            borderPane.setBottom(winnerMsg);

            StackPane innerPane = new StackPane();
            innerPane.getChildren().add(winnerMsg);
            innerPane.setAlignment(Pos.BOTTOM_CENTER);

            StackPane outerPane = new StackPane();
            outerPane.getChildren().addAll(imageView, innerPane);
            Scene dialogScene = new Scene(outerPane, 700, 500);
            dialog.setScene(dialogScene);
            dialog.show();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
