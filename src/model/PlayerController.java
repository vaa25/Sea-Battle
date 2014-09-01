package model;

import java.awt.*;

/**
 * Nick:   sobolevstp
 * Date:   8/30/14
 * Time:   19:30
 *
 * @author Stepan Sobolev
 */
public class PlayerController implements TakingShots
{
	Player player;

	public PlayerController()
	{
		player = new Player();
	}

	public ShotResult shoot(Point p)
	{
		return player.enemy.getShot(p);
	}

	@Override
	public ShotResult getShot(Point p)
	{
		return player.field.cells.get(p).getShot();
	}

	/**
	 * Метод проверяет, готов ли игрок к игре,
	 * т.е. расставил ли он все корабли на поле
	 */
	public boolean isPlayerReadyForGame()
	{
		for (Ship ship : player.ships) {
			if (ship.location.isEmpty()) {
				return false;
			}
		}
		return true;
	}

//	public static void main(String[] args)
//	{
//		PlayerController controller = new PlayerController();
////		LinkedList<Point> shipLocation = controller.generateShipLocation(new Ship(3), new Point(1, 1));
////		System.out.println(shipLocation);
////		LinkedList<Point> shipOutline = controller.generateShipOutline(shipLocation);
////		System.out.println(shipOutline);
//		Ship ship1 = new Ship(3);
//		Ship ship2 = new Ship(3);
//		System.out.println(controller.locateShipToField(ship1, new Point(1, 1)));
//		System.out.println(controller.removeShipFromField(ship1));
//		System.out.println(controller.locateShipToField(ship1, new Point(1, 1)));
//		System.out.println(controller.locateShipToField(ship2, new Point(1, 5)));
//		controller.clearField();
//		System.out.println(controller.locateShipToField(ship1, new Point(1, 1)));
//		System.out.println(controller.locateShipToField(ship2, new Point(1, 5)));
//		System.out.println(controller.removeShipFromField(ship1));
//		System.out.println(controller.removeShipFromField(ship2));
//		System.out.println(controller.locateShipToField(ship1, new Point(1, 1)));
//		System.out.println(controller.locateShipToField(ship2, new Point(1, 5)));
//	}
}