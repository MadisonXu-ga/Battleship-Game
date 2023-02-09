package edu.duke.rx50.battleship;

import java.util.HashSet;

public class RhodeShip<T> extends BasicShip<T> {
    final String name;

    static HashSet<Coordinate> makeRectangleCoords(Coordinate upperLeft, int width, int height) {
        HashSet<Coordinate> s = new HashSet<Coordinate>();
        for (int row = 0; row < height; ++row) {
            for (int column = 0; column < width; ++column) {
                s.add(new Coordinate(upperLeft.getRow() + row, upperLeft.getColumn() + column));
            }
        }
        return s;
    }

    static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int width, int height, char orientation) {
        HashSet<Coordinate> sh = makeRectangleCoords(upperLeft, width, height);
        switch (orientation) {
            case 'U':
                sh.remove(new Coordinate(upperLeft.getRow() + 4, upperLeft.getColumn()));
                sh.remove(new Coordinate(upperLeft.getRow(), upperLeft.getColumn() + 1));
                sh.remove(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn() + 1));
                break;
            case 'R':
                sh.remove(upperLeft);
                sh.remove(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn() + 3));
                sh.remove(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn() + 4));
                break;

            case 'D':
                sh.remove(new Coordinate(upperLeft.getRow() + 3, upperLeft.getColumn()));
                sh.remove(new Coordinate(upperLeft.getRow() + 4, upperLeft.getColumn()));
                sh.remove(new Coordinate(upperLeft.getRow(), upperLeft.getColumn() + 1));
                break;

            case 'L':
                sh.remove(upperLeft);
                sh.remove(new Coordinate(upperLeft.getRow(), upperLeft.getColumn() + 1));
                sh.remove(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn() + 4));
                break;
        }
        return sh;
    }

    public RhodeShip(String name, Coordinate upperLeft, int width, int height, char orientation,
            ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {
        super(makeCoords(upperLeft, width, height, orientation), myDisplayInfo, enemyDisplayInfo);
        this.name = name;
    }

    public RhodeShip(String name, Coordinate uppperLeft, int width, int height, char orientation, T data, T onHit) {
        this(name, uppperLeft, width, height, orientation, new SimpleShipDisplayInfo<>(data, onHit),
                new SimpleShipDisplayInfo<>(null, data));
    }

    @Override
    public String getName() {
        return name;
    }

}
