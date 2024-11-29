package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public class Triple {

    private BagInteface newBag;

    private FactoryInterface newFactory;

    private final UsedTilesGiveInterface usedTyles;

    private ArrayList<Tile> selectedTiles;

    public Triple(BagInteface newBag, FactoryInterface newFactory, UsedTilesGiveInterface usedTyles) {
        this.newBag = newBag;
        this.newFactory = newFactory;
        this.usedTyles = usedTyles;
    }

    public Triple(BagInteface newBag, ArrayList<Tile> selectedTiles, UsedTilesGiveInterface usedTyles) {
        this.newBag = newBag;
        this.selectedTiles = selectedTiles;
        this.usedTyles = usedTyles;
    }
    public BagInteface getNewBag() {
        return newBag;
    }

    public UsedTilesGiveInterface getUsedTyles() {
        return usedTyles;
    }

    public  FactoryInterface getNewFactory() {return newFactory;}

    public ArrayList<Tile> getSelectedTiles() {return selectedTiles;}
}
