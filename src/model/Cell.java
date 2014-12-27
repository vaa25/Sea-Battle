package model;

/**
 * @author Alexander Vlasov
 */
public class Cell {
    private Ship ship;
    private boolean shooted;
    private boolean hurt;

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

    public boolean isHurt() {
        return hurt;
    }

    public void setHurt(boolean hurt) {
        this.hurt = hurt;
    }

    public void clear() {
        ship = null;
        shooted = false;
        hurt = false;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "ship=" + ship +
                ", shooted=" + shooted +
                ", hurt=" + hurt +
                '}';
    }
}
