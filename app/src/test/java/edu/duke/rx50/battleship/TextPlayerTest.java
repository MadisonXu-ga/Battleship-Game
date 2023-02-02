package edu.duke.rx50.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class TextPlayerTest {
  @Test
  void test_read_placement() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    String inputData = "B2V\nC8H\na4v\n";
    TextPlayer player = createTextPlayer("A", 10, 20, inputData, bytes);
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

    // error
    TextPlayer player_error = createTextPlayer("A", 10, 20, "", bytes);
    assertThrows(IOException.class,()->{ player_error.readPlacement(prompt);});
  }

  @Test
  void test_doOnePlacement() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    String inputData = "A0V";
    TextPlayer player = createTextPlayer("A", 4, 3, inputData, bytes);
    // String prompt = "Please enter a location for a ship:";
    Placement p = new Placement("A0V");
    bytes.reset();
    V1ShipFactory shipFactory = new V1ShipFactory();
    player.doOnePlacement("Destroyer", (ship) -> shipFactory.makeDestroyer(ship));

    String expectedHeader = "  0|1|2|3\n";
    String expectedBody = "A d| | |  A\n" +
        "B d| | |  B\n" +
        "C d| | |  C\n";
    String expected = "Player A where do you want to place a Destroyer?\n" + expectedHeader + expectedBody
        + expectedHeader
        + "\n";
    assertEquals(expected, bytes.toString());

    // error
    TextPlayer player_error = createTextPlayer("A", 4, 3, "A0H\nA0V\n", bytes);
    player_error.doOnePlacement("Destroyer", (ship) -> shipFactory.makeDestroyer(ship));
    assertThrows(IOException.class, () -> {
      player_error.doOnePlacement("Destroyer", (ship) -> shipFactory.makeDestroyer(ship));
    });
  }

  private TextPlayer createTextPlayer(String name, int w, int h, String inputData, OutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> b = new BattleShipBoard<Character>(w, h);
    TextPlayer player = new TextPlayer(name, b, input, output, new V1ShipFactory());

    return player;
  }

  @Test
  public void test_doPlacementPhase() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    InputStream input = getClass().getClassLoader().getResourceAsStream("input.txt");
    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20);
    Board<Character> b2 = new BattleShipBoard<Character>(10, 20);
    V1ShipFactory factory = new V1ShipFactory();
    TextPlayer player1 = new TextPlayer("A", b1, reader, output, factory);
    TextPlayer player2 = new TextPlayer("B", b2, reader, output, factory);

    player1.doPlacementPhase();
    player2.doPlacementPhase();

    InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("output.txt");

    String expected = new String(expectedStream.readAllBytes());
    assertEquals(expected, bytes.toString());
  }
}
