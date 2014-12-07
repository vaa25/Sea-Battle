package model;

/**
 * @author Alexander Vlasov
 */
public class Cell {
    private Ship ship;
    private boolean shoot;
    private boolean hurt;

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

    public void clear() {
        ship = null;
        shoot = false;
        hurt = false;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "ship=" + ship +
                ", shoot=" + shoot +
                ", hurt=" + hurt +
                '}';
    }
}
