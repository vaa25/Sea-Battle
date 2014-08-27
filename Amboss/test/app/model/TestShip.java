package app.model;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test Class for the Ship model and included methods
 */
public class TestShip {

    /**
     * Test getShipState()
     */
    @Test
    public void testGetShipState() {
        Cell[] cells = {new Cell(0, 1), new Cell(0, 2), new Cell(0, 3), new Cell(0, 4),};
        Ship ship = new Ship(cells);
        ship.setShipStatus(ShipStatus.BUSY);
        assertEquals("STATE must be the same", ShipState.LIVE, ship.getShipState());
    }

    /**
     * Test getShipStatus()
     */
    @Test
    public void testGetShipStatus() {
        Cell[] cells = {new Cell(0, 1), new Cell(0, 2), new Cell(0, 3), new Cell(0, 4),};
        Ship ship = new Ship(cells);
        assertEquals("STATUS must be the same", ShipStatus.AVAILABLE, ship.getShipStatus());
    }

    /**
     * Test setShipStatus()
     */
    @Test
    public void testSetShipStatus() {
        Cell[] cells = {new Cell(0, 1), new Cell(0, 2), new Cell(0, 3), new Cell(0, 4),};
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
        Cell[] cells = {new Cell(0, 1), new Cell(0, 2), new Cell(0, 3), new Cell(0, 4),};
        Ship ship = new Ship(cells);
        assertTrue("Cells array must be the same", Arrays.equals(cells, ship.getCells()));
    }

    /**
     * Test setCells()
     */
    @Test
    public void testSetCells() {
        Cell[] cells = {new Cell(0, 1), new Cell(0, 2), new Cell(0, 3), new Cell(0, 4),};
        Ship ship = new Ship(cells);
        Cell[] newCells = {new Cell(1, 1), new Cell(1, 2), new Cell(1, 3), new Cell(1, 4),};
        ship.setCells(newCells);
        assertTrue("Cells array must be the same", Arrays.equals(newCells, ship.getCells()));
    }


}
