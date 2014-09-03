package src.main.java.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey Nerodenko
 * Date: 27.08.14
 */
public class Player {
    private Field field;
    private ArrayList<Cell> madeShots = new ArrayList<>();
    private ArrayList<Ship> ships = new ArrayList<>();

    public Player() {
        ships.add(new Ship(0, 0, 4, new Random().nextInt(2)));

        ships.add(new Ship(0, 0, 3, new Random().nextInt(2)));
        ships.add(new Ship(0, 0, 3, new Random().nextInt(2)));

        ships.add(new Ship(0, 0, 2, new Random().nextInt(2)));
        ships.add(new Ship(0, 0, 2, new Random().nextInt(2)));
        ships.add(new Ship(0, 0, 2, new Random().nextInt(2)));

        ships.add(new Ship(0, 0, 1, new Random().nextInt(2)));
        ships.add(new Ship(0, 0, 1, new Random().nextInt(2)));
        ships.add(new Ship(0, 0, 1, new Random().nextInt(2)));
        ships.add(new Ship(0, 0, 1, new Random().nextInt(2)));

    }

    public boolean shoot(Cell cell){
        for(Ship ship : ships){
            if(ship.getCoordinates().contains(cell)) {
                ship.getCoordinates().remove(cell);
                if(ship.getCoordinates().isEmpty()){
                    ships.remove(ship);
                }
                return true;
            }
        }
        return false;
    }

    public int numberOfShip(){
        return ships.size();
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }

    public Field getField() {

        return field;
    }

    public ArrayList<Ship> getShips() {

        return ships;
    }

    public ArrayList<Cell> getMadeShots() {
        return madeShots;
    }

    public void setMadeShots(ArrayList<Cell> madeShots) {
        this.madeShots = madeShots;
    }
}
