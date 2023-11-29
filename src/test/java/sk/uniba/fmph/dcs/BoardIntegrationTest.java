package sk.uniba.fmph.dcs;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class BoardIntegrationTest {
    @Test
    public void integrationTest() {
        FakeUsedTyles usedTyles = new FakeUsedTyles();

        ArrayList<Points> pointPattern = new ArrayList<>();
        pointPattern.add(new Points(-1));
        pointPattern.add(new Points(-1));
        pointPattern.add(new Points(-2));
        pointPattern.add(new Points(-2));
        pointPattern.add(new Points(-2));
        pointPattern.add(new Points(-3));
        pointPattern.add(new Points(-3));
        Floor floor = new Floor(usedTyles, pointPattern);

        ArrayList<Tile> tileTypes = new ArrayList<>();
        tileTypes.add(Tile.RED);
        tileTypes.add(Tile.GREEN);
        tileTypes.add(Tile.YELLOW);
        tileTypes.add(Tile.BLUE);
        tileTypes.add(Tile.BLACK);
        ArrayList<WallLine> wallLines = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            wallLines.add(new WallLine(tileTypes, null, null));
        }

        wallLines.get(0).setLineDown(wallLines.get(1));
        wallLines.get(1).setLineUp(wallLines.get(0));
        wallLines.get(1).setLineDown(wallLines.get(2));
        wallLines.get(2).setLineUp(wallLines.get(1));
        wallLines.get(2).setLineDown(wallLines.get(3));
        wallLines.get(3).setLineUp(wallLines.get(2));
        wallLines.get(3).setLineDown(wallLines.get(4));
        wallLines.get(4).setLineUp(wallLines.get(3));

        ArrayList<PatternLine> patternLines = new ArrayList<>();
        for (int j = 0; j < 5; j++){
            patternLines.add(new PatternLine(j+1, wallLines.get(j), floor));
        }

        Board board = new Board(patternLines, wallLines, floor, new Points(0));

        //neexistujuci destinationIdx
        ArrayList<Tile> tiles = new ArrayList<>(List.of(Tile.RED));
        board.put(10, tiles);
        assertEquals("R", floor.state());

        //existujuci destinationIdx
        tiles = new ArrayList<>(List.of(Tile.YELLOW, Tile.YELLOW));
        board.put(1, tiles);
        assertEquals("PatternLine:\ncapacity: 2\nI\nI\n", patternLines.get(1).state());

        //koniec kola - nie hry
        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        assertEquals(new Points(0), board.getPoints());
        assertEquals("Points: 0 p" +
                "\nPattern Lines:\n " +
                "PatternLine:\ncapacity: 1\n\n" +
                "PatternLine:\ncapacity: 2\n\n" +
                "PatternLine:\ncapacity: 3\n\n" +
                "PatternLine:\ncapacity: 4\n\n" +
                "PatternLine:\ncapacity: 5\n\n" +
                "Wall:\n " +
                "-----\n" +
                "--I--\n" +
                "-----\n" +
                "-----\n" +
                "-----\n" +
                "Floor:\n", board.state());

        //koniec hry
        tiles = new ArrayList<>(List.of(Tile.RED, Tile.RED));
        board.put(1, tiles);
        board.finishRound();
        tiles = new ArrayList<>(List.of(Tile.GREEN, Tile.GREEN));
        board.put(1, tiles);
        board.finishRound();
        tiles = new ArrayList<>(List.of(Tile.BLUE, Tile.BLUE));
        board.put(1, tiles);
        board.finishRound();
        tiles = new ArrayList<>(List.of(Tile.BLACK, Tile.BLACK));
        board.put(1, tiles);
        assertEquals(FinishRoundResult.GAME_FINISHED, board.finishRound());

        board.endGame();
        assertEquals(new Points(25), board.getPoints());
    }
}