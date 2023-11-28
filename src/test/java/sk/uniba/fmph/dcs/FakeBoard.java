package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public class FakeBoard implements BoardInterface{

    public FakeBoard(){
    }

    @Override
    public void put(int destinationIdx, ArrayList<Tile> tyles){
    }

    @Override
    public FinishRoundResult finishRound(){

        return null;
    }

    @Override
    public void endGame(){

    }

    @Override
    public String state(){
        return "";
    }

    @Override
    public Points getPoints(){
        return null;
    }
}
