package model;

/**
 * Nick:   sobolevstp
 * Date:   8/28/14
 * Time:   00:14
 *
 * @author Stepan Sobolev
 */
class Game
{
	PlayerController p1controller;
	PlayerController p2controller;

	public Game()
	{
		initializePlayers();
	}

	private void initializePlayers()
	{
		p1controller = new PlayerController();
		p2controller = new PlayerController();
		p1controller.player.enemy = p2controller;
		p2controller.player.enemy = p1controller;
	}

}
