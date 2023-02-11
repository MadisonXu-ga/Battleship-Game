package edu.duke.rx50.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class ComputerTextPlayerTest {
  @Test
  public void test_selectAction() throws IOException {
    String inputPlaceString1 = "A0H\nB0H\nC0H\nD0H\nE0H\nF0D\nG0R\nH1U\nJ0R\nL0L\n";
    String inputPlaceString2 = "A0V\nA1V\nA2V\nA3V\nA4V\nA5D\nB5R\nA7L\nD0U\nD2D\n";
    String inputAttackString = "A0\nA1\nA2\nA3\nA4\nA5\nA6\nA7\nA8\nA9\nB0\nB1\nB2\nB3\nB4\nB5\nB6\nB7\nB8\nB9\nC0\nC1\nC2\nC3\nC4\n"
        +
        "C5\nC6\nC7\nC8\nC9\nD0\nD1\nD2\nD3\nD4\nD5\nD6\nD7\nD8\nD9\nE0\nE1\nE2\nE3\nE4\nE5\nE6\nE7\nE8\nE9\nF0\nF1\nF2\nF3\nF4\n"
        +
        "F5\nF6\nF7\nF8\nF9\nG0\nG1\nG2\nG3\nG4\nG5\nG6\nG7\nG8\nG9\nH0\nH1\nH2\nH3\nH4\nH5\nH6\nH7\nH8\nH9\nI0\nI1\nI2\nI3\nI4\n"
        +
        "I5\nI6\nI7\nI8\nI9\nJ0\nJ1\nJ2\nJ3\nJ4\nJ5\nJ6\nJ7\nJ8\nJ9\nK0\nK1\nK2\nK3\nK4\nK5\nK6\nK7\nK8\nK9\nL0\nL1\nL2\nL3\nL4\n"
        +
        "L5\nL6\nL7\nL8\nL9\nM0\nM1\nM2\nM3\nM4\nM5\nM6\nM7\nM8\nM9\nN0\nN1\nN2\nN3\nN4\nN5\nN6\nN7\nN8\nN9\nO0\nO1\nO2\nO3\nO4\n"
        +
        "O5\nO6\nO7\nO8\nO9\nP0\nP1\nP2\nP3\nP4\nP5\nP6\nP7\nP8\nP9\nQ0\nQ1\nQ2\nQ3\nQ4\nQ5\nQ6\nQ7\nQ8\nQ9\nR0\nR1\nR2\nR3\nR4\n"
        +
        "R5\nR6\nR7\nR8\nR9\nS0\nS1\nS2\nS3\nS4\nS5\nS6\nS7\nS8\nS9\nT0\nT1\nT2\nT3\nT4\nT5\nT6\nT7\nT8\nT9\n";
    String inputDataComputer1 = inputPlaceString1 + inputAttackString;
    String inputDataComputer2 = inputPlaceString2 + inputAttackString;

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    Board<Character> b2 = new BattleShipBoard<Character>(10, 20, 'X');
    V2ShipFactory factory = new V2ShipFactory();
    TextPlayer player1 = new ComputerTextPlayer("A", b1, new BufferedReader(new StringReader(inputDataComputer1)),
        output, factory);
    TextPlayer player2 = new ComputerTextPlayer("B", b2, new BufferedReader(new StringReader(inputDataComputer2)),
        output, factory);

    output.println("Please choose the type of Player " + player1.name);
    output.println("1. Human");
    output.println("2. Computer");

    output.println("Please choose the type of Player " + player2.name);
    output.println("1. Human");
    output.println("2. Computer");

    player1.doPlacementPhase();
    player2.doPlacementPhase();

    while (true) {
      player1.selectAction(player2.theBoard, player2.view, player2.name);
      if (player2.checkLost()) {
        output.println(player1.name + " has won! " + player2.name + " has lost!");
        break;
      }
      player2.selectAction(player1.theBoard, player1.view, player1.name);
    }

    InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("output_cVSc.txt");

    String expected = new String(expectedStream.readAllBytes());
    assertEquals(expected, bytes.toString());
  }

}
