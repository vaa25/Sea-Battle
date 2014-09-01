package common;

import model.Field;
import model.Player;
import model.Ship;
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

    public Main(String name) {
        player = new Player(10, 10, name);
    }

    public String getName() {
        return player.getName();
    }

    @Override
    public void run() {

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
        System.out.println(System.nanoTime() + " " + player.getName() + " map:");
        player.printMy();
        Network network = null;
        try {
            network = new Network(this, InetAddress.getLocalHost(), 1000);
        } catch (IOException e) {
            System.out.println(System.nanoTime() + " " + player.getName() + "Проблемы с сетью");
            e.printStackTrace();
        }
        Thread networkThread = new Thread(network);
        networkThread.start();
        Random random = new Random();
        Double myRandom = random.nextDouble();

        try {
            network.sendMessage(new Message(myRandom));
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (enemyRandom == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        boolean myTurn;
        if (enemyRandom < myRandom) {
            System.out.println(System.nanoTime() + " " + player.getName() + " Враг ходит первый");
            myTurn = false;
        } else {
            System.out.println(System.nanoTime() + " " + player.getName() + " Я хожу первый");
            myTurn = true;
        }
        int turn = 0;
        while (!player.isGameOver()) {
            if (myTurn) {
                try {
                    network.sendMessage(new Message(player.shooting()));
                    myTurn = !myTurn;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {

            }
        }
        ShootResult shootResult;


        ;


    }

    public void setMessage(Message message) {
        switch (message.getType()) {
            case TEXT:
//                todo
//                chat.setText(message.getText());
                break;
            case COORD:
                player.receiveShoot(message.getCoord());
                break;
            case SHOOTRESULT:
                player.setShootResult(message.getShootResult());
                break;
            case RANDOM:
                enemyRandom = message.getRandom();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new Thread(new Main("Player 1: ")).start();
        Thread.sleep(2000);
        new Thread(new Main("Player 2: ")).start();

    }
}
