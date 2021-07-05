package ludo.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the multi-threaded server for the Ludo game. It accepts connections
 * from the clients on port 9999.
 *
 *
 *
 */
public class Server extends Thread {

    private ServerSocket serverSocket;

    //constructor for the server
    public Server() {
        System.out.println("Serving the clients on port 9999");
    }

    public void run() {

        // Initiating Server socket on port 9999
        try {
            this.serverSocket = new ServerSocket(9999);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // infinite loop that is always listening for new connections from the clients. Once 4 clients are connected the game is started
        try {
            while (true) {
                Game game = new Game();

                System.out.println("Waiting for players...");

                // Wait for red user
                game.addPlayer(game.new Player(serverSocket.accept(), "red"));

                // Wait for blue user
                game.addPlayer(game.new Player(serverSocket.accept(), "blue"));

                // Wait for yellow user
                game.addPlayer(game.new Player(serverSocket.accept(), "yellow"));

                // Wait for green user
                game.addPlayer(game.new Player(serverSocket.accept(), "green"));

                //informs the users about the starting of the game by sending a broadcast message
                game.broadcast("STARTGAME");

                // Calls the run method of the Game thread class thereby starting the game
                game.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //stops listening for new connections
                serverSocket.close();
            } catch (IOException e) {
                System.out.println("Error closing game server socket");
                e.printStackTrace();
            }
        }
    }

    /**
     * This class creates a new thread for each game with 4 players and 4 tokens
     */
    class Game extends Thread {

        //List to store the players
        private List<Player> players = new ArrayList<Player>();

        //class constructor
        public Game() {
            System.out.println("New Game");

        }

        //This method adds a new player to the game by adding them to the players
        // list and also notifies other users about the joining of a new player.
        public void addPlayer(Player player) {
            System.out.println(player.getColor() + " has joined.");
            this.players.add(player);

            //sending message to all connected clients
            broadcastLoop("NEWUSERINQUEUE");
        }

        //Broadcasts a message to all the players in the game
        private void broadcast(String message) {

            System.out.println("Broadcasting to users: " + message);

            // Send a message to all the players currently in the game
            for (Player player : players) {
                player.getOut().println(message + " " + player.getColor());
            }
        }

        //Broadcasts a message to all the players in the game
        private void broadcastLoop(String message) {

            System.out.println("Broadcasting to users: " + message);

            // Send a message to all the players currently in the game
            for (Player player : players) {
                for (Player subPlayers : players) {
                    player.getOut().println(message + " " + subPlayers.getColor());
                }
            }
        }

        //Start the game
        public void run() {

            String msgIn = null;
            String[] array;
            int dice;
            boolean gameNotOver = true;

            //Continues the game until there is no winner
            while (gameNotOver) {

                // change players after every turn
                for (Player player : players) {

                    // generate a random number between 1 and 6 inclusive
                    dice = 1 + (int) (Math.random() * 6);
                    System.out.println("Dice: " + dice);

                    // broadcast which player's turn is it
                    broadcast("TURN " + player.getColor() + " " + dice);

                    // check if the player has any valid moves
                    if (!player.canMoveAny(dice)) {
                        continue;
                    }

                    // loop to check the client's move 
                    while (true) {
                        try {
                            // Read input from client
                            msgIn = player.getIn().readLine();
                        } catch (IOException e) {
                            System.out.println("Connection lost");
                            break;
                        }

                        // Continue if no request
                        if (msgIn == null) {
                            continue;
                        }

                        // Move request: MOVE <token_id (1-4)> <color>
                        if (msgIn.startsWith("MOVE")) {
                            //split the msg based on spaces
                            array = msgIn.split(" ");

                            // Get token ID and correct it for Player object
                            int tokenId = Integer.parseInt(array[1]) - 1;

                            // Continue (ignore) if trying to move another color
                            if (!array[2].equals(player.getColor())) {
                                continue;
                            }

                            // Move token if allowed
                            if (player.moveTokenIfAllowed(tokenId, dice)) {

                                // Broadcast move to everyone and continue to next user
                                broadcast("MOVE " + tokenId + " " + player.getTokenPosition(tokenId));
                                break;
                            } else {
                                // Cannot move token
                                player.getOut().println("MOVEDENIED");
                            }
                        }
                    }

                    // Check if user has won
                    if (player.hasWon()) {

                        // Broadcast win
                        broadcast("WIN " + player.getColor());

                        // Winner is found
                        gameNotOver = false;

                        // Break from for loop
                        break;
                    } else {
                        broadcast("NOWIN");
                    }

                }
            }

        }

        /**
         * Player class
         */
        class Player extends Thread {

            private Token[] tokens = new Token[4];
            private String color;
            private Socket socket;
            //to send msgs to the clients
            private PrintWriter out;
            //to read msgs sent from the clients
            private BufferedReader in;

//            private String username = null;
            //class to create a new player with the connection socket and the color
            public Player(Socket socket, String color) {

                System.out.println("New player: " + color);

                // 4 different tokens of Ludo
                tokens[0] = new Token();
                tokens[1] = new Token();
                tokens[2] = new Token();
                tokens[3] = new Token();

                // color of the player
                this.color = color;

                // connection socket
                this.socket = socket;

                //get the outputstream to send data
                try {
                    this.out = new PrintWriter(this.socket.getOutputStream(), true);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //get the inputstream to receive data
                try {
                    this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Start player thread
                this.start();

            }

            //returns the position of the token
            public int getTokenPosition(int tokenId) {
                return tokens[tokenId].getPosition();
            }

            //uses the tokens' location to check if a player has won
            public boolean hasWon() {

                // Check if any token is done.
                for (Token token : tokens) {
                    if (!token.isDone()) {
                        return false;
                    }
                }

                // all tokens have reached home and the player has won
                return true;
            }

            //Check if the dice roll has any valid tokens that can be moved
            public boolean canMoveAny(int dice) {
                for (Token token : tokens) {
                    if (!token.isValidMove(dice)) {

                        // Cannot move any
                        return false;
                    }
                }

                // Can move a token
                return true;
            }

            //move the token if the move is valid and return a boolean value 
            //indicating if the token was moved successfully.
            public boolean moveTokenIfAllowed(int tokenId, int steps) {
                return this.tokens[tokenId].move(steps);
            }

            //returns player socket
            public Socket getSocket() {
                return this.socket;
            }

            //returns the instance of print writer 
            public PrintWriter getOut() {
                return this.out;
            }

            //returns the instance of buffered reader
            public BufferedReader getIn() {
                return this.in;
            }

            //returns the color assigned ot the player
            public String getColor() {
                return this.color;
            }

        }

        //Class for a single token of Ludo Token
        class Token {

            private int position;

            //Class constructor defining the initial position of the token as zero.
            public Token() {
                System.out.println("Token created");
                this.position = 0;
            }

            //Take the dice roll output as the argument to check if any valid move is possible 
            public boolean isValidMove(int dice) {

                // Try to move the token according to the dice roll
                if (this.move(dice)) {

                    //if the move is successful, revert the move and return true
                    setPosition(position - dice);
                    return true;
                }

                //if the move is unsuccessful, return false
                return false;

            }

            //move the token/token according to the number on the dice.
            //Increment the current position of the token by adding in 
            //the number of the dice.
            public boolean move(int steps) {
                //where the final position of the token will be
                int targetPos = this.position + steps;

                // If the user is trying to move the token past the end.
                if (targetPos > 59) {
                    return false;
                } //if the move is valid, the new token position is set and it returns true
                else {
                    setPosition(targetPos);
                    return true;
                }
            }

            //simple method that just sets the position of the token
            private void setPosition(int position) {
                this.position = position;
            }

            //gets the current position of the token
            public int getPosition() {
                return this.position;
            }

            //check if the token is still in home
            public boolean isHome() {
                return this.position == 0;
            }

            //Check if the token has reached the end
            public boolean isDone() {
                return this.position == 59;
            }
        }
    }

    public static void main(String[] args) throws Exception {

        System.out.println("Starting server");

        // Game Server
        new Server().start();
    }

}
