package model;

import java.util.Arrays;
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
    public static int DEAD_CELL = 2;


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

    public void print(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(matrix[i][j] == EMPTY_CELL) System.out.print(" . ");
                else if (matrix[i][j] == ALIVE_CELL) System.out.print(" S ");
                else if (matrix[i][j] == DEAD_CELL) System.out.print(" x ");
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

        switch(ship.getVertical()){
            case 0 :
                for(int i = 0; i < shipLength; i++){
                    if(matrix[x + i][y] != ALIVE_CELL){
                        matrix[x + i][y] = ALIVE_CELL;
                        ship.getCoordinates().add(new Cell(x + i, y));
                    }
                    else randomlyPutShip(ship);
                }
                break;
            case 1 :
                for(int i = 0; i < shipLength; i++){
                    if(matrix[x][y + i] != ALIVE_CELL) {
                        matrix[x][y + i] = ALIVE_CELL;
                        ship.getCoordinates().add(new Cell(x, y + i));
                    }
                    else randomlyPutShip(ship);
                }
                break;
        }
    }
}
