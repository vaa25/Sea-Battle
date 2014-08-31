package model;

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
class Player
{
	TakingShots enemy;
	Field field;
	Field enemyField;
	ArrayList<Ship> ships;

	Player()
	{
		this.field = new Field(EMPTY);
		this.enemyField = new Field(FOG);
	}
}