package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTestFinal {
    public static class FakeBagGame implements BagInteface{
        private final ArrayList<Tile> _tiles;
        private final UsedTyles usedTyles_instance;
        public FakeBagGame(){
            this.usedTyles_instance = UsedTyles.getInstance();
            _tiles = new ArrayList<>();

            _tiles.addAll(List.of(Tile.GREEN, Tile.RED, Tile.RED, Tile.BLUE, Tile.GREEN, Tile.RED, Tile.RED, Tile.BLUE));
        }

        @Override
        public ArrayList<Tile> take(int count) {
            if (this._tiles.isEmpty() || this._tiles.size() < count){
                refill();
            }

            ArrayList<Tile> vyber = new ArrayList<>();
            for (int i = 0; i < count; i++){
                vyber.add(this._tiles.get(i));
            }
            if (count > 0) {
                this._tiles.subList(0, count).clear();
            }
            return vyber;
        }

        @Override
        public String state(){
            StringBuilder ans = new StringBuilder();
            ans.append("Bag:\n");
            for (Tile ts: this._tiles) {
                ans.append(ts.toString()).append("\n");
            }
            return ans.toString();
        }

        private void refill(){
            ArrayList<Tile> tiles = usedTyles_instance.takeAll();
            tiles.remove(Tile.STARTING_PLAYER);

            this._tiles.addAll(tiles);
        }
    }
    public Game makeGame() {
        FakeTableCenter tableCenter = new FakeTableCenter();
        UsedTilesGiveInterface usedTyles = new FakeUsedTyles();
        FakeBagGame bag = new FakeBagGame();
        ArrayList<TyleSource> factories = new ArrayList<>();
        ArrayList<BoardInterface> boards = new ArrayList<>();

        ArrayList<Points> pointPattern = new ArrayList<>();
        pointPattern.add(new Points(-1));
        pointPattern.add(new Points(-1));
        pointPattern.add(new Points(-2));
        pointPattern.add(new Points(-2));
        pointPattern.add(new Points(-2));
        pointPattern.add(new Points(-3));
        pointPattern.add(new Points(-3));

        FakeFloor floor = new FakeFloor(usedTyles, pointPattern);

        ArrayList<Tile> tileTypes = new ArrayList<>();
        tileTypes.add(Tile.RED);
        tileTypes.add(Tile.GREEN);
        tileTypes.add(Tile.YELLOW);
        tileTypes.add(Tile.BLUE);
        tileTypes.add(Tile.BLACK);
        ArrayList<FakeWallLine> wallLines = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            wallLines.add(new FakeWallLine(tileTypes, null, null));
        }

        wallLines.get(0).setLineDown(wallLines.get(1));
        wallLines.get(1).setLineDown(wallLines.get(2));
        wallLines.get(2).setLineDown(wallLines.get(3));
        wallLines.get(3).setLineDown(wallLines.get(4));

        wallLines.get(1).setLineUp(wallLines.get(0));
        wallLines.get(2).setLineUp(wallLines.get(1));
        wallLines.get(3).setLineUp(wallLines.get(2));
        wallLines.get(4).setLineUp(wallLines.get(3));

        ArrayList<PatternLineInterface> patternLines = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            patternLines.add(new PatternLine(i+1, wallLines.get(i), floor));
        }

        for (int i = 0; i < 2; i++){
            TyleSource factory = new FakeFactory(bag);
            BoardInterface board = new FakeBoard(patternLines, wallLines, floor, new Points(0));
            factories.add(factory);
            boards.add(board);
        }

        TableAreaInterface tableArea= new FakeTableArea(tableCenter, factories);
        tableArea.startNewRound();
        ObserverInterface ob = FakeObserver.getInstance();


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
        String expected = "Floor:\nG\n";
        assertTrue(game.state().contains(expected));

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