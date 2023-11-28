package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bag implements BagInteface{

    private ArrayList<Tile> _tiles;
    private UsedTilesGiveInterface usedTyles_instance;
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
            this.refill();
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
        StringBuilder ans = new StringBuilder();
        ans.append("Bag:\n");
        ans.append("RED:").append(this._tiles.stream().filter(x -> x == Tile.RED).count()).append("\n");
        ans.append("GREEN:").append(this._tiles.stream().filter(x -> x == Tile.GREEN).count()).append("\n");
        ans.append("YELLOW:").append(this._tiles.stream().filter(x -> x == Tile.YELLOW).count()).append("\n");
        ans.append("BLUE:").append(this._tiles.stream().filter(x -> x == Tile.BLUE).count()).append("\n");
        ans.append("BLACK:").append(this._tiles.stream().filter(x -> x == Tile.BLACK).count()).append("\n");
        return ans.toString();
    }

    private void refill(){
        ArrayList<Tile> tiles = usedTyles_instance.takeAll();
        tiles.remove(Tile.STARTING_PLAYER);

        this._tiles.addAll(tiles);
    }
}
