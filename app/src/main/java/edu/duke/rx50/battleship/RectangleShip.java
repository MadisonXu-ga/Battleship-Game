package edu.duke.rx50.battleship;

import java.util.HashSet;

public class RectangleShip<T> extends BasicShip<T> {
  final String name;

  /**
   * static function to initialize a hashset containing occupied coordinates
   */
  static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int width, int height) {
    HashSet<Coordinate> s = new HashSet<Coordinate>();
    for (int row = 0; row < height; ++row) {
      for (int column = 0; column < width; ++column) {
        s.add(new Coordinate(upperLeft.getRow() + row, upperLeft.getColumn() + column));
      }
    }
    return s;
  }

  /**
   * Constrcutor1
   */
  public RectangleShip(String name, Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> myDisplayInfo,
      ShipDisplayInfo<T> enemyDisplayInfo) {
    super(makeCoords(upperLeft, width, height), myDisplayInfo, enemyDisplayInfo);
    this.name = name;
  }

  /**
   * convience constructors
   * for my own view diplay: data if not hit; onHit if hit
   * for the enemy view: nothing if not hit; data if hit
   */
  public RectangleShip(String name, Coordinate upperLeft, int width, int height, T data, T onHit) {
    this(name, upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit),
        new SimpleShipDisplayInfo<T>(null, data));
  }

  /**
   * just use for testing
   */
  public RectangleShip(Coordinate upperLeft, T data, T onHit) {
    this("testship", upperLeft, 1, 1, data, onHit);
  }

  @Override
  public String getName() {
    return name;
  }
}
