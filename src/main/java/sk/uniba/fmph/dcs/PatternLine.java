package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PatternLine {
    private int capacity;
    private List<Tile> tiles;
    public PatternLine(int capacity){
        this.capacity = capacity;
        this.tiles = new ArrayList<>();
    }
    public void put(final Collection<Tile> tiles) {
        this.tiles.addAll(tiles);
    }

    public Points finishRound(){
        return null;
    }
    public String state(){
        String toReturn = "";
        for (final Tile tile : tiles) {
            toReturn += tile.toString();
        }
        toReturn += "\n";
        return toReturn;
    }

}
