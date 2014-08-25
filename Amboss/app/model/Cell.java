package model;

import java.io.Serializable;

/**
 * Enum for cell state
 */
enum CellState {
    NULL {},
    BUSY {},
    HIT {},
    NO_HIT {};

    public static CellState getEnum(String s) {
        if (BUSY.name().equals(s)) {
            return BUSY;
        } else if (HIT.name().equals(s)) {
            return HIT;
        } else if (NO_HIT.name().equals(s)) {
            return NO_HIT;
        } else {
            return NULL;
        }
    }
}

/**
 * Class Sell represents a sell of map
 */
public class Cell implements Serializable{

    private int digit;

    private String letter;

    private CellState cellState;

    public Cell() {}

    public Cell(int digit, String letter, CellState cellState) {
        this.digit = digit;
        this.letter = letter;
        this.cellState = cellState;
    }

    public int getDigit() {
        return digit;
    }

    public void setDigit(int digit) {
        this.digit = digit;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public CellState getCellState() {
        return cellState;
    }

    public void setCellState(CellState cellState) {
        this.cellState = cellState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;

        Cell cell = (Cell) o;

        if (digit != cell.digit) return false;
        if (cellState != cell.cellState) return false;
        if (letter != null ? !letter.equals(cell.letter) : cell.letter != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = digit;
        result = 31 * result + (letter != null ? letter.hashCode() : 0);
        result = 31 * result + (cellState != null ? cellState.hashCode() : 0);
        return result;
    }
}
