package view;/**
 * Created with IntelliJ IDEA.
 * User: Vlasov Alexander
 * Date: 23.08.2014
 * Time: 17:55
 * To change this template use File | Settings | File Templates.
 * @author Alexander Vlasov
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DesktopView extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Button edit = new Button("Редактирование поля");
        Button net = new Button("Сеть");
        Button ready = new Button("Готов");


        Group root = new Group();
        Scene scene = new Scene(root, 600, 300, Color.BEIGE);
        primaryStage.setScene(scene);
        primaryStage.show();

        MenuBar menuBar = new MenuBar();
        menuBar.setLayoutX(10);
        menuBar.setLayoutY(10);
        menuBar.setBlendMode(BlendMode.HARD_LIGHT);
        menuBar.setCursor(Cursor.CLOSED_HAND);
        DropShadow effect = new DropShadow();
        effect.setOffsetX(5);
        effect.setOffsetY(5);
        menuBar.setEffect(effect);
        menuBar.setStyle("-fx-base:skyblue;-fx-border-width:4pt;-fx-border-color:navy;-fx-font:bold 16pt Georgia;");
        menuBar.setPrefSize(250, 50);

        Menu menuFile = new Menu("Файл");

        MenuItem menuItemSave = new MenuItem("Сохранить поле");
        menuItemSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Сохраняю поле");
            }
        });

        MenuItem menuItemLoad = new MenuItem("Загрузить поле");
        menuItemLoad.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Загружаю поле");
            }
        });

        menuFile.getItems().addAll(menuItemSave, menuItemLoad);

        Menu menuNet = new Menu("Сеть");

        MenuItem menuItemWithServer = new MenuItem("Игра с сервером");
        menuItemWithServer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Подключаюсь к серверу");
            }
        });

        Menu menuWithoutServer = new Menu("Игра без сервера");
        MenuItem menuWithoutServerHost = new MenuItem("Создать подключение");
        menuWithoutServerHost.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Создаю подключение");
            }
        });
        MenuItem menuWithoutServerClient = new MenuItem("Подключиться");
        menuWithoutServerClient.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Подключаюсь");
            }
        });
        menuWithoutServer.getItems().addAll(menuWithoutServerHost, menuWithoutServerClient);
        menuNet.getItems().addAll(menuItemWithServer, menuWithoutServer);

        Menu menuPlayer = new Menu("Игрок");
        MenuItem menuItemPlayerInfo = new MenuItem("Информация");

        menuPlayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Вывожу таблицу рекордов");
            }
        });

        menuPlayer.getItems().addAll(menuItemPlayerInfo);

        menuBar.getMenus().addAll(menuFile, menuNet, menuPlayer);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);

        FlowPane playingPane = new FlowPane(Orientation.HORIZONTAL);
        Pane myField = new Pane();
        Pane enemyField = new Pane();
        myField.setMinSize(200, 200);
        enemyField.setMinSize(200, 200);
        playingPane.getChildren().addAll(myField, enemyField);
        borderPane.setCenter(playingPane);
        root.getChildren().addAll(borderPane);
    }
}
