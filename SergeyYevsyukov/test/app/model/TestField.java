package app.model;

import app.model.enums.ShipDirection;
import app.model.enums.ShipSize;
import app.model.enums.ShipStatus;
import app.service.FieldService;
import app.service.ShipService;
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
        FieldService service = new FieldService();
        assertTrue(service.deployShipToField(field, new Ship(ShipSize.FOUR, new Cell(1,1),
                ShipStatus.AVAILABLE, ShipDirection.VERTICAL)));

        assertFalse(service.deployShipToField(field, new Ship(ShipSize.FOUR, new Cell(1,1),
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
        ShipService shipService = new ShipService();
        // first ship
        Field field = new Field();
        Ship ship1 = new Ship(ShipSize.THREE);
        ship1.setCells(shipService.setShipCellsByPointing(
                new Cell(1,1), ship1.getShipDirection(), ship1.getShipSize()));
        List<Ship> list = new ArrayList<>();
        list.add(ship1);
        field.setShipList(list);

        // target ship
        Ship ship2 = new Ship(ShipSize.THREE);
        ship2.setShipDirection(ShipDirection.VERTICAL);
        ship2.setCells(shipService.setShipCellsByPointing(
                new Cell(1,2), ship2.getShipDirection(), ship2.getShipSize()));
        assertFalse("Second ship cant be placed", new FieldService().isShipSpotIsFree(field, ship2));
    }
}
