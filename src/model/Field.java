package model;

/**
 * Nick:   sobolevstp
 * Date:   8/28/14
 * Time:   00:28
 *
 * @author Stepan Sobolev
 */
public class Field
{
	final int SIZE = 10;
	Cell[][] cells;

	public Field()
	{
		this.cells = new Cell[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				cells[i][j] = new Cell(i + 1, j + 1);
			}
		}
	}
}