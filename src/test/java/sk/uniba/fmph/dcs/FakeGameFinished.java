package sk.uniba.fmph.dcs;

import java.util.List;
import java.util.Optional;

public class FakeGameFinished {
    public static FinishRoundResult gameFinished(List<FakeWallLine> wall){
        for(FakeWallLine row : wall){
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
