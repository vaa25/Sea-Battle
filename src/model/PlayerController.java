package model;

import java.awt.*;
import java.util.ArrayList;

import static model.CellState.EMPTY;
import static model.CellState.SHIP;
import static model.CellState.OUTLINE;
import static model.ShipLayout.VERTICAL;

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
		initializeShips();
	}

	public ShotResult shoot(Point p)
	{
		return player.enemy.getShot(p);
	}

	@Override
	public ShotResult getShot(Point p)
	{
		return player.field.cells[p.x][p.y].getShot();
	}

	/**
	 * Метод создает массив с кораблями для игрока
	 */
	private void initializeShips()
	{
		player.ships = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			int qt = 4;
			int size = 1;
			for (int j = 0; j < qt; j++) {
				player.ships.add(new Ship(size));
			}
			qt--;
			size++;
		}
	}

	/**
	 * Метод размещает корабль на поле
	 */
	public boolean locateShip(Ship ship, Point p)
	{
		// проверка, вмещается ли корабль на поле
		if (!checkLocationOutOfBounds(ship, p)) {
			return false;
		}

		// проверка, доступны ли данные ячейки для размещения корабля
		Cell[] location = generateShipLocation(ship, p);
		if (!checkLocationAvailability(location)) {
			return false;
		}

		// изменяем состояние ячейки и присваиваем ей ссылку на корабль
		for (Cell cell : location) {
			cell.state = SHIP;
			cell.locatedShip = ship;
		}

		// присваиваем кораблю локацию
		ship.location = location;

		// присваиваем соседним ячейкам состояние околокорабельного пространства
		setOutlineStateAroundShip(location[0].P, location[ship.SIZE - 1].P);

		return true;
	}

	/**
	 * Метод проверяет, не будет ли корабль вылазить за границы поля учитывая логику построения корабля (вниз или вправо от точки "p")
	 * @param p - начальная позиция размещения
	 */
	private boolean checkLocationOutOfBounds(Ship ship, Point p)
	{
		int shipSize = ship.SIZE;
		if (ship.layout == VERTICAL) {
			if (p.x > 0 && p.x <= player.field.SIZE && p.y > 0 && (p.y + shipSize - 1) <= player.field.SIZE) {
				return true;
			}
		} else {
			if (p.x > 0 && (p.x + shipSize - 1) <= 10 && p.y > 0 && p.y <= 10) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Метод формирует массив ячеек где будет размещатся корабль, исходя из координат Point p по определенным правилам. Логика размещения:
	 * - если размещение корабля вертикальное то локация строится с точки "p" и вниз;
	 * - а если - горизонтальное, то вправо.
	 */
	private Cell[] generateShipLocation(Ship ship, Point p)
	{
		int shipSize = ship.SIZE;
		Cell[] location = new Cell[shipSize];
		if (ship.layout == VERTICAL) {
			int i = 0;
			for (int y = p.y; y < (p.y + shipSize); y++) {
				location[i++] = player.field.cells[p.x - 1][y - 1];

			}
		} else {
		for (int x = p.x; x < (p.x + shipSize); x++) {
			int i = 0;
			location[i++] = player.field.cells[x - 1][p.y - 1];
			}
		}
		return location;
	}

	/**
	 * Метод проверяет все ли ячейки находятся в свободном состоянии;
	 */
	private boolean checkLocationAvailability(Cell[] location)
	{
		for (Cell cell : location) {
			if (cell.state != EMPTY) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Метод, присваивающий квадрату ячеек состояние околокорабельного пространства
	 */
	private void setOutlineStateAroundShip(Point start, Point end)
	{
		ArrayList<Cell> shipOutline = new ArrayList<>();

		int x1 = start.x - 1;
		int y1 = start.y - 1;
		int x2 = end.x + 1;
		int y2 = end.y + 1;

		for (int i = x1; i <= x2; i++) {
			for (int j = y1; j <= y2; j++) {
				shipOutline.add(player.field.cells[i - 1][j - 1]);
			}
		}

		for (Cell cell : shipOutline) {
			if (cell.state != SHIP) {
				cell.state = OUTLINE;
			}
		}
	}


}
