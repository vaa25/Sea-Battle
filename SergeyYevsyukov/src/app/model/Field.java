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
     * Deploy ship to field shipList
     *
     * @param ship - target ship to be placed
     */
    public boolean deployShipToField(Ship ship) {
        if (ship.getCells() != null) {
            for (Ship scannedShip : shipList) {
                if (scannedShip.getShipSize() == ship.getShipSize() &&
                        scannedShip.getShipStatus().equals(ShipStatus.AVAILABLE)) {
                    if (isShipSpotIsFree(ship)) {
                        scannedShip = ship;
                        scannedShip.setShipStatus(ShipStatus.BUSY);
                        return true;
                    }
                }
            }
        }
        return false;
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
            if (c.getCellState() == CellState.HIT) count++;
        }
        if (count == ship.getCells().length) shipList.remove(ship);
    }

    /**
     * verifying that cells, where ship to be placed, are free
     *
     * @return boolean
     */
    public boolean isShipSpotIsFree(Ship ship) {
        if (ship.getCells().length > 0) {
            Cell[] shipPlusAura = ship.getCellsAroundShip();
            for (Ship shipFromList : shipList) {
                for (Cell shipsCell : shipFromList.getCells()) {
                    for (Cell thisShipCell : shipPlusAura) {
                        System.out.println();
                        if (shipsCell.equals(thisShipCell)) return false;
                    }
                }
            }
        }
        return true;
    }

    public List<Ship> getAvailableShipList() {
        List<Ship> list = new ArrayList<>();
        for (Ship ship : shipList) {
            if (ship.getShipStatus().equals(ShipStatus.AVAILABLE)) list.add(ship);
        }
        return list;
    }
}
