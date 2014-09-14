package model.objects;

import model.objects.field.Field;
import model.objects.flotilla.Flotilla;
import model.services.LocationManager;
import model.services.ShotHandler;

import static model.enums.CellState.EMPTY;
import static model.enums.CellState.UNDEFINED;

/**
 * Nick:   sobolevstp
 * Date:   8/28/14
 * Time:   00:15
 *
 * @author Stepan Sobolev
 */
public class Player
{
	private Flotilla flotilla;
	public Field playerField;
	public Field enemyField;

	public ShotHandler shotHandler;
	public LocationManager locationManager;

	public Player()
	{
		flotilla = new Flotilla();
		playerField = new Field(EMPTY);
		enemyField = new Field(UNDEFINED);
		shotHandler = new ShotHandler(playerField, enemyField);
		locationManager = new LocationManager(playerField, flotilla);
	}

}