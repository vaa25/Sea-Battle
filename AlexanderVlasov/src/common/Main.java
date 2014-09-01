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
public class Main {
    Player player = new Player(10, 10, "Player");
    Double enemyRandom;

    public void start() throws IOException {

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
        System.out.println("player map:");
        player.printMy();
        Network network = null;
        try {
            network = new Network(this, InetAddress.getLocalHost(), 1000);
        } catch (IOException e) {
            System.out.println("Проблемы с сетью");
            e.printStackTrace();
        }
        Thread networkThread = new Thread(network);
        networkThread.start();
        Random random = new Random();
        Double myRandom = random.nextDouble();

        network.sendMessage(new Message(myRandom));
        while (enemyRandom == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ;
        if (enemyRandom < myRandom) {
            System.out.println(" Враг ходит первый");
        }
        System.out.println(" Я хожу первый");
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

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.start();
    }
}
