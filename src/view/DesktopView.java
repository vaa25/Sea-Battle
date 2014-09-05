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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class DesktopView extends Application implements View {
    private int width, height;
    private Pane[][] myPanes;
    private Pane[][] enemyPanes;
    private Image empty;
    private Rectangle ship;
    private Rectangle missed;
    private Rectangle hurt;
    private Rectangle canPlace;
    private Rectangle notCanPlace;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
//        empty=new Image("resources/emptyCell.bmp");
        width = 20;
        height = 20;
        myPanes = new Pane[width][height];
        enemyPanes = new Pane[width][height];
        createEmpty();
        createHurt();
        createMissed();
        createShip();
        createCanPlace();
        createNotCanPlace();
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
        menuBar.setPrefSize(500, 50);

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
                System.out.println("Вывожу информацию");
            }
        });
        menuPlayer.getItems().addAll(menuItemPlayerInfo);

        menuBar.getMenus().addAll(menuFile, menuNet, menuPlayer);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);

        FlowPane playingPane = new FlowPane(Orientation.HORIZONTAL);
        GridPane myField = new GridPane();
        GridPane enemyField = new GridPane();

        myField.setMinSize(200, 200);
        myField.setPrefSize(200, 200);
        myField.setMaxSize(200, 200);
        enemyField.setMinSize(200, 200);
        enemyField.setPrefSize(200, 200);
        enemyField.setMaxSize(200, 200);
        createField(myField, myPanes);
        createField(enemyField, enemyPanes);
        playingPane.getChildren().addAll(myField, enemyField);
        playingPane.setHgap(10);
//        playingPane.autosize();
        borderPane.setCenter(playingPane);
        enemyField.setVisible(false);
        root.getChildren().addAll(borderPane);
    }

    private void createField(GridPane field, Pane[][] panes) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Pane pane = new Pane();
                pane.setMinSize(10, 10);
                pane.setPrefSize(10, 10);
                pane.setMaxSize(10, 10);
                pane.getChildren().addAll(createEmpty());

                pane.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Pane source = (Pane) event.getSource();

//                        source.getChildren().set(0,canSet);
                        System.out.println(source.getChildren().toString());
                    }
                });
                panes[i][j] = pane;
                field.add(pane, i, j);
            }
        }
        System.out.println();
    }

    private Rectangle createEmpty() {
        Rectangle rectangle = new Rectangle(10, 10, Color.BLUE);
//        rectangle.setFill(new ImagePattern(empty));
        return rectangle;
    }

    private Rectangle createShip() {
        Rectangle rectangle = new Rectangle(10, 10, Color.BLACK);
//        rectangle.setFill(new ImagePattern(empty));
        return rectangle;
    }

    private Rectangle createMissed() {
        Rectangle rectangle = new Rectangle(10, 10, Color.WHEAT);
//        rectangle.setFill(new ImagePattern(empty));
        return rectangle;
    }

    private Rectangle createHurt() {
        Rectangle rectangle = new Rectangle(10, 10, Color.RED);
//        rectangle.setFill(new ImagePattern(empty));
        return rectangle;
    }

    private Rectangle createCanPlace() {
        Rectangle rectangle = new Rectangle(10, 10, Color.GREEN);
//        rectangle.setFill(new ImagePattern(empty));
        return rectangle;
    }

    private Rectangle createNotCanPlace() {
        Rectangle rectangle = new Rectangle(10, 10, Color.BROWN);
//        rectangle.setFill(new ImagePattern(empty));
        return rectangle;
    }

    private void getNetwork() {

    }
}
