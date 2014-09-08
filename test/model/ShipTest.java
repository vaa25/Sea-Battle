package model;

import common.Coord;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class ShipTest extends TestCase {
    private Ship ship;
    private Ship ship2;
    private Ship ship3;
    Coord leftUp = new Coord(3, 5);

    @Before
    public void setUp() throws Exception {
        ship = new Ship(1);
        ship2 = new Ship(2);
        ship3 = new Ship(3);
    }

    @Test
    public void testSetPlaced() throws Exception {
        ship.setPlaced(true);
        assertTrue(ship.isPlaced());
        assertTrue(ship.isAlive());
        ship.setPlaced(false);
        assertFalse(ship.isPlaced());
        assertTrue(ship.isAlive());
    }

    @Test
    public void testIsPlaced() throws Exception {
        ship.setPlaced(true);
        assertTrue(ship.isPlaced());
        assertTrue(ship.isAlive());
        ship.setPlaced(false);
        assertFalse(ship.isPlaced());
        assertTrue(ship.isAlive());
    }

    @Test
    public void testCompareTo() throws Exception {
        assertTrue(ship.compareTo(ship2) < ship2.compareTo(ship));
        assertEquals(ship2.compareTo(ship2), ship.compareTo(ship));
        assertEquals(ship2.compareTo(ship), ship3.compareTo(ship2));
        assertTrue(ship2.compareTo(ship) < ship3.compareTo(ship));
        assertTrue(ship2.compareTo(ship) > ship.compareTo(ship3));
    }

    @Test
    public void testShoot() throws Exception {
        assertTrue(ship.isAlive());
        ship.shoot();
        assertFalse(ship.isAlive());
        ship.shoot();
        assertFalse(ship.isAlive());
        ship3.shoot();
        assertTrue(ship3.isAlive());
    }

    @Test
    public void testIsAlive() throws Exception {
        assertTrue(ship.isAlive());
        ship.shoot();
        assertFalse(ship.isAlive());
        ship.shoot();
        assertFalse(ship.isAlive());
        ship3.shoot();
        assertTrue(ship3.isAlive());
    }

    @Test
    public void testSetCoords() throws Exception {
        Coord leftUp = new Coord(3, 5);
        Coord[] coordsH = {
                new Coord(3, 5),
                new Coord(4, 5),
                new Coord(5, 5),
        };
        ship3.setCoords(leftUp);
        assertEquals(coordsH, ship3.getShipCoords());
        coordsH[0] = coordsH[0].getLeft();
        assertFalse(coordsH.equals(ship3.getShipCoords()));
    }

    @Test
    public void testGetShipCoords() throws Exception {
        Coord[] coordsH = {
                new Coord(3, 5),
                new Coord(4, 5),
                new Coord(5, 5),
        };

        ship3.setCoords(leftUp);
        assertTrue(Arrays.equals(coordsH, ship3.getShipCoords()));
        coordsH[0] = coordsH[0].getLeft();
        assertFalse(Arrays.equals(coordsH, ship3.getShipCoords()));
    }

    @Test
    public void testChangeDirection() throws Exception {
        Coord[] coordsH = {
                new Coord(3, 5),
                new Coord(4, 5),
                new Coord(5, 5),
        };
        Coord[] coordsV = {
                new Coord(3, 5),
                new Coord(3, 6),
                new Coord(3, 7),
        };
        ship3.setCoords(leftUp);
        assertTrue(Arrays.equals(coordsH, ship3.getShipCoords()));
        assertFalse(Arrays.equals(coordsV, ship3.getShipCoords()));
        ship3.changeDirection();
        assertFalse(Arrays.equals(coordsH, ship3.getShipCoords()));
        assertTrue(Arrays.equals(coordsV, ship3.getShipCoords()));
    }

    @Test
    public void testGetAroundCoords() throws Exception {
        Coord[] coordsH = {
                new Coord(3, 5),
                new Coord(4, 5),
        };
        Coord[] coordsHA = {
                new Coord(2, 4),
                new Coord(2, 5),
                new Coord(2, 6),
                new Coord(5, 4),
                new Coord(5, 5),
                new Coord(5, 6),
                new Coord(3, 4),
                new Coord(4, 4),
                new Coord(3, 5),
                new Coord(4, 5),
                new Coord(3, 6),
                new Coord(4, 6),
        };

        ship2.setCoords(leftUp);
        assertTrue(Arrays.equals(coordsHA, ship2.getAroundCoords()));
        ship2.changeDirection();
        assertFalse(Arrays.equals(coordsH, ship2.getAroundCoords()));
        assertFalse(Arrays.equals(coordsHA, ship2.getAroundCoords()));
    }

    @Test
    public void testIsCrossing() throws Exception {

        ship3.setCoords(leftUp);
        ship.setCoords(leftUp.getLeftUp());
        ship2.setCoords(leftUp.getRight());
        assertTrue(ship3.isCrossing(ship));
        assertTrue(ship.isCrossing(ship3));
        assertTrue(ship3.isCrossing(ship2));
        assertFalse(ship2.isCrossing(ship));
    }

    @Test
    public void testToString() throws Exception {

    }

    @Test
    public void testEquals() throws Exception {
        Ship ship1 = new Ship(1);
        assertTrue(ship1.equals(ship));
        assertTrue(ship.equals(ship1));
        assertTrue(ship.equals(ship));
        assertFalse(ship.equals(ship2));
    }

    @Test
    public void testHashCode() throws Exception {
        ship.setCoords(leftUp);
        Ship ship1 = new Ship(1);
        ship1.setCoords(leftUp.getLeftUp());
        assertFalse(ship1.hashCode() == ship.hashCode());
        ship1.setCoords(leftUp);
        assertEquals(ship1.hashCode(), ship.hashCode());
    }
}