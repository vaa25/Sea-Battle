package model;

import static model.CellState.*;
import static model.ShotResult.*;

enum CellState
{
	EMPTY,
	WAS_SHOT,
	SHIP,
	SHIP_WAS_SHOT,
	SEPARATING_ZONE
}

/**
 * Nick:   sobolevstp
 * Date:   8/28/14
 * Time:   00:29
 *
 * @author Stepan Sobolev
 */
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
			state = WAS_SHOT;
			return DO_NOT_HIT;
		}
		if (state == SHIP) {
			state = SHIP_WAS_SHOT;
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

	public void setState(CellState state)
	{
		this.state = state;
	}

	public void setLocatedShip(AliveChecker locatedShip)
	{
		this.locatedShip = locatedShip;
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
