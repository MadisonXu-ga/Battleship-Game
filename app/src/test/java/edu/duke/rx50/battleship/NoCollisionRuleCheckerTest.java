package edu.duke.rx50.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class NoCollisionRuleCheckerTest {
  @Test
  public void test_checkMyRule() {
    V1ShipFactory v = new V1ShipFactory();
    Board<Character> b = new BattleShipBoard<Character>(10, 10, new NoCollisionRuleChecker<Character>(null));
    PlacementRuleChecker<Character> checker = new NoCollisionRuleChecker<Character>(null);

    Ship<Character> s1 = v.makeSubmarine(new Placement("A0H"));
    assertTrue(checker.checkMyRule(s1, b));
    assertTrue(b.tryAddShip(s1));
    
    Ship<Character> s2 = v.makeSubmarine(new Placement("A0H"));
    assertFalse(checker.checkMyRule(s2, b));

    Ship<Character> s3 = v.makeSubmarine(new Placement("A1H"));
    assertFalse(checker.checkMyRule(s3, b));
  }

  @Test
  public void test_combine_collision_bounds(){
    V1ShipFactory v = new V1ShipFactory();
    PlacementRuleChecker<Character> checker = new NoCollisionRuleChecker<Character>(new InBoundsRuleChecker<Character>(null));
    Board<Character> b = new BattleShipBoard<Character>(8, 10, checker);

    Ship<Character> b1 = v.makeBattleship(new Placement("A0H"));
    assertTrue(checker.checkPlacement(b1, b));
    assertTrue(b.tryAddShip(b1));

    Ship<Character> s1 = v.makeSubmarine(new Placement("A3H"));
    assertFalse(checker.checkPlacement(s1, b));

    Ship<Character> s2 = v.makeSubmarine(new Placement("B9H"));
    assertFalse(checker.checkPlacement(s2, b));
  }

}
