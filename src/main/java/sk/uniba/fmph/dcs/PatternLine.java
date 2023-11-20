package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PatternLine {
    private int capacity;
    private List<Tile> tiles;
    private final Floor floor;
    private final WallLine adjacentWallLine;
    public PatternLine(int capacity, Floor floor, WallLine wallLine){
        this.capacity = capacity;
        this.tiles = new ArrayList<>();
        this.floor = floor;
        this.adjacentWallLine = wallLine;
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
        if(tiles.size() == capacity) {
            if (adjacentWallLine.canPutTile(tiles.get(0))){
                tiles = new ArrayList<>();
                return adjacentWallLine.putTile(tiles.get(0));
            }
        }
        tiles = new ArrayList<>();
        return new Points(0);
    }
    public String state(){
        String toReturn = "";
        for (final Tile tile : tiles) {
            toReturn += tile.toString();
        }
        return toReturn;
    }

}
