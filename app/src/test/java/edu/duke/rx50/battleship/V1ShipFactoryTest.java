package edu.duke.rx50.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class V1ShipFactoryTest {
  @Test
  public void test_mySubmarine() {
    V1ShipFactory v = new V1ShipFactory();
    Ship<Character> s1 = v.makeSubmarine(new Placement("A0V"));
    checkShip(s1, "Submarine", 's', new Coordinate(0, 0), new Coordinate(1, 0));

    Ship<Character> s2 = v.makeSubmarine(new Placement("B2H"));
    checkShip(s2, "Submarine", 's', new Coordinate(1, 2), new Coordinate(1, 3));
    assertThrows(IllegalArgumentException.class, () -> v.makeSubmarine(new Placement("A0B")));
  }

  @Test
  public void test_myBattleShip() {
    V1ShipFactory v = new V1ShipFactory();
    Ship<Character> s1 = v.makeBattleship(new Placement("A0V"));
    checkShip(s1, "Battleship", 'b', new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(2, 0),
        new Coordinate(3, 0));

    Ship<Character> s2 = v.makeBattleship(new Placement("B2H"));
    checkShip(s2, "Battleship", 'b', new Coordinate(1, 2), new Coordinate(1, 3), new Coordinate(1, 4),
        new Coordinate(1, 5));
    assertThrows(IllegalArgumentException.class, () -> v.makeBattleship(new Placement("A0B")));
  }

  @Test
  public void test_myCarrier() {
    V1ShipFactory v = new V1ShipFactory();
    Ship<Character> s1 = v.makeCarrier(new Placement("A0V"));
    checkShip(s1, "Carrier", 'c', new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(2, 0),
        new Coordinate(3, 0), new Coordinate(4, 0), new Coordinate(5, 0));

    Ship<Character> s2 = v.makeCarrier(new Placement("B2H"));
    checkShip(s2, "Carrier", 'c', new Coordinate(1, 2), new Coordinate(1, 3), new Coordinate(1, 4),
        new Coordinate(1, 5), new Coordinate(1, 6), new Coordinate(1, 7));
    assertThrows(IllegalArgumentException.class, () -> v.makeCarrier(new Placement("A0B")));
  }

  @Test
  public void test_myDestroyer() {
    V1ShipFactory v = new V1ShipFactory();
    Ship<Character> s1 = v.makeDestroyer(new Placement("A0V"));
    checkShip(s1, "Destroyer", 'd', new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(2, 0));

    Ship<Character> s2 = v.makeDestroyer(new Placement("B2H"));
    checkShip(s2, "Destroyer", 'd', new Coordinate(1, 2), new Coordinate(1, 3), new Coordinate(1, 4));

    assertThrows(IllegalArgumentException.class, () -> v.makeDestroyer(new Placement("A0B")));
  }

  private void checkShip(Ship<Character> testShip, String expectedName, char expectedLetter,
      Coordinate... expectedLocs) {
    assertEquals(testShip.getName(), expectedName);
    for (Coordinate c : expectedLocs) {
      assertEquals(testShip.getDisplayInfoAt(c, true), expectedLetter);
      assertEquals(testShip.getDisplayInfoAt(c, false), null);
    }
  }
}
