package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Objects;

public class Game implements GameInterface{
    private final TableAreaInterface tableArea_instance;
    private final ArrayList<BoardInterface> boards;
    private final ObserverInterface gameObserver;
    private Integer startingPlayer;
    private Integer currentPlayer;
    private Integer exitCode;

    public Game(TableAreaInterface tableArea_instance, ArrayList<BoardInterface> boards, ObserverInterface gameObserver){
        this.tableArea_instance = tableArea_instance;
        this.boards = boards;
        this.gameObserver = gameObserver;
        this.startingPlayer = 0;
        this.currentPlayer = 0;
        this.exitCode = 0;

        this.gameObserver.notify("Inicializovana hra");
    }

    public Integer getExitCode(){
        return this.exitCode;
    }

    @Override
    public Boolean take(Integer playerId, Integer sourceId, Integer idx, Integer destinationIdx) {
        if (playerId < 0 || playerId >= boards.size()){
            this.gameObserver.notify("Neexistujuci hrac");
            this.exitCode = 2;
            return false;
        }

        if (!Objects.equals(this.currentPlayer, playerId)){
            this.gameObserver.notify("Nie si na tahu");
            this.exitCode = 1;
            return false;
        }

        ArrayList<Tile> vybrane = this.tableArea_instance.take(sourceId, idx);

        if (vybrane.contains(Tile.STARTING_PLAYER)){
            this.startingPlayer = playerId;
        }

        if (vybrane.isEmpty()){
            this.gameObserver.notify("Vybral z prazdneho miesta");
            this.exitCode = 3;
            return false;
        }

        this.boards.get(playerId).put(destinationIdx, vybrane);

        this.currentPlayer = (this.currentPlayer + 1) % this.boards.size();

        boolean check = true;
        if (this.tableArea_instance.isRoundEnd()){
            this.gameObserver.notify("Koniec kola");

            for (BoardInterface board : this.boards) {
                this.gameObserver.notify("Hrac" + this.boards.indexOf(board) + ": ");
                this.gameObserver.notify(board.state());
                FinishRoundResult r = board.finishRound();

                if (r == FinishRoundResult.GAME_FINISHED) {
                    this.exitCode = 4;
                    check = false;
                }
            }

            this.tableArea_instance.startNewRound();
            this.currentPlayer = this.startingPlayer;
        }

        if (!check){
            this.gameObserver.notify("Koniec hry");
            for (BoardInterface board : this.boards) {
                this.gameObserver.notify(board.state());
            }
        }

        return check;
    }

    public int getCurrentPlayer(){
        return this.currentPlayer;
    }
}
