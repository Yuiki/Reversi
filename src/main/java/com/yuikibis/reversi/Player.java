package com.yuikibis.reversi;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Player {
    private static final Player[] players = new Player[2];
    private static int current = 0;
    private final Name name;
    private int score = 2; /*最初から二枚置いてあるため。*/

    private Player(Name name) {
        this.name = name;
    }

    enum Name {
        Black(0, "黒", Color.BLACK), White(1, "白", Color.WHITE);

        private final int index;
        private final Color color;
        private final String jpName;

        Name(int index, String jpName, Color color) {
            this.index = index;
            this.jpName = jpName;
            this.color = color;
        }

        public int getIndex() {
            return this.index;
        }

        public Color getColor() {
            return this.color;
        }

        @Override
        public String toString() {
            return this.jpName;
        }
    }

    public static Player[] getInstance() {
        if (players[Name.Black.getIndex()] == null) {
            players[Name.Black.getIndex()] = new Player(Name.Black);
            players[Name.White.getIndex()] = new Player(Name.White);
        }
        return players;
    }

    public static void resetInstance() {
        players[Name.Black.getIndex()] = null;
        players[Name.White.getIndex()] = null;
        current = 0;

        getInstance();
    }

    public static Player getCurrentPlayer() {
        getInstance();
        return players[current];
    }

    public static Player getNextPlayer() {
        if (current == Name.Black.getIndex()) {
            return players[Name.White.getIndex()];
        } else {
            return players[Name.Black.getIndex()];
        }
    }

    public static void next() {
        if (current == Name.Black.getIndex()) {
            current = Name.White.getIndex();
        } else {
            current = Name.Black.getIndex();
        }

        Disk disk = Disk.getInstance();
        if (!disk.checkHittable(players[Player.current].getName())) {
            if (current == Name.Black.getIndex()) {
                current = Name.White.getIndex();
            } else {
                current = Name.Black.getIndex();
            }
        }
    }

    public void reverse(Coordinate coordinate) {
        Name name = this.name;
        Player nextPlayer = getNextPlayer();

        int tmp;
        boolean isReversed = false;
        if ((tmp = reverseTop(name, coordinate)) > 0) {
            isReversed = true;
            this.score += tmp;
            nextPlayer.score -= tmp;
        }
        if ((tmp = reverseRight(name, coordinate)) > 0) {
            isReversed = true;
            this.score += tmp;
            nextPlayer.score -= tmp;
        }
        if ((tmp = reverseBottom(name, coordinate)) > 0) {
            isReversed = true;
            this.score += tmp;
            nextPlayer.score -= tmp;
        }
        if ((tmp = reverseLeft(name, coordinate)) > 0) {
            isReversed = true;
            this.score += tmp;
            nextPlayer.score -= tmp;
        }
        if ((tmp = reverseUpperRight(name, coordinate)) > 0) {
            isReversed = true;
            this.score += tmp;
            nextPlayer.score -= tmp;
        }
        if ((tmp = reverseUpperLeft(name, coordinate)) > 0) {
            isReversed = true;
            this.score += tmp;
            nextPlayer.score -= tmp;
        }
        if ((tmp = reverseLowerRight(name, coordinate)) > 0) {
            isReversed = true;
            this.score += tmp;
            nextPlayer.score -= tmp;
        }
        if ((tmp = reverseLowerLeft(name, coordinate)) > 0) {
            isReversed = true;
            this.score += tmp;
            nextPlayer.score -= tmp;
        }

        // 置いた分のスコアを増加し、次のターンに
        if (isReversed) {
            this.score += 1;
            next();
        }

        Table table = Table.getInstance();
        table.setCurrentPlayerLabel(Player.getCurrentPlayer().getName().toString());
        table.setScoreLabel(Name.Black, players[Name.Black.getIndex()].getScore());
        table.setScoreLabel(Name.White, players[Name.White.getIndex()].getScore());

        if (players[Name.Black.getIndex()].getScore() + players[Name.White.getIndex()].getScore() == 64) {
            table.setResult();
        }
    }

    private int reverseTop(Name name, Coordinate coordinate) {
        Disk disk = Disk.getInstance();
        Circle[][] disks = disk.getCircles();

        int amount = disk.checkTop(name, coordinate);
        for (int i = 0, x2 = coordinate.getX(), y2 = coordinate.getY(); i < amount && y2 >= 0; i++, y2--) {
            disks[x2][y2].setFill(name.getColor());
        }

        return amount - 1;
    }

    private int reverseBottom(Name name, Coordinate coordinate) {
        Disk disk = Disk.getInstance();
        Circle[][] disks = disk.getCircles();

        int amount = disk.checkBottom(name, coordinate);
        for (int i = 0, x2 = coordinate.getX(), y2 = coordinate.getY(); i < amount && y2 < 8; i++, y2++) {
            disks[x2][y2].setFill(name.getColor());
        }

        return amount - 1;
    }

    private int reverseRight(Name name, Coordinate coordinate) {
        Disk disk = Disk.getInstance();
        Circle[][] disks = disk.getCircles();

        int amount = disk.checkRight(name, coordinate);
        for (int i = 0, x2 = coordinate.getX(), y2 = coordinate.getY(); i < amount && x2 < 8; i++, x2++) {
            disks[x2][y2].setFill(name.getColor());
        }

        return amount - 1;
    }

    private int reverseLeft(Name name, Coordinate coordinate) {
        Disk disk = Disk.getInstance();
        Circle[][] disks = disk.getCircles();

        int amount = disk.checkLeft(name, coordinate);
        for (int i = 0, x2 = coordinate.getX(), y2 = coordinate.getY(); i < amount && x2 >= 0; i++, x2--) {
            disks[x2][y2].setFill(name.getColor());
        }

        return amount - 1;
    }

    private int reverseUpperRight(Name name, Coordinate coordinate) {
        Disk disk = Disk.getInstance();
        Circle[][] disks = disk.getCircles();

        int amount = disk.checkUpperRight(name, coordinate);
        for (int i = 0, x2 = coordinate.getX(), y2 = coordinate.getY(); i < amount && x2 < 8 && y2 >= 0; i++, x2++, y2--) {
            disks[x2][y2].setFill(name.getColor());
        }

        return amount - 1;
    }

    private int reverseUpperLeft(Name name, Coordinate coordinate) {
        Disk disk = Disk.getInstance();
        Circle[][] disks = disk.getCircles();

        int amount = disk.checkUpperLeft(name, coordinate);
        for (int i = 0, x2 = coordinate.getX(), y2 = coordinate.getY(); i < amount && x2 >= 0 && y2 >= 0; i++, x2--, y2--) {
            disks[x2][y2].setFill(name.getColor());
        }

        return amount - 1;
    }

    private int reverseLowerRight(Name name, Coordinate coordinate) {
        Disk disk = Disk.getInstance();
        Circle[][] disks = disk.getCircles();

        int amount = disk.checkLowerRight(name, coordinate);
        for (int i = 0, x2 = coordinate.getX(), y2 = coordinate.getY(); i < amount && x2 < 8 && y2 < 8; i++, x2++, y2++) {
            disks[x2][y2].setFill(name.getColor());
        }

        return amount - 1;
    }

    private int reverseLowerLeft(Name name, Coordinate coordinate) {
        Disk disk = Disk.getInstance();
        Circle[][] disks = disk.getCircles();

        int amount = disk.checkLowerLeft(name, coordinate);
        for (int i = 0, x2 = coordinate.getX(), y2 = coordinate.getY(); i < amount && x2 >= 0 && y2 < 8; i++, x2--, y2++) {
            disks[x2][y2].setFill(name.getColor());
        }

        return amount - 1;
    }

    public Name getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }
}
