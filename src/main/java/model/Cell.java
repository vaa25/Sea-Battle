package src.main.java.model;

import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey Nerodenko
 * Date: 28.08.14
 */
public class Cell {
    private int x, y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Cell readCell(){
        Scanner scanner = new Scanner(System.in);
        String [] cellCoordinates = scanner.nextLine().split(" ");
        int x = Integer.parseInt(cellCoordinates[0]);
        int y = Integer.parseInt(cellCoordinates[1]);
        return new Cell(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        if (x != cell.x) return false;
        if (y != cell.y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}