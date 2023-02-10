package edu.duke.rx50.battleship;

import java.util.ArrayList;
import java.util.Arrays;

public class V2ShipFactory implements AbstractShipFactory<Character> {

    private final char onHit = '*';

    @Override
    public Ship<Character> makeSubmarine(Placement where) {
        return createSDShip(where, 1, 2, 's', "Submarine");
    }

    @Override
    public Ship<Character> makeBattleship(Placement where) {
        return creatBCShip(where, 3, 2, 'b', "Battleship");
    }

    @Override
    public Ship<Character> makeCarrier(Placement where) {
        return creatBCShip(where, 2, 5, 'c', "Carrier");
    }

    @Override
    public Ship<Character> makeDestroyer(Placement where) {
        return createSDShip(where, 1, 3, 'd', "Destroyer");
    }

    /*
     * Create Submarine and Destroyer
     */
    protected Ship<Character> createSDShip(Placement where, int w, int h, char letter, String name) {
        ArrayList<Character> orien = new ArrayList<>(Arrays.asList('H', 'V'));
        if (!orien.contains(where.getOrientation())) {
            throw new IllegalArgumentException("Orientation is invalid!");
        }
        if (where.getOrientation() == 'H') {
            return new RectangleShip<Character>(name, where.getWhere(), h, w, letter, onHit, where.getOrientation());
        }
        return new RectangleShip<Character>(name, where.getWhere(), w, h, letter, onHit, where.getOrientation());
    }

    /*
     * create Battleship and Carrier
     */
    protected Ship<Character> creatBCShip(Placement where, int w, int h, char letter, String name) {
        ArrayList<Character> orien = new ArrayList<>(Arrays.asList('U', 'R', 'D', 'L'));
        if (!orien.contains(where.getOrientation())) {
            throw new IllegalArgumentException("Orientation is invalid!");
        }
        if (where.getOrientation() == 'U' || where.getOrientation() == 'D') {
            if (letter == 'b') {
                return new TeeweeShip<Character>(name, where.getWhere(), w, h, where.getOrientation(), letter, onHit);
            }
            return new RhodeShip<Character>(name, where.getWhere(), w, h, where.getOrientation(), letter, onHit);
        } else {
            if (letter == 'b') {
                return new TeeweeShip<Character>(name, where.getWhere(), h, w, where.getOrientation(), letter, onHit);
            }
            return new RhodeShip<Character>(name, where.getWhere(), h, w, where.getOrientation(), letter, onHit);
        }
    }
}
