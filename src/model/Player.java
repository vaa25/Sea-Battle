package model;

import common.Coord;
import common.ShootResult;
import networks.ObjectParser;
import networks.ObjectSender;

import java.util.ArrayList;
import java.util.List;
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
    private ObjectParser parser;
    private ObjectSender sender;
    private String name;
    private MyField myField;
    private EnemyField enemyField;
    private int width, height;
    private Coord shootCoord;

    public Player(int width, int height, String name) {
        this.width = width;
        this.height = height;
        this.name = name;
        setEnemy();
    }

    public MyField getMyField() {
        return myField;
    }

    public void setMyField(MyField myField) {
        this.myField = myField;
    }

    public EnemyField getEnemyField() {
        return enemyField;
    }

    public void setEnemyField(EnemyField enemyField) {
        this.enemyField = enemyField;
    }

    private void setEnemy() {
        EnemyField enemyField = new EnemyField(10, 10);
        List<Ship> enemyShips = new ArrayList<>();
        enemyShips.add(new Ship(4));
        enemyShips.add(new Ship(3));
        enemyShips.add(new Ship(3));
        enemyShips.add(new Ship(2));
        enemyShips.add(new Ship(2));
        enemyShips.add(new Ship(2));
        enemyShips.add(new Ship(1));
        enemyShips.add(new Ship(1));
        enemyShips.add(new Ship(1));
        enemyShips.add(new Ship(1));
        enemyField.setShips(enemyShips);
        setEnemyField(enemyField);
    }

    public void addEnemyShips(List<Ship> ships) {

    }

    public Object takeObject(Class clazz) {

        try {
            return parser.take(clazz);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isIFirst() {
        Random random = new Random();
        Double myRandom;
        Double enemyRandom;
        do {
            myRandom = random.nextDouble();
            System.out.println(" Пытаюсь послать " + myRandom);
            sendObject(myRandom);
            System.out.println(" Послал  " + myRandom);
            System.out.println(" Пытаюсь принять вражеское число ");
            enemyRandom = (double) takeObject(Double.class);
            System.out.println(" Принял вражеское число " + enemyRandom);
        } while (myRandom.equals(enemyRandom));
        if (enemyRandom > myRandom) {
            return false;
        } else {
            return true;
        }
    }

    public void sendObject(Object object) {
        sender.sendObject(object);
    }

    public void setParser(ObjectParser parser) {
        this.parser = parser;
    }

    public void setSender(ObjectSender sender) {
        this.sender = sender;
    }

    public int getMyKilled() {
        return myField.getKilled();
    }

    public String getName() {
        return name;
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
                enemyField.place(enemyField.setKilled(shootCoord));
                break;
            case MISSED:
                enemyField.setShoot(shootCoord);
        }

    }

    public List<Ship> getReconstructedShips() {
        return enemyField.getReconstructedShips();
    }

    public List<Coord> getWrecks() {
        return enemyField.getWrecks();
    }

    /**
     * Определяет и возвращает результат выстрела врага
     *
     * @param coord координаты выстрела врага
     *
     * @return результат выстрела
     */
    public ShootResult receiveShoot(Coord coord) {
        ShootResult shootResult = myField.shoot(coord);
//        sender.sendObject(shootResult);
        return shootResult;
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
     * Выбирает рандомно координаты выстрела по врагу
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

    public ShootResult turn(Coord coord) {
        shootCoord = coord;
        sendObject(coord);
        ShootResult shootResult = (ShootResult) takeObject(ShootResult.class);
        setShootResult(shootResult);
        return shootResult;
    }

    public void setShootCoord(Coord shootCoord) {
        this.shootCoord = shootCoord;
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
