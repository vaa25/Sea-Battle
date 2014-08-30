package model;

import static model.CellState.*;
import static model.ShipLayout.HORIZONTAL;
import static model.ShipLayout.VERTICAL;

public class Ship implements AliveChecker
{
	final int SIZE;
	ShipLayout layout;
	Cell[] location;

	public Ship(int SIZE)
	{
		this.SIZE = SIZE;
		this.layout = VERTICAL;
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
}
