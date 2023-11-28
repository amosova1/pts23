package sk.uniba.fmph.dcs;

import java.util.List;
import java.util.Optional;

public interface WallLineInterface {
    Boolean canPutTile(Tile tile);
    Points putTile(Tile tile);
    String state();
    List<Optional<Tile>> getTiles();
}

