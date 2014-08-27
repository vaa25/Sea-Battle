package app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents model and service functions of the game field
 */
public class Field {

    // dimension of the field
    private int fieldSize;

    // list of ships
    private List<Ship> shipList;

    // grid with coordinates for the field
    private Cell[][] fieldGrid;

    // def. constructor
    public Field() {
        fieldSize = 10;
        fieldGrid = new Cell[fieldSize][fieldSize];
        shipList = new ArrayList<>();
        setFieldGrid();
    }

    public Cell[][] getFieldGrid() {
        return fieldGrid;
    }

    public void setFieldGrid() {
        this.fieldGrid = new Cell[fieldSize][fieldSize];
        for (int i = 0; i < fieldGrid.length; i++) {
            for (int j = 0; j < fieldGrid.length; j++) {
                fieldGrid[i][j] = new Cell(i + 1, j + 1);
            }
        }
    }

    public int getFieldSize() {
        return fieldSize;
    }

//    public void setFieldSize(int fieldSize) {
//        this.fieldSize = fieldSize;
//    }

    public List<Ship> getShipList() {
        return shipList;
    }

//    public void setShipList(model.Ship[] shipList) {
//        this.shipList = shipList;
//    }

    public void initiateShipList() {

        int[] sizes = {4, 3, 2, 1};

        for (int i = 0; i < sizes.length; i++) {
            for (int j: sizes) {
                //shipList.add(new model.Ship())
            }
        }

    }
}
