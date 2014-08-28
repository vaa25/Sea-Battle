package model;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey Nerodenko
 * Date: 27.08.14
 */
public class Player {
    private Field field;
    private HashMap<Integer, Integer> ships = new HashMap<>(); //numberOfDecks, quantityOfShips

    public Player() {
        ships.put(4, 1);
        ships.put(3, 2);
        ships.put(2, 3);
        ships.put(1, 4);
    }

    public int numberOfShip(){
        int result = 0;
        for(Integer s : ships.keySet()){
            result += ships.get(s);
        }
        return result;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void setShips(HashMap<Integer, Integer> ships) {
        this.ships = ships;
    }

    public Field getField() {

        return field;
    }

    public HashMap<Integer, Integer> getShips() {

        return ships;
    }
}
