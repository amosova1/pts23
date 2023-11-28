package sk.uniba.fmph.dcs;

import java.util.Collection;

public interface FloorInterface {
    void put(final Collection<Tile> tiles);
    Points finishRound();
    String state();
}
