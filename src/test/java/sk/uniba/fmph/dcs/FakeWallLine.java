package sk.uniba.fmph.dcs;

import java.util.*;

public class FakeWallLine implements WallLineInterface{
    @Override
    public Boolean canPutTile(Tile tile) {
        return true;
    }

    @Override
    public Points putTile(Tile tile) {
        return new Points(100);
    }

    @Override
    public String state() {
        return null;
    }

    @Override
    public List<Optional<Tile>> getTiles() {
        return null;
    }

}