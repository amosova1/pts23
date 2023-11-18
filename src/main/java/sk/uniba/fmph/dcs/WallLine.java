package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

public class WallLine {
    private Tile[] tiles;
    private final ArrayList<Tile> tileTypes;
    private final Map<Tile, Integer> idxOf = new HashMap<>();
    private final Integer N;
    private final WallLine lineDown, lineUp;

    public WallLine(ArrayList<Tile> tileTypes, WallLine lineDown, WallLine lineUp) {
        this.lineDown = lineDown;
        this.lineUp = lineUp;
        this.tileTypes = tileTypes;
        this.N = this.tileTypes.size();
        this.tiles = new Tile[this.N];
        for (int idx = 0; idx < this.N; idx++) {
            idxOf.put(this.tileTypes.get(idx), idx);
        }
    }

    Boolean canPutTile(Tile tile) {
        // zly tip, asi nenastatne, ale radsej skontrolujme
        if (!this.idxOf.containsKey(tile))
            return false;
        
        return this.tiles[this.idxOf.get(tile)] == null;
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

        int points = 0;

        // horizontal
        Integer lIdx = idx, rIdx = idx;
        while (lIdx - 1 >= 0 && this.tiles[lIdx - 1] != null)
            lIdx--;
        while (rIdx + 1 < this.N && this.tiles[rIdx + 1] != null)
            rIdx++;
        points += rIdx - lIdx + 1;

        // vertical
        WallLine curLine = this;
        lIdx = rIdx = 0;
        while (curLine.lineUp != null) {
            Optional<Tile>[] upTiles = curLine.lineUp.getTiles();
            if (upTiles.length <= idx || upTiles[idx].isEmpty())
                break;
            
            rIdx++;
            curLine = curLine.lineUp;
        }

        while (curLine.lineDown != null) {
            Optional<Tile>[] downTiles = curLine.lineDown.getTiles();
            if (downTiles.length <= idx || downTiles[idx].isEmpty())
                break;
            
            lIdx--;
            curLine = curLine.lineDown;
        }
        points += rIdx - lIdx + 1;

        return new Points(points);
    }

    public String state() {
        String toReturn = "";
        for (final Tile tile : this.tiles) {
            toReturn += tile == null ? "-" : tile.toString();
        }
        return toReturn;
      }
}
