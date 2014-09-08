package model;

import common.Coord;

/**
 * @author Alexander Vlasov
 */
public class Cell {
    private Ship ship;
    private Coord coord;
    private boolean shoot;
    private boolean hurt;
    public Cell(Coord coord) {
        this.coord = coord;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public boolean isShoot() {
        return shoot;
    }

    public void setShoot(boolean shoot) {
        this.shoot = shoot;
    }

    public boolean isHurt() {
        return hurt;
    }

    public void setHurt(boolean hurt) {
        this.hurt = hurt;
    }
}
