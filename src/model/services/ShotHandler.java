package model.services;

import model.enums.ShotResult;
import model.interfaces.Shotable;
import model.objects.Cell;
import model.objects.Field;

import java.awt.*;
import java.util.LinkedList;

import static model.enums.CellState.*;
import static model.enums.ShotResult.*;

/**
 * TODO DRY for methods
 * Nick:   sobolevstp
 * Date:   9/2/14
 * Time:   23:50
 *
 * @author Stepan Sobolev
 */
public class ShotHandler implements Shotable
{
	private Field playerField;
	private Field enemyField;

	public ShotHandler(Field playerField, Field enemyField)
	{
		this.playerField = playerField;
		this.enemyField = enemyField;
	}

	@Override
	public ShotResult getShot(Point p)
	{
		Cell shelledCell = playerField.getCell(p);

		// если ячейка пустая, то возвращается простреленно
		if (shelledCell.state == EMPTY || shelledCell.state == OUTLINE) {
			shelledCell.state = SHELLED;
			return MISS;
		}
		// если в ячейке есть корабль, то состояние ячейки -> поврежденный корабль
		if (shelledCell.state == SHIP) {
			shelledCell.state = DAMAGED_SHIP;
			// если поврежденный корабль жив, то возвращается ПОПАЛ
			if (shelledCell.locatedShip.isAlive()) {
				return HIT;
			}
		}
		// иначе возвращается убил;
		return DESTROY;
	}

	public ShotResult shot(Shotable enemy, Point p)
	{
		ShotResult result = enemy.getShot(p);
		if (result == MISS) {
			enemyField.getCell(p).state = SHELLED;
		}
		if (result == HIT) {
			enemyField.getCell(p).state = DAMAGED_SHIP;
		}
		if (result == DESTROY) {
			enemyField.getCell(p).state = DAMAGED_SHIP;
			handleDestroyedShip(p);
		}
		return enemy.getShot(p);
	}

	private void handleDestroyedShip(Point destroyPoint)
	{
		// определяем местоположение корабля по координатам последнего выстрела
		LinkedList<Point> destroyedShipLocation = generateShipLocationFromDestroyPoint(destroyPoint);
		// для всех ячеек под убитым кораблем меняем статус
		for (Point locationPoint : destroyedShipLocation) {
			enemyField.getCell(locationPoint).state = DESTROYED_SHIP;
		}

		// определяем контуры убитого корабля по его координатам
		LinkedList<Point> destroyedShipOutline = generateShipOutline(destroyedShipLocation);
		// для всех ячеек, являющихся контуром меняем статус
		for (Point outlinePoint : destroyedShipOutline) {
			enemyField.getCell(outlinePoint).state = OUTLINE;
		}
	}

	private LinkedList<Point> generateShipLocationFromDestroyPoint(Point p)
	{
		LinkedList<Point> shipLocation = new LinkedList<>();

		// проверяем клетки по горизонтали
		int x = p.x;
		while (enemyField.getCell(new Point(x--, p.y)).state == DAMAGED_SHIP) {
			shipLocation.addFirst(p);
		}
		x = p.x;
		while (enemyField.getCell(new Point(++x, p.y)).state == DAMAGED_SHIP) {
			shipLocation.addLast(p);
		}

		// если размер массива = 1, это значит, что корабль размещен вертикально
		if (shipLocation.size() == 1) {
			// проверяем клетки по вертикали
			int y = p.y;
			while (enemyField.getCell(new Point(p.x, y--)).state == DAMAGED_SHIP) {
				shipLocation.addFirst(p);
			}
			y = p.y;
			while (enemyField.getCell(new Point(p.x, ++y)).state == DAMAGED_SHIP) {
				shipLocation.addLast(p);
			}
		}

		return shipLocation;
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
	 * Метод проверяет не выходят ли координаты точки за рамки поля
	 */
	private boolean checkPointOutOfBounds(Point p)
	{
		if (p.x < 1) { return true;}
		if (p.y < 1) { return true;}
		if (p.x > enemyField.SIZE) { return true;}
		if (p.y > enemyField.SIZE) { return true;}

		return false;
	}

}
