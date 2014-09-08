package model;

import common.Coord;

public class CellTest {

    @org.junit.Before
    public void setUp() throws Exception {
        Coord coord = new Coord(3, 5);
        Cell cell = new Cell(coord);
    }

    @org.junit.Test
    public void testGetShip() throws Exception {

    }

    @org.junit.Test
    public void testSetShip() throws Exception {
        Ship ship = new Ship(2);
    }

    @org.junit.Test
    public void testIsShoot() throws Exception {

    }

    @org.junit.Test
    public void testSetShoot() throws Exception {

    }

    @org.junit.Test
    public void testIsHurt() throws Exception {

    }

    @org.junit.Test
    public void testSetHurt() throws Exception {

    }
}