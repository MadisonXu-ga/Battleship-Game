package edu.duke.rx50.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_width_and_height() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }

  @Test
  public void test_invalid_dimensions() {
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8, 20, 'X'));
  }

  @Test
  public void test_add_ships() {
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(10, 20, 'X');
    Character[][] expected = new Character[10][20];
    checkWhatIsAtBoard(b, expected);

    // add ship succeed
    Ship<Character> s = new RectangleShip<Character>(new Coordinate(5, 8), 's', '*', 'H');
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
    assertEquals(b.tryAddShip(sf3), "That placement is invalid: the ship goes off the right of the board.");
  }

  private <T> void checkWhatIsAtBoard(BattleShipBoard<T> b, T[][] expected) {
    for (int i = 0; i < b.getWidth(); ++i) {
      for (int j = 0; j < b.getHeight(); ++j) {
        assertEquals(b.whatIsAtForSelf(new Coordinate(i, j)), expected[i][j]);
      }
    }
  }

  @Test
  public void test_fireAt() {
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(5, 10, 'X');
    V1ShipFactory v = new V1ShipFactory();
    Ship<Character> s1 = v.makeSubmarine(new Placement("A0H"));
    Ship<Character> s2 = v.makeSubmarine(new Placement("C3V"));
    b.tryAddShip(s1);
    b.tryAddShip(s2);
    // fire s1
    assertSame(b.fireAt(new Coordinate(0, 1)), s1);
    assertFalse(s1.isSunk());
    assertEquals(b.whatIsAtForEnemy(new Coordinate(0, 1)), 's');
    assertSame(b.fireAt(new Coordinate(0, 0)), s1);
    assertTrue(s1.isSunk());
    // fire miss
    assertNull(b.fireAt(new Coordinate(0, 2)));
    assertEquals(b.whatIsAtForEnemy(new Coordinate(0, 2)), 'X');
    // ship but no hit
    assertEquals(b.whatIsAtForEnemy(new Coordinate("C3")), null);
  }

  @Test
  public void test_findDeltaRC(){
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(10, 20, 'X');
    V2ShipFactory v2 = new V2ShipFactory();
    Ship<Character> s1 = v2.makeBattleship(new Placement("A0U"));
    assertEquals(1, b.findDeltaR(s1));
    assertEquals(2, b.findDeltaC(s1));

    Ship<Character> s2 = v2.makeCarrier(new Placement("A0D"));
    assertEquals(4, b.findDeltaR(s2));
    assertEquals(1, b.findDeltaC(s2));
  }

  @Test
  public void test_getHitPieces(){
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(10, 20, 'X');
    V2ShipFactory v2 = new V2ShipFactory();
    Ship<Character> s1 = v2.makeBattleship(new Placement("A0U"));
    s1.recordHitAt(new Coordinate("B0"));
    s1.recordHitAt(new Coordinate("B2"));
    s1.recordHitAt(new Coordinate("A1"));

    HashSet<Coordinate> actual = new HashSet<>(b.getHitPieces(s1));
    HashSet<Coordinate> expected = new HashSet<>();
    expected.add(new Coordinate("B0"));
    expected.add(new Coordinate("B2"));
    expected.add(new Coordinate("A1"));


    assertEquals(expected, actual);
  }

  @Test
  public void test_relocateShip(){
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(10, 20, 'X');
    V2ShipFactory v2 = new V2ShipFactory();

    // test non regular ship
    Ship<Character> s1 = v2.makeBattleship(new Placement("A0U"));
    Ship<Character> s2 = v2.makeCarrier(new Placement("G0R"));

    Ship<Character> s3 = v2.makeBattleship(new Placement("S5D"));

    b.tryAddShip(s1);
    b.tryAddShip(s2);

    b.tryAddShip(s3);

    s1.recordHitAt(new Coordinate("B0"));
    s1.recordHitAt(new Coordinate("A1"));

    s3.recordHitAt(new Coordinate("S5"));

    Ship<Character> newShip = v2.makeBattleship(new Placement("B6L"));
    b.relocateShip(new Coordinate("A1"), new Placement("B6L"), newShip);

    Ship<Character> newShip3 = v2.makeBattleship(new Placement("P1D"));
    b.relocateShip(new Coordinate("S5"), new Placement("P1D"), newShip3);

    // check old ship remove
    assertEquals(null, b.findShip(new Coordinate("B0")));
    assertEquals(null, b.findShip(new Coordinate("B1")));
    assertEquals(null, b.findShip(new Coordinate("A1")));
    assertEquals(null, b.findShip(new Coordinate("B2")));

    assertEquals(null, b.findShip(new Coordinate("S5")));
    assertEquals(null, b.findShip(new Coordinate("S6")));
    assertEquals(null, b.findShip(new Coordinate("S7")));
    assertEquals(null, b.findShip(new Coordinate("T6")));

    // check new ship
    Ship<Character> ns = b.findShip(new Coordinate("B7"));
    assertTrue(ns.wasHitAt(new Coordinate("C6")));
    assertTrue(ns.wasHitAt(new Coordinate("D7")));
    assertFalse(ns.wasHitAt(new Coordinate("B7")));
    assertFalse(ns.wasHitAt(new Coordinate("C7")));

    Ship<Character> ns3 = b.findShip(new Coordinate("P1"));
    assertTrue(ns3.wasHitAt(new Coordinate("P1")));
    assertFalse(ns3.wasHitAt(new Coordinate("P2")));
    assertFalse(ns3.wasHitAt(new Coordinate("P3")));
    assertFalse(ns3.wasHitAt(new Coordinate("Q2")));
  }

  @Test
  public void test_relocateRecShip(){
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(10, 20, 'X');
    V2ShipFactory v2 = new V2ShipFactory();

    // test rectangular ship
    Ship<Character> s3 = v2.makeSubmarine(new Placement("A0H"));
    Ship<Character> s4 = v2.makeDestroyer(new Placement("G0V"));
    // do not rotate
    Ship<Character> s5 = v2.makeSubmarine(new Placement("E6H"));

    b.tryAddShip(s3);
    b.tryAddShip(s4);

    b.tryAddShip(s5);

    s3.recordHitAt(new Coordinate("A0"));
    s4.recordHitAt(new Coordinate("I0"));
    s5.recordHitAt(new Coordinate("E7"));

    Ship<Character> newShip3 = v2.makeSubmarine(new Placement("B6V"));
    Ship<Character> newShip4 = v2.makeDestroyer(new Placement("F2H"));

    Ship<Character> newShip5 = v2.makeSubmarine(new Placement("R5H"));

    b.relocateShip(new Coordinate("A1"), new Placement("B6V"), newShip3);
    b.relocateShip(new Coordinate("H0"), new Placement("F2H"), newShip4);

    b.relocateShip(new Coordinate("E7"), new Placement("R5H"), newShip5);

    // check old ship remove
    assertEquals(null, b.findShip(new Coordinate("A0")));
    assertEquals(null, b.findShip(new Coordinate("A1")));

    assertEquals(null, b.findShip(new Coordinate("G0")));
    assertEquals(null, b.findShip(new Coordinate("H0")));
    assertEquals(null, b.findShip(new Coordinate("I0")));

    assertEquals(null, b.findShip(new Coordinate("E6")));
    assertEquals(null, b.findShip(new Coordinate("E7")));

    // check new ship
    Ship<Character> ns3 = b.findShip(new Coordinate("B6"));
    Ship<Character> ns4 = b.findShip(new Coordinate("F2"));

    Ship<Character> ns5 = b.findShip(new Coordinate("R5"));

    assertTrue(ns3.wasHitAt(new Coordinate("B6")));

    assertTrue(ns4.wasHitAt(new Coordinate("F4")));

    assertTrue(ns5.wasHitAt(new Coordinate("R6")));
  }

  @Test
  public void test_findShipName(){
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(10, 20, 'X');
    V2ShipFactory v2 = new V2ShipFactory();
    b.tryAddShip(v2.makeCarrier(new Placement(new Coordinate("A0"), 'U')));
    assertEquals("Carrier", b.findShipName(new Coordinate("A0")));
    assertEquals(null, b.findShipName(new Coordinate("H9")));
  }

}
