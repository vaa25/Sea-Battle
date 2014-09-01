package app.model;

import app.service.FieldService;

import java.util.List;

/**
 * Class represents model and service functions of the game field
 */
public class Field {

    // dimension of the field
    private final static int FIELD_SIZE = 10;

    // list of ships
    private List<Ship> shipList;

    // grid with coordinates for the field
    private Cell[][] fieldGrid;

    // constructor
    public Field() {
        shipList = new FieldService().initiateShipList();
        fieldGrid = new Cell[FIELD_SIZE][FIELD_SIZE];
        setFieldGrid();
    }

    public Cell[][] getFieldGrid() {
        return fieldGrid;
    }

    /**
     * Initiating field grid
     */
    public void setFieldGrid() {
        for (int i = 0; i < fieldGrid.length; i++) {
            for (int j = 0; j < fieldGrid.length; j++) {
                fieldGrid[i][j] = new Cell(i + 1, j + 1);
            }
        }
    }

    public List<Ship> getShipList() {
        return shipList;
    }

    public void setShipList(List<Ship> shipList) {
        this.shipList = shipList;
    }
}
