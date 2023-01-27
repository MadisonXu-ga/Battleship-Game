package edu.duke.rx50.battleship;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class RectangleShipTest {
  @Test
  public void test_makeCoords() {
    RectangleShip<Character> r = new RectangleShip<Character>(new Coordinate(1, 2), 1, 3, 's', '*');
    // HashSet<Coordinate> h = r.makeCoords(new Coordinate(1, 2), 1, 3);
    assertTrue(r.occupiesCoordinates(new Coordinate(1, 2)));
    assertTrue(r.occupiesCoordinates(new Coordinate(2, 2)));
    assertTrue(r.occupiesCoordinates(new Coordinate(3, 2)));

    assertFalse(r.occupiesCoordinates(new Coordinate(2, 1)));
    assertFalse(r.occupiesCoordinates(new Coordinate(2, 3)));
  }
}
