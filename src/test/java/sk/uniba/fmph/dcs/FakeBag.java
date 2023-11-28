package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FakeBag implements BagInteface{
    private ArrayList<Tile> _tiles;
    private UsedTilesGiveInterface usedTyles_instance;
    public FakeBag(UsedTilesGiveInterface usedTyles){
        this.usedTyles_instance = usedTyles;
        this._tiles = new ArrayList<>();

        _tiles.addAll(List.of(Tile.GREEN, Tile.RED, Tile.RED, Tile.BLUE));
    }

    public ArrayList<Tile> take(int count) {
        if (this._tiles.isEmpty() || this._tiles.size() < count){
            //System.out.println("Bag is empty, refilling");
            refill();
            //System.out.println(this._tiles.size());
        }

        ArrayList<Tile> vyber = new ArrayList<>();
        for (int i = 0; i < count; i++){
            vyber.add(this._tiles.get(i));
        }
        for (int i = 0; i < count; i++){
            this._tiles.remove(0);
        }
        return vyber;
    }

    public String state(){
        StringBuilder ans = new StringBuilder();
        ans.append("Bag:\n");
        for (Tile ts: this._tiles) {
            ans.append(ts.toString()).append("\n");
        }
        return ans.toString();
    }

    private void refill(){
        ArrayList<Tile> tiles = usedTyles_instance.takeAll();
        tiles.remove(Tile.STARTING_PLAYER);

        this._tiles.addAll(tiles);
    }
}
