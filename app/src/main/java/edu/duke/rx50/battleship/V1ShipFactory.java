package edu.duke.rx50.battleship;

public class V1ShipFactory implements AbstractShipFactory<Character> {
  private final char onHit = '*';

  @Override
  public Ship<Character> makeSubmarine(Placement where) {
    return createShip(where, 1, 2, 's', "Submarine");
  }

  @Override
  public Ship<Character> makeBattleship(Placement where) {
    return createShip(where, 1, 4, 'b', "Battleship");
  }

  @Override
  public Ship<Character> makeCarrier(Placement where) {
    return createShip(where, 1, 6, 'c', "Carrier");
  }

  @Override
  public Ship<Character> makeDestroyer(Placement where) {
    return createShip(where, 1, 3, 'd', "Destroyer");
  }

  protected Ship<Character> createShip(Placement where, int w, int h, char letter, String name) {
    // orientation is invalid
    if (where.getOrientation() != 'H' && where.getOrientation() != 'V') {
      throw new IllegalArgumentException("Orientation is invalid!");
    }
    // horizontal
    if (where.getOrientation() == 'H') {
      return new RectangleShip<Character>(name, where.getWhere(), h, w, letter, onHit);
    }
    // default vertical
    return new RectangleShip<Character>(name, where.getWhere(), w, h, letter, onHit);
  }

}
