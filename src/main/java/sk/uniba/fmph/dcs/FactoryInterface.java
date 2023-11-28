package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public interface FactoryInterface {
    ArrayList<Tile> take(int idx);
    boolean isEmpty();
    void startNewRound();
    String state();
}
