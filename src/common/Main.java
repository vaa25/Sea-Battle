package common;

import model.Field;
import model.Player;
import model.Ship;
import networks.Message;
import networks.MessageParser;
import networks.MessageSender;
import networks.Network;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Alexander Vlasov
 */
public class Main implements Runnable {
    private Player player;
    private Double enemyRandom;
    private Network network;
    private MessageParser parser;
    private MessageSender sender;
    public Main(String name) {
        player = new Player(10, 10, name);
    }

    public String getName() {
        return player.getName();
    }

    @Override
    public void run() {
        System.out.println(player.getName() + " Main thread is (" + Thread.currentThread().getName() + ")");
        Field my = new Field(10, 10);
        player.setMyField(my);
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
        my.setRandom(ships);
//        System.out.println(System.nanoTime() + " " + player.getName() + " map:");
//        player.printMy();
        try {
//            System.out.print(player.getName()+" ");
            network = new Network(InetAddress.getLocalHost(), 10000);
            parser = network.getParser();
            sender = network.getSender();
        } catch (IOException e) {
            System.out.println(System.nanoTime() + " " + player.getName() + "Проблемы с сетью");
            e.printStackTrace();
        }

        Random random = new Random();
        Double myRandom;
        do {
            myRandom = random.nextDouble();
            sender.sendMessage(new Message(myRandom));
            enemyRandom = parser.takeRandom();
        } while (myRandom == enemyRandom);
        boolean myTurn;
        if (enemyRandom > myRandom) {
            System.out.println(player.getName() + " Враг ходит первый");
            myTurn = false;
        } else {
            System.out.println(player.getName() + " Я хожу первый");
            myTurn = true;
        }
        int turn = 1;
        while (!player.isGameOver()) {
            if (myTurn) {
                Coord coord = player.shooting();
                sender.sendMessage(new Message(coord));
                ShootResult shootResult = parser.takeShootResult();
                player.setShootResult(shootResult);
                if (shootResult == ShootResult.MISSED) {
                    myTurn = !myTurn;
                }
//                System.out.println(player.getName() + " ход " + turn++ + ": Бью " + coord + " " + shootResult);
//                player.printEnemy();
            } else {
                ShootResult shootResult = player.receiveShoot(parser.takeCoord());
                sender.sendMessage(new Message(shootResult));
                if (shootResult == ShootResult.MISSED) {
                    myTurn = !myTurn;
                }
            }
        }
        if (player.isEnemyLoose()) System.out.println(player.getName() + " Я выиграл");
        else System.out.println(player.getName() + " Я проиграл");
        network.close();
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        new Thread(new Main("Player 1: ")).start();
        Thread.sleep(1000);
        new Thread(new Main("Player 2: ")).start();

    }
}
