package sk.uniba.fmph.dcs;

import java.util.ArrayList;

import org.junit.Test;

import static org.junit.Assert.*;
import static sk.uniba.fmph.dcs.Tile.*;

public class BagTestFinal {

    @Test
    public void state_isEmpty() {
        FakeUsedTyles usedTyles = new FakeUsedTyles();
        ArrayList<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tiles.add(RED);
            tiles.add(GREEN);
            tiles.add(YELLOW);
            tiles.add(BLUE);
            tiles.add(BLACK);
        }

        Bag bag = new Bag(usedTyles, tiles);
        Triple nove = bag.take(100);
        bag = (Bag)nove.getNewBag();

        String expected = "Bag:\n" +
                "RED:0\n" +
                "GREEN:0\n" +
                "YELLOW:0\n" +
                "BLUE:0\n" +
                "BLACK:0\n";
        assertEquals(expected, bag.state());
    }

    @Test
    public void state_isNotEmpty() {
        FakeUsedTyles usedTyles = new FakeUsedTyles();
        ArrayList<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tiles.add(RED);
            tiles.add(GREEN);
            tiles.add(YELLOW);
            tiles.add(BLUE);
            tiles.add(BLACK);
        }

        Bag bag = new Bag(usedTyles, tiles);
        Triple nove = bag.take(1);
        bag = (Bag) nove.getNewBag();
        usedTyles = (FakeUsedTyles) nove.getUsedTyles();

        Tile takenTile = nove.getSelectedTiles().get(0);

        usedTyles.give(tiles);
        String expected = switch (takenTile) {
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
        ArrayList<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tiles.add(RED);
            tiles.add(GREEN);
            tiles.add(YELLOW);
            tiles.add(BLUE);
            tiles.add(BLACK);
        }

        Bag bag = new Bag(usedTyles, tiles);

        int count = 3;
        Triple nove = bag.take(count);

        tiles = nove.getSelectedTiles();

        assertEquals(count, tiles.size());
    }

    @Test
    public void take_invalidCount_refilling() {
        FakeUsedTyles usedTyles = new FakeUsedTyles();
        ArrayList<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tiles.add(RED);
            tiles.add(GREEN);
            tiles.add(YELLOW);
            tiles.add(BLUE);
            tiles.add(BLACK);
        }

        Bag bag = new Bag(usedTyles, tiles);
        Triple nove = bag.take(99);
        bag = (Bag) nove.getNewBag();
        usedTyles = (FakeUsedTyles) nove.getUsedTyles();

        ArrayList<Tile> zobrane = nove.getSelectedTiles();
        usedTyles.give(zobrane);

        int count = 3;
        nove = bag.take(count);

        tiles = nove.getSelectedTiles();

        assertEquals(count, tiles.size());
    }

    @Test
    public void refill_NotContainsStartingPlayer() {
        FakeUsedTyles usedTyles = new FakeUsedTyles();
        ArrayList<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tiles.add(RED);
            tiles.add(GREEN);
            tiles.add(YELLOW);
            tiles.add(BLUE);
            tiles.add(BLACK);
        }

        Bag bag = new Bag(usedTyles, tiles);
        Triple nove = bag.take(100);

        ArrayList<Tile> get = nove.getSelectedTiles();

        assertFalse(get.contains(Tile.STARTING_PLAYER));
    }

}
