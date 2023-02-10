package edu.duke.rx50.battleship;

import java.util.HashSet;

public class TeeweeShip<T> extends BasicShip<T> {
    final String name;
    char orientation;
    Coordinate upperLeft;

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
                sh.remove(upperLeft);
                sh.remove(new Coordinate(upperLeft.getRow(), upperLeft.getColumn() + 2));
                break;
            case 'R':
                sh.remove(new Coordinate(upperLeft.getRow(), upperLeft.getColumn() + 1));
                sh.remove(new Coordinate(upperLeft.getRow() + 2, upperLeft.getColumn() + 1));
                break;

            case 'D':
                sh.remove(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn()));
                sh.remove(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn() + 2));
                break;

            case 'L':
                sh.remove(upperLeft);
                sh.remove(new Coordinate(upperLeft.getRow() + 2, upperLeft.getColumn()));
                break;
        }
        return sh;
    }

    public TeeweeShip(String name, Coordinate upperLeft, int width, int height, char orientation,
            ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {
        super(makeCoords(upperLeft, width, height, orientation), myDisplayInfo, enemyDisplayInfo);
        this.name = name;
        this.orientation = orientation;
        this.upperLeft = upperLeft;
    }

    public TeeweeShip(String name, Coordinate uppperLeft, int width, int height, char orientation, T data, T onHit) {
        this(name, uppperLeft, width, height, orientation, new SimpleShipDisplayInfo<>(data, onHit),
                new SimpleShipDisplayInfo<>(null, data));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public char getOrientation() {
        return this.orientation;
    }

    @Override
    public Coordinate getUpperLeft() {
        return this.upperLeft;
    }
}
