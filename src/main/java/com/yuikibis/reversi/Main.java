package com.yuikibis.reversi;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage stage;

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        initGame();
    }

    private void initGame() {
        String appName = "Reversi";
        stage.setTitle(appName);
        stage.setResizable(false);

        BorderPane pane = new BorderPane();
        Table table = Table.getInstance();
        Group root = table.getRoot();
        pane.setTop(makeMenu());
        pane.setBottom(root);
        double sceneSizeWidth = 660;
        double sceneSizeHeight = 700;
        Scene scene = new Scene(pane, sceneSizeWidth, sceneSizeHeight);
        stage.setScene(scene);
        stage.show();

        //TODO: アイコンの追加。(やり方がいまいちつかめていない。)
        //primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("reversi.png")));

        // Diskを四枚回転
        Disks disks = Disks.getInstance();
        disks.getDisk(3, 3).setFill(Color.WHITE);
        disks.getDisk(4, 4).setFill(Color.WHITE);
        disks.getDisk(3, 4).setFill(Color.BLACK);
        disks.getDisk(4, 3).setFill(Color.BLACK);
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
        Disks.resetInstance();
        Table.resetInstance();

        initGame();
    }
}
