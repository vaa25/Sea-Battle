package main.java.model;

import main.java.console.ConsoleHelper;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey Nerodenko
 * Date: 27.08.14
 */

public class Player {
    private String name;
    private Field field;
    private ArrayList<Cell> madeShots = new ArrayList<>();
    private ArrayList<Ship> ships = new ArrayList<>();

    public Player(String name) {
        this.name = name;
        for(int i = 4; i > 0; i--){
            for(int j = i; j < 5; j++){
                ships.add(new Ship(i));
            }
        }
    }

    public Cell shootCell(){
        ConsoleHelper.printMessage(name + "\n" +
                                   "Number of ships: " + this.ships.size() + "\n" +
                                   "Moves made : " + this.madeShots + "\n" +
                                   "Input two numbers between 0..." + (this.field.getWidth() - 1) + "\n" +
                                   "Your move: ");
        int[] shootCoordinates = ConsoleHelper.readShootCoordinates();
        Cell shootCell = new Cell(shootCoordinates[0], shootCoordinates[1]);
        madeShots.add(shootCell);
        return shootCell;
    }

    public boolean isShipDamaged(Cell cell){
        for(Ship ship : ships){
            ArrayList<Cell> coordinates = ship.getCoordinates();
            if(coordinates.contains(cell)) {
                coordinates.remove(cell);
                if(coordinates.isEmpty()){
                    ships.remove(ship);
                }
                ConsoleHelper.printMessage("HIT!");
                this.field.setCell(cell, true);
                this.field.printGame();
                return true;
            }
        }
        ConsoleHelper.printMessage("MISSED!");
        this.field.setCell(cell, false);
        this.field.printGame();
        return false;
    }

    public int numberOfShip(){
        return ships.size();
    }

    public void setField(Field field) {
        this.field = field;
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
}
