package model;


import common.Coord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vlasov Alexander
 * Date: 24.08.2014
 * Time: 23:26
 *
 * @author Alexander Vlasov
 */
public abstract class Field {
    protected final int width;
    protected final int height;
    protected List<Ship> ships;
    private Cell[][] field;
    private int killed;

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
        field = new Cell[width][height];
        ships = new ArrayList<>();
        createCells();
        killed = 0;

    }

    public List<Ship> getShips() {
        return ships;
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }

    protected Ship getShip(Coord coord) {
        return field[coord.getX()][coord.getY()].getShip();
    }

    public void createCells() {
        for (int i = 0; i < field.length; i++) {
            Cell[] collumn = field[i];
            for (int j = 0; j < collumn.length; j++) {
                collumn[j] = new Cell();
            }

        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * Помечает ячейку как обстрелянная
     *
     * @param coord
     */
    public void setShoot(Coord coord) {
        getCell(coord).setShoot(true);
    }

    /**
     * Устанавливает в ячейки корабль
     *
     * @param ship
     */
    public void place(Ship ship) {
        ship.setPlaced(true);
        for (Coord coord : ship.getShipCoords()) {
            getCell(coord).setShip(ship);
        }
    }

    public void unplace(Ship ship) {
        ship.setPlaced(false);
        for (Coord coord : ship.getShipCoords()) {
            getCell(coord).setShip(null);
        }
    }
    public Cell getCell(Coord coord) {
        return field[coord.getX()][coord.getY()];
    }

    public int getKilled() {
        return killed;
    }

    public int getShipsSize() {
        return ships.size();
    }

    public void addKilled() {
        killed++;
    }

    public boolean isLoose() {
        return ships.size() == killed;
    }

    public void printField() {
        for (Cell[] cells : field) {
            for (Cell cell : cells) {
                if (cell.getShip() == null) {
                    if (cell.isHurt()) System.out.println("+ ");
                    else if (cell.isShoot()) System.out.print(". ");
                    else System.out.print("  ");
                } else {
                    if (cell.isShoot()) System.out.print("* ");
                    else System.out.print("O ");
                }
            }
            System.out.println();
        }
    }

    public void clear() {
        ships.clear();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j].clear();
            }
        }
    }
}
