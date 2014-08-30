package app.model;

import java.io.Serializable;

/**
 * Enum for cell state
 */
enum CellState {
    NULL, BUSY, HIT, NO_HIT

}

/**
 * Class Cell represents a model of one cell with coordinates and current state
 */
public class Cell implements Serializable{

    private int x, y;

    private CellState cellState;

    /**
     * Constructor_1 with X, Y coordinates
     *
     * @param x - horizontal
     * @param y - vertical
     */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.cellState = CellState.NULL;
    }

    /**
     * Constructor_2 with X, Y coordinates
     *
     * @param x - vertical
     * @param y - horizontal
     * @param cellState - enum representation
     */
    public Cell(int x, int y, CellState cellState) {
        this.x = x;
        this.y = y;
        this.cellState = cellState;
    }

    public int getX() {
        return x;
    }

    /**
     * X can be only > 0 and < 10
     * @param x - coordinate
     */
    public void setX(int x) {
        if (x >= 0 && x <= 10) {
            this.x = x;
        }
    }

    public int getY() {
        return y;
    }

    /**
     * Y can be only > 0 and < 10
     * @param y - coordinate
     */
    public void setY(int y) {
        if (y >= 0 && y <= 10) {
            this.y = y;
        }
    }

    public CellState getCellState() {
        return cellState;
    }

    public void setCellState(CellState cellState) {
        this.cellState = cellState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof Cell)) { return false; }

        Cell cell = (Cell) o;

        if (x != cell.x) { return false; }
        if (y != cell.y) { return false; }
        if (cellState != cell.cellState) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + (cellState != null ? cellState.hashCode() : 0);
        return result;
    }
}
