package edu.duke.rx50.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V2ShipFactoryTest {
  @Test
  public void test_mySubmarine() {
    V2ShipFactory v = new V2ShipFactory();

    Ship<Character> s1 = v.makeSubmarine(new Placement("A0V"));
    checkShip(s1, "Submarine", 's', new Coordinate(0, 0), new Coordinate(1, 0));

    Ship<Character> s2 = v.makeSubmarine(new Placement("B2H"));
    checkShip(s2, "Submarine", 's', new Coordinate(1, 2), new Coordinate(1, 3));
    assertThrows(IllegalArgumentException.class, () -> v.makeSubmarine(new Placement("A0L")));

  }

  @Test
  public void test_myDestroyer() {
    V2ShipFactory v = new V2ShipFactory();
    Ship<Character> s1 = v.makeDestroyer(new Placement("A0V"));
    checkShip(s1, "Destroyer", 'd', new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(2, 0));

    Ship<Character> s2 = v.makeDestroyer(new Placement("B2H"));
    checkShip(s2, "Destroyer", 'd', new Coordinate(1, 2), new Coordinate(1, 3), new Coordinate(1, 4));

    assertThrows(IllegalArgumentException.class, () -> v.makeDestroyer(new Placement("A0D")));
  }

  @Test
  public void test_myBattleShip() {
    V2ShipFactory v = new V2ShipFactory();

    Ship<Character> s1 = v.makeBattleship(new Placement("A0U"));
    checkShip(s1, "Battleship", 'b', new Coordinate("A1"), new Coordinate("B0"), new Coordinate("B1"),
        new Coordinate("B2"));

    Ship<Character> s2 = v.makeBattleship(new Placement("A0R"));
    checkShip(s2, "Battleship", 'b', new Coordinate("A0"), new Coordinate("B0"), new Coordinate("B1"),
        new Coordinate("C0"));

    Ship<Character> s3 = v.makeBattleship(new Placement("A0D"));
    checkShip(s3, "Battleship", 'b', new Coordinate("A0"), new Coordinate("A1"), new Coordinate("A2"),
        new Coordinate("B1"));

    Ship<Character> s4 = v.makeBattleship(new Placement("A0L"));
    checkShip(s4, "Battleship", 'b', new Coordinate("A1"), new Coordinate("B0"), new Coordinate("B1"),
        new Coordinate("C1"));

    assertThrows(IllegalArgumentException.class, () -> v.makeBattleship(new Placement("A0V")));
  }

  @Test
  public void test_myCarrier() {
    V2ShipFactory v = new V2ShipFactory();

    Ship<Character> s1 = v.makeCarrier(new Placement("A0U"));
    checkShip(s1, "Carrier", 'c', new Coordinate("A0"), new Coordinate("B0"), new Coordinate("C0"),
        new Coordinate("C1"), new Coordinate("D0"), new Coordinate("D1"), new Coordinate("E1"));

    Ship<Character> s2 = v.makeCarrier(new Placement("A0R"));
    checkShip(s2, "Carrier", 'c', new Coordinate("A1"), new Coordinate("A2"), new Coordinate("A3"),
        new Coordinate("A4"), new Coordinate("B0"), new Coordinate("B1"), new Coordinate("B2"));

    Ship<Character> s3 = v.makeCarrier(new Placement("A0D"));
    checkShip(s3, "Carrier", 'c', new Coordinate("A0"), new Coordinate("B0"), new Coordinate("B1"),
        new Coordinate("C0"), new Coordinate("C1"), new Coordinate("D1"), new Coordinate("E1"));

    Ship<Character> s4 = v.makeCarrier(new Placement("A0L"));
    checkShip(s4, "Carrier", 'c', new Coordinate("A2"), new Coordinate("A3"), new Coordinate("A4"),
        new Coordinate("B0"), new Coordinate("B1"), new Coordinate("B2"), new Coordinate("B3"));

    assertThrows(IllegalArgumentException.class, () -> v.makeCarrier(new Placement("A0H")));
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
