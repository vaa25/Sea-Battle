package model;

import java.awt.*;

import static model.CellState.*;
import static model.ShotResult.*;

class Cell
{
	final Point P;
	AliveChecker locatedShip;
	CellState state;

	public Cell(int x, int y)
	{
		this.P = new Point(x, y);
		// default state
		state = EMPTY;
	}

	public ShotResult getShot()
	{
		if (state == EMPTY) {
			state = SHELLED;
			return MISSES;
		}
		if (state == SHIP) {
			state = DAMAGED_SHIP;
			if (locatedShip.isAlive()) {
				return HIT;
			}
		}
		return DESTROY;
	}
}
