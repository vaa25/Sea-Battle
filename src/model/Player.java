package model;

import common.Coord;
import common.ShootResult;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Vlasov Alexander
 * Date: 25.08.2014
 * Time: 17:55
 *
 * @author Alexander Vlasov
 */
public class Player {
    final static boolean WON = true;
    final static boolean DONTWON = false;
    private String name;
    private MyField myField;
    private EnemyField enemyField;
    private int width, height;
    private CurrentStatistic currentStatistic;
    private Coord shootCoord;

    public Player(int width, int height, String name) {
        this.width = width;
        this.height = height;
        currentStatistic = new CurrentStatistic();
        this.name = name;
    }

    public int getMyKilled() {
        return myField.getKilled();
    }
    public String getName() {
        return name;
    }

    public void setMyField(MyField myField) {
        this.myField = myField;
    }

    public void setEnemyField(EnemyField enemyField) {
        this.enemyField = enemyField;
    }


    public boolean isEnemyLoose() {
        return enemyField.isLoose();
    }

    public boolean isMyLoose() {
        return myField.isLoose();
    }

    public boolean isGameOver() {
        return myField.isLoose() || enemyField.isLoose();
    }

    /**
     * помечает вражеское поле в соответствии с сообщением от оппонента о результатах выстрела
     *
     * @param shootResult сообщение
     */
    public void setShootResult(ShootResult shootResult) {
        switch (shootResult) {
            case HURT:
                enemyField.setHurt(shootCoord);
                break;
            case KILLED:
                enemyField.setKilled(shootCoord);
                break;
            case MISSED:
                enemyField.setShoot(shootCoord);
        }

    }

    /**
     * Jпределяет и возвращает результат выстрела врага
     *
     * @param coord координаты выстрела врага
     *
     * @return результат выстрела
     */
    public ShootResult receiveShoot(Coord coord) {
        return myField.shoot(coord);
    }

    public void printEnemy() {
        enemyField.printField();
    }

    public void printMy() {
        myField.printField();
    }

    private boolean isAlreadyShooted(Coord coord) {
        return enemyField.getCell(coord).isShoot();
    }

    /**
     * выбирает рандомно координаты выстрела по врагу
     *
     * @return
     */
    public Coord getRandomShootCoord() {
        Random random = new Random();
        Coord coord;
        do {
            coord = new Coord(random.nextInt(10), random.nextInt(10));
        } while (isAlreadyShooted(coord));
        shootCoord = coord;
        return coord;
    }

    public boolean canPlaceShip(Ship ship) {

        return myField.canPlace(ship);
    }


//    public static boolean turn(Player shooting, Player shooted) {
//        ShootResult shootResult;
//
//        do {
//            do {
//                Random random = new Random();
//                Coord coord;
//                do {
//                    coord = new Coord(random.nextInt(10), random.nextInt(10));
//                } while (shooting.isAlreadyShooted(coord));
//
//                shooting.shootCoord = coord;
//                shootResult = shooted.receiveShoot(shooting.shootCoord);
//                shooting.setShootResult(shootResult);
//                System.out.println(shooting.getName() + " now shoots at " + coord + " with result " + shootResult + ", overall killed " + shooting.enemyField.getKilled() + " enemy ships, enemy map:");
//                shooting.printEnemy();
//                System.out.println("in reality:");
//                shooted.printMy();
//            }
//            while (shootResult == ShootResult.HURT);
//            if (shootResult == ShootResult.KILLED) {
//                if (shooting.isEnemyLoose()) {
//                    System.out.println(shooting.getName() + " won");
//                    return WON;
//                }
//            } else break;
//        } while (true);
//        return DONTWON;
//    }

}
