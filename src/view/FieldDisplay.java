package view;

import common.Coord;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.Cell;
import model.Field;
import model.Ship;

/**
 * @author Alexander Vlasov
 */
public class FieldDisplay {
    private GridPane gridPane;
    private Field field;
    private int width, height;

    public FieldDisplay(GridPane gridPane, Field field) {
        this.gridPane = gridPane;
        this.field = field;
        width = field.getWidth();
        height = field.getHeight();
    }

    public void paint() {
        clear();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Cell cell = field.getCell(new Coord(i, j));
                Ship ship = cell.getShip();
                if (ship == null) {
                    if (cell.isHurt()) gridPane.add(new Text("  x"), i, j);
                    else if (cell.isShoot()) gridPane.add(new Text("  ."), i, j);
                } else {
                    if (ship.isAlive()) {
                        if (cell.isShoot()) gridPane.add(new Text("  x"), i, j);
                        else gridPane.add(new Text("  O"), i, j);
                    } else {
                        gridPane.add(new Text("  X"), i, j);
                    }
                }
            }
        }
    }

    public void clear() {
        ObservableList<Node> nodes = gridPane.getChildren();
        Node group = nodes.get(0);
        nodes.clear();
        nodes.addAll(group);
    }
}
