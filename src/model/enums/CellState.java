package model.enums;

/**
 * Nick:   sobolevstp
 * Date:   8/28/14
 * Time:   00:29
 *
 * @author Stepan Sobolev
 */

public enum CellState
{
	EMPTY,         // пустая
	SHELLED,       // обстрелянная
	OUTLINE,       // контур корабля
	SHIP,          // корабль
	DAMAGED_SHIP,  // раненный корабль
	DESTROYED_SHIP,// убитый корабль
	UNDEFINED,     // неизвестно
}