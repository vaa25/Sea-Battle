package model;

import java.awt.*;
import java.util.ArrayList;

import static model.CellState.EMPTY;
import static model.CellState.FOG;

/**
 * Nick:   sobolevstp
 * Date:   8/28/14
 * Time:   00:15
 *
 * @author Stepan Sobolev
 */
public class Player
{
	TakingShots enemy;
	Field field;
	Field enemyField;
	ArrayList<Ship> ships;

	public Player()
	{
		this.field = new Field(EMPTY);
		this.enemyField = new Field(FOG);
		ships = generateListOfShips();
	}

	/**
	 * Метод создает массив с кораблями для игрока
	 */
	private ArrayList<Ship> generateListOfShips()
	{
		ArrayList<Ship> ships = new ArrayList<>();
		int qt = 4;
		int size = 1;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < qt; j++) {
				ships.add(new Ship(size));
			}
			qt--;
			size++;
		}

		return ships;
	}

	public Cell getCellFromField(Point p)
	{
		return field.cells.get(p);
	}
}