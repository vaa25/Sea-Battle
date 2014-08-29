package app.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test Class for the Field model and included methods
 */
public class TestField {

    /**
     * Test of Field() Constructor
     */
    @Test
    public void testFieldConstructor() {
        /* ship list */
        List<ShipSize> ships = new ArrayList<ShipSize>();
        ships.add(ShipSize.FOUR);
        ships.add(ShipSize.THREE);
        ships.add(ShipSize.TWO);
        ships.add(ShipSize.ONE);
        List<Ship> shipList = new ArrayList<>();
        for (int i = 0; i < ships.size(); i++) {
            for (int j = 1; j <= i + 1; j++) {
                shipList.add(new Ship(ships.get(i)));
            }
        }

        /* ship grid */
        int size = 10;
        Cell[][] fieldGrid = new Cell[size][size];
        for (int i = 0; i < fieldGrid.length; i++) {
            for (int j = 0; j < fieldGrid.length; j++) {
                fieldGrid[i][j] = new Cell(i + 1, j + 1);
            }
        }
        Field field = new Field();

        // shipList
        assertEquals("LIST must be same", shipList, field.getShipList());
        // fieldGrid
        assertTrue("array must be the same", Arrays.deepEquals(fieldGrid, field.getFieldGrid()));
    }

    /**
     * Test getFieldGrid()
     */
    @Test
    public void testGetFieldGrid() {
        int size = 10;
        Cell[][] fieldGrid = new Cell[size][size];
        for (int i = 0; i < fieldGrid.length; i++) {
            for (int j = 0; j < fieldGrid.length; j++) {
                fieldGrid[i][j] = new Cell(i + 1, j + 1);
            }
        }

        /* assert with deepEquals*/
        Field field = new Field();
        assertTrue("array must be the same", Arrays.deepEquals(fieldGrid, field.getFieldGrid()));
    }

    /**
     * Test getFieldGrid()
     */
    @Test
    public void testSetFieldGrid() {
        int size = 10;
        Cell[][] fieldGrid = new Cell[size][size];
        for (int i = 0; i < fieldGrid.length; i++) {
            for (int j = 0; j < fieldGrid.length; j++) {
                fieldGrid[i][j] = new Cell(i + 1, j + 1);
            }
        }

        /* assert with deepEquals*/
        Field field = new Field();
        assertTrue("array must be the same", Arrays.deepEquals(fieldGrid, field.getFieldGrid()));
    }

    /**
     * Test getShipList()
     */
    @Test
    public void testGetShipList() {
        List<ShipSize> ships = new ArrayList<ShipSize>();
        ships.add(ShipSize.FOUR);
        ships.add(ShipSize.THREE);
        ships.add(ShipSize.TWO);
        ships.add(ShipSize.ONE);
        List<Ship> shipList = new ArrayList<>();
        for (int i = 0; i < ships.size(); i++) {
            for (int j = 1; j <= i + 1; j++) {
                shipList.add(new Ship(ships.get(i)));
            }
        }
        Field field = new Field();

        assertEquals("LIST must be same", shipList, field.getShipList());
    }

    /**
     * Test deployShipToField
     */
    public void testDeployShipToField() {
        Field field = new Field();

        assertTrue(field.deployShipToField(new Ship(ShipSize.FOUR, new Cell(1,1),
                ShipStatus.AVAILABLE, ShipDirection.VERTICAL)));

        assertFalse(field.deployShipToField(new Ship(ShipSize.FOUR, new Cell(1,1),
                ShipStatus.AVAILABLE, ShipDirection.VERTICAL)));

    }

    /** TODO
     * Test deleteShipFromField
     */
    public void testDeleteShipFromField() {}

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
        assertFalse("Second ship cant be placed", field.isShipSpotIsFree(ship2));
    }
}
