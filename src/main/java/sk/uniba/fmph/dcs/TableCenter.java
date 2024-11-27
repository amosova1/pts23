package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collections;

public class TableCenter implements TyleSource, TableCenterInterface{
    private ArrayList<Tile> _tyles;
    private static TableCenter instance = new TableCenter();
    private TableCenter(){
        this._tyles = new ArrayList<>();
    }
    public static TableCenter getInstance() {
        return instance;
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

        for (int i = 0; i < _tyles.size(); i++){
            if (this._tyles.get(idx).equals(this._tyles.get(i))){
                vyber.add(this._tyles.get(i));
            }
        }

        for (int i = 0; i < vyber.size(); i++){
            this._tyles.remove(vyber.get(i));
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
        for (Tile tile : _tyles) ans.append(tile.toString());
        return ans.toString();
    }

    public void add(ArrayList<Tile> tyles){
        this._tyles.addAll(tyles);
    }
}