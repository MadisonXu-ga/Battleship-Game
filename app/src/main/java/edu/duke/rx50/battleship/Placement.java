package edu.duke.rx50.battleship;

public class Placement {
  private final Coordinate where;
  private final char orientation;

  public Coordinate getWhere() {
    return where;
  }

  public char getOrientation() {
    return orientation;
  }

  public Placement(Coordinate where, char orientation) {
    this.where = where;
    if (orientation >= 'a' && orientation <= 'z') {
      orientation = (char) (orientation - 'a' + 'A');
    } else if (orientation < 'A' || orientation > 'Z') {
      throw new IllegalArgumentException("Orientation is invalid!");
    }
    this.orientation = orientation;
  }

  public Placement(String descr) {
    if (descr.length() != 3) {
      throw new IllegalArgumentException("Input string length is incorrect!");
    }
    where = new Coordinate(descr.substring(0, 2));
    char temp = descr.charAt(2);
    if (temp >= 'a' && temp <= 'z') {
      temp = (char) (temp - 'a' + 'A');
    } else if (temp < 'A' || temp > 'Z') {
      throw new IllegalArgumentException("Orientation is invalid!");
    }
    this.orientation = temp;
  }

  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Placement p = (Placement) o;
      return where.equals(p.where) && orientation == p.orientation;
    }
    return false;
  }

  @Override
  public String toString() {
    return "(" + where.toString() + ", " + orientation + ")";
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}
