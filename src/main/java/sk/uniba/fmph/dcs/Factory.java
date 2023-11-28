package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public class Factory implements TyleSource, FactoryInterface{
    private ArrayList<Tile> _tyles;
    private BagInteface bag_instance;
    private TableCenter tableCenter_instance;
    public Factory(BagInteface bag_instance){
        this.tableCenter_instance = TableCenter.getInstance();
        this.bag_instance = bag_instance;
        _tyles = new ArrayList<>();
    }

    @Override
    public ArrayList<Tile> take(int idx) {
        ArrayList<Tile> vyber = new ArrayList<>();
        if (idx < 0 || idx >= 4 || _tyles.isEmpty()){
            return vyber;
        }
        for (int i = 0; i < _tyles.size(); i++){
            if (this._tyles.get(idx).equals(this._tyles.get(i))){
                vyber.add(this._tyles.get(i));
            }
        }

        for (int i = 0; i < vyber.size(); i++){
            this._tyles.remove(vyber.get(i));
        }
        Tile[] vyber2 = new Tile[this._tyles.size()];
        for (int i = 0; i < this._tyles.size(); i++){
            vyber2[i] = this._tyles.get(i);
        }
        this._tyles.clear();
        this.tableCenter_instance.add(vyber2);

        return vyber;
    }

    @Override
    public boolean isEmpty() {
        return _tyles.isEmpty();
    }

    @Override
    public void startNewRound() {
        this._tyles = bag_instance.take(4);
    }

    @Override
    public String state() {
        StringBuilder ans = new StringBuilder();
        ans.append("Factory:\n");
        for (Tile tile : _tyles) {
            ans.append(tile.toString()).append("\n");
        }
        return ans.toString();
    }
}
