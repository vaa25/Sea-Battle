package app.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test Class for the Ship model and included methods
 */
public class TestShip {

    /**
     * Test of Ship(ShipSize shipSize) Constructor
     */
    @Test
    public void testShipConstructor_1() {
        ShipSize shipSize = ShipSize.FOUR;
        Ship ship = new Ship(shipSize);

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

        // shipSize
        assertEquals("SIZE must be the same", ship.getShipSizeFromCellsLength(cells), ship.getShipSize());
        // shipState
        assertNull("must be NULL", ship.getShipState());
        // shipStatus
        assertEquals("STATUS must be the same", ShipStatus.AVAILABLE, ship.getShipStatus());
        // cells
        assertEquals("Length must be the same", cells.length, ship.getCells().length);
    }

    /**
     * Test of Ship(Cell[] cells, ShipStatus status) Constructor
     */
    @Test
    public void testShipConstructor_3() {
        Cell[] cells = {new Cell(0, 1), new Cell(0, 2), new Cell(0, 3), new Cell(0, 4)};
        ShipStatus shipStatus = ShipStatus.BUSY;
        Ship ship = new Ship(cells, shipStatus);

        // shipSize
        assertEquals("SIZE must be the same", ship.getShipSizeFromCellsLength(cells), ship.getShipSize());
        // shipState
        assertEquals("STATE must be same", ShipState.LIVE, ship.getShipState());
        // shipStatus
        assertEquals("STATUS must be the same", shipStatus, ship.getShipStatus());
        // cells
        assertArrayEquals("Array must be the same", cells, ship.getCells());
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
     * Test isShipSpotIsFree
     */
    @Test
    public void testIsShipSpotIsFree() {
        // first ship
        Cell[] cells1 = {new Cell(1, 2, CellState.BUSY),
                new Cell(1, 3, CellState.BUSY),
                new Cell(1, 4, CellState.BUSY)};
        Ship ship1 = new Ship(cells1, ShipStatus.BUSY);
        // second ship
        Cell[] cells2 = {new Cell(0, 4, CellState.BUSY),
                new Cell(1, 4, CellState.BUSY),
                new Cell(2, 4, CellState.BUSY)};
        Ship ship2 = new Ship(cells2, ShipStatus.BUSY);
        // new field
        Field field = new Field();
        List<Ship> list = field.getShipList();

        list.add(ship1);

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
                ship.getShipSizeFromCellsLength(cells), ship.getShipSize());
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
