import model.*;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static model.ShipLayout.HORIZONTAL;
import static model.ShipLayout.VERTICAL;
import static org.junit.Assert.*;

public class LocationManagerTest
{
	LocationManager manager;
	Player player;
	Ship ship;
	Point point;

	@Before
	public void setUp() throws Exception
	{
		player = new Player();
		manager = new LocationManager(player);
	}

	@Test
	public void locateShipToEmptyField()
	{
		ship = new Ship(3);
		point = new Point(1, 8);
		boolean actual = manager.locateShipToField(ship, point);
		boolean expected = true;

		assertEquals(expected, actual);

		manager.clearField();
		ship = new Ship(4);
		ship.setLayout(HORIZONTAL);
		point = new Point(7, 8);
		actual = manager.locateShipToField(ship, point);
		expected = true;

		assertEquals(expected, actual);

		manager.clearField();
		ship = new Ship(2);
		ship.setLayout(HORIZONTAL);
		point = new Point(9, 1);
		actual = manager.locateShipToField(ship, point);
		expected = true;

		assertEquals(expected, actual);

		manager.clearField();
		ship = new Ship(1);
		point = new Point(1, 1);
		actual = manager.locateShipToField(ship, point);
		expected = true;

		assertEquals(expected, actual);
	}

	@Test
	public void locateShipsWithoutDistancing()
	{
		ship = new Ship(3);
		ship.setLayout(VERTICAL);
		point = new Point(1, 1);
		manager.locateShipToField(ship, point);

		ship = new Ship(3);
		ship.setLayout(VERTICAL);
		point = new Point(2, 1);
		boolean actual = manager.locateShipToField(ship, point);
		boolean expected = false;

		assertEquals(expected, actual);

		manager.clearField();
		ship = new Ship(1);
		point = new Point(1, 1);
		manager.locateShipToField(ship, point);

		ship = new Ship(1);
		point = new Point(1, 2);
		actual = manager.locateShipToField(ship, point);
		expected = false;

		assertEquals(expected, actual);
	}

	@Test
	public void locateAllShipsToField()
	{
		point = new Point(1, 1);
		ship = manager.getShipForLocation(1);
		manager.locateShipToField(ship, point);
		assertNotNull(manager.getShipForLocation(1));

		point = new Point(1, 3);
		ship = manager.getShipForLocation(1);
		manager.locateShipToField(ship, point);
		assertNotNull(manager.getShipForLocation(1));

		point = new Point(1, 5);
		ship = manager.getShipForLocation(1);
		manager.locateShipToField(ship, point);
		assertNotNull(manager.getShipForLocation(1));

		point = new Point(1, 7);
		ship = manager.getShipForLocation(1);
		manager.locateShipToField(ship, point);
		assertNull(manager.getShipForLocation(1));

		point = new Point(3, 1);
		ship = manager.getShipForLocation(2);
		manager.locateShipToField(ship, point);
		assertNotNull(manager.getShipForLocation(2));

		point = new Point(3, 4);
		ship = manager.getShipForLocation(2);
		manager.locateShipToField(ship, point);
		assertNotNull(manager.getShipForLocation(2));

		point = new Point(3, 7);
		ship = manager.getShipForLocation(2);
		manager.locateShipToField(ship, point);
		assertNull(manager.getShipForLocation(2));

		point = new Point(5, 1);
		ship = manager.getShipForLocation(3);
		manager.locateShipToField(ship, point);
		assertNotNull(manager.getShipForLocation(3));

		point = new Point(5, 5);
		ship = manager.getShipForLocation(3);
		manager.locateShipToField(ship, point);
		assertNull(manager.getShipForLocation(3));

		point = new Point(7, 1);
		ship = manager.getShipForLocation(4);
		manager.locateShipToField(ship, point);
		assertNull(manager.getShipForLocation(4));
	}

	@Test
	public void locateShipWithOutOfBoundsCoordinate()
	{
		ship = new Ship(3);
		point = new Point(0, 1);
		boolean actual = manager.locateShipToField(ship, point);
		boolean expected = false;

		assertEquals(expected, actual);

		manager.clearField();
		ship = new Ship(3);
		point = new Point(11, 1);
		actual = manager.locateShipToField(ship, point);
		expected = false;

		assertEquals(expected, actual);

		manager.clearField();
		ship = new Ship(3);
		point = new Point(1, 9);
		actual = manager.locateShipToField(ship, point);
		expected = false;

		assertEquals(expected, actual);

		manager.clearField();
		ship = new Ship(4);
		point = new Point(1, 8);
		actual = manager.locateShipToField(ship, point);
		expected = false;

		assertEquals(expected, actual);
	}

	@Test
	public void locateShipsOneToAnother()
	{
		ship = new Ship(3);
		ship.setLayout(VERTICAL);
		point = new Point(1, 1);
		manager.locateShipToField(ship, point);

		ship = new Ship(3);
		ship.setLayout(VERTICAL);
		point = new Point(1, 1);
		boolean actual = manager.locateShipToField(ship, point);
		boolean expected = false;

		assertEquals(expected, actual);

		manager.clearField();
		ship = new Ship(1);
		point = new Point(10, 10);
		manager.locateShipToField(ship, point);

		ship = new Ship(1);
		point = new Point(10, 10);
		actual = manager.locateShipToField(ship, point);
		expected = false;

		assertEquals(expected, actual);
	}

	@Test
	public void removeShipFromField()
	{
		ship = new Ship(2);
		point = new Point(1, 1);
		manager.locateShipToField(ship, point);
		assertTrue(ship.isLocated());

		manager.removeShipFromField(ship);
		assertFalse(ship.isLocated());
	}

	@Test
	public void removeShipFromFieldWhenIsNotLocated()
	{
		ship = new Ship(2);
		assertFalse(manager.removeShipFromField(ship));
	}

	@Test
	public void getShipForLocation()
	{
		ship = manager.getShipForLocation(1);
		int expected = 1;
		int actual = ship.SIZE;
		assertEquals(expected, actual);

		ship = manager.getShipForLocation(2);
		expected = 2;
		actual = ship.SIZE;
		assertEquals(expected, actual);

		ship = manager.getShipForLocation(3);
		expected = 3;
		actual = ship.SIZE;
		assertEquals(expected, actual);

		ship = manager.getShipForLocation(4);
		expected = 4;
		actual = ship.SIZE;
		assertEquals(expected, actual);
	}

	@Test
	public void clearField()
	{
		ship = new Ship(2);
		point = new Point(1, 1);
		manager.locateShipToField(ship, point);
		Cell cell = player.getCellFromField(point);
		assertEquals(CellState.SHIP, cell.getState());

		manager.clearField();
		assertEquals(CellState.EMPTY, cell.getState());
	}

}