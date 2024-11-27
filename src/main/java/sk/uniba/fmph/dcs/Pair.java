package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public class Pair {

    private BagInteface newBag;

    private FactoryInterface newFactory;

    private ArrayList<Tile> selectedTiles;

    public Pair(BagInteface newBag, ArrayList<Tile> selectedTiles) {
        this.newBag = newBag;
        this.selectedTiles = selectedTiles;
    }

    public Pair(FactoryInterface newFactory, ArrayList<Tile> selectedTiles) {
        this.newFactory = newFactory;
        this.selectedTiles = selectedTiles;
    }

    public BagInteface getNewBag() {
        return newBag;
    }

    public ArrayList<Tile> getSelectedTiles() {
        return selectedTiles;
    }

    public  FactoryInterface getNewFactory() {return newFactory;}
}
