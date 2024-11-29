package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public class FakeFactory implements FactoryInterface{
    private ArrayList<Tile> _tyles;
    private  BagInteface bag_instance;
    private final TableCenter tableCenter_instance;
    public FakeFactory(BagInteface bag_instance, ArrayList<Tile> tile){
        this.tableCenter_instance = TableCenter.getInstance();
        this.bag_instance = bag_instance;
        _tyles = new ArrayList<>(tile);
    }

    @Override
    public Triple take(int idx) {
        ArrayList<Tile> selectedTiles = new ArrayList<>();
        ArrayList<Tile> remainingTiles = new ArrayList<>(this._tyles);
        TableCenterInterface newTableCenter_interface = tableCenter_instance;

        if (idx < 0 || idx >= 4 || remainingTiles.isEmpty()){
            return new Triple(bag_instance, selectedTiles, UsedTyles.getInstance());
        }
        for (Tile tyle : remainingTiles) {
            if (remainingTiles.get(idx).equals(tyle)) {
                selectedTiles.add(tyle);
            }
        }

        for (Tile tile : selectedTiles) {
            remainingTiles.remove(tile);
        }
        ArrayList<Tile> vyber2 = new ArrayList<>(remainingTiles);
        remainingTiles.clear();
        newTableCenter_interface.add(vyber2);

        return new Triple(bag_instance, selectedTiles, UsedTyles.getInstance());
    }

    @Override
    public boolean isEmpty() {
        return _tyles.isEmpty();
    }

    @Override
    public Triple startNewRound() {
        Triple nove = bag_instance.take(4);
        bag_instance = nove.getNewBag();
        UsedTilesGiveInterface newUsedTiles = nove.getUsedTyles();

        return new Triple(bag_instance, new FakeFactory(bag_instance, nove.getSelectedTiles()), newUsedTiles);
    }

    @Override
    public String state() {
        return "";
    }
}
