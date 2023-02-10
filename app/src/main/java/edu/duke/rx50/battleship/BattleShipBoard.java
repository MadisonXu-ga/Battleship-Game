package edu.duke.rx50.battleship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class BattleShipBoard<T> implements Board<T> {
  private final int width;
  private final int height;
  final ArrayList<Ship<T>> myShips;
  private final PlacementRuleChecker<T> placementChecker;
  HashSet<Coordinate> enemyMisses;

  // HashMap<Coordinate, T> fireCoordsStatusForSelf;
  HashMap<Coordinate, T> fireCoordsStatusForEnemy;

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

    // this.fireCoordsStatusForSelf = new HashMap<>();
    this.fireCoordsStatusForEnemy = new HashMap<>();
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
    // Ship display info
    // for (Ship<T> s : myShips) {
    // if (s.occupiesCoordinates(where)) {
    // return s.getDisplayInfoAt(where, isSelf);
    // }
    // }
    // if (!isSelf && enemyMisses.contains(where)) {
    // return missInfo;
    // }

    // Board display info
    if (isSelf) {
      for (Ship<T> s : myShips) {
        if (s.occupiesCoordinates(where)) {
          return s.getDisplayInfoAt(where, isSelf);
        }
      }
    }
    if (!isSelf && fireCoordsStatusForEnemy.containsKey(where)) {
      return fireCoordsStatusForEnemy.get(where);
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
        // fireCoordsStatusForSelf.put(c, s.getDisplayInfoAt(c, true));
        fireCoordsStatusForEnemy.put(c, s.getDisplayInfoAt(c, false));
        return s;
      }
    }
    enemyMisses.add(c);
    fireCoordsStatusForEnemy.put(c, missInfo);
    return null;
  }

  public boolean checkShipRemain() {
    for (Ship<T> s : myShips) {
      if (!s.isSunk()) {
        return true;
      }
    }
    return false;
  }

  /**
   * find the ship which occupies coordinate lc
   * 
   * @param loc maybe part of a ship
   * @return the ship if loc is valid, null if invalid
   */
  public Ship<T> findShip(Coordinate loc) {
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(loc)) {
        return s;
      }
    }
    return null;
  }

  public String findShipName(Coordinate c) {
    if (findShip(c) != null) {
      return findShip(c).getName();
    }
    return null;
  }

  protected String relocateShipHelper(char orien_tar, char orien_ori, Placement target, Ship<T> newShip, Ship<T> s) {
    // can abstract calculate dist as a function
    ArrayList<Character> rotations = new ArrayList<>(Arrays.asList('U', 'R', 'D', 'L'));
    int dist = 4;
    if (orien_ori == 'H' || orien_ori == 'V') {
      if (orien_tar != orien_ori) {
        if (orien_ori == 'H') {
          dist = 1;
        } else {
          dist = 3;
        }
      }
    } else {
      if (orien_tar != orien_ori) {
        dist = (rotations.indexOf(orien_tar) - rotations.indexOf(orien_ori) + 4) % 4;
      }
    }

    Coordinate oldUpperLeft = new Coordinate(s.getUpperLeft().getRow(),
        s.getUpperLeft().getColumn());
    Coordinate newUpperLeft = new Coordinate(target.getWhere().getRow(), target.getWhere().getColumn());

    int deltaR = findDeltaR(s);
    int deltaC = findDeltaC(s);
    int oldAfterRotation_r = 0;
    int oldAfterRotation_c = 0;
    ArrayList<Coordinate> hitPieces = getHitPieces(s);
    ArrayList<Coordinate> newHitPieces = new ArrayList<>();
    for (int i = 0; i < dist; ++i) {
      // first, calculate old upperleft point in new shape's coordinate after rotation
      if (i % 2 == 0) {
        oldAfterRotation_r = newUpperLeft.getRow();
        oldAfterRotation_c = newUpperLeft.getColumn() + deltaR;
      } else {
        oldAfterRotation_r = newUpperLeft.getRow();
        oldAfterRotation_c = newUpperLeft.getColumn() + deltaC;
      }

      // second, calculate hit parts of the old ship
      for (int j = 0; j < hitPieces.size(); ++j) {
        int diffRow = hitPieces.get(j).getRow() - oldUpperLeft.getRow();
        int diffColumn = hitPieces.get(j).getColumn() - oldUpperLeft.getColumn();

        newHitPieces.add(new Coordinate(oldAfterRotation_r + diffColumn, oldAfterRotation_c - diffRow));
      }

      // update hitPieces
      hitPieces = new ArrayList<Coordinate>(newHitPieces);
      newHitPieces.clear();

      // update oldUpperLeft
      oldUpperLeft = new Coordinate(newUpperLeft.getRow(), newUpperLeft.getColumn());
    }
    for (Coordinate c : hitPieces) {
      newShip.recordHitAt(c);
    }

    myShips.remove(s);
    String error = tryAddShip(newShip);
    return error;
  }

  /*
   * main relocate function
   */
  public String relocateShip(Coordinate origin, Placement target, Ship<T> newShip) {
    // maybe need to change what is at for enemy!!!
    // if target is valid!
    Ship<T> s = findShip(origin);
    char orien_ori = s.getOrientation();
    char orien_tar = target.getOrientation();

    return relocateShipHelper(orien_tar, orien_ori, target, newShip, s);
  }

  protected ArrayList<Coordinate> getHitPieces(Ship<T> s) {
    // return a new one!!(?)
    ArrayList<Coordinate> hitPieces = new ArrayList<>();
    for (Coordinate c : s.getCoordinates()) {
      if (s.wasHitAt(c)) {
        hitPieces.add(c);
      }
    }
    return hitPieces;
  }

  protected int findDeltaR(Ship<T> s) {
    int deltaR = 0;
    for (Coordinate c1 : s.getCoordinates()) {
      for (Coordinate c2 : s.getCoordinates()) {
        if (Math.abs(c2.getRow() - c1.getRow()) > deltaR) {
          deltaR = Math.abs(c2.getRow() - c1.getRow());
        }
      }
    }
    return deltaR;
  }

  protected int findDeltaC(Ship<T> s) {
    int deltaC = 0;
    for (Coordinate c1 : s.getCoordinates()) {
      for (Coordinate c2 : s.getCoordinates()) {
        if (Math.abs(c2.getColumn() - c1.getColumn()) > deltaC) {
          deltaC = Math.abs(c2.getColumn() - c1.getColumn());
        }
      }
    }
    return deltaC;
  }
}
