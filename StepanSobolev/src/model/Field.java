package model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Nick:   sobolevstp
 * Date:   8/28/14
 * Time:   00:28
 *
 * @author Stepan Sobolev
 */
public class Field implements TakingShots
{
	private final int SIZE = 10;
	private Cell[][] cells;
	private ArrayList<Ship> ships;

	public Field()
	{
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				cells[i][j] = new Cell(i, j);
			}
		}
		initializeShips();
	}

	private void initializeShips()
	{
		ships = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			int qt = 4;
			int size = 1;
			for (int j = 0; j < qt; j++) {
				ships.add(new Ship(size));
			}
			qt--;
			size++;
		}
	}

	@Override
	public ShotResult getShot(Point p)
	{
		return cells[p.x][p.y].getShot();
	}

//	public boolean locateShip(Ship ship)
//	{
//		ship.setLocation();
//	}
}
