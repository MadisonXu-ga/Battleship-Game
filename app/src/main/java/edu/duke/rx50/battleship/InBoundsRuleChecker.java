package edu.duke.rx50.battleship;

public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {

  public InBoundsRuleChecker(PlacementRuleChecker<T> next){
    super(next);
  }

  @Override
  protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    Iterable<Coordinate> set = theShip.getCoordinates();
    for (Coordinate coord : set) {
      if (coord.getRow() < 0 || coord.getRow() >= theBoard.getHeight() || coord.getColumn() < 0
          || coord.getColumn() >= theBoard.getWidth()) {
        return false;
      }
    }
    return true;
  }
}
