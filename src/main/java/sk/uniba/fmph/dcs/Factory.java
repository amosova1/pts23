package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Factory implements FactoryInterface{
    private ArrayList<Tile> _tiles;
    private BagInteface bag_instance;
    private TableCenterInterface tableCenter_instance;
    public Factory(BagInteface bag_instance, TableCenterInterface tableCenter_instance, ArrayList<Tile> tiles){
        this.tableCenter_instance = tableCenter_instance;
        this.bag_instance = bag_instance;
        this._tiles = new ArrayList<>(tiles);
    }

    @Override
    public Triple take(int idx) {
        if (idx < 0 || idx >= 4 || _tiles.isEmpty()) {
            return new Triple(bag_instance, new ArrayList<>(), UsedTyles.getInstance());
        }

        List<Tile> selectedTiles = _tiles.stream()
                .filter(tile -> tile.equals(_tiles.get(idx)))
                .collect(Collectors.toList());

        List<Tile> remainingTiles = _tiles.stream()
                .filter(tile -> !tile.equals(_tiles.get(idx)))
                .collect(Collectors.toList());

        tableCenter_instance.add(new ArrayList<>(remainingTiles));

        return new Triple(bag_instance, (ArrayList<Tile>) selectedTiles, UsedTyles.getInstance());
    }

    @Override
    public boolean isEmpty() {
        return _tiles.isEmpty();
    }

    @Override
    public Triple startNewRound() {
        Triple nove = bag_instance.take(4);
        bag_instance = nove.getNewBag();
        UsedTilesGiveInterface newUsedTiles = nove.getUsedTyles();

        return new Triple(bag_instance, new Factory(bag_instance, tableCenter_instance, nove.getSelectedTiles()), newUsedTiles);
    }

    @Override
    public String state() {
        return _tiles.stream()
                .map(Tile::toString)
                .collect(Collectors.joining("\n", "Factory:\n", ""));
    }
}
