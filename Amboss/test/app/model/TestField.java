package app.model;

import org.junit.Test;

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
                fieldGrid[i][j].setX(i);
                fieldGrid[i][j].setY(j);
            }
        }

    }

    /**
     * Test getFieldSize()
     */
    @Test
    public void testSetFieldGrid() {


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
