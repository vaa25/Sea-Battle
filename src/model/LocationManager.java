package model;

import java.awt.*;
import java.util.LinkedList;

import static model.CellState.EMPTY;
import static model.CellState.SHIP;

/**
 * Nick:   sobolevstp
 * Date:   9/1/14
 * Time:   22:13
 *
 * @author Stepan Sobolev
 */
public class LocationManager
{
	private Player player;

	public LocationManager(Player player)
	{
		this.player = player;
	}

	/**
	 * Метод удаляет корабль из поля
	 */
	public boolean removeShipFromField(Ship ship)
	{
		for (Cell cell : ship.location) {
			cell.state = EMPTY;
			cell.locatedShip = null;
		}
		ship.location.clear();

		return true;
	}

	/**
	 * Метод размещает корабль на поле
	 */
	public boolean locateShipToField(Ship ship, Point p)
	{
		LinkedList<Point> shipLocation = generateShipLocation(ship, p);

		// проверяем, не размещен ли корабль уже
		if (!ship.location.isEmpty()) {
			System.out.println("false ship already located");
			return false;
		}

		// проверяем, вмещается ли корабль на поле
		if (checkLocationOutOfBounds(shipLocation)) {
			System.out.println("false shipLocation outOfBounds");
			return false;
		}

		// проверяем, свободны ли ячейки под кораблем
		if (!checkLocationAvailability(shipLocation)) {
			System.out.println("false shipLocation availability");
			return false;
		}

		LinkedList<Point> shipOutline = generateShipOutline(shipLocation);

		// проверяем, свободны ли ячейки по контуру корабля
		if (!checkLocationAvailability(shipOutline)) {
			System.out.println("false shipOutline availability");
			return false;
		}

		// изменяем состояние ячеек под кораблем и присваиваем им ссылку на корабль
		for (Point locationPoint : shipLocation) {
			Cell locationCell = player.field.cells.get(locationPoint);
			locationCell.state = SHIP;
			locationCell.locatedShip = ship;
		}

		// присваиваем кораблю ссылки на ячейки для проверки своего состояния
		for (Point point : shipLocation) {
			ship.location.add(player.field.cells.get(point));
		}

		return true;
	}

	/**
	 * Метод формирует массив ячеек где будет размещатся корабль, исходя из координат Point p по определенным правилам. Логика размещения:
	 * - если размещение корабля вертикальное то локация строится с точки "p" и вниз;
	 * - а если - горизонтальное, то вправо.
	 */
	private LinkedList<Point> generateShipLocation(Ship ship, Point p)
	{
		LinkedList<Point> location = new LinkedList<>();

		switch (ship.layout) {
			case VERTICAL:
				for (int y = p.y; y < (p.y + ship.SIZE); y++) {
					location.add(new Point(p.x, y));
				}
				break;
			case HORIZONTAL:
				for (int x = p.x; x < (p.x + ship.SIZE); x++) {
					location.add(new Point(x, p.y));
				}
				break;
		}

		return location;
	}

	/**
	 * Метод проверяет не выходят ли координаты корабля за рамки поля
	 */
	private boolean checkLocationOutOfBounds(LinkedList<Point> location)
	{
		for (Point p : location) {
			if (checkPointOutOfBounds(p)) {return true;}
		}
		return false;
	}

	/**
	 * Метод проверяет не выходят ли координаты точки за рамки поля
	 */
	private boolean checkPointOutOfBounds(Point p)
	{
		if (p.x < 1) { return true;}
		if (p.y < 1) { return true;}
		if (p.x > player.field.SIZE) { return true;}
		if (p.y > player.field.SIZE) { return true;}

		return false;
	}

	/**
	 * Метод проверяет все ли ячейки находятся в свободном состоянии;
	 */
	private boolean checkLocationAvailability(LinkedList<Point> location)
	{
		for (Point p : location) {
			if (player.field.cells.get(p).state != EMPTY) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Метод, присваивающий ячейкам вокруг корабля состояние контура
	 */
	private LinkedList<Point> generateShipOutline(LinkedList<Point> location)
	{
		LinkedList<Point> outlineRectangle = new LinkedList<>();

		// определяем координаты левого верхнего и правого нижнего угла контура
		int x1 = location.getFirst().x - 1; //0
		int y1 = location.getFirst().y - 1; //0
		int x2 = location.getLast().x + 1;  //2
		int y2 = location.getLast().y + 1;  //4

		// далее формируем массив координат данного квадрата

		// линия направо
		for (int x = x1; x <= x2; x++) {
			Point p = new Point(x, y1);
			if (!checkPointOutOfBounds(p)) {
				outlineRectangle.add(p);
			}
		}

		// продолжение линии вниз
		for (int y = y1 + 1; y <= y2; y++) {
			Point p = new Point(x2, y);
			if (!checkPointOutOfBounds(p)) {
				outlineRectangle.add(p);
			}
		}

		// продолжение линии налево
		for (int x = x2 - 1; x >= x1; x--) {
			Point p = new Point(x, y2);
			if (!checkPointOutOfBounds(p)) {
				outlineRectangle.add(p);
			}
		}

		// продолжение линии вверх
		for (int y = y2 - 1; y > y1; y--) {
			Point p = new Point(x1, y);
			if (!checkPointOutOfBounds(p)) {
				outlineRectangle.add(p);
			}
		}

		return outlineRectangle;
	}

	/**
	 * Метод очищает поле от кораблей и присваивает всем ячейкам статус пустой
	 */
	public void clearField()
	{
		for (Cell cell : player.field.cells.values()) {
			cell.state = EMPTY;
			cell.locatedShip = null;
		}

		for (Ship ship : player.ships) {
			ship.location.clear();
		}
	}

}
