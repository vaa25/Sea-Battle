package common;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class CoordTest extends TestCase {
    Coord coord1;
    Coord coord3;
    Coord coord2;

    @Before
    public void setUp() throws Exception {
        coord1 = new Coord(3, 5);
        coord3 = new Coord(3, 5);
        coord2 = new Coord(2, 4);
    }

    @Test
    public void testGetX() throws Exception {
        assertEquals(3, coord1.getX());
        assertEquals(2, coord2.getX());
    }

    @Test
    public void testGetY() throws Exception {
        assertEquals(5, coord3.getY());
        assertEquals(4, coord2.getY());
    }

    @Test
    public void testGetLeftUp() throws Exception {
        assertEquals(new Coord(2, 4), coord1.getLeftUp());
    }

    @Test
    public void testGetRightUp() throws Exception {
        assertEquals(new Coord(4, 4), coord1.getRightUp());
    }

    @Test
    public void testGetLeftDown() throws Exception {
        assertEquals(new Coord(2, 6), coord1.getLeftDown());
    }

    @Test
    public void testGetRightDown() throws Exception {
        assertEquals(new Coord(4, 6), coord1.getRightDown());
    }

    @Test
    public void testGetLeft() throws Exception {
        assertEquals(new Coord(2, 5), coord1.getLeft());
    }

    @Test
    public void testGetUp() throws Exception {
        assertEquals(new Coord(3, 4), coord1.getUp());
    }

    @Test
    public void testGetRight() throws Exception {
        assertEquals(new Coord(4, 5), coord1.getRight());
    }

    @Test
    public void testGetDown() throws Exception {
        assertEquals(new Coord(3, 6), coord1.getDown());
    }

    @Test
    public void testGetCenter() throws Exception {
        assertEquals(new Coord(3, 5), coord1.getCenter());
    }

    @Test
    public void testEquals() throws Exception {
        assertEquals(coord1, coord3);
        assertNotSame(coord1, coord3);
        assertSame(coord1, coord1);
        assertFalse(coord1.equals(coord2));
        assertFalse(coord2.equals(coord1));
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(coord1.hashCode(), coord3.hashCode());
        assertFalse(coord1.hashCode() == coord2.hashCode());
    }

}