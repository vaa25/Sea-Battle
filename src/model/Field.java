package model;


import common.Coord;
import common.ShootResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Vlasov Alexander
 * Date: 24.08.2014
 * Time: 23:26
 *
 * @author Alexander Vlasov
 */
public class Field {
    private Cell[][] field;
    private List<Ship> ships;
    private final int width, height;
    private int killed;


    public Field(int width, int height) {
        this.width = width;
        this.height = height;
        field = new Cell[width][height];
        ships = new ArrayList<>();
        createCells();
        killed = 0;

    }

    public void createCells() {
        for (int i = 0; i < field.length; i++) {
            Cell[] collumn = field[i];
            for (int j = 0; j < collumn.length; j++) {
                collumn[j] = new Cell(new Coord(i, j));

            }

        }
    }

    public Cell[][] getField() {
        return field;
    }

    /**
     * проверяет, можно ли расположить корабль с заданными координатами на поле с расставленными ранее кораблями
     *
     * @param ship
     *
     * @return
     */
    public boolean canPlace(Ship ship) {
        if (!inBorders(ship)) return false;
        for (int i = 0; i < ships.size(); i++) {
            Ship alreadyPlaced = ships.get(i);
            if (ship.isCrossing(alreadyPlaced)) return false;
        }
        return true;
    }

    /**
     * Устанавливает в ячейки корабль
     * Удаляет временные корабли, установленные при восстановлении убитых кораблей, когда поле является вражьим
     *
     * @param ship
     */
    public void place(Ship ship) {
        ships.add(ship);
        for (Coord coord : ship.getShipCoords()) {
            Cell cell = getCell(coord);
            Ship temp = cell.getShip();
            if (temp != null) ships.remove(temp);
            cell.setShip(ship);
        }
    }

    /**
     * Проверяет, находится ли весь корабль в границах поля
     *
     * @param ship
     *
     * @return true, если находится
     */
    private boolean inBorders(Ship ship) {
        for (Coord coord : ship.getShipCoords()) {
            if (coord.getX() < 0 || coord.getY() < 0 || coord.getX() >= width || coord.getY() >= height) return false;
        }
        return true;
    }

    /**
     * Ячейки вдали от кораблей
     *
     * @return
     */
    private List<Coord> getFreeCoords() {
        List<Coord> res = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                res.add(new Coord(i, j));
            }
        }
        for (Ship ship : ships) {
            for (int i = 0; i < ship.getAroundCoords().length; i++) {
                Coord coord = ship.getAroundCoords()[i];
                res.remove(coord);

            }
        }
        return res;
    }

    /**
     * Удаляет из ячеек корабль
     *
     * @param ship
     */
    public void unPlace(Ship ship) {
        if (ships.remove(ship)) {
            for (int i = 0; i < ship.getShipCoords().length; i++) {
                Coord coord = ship.getShipCoords()[i];
                getCell(coord).setShip(null);
            }
        }
    }

    /**
     * расставляет рандомно указанные корабли
     *
     * @param ships
     */
    public void setRandom(List<Ship> ships) {
//        Collections.sort(ships);
        while (!trySetRandom(ships)) ;
    }

    /**
     * Делает попытку рандомно поставить корабли на поле
     *
     * @param ships список расставляемых кораблей
     *
     * @return true, если все корабли в границах поля и не мешают друг другу
     */
    public boolean trySetRandom(List<Ship> ships) {

        for (int i = 0; i < ships.size(); i++) {
            Ship ship = ships.get(i);
            Random random = new Random();
            boolean placed = false;
            //делает десять попыток поставить каждый следующий корабль.
            //можно было бы сделать и через while(!placed), но в таком случае возможно зависание при большой плотности кораблей
            for (int j = 0; j < 10; j++) {
                int x = random.nextInt(width);
                int y = random.nextInt(height);
                ship.setCoords(new Coord(x, y));
                if (random.nextBoolean()) ship.changeDirection();
                if (!canPlace(ship)) {
                    ship.changeDirection();
                    if (!canPlace(ship))continue;
                }
                place(ship);
                placed = true;
                break;
            }
            if (!placed) {
                unPlace(ships);
                return false;
            }
        }
        return true;
    }

    public ShootResult shoot(Coord coord) {
        Cell cell = getCell(coord);
        Ship ship = cell.getShip();
        if (ship != null) {
            if (!cell.isShoot()) {
                cell.setShoot(true);
                ship.shoot();
            }
            if (ship.isAlive()) return ShootResult.HURT;
            else {
                addKilled();
                return ShootResult.KILLED;
            }
        } else {
            cell.setShoot(true);
            return ShootResult.MISSED;
        }
    }

    /**
     * Помечает ячейку как обстрелянная
     *
     * @param coord
     */
    public void setShoot(Coord coord) {
        getCell(coord).setShoot(true);
    }

    public Cell getCell(Coord coord) {
        return field[coord.getX()][coord.getY()];
    }

    private void unPlace(List<Ship> ships) {
        for (Ship ship : ships) {
            unPlace(ship);
        }
    }

    public int getKilled() {
        return killed;
    }

    public int getShipSize() {
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
                    if (cell.isShoot()) System.out.print(". ");
                    else System.out.print("  ");
                } else {
                    if (cell.isShoot()) System.out.print("* ");
                    else System.out.print("O ");
                }
            }
            System.out.println();
        }
    }
}
