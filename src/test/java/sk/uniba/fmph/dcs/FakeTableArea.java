package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public class FakeTableArea implements TableAreaInterface{
    private ArrayList<TyleSource> _tyleSources;
    public FakeTableArea(TyleSource tableCenter, ArrayList<TyleSource> factories){
        this._tyleSources = new ArrayList<>();
        this._tyleSources.add(tableCenter);
        this._tyleSources.addAll(factories);
    }

    @Override
    public ArrayList<Tile> take(int sourceId, int idx){
        ArrayList<Tile> fin = new ArrayList<>();

        if(sourceId < 0 || sourceId >= _tyleSources.size()) {
            return fin;
        }
        TyleSource tyleSource = _tyleSources.get(sourceId);
        if (tyleSource.isEmpty()) {
            return fin;
        }
        if(idx < 0 || idx > 4) {
            return fin;
        }
        for (Tile t : tyleSource.take(idx)) {
            fin.add(t);
        }
        return fin;
    }

    @Override
    public boolean isRoundEnd(){
        for(TyleSource tyleSource: _tyleSources){
            if(!tyleSource.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public void startNewRound(){
        for(TyleSource tyleSource: _tyleSources){
            tyleSource.startNewRound();
        }
    }

    @Override
    public String state(){
        StringBuilder ans = new StringBuilder();
        ans.append("TyleSources:\n");
        for (TyleSource ts: this._tyleSources) {
            ans.append(ts.state()).append("\n");
        }
        return ans.toString();
    }
}
