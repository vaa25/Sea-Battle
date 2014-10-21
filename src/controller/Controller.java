package controller;

import model.Player;
import model.Ship;
import networks.Network;
import view.View;

import java.io.IOException;
import java.net.InetAddress;

/**
 * @author Alexander Vlasov
 */
public class Controller {
    private View view;
    private Player player;
    private Network network;

    public Controller(View view, Player player, Network network) {
        this.view = view;
        view.setController(this);
        this.player = player;
        this.network = network;
    }

    private void startGame() {
        view.show(new String[]{});
    }

    public static void main(String[] args) throws IOException {
        Controller controller = new Controller(
                null,    // this is mock
                new Player(20, 20, "Player 1"),
                new Network(InetAddress.getLocalHost(), 10000));
        controller.startGame();
    }

    public boolean canPlaceShip(Ship ship) {
        return player.canPlaceShip(ship);
    }
}
