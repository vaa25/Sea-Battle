package model;

import common.Coord;

/**
 * @author Alexander Vlasov
 */
public class Bounds {
    private int width;
    private int height;

    public Bounds(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public boolean isInBounds(Coord coord) {
        return ((coord.getX() >= 0) && (coord.getY() >= 0) && (coord.getX() < width) && (coord.getY() < height));
    }

    public boolean isInBounds(Ship ship) {
        for (Coord coord : ship.getShipCoords()) {
            if (!isInBounds(coord)) return false;
        }
        return true;
    }
}
