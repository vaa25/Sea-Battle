package model;

public class Ship {

    private Cell[] cells;

    public Ship(Cell[] cells) {
        this.cells = cells;
        for (int i = 0; i < cells.length; i++) {
            cells[i].setEntity(Cell.InternalEntity.PART_OF_SHIP);
        }
    }
}
