package com.yuikibis.reversi;

import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Disks {
    private static Disks disks;
    List<List<Circle>> listOfList = new ArrayList<>();

    private Disks() {
        double circleSize = 30;

        for (int col = 0; col < 8; col++) {
            List<Circle> list = new ArrayList<>();
            listOfList.add(list);
            for (int row = 0; row < 8; row++) {
                Circle circle = new Circle(circleSize);
                circle.setFill(Color.TRANSPARENT);
                list.add(circle);

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

    public static Disks getInstance() {
        if (Objects.isNull(disks)) {
            resetInstance();
        }
        return disks;
    }

    public static void resetInstance() {
        disks = new Disks();
    }

    public Circle getDisk(int x, int y) {
        //return listOfList.get(x).get(y);
        return listOfList.get(x).get(y);
    }

    public boolean checkHittable(Player.Name name) {
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                Coordinate coordinate = new Coordinate(col, row);

                // Diskが置いてあるところは無視。
                if (listOfList.get(col).get(row).getFill() != Color.TRANSPARENT) {
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
            if (listOfList.get(x).get(y).getFill() == Color.TRANSPARENT) {
                return 0;
            }
            if (listOfList.get(x).get(y).getFill() == color.getColor()) {
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
            if (listOfList.get(x).get(y).getFill() == Color.TRANSPARENT) {
                return 0;
            }
            if (listOfList.get(x).get(y).getFill() == color.getColor()) {
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
            if (listOfList.get(x).get(y).getFill() == Color.TRANSPARENT) {
                return 0;
            }
            if (listOfList.get(x).get(y).getFill() == color.getColor()) {
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
            if (listOfList.get(x).get(y).getFill() == Color.TRANSPARENT) {
                return 0;
            }
            if (listOfList.get(x).get(y).getFill() == color.getColor()) {
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
            if (listOfList.get(x).get(y).getFill() == Color.TRANSPARENT) {
                return 0;
            }
            if (listOfList.get(x).get(y).getFill() == color.getColor()) {
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
            if (listOfList.get(x).get(y).getFill() == Color.TRANSPARENT) {
                return 0;
            }
            if (listOfList.get(x).get(y).getFill() == color.getColor()) {
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
            if (listOfList.get(x).get(y).getFill() == Color.TRANSPARENT) {
                return 0;
            }
            if (listOfList.get(x).get(y).getFill() == color.getColor()) {
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
            if (listOfList.get(x).get(y).getFill() == Color.TRANSPARENT) {
                return 0;
            }
            if (listOfList.get(x).get(y).getFill() == color.getColor()) {
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
