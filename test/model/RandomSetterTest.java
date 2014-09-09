package model;

import common.Coord;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RandomSetterTest extends TestCase {
    private RandomSetter rs;
    private List<Ship> ships;

    @Before
    public void setUp() throws Exception {
        rs = new RandomSetter(10, 10);
        ships = new ArrayList<>();
        ships.add(new Ship(4));
        ships.add(new Ship(3));
        ships.add(new Ship(2));
        ships.add(new Ship(1));
        ships.add(new Ship(4));
        ships.add(new Ship(3));
        ships.add(new Ship(2));
        ships.add(new Ship(1));
        rs.setShips(ships);
    }

    private boolean checkCoord(Coord[] coords) {
        for (Coord coord : coords) {
            if (!(coord.getX() >= 0 && coord.getY() >= 0 && coord.getX() < 10 && coord.getY() < 10)) return false;
        }
        return true;
    }

    private boolean checkCoord(Coord[] coords1, Coord[] coords2) {
        for (Coord coord : coords1) {
            for (Coord coord1 : coords2) {
                if (coord1.equals(coord)) return false;
            }
        }
        return true;
    }

    @Test
    public void testSetAll() throws Exception {
        assertTrue("Установить все корабли не удалось", rs.setAll());
        for (Ship ship : ships) {
            assertTrue("Координаты корабля выходят за пределы поля", checkCoord(ship.getShipCoords()));
            for (Ship ship1 : ships) {
                if (ship != ship1) {
                    assertTrue("Координаты корабля и окружающего пространства другого корабля накладываются друг на друга", checkCoord(ship1.getAroundCoords(), ship.getShipCoords()));
                }
            }
        }
//        System.out.println();
    }

    @Test
    public void testSetRest() throws Exception {
        Ship ship4 = ships.get(4);
        ship4.setCoords(new Coord(2, 2));
        Coord[] coords4 = Arrays.copyOf(ship4.getShipCoords(), ship4.getSize());
        ship4.setPlaced(true);
        Ship ship7 = ships.get(7);
        ship7.setCoords(new Coord(6, 4));
        Coord[] coords7 = Arrays.copyOf(ship7.getShipCoords(), ship7.getSize());
        ship7.setPlaced(true);
        assertTrue("Установить остальные корабли не удалось", rs.setRest());
        for (Ship ship : ships) {
            assertTrue("Координаты корабля выходят за пределы поля", checkCoord(ship.getShipCoords()));
            for (Ship ship1 : ships) {
                if (ship != ship1) {
                    assertTrue("Координаты корабля и окружающего пространства другого корабля накладываются друг на друга", checkCoord(ship1.getAroundCoords(), ship.getShipCoords()));
                }
            }
        }

        assertTrue("Координаты заранее установленного корабля изменились", Arrays.deepEquals(coords4, ship4.getShipCoords()));
        assertTrue("Координаты заранее установленного корабля изменились", Arrays.deepEquals(coords7, ship7.getShipCoords()));
    }

    @Test
    public void testSetShips() throws Exception {

        rs.setAll();
        assertEquals(ships, rs.getShipsPlaced());
    }

    @Test
    public void testGetShipsPlaced() throws Exception {
        rs.setAll();
        assertEquals(ships, rs.getShipsPlaced());
    }
}