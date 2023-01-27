package edu.duke.rx50.battleship;

import java.util.HashMap;
import java.util.Map;

public abstract class BasicShip<T> implements Ship<T> {
  // private final Coordinate myLocation;
  protected HashMap<Coordinate, Boolean> myPieces;
  protected ShipDisplayInfo<T> myDisplayInfo;

  public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo) {
    this.myPieces = new HashMap<Coordinate, Boolean>();
    this.myDisplayInfo = myDisplayInfo;
    for (Coordinate c : where) {
      myPieces.put(c, false);
    }
  }

  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    return (myPieces.get(where) != null);
  }

  @Override
  public boolean isSunk() {
    for (Map.Entry<Coordinate, Boolean> set : myPieces.entrySet()) {
      if (!set.getValue()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void recordHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    myPieces.put(where, true);
  }

  @Override
  public boolean wasHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    return myPieces.get(where);
  }

  @Override
  public T getDisplayInfoAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    return myDisplayInfo.getInfo(where, wasHitAt(where));
  }

  protected void checkCoordinateInThisShip(Coordinate c) {
    if (!occupiesCoordinates(c)) {
      throw new IllegalArgumentException("Coordinate is not in the ship!");
    }
  }

}
