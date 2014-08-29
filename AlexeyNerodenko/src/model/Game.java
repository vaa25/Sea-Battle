package model;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey Nerodenko
 * Date: 27.08.14
 */
public class Game {
    private Player playerOne = new Player();
    private Player playerTwo = new Player();


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
                    System.out.println("Player 1");
                    System.out.println("Number of ships: " + playerOne.numberOfShip());
                    System.out.println("Moves made : " + playerOne.getMadeShots());
                    System.out.print("Move: "); //format of input "0 0"
                    Cell shootCell1 = Cell.readCell();
                    playerOne.getMadeShots().add(shootCell1);
                    if(playerTwo.shoot(shootCell1)){
                        System.out.println("HIT!");
                        playerOne.getField().setCell(shootCell1, true);
                        playerOne.getField().printGame();
                        break;
                    } else {
                        System.out.println("MISSED!");
                        playerOne.getField().setCell(shootCell1, false);
                        playerOne.getField().printGame();
                        order = 2;
                        break;
                    }
                case 2 :
                    System.out.println("Player 2");
                    System.out.println("Number of ships: " + playerTwo.numberOfShip());
                    System.out.println("Moves made : " + playerTwo.getMadeShots());
                    System.out.print("Move: "); //format of input "2 3"
                    Cell shootCell2 = Cell.readCell();
                    playerTwo.getMadeShots().add(shootCell2);
                    if(playerOne.shoot(shootCell2)){
                        System.out.println("HIT!");
                        playerTwo.getField().setCell(shootCell2, true);
                        playerTwo.getField().printGame();
                        break;
                    } else {
                        System.out.println("MISSED!");
                        playerTwo.getField().setCell(shootCell2, false);
                        playerTwo.getField().printGame();
                        order = 1;
                        break;
                    }
            }
        }

    }

    public boolean isGameOver(){
        if(playerOne.numberOfShip() == 0){
            System.out.println("Player 1 wins");
            return true;
        } else if (playerTwo.numberOfShip() == 0){
            System.out.println("Player 2 wins");
            return true;
        } else return false;
    }


}
