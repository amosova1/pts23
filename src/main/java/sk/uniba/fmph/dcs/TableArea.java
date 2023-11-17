package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public class TableArea{
    private ArrayList<TyleSource> _tyleSources;
    public TableArea(){
        this._tyleSources = new ArrayList<>();
        this._tyleSources.add(TableCenter.getInstance());
        for (int i = 0; i < 4; i++){
            this._tyleSources.add(new Factory());
        }
    }

    public Tile[] take(int sourceId, int idx){
        return _tyleSources.get(sourceId).take(idx);
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
        for(TyleSource tyleSource: _tyleSources){
            if (tyleSource instanceof TableCenter){
                ans.append("Table center: \n");
            } else {
                ans.append("Factory " + i + " : \n");
            }
            ans.append(tyleSource.state());
            i++;
        }
        return ans.toString();
    }
}