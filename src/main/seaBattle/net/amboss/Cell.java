package seaBattle.net.amboss;

import java.io.Serializable;

/**
 * Class Sell represents a sell of map
 */
public class Cell implements Serializable{

    private int digit;

    private String letter;

    public Cell() {}

    public Cell(int digit, String letter) {
        this.digit = digit;
        this.letter = letter;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;

        Cell sell = (Cell) o;

        if (digit != sell.digit) return false;
        if (!letter.equals(sell.letter)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = digit;
        result = 31 * result + letter.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Sell{" +
                "digit=" + digit +
                ", letter='" + letter + '\'' +
                '}';
    }

}
