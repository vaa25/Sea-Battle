package model.objects;

import model.enums.ShipLayout;
import model.interfaces.AliveChecker;

import java.util.LinkedList;

import static model.enums.CellState.SHIP;
import static model.enums.ShipLayout.HORIZONTAL;
import static model.enums.ShipLayout.VERTICAL;

public class Ship implements AliveChecker
{
	public final int SIZE;
	public ShipLayout layout;
	public LinkedList<Cell> location;

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

	public boolean isLocated()
	{
		return location.size() > 0;
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