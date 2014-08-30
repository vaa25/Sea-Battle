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
	private Player player1;
	private Player player2;

	public Game()
	{
		player1 = new Player();
		player2 = new Player();
		player1.setEnemy(player2);
		player2.setEnemy(player1);
	}

}
