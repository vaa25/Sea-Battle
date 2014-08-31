package model;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

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
	Map<Point, Cell> cells;

	public Field()
	{
		this.cells = new HashMap<>();
		for (int i = 1; i <= SIZE; i++) {
			for (int j = 1; j <= SIZE; j++) {
				cells.put(new Point(i,j), new Cell());
			}
		}
	}
}