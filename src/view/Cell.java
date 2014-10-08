package view;

import common.Coord;
import javafx.scene.layout.Pane;

/**
 * @author Alexander Vlasov
 */
public class Cell extends Pane {
    private Coord coord;

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }
}
