package edu.duke.rx50.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class TextPlayerTest {
  @Test
  void test_read_placement() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    String inputData = "B2V\nC8H\na4v\n";
    TextPlayer player = createTextPlayer(10, 20, inputData, bytes);
    String prompt = "Please enter a location for a ship:";
    Placement[] expected = new Placement[3];
    expected[0] = new Placement(new Coordinate(1, 2), 'V');
    expected[1] = new Placement(new Coordinate(2, 8), 'H');
    expected[2] = new Placement(new Coordinate(0, 4), 'V');

    for (int i = 0; i < expected.length; i++) {
      Placement p = player.readPlacement(prompt);
      assertEquals(p, expected[i]); // did we get the right Placement back
      assertEquals(prompt + "\n", bytes.toString()); // should have printed prompt and newline
      bytes.reset(); // clear out bytes for next time around
    }
  }

  @Test
  void test_doOnePlacement() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    String inputData = "A0V";
    TextPlayer player = createTextPlayer(4, 3, inputData, bytes);
    // String prompt = "Please enter a location for a ship:";
    Placement p = new Placement("A0V");
    bytes.reset();
    player.doOnePlacement();

    String expectedHeader = "  0|1|2|3\n";
    String expectedBody = "A d| | |  A\n" +
        "B d| | |  B\n" +
        "C d| | |  C\n";
    String expected = "Player A where do you want to place a Destroyer?\n" + expectedHeader + expectedBody
        + expectedHeader
        + "\n";
    assertEquals(expected, bytes.toString());
  }

  private TextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> b = new BattleShipBoard<Character>(w, h);
    TextPlayer player = new TextPlayer("A", b, input, output, new V1ShipFactory());

    return player;
  }

  @Test
  public void test_doPlacementPhase() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    String inputData = "A0V";
    TextPlayer player = createTextPlayer(4, 3, inputData, bytes);
    Placement p = new Placement("A0V");
    bytes.reset();
    String message = "Player A: you are going to place the following ships (which are all rectangular). For each ship, type the coordinate of the upper left side of the ship, followed by either H (for horizontal) or V (for vertical).  For example M4H would place a ship horizontally starting at M4 and going to the right.  You have\n\n"
        + "2 \"Submarines\" ships that are 1x2\n" + "3 \"Destroyers\" that are 1x3\n"
        + "3 \"Battleships\" that are 1x4\n" + "2 \"Carriers\" that are 1x6\n";
    player.doPlacementPhase();

    String expectedHeader = "  0|1|2|3\n";
    String expectedBody1 = "A  | | |  A\n" +
        "B  | | |  B\n" +

        "C  | | |  C\n";
    String expectedBody2 = "A d| | |  A\n" +
        "B d| | |  B\n" +

        "C d| | |  C\n";
    String expected = expectedHeader + expectedBody1 + expectedHeader + "\n" + message
        + "Player A where do you want to place a Destroyer?\n" + expectedHeader + expectedBody2
        + expectedHeader
        + "\n";
    assertEquals(expected, bytes.toString());
  }
}
