package model.objects;

import model.enums.CellState;
import model.interfaces.AliveChecker;

public class Cell
{
	public CellState state;
	public AliveChecker locatedShip;

	Cell(CellState state)
	{
		this.state = state;
	}
}