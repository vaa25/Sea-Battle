package model.objects;

/**
 * Nick:   sobolevstp
 * Date:   8/28/14
 * Time:   00:14
 *
 * @author Stepan Sobolev
 */
public class Game
{
	Player p1;
	Player p2;

	public Game()
	{
		initializePlayers();
	}

	private void initializePlayers()
	{
		p1 = new Player();
		p2 = new Player();
	}

}