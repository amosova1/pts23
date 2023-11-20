package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PatternLine {
    private int capacity;
    private List<Tile> tiles;
    private final Floor floor;
    public PatternLine(int capacity, Floor floor){
        this.capacity = capacity;
        this.tiles = new ArrayList<>();
        this.floor = floor;
    }
    public void put(final Collection<Tile> newTiles) {
        for(Tile newTile: newTiles){
            if(newTile == Tile.STARTING_PLAYER){
                floor.put(Collections.singleton(newTile));
            }
            else if(this.tiles.size() < capacity){
                tiles.add(newTile);
            }
            else floor.put(Collections.singleton(newTile));
        }
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
