package edu.duke.rx50.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TeeweeShipTest {
  @Test
  public void test_makeCoords() {
    /*
     *  b
     * bbb
     */
    TeeweeShip<Character> su = makeShip(3, 2, 'U');
    assertTrue(su.occupiesCoordinates(new Coordinate(0, 1)));
    assertTrue(su.occupiesCoordinates(new Coordinate(1, 0)));
    assertTrue(su.occupiesCoordinates(new Coordinate(1, 1)));
    assertTrue(su.occupiesCoordinates(new Coordinate(1, 2)));

    assertFalse(su.occupiesCoordinates(new Coordinate(0, 0)));
    assertFalse(su.occupiesCoordinates(new Coordinate(0, 2)));

    /*
     * b
     * bb
     * b
     */
    TeeweeShip<Character> sr = makeShip(2, 3, 'R');
    assertTrue(sr.occupiesCoordinates(new Coordinate(0, 0)));
    assertTrue(sr.occupiesCoordinates(new Coordinate(1, 0)));
    assertTrue(sr.occupiesCoordinates(new Coordinate(1, 1)));
    assertTrue(sr.occupiesCoordinates(new Coordinate(2, 0)));

    assertFalse(sr.occupiesCoordinates(new Coordinate(0, 1)));
    assertFalse(sr.occupiesCoordinates(new Coordinate(2, 1)));

    /*
     * bbb
     *  b
     */
    TeeweeShip<Character> sd = makeShip(3, 2, 'D');
    assertTrue(sd.occupiesCoordinates(new Coordinate(0, 0)));
    assertTrue(sd.occupiesCoordinates(new Coordinate(0, 1)));
    assertTrue(sd.occupiesCoordinates(new Coordinate(0, 2)));
    assertTrue(sd.occupiesCoordinates(new Coordinate(1, 1)));

    assertFalse(sd.occupiesCoordinates(new Coordinate(1, 0)));
    assertFalse(sd.occupiesCoordinates(new Coordinate(1, 2)));

    /*
     *  b
     * bb
     *  b
     */
    TeeweeShip<Character> sl = makeShip(2, 3, 'L');
    assertTrue(sl.occupiesCoordinates(new Coordinate(0, 1)));
    assertTrue(sl.occupiesCoordinates(new Coordinate(1, 0)));
    assertTrue(sl.occupiesCoordinates(new Coordinate(1, 1)));
    assertTrue(sl.occupiesCoordinates(new Coordinate(2, 1)));

    assertFalse(sl.occupiesCoordinates(new Coordinate(0, 0)));
    assertFalse(sl.occupiesCoordinates(new Coordinate(2, 0)));
  }

  TeeweeShip<Character> makeShip(int width, int height, char orientation) {
    return new TeeweeShip<>("battleship", new Coordinate(0, 0), width, height, orientation, 'b', '*');
  }

  @Test
  public void test_getName() {
    TeeweeShip<Character> r = makeShip(3, 2, 'U');
    assertEquals(r.getName(), "battleship");
  }
}
