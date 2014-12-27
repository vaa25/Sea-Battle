package model;

import common.Coord;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class CellTest extends TestCase {
    Cell cell;
    Ship ship;
    Ship ship2;
    Coord coord;
    @Before
    public void setUp() throws Exception {
        coord=new Coord(3,5);
        cell = new Cell();
        ship=new Ship(2);
        ship2=new Ship(1);
    }

    @Test
    public void testGetShip() throws Exception {
        cell.setShip(ship);
        assertEquals(ship,cell.getShip());
    }

    @Test
    public void testSetShip() throws Exception {
        cell.setShip(ship);
        assertEquals(ship,cell.getShip());
        assertSame(ship,cell.getShip());
    }

    @Test
    public void testIsShoot() throws Exception {
        cell.setShooted(true);
        assertTrue(cell.isShooted());
        assertFalse(cell.isHurt());
        cell.setShooted(false);
        assertFalse(cell.isShooted());
        assertFalse(cell.isHurt());
    }

    @Test
    public void testSetShoot() throws Exception {
        cell.setShooted(true);
        assertTrue(cell.isShooted());
        assertFalse(cell.isHurt());
        cell.setShooted(false);
        assertFalse(cell.isShooted());
        assertFalse(cell.isHurt());
    }

    @Test
    public void testIsHurt() throws Exception {
        cell.setHurt(true);
        assertFalse(cell.isShooted());
        assertTrue(cell.isHurt());
        cell.setHurt(false);
        assertFalse(cell.isShooted());
        assertFalse(cell.isHurt());
    }

    @Test
    public void testSetHurt() throws Exception {
        cell.setHurt(true);
        assertFalse(cell.isShooted());
        assertTrue(cell.isHurt());
        cell.setHurt(false);
        assertFalse(cell.isShooted());
        assertFalse(cell.isHurt());
    }
}