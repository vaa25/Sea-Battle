package model;

import common.Coord;
import common.ShootResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vlasov Alexander
 * Date: 25.08.2014
 * Time: 17:55
 *
 * @author Alexander Vlasov
 */
public class Player {
    private String name;
    private MyField myField;
    private EnemyField enemyField;
    private Coord shootCoord;

    public Player(String name) {
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

    public String getName() {
        return name;
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
            case HURTED:
                enemyField.setHurt(shootCoord);
                break;
            case KILLED:
                enemyField.place(enemyField.setKilled(shootCoord));
                break;
            case MISSED:
                enemyField.setShooted(shootCoord);
                break;
        }
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
        return shootResult;
    }


    public void setShootCoord(Coord shootCoord) {
        this.shootCoord = shootCoord;
    }
}
