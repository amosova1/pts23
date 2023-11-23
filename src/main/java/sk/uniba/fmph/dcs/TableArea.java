package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public class TableArea{
    private ArrayList<TyleSource> _tyleSources;
    public TableArea(ArrayList<TyleSource> tyleSources){
        _tyleSources = tyleSources;
    }

    public ArrayList<Tile> take(int sourceId, int idx){
        ArrayList<Tile> fin = new ArrayList<>();
        if(sourceId < 0 || sourceId >= _tyleSources.size()) {
            return fin;
        }
        TyleSource tyleSource = _tyleSources.get(sourceId);
        if(idx < 0 || idx >= _tyleSources.size()) {
            return fin;
        }
        for (Tile t : tyleSource.take(idx)) {
            fin.add(t);
        }
        return fin;
    }

    public boolean isRoundEnd(){
        for(TyleSource tyleSource: _tyleSources){
            if(!tyleSource.isEmpty()) return false;
        }
        return true;
    }

    public void startNewRound(){
        for(TyleSource tyleSource: _tyleSources){
            tyleSource.startNewRound();
        }
    }

    public String state(){
        StringBuilder ans = new StringBuilder();
        int i = 0;
        for (TyleSource tile : _tyleSources) ans.append(tile.toString());
        return ans.toString();
    }
}