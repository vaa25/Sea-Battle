package model.objects;

import model.services.LocationManager;
import model.services.ShotHandler;

import java.util.ArrayList;

import static model.enums.CellState.EMPTY;

/**
 * Nick:   sobolevstp
 * Date:   8/28/14
 * Time:   00:15
 *
 * @author Stepan Sobolev
 */
public class Player
{
	public Field field;
	public ArrayList<Ship> ships;

	ShotHandler shotHandler;
	LocationManager locationManager;

	public Player()
	{
		field = new Field(EMPTY);
		ships = generateListOfShips();
		locationManager = new LocationManager(field, ships);
		shotHandler = new ShotHandler(field);
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

}