package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Bag implements BagInteface{
    private final ArrayList<Tile> _tiles;
    private final UsedTilesGiveInterface usedTiles_instance;
    public Bag(UsedTilesGiveInterface usedTiles, ArrayList<Tile> tiles){
        this.usedTiles_instance = usedTiles;
        _tiles = new ArrayList<>(tiles);
    }

    @Override
    public Triple take(int count) {
        /*ArrayList<Tile> remainingTiles = new ArrayList<>(this._tiles);
        ArrayList<Tile> selectedTiles = new ArrayList<>();
        UsedTilesGiveInterface newUsedTiles = usedTiles_instance;

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
        return new Pair(new Bag(newUsedTiles, remainingTiles), selectedTiles);*/

        ArrayList<Tile> remainingTiles = new ArrayList<>(_tiles);
        UsedTilesGiveInterface newUsedTiles = usedTiles_instance;

        if (remainingTiles.isEmpty() || remainingTiles.size() < count) {
            List<Tile> usedTiles = newUsedTiles.takeAll().stream()
                    .filter(tile -> tile != Tile.STARTING_PLAYER)
                    .collect(Collectors.toList());
            remainingTiles.addAll(usedTiles);
        }

        Random random = new Random();
        List<Tile> selectedTiles = random.ints(0, remainingTiles.size())
                .distinct()
                .limit(Math.min(count, remainingTiles.size()))
                .mapToObj(remainingTiles::get)
                .collect(Collectors.toList());

        for (Tile t: selectedTiles){
            remainingTiles.remove(t);
        }

        return new Triple(new Bag(newUsedTiles, remainingTiles), (ArrayList<Tile>) selectedTiles, newUsedTiles);
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
}
