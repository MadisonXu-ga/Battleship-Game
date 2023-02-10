package edu.duke.rx50.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Function;

public class TextPlayer {
  final Board<Character> theBoard;
  final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out;
  final AbstractShipFactory<Character> shipFactory;
  final String name;

  final ArrayList<String> shipsToPlace;
  final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;

  private int moveShipChances;
  private int sonarScanChances;

  public TextPlayer(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out,
      AbstractShipFactory<Character> shipFactory) {
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.inputReader = inputSource;
    this.out = out;
    this.shipFactory = shipFactory;
    this.name = name;

    this.shipsToPlace = new ArrayList<>();
    this.shipCreationFns = new HashMap<>();
    setupShipCreationMap();
    setupShipCreationList();

    this.moveShipChances = 3;
    this.sonarScanChances = 3;
  }

  protected void setupShipCreationMap() {
    shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
    shipCreationFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));
    shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));
    shipCreationFns.put("Destroyer", (p) -> shipFactory.makeDestroyer(p));
  }

  protected void setupShipCreationList() {
    shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
    shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
    shipsToPlace.addAll(Collections.nCopies(3, "Battleship"));
    shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));
  }

  /**
   * read placement and deal with it
   */
  public Placement readPlacement(String prompt) throws IOException, IllegalArgumentException {
    out.println(prompt);
    String s = inputReader.readLine();
    if (s == null) {
      throw new IOException();
    }
    return new Placement(s);
  }

  public Coordinate readCoordinate(String prompt) throws IOException, IllegalArgumentException {
    out.println(prompt);
    String s = inputReader.readLine();
    if (s == null) {
      throw new IOException();
    }
    return new Coordinate(s);
  }

  public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
    try {
      Placement p = readPlacement("Player " + name + " where do you want to place a " + shipName + "?");
      Ship<Character> s = createFn.apply(p);
      String error = theBoard.tryAddShip(s);
      if (error != null) {
        throw new IllegalArgumentException(error);
      }
      out.println(view.displayMyOwnBoard());
    } catch (IllegalArgumentException ex) {
      out.println(ex.getMessage());
      out.println("Please place again!");
      doOnePlacement(shipName, createFn);
    }
  }

  public void doPlacementPhase() throws IOException {
    out.println(view.displayMyOwnBoard());
    String message = "Player " + name
        + ": you are going to place the following ships (which are all rectangular). For each ship, type the coordinate of the upper left side of the ship, followed by either H (for horizontal) or V (for vertical).  For example M4H would place a ship horizontally starting at M4 and going to the right.  You have\n\n"
        + "2 \"Submarines\" ships that are 1x2\n" + "3 \"Destroyers\" that are 1x3\n"
        + "3 \"Battleships\" that are 1x4\n" + "2 \"Carriers\" that are 1x6";
    out.println(message);
    for (String ship : shipsToPlace) {
      doOnePlacement(ship, shipCreationFns.get(ship));
    }
  }

  public boolean checkLost() {
    return !theBoard.checkShipRemain();
  }

  /*
   * option 1 Fire at a ship
   */
  public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView, String enemyName) throws IOException {
    // view.displayMyBoardWithEnemyNextToIt(enemyView, enemyName, enemyName)
    String prompt = name + " Please choose a coordinate to attack!";
    String myHeader = "Your ocean";
    String enemyHeader = "Player " + enemyName + "'s ocean";
    try {
      out.println(view.displayMyBoardWithEnemyNextToIt(enemyView, myHeader, enemyHeader));
      Coordinate c = readCoordinate(prompt);
      Ship<Character> ship = enemyBoard.fireAt(c);
      if (ship == null) {
        out.println("You missed!");
      } else {
        out.println("You hit a " + ship.getName());
      }

    } catch (IllegalArgumentException ex) {
      out.println(ex.getMessage());
      out.println("Please attack again!");
      playOneTurn(enemyBoard, enemyView, enemyName);
    }
  }

  /*
   * option 2 Move ship to another place
   */
  // error handling???????
  public void moveShip() throws IOException {
    String prompt_select = "Which ship do you want to select?";
    String prompt_change = "Where do you want to place it?";
    try {
      Coordinate c_ship = readCoordinate(prompt_select);
      Placement c_target = readPlacement(prompt_change);
      String shipType = theBoard.findShipName(c_ship);
      Ship<Character> newShip = shipCreationFns.get(shipType).apply(c_target);
      theBoard.relocateShip(c_ship, c_target, newShip);
    } catch (IllegalArgumentException ex) {
      out.println(ex.getMessage());
      moveShip();
    }
  }

  /*
   * option 3 Sonar Scan Boats
   */
  public void sonarScan(Board<Character> enemyBoard) throws IOException {
    String prompt = "Choose a center coordinate to scan";
    try {
      Coordinate center = readCoordinate(prompt);

      HashSet<Coordinate> scanAreaCoords = new HashSet<>();

      // draw the area
      int k = 0;
      for (int r = -3; r <= 3; ++r) {
        for (int c = (-k); c <= k; ++c) {
          int scanR = center.getRow() + r;
          int scanC = center.getColumn() + c;
          if (scanR < 0 || scanR >= enemyBoard.getHeight() || scanC < 0 || scanC >= enemyBoard.getWidth()) {
            continue;
          }
          scanAreaCoords.add(new Coordinate(scanR, scanC));
        }
        if (r >= 0) {
          --k;
        } else {
          ++k;
        }
      }

      int submarineNum = 0, destroyerNum = 0, battleshipNum = 0, carrierNum = 0;
      for (Coordinate c : scanAreaCoords) {
        String shipName = enemyBoard.findShipName(c);
        if (shipName != null) {
          switch (shipName) {
            case "Submarine":
              submarineNum += 1;
              break;
            case "Destroyer":
              destroyerNum += 1;
              break;
            case "Battleship":
              battleshipNum += 1;
              break;
            case "Carrier":
              carrierNum += 1;
              break;
          }
        }
      }
      out.println("Submarines occupy " + submarineNum + " squares");
      out.println("Destroyers occupy " + destroyerNum + " squares");
      out.println("Battleships occupy " + battleshipNum + " squares");
      out.println("Carriers occupy " + carrierNum + " squares");

    } catch (IllegalArgumentException ex) {
      out.println(ex.getMessage());
      out.println("Please select a valid coordinate!");
      sonarScan(enemyBoard);
    }

  }

  /*
   * Provide 3 options for player
   */
  public void selectAction(Board<Character> enemyBoard, BoardTextView enemyView, String enemyName) throws IOException {
    out.println("Possible actions for Player " + name + ":");
    out.println("F Fire at a square");
    if (moveShipChances > 0) {
      out.println("M Move a ship to another square (" + moveShipChances + " remaining)");
    }
    if (sonarScanChances > 0) {
      out.println("S Sonar scan (" + sonarScanChances + " remaining)");
    }

    // abstract this as a function!!!
    try {
      String s = inputReader.readLine();
      // if (s == null) {
      //   throw new IOException();
      // }

      if (s.equalsIgnoreCase("F")) {
        playOneTurn(enemyBoard, enemyView, enemyName);
      } else if (s.equalsIgnoreCase("M") && moveShipChances > 0) {
        moveShip();
        --moveShipChances;
      } else if (s.equalsIgnoreCase("S") && sonarScanChances > 0) {
        sonarScan(enemyBoard);
        --sonarScanChances;
      } else {
        throw new IllegalArgumentException("Choice is invalid!");
      }
    } catch (IllegalArgumentException ex) {
      out.println(ex.getMessage());
      out.println("Please select a valid choice!");
      selectAction(enemyBoard, enemyView, enemyName);
    }
  }
}
