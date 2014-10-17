package view;

import common.Coord;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Ship;

/**
 * @author Alexander Vlasov
 */
public class MyFieldImage extends GridPane {
    private int width, height;
    private int cellWidth, cellHeight;
    private int cellCountWidth, cellCountHeight;
    private CellImage[][] cellImages;
    private Ship selectedShip;

    public MyFieldImage() {
        cellWidth = 10;
        cellHeight = 10;
        cellCountWidth = 20;
        cellCountHeight = 20;
        width = cellWidth * cellCountWidth;
        height = cellHeight * cellCountHeight;
        setMinSize(width, height);
        setPrefSize(width, height);
        setMaxSize(width, height);
        cellImages = new CellImage[cellCountWidth][cellCountHeight];
        for (int i = 0; i < cellCountWidth; i++) {
            for (int j = 0; j < cellCountHeight; j++) {
                CellImage cellImage = new CellImage(cellWidth, cellHeight);
                cellImage.setCoord(new Coord(i, j));
                cellImage.getChildren().addAll(new Rectangle(cellWidth, cellHeight, Color.BLUE));
                cellImages[i][j] = cellImage;
                getChildren().addAll(cellImage);
            }
        }
        System.out.println(getWidth());
    }

    public void setSelectedShip(Ship selectedShip) {
        this.selectedShip = selectedShip;
    }

    public void drawShip(Ship ship, Color color) {
        Coord[] coords = ship.getShipCoords();
        for (Coord coord : coords) {
            ((Rectangle) (getCellImage(coord).getChildren().get(0))).setFill(color);
        }

    }

    public void unDrawShip(Ship ship) {
        Coord[] coords = ship.getShipCoords();
        for (Coord coord : coords) {
            ((Rectangle) (getCellImage(coord).getChildren().get(0))).setFill(Color.BLUE);
        }
    }

    public void drawMissed(Coord coord) {
        ((Rectangle) (getCellImage(coord).getChildren().get(0))).setFill(Color.WHITESMOKE);
    }

    public void drawHurt(Coord coord) {
        ((Rectangle) (getCellImage(coord).getChildren().get(0))).setFill(Color.ORANGERED);
    }

    private CellImage getCellImage(Coord coord) {
        return cellImages[coord.getX()][coord.getY()];
    }
}
