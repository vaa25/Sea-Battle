package view;

import common.Coord;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.MyField;
import model.Orientation;
import model.RandomSetter;
import model.Ship;

import java.net.URL;
import java.util.*;

/**
 * @author Alexander Vlasov
 */
public class EditController implements Initializable {
    @FXML
    private AnchorPane editAnchorPane;

    @FXML
    private Label amountLabel1;

    @FXML
    private Label amountLabel2;

    @FXML
    private Label amountLabel3;

    @FXML
    private Label amountLabel4;

    @FXML
    private Pane editPane;

    @FXML
    private RadioButton radioDeck1;

    @FXML
    private RadioButton radioDeck2;

    @FXML
    private RadioButton radioDeck3;

    @FXML
    private RadioButton radioDeck4;

    @FXML
    private Button setAllButton;

    @FXML
    private Button setRestButton;

    @FXML
    private ToggleGroup toggleDeck;
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
    private Map<Integer, Label> amountLabels;
    private boolean editPaneOut;
    private boolean keyPressed;
    SimpleStringProperty printChatMessage;
    SimpleBooleanProperty allShipSetted;

    public EditController() {
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

    @FXML
    void editPaneKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.CONTROL) {
            keyPressed = true;
            setOrientation(Orientation.Vertical);
        }
    }

    @FXML
    void editPaneKeyReleased(KeyEvent event) {

        if (event.getCode() == KeyCode.CONTROL) {
            keyPressed = false;
            setOrientation(Orientation.Horizontal);
        }
    }

    @FXML
    void editPaneMouseClicked(MouseEvent event) {
        if (selected != null) {
            if (isCanPlace(coord)) {
                confirmPlacing();
                PaneService.refreshPane(editPane, getPlaced());
            }
        }
        keepFocus();
    }

    void keepFocus() {
        ((RadioButton) (toggleDeck.getSelectedToggle())).requestFocus();
    }

    @FXML
    void editPaneMouseExited(MouseEvent event) {
        PaneService.refreshPane(editPane, getPlaced());
        editPaneOut = true;
    }

    @FXML
    void editPaneMouseMoved(MouseEvent event) {
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
        editPaneOut = false;

    }

    @FXML
    void radioDeckOnAction1(ActionEvent event) {
        setSelected(1);
    }

    @FXML
    void radioDeckOnAction2(ActionEvent event) {
        setSelected(2);
    }

    @FXML
    void radioDeckOnAction3(ActionEvent event) {
        setSelected(3);
    }

    @FXML
    void radioDeckOnAction4(ActionEvent event) {
        setSelected(4);
    }

    @FXML
    void setAll(ActionEvent event) {
        placeAll();
        updateAmountAvailable();
        clearMyPanes();
        PaneService.refreshPane(editPane, getPlaced());
        allShipSetted.setValue(null);
        allShipSetted.set(true);

    }

    @FXML
    void clearField(ActionEvent event) {
        clearAll();
        updateAmountAvailable();
        clearMyPanes();
        setSelected((int) (toggleDeck.getSelectedToggle().getUserData()));
        allShipSetted.set(false);
    }

    private void updateAmountAvailable() {
        amountLabel1.setText(String.valueOf(getAmountAvailable(1)));
        amountLabel2.setText(String.valueOf(getAmountAvailable(2)));
        amountLabel3.setText(String.valueOf(getAmountAvailable(3)));
        amountLabel4.setText(String.valueOf(getAmountAvailable(4)));
    }

    private void clearMyPanes() {
        PaneService.clearPane(editPane);
    }

    @FXML
    void setRest(ActionEvent event) {
        if (placeRest()) {
            PaneService.refreshPane(editPane, getPlaced());
            allShipSetted.setValue(null);
            allShipSetted.set(true);
        }
    }

    private void paint(Ship ship) {
        if (editPaneOut) return;
        PaneService.refreshPane(editPane, myField.getShips());
        if (inBorders(ship)) {
            if (isCanPlace(coord)) {
                PaneService.drawShip(editPane, ship, Color.GREEN);
            } else {
                PaneService.drawShip(editPane, ship, Color.RED);
            }
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
        myField.getShips().add(selected);
        myField.place(selected);
        placed.add(selected);
        int decks = selected.getSize();
        int was = availableShips.get(decks);
        availableShips.put(decks, --was);
        amountLabels.get(decks).setText(String.valueOf(was));
        setSelected(decks);
        setOrientation(orientation);
        if (placed.size() == 10) {
            allShipSetted.set(true);
        }

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
        myField.unPlaceAll();
        placed.clear();

    }

    public void setOrientation(Orientation orientation) {
        System.out.println(orientation);
//        if (orientation==this.orientation)return;
        this.orientation = orientation;
        if (selected != null && coord != null) {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Edit init");
        radioDeck1.setUserData(1);
        radioDeck2.setUserData(2);
        radioDeck3.setUserData(3);
        radioDeck4.setUserData(4);
        setAmountLabel((int) (radioDeck1.getUserData()), amountLabel1);
        setAmountLabel((int) (radioDeck2.getUserData()), amountLabel2);
        setAmountLabel((int) (radioDeck3.getUserData()), amountLabel3);
        setAmountLabel((int) (radioDeck4.getUserData()), amountLabel4);
        initAvailable();
        setSelected((int) (toggleDeck.getSelectedToggle().getUserData()));
        PaneService.drawGrid(editPane);
    }
}
