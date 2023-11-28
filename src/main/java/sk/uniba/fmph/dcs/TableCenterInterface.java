package sk.uniba.fmph.dcs;

import java.util.ArrayList;
public interface TableCenterInterface {
    boolean isEmpty();
    ArrayList<Tile> take(int idx);
    void startNewRound();
    String state();
    void add(ArrayList<Tile> tiles);
}
