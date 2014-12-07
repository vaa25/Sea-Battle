package view;

import common.Coord;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import model.Cell;
import model.Field;
import model.Orientation;
import model.Ship;

import java.util.List;

/**
 * @author Alexander Vlasov
 */
public class FieldDisplay {
    private Pane pane;
    private Field field;
    private int width, height;

    public FieldDisplay(Pane pane, Field field) {
        this.pane = pane;
        this.field = field;
        width = field.getWidth();
        height = field.getHeight();
    }

    public void paint() {
        List<Ship> ships = field.getShips();
        refreshPane(ships);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Cell cell = field.getCell(new Coord(i, j));
                Ship ship = cell.getShip();
                if (ship == null) {
                    if (cell.isHurt()) drawWreck(i, j);
                    else if (cell.isShoot()) drawShoot(i, j);
                } else {
                    if (ship.isAlive() && cell.isShoot()) {
                        drawWreck(i, j);
                    }
                }
            }
        }
    }

    private void drawWreck(int x, int y) {
        Rectangle rectangle = new Rectangle(x * 20, y * 20, 20, 20);
        rectangle.setFill(Color.RED);
        pane.getChildren().add(rectangle);
    }

    private void drawShoot(int x, int y) {
        Rectangle rectangle = new Rectangle(x * 20, y * 20, 20, 20);
        rectangle.setFill(Color.GRAY);
        pane.getChildren().add(rectangle);
    }

    private void clearGridPane() {
        ObservableList<Node> list = pane.getChildren();
        list.clear();
        drawGrid(pane);
    }

    private void drawGrid(Pane pane) {
        ObservableList<Node> nodes = pane.getChildren();
        for (int i = 0; i <= 10; i++) {
            Line line = new Line(i * 20, 0, i * 20, 10 * 20);
            Line line2 = new Line(0, i * 20, 10 * 20, i * 20);
            nodes.addAll(line, line2);
        }
    }

    private void refreshPane(List<Ship> placed) {
        clearGridPane();
        for (Ship ship : placed) {
            if (ship.isAlive()) {
                drawShip(pane, ship, Color.BLACK);
            } else {
                drawShip(pane, ship, Color.BROWN);

            }
        }
    }

    private void drawShip(Pane pane, Ship ship, Color color) {
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
        pane.getChildren().add(rectangle);
    }
    public void clear() {
        ObservableList<Node> nodes = pane.getChildren();
        Node group = nodes.get(0);
        nodes.clear();
        nodes.addAll(group);
    }
}
