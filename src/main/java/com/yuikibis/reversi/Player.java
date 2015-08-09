package com.yuikibis.reversi;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Player {
    private static Player blackPlayer = new Player(Name.Black);
    private static Player whitePlayer = new Player(Name.White);
    private static Player current = blackPlayer;
    private final Name name;
    private int score = 2; /*最初から二枚置いてあるため。*/

    private Player(Name name) {
        this.name = name;
    }

    enum Name {
        Black("黒", Color.BLACK), White("白", Color.WHITE);

        private final Color color;
        private final String jpName;

        Name(String jpName, Color color) {
            this.jpName = jpName;
            this.color = color;
        }

        public Color getColor() {
            return this.color;
        }

        @Override
        public String toString() {
            return this.jpName;
        }
    }

    public static Player getInstance(Name name) {
        switch (name) {
            case Black:
                return blackPlayer;
            case White:
                return whitePlayer;
        }
        return null;
    }

    public static void resetInstance() {
        blackPlayer = new Player(Name.Black);
        whitePlayer = new Player(Name.White);
        current = blackPlayer;
    }

    public static Player getCurrentPlayer() {
        return current;
    }

    public static Player getNextPlayer() {
        if (current == blackPlayer) {
            return whitePlayer;
        } else {
            return blackPlayer;
        }
    }

    public static void next() {
        if (current == blackPlayer) {
            current = whitePlayer;
        } else {
            current = blackPlayer;
        }

        Disk disk = Disk.getInstance();
        if (!disk.checkHittable(current.getName())) {
            if (current == blackPlayer) {
                current = whitePlayer;
            } else {
                current = blackPlayer;
            }
        }
    }

    public void reverse(Coordinate coordinate) {
        int oldTotalScore = blackPlayer.score + whitePlayer.score;

        reverseTop(this.name, coordinate);
        reverseRight(this.name, coordinate);
        reverseBottom(this.name, coordinate);
        reverseLeft(this.name, coordinate);
        reverseUpperRight(this.name, coordinate);
        reverseUpperLeft(this.name, coordinate);
        reverseLowerRight(this.name, coordinate);
        reverseLowerLeft(this.name, coordinate);

        Disk disk = Disk.getInstance();
        Circle[][] circles = disk.getCircles();
        int blackScore = 0;
        int whiteScore = 0;
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                if (circles[col][row].getFill() == Color.BLACK) {
                    blackScore++;
                } else if (circles[col][row].getFill() == Color.WHITE) {
                    whiteScore++;
                }
            }
        }
        blackPlayer.score = blackScore;
        whitePlayer.score = whiteScore;

        int newTotalScore = blackScore + whiteScore;
        // 場の石に変化があれば次のターンへ
        if (newTotalScore != oldTotalScore) {
            next();
        }

        Table table = Table.getInstance();
        table.setCurrentPlayerLabel(Player.getCurrentPlayer().getName().toString());
        table.setScoreLabel(Name.Black, blackScore);
        table.setScoreLabel(Name.White, whiteScore);

        if (blackScore + whiteScore == 64) {
            table.showResult();
        }
    }

    private void reverseTop(Name name, Coordinate coordinate) {
        Disk disk = Disk.getInstance();
        Circle[][] disks = disk.getCircles();

        int amount = disk.checkTop(name, coordinate);
        for (int i = 0, x2 = coordinate.x, y2 = coordinate.y; i < amount && y2 >= 0; i++, y2--) {
            disks[x2][y2].setFill(name.getColor());
        }
    }

    private void reverseBottom(Name name, Coordinate coordinate) {
        Disk disk = Disk.getInstance();
        Circle[][] disks = disk.getCircles();

        int amount = disk.checkBottom(name, coordinate);
        for (int i = 0, x2 = coordinate.x, y2 = coordinate.y; i < amount && y2 < 8; i++, y2++) {
            disks[x2][y2].setFill(name.getColor());
        }
    }

    private void reverseRight(Name name, Coordinate coordinate) {
        Disk disk = Disk.getInstance();
        Circle[][] disks = disk.getCircles();

        int amount = disk.checkRight(name, coordinate);
        for (int i = 0, x2 = coordinate.x, y2 = coordinate.y; i < amount && x2 < 8; i++, x2++) {
            disks[x2][y2].setFill(name.getColor());
        }
    }

    private void reverseLeft(Name name, Coordinate coordinate) {
        Disk disk = Disk.getInstance();
        Circle[][] disks = disk.getCircles();

        int amount = disk.checkLeft(name, coordinate);
        for (int i = 0, x2 = coordinate.x, y2 = coordinate.y; i < amount && x2 >= 0; i++, x2--) {
            disks[x2][y2].setFill(name.getColor());
        }
    }

    private void reverseUpperRight(Name name, Coordinate coordinate) {
        Disk disk = Disk.getInstance();
        Circle[][] disks = disk.getCircles();

        int amount = disk.checkUpperRight(name, coordinate);
        for (int i = 0, x2 = coordinate.x, y2 = coordinate.y; i < amount && x2 < 8 && y2 >= 0; i++, x2++, y2--) {
            disks[x2][y2].setFill(name.getColor());
        }
    }

    private void reverseUpperLeft(Name name, Coordinate coordinate) {
        Disk disk = Disk.getInstance();
        Circle[][] disks = disk.getCircles();

        int amount = disk.checkUpperLeft(name, coordinate);
        for (int i = 0, x2 = coordinate.x, y2 = coordinate.y; i < amount && x2 >= 0 && y2 >= 0; i++, x2--, y2--) {
            disks[x2][y2].setFill(name.getColor());
        }
    }

    private void reverseLowerRight(Name name, Coordinate coordinate) {
        Disk disk = Disk.getInstance();
        Circle[][] disks = disk.getCircles();

        int amount = disk.checkLowerRight(name, coordinate);
        for (int i = 0, x2 = coordinate.x, y2 = coordinate.y; i < amount && x2 < 8 && y2 < 8; i++, x2++, y2++) {
            disks[x2][y2].setFill(name.getColor());
        }
    }

    private void reverseLowerLeft(Name name, Coordinate coordinate) {
        Disk disk = Disk.getInstance();
        Circle[][] disks = disk.getCircles();

        int amount = disk.checkLowerLeft(name, coordinate);
        for (int i = 0, x2 = coordinate.x, y2 = coordinate.y; i < amount && x2 >= 0 && y2 < 8; i++, x2--, y2++) {
            disks[x2][y2].setFill(name.getColor());
        }
    }

    public Name getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }
}
