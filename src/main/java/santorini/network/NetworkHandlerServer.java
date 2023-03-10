package santorini.network;

import santorini.Parameters;
import santorini.controller.Game;
import santorini.model.Gamer;
import santorini.model.godCards.God;
import santorini.model.Mossa;
import santorini.model.Mossa.Action;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Class NetworkHandlerServer
 */

public class NetworkHandlerServer implements Runnable {
    private ServerSocket serverSocket;
    private ArrayList<Gamer> players = new ArrayList<>();
    private Game game;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Thread partita;
    private int i = 0, max = 0;

    /**
     * method getGame
     *
     * @return game
     */
    public Game getGame() {
        return game;
    }

    /**
     * method getPartita
     *
     * @return partita
     */
    public Thread getPartita() {
        return partita;
    }

    /**
     * initialization NetworkHandlerServer
     *
     * @throws IOException
     */
    public NetworkHandlerServer() throws IOException {
        serverSocket = new ServerSocket(Parameters.PORT);
    }

    /**
     * Starts listening to clients.
     * The first client connected will choose the number of players.
     * Until player numbers is reached, the game starts.
     */
    public void run() {
        initGameConnections();
    }

    /**
     * Listens to connections from 2 or 3 clients
     * Any gamer object will be created with
     * - A reference to its socket
     * - A name
     * - A progress id (created from i)
     */
    public void initGameConnections() {
        try {
            i = 0;
            Socket s = serverSocket.accept();
            max = initializeFirstClient(s);
            for (; i < max; i++) {
                Socket s1 = serverSocket.accept();
                initializeClient(s1);
            }
            //wait();
            startGame();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Unreachable");
        }

    }

    /**
     * method initializeFirstClient
     * initialize parameter of first client connected and asks him the number of players
     * in the match
     *
     * @param s first connected client's socket
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public int initializeFirstClient(Socket s) throws IOException, ClassNotFoundException {
        inputStream = new ObjectInputStream(s.getInputStream());
        outputStream = new ObjectOutputStream(s.getOutputStream());
        String name = inputStream.readObject().toString();
        players.add(new Gamer(s, name, i, inputStream, outputStream));
        outputStream.writeObject(i + "," + name);
        outputStream.flush();
        i++;
        outputStream.writeObject(Parameters.command.SET_PLAYERS_NUMBER);
        outputStream.flush();
        int m = Integer.parseInt((String) inputStream.readObject());
        return m;
    }

    /**
     * method initializeClient
     * initialize all parameters to gamer class after user is connected
     *
     * @param s just connected client's socket
     */
    public void initializeClient(Socket s) {
        //new Thread(() -> {
        try {
            inputStream = new ObjectInputStream(s.getInputStream());
            outputStream = new ObjectOutputStream(s.getOutputStream());
            String newName = handleDoubleName(inputStream.readObject().toString());
            players.add(new Gamer(s, newName, i, inputStream, outputStream));
            outputStream.writeObject(i + "," + newName);
            outputStream.flush();
            //notifyAll();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Unreachable");
        }
        //}).start();
    }

    /**
     * method handleDoubleName
     * corrects duplicated name with a progressive number after last connected duplicated name
     *
     * @param name original name
     * @return handled name
     */
    private String handleDoubleName(String name) {
        int i = 0;
        for (Gamer g : players) {
            if (i == 0) {
                if (g.getName().equals(name)) {
                    i++;
                }
            } else {
                if (g.getName().equals(name + "-" + i)) {
                    i++;
                }
            }
        }
        if (i == 0) {
            return name;
        } else {
            return name + "-" + i;
        }
    }

    /**
     * method startGame
     * starts a new game and becomes handler for socket and
     * network data transmissions
     */
    public void startGame() {
        if (players.size() == 2 || players.size() == 3) {
            game = new Game(players, this);
            partita = new Thread(game);
            partita.start();
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.out.println("Unreachable");
            }
        }
    }

    /**
     * method updateField
     * sends field status to a specific player
     *
     * @param gamer player to send field
     */
    public void updateField(Gamer gamer) {
        outputStream = gamer.getOutputStream();
        try {
            outputStream.reset();
            outputStream.writeObject(game.getTable());
            outputStream.flush();
        } catch (IOException e) {
            game.networkError(gamer);
        }
    }

    /**
     * method chooseCard
     * requests to a specific player to choose a card from a pool
     *
     * @param cards  pool of cards
     * @param player player that has to make the choice
     * @return choosed card
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public God chooseCard(ArrayList<God> cards, Gamer player) throws IOException, ClassNotFoundException {
        outputStream = player.getOutputStream();
        outputStream.reset();
        outputStream.writeObject(cards);
        outputStream.flush();
        inputStream = player.getInputStream();
        God g = (God) inputStream.readObject();
        return g;
    }

    /**
     * method richiediMossa
     * request to a specific client to do his moves
     *
     * @param tipo  Type of action (e.g. move, build)
     * @param gamer Player that has to do action
     * @return player action details
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Mossa richiediMossa(Action tipo, Gamer gamer) throws IOException, ClassNotFoundException {
        outputStream = gamer.getOutputStream();
        Parameters.command command = null;
        switch (tipo) {
            case MOVE:
                command = Parameters.command.MOVE;
                break;
            case BUILD:
                command = Parameters.command.BUILD;
                break;

            /**default:
             command = Parameters.command.FAILED;
             break;*/
        }
        outputStream.reset();
        outputStream.writeObject(command);
        outputStream.flush();
        inputStream = gamer.getInputStream();
        Mossa mossa = (Mossa) inputStream.readObject();
        return mossa;
    }

    /**
     * method sendFailed
     * generic error message that means "action failed" for a specified player
     *
     * @param gamer player to send error message
     */
    public void sendFailed(Gamer gamer, String message) {
        outputStream = gamer.getOutputStream();
        try {
            outputStream.reset();
            outputStream.writeObject(Parameters.command.FAILED);
            outputStream.flush();
            outputStream.reset();
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException e) {
            game.networkError(gamer);
        }
    }


    /**
     * method placePawns
     * requests initial pawn coordinates to players
     *
     * @param gamer player to request to put pawns
     * @return String containing 2 positions in format "x1,y1,x2,y2"
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public String placePawns(Gamer gamer) throws IOException, ClassNotFoundException {
        outputStream = gamer.getOutputStream();
        outputStream.reset();
        outputStream.writeObject(Parameters.command.INITIALIZE_PAWNS);
        outputStream.flush();
        inputStream = gamer.getInputStream();
        String posizioni = inputStream.readObject().toString();
        return posizioni;
    }

    /**
     * method winner
     * sends who is the winner to a specified player
     *
     * @param to     specified player
     * @param winner winner player
     * @throws IOException
     */
    public void winner(Gamer to, Gamer winner) throws IOException {
        outputStream = to.getOutputStream();
        outputStream.reset();
        if (to == winner) {
            outputStream.writeObject(Parameters.command.WINNER);
            outputStream.flush();
        } else {
            outputStream.writeObject(Parameters.command.LOSER);
            outputStream.flush();
            outputStream.reset();
            outputStream.writeObject(winner.getName());
            outputStream.flush();
        }
    }

    /**
     * method notifyNetworkError
     * notify a network error to a specified player
     *
     * @param to  notification receiver
     * @param who player disconnected
     * @throws IOException
     */
    public void notifyNetworkError(Gamer to, Gamer who) throws IOException {
        outputStream = to.getOutputStream();
        outputStream.reset();
        outputStream.writeObject(Parameters.command.NETWORK_ERROR);
        outputStream.flush();
        outputStream.reset();
        outputStream.writeObject(who.getName());
        outputStream.flush();
    }

    /**
     * method sendMessage
     * sends a text message to a specified player
     *
     * @param to  specified player
     * @param msg text message
     */
    public void sendMessage(Gamer to, String msg) {
        outputStream = to.getOutputStream();
        try {
            outputStream.reset();
            outputStream.writeObject(Parameters.command.MESSAGE);
            outputStream.flush();
            outputStream.reset();
            outputStream.writeObject(msg);
            outputStream.flush();
        } catch (IOException e) {
            game.networkError(to);
        }
    }

    /**
     * method sendLockedPawn
     * sends the pawn which is locked
     *
     * @param i      id pawn
     * @param g      gamer
     * @param locked it is locked or not
     */

    public void sendLockedPawn(int i, Gamer g, boolean locked) {
        outputStream = g.getOutputStream();
        try {
            outputStream.reset();
            outputStream.writeObject(Parameters.command.LOCKED_PAWN);
            outputStream.flush();
            outputStream.reset();
            outputStream.writeObject(i + "," + locked);
            outputStream.flush();
        } catch (IOException e) {
            game.networkError(g);
        }

    }

    /**
     * method switchPawn
     * changes the reference of the pawn
     * uses for Ares effect
     *
     * @param g gamer
     */
    public void switchPawn(Gamer g) {
        outputStream = g.getOutputStream();
        try {
            outputStream.reset();
            outputStream.writeObject(Parameters.command.SWITCH_PAWN);
            outputStream.flush();
        } catch (IOException e) {
            game.networkError(g);
        }
    }

}
