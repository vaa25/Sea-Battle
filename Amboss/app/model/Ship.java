package model;

/**
 * Model Class represents Ship POJO
 */

public class Ship {

    private int shipSize;

    private Cell[] coordinates;

    private boolean isAlive;

    public Ship() {

    }

    public Ship(int shipSize, Cell[] coordinates, boolean isAlive) {
        this.shipSize = shipSize;
        this.coordinates = coordinates;
        this.isAlive = isAlive;
    }

    public int getShipSize() {
        return shipSize;
    }

    public void setShipSize(int shipSize) {
        this.shipSize = shipSize;
    }

    public Cell[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Cell[] coordinates) {
        this.coordinates = coordinates;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }



}