package edu.duke.rx50.battleship;

public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {

  public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

  @Override
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    Iterable<Coordinate> set = theShip.getCoordinates();
    for (Coordinate coord : set) {
      if (coord.getRow() < 0) {
        return "That placement is invalid: the ship goes off the top of the board.";
      }
      if (coord.getRow() >= theBoard.getHeight()) {
        return "That placement is invalid: the ship goes off the bottom of the board.";
      }
      if (coord.getColumn() < 0) {
        return "That placement is invalid: the ship goes off the left of the board.";
      }
      if (coord.getColumn() >= theBoard.getWidth()) {
        return "That placement is invalid: the ship goes off the right of the board.";
      }
    }

    return null;
  }
}
