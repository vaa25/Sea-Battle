package model;


import common.Coord;
import common.ShootResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: Vlasov Alexander
 * Date: 24.08.2014
 * Time: 23:26
 *
 * @author Alexander Vlasov
 */
public class MyField extends Field {

    public MyField(int width, int height) {
        super(width, height);
        createCells();
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
        for (Ship alreadyPlaced : ships) {
            if (ship.isCrossing(alreadyPlaced)) return false;
        }
        return true;
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

    public void place(List<Ship> ships) {
        for (Ship ship : ships) {
            place(ship);
        }
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

    public List<Ship> getAliveShips() {
        return ships.stream().filter(Ship::isAlive).collect(Collectors.toList());
    }
}
