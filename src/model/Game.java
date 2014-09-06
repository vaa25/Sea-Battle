package model;

import console.ConsoleHelper;
import network.Client;
import network.Server;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey Nerodenko
 * Date: 27.08.14
 */

public class Game {
    private Player player = new Player("SERVER");
    private Player playerRemote;

    public Game(int width, int height){
        player.setField(new Field(height, width));
    }

    public static Game seaBattle;
    public static void main(String[] args) {
        seaBattle = new Game(20, 20);
        seaBattle.run();
    }

    public void run(){

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

                while(playerRemote == null){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) { System.out.println(e); }
                }

                int order = 1;
                while (!isGameOver()) {
                    switch (order) {
                        case 1:
                            Cell shootCell = player.shootCell();
                            server.sendCell(shootCell);
                            server.sendPlayer(player);
                            if (playerRemote.isShipDamaged(shootCell)) { break; }
                            else { order = 2; }
                        case 2:
                            shootCell = server.receiveCell();
                            playerRemote = server.receivePlayer();
                            if (player.isShipDamaged(shootCell)) { break; }
                            else { order = 1; }
                    }
                }
                break;
            case "c" :
                Client client = new Client();
                client.run();
                break;
        }
    }

    private boolean isGameOver(){
        if(player.numberOfShip() == 0){
            ConsoleHelper.printMessage(playerRemote.getName() + " won");
            return true;
        } else if (playerRemote.numberOfShip() == 0){
            ConsoleHelper.printMessage(player.getName() + " won");
            return true;
        } else { return false; }
    }

    public void setPlayerRemote(Player playerRemote) {
        this.playerRemote = playerRemote;
    }

    public Player getPlayer() {
        return player;
    }
}
