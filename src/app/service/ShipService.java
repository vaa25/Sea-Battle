package app.service;

import app.model.Cell;
import app.model.Ship;
import app.model.enums.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Class represents functionality of Ship class
*/
public class ShipService {

    /**
     * Initiating Live status of ship if ship state is BUSY
     *
     * @param shipStatus - status of the ship
     * @return Ship State
     */
    public ShipState initiateShipState(ShipStatus shipStatus) {

        if (shipStatus == null) { return null; }
        return shipStatus.equals(ShipStatus.BUSY) ? ShipState.LIVE : null;
    }

    /**
     * Initiating Size of the ship by counting Cell array size
     *
     * @param cells - cells of the ship
     * @return ShipSize
     */
    public ShipSize getShipSizeFromCellsLength(Cell[] cells) {

        if (cells.equals(null)) { return null; }

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
     * Calculate cells around ship
     *
     * @return Cell[]
     */
    public Cell[] getCellsAroundShip(Ship ship) {

        if (ship.equals(null)) { return null; }

        // size off array
        List<Cell> list = new LinkedList<>();

        ShipDirection shipDirection = ship.getShipDirection();

        Cell[] cells = ship.getCells();

        if (shipDirection != null) {
            if (shipDirection.equals(ShipDirection.HORIZONTAL)) {

                list.add(new Cell(cells[0].getX() - 1, cells[0].getY() - 1, CellState.BUSY));
                list.add(new Cell(cells[0].getX(), cells[0].getY() - 1, CellState.BUSY));
                list.add(new Cell(cells[0].getX() + 1, cells[0].getY() - 1, CellState.BUSY));
                for (Cell cell : cells) {
                    list.add(new Cell(cell.getX() - 1, cell.getY(), CellState.BUSY));
                    list.add(new Cell(cell.getX(), cell.getY(), CellState.BUSY));
                    list.add(new Cell(cell.getX() + 1, cell.getY(), CellState.BUSY));
                }
                list.add(new Cell(cells[cells.length - 1].getX() - 1, cells[cells.length - 1].getY() + 1, CellState.BUSY));
                list.add(new Cell(cells[cells.length - 1].getX(), cells[cells.length - 1].getY() + 1, CellState.BUSY));
                list.add(new Cell(cells[cells.length - 1].getX() + 1, cells[cells.length - 1].getY() + 1, CellState.BUSY));
            } else {
                list.add(new Cell(cells[0].getX() - 1, cells[0].getY() - 1, CellState.BUSY));
                list.add(new Cell(cells[0].getX() - 1, cells[0].getY(), CellState.BUSY));
                list.add(new Cell(cells[0].getX() - 1, cells[0].getY() + 1, CellState.BUSY));
                for (Cell cell : cells) {
                    list.add(new Cell(cell.getX(), cell.getY() - 1, CellState.BUSY));
                    list.add(new Cell(cell.getX(), cell.getY(), CellState.BUSY));
                    list.add(new Cell(cell.getX(), cell.getY() + 1, CellState.BUSY));
                }
                list.add(new Cell(cells[cells.length - 1].getX() + 1, cells[cells.length - 1].getY() - 1, CellState.BUSY));
                list.add(new Cell(cells[cells.length - 1].getX() + 1, cells[cells.length - 1].getY(), CellState.BUSY));
                list.add(new Cell(cells[cells.length - 1].getX() + 1, cells[cells.length - 1].getY() + 1, CellState.BUSY));
            }
        }
        return list.toArray(new Cell[list.size()]);
    }

    /**
     * Building ship cells by pointing LeftUpCell
     * ShipCells array must be initiated!
     * <p/>
     * X (left to right) , Y (up to down)
     *
     * @param leftUpCell - pointing cell
     */
    public Cell[] setShipCellsByPointing(Cell leftUpCell, ShipDirection shipDirection, ShipSize shipSize) {

        if (leftUpCell.equals(null) || shipDirection.equals(null) || shipSize.equals(null)) {
            return null;
        }
        Cell[] cells = new Cell[shipSize.getValue()];

        if (cells.length > 0 && cells.length <= 4) {

            int x = leftUpCell.getX();
            int y = leftUpCell.getY();
            cells[0] = leftUpCell;
            cells[0].setCellState(CellState.BUSY);

            for (int i = 1; i < cells.length; i++) {
                if (shipDirection.equals(ShipDirection.HORIZONTAL)) {
                    cells[i] = new Cell(x, ++y, CellState.BUSY);
                } else {
                    cells[i] = new Cell(++x, y, CellState.BUSY);
                }
            }
        }
        return cells;
    }
}
