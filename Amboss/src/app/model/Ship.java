package app.model;

/**
 * Representation of ship health
 */
enum ShipState {
    LIVE, WOUNDED, DESTROYED
}

/**
 * Representation of ship status
 */
enum ShipStatus {
    AVAILABLE, BUSY
}

/**
 * Representation of ship size
 */
enum ShipSize {
    ONE(1), TWO(2), THREE(3), FOUR(4);

    private final int value;

    private ShipSize(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }
}

/**
 * Class represents model and service functions of the Ship
 */
public class Ship {

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
    }

    /**
     * Constructor_2 with specified array of coordinates
     *
     * @param cells - array with coordinates
     */
    public Ship(Cell[] cells) {
        this.shipSize = getShipSizeFromCellsLength(cells);
        this.cells = cells;
        this.shipStatus = ShipStatus.AVAILABLE;
    }

    /**
     * Constructor_3 with specified array of coordinates and ship status
     *
     * @param cells - array with coordinates
     * @param status - status of the ship
     */
    public Ship(Cell[] cells, ShipStatus status) {
        this.shipSize = getShipSizeFromCellsLength(cells);
        this.cells = cells;
        this.shipStatus = status;
        this.shipState = initiateShipState(status);
    }

    /**
     * Initiating Live status of ship if ship state is BUSY
     *
     * @param shipStatus - status of the ship
     * @return Ship State
     */
    public ShipState initiateShipState(ShipStatus shipStatus) {
        return shipStatus.equals(ShipStatus.BUSY)? ShipState.LIVE : null;
    }

    /**
     * Initiating Size of the ship by counting Cell array size
     *
     * @param cells - cells of the ship
     * @return ShipSize
     */
    public ShipSize getShipSizeFromCellsLength(Cell[] cells) {
        ShipSize shipSize = null;
        if (cells.length > 0 && cells.length < 5) {
            switch (cells.length) {
                case 4:
                    shipSize = ShipSize.FOUR;
                    break;
                case 3:
                    shipSize = ShipSize.THREE;
                    break;
                case 2:
                    shipSize = ShipSize.TWO;
                    break;
                case 1:
                    shipSize = ShipSize.ONE;
                    break;
                default:
                    shipSize = null;
            }
        }
        return shipSize;
    }

    /**
     * Return ship state if ship is BUSY (have been placed to the field)
     *
     * @return - ShipState
     */
    public ShipState getShipState() {
        if (shipStatus.equals(ShipStatus.BUSY)) {
            int count = 0;
            for (Cell cell : cells) {
                if (cell.getCellState() == CellState.HIT) count++;
            }

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
}