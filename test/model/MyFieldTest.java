package model;

import common.Coord;
import common.ShootResult;
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
    private List<Ship> ships;
    private Coord coord1;
    private Coord coord2;

    @Before
    public void setUp() throws Exception {
        myField = new MyField(10, 10);
        ship4 = new Ship(4);
        ship3 = new Ship(3);
        ship2 = new Ship(2);
        ship1 = new Ship(1);
        ships = new ArrayList<>();
        myField.setShips(ships);
        coord1 = new Coord(3, 3);
        coord2 = new Coord(0, 0);
        ship2.setCoords(coord2);
        ships.add(ship2);
    }

    @Test
    public void testCanPlace() throws Exception {

        ship1.setCoords(new Coord(0, -2));
        assertFalse("ship1(" + ship1 + ") can be placed out of bounds", myField.canPlace(ship1));
        ship1.setCoords(new Coord(10, 2));
        assertFalse("ship1(" + ship1 + ") can be placed out of bounds", myField.canPlace(ship1));
        ship1.setCoords(new Coord(0, 0));
        assertFalse("ship1(" + ship1 + ") can be placed at same cell with ship2(" + ship2 + ")", myField.canPlace(ship1));
        ship1.setCoords(new Coord(1, 0));
        assertFalse("ship1(" + ship1 + ") can be placed at same cell with ship2(" + ship2 + ")", myField.canPlace(ship1));
        ship1.setCoords(new Coord(2, 0));
        assertFalse("ship1(" + ship1 + ") can be placed near ship2(" + ship2 + ")", myField.canPlace(ship1));
        ship1.setCoords(new Coord(0, 5));
        assertTrue("ship1(" + ship1 + ") can't be placed at free space", myField.canPlace(ship1));
    }

//
//    @Test
//    public void testPlaceRandom() throws Exception {
//        ship2.setCoords(coord2);
//        ship1.setCoords(coord1);
//        addAllShips(ship1, ship2, ship3, ship4);
////        myField.placeRandom();
//        assertEquals("ship2 changed coords",new Coord(0, 0),ship2.getShipCoords()[0]);
//        assertEquals("ship2 changed coords",new Coord(1, 0),ship2.getShipCoords()[1]);
//        assertEquals("ship1 changed coords",new Coord(6,6) ,ship1.getShipCoords()[0]);
//        for (int i = 0; i < ships.size()-1; i++) {
//            Ship ship=ships.get(i);
//            for (int j = i+1; j < ships.size(); j++) {
//                assertFalse("ship (" + ship + ") collides with ship (" + ships.get(j) + ")",ship.isCrossing(ships.get(j)));
//            }
//        }
//    }

//    private void addAllShips(Ship ... ship){
//        for(Ship elem:ship){
//            ships.add(elem);
//        }
//    }

    @Test
    public void testPlace() throws Exception {
        myField.place(ships);
        assertTrue("ship is not marked as placed", ship2.isPlaced());
        for (Coord coord : ship2.getShipCoords()) {
            assertEquals("Cell " + myField.getCell(coord) + "not contains ship " + ship2, ship2, myField.getCell(coord).getShip());
        }
    }

    @Test
    public void testShoot() throws Exception {
        myField.place(ships);
        assertEquals(ShootResult.HURT, myField.shoot(new Coord(0, 0)));
        assertEquals(ShootResult.KILLED, myField.shoot(new Coord(1, 0)));
        assertEquals(ShootResult.MISSED, myField.shoot(new Coord(1, 1)));
        assertEquals(ShootResult.MISSED, myField.shoot(new Coord(1, 1)));
    }

    @Test
    public void testGetAliveShips() throws Exception {
        ship1.setCoords(coord1);
        ship3.setCoords(new Coord(5, 5));
        ship4.setCoords(new Coord(0, 5));
        ships.add(ship1);
        ships.add(ship3);
        ships.add(ship4);
        myField.place(ships);
        myField.shoot(coord1);
        myField.shoot(coord2);
        myField.shoot(coord2.getRightDown());
        List<Ship> alive = myField.getAliveShips();
        assertTrue("Alive ship " + ship2 + " not in the list", alive.contains(ship2));
        assertTrue("Alive ship " + ship3 + " not in the list", alive.contains(ship3));
        assertTrue("Alive ship " + ship4 + " not in the list", alive.contains(ship4));
        assertFalse("Dead ship " + ship1 + " in the list", alive.contains(ship1));
        myField.shoot(coord2.getRight());
        assertFalse("Dead ship " + ship2 + " in the list", myField.getAliveShips().contains(ship2));
    }
}