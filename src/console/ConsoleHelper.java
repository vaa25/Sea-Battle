package console;

import static model.CellConstants.*;
import model.Field;

import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey Nerodenko
 * Date: 03.09.14
 */
public class ConsoleHelper {

    public static int[] getPlayerShot(){
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if("exit".equals(input)) {System.exit(1);}
            String [] shootCoordinates = input.split(" ");
            try {
                int x = Integer.parseInt(shootCoordinates[0]);
                int y = Integer.parseInt(shootCoordinates[1]);
                return new int[]{x,y};
            } catch (NumberFormatException e){
                System.out.println("Pls input two numbers, separated by space (e.g. \"3 15\") or \"exit\" to quit the game.");
            }
        }
    }

    public static String getUserInput(){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return input;
    }

    public static void printMessage(String message){
        System.out.println(message);
    }

    public static void printReal(Field field){
        int[][] matrix = field.getMatrix();
        for(int i = 0; i < field.getHeight(); i++){
            for(int j = 0; j < field.getWidth(); j++){
                if(matrix[i][j] == EMPTY_CELL) System.out.print(" . ");
                else if (matrix[i][j] == ALIVE_CELL) System.out.print(" S ");
            }
            System.out.println();
        }

        System.out.println("-----------------------------------------------------------");
        System.out.println();
        System.out.println();
    }

    public static void printGame(Field field){
        int[][] matrix = field.getMatrix();
        for(int i = 0; i < field.getHeight(); i++){
            for(int j = 0; j < field.getWidth(); j++){
                if(matrix[i][j] == EMPTY_CELL || matrix[i][j] == ALIVE_CELL) System.out.print(" . ");
                else if (matrix[i][j] == HIT_CELL) System.out.print(" X ");
                else if (matrix[i][j] == MISSED_CELL) System.out.print(" o ");
            }
            System.out.println();
        }

        System.out.println("-----------------------------------------------------------");
        System.out.println();
        System.out.println();
    }

}
