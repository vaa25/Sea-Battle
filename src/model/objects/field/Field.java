package model.objects.field;

import model.enums.CellState;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static model.enums.CellState.EMPTY;

/**
 * Nick:   sobolevstp
 * Date:   9/13/14
 * Time:   03:45
 *
 * @author Stepan Sobolev
 */
public class Field
{
	/**
	 * Размер стороны игрового поля;
	 */
	public final int FIELD_SIZE = 10;
	/**
	 * Ячейки
	 */
	private Map<Point, Cell> cells;

	public Field(CellState state)
	{
		cells = generateMapOfCells(state);
	}

	/**
	 * Метод создает массив с ячейками
	 */
	private Map<Point, Cell> generateMapOfCells(CellState state)
	{
		Map<Point, Cell> cells = new HashMap<>();

		for (int i = 1; i <= FIELD_SIZE; i++) {
			for (int j = 1; j <= FIELD_SIZE; j++) {
				cells.put(new Point(i, j), new Cell(state));
			}
		}

		return cells;
	}

	/**
	 * Метод возвращает ссылку на ячейку.
	 *
	 * @param p Координаты ячейки
	 * @return Ячейка, находящаяся в указанной точке.
	 */
	public Cell getCell(Point p)
	{
		return cells.get(p);
	}

	/**
	 * Устанавливаем состояние всех ячеек.
	 *
	 * @param state требуемое состояние ячейки
	 */
	public void setCellsStates(CellState state)
	{
		this.cells = generateMapOfCells(state);
	}

	/**
	 * Метод очищает поле от кораблей и присваивает всем ячейкам статус пустой
	 */
	public void reset()
	{
		setCellsStates(EMPTY);
	}
}
