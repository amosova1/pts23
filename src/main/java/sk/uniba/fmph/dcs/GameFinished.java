package sk.uniba.fmph.dcs;

import java.util.Optional;

import sk.uniba.fmph.dcs.FinishRoundResult;

public class GameFinished {

    public static FinishRoundResult gameFinished(List<WallLine> wall){
        for(WallLine row : wall){
            boolean rowIncomplete = false;
            for(Optional<Tile> tile : row.getTiles()){
                if(!tile.isPresent()){
                    rowIncomplete = true;
                    break;
                }
            }
            if(!rowIncomplete){
                return FinishRoundResult.GAME_FINISHED;
            }
        }
        return FinishRoundResult.NORMAL;
    }

}
