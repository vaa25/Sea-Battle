package view;

import common.Coord;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import model.Orientation;
import model.Ship;

import java.util.List;

/**
 * @author Alexander Vlasov
 */
public class PaneService {
    public static void refreshPane(Pane pane, List<Ship> placed) {
        clearPane(pane);
        if (placed != null) {
            for (Ship ship : placed) {
                drawShip(pane, ship, Color.BLACK);
            }

        }
    }

    public static void drawShip(Pane pane, Ship ship, Color color) {
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

    public static void clearPane(Pane gridPane) {
        ObservableList<Node> list = gridPane.getChildren();
        list.clear();
        drawGrid(gridPane);
    }


    public static void drawGrid(Pane pane) {
        ObservableList<Node> nodes = pane.getChildren();
        for (int i = 0; i <= 10; i++) {
            Line line = new Line(i * 20, 0, i * 20, 10 * 20);
            Line line2 = new Line(0, i * 20, 10 * 20, i * 20);
            nodes.addAll(line, line2);
        }
    }
}
