package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public interface BoardInterface {
    void put(int destinationIdx, ArrayList<Tile> tyles);
    FinishRoundResult finishRound();
    void endGame();
    String state();
    Points getPoints();
}
