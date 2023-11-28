package sk.uniba.fmph.dcs;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TableCenterTest {
    @Test
    public void givesStartingTileAndCanBeEmptied() {
        TableCenter tc = TableCenter.getInstance();
        tc.startNewRound();
        assertEquals("Does not have a starting tile", "Table Center:\nS", tc.state());
        ArrayList<Tile> tiles = new ArrayList<>(List.of(Tile.RED, Tile.RED, Tile.YELLOW, Tile.GREEN));
        tc.add(tiles);
        assertEquals("Did not successfully accept all new tiles.", "Table Center:\nSRRIG", tc.state());
        tiles = tc.take(1);
        ArrayList<Tile> expected = new ArrayList<>(List.of(Tile.RED, Tile.RED, Tile.STARTING_PLAYER));
        assertEquals("First take does not include the starting player tile as well as all the tiles of the same color as the taken tile.", tiles, expected);
        assertEquals("Removal of tiles did not go very well","Table Center:\nIG", tc.state());
        assertFalse("Should not be reporting that it's empty.", tc.isEmpty());
        tc.take(0);
        assertEquals("Removal of tiles did not go very well", "Table Center:\nG", tc.state());
        tc.take(0);
        assertTrue("Should be reporting that it's empty.", tc.isEmpty());
    }
}
