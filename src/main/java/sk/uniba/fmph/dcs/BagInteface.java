package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public interface BagInteface {
    ArrayList<Tile> take(int count);
    String state();
}
