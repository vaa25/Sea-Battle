package app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents model and service functions of the game field
 */
public class Field {

    // dimension of the field
    private int fieldSize = 10;

    // list of ships
    private List<Ship> shipList;

    // grid with coordinates for the field
    private Cell[][] fieldGrid;

    // def. constructor
    public Field() {
        fieldGrid = new Cell[fieldSize][fieldSize];
        shipList = new ArrayList<>();
    }

    public Cell[][] getFieldGrid() {
        return fieldGrid;
    }

    public void setFieldGrid(Cell[][] fieldGrid) {
        this.fieldGrid = fieldGrid;
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public void setFieldSize(int fieldSize) {
        this.fieldSize = fieldSize;
    }

    public List<Ship> getShipList() {
        return shipList;
    }

    public void setShipList(List<Ship> shipList) {
        this.shipList = shipList;
    }


}
