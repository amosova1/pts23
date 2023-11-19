package sk.uniba.fmph.dcs;

import java.util.*;

public class Board{
    private Points points;
    private List<List<Tile>> boardRows;
    private List<Integer> actualTilesInRow;
    private Floor bin;
    private WallLine[] wall;
    public Board() {
        points = new Points(0);
        boardRows = new ArrayList<>();
        actualTilesInRow = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            boardRows.add(new ArrayList<>(i));
            actualTilesInRow.add(0);
        }
        createWallAndFloor();
    }

    public void createWallAndFloor() {
        ArrayList<Tile> types = new ArrayList<>();
        types.add(Tile.BLUE);
        types.add(Tile.YELLOW);
        types.add(Tile.RED);
        types.add(Tile.GREEN);
        types.add(Tile.BLACK);
        wall = new WallLine[5];
        wall[0] = new WallLine(types, wall[1], null);
        wall[1] = new WallLine(types, wall[2], wall[0]);
        wall[2] = new WallLine(types, wall[3], wall[1]);
        wall[3] = new WallLine(types, wall[4], wall[2]);
        wall[4] = new WallLine(types, null, wall[3]);
        ArrayList<Points> pointPattern = new ArrayList<>();
        pointPattern.add(new Points(-1));
        pointPattern.add(new Points(-1));
        pointPattern.add(new Points(-2));
        pointPattern.add(new Points(-2));
        pointPattern.add(new Points(-2));
        pointPattern.add(new Points(-3));
        pointPattern.add(new Points(-3));
        bin = new Floor((UsedTilesGiveInterface) new UsedTyles(), pointPattern);
    }

    public void put(int destinationIdx, Tile[] tyles){
        if(destinationIdx < 0 || destinationIdx > boardRows.size())throw new InputMismatchException();
        //insert selected tiles into the board
        for(Tile tile : tyles){
            if(tile == Tile.STARTING_PLAYER){
                bin.put(Collections.singleton(tile));
            }
            else if(boardRows.get(destinationIdx).size() <= destinationIdx){
                boardRows.get(destinationIdx).add(tile);
            }
            else bin.put(Collections.singleton(tile));
        }

    }
    public FinishRoundResult finishRound(){
        //adds tiles to wall and adds points
        for (int i = 0; i < 5; i++) {
            if(boardRows.get(i).size() > i){
                if(wall[i].canPutTile(boardRows.get(i).get(0))){
                    Points add = wall[i].putTile(boardRows.get(i).get(0));
                    points = new Points(add.getValue() + points.getValue());
                    boardRows.set(i, new ArrayList<>());
                }
            }
        }
        //substract tiles from floor
        int tempResult = points.getValue() + bin.finishRound().getValue();
        points = new Points(tempResult>0?tempResult:0);
        //if a row is full, end the game
        for (int i = 0; i < 5; i++) {
            if(wall[i].getTiles().size() == 5){
                endGame();
                return FinishRoundResult.GAME_FINISHED;
            }
        }
        return FinishRoundResult.NORMAL;
    }

    public void endGame(){
        FinalPointsCalculation finalPointCalculation = new FinalPointsCalculation();
        Optional<Tile>[][] migratedWall = new Optional[5][5];
        for(int i = 0; i < 5; i++){
            migratedWall[0] = wall[0].getTiles().toArray(new Optional[0]);
        }
        points = new Points(finalPointCalculation.getPoints(migratedWall).getValue() + points.getValue());
        new GameFinished();
    }

    public String state(){
        String toReturn = "";
        for (List<Tile> row : boardRows ){
            for (final Tile tile : row) {
                toReturn += tile.toString();
            }
            toReturn += " ";
        }
        toReturn += bin.state();
        return toReturn;
    }

    public static void main(String[] args) {
        Board board =  new Board();
        Tile[] tiles = new Tile[3];
        tiles[1] = Tile.STARTING_PLAYER;
        tiles[0] = Tile.RED;
        tiles[2] = Tile.RED;
        board.put(0, tiles );
        board.put(1, tiles );
        board.put(2, tiles );
        board.put(2, tiles );
        String val = board.state();
        System.out.println(val);
        board.finishRound();
    }

}
