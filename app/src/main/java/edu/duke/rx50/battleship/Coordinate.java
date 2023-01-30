package edu.duke.rx50.battleship;

public class Coordinate {
  private final int row;
  private final int column;

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }

  public Coordinate(int row, int column) {
    this.row = row;
    this.column = column;
  }

  public Coordinate(String descr) {
    if (descr.length() != 2) {
      throw new IllegalArgumentException("Input string length is incorrect!");
    }
    if (descr.toUpperCase().charAt(0) < 'A' || descr.toUpperCase().charAt(0) > 'Z') {
      throw new IllegalArgumentException("Row is not legal!");
    }
    if (descr.charAt(1) < '0' || descr.charAt(1) > '9') {
      throw new IllegalArgumentException("Column is not legal!");
    }
    row = descr.toUpperCase().charAt(0) - 'A';
    column = descr.charAt(1) - '0';
  }

  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Coordinate c = (Coordinate) o;
      return row == c.row && column == c.column;
    }
    return false;
  }

  @Override
  public String toString() {
    return "(" + row + ", " + column + ")";
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}
