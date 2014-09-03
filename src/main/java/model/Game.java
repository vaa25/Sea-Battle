package main.java.model;

import main.java.console.ConsoleHelper;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey Nerodenko
 * Date: 27.08.14
 */
public class Game {
    private Player playerOne = new Player("Alex");
    private Player playerTwo = new Player("Olga");


    public Game(int width, int height){
        playerOne.setField(new Field(height, width));
        playerTwo.setField(new Field(height, width));
    }

    public static Game seaBattle;
    public static void main(String[] args) {
        seaBattle = new Game(20, 20);
        seaBattle.run();
    }

    public void run(){
        for(Ship ship : playerOne.getShips()){
                playerOne.getField().randomlyPutShip(ship);
        }

        for(Ship ship : playerTwo.getShips()){
                playerTwo.getField().randomlyPutShip(ship);
        }

        System.out.println("Player 1, Number of ships: " + playerOne.numberOfShip());
        playerOne.getField().printReal();

        System.out.println("Player 2, Number of ships: " + playerTwo.numberOfShip());
        playerTwo.getField().printReal();

        int order = 1;
        while(!isGameOver()){
            switch (order){
                case 1 :
                    if(playerTwo.isShipDamaged(playerOne.shootCell())){ break; }
                    else { order = 2; }
                case 2 :
                    if(playerOne.isShipDamaged(playerTwo.shootCell())){ break; }
                    else { order = 1; }
            }
        }
    }

    public boolean isGameOver(){
        if(playerOne.numberOfShip() == 0){
            System.out.println("Player 2 wins");
            return true;
        } else if (playerTwo.numberOfShip() == 0){
            System.out.println("Player 1 wins");
            return true;
        } else return false;
    }
}
