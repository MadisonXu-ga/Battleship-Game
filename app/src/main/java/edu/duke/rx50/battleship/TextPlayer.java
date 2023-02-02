package edu.duke.rx50.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;

public class TextPlayer {
  final Board<Character> theBoard;
  final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out;
  final AbstractShipFactory<Character> shipFactory;
  final String name;

  public TextPlayer(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out,
      AbstractShipFactory<Character> shipFactory) {
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.inputReader = inputSource;
    this.out = out;
    this.shipFactory = shipFactory;
    this.name = name;
  }

  /**
   * read placement and deal with it
   */
  public Placement readPlacement(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    return new Placement(s);
  }

  public void doOnePlacement() throws IOException {
    // read a Placement (prompt: "Where would you like to put your ship?")
    Placement p = readPlacement("Player " + name + " where do you want to place a Destroyer?");
    // Create a destroyer based on the location in that Placement
    Ship<Character> b = shipFactory.makeDestroyer(p);
    // Add that ship to the board
    theBoard.tryAddShip(b);
    // Print out the board (to out, not to System.out)
    out.println(view.displayMyOwnBoard());
  }

  public void doPlacementPhase() throws IOException {
    out.println(view.displayMyOwnBoard());
    String message = "Player " + name
        + ": you are going to place the following ships (which are all rectangular). For each ship, type the coordinate of the upper left side of the ship, followed by either H (for horizontal) or V (for vertical).  For example M4H would place a ship horizontally starting at M4 and going to the right.  You have\n\n"
        + "2 \"Submarines\" ships that are 1x2\n" + "3 \"Destroyers\" that are 1x3\n"
        + "3 \"Battleships\" that are 1x4\n" + "2 \"Carriers\" that are 1x6";
    out.println(message);
    doOnePlacement();
  }

}
