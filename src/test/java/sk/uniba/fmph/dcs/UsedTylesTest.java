package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import static org.junit.Assert.*;

public class UsedTylesTest {

    @Test
    public void tryCreateMoreUsedTyles() {
        UsedTyles usedTyles = UsedTyles.getInstance();
        UsedTyles usedTyles2 = UsedTyles.getInstance();
        assertEquals(usedTyles, usedTyles2);
    }

    @Test
    public void state_give_valid() {
        UsedTyles usedTyles = UsedTyles.getInstance();
        Collection<Tile> tiles = Arrays.asList(Tile.RED, Tile.BLUE, Tile.GREEN);

        usedTyles.give(tiles);

        String expectedState = "UsedTyles:\n" + "R\n" + "B\n" + "G\n";

        assertEquals(expectedState, usedTyles.state());
        usedTyles.takeAll();
    }

    @Test
    public void takeAll_Empty() {
        UsedTyles usedTyles = UsedTyles.getInstance();
        ArrayList<Tile> result = usedTyles.takeAll();
        assertTrue(result.isEmpty());
    }

    @Test
    public void takeAll_notEmpty() {
        UsedTyles usedTyles = UsedTyles.getInstance();
        Collection<Tile> tiles = Arrays.asList(Tile.RED, Tile.BLUE, Tile.GREEN);
        usedTyles.give(tiles);

        ArrayList<Tile> result = usedTyles.takeAll();

        assertEquals(tiles, result);
        assertTrue(usedTyles.takeAll().isEmpty());
    }

    @Test
    public void state_isEmpty(){
        UsedTyles usedTyles = UsedTyles.getInstance();
        String expectedState = "UsedTyles:\n";
        assertEquals(expectedState, usedTyles.state());
    }

    @Test
    public void state_isNotEmpty(){
        UsedTyles usedTyles = UsedTyles.getInstance();
        Collection<Tile> tiles = Arrays.asList(Tile.RED, Tile.BLUE, Tile.GREEN);
        usedTyles.give(tiles);
        String expectedState = "UsedTyles:\n" + "R\n" + "B\n" + "G\n";
        assertEquals(expectedState, usedTyles.state());
        usedTyles.takeAll();
    }
}

