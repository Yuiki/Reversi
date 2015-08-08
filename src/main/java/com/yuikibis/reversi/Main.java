package com.yuikibis.reversi;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initGame();
    }

    private void initGame() {
        String appName = "Reversi";
        double sceneSizeWidth = 660;
        double sceneSizeHeight = 700;

        Table table = Table.getInstance();
        Group root = table.getRoot();

        primaryStage.setTitle(appName);
        primaryStage.setResizable(false);

        BorderPane pane = new BorderPane();
        pane.setTop(makeMenu());
        pane.setBottom(root);

        Scene scene = new Scene(pane, sceneSizeWidth, sceneSizeHeight);
        primaryStage.setScene(scene);

        //TODO: アイコンの追加。(やり方がいまいちつかめていない。)
        //primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("reversi.png")));

        primaryStage.show();

        // Diskを四枚回転
        Disk disk = Disk.getInstance();
        Circle[][] circles = disk.getCircles();
        circles[3][3].setFill(Color.WHITE);
        circles[4][4].setFill(Color.WHITE);
        circles[3][4].setFill(Color.BLACK);
        circles[4][3].setFill(Color.BLACK);
    }

    private MenuBar makeMenu() {
        Menu menu = new Menu("File");
        MenuItem item = new MenuItem("Reset");
        item.setOnAction(event -> resetGame());
        menu.getItems().addAll(item);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu);
        return menuBar;
    }

    private void resetGame() {
        Player.resetInstance();
        Disk.resetDisk();
        Table.resetTable();

        initGame();
    }
}
