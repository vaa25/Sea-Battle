package seaBattle.net.amboss;

/**
 * Model Class represents Ship POJO
 */

public class Ship {

    private int shipSize;

    private Ship[] coordinates;

    private boolean isAlive;

    public Ship() {

    }

    public Ship(int shipSize, Ship[] coordinates, boolean isAlive) {
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

    public Ship[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Ship[] coordinates) {
        this.coordinates = coordinates;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    /**
     * Inner static class handel's Ship model service
     */
    public static class ShipService {

        /**
         * Creating new Ship
         *
         * @param size  - ships cell size
         * @param coordinates - array with coordinates
         * @param alive - status of ship
         * @return new Ship Object
         */
        public static Ship setShip(int size, Ship[] coordinates, boolean alive) {

            Ship ship = null;
            try {
                ship = new Ship(size, coordinates, alive);
            } catch (Exception e) {
                /* TODO remove after production */
                e.printStackTrace();
            }
            return ship;
        }

        /**
         * Creating new Ship
         */
        public static Ship setShip() {

            Ship ship = null;
            try {
                ship = new Ship();
            } catch (Exception e) {
                /* TODO remove after production */
                e.printStackTrace();
            }
            return ship;
        }
    }

}