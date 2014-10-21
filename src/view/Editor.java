package view;

import common.Coord;
import model.MyField;
import model.Orientation;
import model.RandomSetter;
import model.Ship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alexander Vlasov
 */
public class Editor {
    private List<Ship> placed;
    private Ship selected;
    private Map<Integer, Integer> available;
    private MyField myField;
    private RandomSetter randomSetter;

    public MyField getMyField() {
        return myField;
    }

    public Editor() {
        available = new HashMap<>();
        initAvailable();
        myField = new MyField(10, 10);
        placed = new ArrayList<>();
        randomSetter = new RandomSetter(10, 10);
    }

    public List<Ship> getPlaced() {
        return placed;
    }

    private void initAvailable() {
        available.put(1, 4);
        available.put(2, 3);
        available.put(3, 2);
        available.put(4, 1);
    }

    public Ship getSelected() {
        return selected;
    }

    public void setSelected(int decks) {
        selected = new Ship(decks);
    }

    public int getAmountAvailable(int decks) {
        return available.get(decks);
    }


    public void confirmPlacing() {
        myField.place(selected);
        placed.add(selected);
        available.put(selected.getSize(), available.get(selected.getSize()) - 1);
    }

    public boolean isCanPlace(Coord coord) {
        selected.setCoords(coord);
        return myField.canPlace(selected);
    }

    public boolean placeRest() {
        List<Ship> rest = createRestShips();
        if (rest.size() == 0) return false;
        placed.addAll(rest);
        randomSetter.setShips(placed);
        if (randomSetter.setRest()) {
            myField.setShips(placed);
            myField.place(placed);
            clearAvailable();
        } else {
            placed.removeAll(rest);
            return false;
        }
        return true;
    }

    public void placeAll() {
        initAvailable();
        placed.clear();
        placed.addAll(createRestShips());
        randomSetter.setShips(placed);
        while (!randomSetter.setAll()) ;
        myField.setShips(placed);
        myField.place(placed);
        clearAvailable();
    }

    private void clearAvailable() {
        for (int i = 0; i < available.size(); i++) {
            available.put(i + 1, 0);
        }
    }

    private List<Ship> createRestShips() {
        List<Ship> ships = new ArrayList<>();
        for (int i = 0; i < available.size(); i++) {
            for (int j = 0; j < available.get(i + 1); j++) {
                ships.add(new Ship(i + 1));

            }
        }
        return ships;
    }

    public void clearAll() {
        initAvailable();
        placed.clear();
        myField.unPlaceAll();

    }

    public void setOrientation(Orientation orientation) {
        selected.setOrientation(orientation);
        selected.changeOrientation();

    }
}
