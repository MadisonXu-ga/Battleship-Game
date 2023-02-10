package edu.duke.rx50.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class NoCollisionRuleCheckerTest {
  @Test
  public void test_checkMyRule() {
    V1ShipFactory v = new V1ShipFactory();
    Board<Character> b = new BattleShipBoard<Character>(10, 10, 'X', new NoCollisionRuleChecker<Character>(null));
    PlacementRuleChecker<Character> checker = new NoCollisionRuleChecker<Character>(null);

    Ship<Character> s1 = v.makeSubmarine(new Placement("A0H"));
    assertEquals(checker.checkMyRule(s1, b), null);
    assertEquals(b.tryAddShip(s1), null);

    Ship<Character> s2 = v.makeSubmarine(new Placement("A0H"));
    assertEquals("That placement is invalid: the ship overlaps another ship.", checker.checkMyRule(s2, b));

    Ship<Character> s3 = v.makeSubmarine(new Placement("A1H"));
    assertEquals("That placement is invalid: the ship overlaps another ship.", checker.checkMyRule(s3, b));
  }

  @Test
  public void test_combine_collision_bounds() {
    V1ShipFactory v = new V1ShipFactory();
    PlacementRuleChecker<Character> checker = new NoCollisionRuleChecker<Character>(
        new InBoundsRuleChecker<Character>(null));
    Board<Character> b = new BattleShipBoard<Character>(8, 10, 'X', checker);

    Ship<Character> b1 = v.makeBattleship(new Placement("A0H"));
    assertEquals(checker.checkPlacement(b1, b), null);
    assertEquals(b.tryAddShip(b1), null);

    Ship<Character> s1 = v.makeSubmarine(new Placement("A3H"));
    assertEquals("That placement is invalid: the ship overlaps another ship.", checker.checkPlacement(s1, b));

    Ship<Character> s2 = v.makeSubmarine(new Placement("B9H"));
    assertEquals("That placement is invalid: the ship goes off the right of the board.", checker.checkPlacement(s2, b));
  }

}
