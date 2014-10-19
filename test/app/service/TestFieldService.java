package app.service;

import app.model.Cell;
import app.model.Field;
import app.model.Ship;
import app.model.enums.ShipDirection;
import app.model.enums.ShipSize;
import app.model.enums.ShipStatus;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Class
 */
public class TestFieldService {

    /**
     * Test deployShipToField
     */
    @Test
    public void testInitiateShipList() {
        List<Ship> expectedList = new ArrayList<>();
        expectedList.add(new Ship(ShipSize.FOUR));
        expectedList.add(new Ship(ShipSize.THREE));
        expectedList.add(new Ship(ShipSize.THREE));
        for (int i = 0; i <= 2; i++) {
            expectedList.add(new Ship(ShipSize.TWO));
        }
        for (int i = 0; i <= 3; i++) {
            expectedList.add(new Ship(ShipSize.ONE));
        }
        assertTrue("List must be same", expectedList.equals(new FieldService().initiateShipList()));

    }

    /**
     * Test deployShipToField
     */
    @Test
    public void testDeployShipToField() {
        Field field = new Field();
        FieldService service = new FieldService();

        assertTrue(service.deployShipToField(field, new Ship(ShipSize.FOUR, new Cell(1, 1),
                ShipStatus.AVAILABLE, ShipDirection.VERTICAL)));
    }

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
                new Cell(1, 1), ship1.getShipDirection(), ship1.getShipSize()));
        List<Ship> list = new ArrayList<>();
        list.add(ship1);
        field.setShipList(list);

        // target ship
        Ship ship2 = new Ship(ShipSize.THREE);
        ship2.setShipDirection(ShipDirection.VERTICAL);
        ship2.setCells(shipService.setShipCellsByPointing(
                new Cell(1, 2), ship2.getShipDirection(), ship2.getShipSize()));
        assertFalse("Second ship cant be placed", new FieldService().isShipSpotIsFree(field, ship2));
    }
}
