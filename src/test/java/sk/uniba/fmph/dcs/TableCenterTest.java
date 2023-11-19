package sk.uniba.fmph.dcs;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class TableCenterTest {
    @Test
    public void givesStartingTileAndCanBeEmptied() {
        TableCenter tc = TableCenter.getInstance();
        tc.startNewRound();
        assertEquals("Does not have a starting tile", "S", tc.state());
        tc.add(new Tile[]{Tile.RED, Tile.RED, Tile.YELLOW, Tile.GREEN});
        assertEquals("Did not successfully accept all new tiles.", "SRRIG", tc.state());
        Tile[] tiles = tc.take(1);
        Tile[] expected = new Tile[]{Tile.RED, Tile.RED, Tile.STARTING_PLAYER};
        assertArrayEquals("First take does not include the starting player tile as well as all the tiles of the same color as the taken tile.", tiles, expected);
        assertEquals("Removal of tiles did not go very well","IG", tc.state());
        assertFalse("Should not be reporting that it's empty.", tc.isEmpty());
        tc.take(0);
        assertEquals("Removal of tiles did not go very well", "G", tc.state());
        tc.take(0);
        assertTrue("Should be reporting that it's empty.", tc.isEmpty());
    }
}
