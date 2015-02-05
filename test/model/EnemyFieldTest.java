package model;

import common.Coord;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class EnemyFieldTest extends TestCase {

    private EnemyField enemyField;
    private Coord coord;

    @Before
    public void setUp() throws Exception {
        enemyField = new EnemyField(10, 10);
        coord = new Coord(1, 0);

    }

    @Test
    public void testSetHurt() throws Exception {
        assertFalse("Cell were hurt before it was shoot ", enemyField.getCell(coord).isHurted());
        assertFalse("Cell were shoot before it was shoot ", enemyField.getCell(coord).isShooted());
        assertFalse("Cell was hurt before not it was shoot ", enemyField.getCell(coord.getRightDown()).isHurted());
        assertFalse("Cell was shoot before not it was shoot ", enemyField.getCell(coord.getRightDown()).isShooted());
        enemyField.setHurt(coord);
        assertTrue("Cell wasn't hurt after it was shoot ", enemyField.getCell(coord).isHurted());
        assertTrue("Cell wasn't shoot after it was shoot ", enemyField.getCell(coord).isShooted());
        assertFalse("Cell was hurt after not it was shoot ", enemyField.getCell(coord.getRightDown()).isHurted());
        assertFalse("Cell was shoot after not it was shoot ", enemyField.getCell(coord.getRightDown()).isShooted());
    }


    @Test
    public void testSetKilled() throws Exception {
        enemyField.setHurt(coord);
        assertEquals(0, enemyField.getKilled());
        assertFalse(enemyField.getCell(coord.getRight()).isHurted());
        enemyField.setKilled(coord.getRight());
        assertEquals(1, enemyField.getKilled());
        assertTrue(enemyField.getCell(coord.getRight()).isHurted());
        assertFalse(enemyField.getCell(coord.getRightDown()).isHurted());
    }

    @Test
    public void testGetReconstructedShips() throws Exception {
        enemyField.setHurt(coord);
        enemyField.setHurt(coord.getLeft());
        assertEquals(0, enemyField.getReconstructedShips().size());
        enemyField.setKilled(coord.getRight());
        assertEquals(1, enemyField.getReconstructedShips().size());
        Ship example = new Ship(3);
        example.setCoords(new Coord(0, 0));
        assertEquals(example, enemyField.getReconstructedShips().get(0));
        enemyField.setKilled(coord.getRightDown().getRightDown());
        assertFalse(example.equals(enemyField.getReconstructedShips().get(1)));
        Ship example2 = new Ship(1);
        example2.setCoords(coord.getRightDown().getRightDown());
        assertEquals(example2, enemyField.getReconstructedShips().get(1));
    }
}