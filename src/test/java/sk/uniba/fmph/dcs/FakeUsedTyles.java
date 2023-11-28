package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collection;

public class FakeUsedTyles implements UsedTilesGiveInterface{
    private ArrayList<Tile> _usedTyles;
    public FakeUsedTyles(){
        _usedTyles = new ArrayList<>();
    }

    private static class FakeUsedTylesHolder {
        private static final FakeUsedTyles INSTANCE = new FakeUsedTyles();
    }

    public static FakeUsedTyles getInstance() {
        return FakeUsedTyles.FakeUsedTylesHolder.INSTANCE;
    }

    @Override
    public void give(Collection<Tile> tiles){
        for(Tile tile: tiles){
            _usedTyles.add(tile);
        }
    }

    public String state(){
        StringBuilder ans = new StringBuilder();
        ans.append("UsedTyles:\n");
        for (Tile ts: this._usedTyles) {
            ans.append(ts.toString()).append("\n");
        }
        return ans.toString();
    }

    @Override
    public ArrayList<Tile> takeAll(){
        ArrayList<Tile> ans = _usedTyles;
        _usedTyles = new ArrayList<>();
        return ans;
    }
}