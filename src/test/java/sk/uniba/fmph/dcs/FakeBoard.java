package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public class FakeBoard implements BoardInterface{
    private Points points;
    private final ArrayList<PatternLineInterface> patternLines;
    private final FakeFloor bin;
    private final ArrayList<FakeWallLine> wall;
    public FakeBoard(ArrayList<PatternLineInterface> patternLines, ArrayList<FakeWallLine> wall, FakeFloor floor, Points points) {
        this.patternLines = patternLines;
        this.wall = wall;
        this.bin = floor;
        this.points = points;
    }

    @Override
    public void put(int destinationIdx, ArrayList<Tile> tyles){
        if(destinationIdx < 0 || destinationIdx >= patternLines.size()) {
            bin.put(tyles);
        } else {
            //insert selected tiles into the selected row of pattern
            patternLines.get(destinationIdx).put(tyles);
        }
    }

    @Override
    public FinishRoundResult finishRound(){
        //adds tiles to wall and adds points
        for (int i = 0; i < 5; i++) {
            points.addPoints(patternLines.get(i).finishRound());
            //points = new Points(patternLines.get(i).finishRound().getValue()+points.getValue());
        }
        //substract points from floor
        points.addPoints(bin.finishRound());

        return FakeGameFinished.gameFinished(wall);
    }

    @Override
    public void endGame(){
        FinalPointsCalculation finalPointCalculation = new FinalPointsCalculation();
        points.addPoints(FakeFinalPointsCalculation.getPoints(wall));
        //points = new Points(FinalPointsCalculation.getPoints(wall).getValue()+ points.getValue());
        new GameFinished();
    }

    @Override
    public String state(){
        String toReturn = "Points: " + points.getValue() + " p\n";
        toReturn += "Pattern Lines:\n ";
        for (PatternLineInterface row : patternLines ){
            toReturn += row.state() + "\n";
        }
        toReturn += "Wall:\n ";
        for (FakeWallLine line : wall){
            toReturn += line.state() + "\n";
        }
        toReturn += "Floor:\n";
        if (!bin.state().equals("")){
            toReturn += bin.state() + "\n";
        }
        return toReturn;
    }

    @Override
    public Points getPoints(){
        return points;
    }
}
