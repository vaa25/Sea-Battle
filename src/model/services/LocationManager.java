package model.services;

import model.objects.Cell;
import model.objects.Field;
import model.objects.Ship;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

import static model.enums.CellState.EMPTY;
import static model.enums.CellState.SHIP;

/**
 * Nick:   sobolevstp
 * Date:   9/1/14
 * Time:   22:13
 *
 * @author Stepan Sobolev
 */
public class LocationManager
{
	private Field field;
	private ArrayList<Ship> ships;

	public LocationManager(Field field)
	{
		this.field = field;
	}

	public LocationManager(Field field, ArrayList<Ship> ships)
	{
		this.field = field;
		this.ships = ships;
	}

	/**
	 * Метод размещает корабль на поле
	 */
	public boolean locateShipToField(Ship ship, Point p)
	{
		LinkedList<Point> shipLocation = generateShipLocation(ship, p);

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
			Cell locationCell = field.getCell(locationPoint);
			locationCell.state = SHIP;
			locationCell.locatedShip = ship;
		}

		// обнуляем локацию корабля
		ship.location.clear();


		// присваиваем кораблю ссылки на ячейки для проверки своего состояния
		for (Point locationPoint : shipLocation) {
			ship.location.add(field.getCell(locationPoint));
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
		if (p.x > field.SIZE) { return true;}
		if (p.y > field.SIZE) { return true;}

		return false;
	}

	/**
	 * Метод проверяет все ли ячейки находятся в свободном состоянии;
	 *
	 * @param location - список ячеек, которым надо проверить состояние;
	 * @return - true, если ячейки свободные и false, если на них размещен корабль или если они являются контуром другого корабля;
	 */
	private boolean checkLocationAvailability(LinkedList<Point> location)
	{
		for (Point p : location) {
			if (field.getCell(p).state != EMPTY) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Метод, определяющий и возвращающий контур корабля;
	 *
	 * @param shipLocation - локация корабля, вокруг которого надо собрать контур№
	 * @return - контур вокруг shipLocation;
	 */
	private LinkedList<Point> generateShipOutline(LinkedList<Point> shipLocation)
	{
		LinkedList<Point> outlineRectangle = new LinkedList<>();

		// определяем координаты левого верхнего и правого нижнего угла контура
		int x1 = shipLocation.getFirst().x - 1; //0
		int y1 = shipLocation.getFirst().y - 1; //0
		int x2 = shipLocation.getLast().x + 1;  //2
		int y2 = shipLocation.getLast().y + 1;  //4

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
	 * Метод удаляет корабль из поля;
	 *
	 * @param ship - корабль, который надо убрать из поля;
	 * @return - true, если корабль был на поле и его убрали, и false, если корабля на поле не было;
	 */
	public boolean removeShipFromField(Ship ship)
	{
		if (!ship.isLocated()) {
			return false;
		}

		for (Cell cell : ship.location) {
			cell.state = EMPTY;
			cell.locatedShip = null;
		}
		ship.location.clear();

		return true;
	}

	/**
	 * Метод возвращает из списка всех кораблей еще не размещенный корабль указанного размер;
	 *
	 * @param shipSize - размер корабля, который надо разместить
	 * @return - корабль заданного размера shipSize, если есть такой неразмещенный корабль, или null, если все корабли данного размера находятся на поле;
	 */
	public Ship getShipForLocation(int shipSize)
	{
		for (Ship ship : ships) {
			if (ship.SIZE == shipSize) {
				if (!ship.isLocated()) {
					return ship;
				}
			}
		}
		return null;
	}

	/**
	 * Метод проверяет, готов ли игрок к игре,
	 * т.е. расставил ли он все корабли на поле
	 *
	 * @return - true, если все корабли размещены на поле, и false, если хоть один корабль не размещен на поле;
	 */
	public boolean isPlayerReadyForGame()
	{
		for (Ship ship : ships) {
			if (ship.location.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Метод очищает поле от кораблей и присваивает всем ячейкам статус пустой
	 */
	public void clearField()
	{
		ArrayList<Point> allPossiblePoints = new ArrayList<Point>();
		for (int i = 1; i <= field.SIZE; i++) {
			for (int j = 1; j <= field.SIZE; j++) {
				allPossiblePoints.add(new Point(i, j));
			}
		}

		for (Point p : allPossiblePoints) {
			Cell cell = field.getCell(p);
			cell.state = EMPTY;
			cell.locatedShip = null;
		}

		for (Ship ship : ships) {
			ship.location.clear();
		}
	}

}
