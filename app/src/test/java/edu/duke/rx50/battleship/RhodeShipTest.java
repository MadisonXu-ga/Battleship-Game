package edu.duke.rx50.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class RhodeShipTest {
  @Test
  public void test_makeCoords() {
    /*
     * c
     * c
     * cc
     * cc
     *  c
     */
    RhodeShip<Character> su = makeShip(2, 5, 'U');
    assertTrue(su.occupiesCoordinates(new Coordinate(0, 0)));
    assertTrue(su.occupiesCoordinates(new Coordinate(1, 0)));
    assertTrue(su.occupiesCoordinates(new Coordinate(2, 0)));
    assertTrue(su.occupiesCoordinates(new Coordinate(2, 1)));
    assertTrue(su.occupiesCoordinates(new Coordinate(3, 0)));
    assertTrue(su.occupiesCoordinates(new Coordinate(3, 1)));
    assertTrue(su.occupiesCoordinates(new Coordinate(4, 1)));

    assertFalse(su.occupiesCoordinates(new Coordinate(4, 0)));
    assertFalse(su.occupiesCoordinates(new Coordinate(0, 1)));
    assertFalse(su.occupiesCoordinates(new Coordinate(1, 1)));

    /*
     *  cccc
     * ccc
     */
    RhodeShip<Character> sr = makeShip(5, 2, 'R');
    assertTrue(sr.occupiesCoordinates(new Coordinate(0, 1)));
    assertTrue(sr.occupiesCoordinates(new Coordinate(0, 2)));
    assertTrue(sr.occupiesCoordinates(new Coordinate(0, 3)));
    assertTrue(sr.occupiesCoordinates(new Coordinate(0, 4)));
    assertTrue(sr.occupiesCoordinates(new Coordinate(1, 0)));
    assertTrue(sr.occupiesCoordinates(new Coordinate(1, 1)));
    assertTrue(sr.occupiesCoordinates(new Coordinate(1, 2)));

    assertFalse(sr.occupiesCoordinates(new Coordinate(0, 0)));
    assertFalse(sr.occupiesCoordinates(new Coordinate(1, 3)));
    assertFalse(sr.occupiesCoordinates(new Coordinate(1, 4)));

    /*
     * c
     * cc
     * cc
     *  c
     *  c
     */
    RhodeShip<Character> sd = makeShip(2, 5, 'D');
    assertTrue(sd.occupiesCoordinates(new Coordinate(0, 0)));
    assertTrue(sd.occupiesCoordinates(new Coordinate(1, 0)));
    assertTrue(sd.occupiesCoordinates(new Coordinate(2, 0)));
    assertTrue(sd.occupiesCoordinates(new Coordinate(1, 1)));
    assertTrue(sd.occupiesCoordinates(new Coordinate(2, 1)));
    assertTrue(sd.occupiesCoordinates(new Coordinate(3, 1)));
    assertTrue(sd.occupiesCoordinates(new Coordinate(4, 1)));

    assertFalse(sd.occupiesCoordinates(new Coordinate(3, 0)));
    assertFalse(sd.occupiesCoordinates(new Coordinate(4, 0)));
    assertFalse(sd.occupiesCoordinates(new Coordinate(0, 1)));

    /*
     *   ccc
     * cccc
     */
    RhodeShip<Character> sl = makeShip(5, 2, 'L');
    assertTrue(sl.occupiesCoordinates(new Coordinate(0, 2)));
    assertTrue(sl.occupiesCoordinates(new Coordinate(0, 3)));
    assertTrue(sl.occupiesCoordinates(new Coordinate(0, 4)));
    assertTrue(sl.occupiesCoordinates(new Coordinate(1, 0)));
    assertTrue(sl.occupiesCoordinates(new Coordinate(1, 1)));
    assertTrue(sl.occupiesCoordinates(new Coordinate(1, 2)));
    assertTrue(sl.occupiesCoordinates(new Coordinate(1, 3)));

    assertFalse(sl.occupiesCoordinates(new Coordinate(0, 0)));
    assertFalse(sl.occupiesCoordinates(new Coordinate(0, 1)));
    assertFalse(sl.occupiesCoordinates(new Coordinate(1, 4)));
  }

  RhodeShip<Character> makeShip(int width, int height, char orientation) {
    return new RhodeShip<>("carrier", new Coordinate(0, 0), width, height, orientation, 'c', '*');
  }

  @Test
  public void test_getName() {
    RhodeShip<Character> r = makeShip(2, 5, 'U');
    assertEquals("carrier", r.getName());
  }

  @Test
  public void test_getOrientation(){
    RhodeShip<Character> r = makeShip(2, 5, 'U');
    assertEquals('U', r.getOrientation());
  }

  @Test
  public void test_getUpperLeft(){
    RhodeShip<Character> r = makeShip(2, 5, 'U');
    assertEquals(new Coordinate(0,0), r.getUpperLeft());
  }

}
