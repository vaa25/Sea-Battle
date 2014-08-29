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

    // constructor
    public Field() {
        shipList = new ArrayList<>();
        initiateShipList();
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

    /**
     * Creating Ship list with quantity-size specification
     */
    public void initiateShipList() {
        List<ShipSize> shipSizes = Arrays.asList(ShipSize.values());
        Collections.reverse(shipSizes);
        for (int i = 0; i < shipSizes.size(); i++) {
            for (int j = 1; j <= i + 1; j++) {
                shipList.add(new Ship(shipSizes.get(i)));
            }
        }
    }

    /**
     * TODO * test
     * Deploy ship to field shipList
     *
     * @param ship - target ship to be placed
     */
    public void deployShipToField(Ship ship) {
        if (ship.getCells() != null) {
            for (Ship scannedShip : shipList) {
                if (scannedShip.getShipSize() == ship.getShipSize() &&
                        scannedShip.getShipStatus().equals(ShipStatus.AVAILABLE)) {
                    scannedShip = new Ship(ship.getCells(), ShipStatus.BUSY, ShipDirection.HORISONTAL);
                }
            }
        }
    }

    /**
     * TODO * test
     * Deploy ship to field shipList
     *
     * @param ship - target ship to be placed
     */
    public void deleteShipFromField(Ship ship) {
        int count = 0;
        for (Cell c : ship.getCells()) {
            if(c.getCellState() == CellState.HIT) count++;
        }
        if (count == ship.getCells().length) shipList.remove(ship);
    }
}
