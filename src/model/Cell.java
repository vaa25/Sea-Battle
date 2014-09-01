package model;

import static model.CellState.*;
import static model.ShotResult.*;

public class Cell
{
	CellState state;
	AliveChecker locatedShip;

	public Cell()
	{
		// default state
		this.state = EMPTY;
	}

	Cell(CellState state)
	{
		this.state = state;
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

	public CellState getState()
	{
		return state;
	}
}