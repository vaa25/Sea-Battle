package model.objects.flotilla;

import java.util.ArrayList;

/**
 * Nick:   sobolevstp
 * Date:   9/13/14
 * Time:   00:43
 *
 * @author Stepan Sobolev
 */
public class Flotilla
{
	/**
	 * Player ships
	 */
	public ArrayList<Ship> ships;

	public Flotilla()
	{
		this.ships = generateListOfShips();
	}

	/**
	 * Метод создает массив с кораблями для игрока
	 */
	public ArrayList<Ship> generateListOfShips()
	{
		ArrayList<Ship> ships = new ArrayList<>();
		for (int size = 1; size <= 4; size++) {
			for (int qt = 1; qt <= 5 - size; qt++) { // size + qt = 5  =>  qt = 5 - size;
				ships.add(new Ship(size));
			}
		}

		return ships;
	}

	/**
	 * Метод очищает поле от кораблей
	 */
	public void reset()
	{
		this.ships = generateListOfShips();
	}

}
