package edu.duke.rx50.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InBoundsRuleCheckerTest {
  @Test
  public void test_checkMyRule() {
    V1ShipFactory v = new V1ShipFactory();
    Board<Character> b = new BattleShipBoard(8, 7, new InBoundsRuleChecker(null));
    PlacementRuleChecker<Character> checker = new InBoundsRuleChecker(null);
    // valid ship
    Ship<Character> s1 = v.makeSubmarine(new Placement("A0H"));
    Ship<Character> s2 = v.makeBattleship(new Placement("B0H"));
    assertTrue(checker.checkPlacement(s1, b));
    assertTrue(checker.checkPlacement(s2, b));

    // invalid ship
    Ship<Character> s3 = v.makeSubmarine(new Placement("A0H"));
    Ship<Character> s4 = v.makeSubmarine(new Placement("Z0H"));
    Ship<Character> s5 = v.makeSubmarine(new Placement("A0H"));
    Ship<Character> s6 = v.makeSubmarine(new Placement("A8H"));
    // assertFalse(checker.checkPlacement(s3, b));
    assertFalse(checker.checkPlacement(s4, b));
    // assertFalse(checker.checkPlacement(s5, b));
    assertFalse(checker.checkPlacement(s6, b));
  }

}
