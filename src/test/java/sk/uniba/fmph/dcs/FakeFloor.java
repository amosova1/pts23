package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collection;

public class FakeFloor implements FloorInterface{
    public FakeFloor() {
    }

    @Override
    public void put(final Collection<Tile> tiles) {
    }

    @Override
    public String state() {
        return "";
    }

    @Override
    public Points finishRound() {
        return null;
    }
}
