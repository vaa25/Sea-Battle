package app.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        ShipDirection direction = ShipDirection.HORISONTAL;
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
        ShipDirection direction = ShipDirection.HORISONTAL;
        // shipDirection
        assertEquals("DIRECTION must be the same", direction, ship.getShipDirection());
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
        ShipDirection direction = ShipDirection.VERTICAL;
        Ship ship = new Ship(cells, shipStatus, direction);
        // shipDirection
        assertEquals("DIRECTION must be the same", direction, ship.getShipDirection());
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
        Field field = new Field();
        Ship ship1 = new Ship(ShipSize.THREE);
        ship1.setShipCellsByPointing(new Cell(1,1));
        List<Ship> list = new ArrayList<>();
        list.add(ship1);
        field.setShipList(list);
        // target ship
        Ship ship2 = new Ship(ShipSize.THREE);
        ship2.setShipCellsByPointing(new Cell(1,2));
        ship2.setShipDirection(ShipDirection.VERTICAL);
        assertFalse("Second ship cant be placed", ship2.isShipSpotIsFree(field));
    }

    /**
     * Test  getCellsAroundShip()
     */
    @Test
    public void testGetCellsAroundShip() {
        Cell[] expectedArray1 = {
                new Cell(0, 0, CellState.BUSY), new Cell(1, 0, CellState.BUSY), new Cell(2, 0, CellState.BUSY),
                new Cell(0, 1, CellState.BUSY), new Cell(1, 1, CellState.BUSY), new Cell(2, 1, CellState.BUSY),
                new Cell(0, 2, CellState.BUSY), new Cell(1, 2, CellState.BUSY), new Cell(2, 2, CellState.BUSY),
                new Cell(0, 3, CellState.BUSY), new Cell(1, 3, CellState.BUSY), new Cell(2, 3, CellState.BUSY),
                new Cell(0, 4, CellState.BUSY), new Cell(1, 4, CellState.BUSY), new Cell(2, 4, CellState.BUSY),
                new Cell(0, 5, CellState.BUSY), new Cell(1, 5, CellState.BUSY), new Cell(2, 5, CellState.BUSY)};
        Ship ship1 = new Ship(ShipSize.FOUR);
        assertEquals("wrong ship direction", ShipDirection.HORISONTAL, ship1.getShipDirection());
        ship1.setShipCellsByPointing(new Cell(1, 1));
        assertTrue("Arrays must be same", Arrays.deepEquals(expectedArray1, ship1.getCellsAroundShip()));


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
        ship2.setShipCellsByPointing(new Cell(1, 1));
        assertTrue("Arrays must be same", Arrays.deepEquals(expectedArray2, ship2.getCellsAroundShip()));
    }

    /**
     * Test setShipCellsByPointing
     */
    @Test
    public void testSetShipCellsByPointing() {
        Cell pointingCell = new Cell(1, 2);
        Cell[] cells = new Cell[3];
        Ship ship = new Ship(cells, ShipStatus.AVAILABLE, ShipDirection.HORISONTAL);
        ship.setShipCellsByPointing(pointingCell);
        Cell[] expectedHorCells = {new Cell(1, 2), new Cell(1, 3), new Cell(1, 4)};
        assertTrue("Array must be the same", Arrays.deepEquals(expectedHorCells, ship.getCells()));


        ship.setShipDirection(ShipDirection.VERTICAL);
        ship.setShipCellsByPointing(pointingCell);
        Cell[] expectedVertCells = {new Cell(1, 2), new Cell(2, 2), new Cell(3, 2)};
        assertTrue("Array must be the same", Arrays.deepEquals(expectedVertCells, ship.getCells()));
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
