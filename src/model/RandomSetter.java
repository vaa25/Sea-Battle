package model;

import common.Coord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * класс служит для автоматической расстановки части или всех кораблей
 *
 * @author Alexander Vlasov
 */
public class RandomSetter {
    private final int width, height;
    private List<Ship> ships;
    private List<Ship> shipsToPlace;
    private List<Ship> shipsPlaced;
    private List<Coord> placeableCoords;
    private List<Coord> unPlaceableCoords;

    public RandomSetter(int width, int height) {
        this.shipsToPlace = new ArrayList<>();
        this.shipsPlaced = new ArrayList<>();
        this.width = width;
        this.height = height;
    }

    /**
     * Разделяет ships на уже расставленные и те, что нужно расставить
     */
    private void divideShipsList() {
        shipsToPlace.clear();
        shipsPlaced.clear();
        for (Ship ship : ships) {
            if (ship.isPlaced()) {
                shipsPlaced.add(ship);
            } else {
                shipsToPlace.add(ship);
            }
        }
    }

    /**
     * Расставляет все корабли
     *
     * @return
     */
    public boolean setAll() {
        shipsToPlace.clear();
        shipsPlaced.clear();
        shipsToPlace.addAll(ships);
        return set();
    }

    /**
     * Расставляет еще не расставленные корабли
     *
     * @return
     */
    public boolean setRest() {
        divideShipsList();
        return set();

    }

    /**
     * Расставляет корабль в свободное место
     *
     * @param ship
     *
     * @return
     */
    private boolean set(Ship ship) {
        class ShipPlacer {
            private List<Coord> coordList;

            public ShipPlacer() {
                coordList = new ArrayList<>(placeableCoords);
            }

            boolean invoke() {
                do {
                    if (coordList.size() == 0) {
                        return false;
                    }
                    setRandomPlaceableCoordsInShip();
                    ship.setRandomOrientation();
                } while (!trySetPlaceableOrientation());
                placeNewShip(ship);
                return true;
            }

            private void setRandomPlaceableCoordsInShip() {
                int randomPlaceableCoordIndex = new Random().nextInt(coordList.size());
                ship.setCoords(coordList.get(randomPlaceableCoordIndex));
                coordList.remove(randomPlaceableCoordIndex);
            }

            private boolean trySetPlaceableOrientation() {
                if (isShipPlaceable()) {
                    return true;
                }
                ship.changeOrientation();
                return isShipPlaceable();
            }

            private boolean isShipPlaceable() {
                return placeableCoords.containsAll(Arrays.asList(ship.getShipCoords()));
            }
        }
        return new ShipPlacer().invoke();
    }

    /**
     * Расставляет корабль согласно его внутренним координатам
     *
     * @param ship
     */
    private void placeNewShip(Ship ship) {
        List<Coord> coords = Arrays.asList(ship.getAroundCoords());
        placeableCoords.removeAll(coords);
        unPlaceableCoords.addAll(coords);
//        ship.setPlaced(true);
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }


    /**
     * Заполняет массив свободных координат
     */
    private void setPlaceableCoords() {
        placeableCoords = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                placeableCoords.add(new Coord(i, j));
            }
        }
    }

    /**
     * Расставляет корабли согласно спискам shipsPlaced и shipsToPlace
     *
     * @return
     */
    private boolean set() {
        unPlaceableCoords = new ArrayList<>();
        setPlaceableCoords();
        placeShipsPlaced();
        return placeShipsToPlace();
    }

    private boolean placeShipsToPlace() {
        for (Ship ship : shipsToPlace) {
            if (!set(ship)) return false;
//            ship.setPlaced(true);
            shipsPlaced.add(ship);
        }
        return true;
    }

    private void placeShipsPlaced() {
        for (Ship ship : shipsPlaced) {
            placeNewShip(ship);
            ship.setPlaced(true);
        }
    }
}
