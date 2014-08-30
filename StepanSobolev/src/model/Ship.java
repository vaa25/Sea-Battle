package model;

import static model.ShipLayout.HORIZONTAL;
import static model.ShipLayout.VERTICAL;

/**
 * Nick:   sobolevstp
 * Date:   8/28/14
 * Time:   00:17
 *
 * @author Stepan Sobolev
 */

enum ShipLayout
{
	VERTICAL,
	HORIZONTAL
}

public class Ship implements AliveChecker
{
	private final int SIZE;
	private ShipLayout layout;
	private Cell[] location;

	public Ship(int SIZE)
	{
		this.SIZE = SIZE;
		this.layout = VERTICAL;
	}

	public int getSIZE()
	{
		return SIZE;
	}

	@Override
	public boolean isAlive()
	{
		for (Cell cell : location) {
			if (cell.getState() == CellState.SHIP) {
				return true;
			}
		}
		return false;
	}

	public void setLocation(Cell[] location)
	{
		this.location = location;
	}

	public ShipLayout getLayout()
	{
		return layout;
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
