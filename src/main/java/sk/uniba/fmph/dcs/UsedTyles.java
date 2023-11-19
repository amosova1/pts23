package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public class UsedTyles {
    private ArrayList<Tile> _usedTyles;
    public UsedTyles(){
        _usedTyles = new ArrayList<>();
    }

    public void give(Tile[] tiles){
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

    public Tile[] takeAll(){
        Tile[] ans = new Tile[_usedTyles.size()];
        _usedTyles.toArray(ans);
        _usedTyles.clear();
        return ans;
    }
}
