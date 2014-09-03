package model.objects;

import model.enums.CellState;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static model.enums.CellState.EMPTY;

/**
 * Nick:   sobolevstp
 * Date:   8/28/14
 * Time:   00:28
 *
 * @author Stepan Sobolev
 */
public class Field
{
	public final int SIZE = 10;
	private Map<Point, Cell> cells;

	public Field()
	{
		this.cells = new HashMap<Point, Cell>();
		for (int i = 1; i <= SIZE; i++) {
			for (int j = 1; j <= SIZE; j++) {
				cells.put(new Point(i,j), new Cell());
			}
		}
	}

	public Field(CellState state)
	{
		this.cells = new HashMap<Point, Cell>();
		for (int i = 1; i <= SIZE; i++) {
			for (int j = 1; j <= SIZE; j++) {
				cells.put(new Point(i,j), new Cell(state));
			}
		}
	}

	public Cell getCell(Point p)
	{
		return cells.get(p);
	}


}