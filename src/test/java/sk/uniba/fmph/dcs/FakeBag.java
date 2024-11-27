package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FakeBag implements BagInteface{
    private final ArrayList<Tile> _tiles;
    private final UsedTilesGiveInterface usedTyles_instance;
    public FakeBag(UsedTilesGiveInterface usedTyles){
        this.usedTyles_instance = usedTyles;
        this._tiles = new ArrayList<>();

        _tiles.addAll(List.of(Tile.GREEN, Tile.RED, Tile.RED, Tile.BLUE));
    }

    @Override
    public Pair take(int count) {
        ArrayList<Tile> remainingTiles = new ArrayList<>(this._tiles);
        ArrayList<Tile> selectedTiles = new ArrayList<>();
        UsedTilesGiveInterface newUsedTiles = usedTyles_instance;

        if (remainingTiles.isEmpty() || remainingTiles.size() < count){
            ArrayList<Tile> tiles = newUsedTiles.takeAll();
            tiles.remove(Tile.STARTING_PLAYER);

            remainingTiles.addAll(tiles);
//            refill();
        }

        for (int i = 0; i < count; i++){
            selectedTiles.add(remainingTiles.get(0));
            remainingTiles.remove(0);
        }
        return new Pair(new Bag(newUsedTiles, remainingTiles), selectedTiles);
    }

    @Override
    public String state(){
        return "";
    }

//    private Pair refill(){
//        ArrayList<Tile> tiles = new ArrayList<>(_tiles);
//        ArrayList<Tile> tiles2 = usedTyles_instance.takeAll();
//        usedTyles_instance.give(tiles2);
//
//        tiles.addAll(tiles2);
//        tiles.remove(Tile.STARTING_PLAYER);
//
//        return new Pair(new Bag(this.usedTyles_instance, tiles), new ArrayList<>());
//    }
}
