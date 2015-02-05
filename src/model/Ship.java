package model;

import common.Coord;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Vlasov Alexander
 * Date: 24.08.2014
 * Time: 23:38
 *
 * @author Alexander Vlasov
 */
public class Ship implements Comparable<Ship>, Serializable {
    //    int[][]profile={
//            {1,1,1,1},
//            {0,0,0,0},
//            {0,0,0,0},
//            {0,0,0,0}
//    };
    private int size;
    private Orientation orientation;
    private Coord[] coords;
    private Coord coordLeftUp;
    private int health;
    private boolean placed;

    public Ship() {
    }

    public Ship(int size) {
        this.size = size;
        orientation = Orientation.Horizontal;
        coords = new Coord[size];
        health = size;

    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
        setCoords(coordLeftUp);
    }

    public int getSize() {
        return size;
    }

    public boolean isPlaced() {
        return placed;
    }

    public void setPlaced(boolean placed) {
        this.placed = placed;
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
        return (size - o.size);
    }

    /**
     * Вызывается, когда по кораблю попадают
     */
    public void reduceHealth() {
        health--;
    }

    /**
     * @return true , если корабль не потоплен
     */
    public boolean isAlive() {
        return (health > 0);
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
            if (orientation == Orientation.Horizontal) coords[i] = coords[i - 1].getRight();
            else if (orientation == Orientation.Vertical) coords[i] = coords[i - 1].getDown();
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

    public void changeOrientation() {
        if (orientation == Orientation.Horizontal) {
            orientation = Orientation.Vertical;
        } else {
            orientation = Orientation.Horizontal;
        }
        setCoords(coordLeftUp);
    }

    /**
     * Координаты места вокруг корабля и самого корабля, чтобы не столкнуться с другим кораблем
     *
     * @return
     */
    public Coord[] getAroundCoords() {
        if (orientation == Orientation.Horizontal) {
            return getHorizontalAroundCoords();
        } else {
            return getVerticalAroundCoords();
        }
    }

    private Coord[] getVerticalAroundCoords() {
        Coord[] result = new Coord[size * 3 + 6];
        Coord coordUp = coords[0];
        result[0] = coordUp.getLeftUp();
        result[1] = coordUp.getUp();
        result[2] = coordUp.getRightUp();
        Coord coordDown = coords[size - 1];
        result[3] = coordDown.getLeftDown();
        result[4] = coordDown.getDown();
        result[5] = coordDown.getRightDown();
        for (int i = 0; i < size; i++) {
            result[6 + i] = coords[i].getLeft();
            result[6 + i + size] = coords[i].getCenter();
            result[6 + i + size + size] = coords[i].getRight();
        }
        return result;
    }

    private Coord[] getHorizontalAroundCoords() {
        Coord[] result = new Coord[size * 3 + 6];
        Coord coordLeft = coords[0];
        result[0] = coordLeft.getLeftUp();
        result[1] = coordLeft.getLeft();
        result[2] = coordLeft.getLeftDown();
        Coord coordRight = coords[size - 1];
        result[3] = coordRight.getRightUp();
        result[4] = coordRight.getRight();
        result[5] = coordRight.getRightDown();
        for (int i = 0; i < size; i++) {
            result[6 + i] = coords[i].getUp();
            result[6 + i + size] = coords[i].getCenter();
            result[6 + i + size + size] = coords[i].getDown();
        }
        return result;
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
                if (coord.equals(anotherCoords)) {
                    return true;
                }
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

    public void kill() {
        health = 0;
    }

    void setRandomOrientation() {
        if (new Random().nextBoolean()) {
            changeOrientation();
        }
    }
}
