package view;

import common.Coord;
import javafx.scene.layout.Pane;

/**
 * @author Alexander Vlasov
 */
public class CellImage extends Pane {
    private Coord coord;

    public CellImage(int width, int height) {
        setMinSize(width, height);
        setPrefSize(width, height);
        setMaxSize(width, height);

    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }
}
