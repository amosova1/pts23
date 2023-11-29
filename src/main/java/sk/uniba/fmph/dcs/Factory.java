package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public class Factory implements TyleSource, FactoryInterface{
    private ArrayList<Tile> _tyles;
    private BagInteface bag_instance;
    private TableCenterInterface tableCenter_instance;
    public Factory(BagInteface bag_instance, TableCenterInterface tableCenter_instance){
        this.tableCenter_instance = tableCenter_instance;
        this.bag_instance = bag_instance;
        this._tyles = new ArrayList<>();
    }

    @Override
    public ArrayList<Tile> take(int idx) {
        ArrayList<Tile> vyber = new ArrayList<>();
        if (idx < 0 || idx >= 4 || _tyles.isEmpty()){
            return vyber;
        }
        for (Tile tyle : _tyles) {
            if (this._tyles.get(idx).equals(tyle)) {
                vyber.add(tyle);
            }
        }

        for (Tile tile : vyber) {
            this._tyles.remove(tile);
        }
        ArrayList<Tile> vyber2 = new ArrayList<>(this._tyles);
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
