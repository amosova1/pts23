package sk.uniba.fmph.dcs;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Arrays;

import org.apache.commons.lang3.text.translate.NumericEntityUnescaper.OPTION;
import org.junit.Test;

public class WallLineTest {
  @Test
  public void testCanPutTiles() {
    ArrayList<Tile> tileTypes = new ArrayList<>();
    tileTypes.add(Tile.BLACK);
    tileTypes.add(Tile.BLUE);
    tileTypes.add(Tile.GREEN);
    tileTypes.add(Tile.RED);
    tileTypes.add(Tile.YELLOW);
    
    WallLine wallLine = new WallLine(tileTypes, null, null);
    wallLine.putTile(Tile.BLACK);

    assertFalse("Already present.", wallLine.canPutTile(Tile.BLACK)); // nemali by sme vediet dat
    assertFalse("Not in tileTypes.", wallLine.canPutTile(Tile.STARTING_PLAYER)); // nie je v tileTypes
    assertTrue("Should be insertable.", wallLine.canPutTile(Tile.BLUE)); // mali by sme vediet dat
  }

  @Test
  public void testGetTiles() {
    ArrayList<Tile> tileTypes = new ArrayList<>();
    tileTypes.add(Tile.RED);
    tileTypes.add(Tile.YELLOW);
    tileTypes.add(Tile.BLACK);
    tileTypes.add(Tile.BLUE);
    tileTypes.add(Tile.GREEN);
    
    WallLine wallLine = new WallLine(tileTypes, null, null);
    ArrayList<Optional<Tile>> empty = new ArrayList<>();
    Tile[] tiles = new Tile[5];
    for (int i = 0; i < 5; i++)
        empty.add(Optional.ofNullable(tiles[i]));
    Optional<Tile>[] wallLineTiles = wallLine.getTiles();
    assertEquals(empty.size(), wallLineTiles.length);

    // wallLine.putTile(Tile.BLACK);
    // assertFalse("Is not empty anymore", Arrays.equals(wallLine.getTiles(), empty.toArray()));
    // tiles[2] = Tile.BLACK;
    // ArrayList<Optional<Tile>> wantTiles = new ArrayList<>();
    // for (int i = 0; i < 5; i++)
    //     wantTiles.add(Optional.ofNullable(tiles[i]));
    // assertArrayEquals("Contains something.", wallLine.getTiles(), empty.toArray());
  }
}
