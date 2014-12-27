package model;

import common.Coord;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MyFieldTest extends TestCase {
    private MyField myField;
    private Ship ship1;
    private Ship ship2;
    private Ship ship3;
    private Ship ship4;

    @Before
    public void setUp() throws Exception {
        myField = new MyField(10, 10);
        ship4 = new Ship(4);
        ship3 = new Ship(3);
        ship2 = new Ship(2);
        ship1 = new Ship(1);
    }

    @Test
    public void testCanPlace() throws Exception {
        ship2.setCoords(new Coord(0, 0));
        List<Ship> ships = new ArrayList<>();
        ships.add(ship2);
        myField.setShips(ships);
        ship1.setCoords(new Coord(0, 0));
        assertFalse("ship1(" + ship1 + ") can be placed at same cell with ship2(" + ship2 + ")", myField.canPlace(ship1));
        ship1.setCoords(new Coord(0, -2));
        assertFalse("ship1(" + ship1 + ") can be placed out of bounds", myField.canPlace(ship1));
        ship1.setCoords(new Coord(1, 0));
        assertFalse("ship1(" + ship1 + ") can be placed at same cell with ship2(" + ship2 + ")", myField.canPlace(ship1));
        ship1.setCoords(new Coord(1, 1));
        assertFalse("ship1(" + ship1 + ") can be placed near ship2(" + ship2 + ")", myField.canPlace(ship1));
        ship1.setCoords(new Coord(0, 5));
        assertTrue("ship1(" + ship1 + ") can't be placed at free space", myField.canPlace(ship1));
    }


    @Test
    public void testUnPlace() throws Exception {

    }

    @Test
    public void testPlaceRandom() throws Exception {

    }

    @Test
    public void testPlace() throws Exception {

    }

    @Test
    public void testShoot() throws Exception {

    }

    @Test
    public void testGetShipSize() throws Exception {

    }

    @Test
    public void testUnPlaceAll() throws Exception {

    }

    @Test
    public void testGetAliveShips() throws Exception {

    }
}