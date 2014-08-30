package model;

import java.awt.*;

/**
 * Nick:   sobolevstp
 * Date:   8/28/14
 * Time:   00:15
 *
 * @author Stepan Sobolev
 */
class Player implements TakingShots
{
	private Field field;
	private TakingShots enemy;

	public ShotResult shoot(Point p)
	{
		return enemy.getShot(p);
	}

	@Override
	public ShotResult getShot(Point p)
	{
		return field.getShot(p);
	}

	public void setEnemy(TakingShots enemy)
	{
		this.enemy = enemy;
	}
}
