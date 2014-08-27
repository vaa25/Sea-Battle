package app.model;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test Class for the Field model and included methods
 */
public class TestField {

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
                //System.out.println();
            }
        }

        /* assert with deepEquals*/
        Field field = new Field();
        assertTrue("array must be the same", Arrays.deepEquals(fieldGrid, field.getFieldGrid()));
    }

    /**
     * Test getFieldSize()
     */
    @Test
    public void testSetFieldGrid() {
        Field field = new Field();
        assertEquals("default size must be: 10", (Object)10, field.getFieldSize());
    }

    /**
     * Test getShipState()
     */
    @Test
    public void setFieldSize() {

    }

    /**
     * Test getShipState()
     */
    @Test
    public void getShipList() {

    }

    /**
     * Test setShipList()
     */
    @Test
    public void testGetShipList() {

    }

}
