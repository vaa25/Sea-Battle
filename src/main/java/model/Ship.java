package main.java.model;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey Nerodenko
 * Date: 27.08.14
 */
public class Ship {
    private int x, y;
    private int shipLength;
    private int vertical;
    private ArrayList<Cell> coordinates = new ArrayList<>();

    public Ship(int x, int y, int shipLength, int vertical) {
        this.x = x;
        this.y = y;
        this.vertical = vertical;
        this.shipLength = shipLength;
    }

    public ArrayList<Cell> getCoordinates() {
        return coordinates;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getShipLength() {
        return shipLength;
    }

    public int getVertical() {
        return vertical;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setShipLength(int shipLength) {
        this.shipLength = shipLength;
    }

    public void setVertical(int vertical) {
        this.vertical = vertical;
    }
}
