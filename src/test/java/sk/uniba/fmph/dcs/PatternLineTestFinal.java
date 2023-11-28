package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

public class PatternLineTestFinal {

    @Test
    public void put_enoughTiles() {
        ArrayList<Tile> tileTypes = new ArrayList<>();
        tileTypes.add(Tile.RED);
        tileTypes.add(Tile.GREEN);
        tileTypes.add(Tile.YELLOW);
        tileTypes.add(Tile.BLUE);
        tileTypes.add(Tile.BLACK);
        FakeUsedTyles usedTyles = new FakeUsedTyles();

        ArrayList<Points> pointPattern = new ArrayList<>();
        pointPattern.add(new Points(-1));
        pointPattern.add(new Points(-1));
        pointPattern.add(new Points(-2));
        pointPattern.add(new Points(-2));
        pointPattern.add(new Points(-2));
        pointPattern.add(new Points(-3));
        pointPattern.add(new Points(-3));

        WallLineInterface wallLine = new FakeWallLine();
        FloorInterface floor = new Floor(usedTyles, pointPattern);
        PatternLine patternLine = new PatternLine(3, wallLine, floor);

        ArrayList<Tile> tiles = new ArrayList<>(List.of(Tile.RED, Tile.RED));

        patternLine.put(tiles);
        assertTrue(patternLine.state().contains("R"));
    }

    @Test
    public void put_notEnoughTiles() {
        ArrayList<Tile> tileTypes = new ArrayList<>();
        tileTypes.add(Tile.RED);
        tileTypes.add(Tile.GREEN);
        tileTypes.add(Tile.YELLOW);
        tileTypes.add(Tile.BLUE);
        tileTypes.add(Tile.BLACK);
        FakeUsedTyles usedTyles = new FakeUsedTyles();

        ArrayList<Points> pointPattern = new ArrayList<>();
        pointPattern.add(new Points(-1));
        pointPattern.add(new Points(-1));
        pointPattern.add(new Points(-2));
        pointPattern.add(new Points(-2));
        pointPattern.add(new Points(-2));
        pointPattern.add(new Points(-3));
        pointPattern.add(new Points(-3));

        WallLineInterface wallLine = new FakeWallLine();

        FloorInterface floor = new Floor(usedTyles, pointPattern);
        PatternLine patternLine = new PatternLine(3, wallLine, floor);

        ArrayList<Tile> tiles = new ArrayList<>(List.of(Tile.RED, Tile.RED, Tile.RED, Tile.RED));

        patternLine.put(tiles);
        assertTrue(patternLine.state().contains("R"));
        assertTrue(patternLine.state().contains("R"));
    }

    @Test
    public void put_DifferentColors() {
        ArrayList<Tile> tileTypes = new ArrayList<>();
        tileTypes.add(Tile.RED);
        tileTypes.add(Tile.GREEN);
        tileTypes.add(Tile.YELLOW);
        tileTypes.add(Tile.BLUE);
        tileTypes.add(Tile.BLACK);
        FakeUsedTyles usedTyles = new FakeUsedTyles();

        ArrayList<Points> pointPattern = new ArrayList<>();
        pointPattern.add(new Points(-1));
        pointPattern.add(new Points(-1));
        pointPattern.add(new Points(-2));
        pointPattern.add(new Points(-2));
        pointPattern.add(new Points(-2));
        pointPattern.add(new Points(-3));
        pointPattern.add(new Points(-3));

        WallLineInterface wallLine = new FakeWallLine();
        FloorInterface floor = new Floor(usedTyles, pointPattern);
        PatternLine patternLine = new PatternLine(3, wallLine, floor);

        ArrayList<Tile> tiles = new ArrayList<>(List.of(Tile.RED, Tile.RED));
        patternLine.put(tiles);

        tiles = new ArrayList<>(List.of(Tile.GREEN, Tile.GREEN));
        patternLine.put(tiles);
        assertTrue(patternLine.state().contains("R"));
        assertTrue(floor.state().contains("G"));
    }

    @Test
    public void finishRound_countPoints(){
        ArrayList<Tile> tileTypes = new ArrayList<>();
        tileTypes.add(Tile.RED);
        tileTypes.add(Tile.GREEN);
        tileTypes.add(Tile.YELLOW);
        tileTypes.add(Tile.BLUE);
        tileTypes.add(Tile.BLACK);
        FakeUsedTyles usedTyles = new FakeUsedTyles();

        ArrayList<Points> pointPattern = new ArrayList<>();
        pointPattern.add(new Points(-1));
        pointPattern.add(new Points(-1));
        pointPattern.add(new Points(-2));
        pointPattern.add(new Points(-2));
        pointPattern.add(new Points(-2));
        pointPattern.add(new Points(-3));
        pointPattern.add(new Points(-3));

        WallLineInterface wallLine = new FakeWallLine();
        FloorInterface floor = new Floor(usedTyles, pointPattern);
        PatternLine patternLine = new PatternLine(3, wallLine, floor);

        ArrayList<Tile> tiles = new ArrayList<>(List.of(Tile.RED, Tile.RED, Tile.RED));
        patternLine.put(tiles);

        assertEquals(new Points(100), patternLine.finishRound());
    }

    @Test
    public void state_isEmpty(){
        ArrayList<Tile> tileTypes = new ArrayList<>();
        tileTypes.add(Tile.RED);
        tileTypes.add(Tile.GREEN);
        tileTypes.add(Tile.YELLOW);
        tileTypes.add(Tile.BLUE);
        tileTypes.add(Tile.BLACK);
        FakeUsedTyles usedTyles = new FakeUsedTyles();

        ArrayList<Points> pointPattern = new ArrayList<>();
        pointPattern.add(new Points(-1));
        pointPattern.add(new Points(-1));
        pointPattern.add(new Points(-2));
        pointPattern.add(new Points(-2));
        pointPattern.add(new Points(-2));
        pointPattern.add(new Points(-3));
        pointPattern.add(new Points(-3));

        WallLineInterface wallLine = new FakeWallLine();
        FloorInterface floor = new Floor(usedTyles, pointPattern);
        PatternLine patternLine = new PatternLine(3, wallLine, floor);

        assertEquals("PatternLine:\n" +
                "capacity: 3\n", patternLine.state());
    }

    @Test
    public void state_isNotEmpty(){
        ArrayList<Tile> tileTypes = new ArrayList<>();
        tileTypes.add(Tile.RED);
        tileTypes.add(Tile.GREEN);
        tileTypes.add(Tile.YELLOW);
        tileTypes.add(Tile.BLUE);
        tileTypes.add(Tile.BLACK);
        FakeUsedTyles usedTyles = new FakeUsedTyles();

        ArrayList<Points> pointPattern = new ArrayList<>();
        pointPattern.add(new Points(-1));
        pointPattern.add(new Points(-1));
        pointPattern.add(new Points(-2));
        pointPattern.add(new Points(-2));
        pointPattern.add(new Points(-2));
        pointPattern.add(new Points(-3));
        pointPattern.add(new Points(-3));

        WallLineInterface wallLine = new FakeWallLine();
        FloorInterface floor = new Floor(usedTyles, pointPattern);
        PatternLine patternLine = new PatternLine(3, wallLine, floor);

        ArrayList<Tile> tiles = new ArrayList<>(List.of(Tile.RED, Tile.RED, Tile.RED));
        patternLine.put(tiles);

        assertEquals("PatternLine:\n" + "capacity: 3\n" + "R\n" + "R\n" + "R\n", patternLine.state());
    }
}
