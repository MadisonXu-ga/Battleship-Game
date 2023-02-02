package edu.duke.rx50.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_width_and_height() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20);
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }

  @Test
  public void test_invalid_dimensions() {
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8, 20));
  }

  @Test
  public void test_add_ships() {
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(10, 20);
    Character[][] expected = new Character[10][20];
    checkWhatIsAtBoard(b, expected);

    // add ship succeed
    Ship<Character> s = new RectangleShip<Character>(new Coordinate(5, 8), 's', '*');
    assertEquals(b.tryAddShip(s), null);
    expected[5][8] = 's';
    checkWhatIsAtBoard(b, expected);

    // add ship failed
    V1ShipFactory v = new V1ShipFactory();
    // overlap
    Ship<Character> sf = v.makeSubmarine(new Placement("F8H"));
    assertEquals(b.tryAddShip(sf), "That placement is invalid: the ship overlaps another ship.");
    // out of bound
    Ship<Character> sf2 = v.makeSubmarine(new Placement("Z8H"));
    assertEquals(b.tryAddShip(sf2), "That placement is invalid: the ship goes off the bottom of the board.");

    Ship<Character> sf3 = v.makeBattleship(new Placement("A9H"));
    assertEquals(b.tryAddShip(sf3),"That placement is invalid: the ship goes off the right of the board.");
  }

  private <T> void checkWhatIsAtBoard(BattleShipBoard<T> b, T[][] expected) {
    for (int i = 0; i < b.getWidth(); ++i) {
      for (int j = 0; j < b.getHeight(); ++j) {
        assertEquals(b.whatIsAt(new Coordinate(i, j)), expected[i][j]);
      }
    }
  }

}
