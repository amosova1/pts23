package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Random;

public class Bag implements BagInteface{

    private final ArrayList<Tile> _tiles;
    private final UsedTilesGiveInterface usedTyles_instance;
    public Bag(UsedTilesGiveInterface usedTyles){
        this.usedTyles_instance = usedTyles;
        _tiles = new ArrayList<>();

        for (int i = 0; i < 20; i++){
            _tiles.add(Tile.RED);
            _tiles.add(Tile.GREEN);
            _tiles.add(Tile.YELLOW);
            _tiles.add(Tile.BLUE);
            _tiles.add(Tile.BLACK);
        }
    }

    @Override
    public ArrayList<Tile> take(int count) {
        if (this._tiles.isEmpty() || this._tiles.size() < count){
            refill();
        }

        ArrayList<Tile> vyber = new ArrayList<>();
        for (int i = 0; i < count; i++){
            int k = new Random().nextInt(_tiles.size());
            vyber.add(this._tiles.get(k));
            this._tiles.remove(k);
        }
        return vyber;
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

    private void refill(){
        ArrayList<Tile> tiles = usedTyles_instance.takeAll();
        tiles.remove(Tile.STARTING_PLAYER);

        this._tiles.addAll(tiles);
    }
}
