package model;

import java.util.ArrayList;

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
	ArrayList<Ship> ships;

	Player()
	{
		this.field = new Field();
	}
}