package common;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Vlasov Alexander
 * Date: 24.08.2014
 * Time: 23:51
 *
 * @author Alexander Vlasov
 */
public class Coord implements Serializable {
    private final int x, y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coord getLeftUp() {
        return new Coord(x - 1, y - 1);
    }

    public Coord getRightUp() {
        return new Coord(x + 1, y - 1);
    }

    public Coord getLeftDown() {
        return new Coord(x - 1, y + 1);
    }

    public Coord getRightDown() {
        return new Coord(x + 1, y + 1);
    }

    public Coord getLeft() {
        return new Coord(x - 1, y);
    }

    public Coord getUp() {
        return new Coord(x, y - 1);
    }

    public Coord getRight() {
        return new Coord(x + 1, y);
    }

    public Coord getDown() {
        return new Coord(x, y + 1);
    }

    public Coord getCenter() {
        return new Coord(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coord coord = (Coord) o;

        if (x != coord.x) return false;
        if (y != coord.y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "(" +
                x +
                "," + y +
                ")";
    }
}
