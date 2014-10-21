package common;

import model.EnemyField;
import model.MyField;
import model.Player;
import model.Ship;
import networks.Network;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Vlasov
 */
public class Main implements Runnable {
    private Player player;
    private Double enemyRandom;
    private Network network;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Main(String name) {
        player = new Player(10, 10, name);
    }

    public String getName() {
        return player.getName();
    }

    @Override
    public void run() {
        System.out.println(player.getName() + " Main thread is (" + Thread.currentThread().getName() + ")");

        MyField myField = new MyField(10, 10);
        List<Ship> ships = new ArrayList<>();
        ships.add(new Ship(4));
        ships.add(new Ship(3));
        ships.add(new Ship(3));
        ships.add(new Ship(2));
        ships.add(new Ship(2));
        ships.add(new Ship(2));
        ships.add(new Ship(1));
        ships.add(new Ship(1));
        ships.add(new Ship(1));
        ships.add(new Ship(1));
        myField.setShips(ships);
        myField.placeRandom();
        player.setMyField(myField);

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
        player.setEnemyField(enemyField);
        System.out.println(player.getName() + " map:");
        player.printMy();
        try {
//            System.out.print(player.getName()+" ");
            network = new Network(InetAddress.getLocalHost(), 20000);
            network.setAnyConnection();
            player.setParser(network.getParser());
            player.setSender(network.getSender());
        } catch (IOException e) {
            System.out.println(System.nanoTime() + " " + player.getName() + "Проблемы с сетью");
            e.printStackTrace();
        }
        boolean myTurn;
        if (!player.isIFirst()) {
            System.out.println(player.getName() + " Враг ходит первый");
            myTurn = false;
        } else {
            System.out.println(player.getName() + " Я хожу первый");
            myTurn = true;
        }
        int turn = 1;
        while (!player.isGameOver()) {
            if (myTurn) {
                Coord coord = player.getRandomShootCoord();
                player.sendObject(coord);
                ShootResult shootResult = player.turn(coord);
                if (shootResult == ShootResult.MISSED) {
                    myTurn = false;
                }
                System.out.println(player.getName() + " ход " + turn++ + ": Бью " + coord + " " + shootResult);
                player.printEnemy();
            } else {
                ShootResult shootResult = player.receiveShoot((Coord) player.takeObject(Coord.class));
                if (shootResult == ShootResult.KILLED)
                    System.out.println(player.getName() + " всего убито " + myField.getKilled() + " ships.size()=" + myField.getShipSize());
                player.sendObject(shootResult);
                if (shootResult == ShootResult.MISSED) {
                    myTurn = true;
                }
            }
        }
        if (player.isEnemyLoose()) System.out.println(player.getName() + " Я выиграл");
        if (player.isMyLoose()) System.out.println(player.getName() + " Я проиграл");
        network.close();
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        new Thread(new Main("Player 1: ")).start();
        Thread.sleep(1000);
        new Thread(new Main("Player 2: ")).start();

    }
}
