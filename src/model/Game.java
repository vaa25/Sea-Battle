package model;

import console.ConsoleHelper;
import network.ChatClient;
import network.ChatServer;
import network.Client;
import network.Server;

import java.util.Random;

import static model.Game.TurnToMove.playerTurn;
import static model.Game.TurnToMove.remotePlayerTurn;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey Nerodenko
 * Date: 27.08.14
 */

public class Game implements Runnable {
    private Player player = new Player();
    private Player playerRemote;
    private TurnToMove turn;

    public Game(int width, int height) {
        player.setField(new Field(height, width));
    }

    public static final Game seaBattle = new Game(20, 20);

    public static void main(String[] args) {
        seaBattle.run();
    }

    public void run() {

        ConsoleHelper.printMessage("Please input player's name: ");
        player.setName(ConsoleHelper.getUserInput());

        for (Ship ship : player.getShips()) {
            player.getField().randomlyPutShip(ship);
        }

        ConsoleHelper.printMessage(player.getName() + ", Number of ships: " + player.numberOfShip());
        ConsoleHelper.printReal(player.getField());

        ConsoleHelper.printMessage("Choose Server(s) or Client(c): ");
        switch (ConsoleHelper.getUserInput()) {
            case "s":
                Server server = new Server();
                new Thread(server).start();
                synchronized (seaBattle) {
                    try {
                        seaBattle.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                playerRemote = server.receivePlayer();

                ConsoleHelper.printMessage("Server randomly decides who moves first...");
                String msgTurn = seaBattle.whoMovesFirst();
                server.sendData(msgTurn);
                if (msgTurn.equals(playerTurn.name())) {
                    turn = playerTurn;
                    ConsoleHelper.printMessage("You are lucky, make first move!");
                } else {
                    turn = remotePlayerTurn;
                    ConsoleHelper.printMessage("Your enemy moves first!");
                }

                while (!isGameOver()) {
                    ConsoleHelper.printMessage("You can chat!");
                    new ChatServer(server).run();
                    switch (turn) {
                        case playerTurn:
                            Cell shootCell = player.shootCell();
                            server.sendCell(shootCell);
                            server.sendPlayer(player);
                            if (playerRemote.isShipDamaged(shootCell)) {
                                ConsoleHelper.printMessage("HIT!\nYour enemy field: ");
                                ConsoleHelper.printGame(playerRemote.getField());
                                ConsoleHelper.printMessage("You make next move! (e.g. \"3 1\" or \"12 14\")");
                                break;
                            } else {
                                turn = remotePlayerTurn;
                                ConsoleHelper.printMessage("MISSED!\nYour enemy field: ");
                                ConsoleHelper.printGame(playerRemote.getField());
                                ConsoleHelper.printMessage("Your enemy makes next move!");
                                break;
                            }
                        case remotePlayerTurn:
                            shootCell = server.receiveCell();
                            playerRemote = server.receivePlayer();
                            if (player.isShipDamaged(shootCell)) {
                                ConsoleHelper.printMessage("HIT!\nYour field: ");
                                ConsoleHelper.printReal(player.getField());
                                ConsoleHelper.printMessage("Your enemy makes next move!");
                                break;
                            } else {
                                ConsoleHelper.printMessage("MISSED!\nYour field: ");
                                ConsoleHelper.printReal(player.getField());
                                ConsoleHelper.printMessage("Your enemy field: ");
                                ConsoleHelper.printGame(playerRemote.getField());
                                ConsoleHelper.printMessage("You make next move! (e.g. \"3 1\" or \"12 14\")");
                                turn = playerTurn;
                                break;
                            }
                    }
                }
                break;
            case "c":
                Client client = new Client();
                new Thread(client).start();
                synchronized (seaBattle) {
                    try {
                        seaBattle.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                client.sendPlayer(player);

                ConsoleHelper.printMessage("Server randomly decides who moves first...");
                msgTurn = client.receiveData();
                if (msgTurn.equals(playerTurn.name())) {
                    turn = playerTurn;
                    ConsoleHelper.printMessage("Your enemy moves first!");
                } else {
                    turn = remotePlayerTurn;
                    ConsoleHelper.printMessage("You are lucky, make first move!");
                }

                while (!isGameOver()) {
                    ConsoleHelper.printMessage("You can chat!");
                    new ChatClient(client).run();

                    switch (turn) {
                        case playerTurn:
                            Cell shootCell = client.receiveCell();
                            playerRemote = client.receivePlayer();
                            if (player.isShipDamaged(shootCell)) {
                                ConsoleHelper.printMessage("HIT!\nYour field: ");
                                ConsoleHelper.printReal(player.getField());
                                ConsoleHelper.printMessage("Your enemy makes next move!");
                                break;
                            } else {
                                turn = remotePlayerTurn;
                                ConsoleHelper.printMessage("MISSED!\nYour field: ");
                                ConsoleHelper.printReal(player.getField());
                                ConsoleHelper.printMessage("Your enemy field: ");
                                ConsoleHelper.printGame(playerRemote.getField());
                                ConsoleHelper.printMessage("You make next move! (e.g. \"3 1\" or \"12 14\")");
                                break;
                            }
                        case remotePlayerTurn:
                            shootCell = player.shootCell();
                            client.sendCell(shootCell);
                            client.sendPlayer(player);
                            if (playerRemote.isShipDamaged(shootCell)) {
                                ConsoleHelper.printMessage("HIT!\nYour enemy field: ");
                                ConsoleHelper.printGame(playerRemote.getField());
                                ConsoleHelper.printMessage("You make next move! (e.g. \"3 1\" or \"12 14\")");
                                break;
                            } else {
                                turn = playerTurn;
                                ConsoleHelper.printMessage("MISSED!\nYour enemy field: ");
                                ConsoleHelper.printGame(playerRemote.getField());
                                ConsoleHelper.printMessage("Your enemy makes next move!");
                                break;
                            }
                    }
                }
                break;
        }
    }

    private String whoMovesFirst() {
        int playerNumber = new Random().nextInt();
        int remotePlayerNumber = new Random().nextInt();
        do {
            if (playerNumber > remotePlayerNumber) {
                return playerTurn.name();
            } else if (remotePlayerNumber > playerNumber) {
                return remotePlayerTurn.name();
            }
        } while (playerNumber == remotePlayerNumber);

        return null;
    }

    private boolean isGameOver() {
        if (playerRemote == null) return false;
        if (player.numberOfShip() == 0) {
            ConsoleHelper.printMessage(playerRemote.getName() + " won");
            return true;
        } else if (playerRemote.numberOfShip() == 0) {
            ConsoleHelper.printMessage(player.getName() + " won");
            return true;
        } else {
            return false;
        }
    }

    enum TurnToMove {
        playerTurn,
        remotePlayerTurn
    }

}
