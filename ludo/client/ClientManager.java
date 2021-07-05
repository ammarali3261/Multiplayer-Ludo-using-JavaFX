package ludo.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

/**
 * LoginManager handles login, registration and chat messages.
 *
 */
public class ClientManager {

    //create a new Scene
    private Scene scene;

    //create a new stage
    private Stage stage;

    //creates a socket to communicate with the server
    private Socket clientSocket;

    //Buffered reader to read messages from the server
    private BufferedReader msgFromServer;

    //print writer to send messages to the server
    private PrintWriter msgToServer;

    //constructor to set the scene and the stage and displaye the landing screen
    public ClientManager(Scene scene, Stage stage) {

        this.scene = scene;
        this.stage = stage;

        // Display landing screen to user
        showHomeScreen();
    }


    //Method to connect to the game server
    private Socket connectToServer() {
        Socket socket = null;

        // Connect to server
        try {
            // Create new socket connecting with server on port 9999
            socket = new Socket("localhost", 9999);

        } catch (UnknownHostException e) {
            System.out.println("Unknown Host");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error connecting to server");
            e.printStackTrace();
        }

        // Define input/output streams to communicate with the server
        try {
            this.msgFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.msgToServer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Error establishing I/O with server");
            e.printStackTrace();
        }

        return socket;
    }

    public void showHomeScreen() {
        // Connect to game server
        this.clientSocket = connectToServer();

        // Main View Controller
        HomePageController controller = new HomePageController();
        scene.setRoot((Parent) controller);
        stage.setTitle("Ludo");
        stage.sizeToScene();

        // Start Queue Thread
        Thread queueThread = new QueueHandler(scene, stage, controller, this.msgFromServer, this.msgToServer);
        queueThread.start();
    }

    private static class QueueHandler extends Thread {

        
        private HomePageController controller;
        private BufferedReader in;
        private PrintWriter out;
        private Scene scene;
        private Stage stage;
        private Boolean running = true;

        public QueueHandler(Scene scene, Stage stage, HomePageController controller, BufferedReader in,
                PrintWriter out) {
            this.controller = controller;
            this.in = in;
            this.out = out;
            this.scene = scene;
            this.stage = stage;
        }

        
        //Run thread to read incoming server messages
         
        public void run() {
            // Tell user there is a game queue
            while (true) {

                String line = null;
                String[] args = null;

                // Try reading server message
                try {
                    line = in.readLine();
                    //split the messages based on space
                    args = line.split(" ");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // If message is null, continue
                if (line == null) {
                    continue;
                }

                // New user in queue
                if (line.startsWith("NEWUSERINQUEUE")) {
                    //changes the status to READY of the active player on the landing screen
                    controller.setUserActiveStatus(args[1]);
                }

                //All the players have joined
                //Game is now started
                if (line.startsWith("STARTGAME")) {
                    System.out.println("Starting game");

                    String color = args[1];

                    // Switches from home screen to game screen 
                    showGameScreen(scene, stage, in, out, color);
                    break;
                }

            }
        }
    }

    //Show the game screen
    public static void showGameScreen(Scene scene, Stage stage, BufferedReader msgFromServer, PrintWriter msgToServer,
            String color) {

        Platform.runLater(() -> {
            LudoController controller = new LudoController();
            scene.setRoot((Parent) controller);
            stage.setTitle("Ludo");
            stage.sizeToScene();

            // Start Game Thread
            Thread gameThread = new GameHandler(controller, msgFromServer, msgToServer, color);
            gameThread.start();

            controller.turnHandler(msgToServer);
        });
    }

    //
    private static class GameHandler extends Thread {

        //list of tokens
        private List<Circle> tokens;

        //coordinates for all the possible positions on the Ludo Board
        private Coordinates[] fields;

        //coordinates of indicators
        private Coordinates[] indicators;

        //Indicator to tell whose turn is it
        private Ellipse indicator;

        private String color;
        private String turnColor;
        private int dice;
        private LudoController controller;
        private BufferedReader in;
        private PrintWriter out;

        //Used to indicate if thread is running or not.
        private Boolean running = true;

        //Argumented constructor of the class which initializes all the different components 
        public GameHandler(LudoController controller, BufferedReader in, PrintWriter out, String color) {
            this.controller = controller;
            this.in = in;
            this.out = out;
            this.color = color;

            // JavaFX elements
            this.tokens = controller.getTokens();
            this.fields = controller.getCoordinates();
            this.indicators = controller.getIndicators();
            this.indicator = controller.getIndicator();

        }

        //Actual code that physically moves the tokens on the board
        private void moveToken(int tokenId, String color, int fieldsMoved) {
            
            
            //Offset in list of tokens. 1-4 : red 5-8 : blue 9-12 : yellow
            // 13-16 : green
            int tokenIdOffset = 0;

            //home position of the token. 0 is the home position of red token
            int homePosition = 0;

            //First block where the token can start from
            int startingPosition = 0;

            //first position of the road to goal of a given token
            int startingGoalPosition = 0;

            // Set the above values based on which color is moving.
            if (color.equals("red")) {
                tokenIdOffset = 0;
                homePosition = 0;
                startingPosition = 1;
                startingGoalPosition = 53;
            }

            if (color.equals("blue")) {
                tokenIdOffset = 4;
                homePosition = 13;
                startingPosition = 14;
                startingGoalPosition = 59;
            }

            if (color.equals("yellow")) {
                tokenIdOffset = 8;
                homePosition = 26;
                startingPosition = 27;
                startingGoalPosition = 65;
            }

            if (color.equals("green")) {
                tokenIdOffset = 12;
                homePosition = 39;
                startingPosition = 40;
                startingGoalPosition = 71;
            }

            
            int positionWrap = 52;

            /**
             * If the user has wrapped (made 1 lap)
             */
            boolean hasWrapped = false;

            //calculate the position of the token
            int position = fieldsMoved + homePosition;

            // Check if tokens have wrapped
            if (position > positionWrap) {
                hasWrapped = true;
                position = position % positionWrap;
            }

            // Check if token is going to goal
            if (position >= startingPosition && hasWrapped) {
                position = position % startingPosition + startingGoalPosition;
            }

            // Movie token's X and Y coordinated to the calculated position.
            this.tokens.get(tokenId + tokenIdOffset).setLayoutX(fields[position].getXCoordinates());
            this.tokens.get(tokenId + tokenIdOffset).setLayoutY(fields[position].getYCoordinates());

            
        }

        //Method to change the position of the indicator based on whose turn it is
        public void moveIndicator(String color) {

            int colorNumber = 1;
            if (color.equals("red")) {
                colorNumber = 1;
            }
            if (color.equals("blue")) {
                colorNumber = 2;
            }
            if (color.equals("yellow")) {
                colorNumber = 3;
            }
            if (color.equals("green")) {
                colorNumber = 4;
            }

            // Move indicator to the corresponding colored player
            this.indicator.setLayoutX(indicators[colorNumber].getXCoordinates());
            this.indicator.setLayoutY(indicators[colorNumber].getYCoordinates());
        }

        
        //Run thread to read incoming game server messages
         
        public void run() {

            // Input from server
            String line = null;

            // Tell user the game is starting
            controller.gameHasStarted(this.color);

            
            //All players have connected and the game has started.
             
            System.out.println("Game loop started");
            String[] args;
            while (true) {

                // Get turnColor
                while (true) {
                    try {
                        line = in.readLine();
                    } catch (IOException e) {
                        System.out.println(e);
                    }

                    // If no message from server
                    if (line == null) {
                        continue;
                    }

                    // Turn message received
                    if (line.startsWith("TURN")) {
                        args = line.split(" ");

                        this.turnColor = args[1];
                        this.dice = Integer.parseInt(args[2]);
                        

                        // It's your turn
                        if (this.turnColor.equals(this.color)) {
                            System.out.println("My turn");
                            controller.itsYourTurn(this.dice);
                        } else {
                            System.out.println(this.turnColor + " turn");
                            controller.itsNotMyTurn(this.dice);
                        }

                        // Move indicator
                        this.moveIndicator(this.turnColor);

                        break;

                    }
                }

                // Get move from whoever's turn it is
                while (true) {
                    try {
                        line = in.readLine();
                    } catch (IOException e) {
                        System.out.println(e);
                    }

                    // If no message from server
                    if (line == null) {
                        continue;
                    }

                    // Move request received: MOVE <tokenId (1-4)> <position>
                    if (line.startsWith("MOVE")) {
                        args = line.split(" ");

                        System.out.println(line);
                        this.moveToken(Integer.parseInt(args[1]), this.turnColor, Integer.parseInt(args[2]));

                        break;
                    }

                    // If move was denied
                    if (line.startsWith("MOVEDENIED")) {
                        controller.moveDenied();
                    }
                }

                // Check if there is a winner
                while (true) {
                    try {
                        line = in.readLine();
                    } catch (IOException e) {
                        System.out.println(e);
                    }

                    // If no message from server
                    if (line == null) {
                        continue;
                    }

                    // No winners
                    if (line.startsWith("NOWIN")) {
                        break;
                    }

                    // There is a winner
                    if (line.startsWith("WIN")) {
                        args = line.split(" ");

                        // Show end screen with winner color
                        this.controller.endGame(args[1]);
                    }
                }

            }

        }

    }
}
