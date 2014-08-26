package app.model;

enum ShipState {
    LIVE, WOUNDED, DESTROYED;
}

/**
 * Class represents model and service functions of the Ship
 */
public class Ship {

    private ShipState shipState;

    private Cell[] cells;

    public Ship(Cell[] cells) {
        this.cells = cells;
        this.shipState = ShipState.LIVE;
    }

    public ShipState getShipState() {

        int count = 0;

        for (Cell cell : cells) {
            if (cell.getCellState() == CellState.HIT) count++;
        }

        if (count > 0 && count == cells.length) {
            return ShipState.DESTROYED;
        } else if (count > 0 && count < cells.length) {
            return ShipState.WOUNDED;
        } else {
            return ShipState.LIVE;
        }
    }

    public Cell[] getCells() {
        return cells;
    }

    public void setCells(Cell[] cells) {
        this.cells = cells;
    }

}