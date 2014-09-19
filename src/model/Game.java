package model;

import console.ConsoleHelper;
import network.ChatClient;
import network.ChatServer;
import network.Client;
import network.Server;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey Nerodenko
 * Date: 27.08.14
 */

public class Game implements Runnable {
    private Player player = new Player();
    private Player playerRemote;

    public Game(int width, int height){
        player.setField(new Field(height, width));
    }

    public static final Game seaBattle = new Game(20, 20);
    public static void main(String[] args) {
        seaBattle.run();
    }

    public void run(){

        ConsoleHelper.printMessage("Please input player's name: ");
        player.setName(ConsoleHelper.getUserInput());

        for(Ship ship : player.getShips()){
            player.getField().randomlyPutShip(ship);
        }

        ConsoleHelper.printMessage(player.getName() + ", Number of ships: " + player.numberOfShip());
        ConsoleHelper.printReal(player.getField());

        ConsoleHelper.printMessage("Choose Server(s) or Client(c): ");
        switch(ConsoleHelper.getUserInput()){
            case "s" :
                Server server = new Server();
                new Thread(server).start();
                synchronized (seaBattle){
                    try {
                        seaBattle.wait();
                    } catch (InterruptedException e) { e.printStackTrace(); }
                }
                playerRemote = server.receivePlayer();

                int order = 1;
                while (!isGameOver()) {
                    new ChatServer(server).run();
                    switch (order) {
                        case 1:
                            Cell shootCell = player.shootCell();
                            server.sendCell(shootCell);
                            server.sendPlayer(player);
                            if (playerRemote.isShipDamaged(shootCell)) { break; }
                            else { order = 2; break; }
                        case 2:
                            shootCell = server.receiveCell();
                            playerRemote = server.receivePlayer();
                            if (player.isShipDamaged(shootCell)) { break; }
                            else { order = 1; break; }
                    }
                }
                break;
            case "c" :
                Client client = new Client();
                new Thread(client).start();
                synchronized (seaBattle){
                    try {
                        seaBattle.wait();
                    } catch (InterruptedException e) { e.printStackTrace(); }
                }
                client.sendPlayer(player);

                order = 1;
                while (!isGameOver()) {
                    new ChatClient(client).run();
                    switch (order) {
                        case 1:
                            Cell shootCell = client.receiveCell();
                            playerRemote = client.receivePlayer();
                            if (player.isShipDamaged(shootCell)) { break; }
                            else { order = 2; break; }
                        case 2:
                            shootCell = player.shootCell();
                            client.sendCell(shootCell);
                            client.sendPlayer(player);
                            if (playerRemote.isShipDamaged(shootCell)) { break; }
                            else { order = 1; break; }
                    }
                }
                break;
        }
    }

    private boolean isGameOver(){
        if(playerRemote == null) return false;
        if(player.numberOfShip() == 0){
            ConsoleHelper.printMessage(playerRemote.getName() + " won");
            return true;
        } else if (playerRemote.numberOfShip() == 0){
            ConsoleHelper.printMessage(player.getName() + " won");
            return true;
        } else { return false; }
    }

}
