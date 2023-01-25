package edu.duke.rx50.battleship;

public class BasicShip implements Ship<Character>{
  private final Coordinate myLocation;

  public BasicShip(Coordinate where){
    this.myLocation = where;
  }
  
  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    return where.equals(myLocation);
  }

  @Override
  public boolean isSunk() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void recordHitAt(Coordinate where) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean wasHitAt(Coordinate where) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Character getDisplayInfoAt(Coordinate where) {
    return 's';
  }

}
