package sk.uniba.fmph.dcs;

import java.util.*;

public class FakeWallLine implements WallLineInterface{
    private Tile[] tiles;
    private final ArrayList<Tile> tileTypes;
    private final Map<Tile, Integer> idxOf = new HashMap<>();
    private final Integer N;
    private FakeWallLine lineDown, lineUp;

    public FakeWallLine(ArrayList<Tile> tileTypes, FakeWallLine lineDown, FakeWallLine lineUp) {
        this.lineDown = null;
        this.lineUp = null;
        this.tileTypes = tileTypes;
        this.N = this.tileTypes.size();
        this.tiles = new Tile[this.N];
        for (int idx = 0; idx < this.N; idx++) {
            idxOf.put(this.tileTypes.get(idx), idx);
        }
    }

    @Override
    public Boolean canPutTile(Tile tile) {
        // zly tip, asi nenastatne, ale radsej skontrolujme
        if (!this.idxOf.containsKey(tile))
            return false;

        return this.tiles[this.idxOf.get(tile)] == null;
    }

    @Override
    public List<Optional<Tile>> getTiles() {
        ArrayList<Optional<Tile>> resultTiles = new ArrayList<>();

        for (int idx = 0; idx < this.N; idx++)
            resultTiles.add(Optional.ofNullable(this.tiles[idx]));

        return resultTiles;
    }

    @Override
    public Points putTile(Tile tile) {
        // taky tile nie je na stene
        if (!this.idxOf.containsKey(tile))
            return new Points(0);

        // ak uz je v line tile toho typu, tak tiez nic
        int idx = this.idxOf.get(tile);
        if (this.tiles[idx] != null) {
            return new Points(0);
        }
        this.tiles[idx] = tile;

        // horizontal
        int lIdx = idx, rIdx = idx;
        while (lIdx - 1 >= 0 && this.tiles[lIdx - 1] != null)
            lIdx--;
        while (rIdx + 1 < this.N && this.tiles[rIdx + 1] != null)
            rIdx++;
        int horizontalPoints = rIdx - lIdx + 1;

        return new Points(horizontalPoints);
    }

    @Override
    public String state() {
        String toReturn = "";
        for (final Tile tile : this.tiles) {
            toReturn += tile == null ? "-" : tile.toString();
        }
        return toReturn;
    }


    public void setLineDown(FakeWallLine lineDown) {
        this.lineDown = lineDown;
    }


    public void setLineUp(FakeWallLine lineUp) {
        this.lineUp =  lineUp;
    }
}