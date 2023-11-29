package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;

public class FakeBag implements BagInteface{
    private final ArrayList<Tile> _tiles;
    private final UsedTilesGiveInterface usedTyles_instance;
    public FakeBag(UsedTilesGiveInterface usedTyles){
        this.usedTyles_instance = usedTyles;
        this._tiles = new ArrayList<>();

        _tiles.addAll(List.of(Tile.GREEN, Tile.RED, Tile.RED, Tile.BLUE));
    }

    @Override
    public ArrayList<Tile> take(int count) {
        if (this._tiles.isEmpty() || this._tiles.size() < count){
            refill();
        }

        ArrayList<Tile> vyber = new ArrayList<>();
        for (int i = 0; i < count; i++){
            vyber.add(this._tiles.get(i));
        }
        if (count > 0) {
            this._tiles.subList(0, count).clear();
        }
        return vyber;
    }

    @Override
    public String state(){
        return "";
    }

    private void refill(){
        ArrayList<Tile> tiles = usedTyles_instance.takeAll();
        tiles.remove(Tile.STARTING_PLAYER);

        this._tiles.addAll(tiles);
    }
}
