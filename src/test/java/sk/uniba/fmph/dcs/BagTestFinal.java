package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class BagTestFinal {

    @Test
    public void state_isEmpty() {
        FakeUsedTyles usedTyles = new FakeUsedTyles();
        Bag bag = new Bag(usedTyles);
        bag.take(100);

        String expected = "Bag:\n" +
                "RED:0\n" +
                "GREEN:0\n" +
                "YELLOW:0\n" +
                "BLUE:0\n" +
                "BLACK:0\n";
        assertEquals(expected, bag.state());
    }

    @Test
    public void state_isNotEmpty(){
        FakeUsedTyles usedTyles = new FakeUsedTyles();
        Bag bag = new Bag(usedTyles);

        ArrayList<Tile> tiles = bag.take(1);
        usedTyles.give(tiles);
        String expected = switch (tiles.get(0)) {
            case RED -> "Bag:\n" +
                    "RED:19\n" +
                    "GREEN:20\n" +
                    "YELLOW:20\n" +
                    "BLUE:20\n" +
                    "BLACK:20\n";
            case GREEN -> "Bag:\n" +
                    "RED:20\n" +
                    "GREEN:19\n" +
                    "YELLOW:20\n" +
                    "BLUE:20\n" +
                    "BLACK:20\n";
            case YELLOW -> "Bag:\n" +
                    "RED:20\n" +
                    "GREEN:20\n" +
                    "YELLOW:19\n" +
                    "BLUE:20\n" +
                    "BLACK:20\n";
            case BLUE -> "Bag:\n" +
                    "RED:20\n" +
                    "GREEN:20\n" +
                    "YELLOW:20\n" +
                    "BLUE:19\n" +
                    "BLACK:20\n";
            case BLACK -> "Bag:\n" +
                    "RED:20\n" +
                    "GREEN:20\n" +
                    "YELLOW:20\n" +
                    "BLUE:20\n" +
                    "BLACK:19\n";
            default -> "";
        };

        assertEquals(expected, bag.state());
    }

    @Test
    public void take_validCount() {
        FakeUsedTyles usedTyles = new FakeUsedTyles();
        Bag bag = new Bag(usedTyles);

        int count = 3;
        ArrayList<Tile> tiles = bag.take(count);

        assertEquals(count, tiles.size());
    }

    @Test
    public void take_invalidCount_refilling() {
        FakeUsedTyles usedTyles = new FakeUsedTyles();
        Bag bag = new Bag(usedTyles);

        ArrayList<Tile> zobrane = bag.take(99); // celkovo 100
        usedTyles.give(zobrane);

        int count = 3;
        ArrayList<Tile> tiles = bag.take(count);

        assertEquals(count, tiles.size());
    }

    @Test
    public void refill_NotContainsStartingPlayer() {
        FakeUsedTyles usedTyles = new FakeUsedTyles();
        Bag bag = new Bag(usedTyles);

        ArrayList<Tile> get = bag.take(100);

        assertFalse(get.contains(Tile.STARTING_PLAYER));
    }

}
