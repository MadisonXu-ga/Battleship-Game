package edu.duke.rx50.battleship;

import java.util.function.Function;

/**
 * This class handles textual display of
 * a Board (i.e., converting it to a string to show
 * to the user).
 * It supports two ways to display the Board:
 * one for the player's own board, and one for the
 * enemy's board.
 */

public class BoardTextView {
  /**
   * The Board to display
   */
  private final Board<Character> toDisplay;

  /**
   * Constructs a BoardView, given the board it will display.
   * 
   * @param toDisplay is the Board to display
   * @throws IllegalArgumentException if the board is larger than 10x26.
   */
  public BoardTextView(Board<Character> toDisplay) {
    this.toDisplay = toDisplay;
    if (toDisplay.getWidth() > 10 || toDisplay.getHeight() > 20) {
      throw new IllegalArgumentException(
          "Board must be no larger than 10x26, but is " + toDisplay.getWidth() + "x" + toDisplay.getHeight());
    }
  }

  public String displayAnyBoard(Function<Coordinate, Character> getSquareFn) {
    String header = makeHeader();
    String body = "";
    for (int row = 0; row < toDisplay.getHeight(); ++row) {
      String s = makeBody(row, getSquareFn);
      body += s;
    }
    return header + body + header;
  }

  public String displayMyOwnBoard() {
    return displayAnyBoard((c) -> toDisplay.whatIsAtForSelf(c));
  }

  public String displayEnemyBoard() {
    return displayAnyBoard((c) -> toDisplay.whatIsAtForEnemy(c));
  }

  public String displayMyBoardWithEnemyNextToIt(BoardTextView enemyView, String myHeader, String enemyHeader) {
    String[] myLines = this.displayMyOwnBoard().split("\n");
    String[] enemyLines = enemyView.displayEnemyBoard().split("\n");

    StringBuilder ans = new StringBuilder("");
    int spaceLength = 16; // ??????
    int leftHeader = 5;

    for (int i = 0; i < myLines.length; ++i) {
      if (i == 0) {
        ans.append(" ".repeat(leftHeader));
        ans.append(myHeader);
        ans.append(" ".repeat(spaceLength));
        ans.append(enemyHeader);
        ans.append("\n");
      }
      ans.append(myLines[i]);
      if (i == 0 || i == myLines.length - 1) {
        ans.append("  ");
      }
      ans.append(" ".repeat(spaceLength));
      ans.append(enemyLines[i]);
      ans.append("\n");
    }

    return ans.toString();
  }

  String makeHeader() {
    StringBuilder ans = new StringBuilder("  ");
    String sep = "";
    for (int i = 0; i < toDisplay.getWidth(); ++i) {
      ans.append(sep);
      ans.append(i);
      sep = "|";
    }
    ans.append("\n");
    return ans.toString();
  }

  String makeBody(int row, Function<Coordinate, Character> getSquareFn) {
    StringBuilder ans = new StringBuilder("");
    String sep = " ";
    char c = (char) ('A' + row);
    ans.append(c);
    ans.append(" ");
    for (int column = 0; column < toDisplay.getWidth(); ++column) {
      if (getSquareFn.apply(new Coordinate(row, column)) == null) {
        ans.append(" ");
      } else {
        ans.append(getSquareFn.apply(new Coordinate(row, column)));
      }
      if (column != toDisplay.getWidth() - 1) {
        ans.append("|");
      }
    }
    ans.append(" ");
    ans.append(c);
    ans.append("\n");
    return ans.toString();
  }
}
