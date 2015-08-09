package com.yuikibis.reversi;

import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Objects;

public class Disk {
    private static Disk disk;
    private final Circle[][] circles = new Circle[8][8];

    private Disk() {
        double circleSize = 30;

        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                Circle circle = new Circle(circleSize);
                circles[col][row] = circle;
                circles[col][row].setFill(Color.TRANSPARENT);

                circle.setOnMouseClicked(event -> {
                    if (event.getButton().equals(MouseButton.PRIMARY)) {
                        if (circle.getFill() != Color.TRANSPARENT) {
                            return;
                        }

                        Player currentPlayer = Player.getCurrentPlayer();

                        int x = GridPane.getColumnIndex(circle);
                        int y = GridPane.getRowIndex(circle);
                        Coordinate coordinate = new Coordinate(x, y);

                        currentPlayer.reverse(coordinate);
                    }
                });
            }
        }
    }


    public static Disk getInstance() {
        if (Objects.isNull(disk)) {
            resetDisk();
        }
        return disk;
    }

    public static void resetDisk() {
        disk = new Disk();
    }

    public Circle[][] getCircles() {
        return circles;
    }

    public boolean checkHittable(Player.Name name) {
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                Coordinate coordinate = new Coordinate(col, row);

                // Diskが置いてあるところは無視。
                if (circles[col][row].getFill() != Color.TRANSPARENT) {
                    continue;
                }

                if (checkAround(name, coordinate)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkAround(Player.Name name, Coordinate coordinate) {
        return checkTop(name, coordinate) > 0
                || checkRight(name, coordinate) > 0
                || checkBottom(name, coordinate) > 0
                || checkLeft(name, coordinate) > 0
                || checkUpperRight(name, coordinate) > 0
                || checkUpperLeft(name, coordinate) > 0
                || checkLowerRight(name, coordinate) > 0
                || checkLowerLeft(name, coordinate) > 0;
    }

    public int checkTop(Player.Name color, Coordinate coordinate) {
        int x = coordinate.x;
        int y = coordinate.y;

        for (y--; y >= 0; y--) {
            if (circles[x][y].getFill() == Color.TRANSPARENT) {
                return 0;
            }
            if (circles[x][y].getFill() == color.getColor()) {
                break;
            }
        }

        if (y == -1) {
            return 0;
        }

        int amount = Math.abs(y - coordinate.y);
        if (amount > 1) {
            return amount;
        }
        return 0;
    }

    public int checkBottom(Player.Name color, Coordinate coordinate) {
        int x = coordinate.x;
        int y = coordinate.y;

        for (y++; y < 8; y++) {
            if (circles[x][y].getFill() == Color.TRANSPARENT) {
                return 0;
            }
            if (circles[x][y].getFill() == color.getColor()) {
                break;
            }
        }

        if (y == 8) {
            return 0;
        }

        int amount = Math.abs(y - coordinate.y);
        if (amount > 1) {
            return amount;
        }
        return 0;
    }

    public int checkRight(Player.Name color, Coordinate coordinate) {
        int x = coordinate.x;
        int y = coordinate.y;

        for (x++; x < 8; x++) {
            if (circles[x][y].getFill() == Color.TRANSPARENT) {
                return 0;
            }
            if (circles[x][y].getFill() == color.getColor()) {
                break;
            }
        }

        if (x == 8) {
            return 0;
        }

        int amount = Math.abs(x - coordinate.x);
        if (amount > 1) {
            return amount;
        }
        return 0;
    }

    public int checkLeft(Player.Name color, Coordinate coordinate) {
        int x = coordinate.x;
        int y = coordinate.y;

        for (x--; x >= 0; x--) {
            if (circles[x][y].getFill() == Color.TRANSPARENT) {
                return 0;
            }
            if (circles[x][y].getFill() == color.getColor()) {
                break;
            }
        }

        if (x == -1) {
            return 0;
        }

        int amount = Math.abs(x - coordinate.x);
        if (amount > 1) {
            return amount;
        }
        return 0;
    }

    public int checkUpperRight(Player.Name color, Coordinate coordinate) {
        int x = coordinate.x;
        int y = coordinate.y;

        for (x++, y--; x < 8 && y >= 0; x++, y--) {
            if (circles[x][y].getFill() == Color.TRANSPARENT) {
                return 0;
            }
            if (circles[x][y].getFill() == color.getColor()) {
                break;
            }
        }

        if (x == 8 || y == -1) {
            return 0;
        }

        int amount = Math.abs(y - coordinate.y);
        if (amount > 1) {
            return amount;
        }
        return 0;
    }

    public int checkUpperLeft(Player.Name color, Coordinate coordinate) {
        int x = coordinate.x;
        int y = coordinate.y;

        for (x--, y--; x >= 0 && y >= 0; x--, y--) {
            if (circles[x][y].getFill() == Color.TRANSPARENT) {
                return 0;
            }
            if (circles[x][y].getFill() == color.getColor()) {
                break;
            }
        }

        if (x == -1 || y == -1) {
            return 0;
        }

        int amount = Math.abs(y - coordinate.y);
        if (amount > 1) {
            return amount;
        }
        return 0;
    }

    public int checkLowerRight(Player.Name color, Coordinate coordinate) {
        int x = coordinate.x;
        int y = coordinate.y;

        for (x++, y++; x < 8 && y < 8; x++, y++) {
            if (circles[x][y].getFill() == Color.TRANSPARENT) {
                return 0;
            }
            if (circles[x][y].getFill() == color.getColor()) {
                break;
            }
        }

        if (x == 8 || y == 8) {
            return 0;
        }

        int amount = Math.abs(y - coordinate.y);
        if (amount > 1) {
            return amount;
        }
        return 0;
    }

    public int checkLowerLeft(Player.Name color, Coordinate coordinate) {
        int x = coordinate.x;
        int y = coordinate.y;

        for (x--, y++; x >= 0 && y < 8; x--, y++) {
            if (circles[x][y].getFill() == Color.TRANSPARENT) {
                return 0;
            }
            if (circles[x][y].getFill() == color.getColor()) {
                break;
            }
        }

        if (x == -1 || y == 8) {
            return 0;
        }

        int amount = Math.abs(y - coordinate.y);
        if (amount > 1) {
            return amount;
        }
        return 0;
    }
}
