package model;

import java.util.ArrayList;

/**
 * Nick:   sobolevstp
 * Date:   8/28/14
 * Time:   00:17
 *
 * @author Stepan Sobolev
 */
public class Ship implements AliveChecker
{
	private final int SIZE;
	private ArrayList<Cell> location;

	public Ship(int SIZE)
	{
		this.SIZE = SIZE;
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

	public void setLocation(ArrayList<Cell> location)
	{
		this.location = location;
	}
}
