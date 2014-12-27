package model;

import common.ShootResult;

/**
 * @author Alexander Vlasov
 */
public class Cell {
    private Ship ship;
    private boolean shooted;
    private boolean hurted;

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public boolean isShooted() {
        return shooted;
    }

    public void setShooted(boolean shooted) {
        this.shooted = shooted;
    }

    public boolean isHurted() {
        return hurted;
    }

    public void setHurted(boolean hurted) {
        this.hurted = hurted;
    }

    public void clear() {
        ship = null;
        shooted = false;
        hurted = false;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "ship=" + ship +
                ", shooted=" + shooted +
                ", hurted=" + hurted +
                '}';
    }

    public ShootResult shoot() {
        if (shooted) return null;
        setShooted(true);
        if (hasShip()) {
            ship.reduceHealth();
            hurted = true;
            if (ship.isAlive()) {
                return ShootResult.HURTED;
            } else {
                return ShootResult.KILLED;
            }
        } else {
            return ShootResult.MISSED;
        }
    }

    private boolean hasShip() {
        return ship != null;
    }
}
