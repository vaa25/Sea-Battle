package view;

import common.Coord;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import model.MyField;
import model.Orientation;
import model.RandomSetter;
import model.Ship;

import java.util.*;

/**
 * @author Alexander Vlasov
 */
public class Editor {
    /**
     * Список расставленных кораблей
     */
    private List<Ship> placed;
    /**
     * Выбранный корабль
     */
    private Ship selected;
    /**
     * Ориентация выбранного корабля
     */
    private Orientation orientation;
    private Map<Integer, Integer> availableShips;
    private MyField myField;
    private RandomSetter randomSetter;
    private Coord coord;
    private Pane editGridPane;
    private Map<Integer, Label> amountLabels;

    public Editor(Pane editGridPane) {
        this.editGridPane = editGridPane;
        availableShips = new HashMap<>();
        amountLabels = new HashMap<>();
        orientation = Orientation.Horizontal;

        myField = new MyField(10, 10);
        placed = new ArrayList<>();
        randomSetter = new RandomSetter(10, 10);
    }

    public MyField getMyField() {
        return myField;
    }

    public void editPaneMouseMoved(MouseEvent event) {
        if (selected != null) {
            int x = (int) event.getX() / 20;
            int y = (int) event.getY() / 20;
            Coord coord = new Coord(x, y);
            if (coord.equals(this.coord)) return;
            setCoord(coord);
            Ship ship = getSelected();
            ship.setCoords(coord);
            paint(ship);
        }
    }

    private void paint(Ship ship) {
        refreshEditPane();
        if (inBorders(ship)) {
            if (isCanPlace(coord)) {
                drawShip(ship, Color.GREEN);
            } else {
                drawShip(ship, Color.RED);
            }
        }
    }

    private void drawShip(Ship ship, Color color) {
        Coord coord = ship.getShipCoords()[0];
        Orientation orientation = ship.getOrientation();
        int width, height;
        if (orientation == Orientation.Horizontal) {
            width = ship.getSize();
            height = 1;
        } else {
            height = ship.getSize();
            width = 1;
        }
        Rectangle rectangle = new Rectangle(coord.getX() * 20, coord.getY() * 20, width * 20, height * 20);
        rectangle.setFill(color);
        editGridPane.getChildren().add(rectangle);
    }

    public void refreshEditPane() {
        clearGridPane(editGridPane);
        for (Ship ship : placed) {
            drawShip(ship, Color.BLACK);
        }
    }

    private void clearGridPane(Pane gridPane) {
        ObservableList<Node> list = gridPane.getChildren();
        list.clear();
        drawGrid(gridPane);
    }

    private void drawGrid(Pane pane) {
        ObservableList<Node> nodes = pane.getChildren();
        for (int i = 0; i <= 10; i++) {
            Line line = new Line(i * 20, 0, i * 20, 10 * 20);
            Line line2 = new Line(0, i * 20, 10 * 20, i * 20);
            nodes.addAll(line, line2);
        }
    }
    public List<Ship> getPlaced() {
        return placed;
    }

    public void initAvailable() {
        availableShips.put(1, 4);
        availableShips.put(2, 3);
        availableShips.put(3, 2);
        availableShips.put(4, 1);

        for (int i = 1; i <= 4; i++) {
            amountLabels.get(i).setText(availableShips.get(i).toString());
        }
    }

    public Ship getSelected() {
        return selected;
    }

    public void setSelected(int decks) {
        if (getAmountAvailable(decks) > 0) {
            selected = new Ship(decks);
        } else selected = null;
    }


    public int getAmountAvailable(int decks) {
        return availableShips.get(decks);
    }

    public void confirmPlacing() {
        myField.place(selected);
        placed.add(selected);
        int decks = selected.getSize();
        int was = availableShips.get(decks);
        availableShips.put(decks, --was);
        amountLabels.get(decks).setText(String.valueOf(was));
        setSelected(decks);
        setOrientation(orientation);
    }

    public boolean isCanPlace(Coord coord) {
        selected.setCoords(coord);
        return myField.canPlace(selected);
    }

    private boolean inBorders(Ship ship) {
        for (Coord coord : ship.getShipCoords()) {
            if (coord.getX() < 0 || coord.getY() < 0 || coord.getX() >= 10 || coord.getY() >= 10) return false;
        }
        return true;
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
        selected = null;
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
        selected = null;
    }

    private void clearAvailable() {
        for (int i = 0; i < availableShips.size(); i++) {
            availableShips.put(i + 1, 0);
            amountLabels.get(i + 1).setText("0");
        }
    }

    private List<Ship> createRestShips() {
        List<Ship> ships = new ArrayList<>();
        Set decks = availableShips.keySet();
        Iterator iterator = decks.iterator();
        while (iterator.hasNext()) {
            int deck = (int) iterator.next();
            int availiable = availableShips.get(deck);
            for (int i = 0; i < availiable; i++) {
                ships.add(new Ship(deck));
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
        System.out.println(orientation);
//        if (orientation==this.orientation)return;
        this.orientation = orientation;
        if (selected != null) {

            selected.setCoords(coord);
            selected.setOrientation(orientation);
//        selected.changeOrientation();
            paint(selected);
        }
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public void editPaneMouseClicked() {
        if (selected != null) {
            if (isCanPlace(coord)) {
                confirmPlacing();

            }
        }
    }

    public void setAmountLabel(int i, Label amountLabel) {
        amountLabels.put(i, amountLabel);
    }
}
