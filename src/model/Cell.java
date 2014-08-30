package model;

import static model.CellState.*;
import static model.ShotResult.*;

class Cell
{
	final int X;
	final int Y;
	AliveChecker locatedShip;
	CellState state;

	public Cell(int x, int y)
	{
		this.X = x;
		this.Y = y;
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
