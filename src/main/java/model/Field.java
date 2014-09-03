package main.java.model;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey Nerodenko
 * Date: 27.08.14
 */
public class Field {
    int width, height;
    int [][] matrix;
    public static int EMPTY_CELL = 0;
    public static int ALIVE_CELL = 1;
    public static int HIT_CELL = 3;
    public static int MISSED_CELL = 4;



    public Field(int height, int width) {
        this.width = width;
        this.height = height;
        matrix = new int[height][width];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setCell(Cell cell, boolean isShipHit){
        int x = cell.getX();
        int y = cell.getY();
        if (isShipHit){
            matrix[x][y] = HIT_CELL;
        } else {
            matrix[x][y] = MISSED_CELL;
        }
    }

    public void printReal(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(matrix[i][j] == EMPTY_CELL) System.out.print(" . ");
                else if (matrix[i][j] == ALIVE_CELL) System.out.print(" S ");
            }
            System.out.println();
        }

        System.out.println("-----------------------------------------------------------");
        System.out.println();
        System.out.println();
    }

    public void printGame(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(matrix[i][j] == EMPTY_CELL || matrix[i][j] == ALIVE_CELL) System.out.print(" . ");
                else if (matrix[i][j] == HIT_CELL) System.out.print(" \u2605 ");
                else if (matrix[i][j] == MISSED_CELL) System.out.print(" \u26aa ");
            }
            System.out.println();
        }

        System.out.println("-----------------------------------------------------------");
        System.out.println();
        System.out.println();
    }

    public void randomlyPutShip(Ship ship){
        int shipLength = ship.getShipLength();
        int x = new Random().nextInt(width - shipLength - 1);
        int y = new Random().nextInt(height - shipLength - 1);

        if(ship.getVertical()){
            if(isTherePlaceForShip(x, y, shipLength, true)){
                for(int i = 0; i < shipLength; i++){
                    matrix[x + i][y] = ALIVE_CELL;
                    ship.getCoordinates().add(new Cell(x + i, y));
                }
            } else { randomlyPutShip(ship); }
        } else {
            if(isTherePlaceForShip(x, y, shipLength, false)){
                for(int i = 0; i < shipLength; i++){
                    matrix[x][y + i] = ALIVE_CELL;
                    ship.getCoordinates().add(new Cell(x, y + i));
                }
            } else { randomlyPutShip(ship); }
        }
    }

    public boolean isTherePlaceForShip(int x, int y, int shipLength, boolean vertical){
        int startX = (x == 0) ? 0 : x - 1;
        int startY = (y == 0) ? 0 : y - 1;
        int areaX = 0;
        int areaY = 0;

        if (vertical){
            areaX  = (x == 0 || x == height - shipLength) ? startX + 2 : startX + 3;
            areaY  = (y == 0 || y == width  - shipLength) ? startY + shipLength + 1 : startY + shipLength + 2;
        } else {
            areaX  = (x == 0 || x == height - shipLength) ? startX + shipLength + 1 : startX + shipLength + 2;
            areaY  = (y == 0 || y == width  - shipLength) ? startY + 2 : startY + 3;
        }

        for(int i = startX; i < areaX; i++){
            for(int j = startY; j < areaY; j++){
                    if(matrix[i][j] == ALIVE_CELL) return false;
            }
        }
        return true;
    }
}
