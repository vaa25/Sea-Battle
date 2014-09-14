package model.objects.field;

import model.enums.CellState;
import model.interfaces.AliveChecker;

public class Cell
{
	public CellState state;
	public AliveChecker locatedShip;

	public Cell()
	{
	}

	public Cell(CellState state)
	{
		this.state = state;
	}

}