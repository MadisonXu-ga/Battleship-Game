package edu.duke.rx50.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlacementTest {
  @Test
  public void test_where_and_orientation() {
    Coordinate c = new Coordinate(10, 20);
    Placement p = new Placement(c, 'h');
    assertEquals(new Coordinate(10, 20), p.getWhere());
    assertEquals('H', p.getOrientation());

    assertThrows(IllegalArgumentException.class, () -> new Placement(new Coordinate(5, 8), '@'));
    assertThrows(IllegalArgumentException.class, () -> new Placement(new Coordinate(6, 3), '['));
  }

  @Test
  public void test_equals() {
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(1, 2);
    Coordinate c3 = new Coordinate(1, 3);
    // Coordinate c4 = new Coordinate(3, 2);
    Placement p1 = new Placement(c1, 'H');
    Placement p2 = new Placement(c2, 'h');
    Placement p3 = new Placement(c1, 'z');
    Placement p4 = new Placement(c3, 'H');

    assertEquals(p1, p2);
    assertEquals(p1, p1);
    assertNotEquals(p1, p3);
    assertNotEquals(p1, p4);
    assertNotEquals(p2, p3);
    assertNotEquals(p1, "((1, 2), h)");
  }

  @Test
  public void test_hashCOde() {
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(1, 2);
    Coordinate c3 = new Coordinate(1, 3);
    // Coordinate c4 = new Coordinate(3, 2);
    Placement p1 = new Placement(c1, 'H');
    Placement p2 = new Placement(c2, 'h');
    Placement p3 = new Placement(c1, 'z');
    Placement p4 = new Placement(c3, 'H');

    assertEquals(p1.hashCode(), p2.hashCode());
    assertNotEquals(p1.hashCode(), p3.hashCode());
    assertNotEquals(p1.hashCode(), p4.hashCode());
  }

  @Test
  public void test_string_constructor_valid_cases() {
    Placement p1 = new Placement("E5h");
    assertEquals(new Coordinate("E5"), p1.getWhere());
    assertEquals('H', p1.getOrientation());

    Placement p2 = new Placement("B6V");
    assertEquals(new Coordinate("B6"), p2.getWhere());
    assertEquals('V', p2.getOrientation());

    Placement p3 = new Placement("Z0H");
    assertEquals(new Coordinate("Z0"), p3.getWhere());
    assertEquals('H', p3.getOrientation());

    Placement p4 = new Placement("A9v");
    assertEquals(new Coordinate("A9"), p4.getWhere());
    assertEquals('V', p4.getOrientation());
  }

  @Test
  public void test_string_constructor_error_cases() {
    // coordinate strange
    assertThrows(IllegalArgumentException.class, () -> new Placement("00H"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("AAV"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("@0H"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A/V"));

    // orientation strange
    assertThrows(IllegalArgumentException.class, () -> new Placement("A0@"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("00["));

    // number
    assertThrows(IllegalArgumentException.class, () -> new Placement("A1"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A12H"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A1HH"));
  }

}
