package model;

import console.ConsoleHelper;
import console.InnerMessageHandler;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey Nerodenko
 * Date: 27.08.14
 */

public class Player implements Serializable {
    private String name;
    private Field field;
    private ArrayList<Cell> madeShots = new ArrayList<>();
    private ArrayList<Ship> ships = new ArrayList<>();

    public Player() {
        for (int i = 4; i > 0; i--) {
            for (int j = i; j < 5; j++) {
                ships.add(new Ship(i));
            }
        }
    }

    public Cell shootCell() {
        int[] shootCoordinates = new int[2];
//        while (InnerMessageHandler.isEmpty()) { /*NOP*/ }
        String[] stringCoordinates = InnerMessageHandler.getMsg().split(" ");
        shootCoordinates[0] = Integer.parseInt(stringCoordinates[0]);
        shootCoordinates[1] = Integer.parseInt(stringCoordinates[1]);

        Cell shootCell = new Cell(shootCoordinates[0], shootCoordinates[1]);
        madeShots.add(shootCell);
        ConsoleHelper.printMessage(name + ", Number of ships: " + this.ships.size() + "\n" +
                "Moves made : " + this.madeShots);
        return shootCell;
    }

    public boolean isShipDamaged(Cell cell) {
        for (Ship ship : ships) {
            ArrayList<Cell> coordinates = ship.getCoordinates();
            if (coordinates.contains(cell)) {
                coordinates.remove(cell);
                if (coordinates.isEmpty()) {
                    ships.remove(ship);
                }

                this.field.setCell(cell, true);
                return true;
            }
        }

        this.field.setCell(cell, false);
        return false;
    }

    public int numberOfShip() {
        return ships.size();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public ArrayList<Cell> getMadeShots() {
        return madeShots;
    }


}
