package edu.duke.rx50.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  private void emptyBoardHelper(int w, int h, String expectedHeader, String expectedBody) {
    Board<Character> b1 = new BattleShipBoard<Character>(w, h, 'X');
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_display_empty_2by2() {
    String expectedHeader = "  0|1\n";
    String expectedBody = "A  |  A\n" +
        "B  |  B\n";
    emptyBoardHelper(2, 2, expectedHeader, expectedBody);
  }

  @Test
  public void test_invalid_board_size() {
    Board<Character> wideBoard = new BattleShipBoard<Character>(11, 20, 'X');
    Board<Character> tallBoard = new BattleShipBoard<Character>(10, 27, 'X');
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
  }

  @Test
  public void test_display_empty_3by2() {
    String expectedHeader = "  0|1|2\n";
    String expectedBody = "A  | |  A\n" +
        "B  | |  B\n";
    emptyBoardHelper(3, 2, expectedHeader, expectedBody);
  }

  @Test
  public void test_display_empty_3by5() {
    String expectedHeader = "  0|1|2\n";
    String expectedBody = "A  | |  A\n" +
        "B  | |  B\n" +
        "C  | |  C\n" +
        "D  | |  D\n" +
        "E  | |  E\n";
    emptyBoardHelper(3, 5, expectedHeader, expectedBody);
  }

  @Test
  public void test_display_nonempty_4by3() {
    String expectedHeader = "  0|1|2|3\n";
    String expectedBody = "A  | | |  A\n" +
        "B  | |s|  B\n" +
        "C  | | |  C\n";

    Board<Character> b1 = new BattleShipBoard<Character>(4, 3, 'X');
    b1.tryAddShip(new RectangleShip<Character>(new Coordinate(1, 2), 's', '*'));

    BoardTextView view = new BoardTextView(b1);

    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_displayEnemyBoard(){
    String myView =
      "  0|1|2|3\n" +
      "A  | | |d A\n" +
      "B s|s| |d B\n" +
      "C  | | |d C\n" +
      "  0|1|2|3\n";
    Board<Character> b = new BattleShipBoard<Character>(4, 3, 'X');
    V1ShipFactory v = new V1ShipFactory();
    BoardTextView view = new BoardTextView(b);

    b.tryAddShip(v.makeSubmarine(new Placement("B0H")));
    assertNull(b.tryAddShip(v.makeDestroyer(new Placement("A3V"))));
    assertEquals(myView, view.displayMyOwnBoard());

    String enemyView_unhit =
      "  0|1|2|3\n" +
      "A  | | |  A\n" +
      "B  | | |  B\n" +
      "C  | | |  C\n" +
      "  0|1|2|3\n";

    assertEquals(enemyView_unhit, view.displayEnemyBoard());
    b.fireAt(new Coordinate("B0"));
    b.fireAt(new Coordinate("A0"));

    String enemyView_hit =
      "  0|1|2|3\n" +
      "A X| | |  A\n" +
      "B s| | |  B\n" +
      "C  | | |  C\n" +
      "  0|1|2|3\n";

    assertEquals(enemyView_hit, view.displayEnemyBoard());
  }

  @Test
  public void test_displayMyBoardWithEnemyNextToIt(){
    String expectedView =
      "     Your ocean" + "                " + "Player B's ocean\n" +
      "  0|1|2|3  " + "                " + "  0|1|2|3\n"+
      "A  | | |d A" + "                " + "A X| | |  A\n" +
      "B *|s| |d B" + "                " + "B s| | |  B\n" +
      "C  | | |d C" + "                " + "C  | | |  C\n" +
      "  0|1|2|3  " + "                " + "  0|1|2|3\n";

    String myHeader = "Your ocean";
    String enemyHeader = "Player B's ocean";

    Board<Character> b = new BattleShipBoard<Character>(4, 3, 'X');
    V1ShipFactory v = new V1ShipFactory();
    BoardTextView view = new BoardTextView(b);

    b.tryAddShip(v.makeSubmarine(new Placement("B0H")));
    b.tryAddShip(v.makeDestroyer(new Placement("A3V")));
    
    b.fireAt(new Coordinate("B0"));
    b.fireAt(new Coordinate("A0"));

    assertEquals(expectedView, view.displayMyBoardWithEnemyNextToIt(view, myHeader, enemyHeader));
  }
}
