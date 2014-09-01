package model;

/**
 * Nick:   sobolevstp
 * Date:   8/28/14
 * Time:   00:29
 *
 * @author Stepan Sobolev
 */

public enum CellState
{
	FOG,          // туман, будет использоваться для отображения вражеского поля
	EMPTY,        // пустая
	SHELLED,      // обстрелянная
	SHIP,         // корабль
	DAMAGED_SHIP, // раненный корабль
	OUTLINE       // контур корабля
}