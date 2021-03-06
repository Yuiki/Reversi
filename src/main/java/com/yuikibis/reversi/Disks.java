package com.yuikibis.reversi;

import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Disks {
    private static Disks disks;
    private final List<List<Circle>> listOfDiskList = new ArrayList<>();

    private final List<List<Rectangle>> listOfCollisionDetectionItemList = new ArrayList<>();

    private Disks() {
        double diskRadius = 30;

        for (int col = 0; col < 8; col++) {
            List<Circle> diskList = new ArrayList<>();
            List<Rectangle> collisionDetectionItemList = new ArrayList<>();

            listOfDiskList.add(diskList);
            this.listOfCollisionDetectionItemList.add(collisionDetectionItemList);
            for (int row = 0; row < 8; row++) {
                Circle disk = new Circle(diskRadius);
                disk.setFill(Color.TRANSPARENT);
                diskList.add(disk);

                // 当たり判定用の四角
                double collisionDetectionItemSize = Table.diskMargin * 2 + diskRadius * 2;
                Rectangle collisionDetectionItem = new Rectangle(collisionDetectionItemSize, collisionDetectionItemSize);
                collisionDetectionItem.setFill(Color.TRANSPARENT);
                collisionDetectionItemList.add(collisionDetectionItem);

                collisionDetectionItem.setOnMouseClicked(event -> {
                    if (event.getButton().equals(MouseButton.PRIMARY)) {
                        if (disk.getFill() != Color.TRANSPARENT) {
                            return;
                        }

                        Player currentPlayer = Player.getCurrentPlayer();

                        int clickedCol = GridPane.getColumnIndex(disk);
                        int clickedRow = GridPane.getRowIndex(disk);
                        Coordinate coordinate = new Coordinate(clickedCol, clickedRow);

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

    public Circle getDisk(int col, int row) {
        return listOfDiskList.get(col).get(row);
    }

    public Rectangle getCollisionDetectionItem(int col, int row) {
        return listOfCollisionDetectionItemList.get(col).get(row);
    }

    public boolean checkHittable(Player.Name name) {
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                Coordinate coordinate = new Coordinate(col, row);

                // Diskが置いてあるところは無視。
                if (listOfDiskList.get(col).get(row).getFill() != Color.TRANSPARENT) {
                    continue;
                }

                if (checkAround(name, coordinate)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkAround(Player.Name name, Coordinate coordinate) {
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
        int col = coordinate.col;
        int row = coordinate.row;

        for (row--; row >= 0; row--) {
            if (listOfDiskList.get(col).get(row).getFill() == Color.TRANSPARENT) {
                return 0;
            }
            if (listOfDiskList.get(col).get(row).getFill() == color.getColor()) {
                break;
            }
        }

        // はみ出した時
        if (row == -1) {
            return 0;
        }

        int amount = Math.abs(row - coordinate.row);
        if (amount > 1) {
            return amount;
        }
        return 0;
    }

    public int checkBottom(Player.Name color, Coordinate coordinate) {
        int col = coordinate.col;
        int row = coordinate.row;

        for (row++; row < 8; row++) {
            if (listOfDiskList.get(col).get(row).getFill() == Color.TRANSPARENT) {
                return 0;
            }
            if (listOfDiskList.get(col).get(row).getFill() == color.getColor()) {
                break;
            }
        }

        if (row == 8) {
            return 0;
        }

        int amount = Math.abs(row - coordinate.row);
        if (amount > 1) {
            return amount;
        }
        return 0;
    }

    public int checkRight(Player.Name color, Coordinate coordinate) {
        int col = coordinate.col;
        int row = coordinate.row;

        for (col++; col < 8; col++) {
            if (listOfDiskList.get(col).get(row).getFill() == Color.TRANSPARENT) {
                return 0;
            }
            if (listOfDiskList.get(col).get(row).getFill() == color.getColor()) {
                break;
            }
        }

        if (col == 8) {
            return 0;
        }

        int amount = Math.abs(col - coordinate.col);
        if (amount > 1) {
            return amount;
        }
        return 0;
    }

    public int checkLeft(Player.Name color, Coordinate coordinate) {
        int col = coordinate.col;
        int row = coordinate.row;

        for (col--; col >= 0; col--) {
            if (listOfDiskList.get(col).get(row).getFill() == Color.TRANSPARENT) {
                return 0;
            }
            if (listOfDiskList.get(col).get(row).getFill() == color.getColor()) {
                break;
            }
        }

        if (col == -1) {
            return 0;
        }

        int amount = Math.abs(col - coordinate.col);
        if (amount > 1) {
            return amount;
        }
        return 0;
    }

    public int checkUpperRight(Player.Name color, Coordinate coordinate) {
        int col = coordinate.col;
        int row = coordinate.row;

        for (col++, row--; col < 8 && row >= 0; col++, row--) {
            if (listOfDiskList.get(col).get(row).getFill() == Color.TRANSPARENT) {
                return 0;
            }
            if (listOfDiskList.get(col).get(row).getFill() == color.getColor()) {
                break;
            }
        }

        if (col == 8 || row == -1) {
            return 0;
        }

        int amount = Math.abs(row - coordinate.row);
        if (amount > 1) {
            return amount;
        }
        return 0;
    }

    public int checkUpperLeft(Player.Name color, Coordinate coordinate) {
        int col = coordinate.col;
        int row = coordinate.row;

        for (col--, row--; col >= 0 && row >= 0; col--, row--) {
            if (listOfDiskList.get(col).get(row).getFill() == Color.TRANSPARENT) {
                return 0;
            }
            if (listOfDiskList.get(col).get(row).getFill() == color.getColor()) {
                break;
            }
        }

        if (col == -1 || row == -1) {
            return 0;
        }

        int amount = Math.abs(row - coordinate.row);
        if (amount > 1) {
            return amount;
        }
        return 0;
    }

    public int checkLowerRight(Player.Name color, Coordinate coordinate) {
        int col = coordinate.col;
        int row = coordinate.row;

        for (col++, row++; col < 8 && row < 8; col++, row++) {
            if (listOfDiskList.get(col).get(row).getFill() == Color.TRANSPARENT) {
                return 0;
            }
            if (listOfDiskList.get(col).get(row).getFill() == color.getColor()) {
                break;
            }
        }

        if (col == 8 || row == 8) {
            return 0;
        }

        int amount = Math.abs(row - coordinate.row);
        if (amount > 1) {
            return amount;
        }
        return 0;
    }

    public int checkLowerLeft(Player.Name color, Coordinate coordinate) {
        int col = coordinate.col;
        int row = coordinate.row;

        for (col--, row++; col >= 0 && row < 8; col--, row++) {
            if (listOfDiskList.get(col).get(row).getFill() == Color.TRANSPARENT) {
                return 0;
            }
            if (listOfDiskList.get(col).get(row).getFill() == color.getColor()) {
                break;
            }
        }

        if (col == -1 || row == 8) {
            return 0;
        }

        int amount = Math.abs(row - coordinate.row);
        if (amount > 1) {
            return amount;
        }
        return 0;
    }
}
