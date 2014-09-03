package main.java.console;

import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey Nerodenko
 * Date: 03.09.14
 */
public class ConsoleHelper {

    public static int[] readShootCoordinates(){
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String [] shootCoordinates = scanner.nextLine().split(" ");
            try {
                int x = Integer.parseInt(shootCoordinates[0]);
                int y = Integer.parseInt(shootCoordinates[1]);
                return new int[]{x,y};
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
                System.out.println("Pls input two numbers, separated by space (e.g. \"3 15\")");
            }
        }
    }

    public static void printMessage(String message){
        System.out.println(message);
    }
}
