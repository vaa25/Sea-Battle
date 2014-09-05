import model.enums.CellState;
import model.enums.ShotResult;
import model.objects.Field;
import model.objects.Player;
import model.objects.Ship;
import model.services.LocationManager;
import model.services.ShotHandler;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.LinkedList;

import static model.enums.CellState.*;
import static model.enums.ShotResult.*;
import static org.junit.Assert.assertEquals;

public class ShotHandlerTest
{
	Field enemyField;
	Field playerField;
	ShotHandler shotHandler;
	LocationManager locationManager;

	Player player1;
	Player player2;

	@Before
	public void setUp() throws Exception
	{
		player1 = new Player();
		player2 = new Player();
		enemyField = player1.enemyField;
		playerField = player1.playerField;
		shotHandler = player1.shotHandler;
		locationManager = player1.locationManager;
	}

	@Test
	public void getShotToEmptyCell()
	{
		Point shotPoint = new Point(1, 1);
		ShotResult expectedResult = MISS;
		ShotResult actualResult = shotHandler.getShot(shotPoint);
		assertEquals(expectedResult, actualResult);

		CellState expectedState = SHELLED;
		CellState actualState = playerField.getCell(shotPoint).state;
		assertEquals(expectedState, actualState);

		shotPoint = new Point(5, 5);
		expectedResult = MISS;
		actualResult = shotHandler.getShot(shotPoint);
		assertEquals(expectedResult, actualResult);

		actualState = playerField.getCell(shotPoint).state;
		assertEquals(expectedState, actualState);

		shotPoint = new Point(10, 10);
		expectedResult = MISS;
		actualResult = shotHandler.getShot(new Point(10, 10));
		assertEquals(expectedResult, actualResult);

		actualState = playerField.getCell(shotPoint).state;
		assertEquals(expectedState, actualState);

	}

	@Test
	public void getShotToCellWhereShipIsLocating()
	{
		Ship ship = new Ship(2);
		locationManager.locateShipToField(ship, new Point(1, 1));

		Point shotPoint = new Point(1, 1);
		ShotResult expected = HIT;
		ShotResult actual = shotHandler.getShot(shotPoint);
		assertEquals(expected, actual);

		CellState expectedState = DAMAGED_SHIP;
		CellState actualState = playerField.getCell(shotPoint).state;
		assertEquals(expectedState, actualState);

		shotPoint = new Point(1, 2);
		expected = DESTROY;
		actual = shotHandler.getShot(shotPoint);
		assertEquals(expected, actual);

		expectedState = DESTROYED_SHIP;
		actualState = playerField.getCell(shotPoint).state;
		assertEquals(expectedState, actualState);

	}

	@Test
	public void missShot()
	{
		Point shotPoint = new Point(1, 1);
		ShotResult expectedResult = MISS;
		ShotResult actualResult = player1.shotHandler.shot(player2.shotHandler, shotPoint);
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void damageShot()
	{
		Point shotPoint = new Point(1, 1);
		Ship ship = new Ship(2);
		player2.locationManager.locateShipToField(ship, shotPoint);
		ShotResult expectedResult = HIT;
		ShotResult actualResult = player1.shotHandler.shot(player2.shotHandler, shotPoint);
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void destroyShot()
	{
//		ShotHandler p2ShotHandler = Mockito.mock(ShotHandler.class);
//		Mockito.when(p2ShotHandler.getShot(shotPoint)).thenReturn(DESTROY);

		Point locatePoint = new Point(0, 1);
		Ship ship = new Ship(1);
		player2.locationManager.locateShipToField(ship, locatePoint);

		Point shotPoint = locatePoint;
		ShotResult expectedResult = DESTROY;
		ShotResult actualResult = player1.shotHandler.shot(player2.shotHandler, shotPoint);
		assertEquals(expectedResult, actualResult);

	}


	public static void main(String[] args)
	{
		LinkedList<Point> points = new LinkedList<>();
		points.addFirst(new Point(1, 1));
		points.getFirst();
	}
}