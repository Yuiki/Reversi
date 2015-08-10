package com.yuikibis.reversi;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;

import java.util.Objects;
import java.util.Optional;

public class Table {
    private static Table table;
    private final Group root = new Group();

    private Label currentPlayerLabel;
    private Label blackScoreLabel;
    private Label whiteScoreLabel;
    private Label resultLabel;

    private Table() {
        root.getChildren().addAll(makePane(), makeHorizontalLine(), makeVerticalLine(), makeCurrentTurn(), makeScoreLine(), makeBoarder(), makeResult());
    }


    public static Table getInstance() {
        if (Objects.isNull(table)) {
            resetInstance();
        }
        return table;
    }

    public static void resetInstance() {
        table = new Table();
    }

    public Group getRoot() {
        return root;
    }

    private Path makeBoarder() {
        return new Path(new MoveTo(60.0, 45.0), new LineTo(600.0, 45.0));
    }

    private GridPane makePane() {
        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: #a0c000;");
        double panePaddingTop = 90.0;
        double panePaddingRight = 50.0;
        double panePaddingBottom = 50.0;
        double panePaddingLeft = 50.0;
        pane.setPadding(new Insets(panePaddingTop, panePaddingRight, panePaddingBottom, panePaddingLeft));
        pane.setGridLinesVisible(true);

        Disks disks = Disks.getInstance();
        double diskMargin = 5.0;
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                pane.add(disks.getDisk(col, row), col, row);
                GridPane.setMargin(disks.getDisk(col, row), new Insets(diskMargin, diskMargin, diskMargin, diskMargin));

                // 当たり判定アイテムを追加
                pane.add(disks.getCollisionDetectionItem(col, row), col, row);
            }
        }

        return pane;
    }

    private VBox makeVerticalLine() {
        VBox vBox = new VBox();
        double vBoxPaddingTop = 90.0;
        double vBoxPaddingRight = 0.0;
        double vBoxPaddingBottom = 50.0;
        double vBoxPaddingLeft = 15.0;
        vBox.setPadding(new Insets(vBoxPaddingTop, vBoxPaddingRight, vBoxPaddingBottom, vBoxPaddingLeft));

        double itemPaddingTop = 18.5;
        double itemPaddingRight = 0.0;
        double itemPaddingBottom = 18.5;
        double itemPaddingLeft = 0.0;
        double fontSize = 30.0;
        for (int i = 1; i <= 8; i++) {
            Label label = makeLineLabel(String.valueOf(i), fontSize, itemPaddingTop, itemPaddingRight, itemPaddingBottom, itemPaddingLeft);
            vBox.getChildren().add(label);
        }

        return vBox;
    }

    private HBox makeHorizontalLine() {
        HBox hBox = new HBox();
        double hBoxPaddingTop = 50.0;
        double hBoxPaddingRight = 50.0;
        double hBoxPaddingBottom = 0.0;
        double hBoxPaddingLeft = 50.0;
        hBox.setPadding(new Insets(hBoxPaddingTop, hBoxPaddingRight, hBoxPaddingBottom, hBoxPaddingLeft));

        double itemPaddingTop = 0.0;
        double itemPaddingRight = 27.0;
        double itemPaddingBottom = 0.0;
        double itemPaddingLeft = 26.0;
        double fontSize = 30.0;
        for (char counter = 'a'; counter <= 'h'; counter++) {
            Label label = makeLineLabel(String.valueOf(counter), fontSize, itemPaddingTop, itemPaddingRight, itemPaddingBottom, itemPaddingLeft);
            hBox.getChildren().add(label);
        }

        return hBox;
    }

    private Label makeResult() {
        double fontSize = 25.0;
        resultLabel = makeLineLabel("", fontSize);
        double paddingTop = 10.0;
        double paddingRight = 0.0;
        double paddingBottom = 0.0;
        double paddingLeft = 265.0;
        resultLabel.setPadding(new Insets(paddingTop, paddingRight, paddingBottom, paddingLeft));

        return resultLabel;
    }

    private Label makeCurrentTurn() {
        double fontSize = 25.0;
        currentPlayerLabel = makeLineLabel("「" + Player.getCurrentPlayer().getName().toString() + "」のターン", fontSize);
        double hBoxPaddingTop = 10.0;
        double hBoxPaddingRight = 0.0;
        double hBoxPaddingBottom = 0.0;
        double hBoxPaddingLeft = 50.0;
        currentPlayerLabel.setPadding(new Insets(hBoxPaddingTop, hBoxPaddingRight, hBoxPaddingBottom, hBoxPaddingLeft));

        return currentPlayerLabel;
    }

    private HBox makeScoreLine() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BOTTOM_CENTER);
        double hBoxPaddingTop = 0.0;
        double hBoxPaddingRight = 0.0;
        double hBoxPaddingBottom = 0.0;
        double hBoxPaddingLeft = 465.0;
        hBox.setPadding(new Insets(hBoxPaddingTop, hBoxPaddingRight, hBoxPaddingBottom, hBoxPaddingLeft));

        double itemPaddingTop = 10.0;
        double itemPaddingRight = 0.0;
        double itemPaddingBottom = 18.5;
        double itemPaddingLeft = 0.0;
        double fontSize = 25.0;

        Player blackPlayer = Player.getInstance(Player.Name.Black);
        Optional<Player> oBlackPlayer = Optional.ofNullable(blackPlayer);
        oBlackPlayer.ifPresent(player -> blackScoreLabel = makeLineLabel(player.getName().toString() + "：" + String.format("%02d", player.getScore()), fontSize, itemPaddingTop, itemPaddingRight, itemPaddingBottom, itemPaddingLeft));

        Player whitePlayer = Player.getInstance(Player.Name.White);
        Optional<Player> oWhitePlayer = Optional.ofNullable(whitePlayer);
        oWhitePlayer.ifPresent(player -> whiteScoreLabel = makeLineLabel(" " + player.getName().toString() + "：" + String.format("%02d", player.getScore()), fontSize, itemPaddingTop, itemPaddingRight, itemPaddingBottom, itemPaddingLeft));

        hBox.getChildren().addAll(blackScoreLabel, whiteScoreLabel);

        return hBox;
    }

    public void showResult() {
        Optional<Player> oBlackPlayer = Optional.ofNullable(Player.getInstance(Player.Name.Black));
        Optional<Player> oWhitePlayer = Optional.ofNullable(Player.getInstance(Player.Name.White));
        oBlackPlayer.ifPresent(blackPlayer -> oWhitePlayer.ifPresent(whitePlayer -> {
            String result;
            if (blackPlayer.getScore() > whitePlayer.getScore()) {
                result = blackPlayer.getName().toString();
            } else if (blackPlayer.getScore() < whitePlayer.getScore()) {
                result = whitePlayer.getName().toString();
            } else {
                result = "両";
            }
            resultLabel.setText("「" + result + "」の勝ち");
        }));

    }

    public void setCurrentPlayerLabel(String msg) {
        currentPlayerLabel.setText("「" + msg + "」のターン");
    }

    public void setScoreLabel(Player.Name name, int score) {
        switch (name) {
            case Black:
                blackScoreLabel.setText(name.toString() + "：" + String.format("%02d", score));
                break;
            case White:
                whiteScoreLabel.setText(" " + name.toString() + "：" + String.format("%02d", score));
        }
    }


    private Label makeLineLabel(String text, double fontSize) {
        double top = 0;
        double right = 0;
        double bottom = 0;
        double left = 0;

        return makeLineLabel(text, fontSize, top, right, bottom, left);
    }

    private Label makeLineLabel(String text, double fontSize, double top, double right, double bottom, double left) {
        Label label = new Label(text);
        String fontName = "System";
        label.setFont(new Font(fontName, fontSize));
        label.setPadding(new Insets(top, right, bottom, left));
        return label;
    }
}
