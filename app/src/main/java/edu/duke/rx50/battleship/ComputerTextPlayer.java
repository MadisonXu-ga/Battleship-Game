package edu.duke.rx50.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import java.util.function.Function;

public class ComputerTextPlayer extends TextPlayer{

    public ComputerTextPlayer(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out,
            AbstractShipFactory<Character> shipFactory) {
        super(name, theBoard, inputSource, out, shipFactory);
    }

    @Override
    public Coordinate readCoordinate(String prompt) throws IOException, IllegalArgumentException {
        String s = inputReader.readLine();
        return new Coordinate(s);
    }

    @Override
    public Placement readPlacement(String prompt) throws IOException, IllegalArgumentException {
        String s = inputReader.readLine();
        return new Placement(s);
    }

    @Override
    public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
        Placement p = readPlacement(null);
        Ship<Character> s = createFn.apply(p);
        theBoard.tryAddShip(s);
    }

    @Override
    public void doPlacementPhase() throws IOException {
        for (String ship : shipsToPlace) {
            doOnePlacement(ship, shipCreationFns.get(ship));
        }
        out.println("Player " + name + " finished placing ships.");
    }

    @Override
    public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView, String enemyName) throws IOException {
        Coordinate c = readCoordinate(null);

        Ship<Character> ship = enemyBoard.fireAt(c);
        if (ship == null) {
            out.println("Player " + name + " missed!");
        } else {
            out.println("Player " + name + " hit your " + ship.getName());
        }
    }

    @Override
    public void selectAction(Board<Character> enemyBoard, BoardTextView enemyView, String enemyName) throws IOException{
        playOneTurn(enemyBoard, enemyView, enemyName);
    }

}
