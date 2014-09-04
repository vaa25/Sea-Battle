package controller;

import model.Player;
import networks.Network;
import view.DesktopView;
import view.View;

import java.io.IOException;
import java.net.InetAddress;

/**
 * @author Alexander Vlasov
 */
public class Launcher {
    private View view;
    private Player player;
    private Network network;

    public Launcher(View view, Player player, Network network) {
        this.view = view;
        this.player = player;
        this.network = network;
    }

    private void startGame() {

    }

    public static void main(String[] args) throws IOException {
        Launcher launcher = new Launcher(new DesktopView(), new Player(20, 20, "Player 1"), new Network(InetAddress.getLocalHost(), 10000));
        launcher.startGame();
    }
}
