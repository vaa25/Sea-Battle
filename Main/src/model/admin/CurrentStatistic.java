package model.admin;

import common.CurrentStatisticInterface;

/**
 * Created with IntelliJ IDEA.
 * User: Vlasov Alexander
 * Date: 25.08.2014
 * Time: 14:21
 *
 * @author Alexander Vlasov
 */
public class CurrentStatistic implements CurrentStatisticInterface{
    int shoots, missed, hit, myShipsSunk, myShipsLeft, enemyShipsSunk, enemyShipsLeft;


    public int getShoots() {
        return shoots;
    }

    public void setShoots(int shoots) {
        this.shoots = shoots;
    }

    public int getMissed() {
        return missed;
    }

    public void setMissed(int missed) {
        this.missed = missed;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public int getMyShipsSunk() {
        return myShipsSunk;
    }

    public void setMyShipsSunk(int myShipsSunk) {
        this.myShipsSunk = myShipsSunk;
    }

    public int getMyShipsLeft() {
        return myShipsLeft;
    }

    public void setMyShipsLeft(int myShipsLeft) {
        this.myShipsLeft = myShipsLeft;
    }

    public int getEnemyShipsSunk() {
        return enemyShipsSunk;
    }

    public void setEnemyShipsSunk(int enemyShipsSunk) {
        this.enemyShipsSunk = enemyShipsSunk;
    }

    public int getEnemyShipsLeft() {
        return enemyShipsLeft;
    }

    public void setEnemyShipsLeft(int enemyShipsLeft) {
        this.enemyShipsLeft = enemyShipsLeft;
    }
}
