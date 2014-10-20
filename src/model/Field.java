package model;

import java.util.ArrayList;

public class Field {
    private static final int GRID_ROWS_AMOUNT = 10;
    private static final int GRID_COLUMNS_AMOUNT = 10;

    private Cell[][] grid = new Cell[GRID_ROWS_AMOUNT][GRID_COLUMNS_AMOUNT];
    private ArrayList<Ship> ships = new ArrayList<Ship>();

    public Field() {
        for (int row = 0; row < GRID_ROWS_AMOUNT; row++) {
            for (int column = 0; column < GRID_COLUMNS_AMOUNT; column++) {
                grid[row][column] = new Cell(row, column);
            }
        }
    }

    public Cell getCell(int row, int column) {
        return grid[row][column];
    }

    public Ship locateShip(Cell firstCell, Direction direction, int shipSize) {
        Cell[] shipCells = new Cell[shipSize];
        shipCells[0] = firstCell;

        if (direction == Direction.HORIZONTAL) {
            for (int i = 1; i < shipSize; i++) {
                shipCells[i] = getCell(firstCell.getRowPosition(), firstCell.getColumnPosition() + i);
            }
        }
        else if (direction == Direction.VERTICAL) {
            for (int i = 1; i < shipSize; i++) {
                shipCells[i] = getCell(firstCell.getRowPosition() + i, firstCell.getColumnPosition());
            }
        }

        ships.add(new Ship(shipCells));
        return ships.get(ships.size() - 1);
    }

    public enum Direction {
        HORIZONTAL, VERTICAL
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int letterNumber = 0; letterNumber < GRID_COLUMNS_AMOUNT; letterNumber++) {
            int letterKeycode = 'A' + letterNumber;
            builder.append(" " + (char)letterKeycode);
        }
        builder.append("\n");

        for (int row = 0; row < GRID_ROWS_AMOUNT; row++) {
            builder.append((row + 1) + " ");

            for (int column = 0; column < GRID_COLUMNS_AMOUNT; column++) {
                builder.append(" " + grid[row][column]);
            }
            builder.append("\n");
        }

        return builder.toString();
    }
}