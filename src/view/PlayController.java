package view;

import common.Coord;
import common.ShootResult;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.Player;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Alexander Vlasov
 */
public class PlayController implements Initializable {
    private Player player;
    boolean myTurn;
    private int turn;
    private boolean ready;
    boolean enemyReady;
    private boolean gameIsGoing;
    private boolean connected;
    FieldDisplay myFieldDisplay;
    FieldDisplay enemyFieldDisplay;
    SimpleStringProperty chatMessage;
    SimpleBooleanProperty allShipSetted;

    @FXML
    Pane myFieldPane;

    @FXML
    Pane enemyFieldPane;

    @FXML
    private ToggleButton readyToggleButton;


    @FXML
    void myFieldClicked(MouseEvent event) {


    }

    @FXML
    void ready(ActionEvent event) {
//        if (readyToggleButton.isSelected()) {
//            if (checkReady()) {
//                ready = true;
//                network.getSender().sendObject(Special.Ready);
//                chatTextArea.appendText(" Я готов!" + "\n");
//                if (enemyReady) {
//                    gameIsGoing = true;
//                    startPlay();
//                }
//            } else {
//                readyToggleButton.setSelected(false);
//            }
//        } else {
//            ready = false;
//            network.getSender().sendObject(Special.NotReady);
//            chatTextArea.appendText(" Я не готов!" + "\n");
//        }
    }

    private boolean checkReady() {
//        if (editor == null || editor.getPlaced().size() != 10) {
//            chatTextArea.appendText(" Сначала расставь все корабли" + "\n");
//            return false;
//        }
//        if (!connected) {
//            chatTextArea.appendText(" Сначала установи соединение с кем-либо!" + "\n");
//            return false;
//        }
        return true;
    }

    @FXML
    void enemyFieldClicked(MouseEvent event) {

        if (!gameIsGoing || !myTurn) return;
        int x = (int) (event.getX() / 20);
        int y = (int) (event.getY() / 20);
        Coord coord = new Coord(x, y);
        if (player.getEnemyField().getCell(coord).isShoot()) {
//            chatTextArea.appendText(" Этот квадрат уже обстрелян. Выберите другой квадрат.\n");
            return;
        }
        ShootResult shootResult = player.turn(coord);
        if (shootResult == ShootResult.MISSED) {
            myTurn = false;
        }
//        chatTextArea.appendText(" Ход " + turn++ + ": Бью " + coord + " " + shootResult + "\n");
        enemyFieldDisplay.paint();
        if (shootResult == ShootResult.KILLED) {
            if (player.isGameOver()) {
                gameOver();
            }
        }
    }

    private void gameStarts() {
//        chatTextArea.appendText("Игра началась" + "\n");
//        player = new Player(10, 10, "Z");
//        player.setParser(network.getParser());
//        player.setSender(network.getSender());
//        player.setMyField(editor.getMyField());
//        readyToggleButton.setDisable(true);
//        myFieldDisplay = new FieldDisplay(myFieldPane, player.getMyField());
//        enemyFieldDisplay = new FieldDisplay(enemyFieldPane, player.getEnemyField());
//        myFieldDisplay.paint();
//        enemyFieldDisplay.paint();
    }


    private boolean startPlay() {

//        gameStarts();
//        if (player.isIFirst()) {
//            chatTextArea.appendText(" Мой ход" + "\n");
//            myTurn = true;
//        } else {
//            chatTextArea.appendText(" Враг ходит первый" + "\n");
//            myTurn = false;
//        }
//        readyToggleButton.setDisable(true);
//        turn = 1;
//        editTab.setDisable(true);
        return true;
    }

    private void gameOver() {
//        chatTextArea.appendText(" Игра окончена" + "\n");
//        if (player.isMyLoose()) chatTextArea.appendText(" Я проиграл.\n");
//        else chatTextArea.appendText(" Я выиграл.\n");
//        readyToggleButton.setDisable(false);
//        editTab.setDisable(false);
//        miscTab.setDisable(false);
//        networkTab.setDisable(false);
//        readyToggleButton.setSelected(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Play init");
        readyToggleButton.setDisable(true);
        ready = false;
        enemyReady = false;
        gameIsGoing = false;
        PaneService.drawGrid(myFieldPane);
        PaneService.drawGrid(enemyFieldPane);

    }
}
