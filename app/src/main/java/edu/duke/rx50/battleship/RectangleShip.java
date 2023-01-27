package edu.duke.rx50.battleship;

import java.util.HashSet;

public class RectangleShip<T> extends BasicShip<T> {
  static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int width, int height) {
    HashSet<Coordinate> s = new HashSet<Coordinate>();
    for (int row = 0; row < height; ++row) {
      for (int column = 0; column < width; ++column) {
        s.add(new Coordinate(upperLeft.getRow() + row, upperLeft.getColumn() + column));
      }
    }
    return s;
  }

  public RectangleShip(Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> displayInfo) {
    super(makeCoords(upperLeft, width, height), displayInfo);
  }


  // convience constructors
  public RectangleShip(Coordinate upperLeft, int width, int height, T data, T onHit) {
    this(upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit));
  }

  public RectangleShip(Coordinate upperLeft, T data, T onHit) {
    this(upperLeft, 1, 1, data, onHit);
  }
}
