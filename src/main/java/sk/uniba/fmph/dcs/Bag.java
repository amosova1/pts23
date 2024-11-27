package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Random;

public class Bag implements BagInteface{
    private final ArrayList<Tile> _tiles;
    private final UsedTilesGiveInterface usedTyles_instance;
    public Bag(UsedTilesGiveInterface usedTyles, ArrayList<Tile> tiles){
        this.usedTyles_instance = usedTyles;
        _tiles = tiles;
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
            int k = new Random().nextInt(remainingTiles.size());
            selectedTiles.add(remainingTiles.get(k));
            remainingTiles.remove(k);
        }
        return new Pair(new Bag(newUsedTiles, remainingTiles), selectedTiles);
    }

    @Override
    public String state(){
        return "Bag:\n" +
                "RED:" + this._tiles.stream().filter(x -> x == Tile.RED).count() + "\n" +
                "GREEN:" + this._tiles.stream().filter(x -> x == Tile.GREEN).count() + "\n" +
                "YELLOW:" + this._tiles.stream().filter(x -> x == Tile.YELLOW).count() + "\n" +
                "BLUE:" + this._tiles.stream().filter(x -> x == Tile.BLUE).count() + "\n" +
                "BLACK:" + this._tiles.stream().filter(x -> x == Tile.BLACK).count() + "\n";
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
