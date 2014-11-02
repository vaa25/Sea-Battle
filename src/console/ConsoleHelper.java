package console;

import model.Field;

import java.util.Scanner;

import static model.CellConstants.*;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey Nerodenko
 * Date: 03.09.14
 */
public class ConsoleHelper {
    public static String getUserInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return input;
    }

    public static void printMessage(String message) {
        System.out.println(message);
    }

    public static void printRealField(Field field) {
        int[][] matrix = field.getMatrix();
        for (int i = 0; i < field.getHeight(); i++) {
            System.out.printf("%2d |", i);
            for (int j = 0; j < field.getWidth(); j++) {
                if (matrix[i][j] == EMPTY_CELL) System.out.print(" . ");
                else if (matrix[i][j] == MISSED_CELL) System.out.print(" o ");
                else if (matrix[i][j] == HIT_CELL) System.out.print(" $ ");
                else if (matrix[i][j] == ALIVE_CELL) System.out.print(" S ");
            }
            System.out.println();
        }

        System.out.println("   |------------------------------------------------------------");
        System.out.println("     0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19");
        System.out.println();
    }

    public static void printGameField(Field field) {
        int[][] matrix = field.getMatrix();
        for (int i = 0; i < field.getHeight(); i++) {
            System.out.printf("%2d |", i);
            for (int j = 0; j < field.getWidth(); j++) {
                if (matrix[i][j] == EMPTY_CELL || matrix[i][j] == ALIVE_CELL) System.out.print(" . ");
                else if (matrix[i][j] == HIT_CELL) System.out.print(" X ");
                else if (matrix[i][j] == MISSED_CELL) System.out.print(" o ");
            }
            System.out.println();
        }

        System.out.println("   |------------------------------------------------------------");
        System.out.println("     0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19");
        System.out.println();
    }

}
