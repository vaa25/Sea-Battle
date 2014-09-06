package model;

import model.Cell;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey Nerodenko
 * Date: 27.08.14
 */

public class Ship implements Serializable {
    private int shipLength;
    private boolean vertical;
    private ArrayList<Cell> coordinates = new ArrayList<>();

    public Ship(int shipLength) {
        this.shipLength = shipLength;
        this.vertical = new Random().nextInt(2) == 1;
    }

    public ArrayList<Cell> getCoordinates() {
        return coordinates;
    }

    public int getShipLength() {
        return shipLength;
    }

    public boolean getVertical() {
        return vertical;
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
    }
}
