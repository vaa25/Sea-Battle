package model;

import java.awt.*;
import java.util.ArrayList;

import static model.CellState.EMPTY;
import static model.CellState.SHIP;
import static model.ShipLayout.VERTICAL;

/**
 * Nick:   sobolevstp
 * Date:   8/28/14
 * Time:   00:28
 *
 * @author Stepan Sobolev
 */
public class Field implements TakingShots
{
	private final int SIZE = 10;
	private Cell[][] cells;
	private ArrayList<Ship> ships;

	public Field()
	{
		this.cells = new Cell[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				cells[i][j] = new Cell(i + 1, j + 1);
			}
		}
		initializeShips();
	}

	private void initializeShips()
	{
		ships = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			int qt = 4;
			int size = 1;
			for (int j = 0; j < qt; j++) {
				ships.add(new Ship(size));
			}
			qt--;
			size++;
		}
	}

	@Override
	public ShotResult getShot(Point p)
	{
		return cells[p.x][p.y].getShot();
	}

	/**
	 * Разместить корабль на поле
	 *
	 * @param ship
	 * @param p
	 * @return разместился ли корабль
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


		for (Cell cell : location) {
			cell.setState(SHIP);
			cell.setLocatedShip(ship);
		}

		ship.setLocation(location);

		return true;
	}

	/**
	 * Проверка, не будет ли корабль вылазить за границы поля
	 *
	 * @param ship
	 * @param p - начальная позиция размещения
	 * @return true, если корабль помещается на поле, иначе
	 * @return false
	 */
	private boolean checkLocationOutOfBounds(Ship ship, Point p)
	{
		int shipSize = ship.getSIZE();
		if (ship.getLayout() == VERTICAL) {
			if (p.x > 0 && p.x <= SIZE && p.y > 0 && (p.y + shipSize - 1) <= SIZE) {
				return true;
			}
		} else {
			if (p.x > 0 && (p.x + shipSize - 1) <= 10 && p.y > 0 && p.y <= 10) {
				return true;
			}
		}
		return false;
	}

	private Cell[] generateShipLocation(Ship ship, Point p)
	{
		int shipSize = ship.getSIZE();
		Cell[] location = new Cell[shipSize];
		if (ship.getLayout() == VERTICAL) {
			int i = 0;
			for (int y = p.y; y < (p.y + shipSize); y++) {
				location[i++] = cells[p.x - 1][y - 1];

			}
		} else {
			for (int x = p.x; x < (p.x + shipSize); x++) {
				int i = 0;
				location[i++] = cells[x - 1][p.y - 1];
			}
		}
		return location;
	}

	private boolean checkLocationAvailability(Cell[] location)
	{
		for (Cell cell : location) {
			if (cell.getState() != EMPTY) {
				return false;
			}
		}
		return true;
	}
}