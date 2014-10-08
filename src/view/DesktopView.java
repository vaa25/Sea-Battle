package view;/**
 * Created with IntelliJ IDEA.
 * User: Vlasov Alexander
 * Date: 23.08.2014
 * Time: 17:55
 * To change this template use File | Settings | File Templates.
 * @author Alexander Vlasov
 */

import common.Coord;
import controller.Controller;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class DesktopView extends Application implements View {
    private Controller controller;
    private int width, height;
    private Cell[][] myPanes;
    private Cell[][] enemyPanes;
    private Image empty;
    private Rectangle ship;
    private Rectangle missed;
    private Rectangle hurt;
    private Rectangle canPlace;
    private Rectangle notCanPlace;
    private int gap = 10;
    private int[] shipSizes;
    private int[] shipCounts;
    private int shipAmount;
    private GridPane myField;
    private GridPane enemyField;
    private FlowPane editPane;
    private FlowPane playingPane;
    private Rectangle[] editShips;
    private boolean editModeOn;
    private ToggleGroup toggleGroup;
    private boolean ctrlPressed;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
//        empty=new Image("resources/emptyCell.bmp");
        width = 20;
        height = 20;
        shipAmount = 10;
        shipSizes = new int[]{4, 3, 2, 1};
        shipCounts = new int[]{1, 2, 3, 4,};
        myPanes = new Cell[width][height];
        enemyPanes = new Cell[width][height];
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


        BorderPane borderPane = new BorderPane();
        borderPane.setTop(createMenu());

        playingPane = new FlowPane(Orientation.HORIZONTAL, gap, gap);
        myField = createField();
        enemyField = createField();
        paintField(myField, myPanes);
        paintField(enemyField, enemyPanes);

        editPane = createEditShips();
        playingPane.getChildren().addAll(myField, enemyField, editPane);
//        playingPane.autosize();
        borderPane.setCenter(playingPane);

        playingPane.getChildren().remove(enemyField);
        root.getChildren().addAll(borderPane);
        root.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                ctrlPressed = keyEvent.isControlDown();
            }
        });
        root.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                ctrlPressed = keyEvent.isControlDown();
            }
        });
    }

    private GridPane createField() {
        GridPane field = new GridPane();
        field.setMinSize(200, 200);
        field.setPrefSize(200, 200);
        field.setMaxSize(200, 200);
        return field;
    }

    private MenuBar createMenu() {
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
        return menuBar;
    }

    private void setPlayingMode(boolean mode) {

        if (mode) playingPane.getChildren().addAll(enemyField);
        else playingPane.getChildren().remove(enemyField);

    }

    private void setEditMode(boolean mode) {
        editModeOn = mode;
        if (mode) playingPane.getChildren().addAll(editPane);
        else playingPane.getChildren().remove(editPane);
    }

    private FlowPane createEditShips() {
        editShips = new Rectangle[shipCounts.length];
        toggleGroup = new ToggleGroup();
        RadioButton button;
        FlowPane core = new FlowPane(Orientation.VERTICAL, gap, gap);
        core.setMinSize(200, 200);
        core.setPrefSize(200, 200);
        core.setMaxSize(200, 200);
        for (int i = 0; i < shipCounts.length; i++) {
            RadioButton editShip = createEditShip(shipSizes[i], shipCounts[i]);
            editShip.setToggleGroup(toggleGroup);
            core.getChildren().addAll(editShip);
        }
        toggleGroup.selectToggle(toggleGroup.getToggles().get(0));
        return core;
    }

    private RadioButton createEditShip(int size, int amount) {
        RadioButton button = new RadioButton(String.valueOf(amount));
        Ship ship = new Ship(size);
        button.setGraphic(ship);
        return button;
    }

    private void sendEditShip(Ship ship, Coord coord) {

    }

    private void paintField(GridPane field, Cell[][] cells) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Cell cell = new Cell();
                cell.setMinSize(10, 10);
                cell.setPrefSize(10, 10);
                cell.setMaxSize(10, 10);
                cell.getChildren().addAll(createEmpty());
                cell.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        ctrlPressed = keyEvent.isControlDown();
                    }
                });
                cell.setOnKeyReleased(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        ctrlPressed = keyEvent.isControlDown();
                    }
                });
                cell.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Cell source = (Cell) event.getSource();
                        if (editModeOn) {
                            Ship ship1 = (Ship) ((RadioButton) toggleGroup.getSelectedToggle()).getGraphic();
                            if (ctrlPressed) ship1.setDirection(Ship.Direction.Vertical);
                            else ship1.setDirection(Ship.Direction.Horizontal);
//                            controller.canPlaceShip(source.getCoord(),
//                                    System.out.println(source.getCoord());
                        }

                    }
                });
                cell.setCoord(new Coord(i, j));
                cells[i][j] = cell;
                field.add(cell, i, j);
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


    @Override
    public void show(String[] args) {
        launch(args);
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }
}
