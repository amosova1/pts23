package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collections;

public class FakeTableCenter implements TableCenterInterface, TyleSource{
    private ArrayList<Tile> _tyles;
    public FakeTableCenter(){
        this._tyles = new ArrayList<>();
    }

    @Override
    public ArrayList<Tile> take(int idx) {
        ArrayList<Tile> vyber = new ArrayList<>();

        if (this._tyles.size() == 1 && this._tyles.get(0).equals(Tile.STARTING_PLAYER)){
            return vyber;
        }
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

        if (this._tyles.contains(Tile.STARTING_PLAYER)){
            this._tyles.remove(Tile.STARTING_PLAYER);
            vyber.add(Tile.STARTING_PLAYER);
        }

        return vyber;
    }

    @Override
    public boolean isEmpty() {
        return _tyles.isEmpty();
    }

    @Override
    public void startNewRound() {
        _tyles = new ArrayList<>();
        _tyles.add(Tile.STARTING_PLAYER);
    }

    @Override
    public String state() {
        StringBuilder ans = new StringBuilder();
        ans.append("Table Center:\n");
        for (Tile tile : _tyles) ans.append(tile.toString()).append("\n");
        return ans.toString();
    }

    public void add(ArrayList<Tile> tyles){
        this._tyles.addAll(tyles);
    }
}
