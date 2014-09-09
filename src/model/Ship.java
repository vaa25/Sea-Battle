package model;

import common.Coord;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Vlasov Alexander
 * Date: 24.08.2014
 * Time: 23:38
 *
 * @author Alexander Vlasov
 */
public class Ship implements Comparable<Ship> {
    //    int[][]profile={
//            {1,1,1,1},
//            {0,0,0,0},
//            {0,0,0,0},
//            {0,0,0,0}
//    };
    private final int size;
    private Direction direction;
    private Coord[] coords;
    private Coord coordLeftUp;
    private int health;
    private boolean placed;

    public Ship(int size) {
        this.size = size;
        direction = Direction.Horizontal;
        coords = new Coord[size];
        health = size;
    }

    public int getSize() {
        return size;
    }

    public void setPlaced(boolean placed) {
        this.placed = placed;
    }

    public boolean isPlaced() {
        return placed;
    }

    /**
     * сравнение по размеру
     *
     * @param o
     *
     * @return положительное число, если аргумент меньше
     */
    @Override
    public int compareTo(Ship o) {
        return size - o.size;
    }

    public static enum Direction {
        Vertical, Horizontal
    }

    /**
     * Вызывается, когда по кораблю попадают
     */
    public void shoot() {
        health--;
    }

    /**
     * @return true , если корабль не потоплен
     */
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * Рассчитывает координаты всего корабля по левой верхней координате
     *
     * @param coordLeftUp
     */
    public void setCoords(Coord coordLeftUp) {
        this.coordLeftUp = coordLeftUp;
        coords[0] = coordLeftUp;
        for (int i = 1; i < coords.length; i++) {
            if (direction == Direction.Horizontal) coords[i] = coords[i - 1].getRight();
            else if (direction == Direction.Vertical) coords[i] = coords[i - 1].getDown();
        }
    }

    /**
     * Координаты всего корабля, чтобы проверить не вышел ли за края карты
     *
     * @return
     */
    public Coord[] getShipCoords() {
        return coords;
    }

    public void changeDirection() {
        if (direction == Direction.Horizontal) direction = Direction.Vertical;
        else direction = Direction.Horizontal;
        setCoords(coordLeftUp);
    }

    /**
     * Координаты места вокруг корабля и самого корабля, чтобы не столкнуться с другим кораблем
     *
     * @return
     */
    public Coord[] getAroundCoords() {
        Coord[] res = new Coord[size * 3 + 6];
        if (direction == Direction.Horizontal) {
            Coord coordLeft = coords[0];
            res[0] = coordLeft.getLeftUp();
            res[1] = coordLeft.getLeft();
            res[2] = coordLeft.getLeftDown();
            Coord coordRight = coords[size - 1];
            res[3] = coordRight.getRightUp();
            res[4] = coordRight.getRight();
            res[5] = coordRight.getRightDown();
            for (int i = 0; i < size; i++) {
                res[6 + i] = coords[i].getUp();
                res[6 + i + size] = coords[i].getCenter();
                res[6 + i + size + size] = coords[i].getDown();
            }
        } else {
            Coord coordUp = coords[0];
            res[0] = coordUp.getLeftUp();
            res[1] = coordUp.getUp();
            res[2] = coordUp.getRightUp();
            Coord coordDown = coords[size - 1];
            res[3] = coordDown.getLeftDown();
            res[4] = coordDown.getDown();
            res[5] = coordDown.getRightDown();
            for (int i = 0; i < size; i++) {
                res[6 + i] = coords[i].getLeft();
                res[6 + i + size] = coords[i].getCenter();
                res[6 + i + size + size] = coords[i].getRight();
            }
        }

        return res;
    }

    /**
     * Проверяет по координатам находится ли корабль в пределах одной клетки или ближе
     *
     * @param ship
     *
     * @return true если находится
     */
    public boolean isCrossing(Ship ship) {
        for (Coord coord : coords) {
            Coord[] aroundCoords = ship.getAroundCoords();
            for (Coord anotherCoords : aroundCoords) {
                if (coord.equals(anotherCoords)) return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String res = "size=" + size + " ";
        if (coordLeftUp != null) {
            for (Coord coord : coords) {
                res += coord.toString() + " ";
            }
        }
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ship ship = (Ship) o;

        if (!Arrays.equals(coords, ship.coords)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = size;
        result = 31 * result + Arrays.hashCode(coords);
        result = 31 * result + coordLeftUp.hashCode();
        return result;
    }
}
