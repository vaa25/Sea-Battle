package model;

public class Cell {

    private InternalEntity entity;
    private int rowPosition;
    private int columnPosition;

    public Cell(int rowPosition, int columnPosition) {
        entity = InternalEntity.EMPTY;
        this.rowPosition = rowPosition;
        this.columnPosition = columnPosition;
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public int getColumnPosition() {
        return columnPosition;
    }

    public InternalEntity getEntity() {
        return entity;
    }

    public void setEntity(InternalEntity entity) {
        this.entity = entity;
    }

    public enum InternalEntity {
        PART_OF_SHIP, WRECK, EMPTY
    }

    @Override
    public String toString() {
        switch (entity) {
            case PART_OF_SHIP:
                return "#";
            case WRECK:
                return "X";
            case EMPTY:
            default:
                return " ";
        }
    }
}
