package sk.uniba.fmph.dcs;

import java.util.Optional;
import java.util.List;
public class GameFinished {

    public static FinishRoundResult gameFinished(List<WallLine> wall){
        for(WallLine row : wall){
            boolean rowIncomplete = false;
            for(Optional<Tile> tile : row.getTiles()){
                if(tile.isEmpty()){
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
