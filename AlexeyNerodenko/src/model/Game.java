package model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey Nerodenko
 * Date: 27.08.14
 */
public class Game {
    private Player playerOne = new Player();
    private Player playerTwo = new Player();


    public Game(int width, int height){
        playerOne.setField(new Field(width, height));
        playerTwo.setField(new Field(width, height));


    }

    public static Game seaBattle;
    public static void main(String[] args) {
        seaBattle = new Game(20, 20);
        seaBattle.run();
    }

    public void run(){


        for(Integer s : playerOne.getShips().keySet()){
            for(int i = 0; i < playerOne.getShips().get(s); i++){
                Ship ship = new Ship(0, 0, s, new Random().nextInt(2));
                playerOne.getField().randomlyPutShip(ship);
            }
        }

        for(Integer s : playerTwo.getShips().keySet()){
            for(int i = 0; i < playerTwo.getShips().get(s); i++){
                Ship ship = new Ship(0, 0, s, new Random().nextInt(2));
                playerTwo.getField().randomlyPutShip(ship);
            }
        }

        System.out.println("Player 1, Number of ships: " + playerOne.numberOfShip());
        playerOne.getField().print();

        System.out.println("Player 2, Number of ships: " + playerTwo.numberOfShip());
        playerTwo.getField().print();

    }
}
