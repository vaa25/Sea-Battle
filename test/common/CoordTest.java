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
        coord2 = new Coord(2, 6);
    }

    @Test
    public void testGetX() throws Exception {

    }

    @Test
    public void testGetY() throws Exception {

    }

    @Test
    public void testGetLeftUp() throws Exception {

    }

    @Test
    public void testGetRightUp() throws Exception {

    }

    @Test
    public void testGetLeftDown() throws Exception {

    }

    @Test
    public void testGetRightDown() throws Exception {

    }

    @Test
    public void testGetLeft() throws Exception {

    }

    @Test
    public void testGetUp() throws Exception {

    }

    @Test
    public void testGetRight() throws Exception {

    }

    @Test
    public void testGetDown() throws Exception {

    }

    @Test
    public void testGetCenter() throws Exception {

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

    }

    @Test
    public void testToString() throws Exception {

    }
}