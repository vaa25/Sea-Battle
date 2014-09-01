package model;

import java.util.LinkedList;

import static model.CellState.SHIP;
import static model.ShipLayout.HORIZONTAL;
import static model.ShipLayout.VERTICAL;

public class Ship implements AliveChecker
{
	public final int SIZE;
	ShipLayout layout;
	LinkedList<Cell> location;

	public Ship(int SIZE)
	{
		this.SIZE = SIZE;
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

	public void changeLayout()
	{
		if (layout == HORIZONTAL) {
			layout = VERTICAL;
		} else {
			layout = HORIZONTAL;
		}
	}

	public void setLayout(ShipLayout layout)
	{
		this.layout = layout;
	}

	public boolean isLocated()
	{
		return location.size() > 0;
	}
}