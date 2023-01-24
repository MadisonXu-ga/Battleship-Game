package edu.duke.rx50.battleship;

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
  private final Board toDisplay;

  /**
   * Constructs a BoardView, given the board it will display.
   * 
   * @param toDisplay is the Board to display
   * @throws IllegalArgumentException if the board is larger than 10x26.
   */
  public BoardTextView(Board toDisplay) {
    this.toDisplay = toDisplay;
    if (toDisplay.getWidth() > 10 || toDisplay.getHeight() > 20) {
      throw new IllegalArgumentException(
          "Board must be no larger than 10x26, but is " + toDisplay.getWidth() + "x" + toDisplay.getHeight());
    }
  }

  public String displayMyOwnBoard() {
    String header = makeHeader();
    String body = "";
    for (int i = 0; i < toDisplay.getHeight(); ++i) {
      String s = makeBody(i);
      body += s;
    }
    return header + body + header;
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

  String makeBody(int num) {
    StringBuilder ans = new StringBuilder("");
    String sep = " ";
    char c = (char) ('A' + num);
    ans.append(c);
    ans.append("  ");
    for (int i = 0; i < toDisplay.getWidth() - 1; ++i) {
      ans.append("|");
      ans.append(" ");
    }
    ans.append(" ");
    ans.append(c);
    ans.append("\n");
    return ans.toString();
  }
}
