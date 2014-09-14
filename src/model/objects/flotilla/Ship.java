package model.objects.flotilla;

import model.enums.ShipLayout;
import model.interfaces.AliveChecker;
import model.objects.field.Cell;

import java.util.LinkedList;

import static model.enums.CellState.EMPTY;
import static model.enums.CellState.SHIP;
import static model.enums.ShipLayout.HORIZONTAL;
import static model.enums.ShipLayout.VERTICAL;

public class Ship implements AliveChecker
{
	/**
	 * Длина корабля.
	 */
	public final int SHIP_SIZE;
	/**
	 * Ориентация корабля: горизонтальная или вертикальная.
	 */
	public ShipLayout layout;
	/**
	 * Массив ячеек, на которых размещается корабль. Первый элемент в массиве - левая верхняя часть корабля.
	 */
	public LinkedList<Cell> location;

	public Ship(int SHIP_SIZE)
	{
		this.SHIP_SIZE = SHIP_SIZE;
		this.layout = VERTICAL;
		this.location = new LinkedList<>();
	}

	@Override
	public boolean isAlive()
	{
		for (Cell cell : location) {
			if (cell.state == SHIP) {
				return true;
			}
		}
		return false;
	}

	public boolean isLocated()
	{
		return location.size() > 0;
	}

	public void setLocation(LinkedList<Cell> location)
	{
		this.location = location;
	}

	public boolean addCellToLocation(Cell cell)
	{
		return this.location.add(cell);
	}

	public void clearLocation()
	{
		for (Cell cell : location) {
			cell.state = EMPTY;
		}

		this.location.clear();
	}

	public void setLayout(ShipLayout layout)
	{
		this.layout = layout;
	}

	public void changeLayout()
	{
		if (layout == HORIZONTAL) {
			layout = VERTICAL;
		} else {
			layout = HORIZONTAL;
		}
	}
}