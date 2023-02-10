package edu.duke.rx50.battleship;

public interface Board<T> {
  public int getWidth();
  public int getHeight();
  public String tryAddShip(Ship<T> toAdd);
  public T whatIsAtForSelf(Coordinate where);
  public T whatIsAtForEnemy(Coordinate where);
  public Ship<T> fireAt(Coordinate c);
  public boolean checkShipRemain();
  public String findShipName(Coordinate c);
  public String relocateShip(Coordinate origin, Placement target, Ship<T> newShip);
}
