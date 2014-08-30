package app.service;

import app.model.Cell;
import app.model.Ship;
import app.model.enums.CellState;
import app.model.enums.ShipDirection;
import app.model.enums.ShipSize;
import app.model.enums.ShipStatus;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Class
*/
public class TestShipService {

    /**
     * Test  getCellsAroundShip()
     */
    @Test
    public void testGetCellsAroundShip() {
        ShipService shipService = new ShipService();
        Cell[] expectedArray1 = {
                new Cell(0, 0, CellState.BUSY), new Cell(1, 0, CellState.BUSY), new Cell(2, 0, CellState.BUSY),
                new Cell(0, 1, CellState.BUSY), new Cell(1, 1, CellState.BUSY), new Cell(2, 1, CellState.BUSY),
                new Cell(0, 2, CellState.BUSY), new Cell(1, 2, CellState.BUSY), new Cell(2, 2, CellState.BUSY),
                new Cell(0, 3, CellState.BUSY), new Cell(1, 3, CellState.BUSY), new Cell(2, 3, CellState.BUSY),
                new Cell(0, 4, CellState.BUSY), new Cell(1, 4, CellState.BUSY), new Cell(2, 4, CellState.BUSY),
                new Cell(0, 5, CellState.BUSY), new Cell(1, 5, CellState.BUSY), new Cell(2, 5, CellState.BUSY)};
        Ship ship1 = new Ship(ShipSize.FOUR);

        assertEquals("wrong ship direction", ShipDirection.HORIZONTAL, ship1.getShipDirection());
        ship1.setCells(shipService.setShipCellsByPointing(
                new Cell(1, 1), ship1.getShipDirection(), ship1.getShipSize()));
        assertTrue("Arrays must be same", Arrays.deepEquals(expectedArray1, shipService.getCellsAroundShip(ship1)));

        Cell[] expectedArray2 = {
                new Cell(0, 0, CellState.BUSY), new Cell(0, 1, CellState.BUSY), new Cell(0, 2, CellState.BUSY),
                new Cell(1, 0, CellState.BUSY), new Cell(1, 1, CellState.BUSY), new Cell(1, 2, CellState.BUSY),
                new Cell(2, 0, CellState.BUSY), new Cell(2, 1, CellState.BUSY), new Cell(2, 2, CellState.BUSY),
                new Cell(3, 0, CellState.BUSY), new Cell(3, 1, CellState.BUSY), new Cell(3, 2, CellState.BUSY),
                new Cell(4, 0, CellState.BUSY), new Cell(4, 1, CellState.BUSY), new Cell(4, 2, CellState.BUSY),
                new Cell(5, 0, CellState.BUSY), new Cell(5, 1, CellState.BUSY), new Cell(5, 2, CellState.BUSY)};
        Ship ship2 = new Ship(ShipSize.FOUR);
        ship2.setShipDirection(ShipDirection.VERTICAL);
        assertEquals("wrong ship direction", ShipDirection.VERTICAL, ship2.getShipDirection());
        ship2.setCells(shipService.setShipCellsByPointing(
                new Cell(1, 1), ship2.getShipDirection(), ship2.getShipSize()));
        assertTrue("Arrays must be same", Arrays.deepEquals(expectedArray2, shipService.getCellsAroundShip(ship2)));
    }

    /**
     * Test setShipCellsByPointing
     */
    @Test
    public void testSetShipCellsByPointing() {
        ShipService shipService = new ShipService();
        Cell pointingCell = new Cell(1, 2);
        Cell[] cells = new Cell[3];
        Ship ship = new Ship(cells, ShipStatus.AVAILABLE, ShipDirection.HORIZONTAL);
        ship.setCells(shipService.setShipCellsByPointing(
                pointingCell, ship.getShipDirection(), ship.getShipSize()));
        Cell[] expectedHorCells = {new Cell(1, 2, CellState.BUSY),
                new Cell(1, 3, CellState.BUSY),
                new Cell(1, 4, CellState.BUSY)};
        assertTrue("Array must be the same", Arrays.deepEquals(expectedHorCells, ship.getCells()));

        ship.setShipDirection(ShipDirection.VERTICAL);
        ship.setCells(shipService.setShipCellsByPointing(
                pointingCell, ship.getShipDirection(), ship.getShipSize()));
        Cell[] expectedVertCells = {new Cell(1, 2, CellState.BUSY),
                new Cell(2, 2, CellState.BUSY),
                new Cell(3, 2, CellState.BUSY)};
        assertTrue("Array must be the same", Arrays.deepEquals(expectedVertCells, ship.getCells()));
    }
}
