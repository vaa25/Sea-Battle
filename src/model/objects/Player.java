package model.objects;

import model.services.LocationManager;
import model.services.ShotHandler;

import java.util.ArrayList;

import static model.enums.CellState.EMPTY;
import static model.enums.CellState.FOG;

/**
 * Nick:   sobolevstp
 * Date:   8/28/14
 * Time:   00:15
 *
 * @author Stepan Sobolev
 */
public class Player
{
	public Field enemyField;
	public Field playerField;
	public ArrayList<Ship> ships;

	public ShotHandler shotHandler;
	public LocationManager locationManager;

	public Player()
	{
		enemyField = new Field(FOG);
		playerField = new Field(EMPTY);
		ships = generateListOfShips();
		shotHandler = new ShotHandler(playerField, enemyField);
		locationManager = new LocationManager(playerField, ships);
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