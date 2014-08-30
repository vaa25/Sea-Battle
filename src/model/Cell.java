package model;

import static model.CellState.*;
import static model.ShotResult.*;

/**
 * Nick:   sobolevstp
 * Date:   8/28/14
 * Time:   00:29
 *
 * @author Stepan Sobolev
 */

enum CellState
{
	FOG,
	EMPTY,
	SHELLED,
	SHIP,
	DAMAGED_SHIP,
	SHIP_OUTLINE
}

class Cell
{
	private final int X;
	private final int Y;
	private AliveChecker locatedShip;
	private CellState state;

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

	public void setLocatedShip(AliveChecker locatedShip)
	{
		this.locatedShip = locatedShip;
	}

	public CellState getState()
	{
		return state;
	}

	public void setState(CellState state)
	{
		this.state = state;
	}

	public int getX()
	{
		return X;
	}

	public int getY()
	{
		return Y;
	}
}
