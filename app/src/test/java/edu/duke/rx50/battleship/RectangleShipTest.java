package edu.duke.rx50.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RectangleShipTest {
  @Test
  public void test_getName() {
    RectangleShip<Character> r = new RectangleShip<Character>("submarine", new Coordinate(1, 2), 1, 3, 's', '*');
    assertEquals(r.getName(), "submarine");
  }

  @Test
  public void test_makeCoords() {
    RectangleShip<Character> r = new RectangleShip<Character>("submarine", new Coordinate(1, 2), 1, 3, 's', '*');
    assertTrue(r.occupiesCoordinates(new Coordinate(1, 2)));
    assertTrue(r.occupiesCoordinates(new Coordinate(2, 2)));
    assertTrue(r.occupiesCoordinates(new Coordinate(3, 2)));

    assertFalse(r.occupiesCoordinates(new Coordinate(2, 1)));
    assertFalse(r.occupiesCoordinates(new Coordinate(2, 3)));
  }

  @Test
  public void test_recordHitAt() {
    RectangleShip<Character> r = new RectangleShip<Character>("submarine", new Coordinate(1, 2), 1, 3, 's', '*');
    r.recordHitAt(new Coordinate(2, 2));
    assertEquals(r.wasHitAt(new Coordinate(2, 2)), true);
    assertEquals(r.wasHitAt(new Coordinate(1, 2)), false);

    assertThrows(IllegalArgumentException.class, () -> r.wasHitAt(new Coordinate(1, 3)));
    assertThrows(IllegalArgumentException.class, () -> r.recordHitAt(new Coordinate(3, 3)));
  }

  @Test
  public void test_isSunk() {
    RectangleShip<Character> r = new RectangleShip<Character>("submarine", new Coordinate(1, 2), 1, 3, 's', '*');
    assertEquals(r.isSunk(), false);
    r.recordHitAt(new Coordinate(1, 2));
    r.recordHitAt(new Coordinate(2, 2));
    assertEquals(r.isSunk(), false);
    r.recordHitAt(new Coordinate(3, 2));
    assertEquals(r.isSunk(), true);
  }

  @Test
  public void test_getDisplayInfoAt() {
    RectangleShip<Character> r = new RectangleShip<Character>("submarine", new Coordinate(1, 2), 1, 3, 's', '*');
    assertEquals(r.getDisplayInfoAt(new Coordinate(1, 2)), 's');
    r.recordHitAt(new Coordinate(1, 2));
    assertEquals(r.getDisplayInfoAt(new Coordinate(1, 2)), '*');
    r.recordHitAt(new Coordinate(2, 2));
    assertEquals(r.getDisplayInfoAt(new Coordinate(2, 2)), '*');
    assertThrows(IllegalArgumentException.class, () -> r.getDisplayInfoAt(new Coordinate(3, 3)));
  }
}
