package app.model;

import app.model.enums.*;
import app.service.ShipService;

import java.util.Arrays;

/**
 * Class represents model and service functions of the Ship
 */
public class Ship {

    private ShipDirection shipDirection;

    // starting cell
    private Cell leftUpCell;

    private ShipSize shipSize;

    private ShipState shipState;

    private ShipStatus shipStatus;

    private Cell[] cells;

    /**
     * Constructor_1 with specified ship size
     *
     * @param shipSize - the length of the ship
     */
    public Ship(ShipSize shipSize) {
        this.shipSize = shipSize;
        this.cells = new Cell[shipSize.getValue()];
        this.shipStatus = ShipStatus.AVAILABLE;
        this.shipDirection = ShipDirection.HORIZONTAL;
    }

    /**
     * Constructor_2 with specified array of coordinates
     *
     * @param cells - array with coordinates
     */
    public Ship(Cell[] cells) {
        ShipService shipService = new ShipService();
        this.shipSize = shipService.getShipSizeFromCellsLength(cells);
        this.cells = cells;
        this.shipStatus = ShipStatus.AVAILABLE;
        this.shipDirection = ShipDirection.HORIZONTAL;
    }

    /**
     * Constructor_3 with specified array of coordinates and ship status
     *
     * @param cells  - array with coordinates
     * @param status - status of the ship
     */
    public Ship(Cell[] cells, ShipStatus status, ShipDirection shipDirection) {
        ShipService shipService = new ShipService();
        this.shipSize = shipService.getShipSizeFromCellsLength(cells);
        this.cells = cells;
        this.shipStatus = status;
        this.shipState = shipService.initiateShipState(status);
        this.shipDirection = shipDirection;
    }

    /**
     * Constructor_4
     *
     * @param shipSize      - quantity of cells
     * @param leftUpCell    - pointed cell
     * @param status        - status of the ship
     * @param shipDirection - direction
     */
    public Ship(ShipSize shipSize, Cell leftUpCell, ShipStatus status, ShipDirection shipDirection) {
        ShipService shipService = new ShipService();
        this.leftUpCell = leftUpCell;
        this.shipSize = shipSize;
        this.cells = new Cell[shipSize.getValue()];
        this.shipDirection = shipDirection;
        this.cells = shipService.setShipCellsByPointing(leftUpCell, shipDirection, shipSize);
        this.shipStatus = status;
        this.shipState = new ShipService().initiateShipState(status);
    }

    /**
     * Return ship state if ship is BUSY (have been placed to the field)
     *
     * @return - ShipState
     */
    public ShipState getShipState() {
        if (shipStatus.equals(ShipStatus.BUSY)) {
            int count = 0;
            for (Cell cell : cells) if (cell.getCellState() == CellState.HIT) count++;

            if (count > 0 && count == cells.length) {
                shipState = ShipState.DESTROYED;
                return shipState;
            } else if (count > 0 && count < cells.length) {
                shipState = ShipState.WOUNDED;
                return shipState;
            } else {
                shipState = ShipState.LIVE;
                return shipState;
            }
        }
        return null;
    }

    public ShipDirection getShipDirection() {
        return shipDirection;
    }

    public void setShipDirection(ShipDirection shipDirection) {
        this.shipDirection = shipDirection;
    }

    public Cell getLeftUpCell() {
        return leftUpCell;
    }

    public void setLeftUpCell(Cell leftUpCell) {
        this.leftUpCell = leftUpCell;
    }

    public ShipSize getShipSize() {
        return shipSize;
    }

    public ShipStatus getShipStatus() {
        return shipStatus;
    }

    public void setShipStatus(ShipStatus shipStatus) {
        this.shipStatus = shipStatus;
    }

    public Cell[] getCells() {
        return cells;
    }

    public void setCells(Cell[] cells) {
        this.cells = cells;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ship)) {
            return false;
        }

        Ship ship = (Ship) o;

        if (!Arrays.equals(cells, ship.cells)) {
            return false;
        }
        if (leftUpCell != null ? !leftUpCell.equals(ship.leftUpCell) : ship.leftUpCell != null) {
            return false;
        }
        if (shipDirection != ship.shipDirection) {
            return false;
        }
        if (shipSize != ship.shipSize) {
            return false;
        }
        if (shipState != ship.shipState) {
            return false;
        }
        if (shipStatus != ship.shipStatus) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = shipDirection != null ? shipDirection.hashCode() : 0;
        result = 31 * result + (leftUpCell != null ? leftUpCell.hashCode() : 0);
        result = 31 * result + (shipSize != null ? shipSize.hashCode() : 0);
        result = 31 * result + (shipState != null ? shipState.hashCode() : 0);
        result = 31 * result + (shipStatus != null ? shipStatus.hashCode() : 0);
        result = 31 * result + (cells != null ? Arrays.hashCode(cells) : 0);
        return result;
    }
}