package sk.uniba.fmph.dcs;

import java.util.*;

public class Board{
    private Points points;
    private final List<PatternLine> patternLines;
    private final List<Integer> currentlyInPatternLine;
    private final Floor bin;
    private final List<WallLine> wall;
    public Board(List<PatternLine> patternLines, List<WallLine> wall, Floor floor, Points points) {
        this.patternLines = patternLines;
        this.wall = wall;
        this.bin = floor;
        this.points = points;
        this.currentlyInPatternLine = new ArrayList<>();
        for (PatternLine patternLine : patternLines){
            currentlyInPatternLine.add(0);
        }

    }



    public void put(int destinationIdx, Tile[] tyles){
        if(destinationIdx < 0 || destinationIdx > patternLines.size())throw new InputMismatchException();
        //insert selected tiles into the board
        for(Tile tile : tyles){
            if(tile == Tile.STARTING_PLAYER){
                bin.put(Collections.singleton(tile));
            }
            else if(currentlyInPatternLine.get(destinationIdx) <= destinationIdx){
                patternLines.get(destinationIdx).put(Collections.singleton(tile));
                currentlyInPatternLine.set(destinationIdx, currentlyInPatternLine.get(destinationIdx)+1);
            }
            else bin.put(Collections.singleton(tile));
        }

    }
    public FinishRoundResult finishRound(){
        //adds tiles to wall and adds points
        for (int i = 0; i < 5; i++) {
            points = new Points(patternLines.get(i).finishRound().getValue()+points.getValue());
        }
        //substract points from floor
        int tempResult = points.getValue() + bin.finishRound().getValue();
        points = new Points(tempResult>0?tempResult:0);
        //if a row is full, end the game
        for (int i = 0; i < 5; i++) {
            if(wall.get(i).getTiles().size() == 5){
                //maybe game controlles this
                endGame();
                return FinishRoundResult.GAME_FINISHED;
            }
        }
        return FinishRoundResult.NORMAL;
    }

    public void endGame(){
        FinalPointsCalculation finalPointCalculation = new FinalPointsCalculation();
        points = new Points(FinalPointsCalculation.getPoints(wall).getValue()+ points.getValue());
        new GameFinished();
    }

    public String state(){
        String toReturn = "Pattern Lines: ";
        for (PatternLine row : patternLines ){
            toReturn += row.state() + " ";
        }
        toReturn += "\nWall: ";
        for (WallLine line : wall){
            toReturn += line.state() + " ";
        }
        toReturn += "\nFloor: ";
        toReturn += bin.state();
        return toReturn;
    }
}
