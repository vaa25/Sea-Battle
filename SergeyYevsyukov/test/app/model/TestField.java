package app.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
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

    /** TODO
     * Test deployShipToField
     */
    public void testDeployShipToField() {}

    /** TODO
     * Test deleteShipFromField
     */
    public void testDeleteShipFromField() {}
}
