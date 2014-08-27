package app.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    // def. constructor
    public Field() {
        shipList = new ArrayList<>();
        setShipList();
        fieldGrid = new Cell[FIELD_SIZE][FIELD_SIZE];
        setFieldGrid();
    }

    public Cell[][] getFieldGrid() {
        return fieldGrid;
    }

    public void setFieldGrid() {
        Cell[][] cells = new Cell[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < fieldGrid.length; i++) {
            for (int j = 0; j < fieldGrid.length; j++) {
                fieldGrid[i][j] = new Cell(i + 1, j + 1);
            }
        }
    }

    public List<Ship> getShipList() {
        return shipList;
    }

    /**
     * Creating Ship list with quantity-size specification
     */
    public void setShipList() {
        List<ShipSize> shipSizes = Arrays.asList(ShipSize.values());
        Collections.reverse(shipSizes);
        for (int i = 0; i < shipSizes.size(); i++) {
            for (int j = 1; j <= i + 1; j++) {
                shipList.add(new Ship(shipSizes.get(i)));
            }
        }
    }
}
