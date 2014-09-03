package model.objects;

import model.enums.CellState;
import model.interfaces.AliveChecker;

import static model.enums.CellState.EMPTY;

public class Cell
{
	public CellState state;
	public AliveChecker locatedShip;

	public Cell()
	{
		// default state
		this.state = EMPTY;
	}

	Cell(CellState state)
	{
		this.state = state;
	}
}