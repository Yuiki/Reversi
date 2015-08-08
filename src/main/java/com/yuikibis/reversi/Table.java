package com.yuikibis.reversi;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.*;
import javafx.scene.text.Font;

import java.util.Objects;

public class Table {
    private static Table table;
    private final Group root = new Group();

    private static Label currentPlayer;
    private static Label blackScore;
    private static Label whiteScore;
    private static Label result;


    private Table() {
        root.getChildren().addAll(makePane(), makeHorizontalLine(), makeVerticalLine(), makeDetailsLine(), makeScoreLine(), makePath(), makeResult());
    }


    public static Table getInstance() {
        if (Objects.isNull(table)) {
            table = new Table();
        }
        return table;
    }

    public static void resetTable() {
        table = null;
        currentPlayer = null;
        blackScore = null;
        whiteScore = null;
        getInstance();
    }

    public Group getRoot() {
        return root;
    }

    private Path makePath() {
        return new Path(new MoveTo(60.0, 45.0), new LineTo(600.0, 45.0));
    }

    private GridPane makePane() {
        double panePaddingTop = 90.0;
        double panePaddingRight = 50.0;
        double panePaddingBottom = 50.0;
        double panePaddingLeft = 50.0;
        double diskMargin = 5.0;

        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: #a0c000;");
        pane.setPadding(new Insets(panePaddingTop, panePaddingRight, panePaddingBottom, panePaddingLeft));
        pane.setGridLinesVisible(true);

        Disk disk = Disk.getInstance();
        Circle[][] disks = disk.getCircles();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                pane.add(disks[row][col], row, col);
                GridPane.setMargin(disks[row][col], new Insets(diskMargin, diskMargin, diskMargin, diskMargin));
            }
        }

        return pane;
    }

    private VBox makeVerticalLine() {
        double vBoxPaddingTop = 90.0;
        double vBoxPaddingRight = 0.0;
        double vBoxPaddingBottom = 50.0;
        double vBoxPaddingLeft = 15.0;

        double itemPaddingTop = 18.5;
        double itemPaddingRight = 0.0;
        double itemPaddingBottom = 18.5;
        double itemPaddingLeft = 0.0;

        double fontSize = 30.0;

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(vBoxPaddingTop, vBoxPaddingRight, vBoxPaddingBottom, vBoxPaddingLeft));

        for (int i = 1; i <= 8; i++) {
            Label label = makeLineLabel(String.valueOf(i), fontSize, itemPaddingTop, itemPaddingRight, itemPaddingBottom, itemPaddingLeft);
            vBox.getChildren().add(label);
        }

        return vBox;
    }

    private HBox makeHorizontalLine() {
        double hBoxPaddingTop = 50.0;
        double hBoxPaddingRight = 50.0;
        double hBoxPaddingBottom = 0.0;
        double hBoxPaddingLeft = 50.0;

        double itemPaddingTop = 0.0;
        double itemPaddingRight = 27.0;
        double itemPaddingBottom = 0.0;
        double itemPaddingLeft = 26.0;

        double fontSize = 30.0;

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(hBoxPaddingTop, hBoxPaddingRight, hBoxPaddingBottom, hBoxPaddingLeft));

        for (char counter = 'a'; counter <= 'h'; counter++) {
            Label label = makeLineLabel(String.valueOf(counter), fontSize, itemPaddingTop, itemPaddingRight, itemPaddingBottom, itemPaddingLeft);
            hBox.getChildren().add(label);
        }

        return hBox;
    }

    private HBox makeResult() {
        double hBoxPaddingTop = 0.0;
        double hBoxPaddingRight = 0.0;
        double hBoxPaddingBottom = 0.0;
        double hBoxPaddingLeft = 265.0;

        double itemPaddingTop = 10.0;
        double itemPaddingRight = 0.0;
        double itemPaddingBottom = 18.5;
        double itemPaddingLeft = 0.0;

        double fontSize = 25.0;

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(hBoxPaddingTop, hBoxPaddingRight, hBoxPaddingBottom, hBoxPaddingLeft));

        result = makeLineLabel("", fontSize, itemPaddingTop, itemPaddingRight, itemPaddingBottom, itemPaddingLeft);

        hBox.getChildren().add(result);

        return hBox;
    }

    private HBox makeDetailsLine() {
        double hBoxPaddingTop = 0.0;
        double hBoxPaddingRight = 0.0;
        double hBoxPaddingBottom = 0.0;
        double hBoxPaddingLeft = 50.0;

        double itemPaddingTop = 10.0;
        double itemPaddingRight = 0.0;
        double itemPaddingBottom = 18.5;
        double itemPaddingLeft = 0.0;

        double fontSize = 25.0;

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(hBoxPaddingTop, hBoxPaddingRight, hBoxPaddingBottom, hBoxPaddingLeft));

        currentPlayer = makeLineLabel("「" + Player.getCurrentPlayer().getName().toString() + "」のターン", fontSize, itemPaddingTop, itemPaddingRight, itemPaddingBottom, itemPaddingLeft);

        hBox.getChildren().add(currentPlayer);

        return hBox;
    }

    private HBox makeScoreLine() {
        double hBoxPaddingTop = 0.0;
        double hBoxPaddingRight = 0.0;
        double hBoxPaddingBottom = 0.0;
        double hBoxPaddingLeft = 465.0;

        double itemPaddingTop = 10.0;
        double itemPaddingRight = 0.0;
        double itemPaddingBottom = 18.5;
        double itemPaddingLeft = 0.0;

        double fontSize = 25.0;

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BOTTOM_CENTER);
        hBox.setPadding(new Insets(hBoxPaddingTop, hBoxPaddingRight, hBoxPaddingBottom, hBoxPaddingLeft));

        Player[] players = Player.getInstance();
        blackScore = makeLineLabel("黒：" + String.format("%02d", players[Player.Name.Black.getIndex()].getScore()), fontSize, itemPaddingTop, itemPaddingRight, itemPaddingBottom, itemPaddingLeft);
        whiteScore = makeLineLabel(" 白：" + String.format("%02d", players[Player.Name.White.getIndex()].getScore()), fontSize, itemPaddingTop, itemPaddingRight, itemPaddingBottom, itemPaddingLeft);

        hBox.getChildren().addAll(blackScore, whiteScore);

        return hBox;
    }


    public void setResult() {
        String result;
        Player[] players = Player.getInstance();
        if (players[Player.Name.Black.getIndex()].getScore() > players[Player.Name.White.getIndex()].getScore()) {
            result = players[Player.Name.Black.getIndex()].getName().toString();
        } else if (players[Player.Name.Black.getIndex()].getScore() < players[Player.Name.White.getIndex()].getScore()) {
            result = players[Player.Name.White.getIndex()].getName().toString();
        } else {
            result = "両";
        }
        Table.result.setText("「" + result + "」の勝ち");
    }

    public void setCurrentPlayerLabel(String msg) {
        currentPlayer.setText("「" + msg + "」のターン");
    }

    public void setScoreLabel(Player.Name name, int score) {
        switch (name) {
            case Black:
                blackScore.setText(name.toString() + "：" + String.format("%02d", score));
                break;
            case White:
                whiteScore.setText(" " + name.toString() + "：" + String.format("%02d", score));
        }
    }


    private Label makeLineLabel(String text, double fontSize, double top, double right, double bottom, double left) {
        String fontName = "System";

        Label label = new Label(text);
        label.setFont(new Font(fontName, fontSize));
        label.setPadding(new Insets(top, right, bottom, left));
        return label;
    }
}
