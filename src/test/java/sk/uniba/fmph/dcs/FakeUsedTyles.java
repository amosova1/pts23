package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collection;

public class FakeUsedTyles implements UsedTilesGiveInterface{
    private ArrayList<Tile> _usedTyles;
    public FakeUsedTyles(){
        _usedTyles = new ArrayList<>();
    }

    @Override
    public void give(Collection<Tile> tiles){
        for(Tile tile: tiles){
            _usedTyles.add(tile);
        }
    }

    public String state(){
        return "";
    }

    @Override
    public ArrayList<Tile> takeAll(){
        ArrayList<Tile> ans = _usedTyles;
        _usedTyles = new ArrayList<>();
        return ans;
    }
}