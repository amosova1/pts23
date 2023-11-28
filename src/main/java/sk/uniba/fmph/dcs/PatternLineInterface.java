package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public interface PatternLineInterface {
    void put(ArrayList<Tile> tiles);
    Points finishRound();
    String state();
}
