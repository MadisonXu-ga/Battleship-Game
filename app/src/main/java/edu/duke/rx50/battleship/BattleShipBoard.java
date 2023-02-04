package edu.duke.rx50.battleship;

import java.util.ArrayList;
import java.util.HashSet;

public class BattleShipBoard<T> implements Board<T> {
  private final int width;
  private final int height;
  final ArrayList<Ship<T>> myShips;
  private final PlacementRuleChecker<T> placementChecker;
  HashSet<Coordinate> enemyMisses;

  final T missInfo;

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  /**
   * Constructs a BattleShipBoard with the specified width
   * and height
   * 
   * @param w is the width of the newly constructed board.
   * @param h is the height of the newly constructed board.
   * @throws IllegalArgumentException if the width or height are less than or
   *                                  equal to zero.
   */
  public BattleShipBoard(int w, int h, T missInfo, PlacementRuleChecker<T> placementChecker) {
    if (w <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + w);
    }
    if (h <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + h);
    }
    this.width = w;
    this.height = h;
    this.myShips = new ArrayList<Ship<T>>();
    this.placementChecker = placementChecker;
    this.enemyMisses = new HashSet<Coordinate>();
    this.missInfo = missInfo;
  }

  public BattleShipBoard(int w, int h, T missInfo) {
    this(w, h, missInfo, new InBoundsRuleChecker<T>(new NoCollisionRuleChecker<T>(null)));
  }

  public String tryAddShip(Ship<T> toAdd) {
    String error = placementChecker.checkPlacement(toAdd, this);
    if (error == null) {
      myShips.add(toAdd);
      return null;
    }
    return error;
  }

  /**
   * unhit ship: letter
   * hit ship: *
   * miss: blank
   * 
   * @param target coordinate
   * @return what is at coordiante for player self
   */
  public T whatIsAtForSelf(Coordinate where) {
    return whatIsAt(where, true);
  }
  
  /**
   * unhit ship: blank
   * hit ship: letter
   * miss: X 
   */
  public T whatIsAtForEnemy(Coordinate where) {
    return whatIsAt(where, false);
  }

  protected T whatIsAt(Coordinate where, boolean isSelf) {
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(where)) {
        return s.getDisplayInfoAt(where, isSelf);
      }
    }
    if(!isSelf && enemyMisses.contains(where)){
      return missInfo;
    }
    return null;
  }

  /**
   * Fire at c
   * 
   * @param coordinate where we hit
   * @return ship if hit or null if miss
   */
  public Ship<T> fireAt(Coordinate c) {
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(c)) {
        s.recordHitAt(c);
        return s;
      }
    }
    enemyMisses.add(c);
    return null;
  }
}
