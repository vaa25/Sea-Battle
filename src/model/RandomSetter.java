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
    private List<Ship> ships;
    private List<Ship> shipsToPlace;
    private List<Ship> shipsPlaced;
    private final int width, height;
    private List<Coord> placeable;
    private List<Coord> unPlaceable;

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
        for (Ship ship : ships) {
            if (ship.isPlaced()) shipsPlaced.add(ship);
            else shipsToPlace.add(ship);
        }
    }

    /**
     * Расставляет корабль согласно его внутренним координатам
     *
     * @param ship
     */
    private void placeNewShip(Ship ship) {
        List<Coord> coords = Arrays.asList(ship.getAroundCoords());
        placeable.removeAll(coords);
        unPlaceable.addAll(coords);
        ship.setPlaced(true);
    }

    /**
     * Расставляет все корабли
     *
     * @return
     */
    public boolean setAll() {
        shipsToPlace = ships;
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
        Random random = new Random();
        List<Coord> placeable = new ArrayList<>(this.placeable);
        boolean checked;
        do {
            int r = random.nextInt(placeable.size());
            ship.setCoords(placeable.get(r));
            if (random.nextBoolean()) ship.changeDirection();
            List<Coord> coords = Arrays.asList(ship.getShipCoords());
            if (!(checked = checkCoords(coords))) {
                ship.changeDirection();
                checked = checkCoords(coords);
            }
            placeable.remove(r);
            if (placeable.size() == 0) return false;
        } while (!checked);
        placeNewShip(ship);
        return true;
    }

    /**
     * Проверяет действительно ли данные координаты лежат в пределах незанятых
     *
     * @param coords
     *
     * @return
     */
    private boolean checkCoords(List<Coord> coords) {
        return placeable.containsAll(coords);
    }

    /**
     * Сеттер
     *
     * @param ships
     */
    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }

    /**
     * Расставляет заданные корабли
     *
     * @param shipsToPlace
     *
     * @return true, если успешно
     */
    private boolean set(List<Ship> shipsToPlace) {
        this.shipsToPlace = shipsToPlace;
        return set();
    }

    public List<Ship> getShipsPlaced() {
        return shipsPlaced;
    }

    /**
     * Заполняет массив свободных координат
     */
    private void fillPlaceable() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                placeable.add(new Coord(i, j));
            }
        }
    }

    /**
     * Расставляет корабли согласно спискам shipsPlaced и shipsToPlace
     *
     * @return
     */
    private boolean set() {
        placeable = new ArrayList<>();
        unPlaceable = new ArrayList<>();
        fillPlaceable();
        for (Ship ship : shipsPlaced) {
            placeNewShip(ship);
        }

        for (Ship ship : shipsToPlace) {
            if (!set(ship)) return false;
            ship.setPlaced(true);
            shipsPlaced.add(ship);
        }
        return true;
    }
}
