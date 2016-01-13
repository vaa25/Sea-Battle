package view;

import common.Coord;
import common.ShootResult;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.MyField;
import model.Player;
import model.Ship;

import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * @author Alexander Vlasov
 */
public class PlayController implements Initializable {
    boolean myTurn;
    double myRandom;
    MyField myField;
    SimpleBooleanProperty ready;
    SimpleBooleanProperty enemyReady;
    FieldDisplay myFieldDisplay;
    FieldDisplay enemyFieldDisplay;
    SimpleStringProperty printChatMessage;
    SimpleBooleanProperty playIsGoing;
    SimpleBooleanProperty allShipSetted;
    SimpleObjectProperty sendObject;
    SimpleBooleanProperty gameOver;
    boolean iLoose;
    Person enemyPerson;
    @FXML
    Label myNameLabel;
    @FXML
    Label enemyNameLabel;
    @FXML
    Pane myFieldPane;
    @FXML
    Pane enemyFieldPane;
    @FXML
    ToggleButton readyToggleButton;
    @FXML
    Button breakPlayButton;
    private Player player;
    private int turn;

    @FXML
    void myFieldClicked(MouseEvent event) {
    }

    @FXML
    void breakPlay(ActionEvent event) {
        printChatMessage.set(" Бой прерван мной\n");
        sendObject.set(Command.BreakPlay);
        breakPlay();
    }

    void breakPlay() {
        playIsGoing.set(false);
        readyToggleButton.fire();
        readyToggleButton.setDisable(true);
    }

    @FXML
    void ready(ActionEvent event) {
        if (readyToggleButton.isSelected()) {
            ready.set(true);
        } else {
            ready.set(false);
        }
    }

    @FXML
    void enemyFieldClicked(MouseEvent event) {

        if (!playIsGoing.getValue() || !myTurn) return;
        int x = (int) (event.getX() / 20);
        int y = (int) (event.getY() / 20);
        Coord coord = new Coord(x, y);
        if (player.getEnemyField().getCell(coord).isShooted()) {
            printChatMessage.set(" Этот квадрат уже обстрелян. Выберите другой квадрат.\n");
            printChatMessage.set(null);
            return;
        }
        player.setShootCoord(coord);
        printChatMessage.set(" Ход " + turn++ + ": Бью " + coord + ", ");
        sendObject.set(coord);
    }

    void setShootResult(ShootResult shootResult) {
        if (shootResult == ShootResult.MISSED) {
            myTurn = false;
        }
        printChatMessage.set("результат: " + shootResult + "\n");
        player.setShootResult(shootResult);
        enemyFieldDisplay.paint();
        if (shootResult == ShootResult.KILLED) {
            if (player.isGameOver()) {
                gameOver();
            }
        }
    }

    void setCoord(Coord coord) {
        ShootResult shootResult = player.receiveShoot(coord);
        myFieldDisplay.paint();
        sendObject.set(shootResult);
        printChatMessage.set(" Враг ударил в " + coord + ": " + shootResult + "\n");
        if (shootResult == ShootResult.KILLED) {
            if (player.isGameOver()) {
                gameOver();
            }
        }
        if (shootResult == ShootResult.MISSED) {
            myTurn = true;
            printChatMessage.set(" Мой ход\n");
        }
    }

    void startPlay() {
        playIsGoing.set(true);
        breakPlayButton.setDisable(false);
        printChatMessage.set("Игра началась\n");
        player = new Player("Z");
        player.setMyField(myField);
        readyToggleButton.setDisable(true);
        myFieldDisplay = new FieldDisplay(myFieldPane, player.getMyField());
        enemyFieldDisplay = new FieldDisplay(enemyFieldPane, player.getEnemyField());
        myFieldDisplay.paint();
        enemyFieldDisplay.paint();
        Random random = new Random();
        myRandom = random.nextDouble();
        sendObject.set(myRandom);
    }

    void setEnemyRandom(double enemyRandom) {

        if (enemyRandom < myRandom) {
            printChatMessage.set("Я хожу первый\n");
            myTurn = true;
        } else {
            printChatMessage.set(" Враг ходит первый\n");
            myTurn = false;
        }
        readyToggleButton.setDisable(true);
        turn = 1;
    }

    private void gameOver() {
        printChatMessage.set(" Игра окончена\n");
        if (player.isMyLoose()) {
            printChatMessage.set(" Я проиграл\n");
            iLoose = true;
        } else {
            printChatMessage.set(" Я выиграл\n");
            iLoose = false;
        }
        gameOver.set(true);
        playIsGoing.set(false);
        readyToggleButton.fire();
        readyToggleButton.setDisable(true);
    }

    public void setEnemyShips(List<Ship> ships) {
        player.getEnemyField().getReconstructedShips().addAll(ships);
        enemyFieldDisplay.paint();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PaneService.drawGrid(myFieldPane);
        PaneService.drawGrid(enemyFieldPane);

    }
}
