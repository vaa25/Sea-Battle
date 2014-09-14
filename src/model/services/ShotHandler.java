package model.services;

import model.enums.ShotResult;
import model.interfaces.Shotable;
import model.objects.field.Cell;
import model.objects.field.Field;

import java.awt.*;
import java.util.LinkedList;

import static model.enums.CellState.*;
import static model.enums.ShotResult.*;
import static model.enums.ShotResult.UNDEFINED;

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
		Cell targetCell = playerField.getCell(p);

		switch (targetCell.state) {
			case EMPTY:
				targetCell.state = SHELLED;
				return MISS;
			case OUTLINE:
				targetCell.state = SHELLED;
				return MISS;
			case SHIP:
				targetCell.state = DAMAGED_SHIP;
				if (targetCell.locatedShip.isAlive()) {
					return HIT;
				} else {
					return DESTROY;
				}
			case SHELLED:
				return NOT_ALLOWED;
			case DAMAGED_SHIP:
				return NOT_ALLOWED;
			case DESTROYED_SHIP:
				return NOT_ALLOWED;
			case UNDEFINED:
				return UNDEFINED;
			default:
				return UNDEFINED;
		}
	}

	public void shot(Shotable enemy, Point p)
	{
		ShotResult result = enemy.getShot(p);

		switch (result) {
			case MISS:
				enemyField.getCell(p).state = SHELLED;
				break;
			case HIT:
				enemyField.getCell(p).state = DAMAGED_SHIP;
				break;
			case DESTROY:
				enemyField.getCell(p).state = DAMAGED_SHIP;
				handleDestroyedShip(p);
		}

		// TODO if (result == HIT || DESTROY) then (shot again)
	}

	/**
	 * Обработка подбитого корабля
	 *
	 * @param destroyPoint координата последнего выстрела в корабль
	 */
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

	/**
	 * Воссоздаем размещение уничтоженного корабля по последней подбитой точке
	 *
	 * @param p координаты последнего выстрела
	 * @return локацию подбитого корабля
	 */
	private LinkedList<Point> generateShipLocationFromDestroyPoint(Point p)
	{
		// создаем новый массив и добавляем туда координаты уничтожения корабля
		LinkedList<Point> shipLocation = new LinkedList<Point>();
		shipLocation.add(p);

		// далее составляем позицию корабля по состоянию окружающих клеток

		// проверяем клетки по горизонтали влево
		int x = p.x - 1;
		while (x >= 1 && enemyField.getCell(new Point(x, p.y)).state == DAMAGED_SHIP) {
			shipLocation.addFirst(new Point(x, p.y));
			x--;
		}

		// проверяем клетки по горизонтали вправо
		x = p.x + 1;
		while (enemyField.getCell(new Point(x, p.y)).state == DAMAGED_SHIP && x <= 10) {
			shipLocation.addLast(p);
			x++;
		}
		// если по горизонтали нет окружающих клеток с состоянием подбитого корабля,

		// тогда ищем по вертикали
		if (shipLocation.size() == 1) {

			// проверяем клетки по вертикали вверх
			int y = p.y - 1;
			while (enemyField.getCell(new Point(p.x, y)).state == DAMAGED_SHIP && y >= 1) {
				shipLocation.addFirst(p);
				y--;
			}

			// проверяем клетки по вертикали вниз
			y = p.y + 1;
			while (enemyField.getCell(new Point(p.x, y)).state == DAMAGED_SHIP && y <= 10) {
				shipLocation.addLast(p);
				y++;
			}
		}

		// если и по вертикали нет, тогда размер корабля = 1

		return shipLocation;
	}

	/**
	 * Метод, определяющий и возвращающий контур корабля;
	 *
	 * @param shipLocation - локация корабля, вокруг которого надо собрать контур
	 * @return - контур вокруг shipLocation;
	 */
	private LinkedList<Point> generateShipOutline(LinkedList<Point> shipLocation)
	{
		LinkedList<Point> shipOutline = new LinkedList<>();

		// определяем координаты левого верхнего и правого нижнего угла контура
		int x1 = shipLocation.getFirst().x - 1;
		int y1 = shipLocation.getFirst().y - 1;
		int x2 = shipLocation.getLast().x + 1;
		int y2 = shipLocation.getLast().y + 1;

		// далее формируем массив координат данного квадрата и проверяем,
		// не выходит ли координата за пределы поля.
		// если координата не выходит за пределы - тогда добавляем ее в массив

		// рисуем линию направо
		for (int x = x1; x <= x2; x++) {
			Point p = new Point(x, y1);
			if (!isPointOutOfBounds(p)) {
				shipOutline.add(p);
			}
		}

		// продолжаем рисовать линию вниз
		for (int y = y1 + 1; y <= y2; y++) {
			Point p = new Point(x2, y);
			if (!isPointOutOfBounds(p)) {
				shipOutline.add(p);
			}
		}

		// продолжаем рисовать линию налево
		for (int x = x2 - 1; x >= x1; x--) {
			Point p = new Point(x, y2);
			if (!isPointOutOfBounds(p)) {
				shipOutline.add(p);
			}
		}

		// продолжаем рисовать линию вверх
		for (int y = y2 - 1; y > y1; y--) {
			Point p = new Point(x1, y);
			if (!isPointOutOfBounds(p)) {
				shipOutline.add(p);
			}
		}

		return shipOutline;
	}

	/**
	 * Метод проверяет не выходят ли координаты точки за рамки поля
	 */
	private boolean isPointOutOfBounds(Point p)
	{
		if (p.x < 1) {
			return true;
		}
		if (p.y < 1) {
			return true;
		}
		if (p.x > enemyField.FIELD_SIZE) {
			return true;
		}
		if (p.y > enemyField.FIELD_SIZE) {
			return true;
		}

		return false;
	}

}
