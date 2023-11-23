package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class UsedTyles implements UsedTilesGiveInterface{
    private ArrayList<Tile> _usedTyles;
    public UsedTyles(){
        _usedTyles = new ArrayList<>();
    }

    public void give(Collection<Tile> tiles){
        for(Tile tile: tiles){
            _usedTyles.add(tile);
        }
    }

    public String state(){
        StringBuilder ans = new StringBuilder();
        int n = _usedTyles.size();
        for (Tile tile : _usedTyles) ans.append(tile.toString());
        return ans.toString();
    }

    public ArrayList<Tile> takeAll(){
        ArrayList<Tile> ans = _usedTyles;
        _usedTyles = new ArrayList<>();
        return ans;
    }
}
