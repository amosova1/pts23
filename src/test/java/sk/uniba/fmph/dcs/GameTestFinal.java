package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTestFinal {
    public static class FakeBagGame implements BagInteface{
        private final ArrayList<Tile> _tiles;
        private final UsedTilesGiveInterface usedTyles;
        public FakeBagGame(UsedTilesGiveInterface usedTyles){
            this.usedTyles = usedTyles;
            _tiles = new ArrayList<>();

            _tiles.addAll(List.of(Tile.GREEN, Tile.RED, Tile.RED, Tile.BLUE, Tile.GREEN, Tile.RED, Tile.RED, Tile.BLUE));
        }

        @Override
        public Pair take(int count) {
            ArrayList<Tile> remainingTiles = new ArrayList<>(this._tiles);
            ArrayList<Tile> selectedTiles = new ArrayList<>();

            for (int i = 0; i < count; i++){
                int k = new Random().nextInt(_tiles.size());
                selectedTiles.add(this._tiles.get(k));
                remainingTiles.remove(k);
            }
            return new Pair(new FakeBag(usedTyles), selectedTiles);
        }

        @Override
        public String state(){
            return "";
        }
    }
    public Game makeGame() {
        FakeTableCenter tableCenter = new FakeTableCenter();
        UsedTilesGiveInterface usedTyles = new FakeUsedTyles();
        FakeBagGame bag = new FakeBagGame(usedTyles);
        ArrayList<FactoryInterface> factories = new ArrayList<>();
        ArrayList<BoardInterface> boards = new ArrayList<>();

        FakeFloor floor = new FakeFloor();

        ArrayList<Tile> tileTypes = new ArrayList<>();

        for (int i = 0; i < 2; i++){
            FactoryInterface factory = new FakeFactory(bag);
            BoardInterface board = new FakeBoard();
            factories.add(factory);
            boards.add(board);
        }

        TableAreaInterface tableArea= new FakeTableArea(tableCenter, factories);
        tableArea.startNewRound();
        ObserverInterface ob = new FakeObserver();

        return new Game(tableArea, boards, ob);
    }

    @Test
    public void take_correctFirstPlayer(){
        Game game = makeGame();

        assertTrue(game.take(0, 1, 0, 0));
        assertEquals(0, (int) game.getExitCode());
    }

    @Test
    public void take_correctNotFirstPlayer(){
        Game game = makeGame();

        assertTrue(game.take(0, 1, 0, 0));
        assertEquals(0, (int) game.getExitCode());
        assertTrue(game.take(1, 2, 0, 0));
        assertEquals(0, (int) game.getExitCode());
    }

    @Test
    public void take_incorrectFirstPlayer(){
        Game game = makeGame();

        assertFalse(game.take(1, 1, 0, 0));
        assertEquals(1, (int) game.getExitCode());
    }

    @Test
    public void take_incorrectNotFirstPlayer(){
        Game game = makeGame();

        game.take(0, 1, 0, 0);
        System.out.println(game.getExitCode());
        assertEquals(0, (int) game.getExitCode());
        assertFalse(game.take(0, 2, 0, 0));
        assertEquals(1, (int) game.getExitCode());
    }

    @Test
    public void take_incorrectPlayerId(){
        Game game = makeGame();

        assertFalse(game.take(10, 1, 0, 0));
        assertEquals(2, (int) game.getExitCode());
    }

    @Test
    public void take_incorrectSourceId(){
        Game game = makeGame();

        assertFalse(game.take(0, 10, 0, 0));
        assertEquals(3, (int) game.getExitCode());
    }

    @Test
    public void take_incorrectIdx(){
        Game game = makeGame();

        assertFalse(game.take(0, 1, 10, 0));
        assertEquals(3, (int) game.getExitCode());
    }

    @Test
    public void take_incorrectDestinationIdx(){
        Game game = makeGame();

        assertTrue(game.take(0, 1, 0, 10));
        assertEquals(1, game.getCurrentPlayer());
    }

    @Test
    public void take_emptyFactory(){
        Game game = makeGame();

        assertTrue(game.take(0, 1, 0, 0));
        assertEquals(0, (int) game.getExitCode());
        assertFalse(game.take(1, 1, 0, 0));
        assertEquals(3, (int) game.getExitCode());
    }

    @Test
    public void take_emptyTableCenter(){
        Game game = makeGame();

        assertFalse(game.take(0, 0, 1, 0));
        assertEquals(3, (int) game.getExitCode());
    }
}