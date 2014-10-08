package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * @author Alexander Vlasov
 */
public class Ship extends Rectangle {
    private Cell[] cells;
    private Direction direction;
    private int size;

    public Ship(int size) {
        super(size * 10, 10, Color.BLACK);
        this.size = size;
        cells = new Cell[size];
        direction = Direction.Horizontal;
    }

    public static enum Direction {
        Vertical, Horizontal
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void changeDirection() {
        if (direction == Direction.Horizontal) {
            direction = Direction.Vertical;
            setWidth(10);
            setHeight(10 * size);
        } else {
            direction = Direction.Horizontal;
            setWidth(10 * size);
            setHeight(10);
        }
    }
}
