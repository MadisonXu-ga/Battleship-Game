package edu.duke.rx50.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SimpleShipDisplayInfoTest {
  @Test
  public void test_getInfo() {
    SimpleShipDisplayInfo<Character> s = new SimpleShipDisplayInfo<Character>('s', 'X');
    assertEquals(s.getInfo(new Coordinate(1, 1), true), 'X');
    assertEquals(s.getInfo(new Coordinate(1, 1), false), 's');
  }

}
