package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Orientation;
import model.Ship;


/**
 * @author Alexander Vlasov
 */
public class ShipImage extends Rectangle {
    private Ship ship;
    private CellImage[] cellImages;
    private Orientation orientation;
    private int size;

    public ShipImage(int size) {
        super(size * 10, 10, Color.BLACK);
        this.size = size;
        cellImages = new CellImage[size];
        orientation = Orientation.Horizontal;
        ship = new Ship(size);

    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
        ship.setOrientation(orientation);

    }

    public Ship getShip() {
        return ship;
    }

    public void setCanPlace(boolean canPlace) {
        if (canPlace) setFill(Color.GREEN);
        else setFill(Color.RED);
    }

    public void changeOrientation() {
        if (orientation == Orientation.Horizontal) {
            orientation = Orientation.Vertical;
            setWidth(10);
            setHeight(10 * size);
        } else {
            orientation = Orientation.Horizontal;
            setWidth(10 * size);
            setHeight(10);
        }
    }
}
