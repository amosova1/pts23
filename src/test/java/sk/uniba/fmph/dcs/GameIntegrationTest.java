package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

public class GameIntegrationTest {
    public static class FakeBag2 implements BagInteface{
        private ArrayList<Tile> _tiles;
        private UsedTyles usedTyles_instance;
        private FakeBag2(){
            this.usedTyles_instance = UsedTyles.getInstance();
            _tiles = new ArrayList<>();

            _tiles.addAll(List.of(Tile.GREEN, Tile.RED, Tile.RED, Tile.BLUE,
                    Tile.YELLOW, Tile.BLACK, Tile.GREEN, Tile.BLUE,
                    Tile.GREEN, Tile.RED, Tile.RED, Tile.BLUE,
                    Tile.YELLOW, Tile.BLACK, Tile.GREEN, Tile.BLUE
                    ));
        }

        private static class BagHolder {
            private static final FakeBag2 INSTANCE = new FakeBag2();
        }

        public static FakeBag2 getInstance() {
            return FakeBag2.BagHolder.INSTANCE;
        }

        public ArrayList<Tile> take(int count) {
            if (this._tiles.isEmpty() || this._tiles.size() < count){
                //System.out.println("Bag is empty, refilling");
                this.refill();
                //System.out.println(this._tiles.size());
            }

            ArrayList<Tile> vyber = new ArrayList<>();
            for (int i = 0; i < count; i++){
                vyber.add(this._tiles.get(i));
            }
            for (int i = 0; i < count; i++){
                this._tiles.remove(0);
            }
            return vyber;
        }

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

    @Test
    public void testGame() {
        GameObserver go = GameObserver.getInstance();
        MyObserver myObserver = new MyObserver();
        go.registerObserver(myObserver);
        TableCenter tableCenter_instance = TableCenter.getInstance();
        UsedTyles usedTyles_instance = UsedTyles.getInstance();
        FakeBag2 bag_instance = FakeBag2.getInstance();

        ArrayList<Points> pointPattern = new ArrayList<>();
        pointPattern.add(new Points(-1));
        pointPattern.add(new Points(-1));
        pointPattern.add(new Points(-2));
        pointPattern.add(new Points(-2));
        pointPattern.add(new Points(-2));
        pointPattern.add(new Points(-3));
        pointPattern.add(new Points(-3));
        ArrayList<ObserverInterface> poz = new ArrayList<>();

        int pocetHracov = 2;
        int pocetFactories = 2;

        ArrayList<BoardInterface> boards = new ArrayList<>();
        ArrayList<Floor> floors = new ArrayList<>();
        for (int i = 0; i < pocetHracov; i++){
            ArrayList<Tile> tileTypes = new ArrayList<>();
            tileTypes.add(Tile.RED);
            tileTypes.add(Tile.GREEN);
            tileTypes.add(Tile.YELLOW);
            tileTypes.add(Tile.BLUE);
            tileTypes.add(Tile.BLACK);
            ArrayList<WallLine> wallLines = new ArrayList<>();
            for (int j = 0; j < 5; j++){
                wallLines.add(new WallLine(tileTypes, null, null));
            }

            Floor floor_instance = new Floor(usedTyles_instance, pointPattern);
            floors.add(floor_instance);

            ArrayList<PatternLine> patternLines = new ArrayList<>();
            for (int j = 0; j < 5; j++){
                patternLines.add(new PatternLine(j+1, wallLines.get(j), floor_instance));
            }
            Board board = new Board(patternLines, wallLines, floor_instance, new Points(0));
            boards.add(board);


        }

        ArrayList<Factory> factories = new ArrayList<>();
        for (int i = 0; i < pocetFactories; i++){
            Factory f = new Factory(bag_instance, tableCenter_instance);
            factories.add(f);
        }

        TableArea tableArea_instance = new TableArea(tableCenter_instance, factories);
        tableArea_instance.startNewRound();

        Game game = new Game(tableArea_instance, boards, go);

        int startingPlayer = 0;

        //inicilizovane boardy
        for (int i = 0; i < pocetHracov; i++){
            String expected = "Points: 0 p\n" +
                    "Pattern Lines:\n" +
                    " PatternLine:\n" + "capacity: 1\n" + "\n" +
                    "PatternLine:\n" + "capacity: 2\n" + "\n" +
                    "PatternLine:\n" + "capacity: 3\n" + "\n" +
                    "PatternLine:\n" + "capacity: 4\n" + "\n" +
                    "PatternLine:\n" + "capacity: 5\n" + "\n" +
                    "Wall:\n" + " -----\n" + "-----\n" + "-----\n" + "-----\n" + "-----\n" +
                    "Floor:\n";
            assertEquals(expected, boards.get(i).state());
        }

        //neexistujuci hrac
        assertFalse(game.take(-1, 0, 0, 0));
        assertEquals(2, game.getExitCode().intValue());

        //nespravny prvy hrac
        assertFalse(game.take(1, 0, 0, 0));
        assertEquals(1, game.getExitCode().intValue());

        //neexistujuci sourceId
        assertFalse(game.take(0, 10, 0, 0));
        assertEquals(3, game.getExitCode().intValue());

        //neexistujuci destinationIdx
        assertTrue(game.take(0, 1, 0, 10));
        assertEquals("G", floors.get(0).state());
        assertEquals("Table Center:\n" + "S\n" + "R\n" + "R\n" + "B\n", tableCenter_instance.state());
        assertEquals("Factory:\n", factories.get(0).state());

        //neexistujuci idx
        assertFalse(game.take(1, 0, 10, 0));
        assertEquals(3, game.getExitCode().intValue());


        //1 opakuje tah
        assertTrue(game.take(1, 0, 1, 1));
        startingPlayer = 1; // prvy berie z tablecenter
        assertEquals("Table Center:\n" + "B\n", tableCenter_instance.state());
        assertEquals("Factory:\n", factories.get(0).state());
        assertEquals("Factory:\n" + "I\n" + "L\n" + "G\n" + "B\n", factories.get(1).state());
        assertEquals("Points: 0 p\n" +
                "Pattern Lines:\n" +
                " PatternLine:\n" + "capacity: 1\n" + "\n" +
                "PatternLine:\n" + "capacity: 2\n" + "R\n" + "R\n" + "\n" +
                "PatternLine:\n" + "capacity: 3\n" + "\n" +
                "PatternLine:\n" + "capacity: 4\n" + "\n" +
                "PatternLine:\n" + "capacity: 5\n" + "\n" +
                "Wall:\n" + " -----\n" + "-----\n" + "-----\n" + "-----\n" + "-----\n" +
                "Floor:\n" + "S\n", boards.get(1).state());

        assertTrue(game.take(0, 0, 0, 0));
        assertEquals("Table Center:\n", tableCenter_instance.state());
        assertEquals("Factory:\n", factories.get(0).state());
        assertEquals("Factory:\n" + "I\n" + "L\n" + "G\n" + "B\n", factories.get(1).state());
        assertEquals("Points: 0 p\n" +
                "Pattern Lines:\n" +
                " PatternLine:\n" + "capacity: 1\n" + "B\n" + "\n" +
                "PatternLine:\n" + "capacity: 2\n" + "\n" +
                "PatternLine:\n" + "capacity: 3\n" + "\n" +
                "PatternLine:\n" + "capacity: 4\n" + "\n" +
                "PatternLine:\n" + "capacity: 5\n" + "\n" +
                "Wall:\n" + " -----\n" + "-----\n" + "-----\n" + "-----\n" + "-----\n" +
                "Floor:\n" + "G\n", boards.get(0).state());

        assertTrue(game.take(1, 2, 1, 0));
        assertEquals("Table Center:\n" + "I\n" + "G\n" + "B\n", tableCenter_instance.state());
        assertEquals("Factory:\n", factories.get(0).state());
        assertEquals("Factory:\n" , factories.get(1).state());
        assertEquals("Points: 0 p\n" +
                "Pattern Lines:\n" +
                " PatternLine:\n" + "capacity: 1\n" + "L\n" + "\n" +
                "PatternLine:\n" + "capacity: 2\n" + "R\n" + "R\n" + "\n" +
                "PatternLine:\n" + "capacity: 3\n" + "\n" +
                "PatternLine:\n" + "capacity: 4\n" + "\n" +
                "PatternLine:\n" + "capacity: 5\n" + "\n" +
                "Wall:\n" + " -----\n" + "-----\n" + "-----\n" + "-----\n" + "-----\n" +
                "Floor:\n" + "S\n", boards.get(1).state());

        assertTrue(game.take(0, 0, 0, 1));
        assertEquals("Table Center:\n" + "G\n" + "B\n", tableCenter_instance.state());
        assertEquals("Factory:\n", factories.get(0).state());
        assertEquals("Factory:\n", factories.get(1).state());
        assertEquals("Points: 0 p\n" +
                "Pattern Lines:\n" +
                " PatternLine:\n" + "capacity: 1\n" + "B\n" + "\n" +
                "PatternLine:\n" + "capacity: 2\n" + "I\n" + "\n" +
                "PatternLine:\n" + "capacity: 3\n" + "\n" +
                "PatternLine:\n" + "capacity: 4\n" + "\n" +
                "PatternLine:\n" + "capacity: 5\n" + "\n" +
                "Wall:\n" + " -----\n" + "-----\n" + "-----\n" + "-----\n" + "-----\n" +
                "Floor:\n" + "G\n", boards.get(0).state());

        assertTrue(game.take(1, 0, 0, 2));
        assertEquals("Table Center:\n" + "B\n", tableCenter_instance.state());
        assertEquals("Factory:\n", factories.get(0).state());
        assertEquals("Factory:\n" , factories.get(1).state());
        assertEquals("Points: 0 p\n" +
                "Pattern Lines:\n" +
                " PatternLine:\n" + "capacity: 1\n" + "L\n" + "\n" +
                "PatternLine:\n" + "capacity: 2\n" + "R\n" + "R\n" + "\n" +
                "PatternLine:\n" + "capacity: 3\n" + "G\n" + "\n" +
                "PatternLine:\n" + "capacity: 4\n" + "\n" +
                "PatternLine:\n" + "capacity: 5\n" + "\n" +
                "Wall:\n" + " -----\n" + "-----\n" + "-----\n" + "-----\n" + "-----\n" +
                "Floor:\n" + "S\n", boards.get(1).state());

        assertTrue(game.take(0, 0, 0, 2));
        //koniec kola
        //hrac 0
        assertEquals("Points: 0 p\n" +
                "Pattern Lines:\n" +
                " PatternLine:\n" + "capacity: 1\n" + "\n" +
                "PatternLine:\n" + "capacity: 2\n" + "I\n" + "\n" +
                "PatternLine:\n" + "capacity: 3\n" + "B\n" + "\n" +
                "PatternLine:\n" + "capacity: 4\n" + "\n" +
                "PatternLine:\n" + "capacity: 5\n" + "\n" +
                "Wall:\n" + " ---B-\n" + "-----\n" + "-----\n" + "-----\n" + "-----\n" +
                "Floor:\n", boards.get(0).state());
        //hrac 1
        assertEquals("Points: 1 p\n" +
                "Pattern Lines:\n" +
                " PatternLine:\n" + "capacity: 1\n" + "\n" +
                "PatternLine:\n" + "capacity: 2\n" + "\n" +
                "PatternLine:\n" + "capacity: 3\n" + "G\n" + "\n" +
                "PatternLine:\n" + "capacity: 4\n" + "\n" +
                "PatternLine:\n" + "capacity: 5\n" + "\n" +
                "Wall:\n" + " ----L\n" + "R----\n" + "-----\n" + "-----\n" + "-----\n" +
                "Floor:\n", boards.get(1).state());

        //nove kolo
        assertEquals("Table Center:\n" + "S\n", tableCenter_instance.state());
        assertEquals("Factory:\n" + "G\n" + "R\n" + "R\n" + "B\n", factories.get(0).state());
        assertEquals("Factory:\n" + "I\n" + "L\n" + "G\n" + "B\n", factories.get(1).state());
        assertEquals("UsedTyles:\n" + "B\n" + "G\n" + "L\n" + "R\n" + "R\n", usedTyles_instance.state());
        assertEquals("Bag:\n", bag_instance.state());

        //startingPlayer = 1

        assertTrue(game.take(1, 1, 3, 0));
        assertEquals("Table Center:\n" + "S\n" + "G\n" + "R\n" + "R\n", tableCenter_instance.state());
        assertEquals("Factory:\n", factories.get(0).state());
        assertEquals("Factory:\n" + "I\n" + "L\n" + "G\n" + "B\n" , factories.get(1).state());
        assertEquals("Points: 1 p\n" +
                "Pattern Lines:\n" +
                " PatternLine:\n" + "capacity: 1\n" + "B\n" + "\n" +
                "PatternLine:\n" + "capacity: 2\n" + "\n" +
                "PatternLine:\n" + "capacity: 3\n" + "G\n" + "\n" +
                "PatternLine:\n" + "capacity: 4\n" + "\n" +
                "PatternLine:\n" + "capacity: 5\n" + "\n" +
                "Wall:\n" + " ----L\n" + "R----\n" + "-----\n" + "-----\n" + "-----\n" +
                "Floor:\n", boards.get(1).state());

        assertTrue(game.take(0, 2, 0, 1));
        assertEquals("Table Center:\n" + "S\n" + "G\n" + "R\n" + "R\n" + "L\n" + "G\n" + "B\n" , tableCenter_instance.state());
        assertEquals("Factory:\n", factories.get(0).state());
        assertEquals("Factory:\n", factories.get(1).state());
        assertEquals("Points: 0 p\n" +
                "Pattern Lines:\n" +
                " PatternLine:\n" + "capacity: 1\n" + "\n" +
                "PatternLine:\n" + "capacity: 2\n" + "I\n" + "I\n" + "\n" +
                "PatternLine:\n" + "capacity: 3\n" + "B\n" + "\n" +
                "PatternLine:\n" + "capacity: 4\n" + "\n" +
                "PatternLine:\n" + "capacity: 5\n" + "\n" +
                "Wall:\n" + " ---B-\n" + "-----\n" + "-----\n" + "-----\n" + "-----\n" +
                "Floor:\n", boards.get(0).state());

        assertTrue(game.take(1, 0, 1, 2));
        assertEquals("Table Center:\n" + "R\n" + "R\n" + "L\n" + "B\n" , tableCenter_instance.state());
        assertEquals("Factory:\n", factories.get(0).state());
        assertEquals("Factory:\n", factories.get(1).state());
        assertEquals("Points: 1 p\n" +
                "Pattern Lines:\n" +
                " PatternLine:\n" + "capacity: 1\n" + "B\n" + "\n" +
                "PatternLine:\n" + "capacity: 2\n" + "\n" +
                "PatternLine:\n" + "capacity: 3\n" + "G\n" + "G\n" + "G\n" + "\n" +
                "PatternLine:\n" + "capacity: 4\n" + "\n" +
                "PatternLine:\n" + "capacity: 5\n" + "\n" +
                "Wall:\n" + " ----L\n" + "R----\n" + "-----\n" + "-----\n" + "-----\n" +
                "Floor:\n" + "S\n", boards.get(1).state());

        assertTrue(game.take(0, 0, 3, 2));
        assertEquals("Table Center:\n" + "R\n" + "R\n" + "L\n" , tableCenter_instance.state());
        assertEquals("Factory:\n", factories.get(0).state());
        assertEquals("Factory:\n", factories.get(1).state());
        assertEquals("Points: 0 p\n" +
                "Pattern Lines:\n" +
                " PatternLine:\n" + "capacity: 1\n" + "\n" +
                "PatternLine:\n" + "capacity: 2\n" + "I\n" + "I\n" + "\n" +
                "PatternLine:\n" + "capacity: 3\n" + "B\n" + "B\n" + "\n" +
                "PatternLine:\n" + "capacity: 4\n" + "\n" +
                "PatternLine:\n" + "capacity: 5\n" + "\n" +
                "Wall:\n" + " ---B-\n" + "-----\n" + "-----\n" + "-----\n" + "-----\n" +
                "Floor:\n", boards.get(0).state());

        assertTrue(game.take(1, 0, 1, 3));
        assertEquals("Table Center:\n" + "L\n" , tableCenter_instance.state());
        assertEquals("Factory:\n", factories.get(0).state());
        assertEquals("Factory:\n", factories.get(1).state());
        assertEquals("Points: 1 p\n" +
                "Pattern Lines:\n" +
                " PatternLine:\n" + "capacity: 1\n" + "B\n" + "\n" +
                "PatternLine:\n" + "capacity: 2\n" + "\n" +
                "PatternLine:\n" + "capacity: 3\n" + "G\n" + "G\n" + "G\n" + "\n" +
                "PatternLine:\n" + "capacity: 4\n" + "R\n" + "R\n" + "\n" +
                "PatternLine:\n" + "capacity: 5\n" + "\n" +
                "Wall:\n" + " ----L\n" + "R----\n" + "-----\n" + "-----\n" + "-----\n" +
                "Floor:\n" + "S\n", boards.get(1).state());

        assertTrue(game.take(0, 0, 0, 0));
        //koniec 2. kolo
        //hrac 0
        assertEquals("Points: 3 p\n" +
                "Pattern Lines:\n" +
                " PatternLine:\n" + "capacity: 1\n" + "\n" +
                "PatternLine:\n" + "capacity: 2\n" + "\n" +
                "PatternLine:\n" + "capacity: 3\n" + "B\n" + "B\n" + "\n" +
                "PatternLine:\n" + "capacity: 4\n" + "\n" +
                "PatternLine:\n" + "capacity: 5\n" + "\n" +
                "Wall:\n" + " ---BL\n" + "--I--\n" + "-----\n" + "-----\n" + "-----\n" +
                "Floor:\n", boards.get(0).state());
        //hrac 1
        assertEquals("Points: 3 p\n" +
                "Pattern Lines:\n" +
                " PatternLine:\n" + "capacity: 1\n" + "\n" +
                "PatternLine:\n" + "capacity: 2\n" + "\n" +
                "PatternLine:\n" + "capacity: 3\n" + "\n" +
                "PatternLine:\n" + "capacity: 4\n" + "R\n" + "R\n" + "\n" +
                "PatternLine:\n" + "capacity: 5\n" + "\n" +
                "Wall:\n" + " ---BL\n" + "R----\n" + "-G---\n" + "-----\n" + "-----\n" +
                "Floor:\n", boards.get(1).state());
        assertEquals("Table Center:\n" + "S\n" , tableCenter_instance.state());
        assertEquals("Factory:\n" + "B\n" + "G\n" + "L\n" + "R\n", factories.get(0).state());
        assertEquals("Factory:\n" + "R\n" + "L\n" + "I\n" + "I\n", factories.get(1).state());

        //nove kolo
        //startingPlayer = 1
        assertTrue(game.take(1, 2, 2, 0));
        assertEquals("Table Center:\n" + "S\n" + "R\n" + "L\n", tableCenter_instance.state());
        assertEquals("Factory:\n" + "B\n" + "G\n" + "L\n" + "R\n", factories.get(0).state());
        assertEquals("Factory:\n", factories.get(1).state());
        assertEquals("Points: 3 p\n" +
                "Pattern Lines:\n" +
                " PatternLine:\n" + "capacity: 1\n" + "I\n" + "\n" +
                "PatternLine:\n" + "capacity: 2\n" + "\n" +
                "PatternLine:\n" + "capacity: 3\n" + "\n" +
                "PatternLine:\n" + "capacity: 4\n" + "R\n" + "R\n" + "\n" +
                "PatternLine:\n" + "capacity: 5\n" + "\n" +
                "Wall:\n" + " ---BL\n" + "R----\n" + "-G---\n" + "-----\n" + "-----\n" +
                "Floor:\n" + "I\n", boards.get(1).state());

        assertTrue(game.take(0, 1, 0, 0));
        assertEquals("Table Center:\n" + "S\n" + "R\n" + "L\n" + "G\n" + "L\n" + "R\n", tableCenter_instance.state());
        assertEquals("Factory:\n", factories.get(0).state());
        assertEquals("Factory:\n", factories.get(1).state());
        assertEquals("Points: 3 p\n" +
                "Pattern Lines:\n" +
                " PatternLine:\n" + "capacity: 1\n" + "B\n" + "\n" +
                "PatternLine:\n" + "capacity: 2\n" + "\n" +
                "PatternLine:\n" + "capacity: 3\n" + "B\n" + "B\n" + "\n" +
                "PatternLine:\n" + "capacity: 4\n" + "\n" +
                "PatternLine:\n" + "capacity: 5\n" + "\n" +
                "Wall:\n" + " ---BL\n" + "--I--\n" + "-----\n" + "-----\n" + "-----\n" +
                "Floor:\n", boards.get(0).state());

        assertTrue(game.take(1, 0, 1, 1));
        assertEquals("Table Center:\n" + "L\n" + "G\n" + "L\n", tableCenter_instance.state());
        assertEquals("Factory:\n", factories.get(0).state());
        assertEquals("Factory:\n", factories.get(1).state());
        assertEquals("Points: 3 p\n" +
                "Pattern Lines:\n" +
                " PatternLine:\n" + "capacity: 1\n" + "I\n" + "\n" +
                "PatternLine:\n" + "capacity: 2\n" + "R\n" + "R\n" + "\n" +
                "PatternLine:\n" + "capacity: 3\n" + "\n" +
                "PatternLine:\n" + "capacity: 4\n" + "R\n" + "R\n" + "\n" +
                "PatternLine:\n" + "capacity: 5\n" + "\n" +
                "Wall:\n" + " ---BL\n" + "R----\n" + "-G---\n" + "-----\n" + "-----\n" +
                "Floor:\n" + "IS\n", boards.get(1).state());

        assertTrue(game.take(0, 0, 0, 1));
        assertEquals("Table Center:\n" + "G\n", tableCenter_instance.state());
        assertEquals("Factory:\n", factories.get(0).state());
        assertEquals("Factory:\n", factories.get(1).state());
        assertEquals("Points: 3 p\n" +
                "Pattern Lines:\n" +
                " PatternLine:\n" + "capacity: 1\n" + "B\n" + "\n" +
                "PatternLine:\n" + "capacity: 2\n" + "L\n" + "L\n" + "\n" +
                "PatternLine:\n" + "capacity: 3\n" + "B\n" + "B\n" + "\n" +
                "PatternLine:\n" + "capacity: 4\n" + "\n" +
                "PatternLine:\n" + "capacity: 5\n" + "\n" +
                "Wall:\n" + " ---BL\n" + "--I--\n" + "-----\n" + "-----\n" + "-----\n" +
                "Floor:\n", boards.get(0).state());

        assertTrue(game.take(1, 0, 0, 2));
        //koniec kola
        //hrac 0
        assertEquals("Points: 4 p\n" +
                "Pattern Lines:\n" +
                " PatternLine:\n" + "capacity: 1\n" + "\n" +
                "PatternLine:\n" + "capacity: 2\n" + "\n" +
                "PatternLine:\n" + "capacity: 3\n" + "B\n" + "B\n" + "\n" +
                "PatternLine:\n" + "capacity: 4\n" + "\n" +
                "PatternLine:\n" + "capacity: 5\n" + "\n" +
                "Wall:\n" + " ---BL\n" + "--I-L\n" + "-----\n" + "-----\n" + "-----\n" +
                "Floor:\n", boards.get(0).state());
        //hrac 1
        assertEquals("Points: 4 p\n" +
                "Pattern Lines:\n" +
                " PatternLine:\n" + "capacity: 1\n" + "\n" +
                "PatternLine:\n" + "capacity: 2\n" + "\n" +
                "PatternLine:\n" + "capacity: 3\n" + "G\n" + "\n" +
                "PatternLine:\n" + "capacity: 4\n" + "R\n" + "R\n" + "\n" +
                "PatternLine:\n" + "capacity: 5\n" + "\n" +
                "Wall:\n" + " --IBL\n" + "R----\n" + "-G---\n" + "-----\n" + "-----\n" +
                "Floor:\n", boards.get(1).state());

        assertEquals("Table Center:\n" + "S\n", tableCenter_instance.state());
        assertEquals("Factory:\n" + "B\n" + "G\n" + "G\n" + "G\n", factories.get(0).state());
        assertEquals("Factory:\n" + "B\n" + "L\n" + "L\n" + "I\n", factories.get(1).state());

        //nove kolo
        //startingPlayer = 1
        assertTrue(game.take(1, 1, 1, 0));
        assertEquals("Table Center:\n" + "S\n" + "B\n", tableCenter_instance.state());
        assertEquals("Factory:\n", factories.get(0).state());
        assertEquals("Factory:\n" + "B\n" + "L\n" + "L\n" + "I\n", factories.get(1).state());
        assertEquals("Points: 4 p\n" +
                "Pattern Lines:\n" +
                " PatternLine:\n" + "capacity: 1\n" + "G\n" + "\n" +
                "PatternLine:\n" + "capacity: 2\n" + "\n" +
                "PatternLine:\n" + "capacity: 3\n" + "G\n" + "\n" +
                "PatternLine:\n" + "capacity: 4\n" + "R\n" + "R\n" + "\n" +
                "PatternLine:\n" + "capacity: 5\n" + "\n" +
                "Wall:\n" + " --IBL\n" + "R----\n" + "-G---\n" + "-----\n" + "-----\n" +
                "Floor:\n" + "GG\n", boards.get(1).state());

        assertTrue(game.take(0, 2, 1, 3));
        assertEquals("Table Center:\n" + "S\n" + "B\n" + "B\n" + "I\n", tableCenter_instance.state());
        assertEquals("Factory:\n", factories.get(0).state());
        assertEquals("Factory:\n", factories.get(1).state());
        assertEquals("Points: 4 p\n" +
                "Pattern Lines:\n" +
                " PatternLine:\n" + "capacity: 1\n" + "\n" +
                "PatternLine:\n" + "capacity: 2\n" + "\n" +
                "PatternLine:\n" + "capacity: 3\n" + "B\n" + "B\n" + "\n" +
                "PatternLine:\n" + "capacity: 4\n" + "L\n" + "L\n" + "\n" +
                "PatternLine:\n" + "capacity: 5\n" + "\n" +
                "Wall:\n" + " ---BL\n" + "--I-L\n" + "-----\n" + "-----\n" + "-----\n" +
                "Floor:\n", boards.get(0).state());

        assertTrue(game.take(1, 0, 1, 1));
        assertEquals("Table Center:\n" + "I\n", tableCenter_instance.state());
        assertEquals("Factory:\n", factories.get(0).state());
        assertEquals("Factory:\n", factories.get(1).state());
        assertEquals("Points: 4 p\n" +
                "Pattern Lines:\n" +
                " PatternLine:\n" + "capacity: 1\n" + "G\n" + "\n" +
                "PatternLine:\n" + "capacity: 2\n" + "B\n" + "B\n" + "\n" +
                "PatternLine:\n" + "capacity: 3\n" + "G\n" + "\n" +
                "PatternLine:\n" + "capacity: 4\n" + "R\n" + "R\n" + "\n" +
                "PatternLine:\n" + "capacity: 5\n" + "\n" +
                "Wall:\n" + " --IBL\n" + "R----\n" + "-G---\n" + "-----\n" + "-----\n" +
                "Floor:\n" + "GGS\n", boards.get(1).state());

        assertTrue(game.take(0, 0, 0, 4));

        //koniec kola
        //hrac 0
        assertEquals("Points: 4 p\n" +
                "Pattern Lines:\n" +
                " PatternLine:\n" + "capacity: 1\n" + "\n" +
                "PatternLine:\n" + "capacity: 2\n" + "\n" +
                "PatternLine:\n" + "capacity: 3\n" + "B\n" + "B\n" + "\n" +
                "PatternLine:\n" + "capacity: 4\n" + "L\n" + "L\n" + "\n" +
                "PatternLine:\n" + "capacity: 5\n" + "I\n" + "\n" +
                "Wall:\n" + " ---BL\n" + "--I-L\n" + "-----\n" + "-----\n" + "-----\n" +
                "Floor:\n", boards.get(0).state());
        //hrac 1
        assertEquals("Points: 5 p\n" +
                "Pattern Lines:\n" +
                " PatternLine:\n" + "capacity: 1\n" + "\n" +
                "PatternLine:\n" + "capacity: 2\n" + "\n" +
                "PatternLine:\n" + "capacity: 3\n" + "G\n" + "\n" +
                "PatternLine:\n" + "capacity: 4\n" + "R\n" + "R\n" + "\n" +
                "PatternLine:\n" + "capacity: 5\n" + "\n" +
                "Wall:\n" + " -GIBL\n" + "R--B-\n" + "-G---\n" + "-----\n" + "-----\n" +
                "Floor:\n", boards.get(1).state());

        assertEquals("Table Center:\n" + "S\n", tableCenter_instance.state());
        assertEquals("Factory:\n" + "R\n" + "R\n" + "I\n" + "G\n", factories.get(0).state());
        assertEquals("Factory:\n" + "B\n" + "B\n" + "G\n" + "G\n", factories.get(1).state());

        //nove kolo
        //startingPlayer = 1
        assertTrue(game.take(1, 1, 1, 0));
        assertEquals("Table Center:\n" + "S\n" + "I\n" + "G\n", tableCenter_instance.state());
        assertEquals("Factory:\n", factories.get(0).state());
        assertEquals("Factory:\n" + "B\n" + "B\n" + "G\n" + "G\n", factories.get(1).state());
        assertEquals("Points: 5 p\n" +
                "Pattern Lines:\n" +
                " PatternLine:\n" + "capacity: 1\n" + "R\n" + "\n" +
                "PatternLine:\n" + "capacity: 2\n" + "\n" +
                "PatternLine:\n" + "capacity: 3\n" + "G\n" + "\n" +
                "PatternLine:\n" + "capacity: 4\n" + "R\n" + "R\n" + "\n" +
                "PatternLine:\n" + "capacity: 5\n" + "\n" +
                "Wall:\n" + " -GIBL\n" + "R--B-\n" + "-G---\n" + "-----\n" + "-----\n" +
                "Floor:\n" + "R\n", boards.get(1).state());

        assertTrue(game.take(0, 0, 2, 0));
        assertEquals("Table Center:\n" + "I\n", tableCenter_instance.state());
        assertEquals("Factory:\n", factories.get(0).state());
        assertEquals("Factory:\n" + "B\n" + "B\n" + "G\n" + "G\n", factories.get(1).state());
        assertEquals("Points: 4 p\n" +
                "Pattern Lines:\n" +
                " PatternLine:\n" + "capacity: 1\n" + "G\n" + "\n" +
                "PatternLine:\n" + "capacity: 2\n" + "\n" +
                "PatternLine:\n" + "capacity: 3\n" + "B\n" + "B\n" + "\n" +
                "PatternLine:\n" + "capacity: 4\n" + "L\n" + "L\n" + "\n" +
                "PatternLine:\n" + "capacity: 5\n" + "I\n" + "\n" +
                "Wall:\n" + " ---BL\n" + "--I-L\n" + "-----\n" + "-----\n" + "-----\n" +
                "Floor:\n" + "S\n", boards.get(0).state());

        assertTrue(game.take(1, 2, 2, 2));
        assertEquals("Table Center:\n" + "I\n" + "B\n" + "B\n", tableCenter_instance.state());
        assertEquals("Factory:\n", factories.get(0).state());
        assertEquals("Factory:\n", factories.get(1).state());
        assertEquals("Points: 5 p\n" +
                "Pattern Lines:\n" +
                " PatternLine:\n" + "capacity: 1\n" + "R\n" + "\n" +
                "PatternLine:\n" + "capacity: 2\n" + "\n" +
                "PatternLine:\n" + "capacity: 3\n" + "G\n" + "G\n" + "G\n" + "\n" +
                "PatternLine:\n" + "capacity: 4\n" + "R\n" + "R\n" + "\n" +
                "PatternLine:\n" + "capacity: 5\n" + "\n" +
                "Wall:\n" + " -GIBL\n" + "R--B-\n" + "-G---\n" + "-----\n" + "-----\n" +
                "Floor:\n" + "R\n", boards.get(1).state());

        assertTrue(game.take(0, 0, 2, 1));
        assertEquals("Table Center:\n" + "I\n", tableCenter_instance.state());
        assertEquals("Factory:\n", factories.get(0).state());
        assertEquals("Factory:\n", factories.get(1).state());
        assertEquals("Points: 4 p\n" +
                "Pattern Lines:\n" +
                " PatternLine:\n" + "capacity: 1\n" + "G\n" + "\n" +
                "PatternLine:\n" + "capacity: 2\n" + "B\n" + "B\n" + "\n" +
                "PatternLine:\n" + "capacity: 3\n" + "B\n" + "B\n" + "\n" +
                "PatternLine:\n" + "capacity: 4\n" + "L\n" + "L\n" + "\n" +
                "PatternLine:\n" + "capacity: 5\n" + "I\n" + "\n" +
                "Wall:\n" + " ---BL\n" + "--I-L\n" + "-----\n" + "-----\n" + "-----\n" +
                "Floor:\n" + "S\n", boards.get(0).state());

        assertFalse(game.take(1, 0, 0, 4));
        assertEquals(4 , game.getExitCode().intValue());
        //koniec kola + koniec hry
        //hrac 0
        assertEquals("Points: 7 p\n" +
                "Pattern Lines:\n" +
                " PatternLine:\n" + "capacity: 1\n" + "\n" +
                "PatternLine:\n" + "capacity: 2\n" + "\n" +
                "PatternLine:\n" + "capacity: 3\n" + "B\n" + "B\n" + "\n" +
                "PatternLine:\n" + "capacity: 4\n" + "L\n" + "L\n" + "\n" +
                "PatternLine:\n" + "capacity: 5\n" + "I\n" + "\n" +
                "Wall:\n" + " -G-BL\n" + "--IBL\n" + "-----\n" + "-----\n" + "-----\n" +
                "Floor:\n", boards.get(0).state());
        //hrac 1
        assertEquals("Points: 9 p\n" +
                "Pattern Lines:\n" +
                " PatternLine:\n" + "capacity: 1\n" + "\n" +
                "PatternLine:\n" + "capacity: 2\n" + "\n" +
                "PatternLine:\n" + "capacity: 3\n" + "\n" +
                "PatternLine:\n" + "capacity: 4\n" + "R\n" + "R\n" + "\n" +
                "PatternLine:\n" + "capacity: 5\n" + "I\n" + "\n" +
                "Wall:\n" + " RGIBL\n" + "R--B-\n" + "-G---\n" + "-----\n" + "-----\n" +
                "Floor:\n", boards.get(1).state());
    }
}