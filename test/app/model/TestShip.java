package app.model;

import app.model.enums.*;
import app.service.ShipService;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Test Class for the model.Ship model and included methods
 */
public class TestShip {

    /**
     * Test of Ship(ShipSize shipSize) Constructor
     */
    @Test
    public void testShipConstructor_1() {
        ShipSize shipSize = ShipSize.FOUR;
        Ship ship = new Ship(shipSize);
        ShipDirection direction = ShipDirection.HORIZONTAL;
        // shipDirection
        assertEquals("DIRECTION must be the same", direction, ship.getShipDirection());
        // shipSize
        assertEquals("SIZE must be the same", shipSize, ship.getShipSize());
        // shipState
        assertNull("must be NULL", ship.getShipState());
        // shipStatus
        assertEquals("STATUS must be the same", ShipStatus.AVAILABLE, ship.getShipStatus());
        // cells
        assertEquals("Length must be the same", shipSize.getValue(), ship.getCells().length);
    }

    /**
     * Test of Ship(Cell[] cells) Constructor
     */
    @Test
    public void testShipConstructor_2() {
        Cell[] cells = {new Cell(0, 1), new Cell(0, 2), new Cell(0, 3), new Cell(0, 4)};
        Ship ship = new Ship(cells);
        ShipDirection direction = ShipDirection.HORIZONTAL;
        // shipDirection
        assertEquals("DIRECTION must be the same", direction, ship.getShipDirection());
        // shipSize
        assertEquals("SIZE must be the same",
                new ShipService().getShipSizeFromCellsLength(cells), ship.getShipSize());
        // shipState
        assertNull("must be NULL", ship.getShipState());
        // shipStatus
        assertEquals("STATUS must be the same", ShipStatus.AVAILABLE, ship.getShipStatus());
        // cells
        assertEquals("Length must be the same", cells.length, ship.getCells().length);
    }

    /**
     * Test of Ship(Cell[] cells, ShipStatus status, ShipDirection shipDirection) Constructor
     */
    @Test
    public void testShipConstructor_3() {
        Cell[] cells = {new Cell(0, 1), new Cell(0, 2), new Cell(0, 3), new Cell(0, 4)};
        ShipStatus shipStatus = ShipStatus.BUSY;
        ShipDirection direction = ShipDirection.VERTICAL;
        Ship ship = new Ship(cells, shipStatus, direction);
        // shipDirection
        assertEquals("DIRECTION must be the same", direction, ship.getShipDirection());
        // shipSize
        assertEquals("SIZE must be the same",
                new ShipService().getShipSizeFromCellsLength(cells), ship.getShipSize());
        // shipState
        assertEquals("STATE must be same", ShipState.LIVE, ship.getShipState());
        // shipStatus
        assertEquals("STATUS must be the same", shipStatus, ship.getShipStatus());
        // cells
        assertArrayEquals("Array must be the same", cells, ship.getCells());
    }

    /**
     * Test of Ship(ShipSize shipSize, Cell leftUpCell, ShipStatus status,
     * ShipDirection shipDirection) Constructor
     */
    @Test
    public void testShipConstructor_4() {
        Cell[] expectedCells = {
                new Cell(1, 1, CellState.BUSY),
                new Cell(2, 1, CellState.BUSY),
                new Cell(3, 1, CellState.BUSY),
                new Cell(4, 1, CellState.BUSY)};
        Ship ship = new Ship(ShipSize.FOUR, new Cell(1, 1), ShipStatus.AVAILABLE, ShipDirection.VERTICAL);

        // shipDirection
        assertEquals("DIRECTION must be the same", ShipDirection.VERTICAL, ship.getShipDirection());
        // shipSize
        assertEquals("SIZE must be the same", ShipSize.FOUR, ship.getShipSize());
        // shipState
        assertEquals("STATE must be same", null, ship.getShipState());
        // shipStatus
        assertEquals("STATUS must be the same", ShipStatus.AVAILABLE, ship.getShipStatus());
        // cells
        assertTrue("Array must be the same", Arrays.deepEquals(expectedCells, ship.getCells()));
    }

    /**
     * Test getShipState()
     */
    @Test
    public void testGetShipState() {
        Cell[] cells = {new Cell(0, 1), new Cell(0, 2), new Cell(0, 3), new Cell(0, 4)};
        Ship ship = new Ship(cells);
        ship.setShipStatus(ShipStatus.BUSY);
        assertEquals("STATE must be the same", ShipState.LIVE, ship.getShipState());
    }

    /**
     * Test getShipSize()
     */
    @Test
    public void testGetShipSize() {
        Cell[] cells = {new Cell(0, 1), new Cell(0, 2), new Cell(0, 3), new Cell(0, 4)};
        Ship ship = new Ship(cells);
        ship.setShipStatus(ShipStatus.BUSY);
        assertEquals("STATE must be the same",
                new ShipService().getShipSizeFromCellsLength(cells), ship.getShipSize());
    }

    /**
     * Test getShipStatus()
     */
    @Test
    public void testGetShipStatus() {
        Cell[] cells = {new Cell(0, 1), new Cell(0, 2), new Cell(0, 3), new Cell(0, 4)};
        Ship ship = new Ship(cells);
        assertEquals("STATUS must be the same", ShipStatus.AVAILABLE, ship.getShipStatus());
    }

    /**
     * Test setShipStatus()
     */
    @Test
    public void testSetShipStatus() {
        Cell[] cells = {new Cell(0, 1), new Cell(0, 2), new Cell(0, 3), new Cell(0, 4)};
        Ship ship = new Ship(cells);
        ShipStatus newShipStatus = ShipStatus.BUSY;
        ship.setShipStatus(newShipStatus);
        assertEquals("STATUS must be the same", newShipStatus, ship.getShipStatus());
    }

    /**
     * Test getCells()
     */
    @Test
    public void testGetCells() {
        Cell[] cells = {new Cell(0, 1), new Cell(0, 2), new Cell(0, 3), new Cell(0, 4)};
        Ship ship = new Ship(cells);
        assertTrue("Cells array must be the same", Arrays.equals(cells, ship.getCells()));
    }

    /**
     * Test setCells()
     */
    @Test
    public void testSetCells() {
        Cell[] cells = {new Cell(0, 1), new Cell(0, 2), new Cell(0, 3), new Cell(0, 4)};
        Ship ship = new Ship(cells);
        Cell[] newCells = {new Cell(1, 1), new Cell(1, 2), new Cell(1, 3), new Cell(1, 4)};
        ship.setCells(newCells);
        assertTrue("Cells array must be the same", Arrays.equals(newCells, ship.getCells()));
    }

    /**
     * Test Equals()
     */
    @Test
    public void testEquals() {
        Ship ship1 = new Ship(ShipSize.FOUR);
        Ship ship2 = new Ship(ShipSize.FOUR);
        assertEquals("ships must be the same", ship1, ship2);
    }

    /**
     * Test HashCode()
     */
    @Test
    public void testHashCode() {
        assertNotEquals("HashCode cant be the same", new Ship(ShipSize.FOUR).hashCode(),
                new Ship(ShipSize.TWO).hashCode());
    }
}