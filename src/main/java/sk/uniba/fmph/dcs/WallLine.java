package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

public class WallLine {
    private Tile[] tiles;
    private ArrayList<Tile> tileTypes;
    private Map<Tile, Integer> idxOf = new HashMap<>();
    private Integer N;

    public WallLine(ArrayList<Tile> tileTypes) {
        this.N = this.tileTypes.size();
        this.tiles = new Tile[this.N];
        this.tileTypes = tileTypes;
        for (int idx = 0; idx < this.N; idx++) {
            idxOf.put(this.tileTypes.get(idx), idx);
        }
    }

    Boolean canPutTile(Tile tile) {
        // zly tip, asi nenastatne, ale radsej skontrolujme
        if (!this.idxOf.containsKey(tile))
            return false;
        
        return this.tiles[this.idxOf.get(tile)] != null;
    }

    Optional<Tile>[] getTiles() {
        @SuppressWarnings("unchecked")
        Optional<Tile>[] resultTiles = (Optional[]) new Object[this.N];

        for (int idx = 0; idx < this.N; idx++)
            resultTiles[idx] = Optional.of(this.tiles[idx]);

        return resultTiles;
    }

    Points putTile(Tile tile) {
        // taky tile nie je na stene
        if (!this.idxOf.containsKey(tile))
            return new Points(0);
        
        // ak uz je v line tile toho typu, tak tiez nic
        Integer idx = this.idxOf.get(tile);
        if (this.tiles[idx] != null)
            return new Points(0);

        this.tiles[idx] = tile;

        return new Points(0);
    }

    public String state() {
        String toReturn = "";
        for (final Tile tile : this.tiles) {
            toReturn += tile == null ? "-" : tile.toString();
        }
        return toReturn;
      }
}
