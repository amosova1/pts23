package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collection;

public class TableArea implements TableAreaInterface{
    private ArrayList<TyleSource> _tyleSources;

    private ArrayList<Factory> factories;
    public TableArea(TableCenter tableCenter, ArrayList<Factory> factories){
        this._tyleSources = new ArrayList<>();
        this.factories = new ArrayList<>();
        this._tyleSources.add(tableCenter);
        this.factories.addAll(factories);
    }

    @Override
    public ArrayList<Tile> take(int sourceId, int idx){
        ArrayList<Tile> fin = new ArrayList<>();
        int sizeAll = 1;
        for(Factory f: factories){
            sizeAll++ ;
        }

        //System.out.println(_tyleSources.size() + " _tylesource" );
        if(sourceId < 0 || sourceId >= sizeAll) {
            return fin;
        }

        if (sourceId == 0) {
            TyleSource tyleSource = _tyleSources.get(0);

            if (tyleSource.isEmpty()) {
                return fin;
            }
            if(idx < 0 || idx > 4) {
                return fin;
            }
            fin.addAll(tyleSource.take(idx));
            return fin;
        }

        Factory tyleSource = factories.get(sourceId - 1);

        if (tyleSource.isEmpty()) {
            return fin;
        }
        if(idx < 0 || idx > 4) {
            return fin;
        }

        Triple nove = tyleSource.take(idx);
        tyleSource = (Factory) nove.getNewFactory();
        factories.set(sourceId - 1, tyleSource);

        ArrayList<Tile> result = nove.getSelectedTiles();

        fin.addAll(result);
        return fin;

    }

    @Override
    public boolean isRoundEnd(){
        for(TyleSource tyleSource: _tyleSources){
            if(!tyleSource.isEmpty()) return false;
        }

        for (Factory f: factories){
            if (!f.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public void startNewRound(){
        for(TyleSource tyleSource: _tyleSources){
            tyleSource.startNewRound();
        }

        for (Factory f: factories){
            f.startNewRound();
        }
    }

    @Override
    public String state(){
        StringBuilder ans = new StringBuilder();
        ans.append("TyleSources:\n");
        for (TyleSource ts: this._tyleSources) {
            ans.append(ts.state()).append("\n");
        }

        for (Factory f: factories){
            ans.append(f.state()).append("\n");
        }
        return ans.toString();
    }
}