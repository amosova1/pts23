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

    }

    public Integer getExitCode(){
        return this.exitCode;
    }

    @Override
    public Boolean take(Integer playerId, Integer sourceId, Integer idx, Integer destinationIdx) {
        if (playerId < 0 || playerId >= boards.size()){
            this.exitCode = 2;
            return false;
        }

        if (!Objects.equals(this.currentPlayer, playerId)){
            this.exitCode = 1;
            return false;
        }

        ArrayList<Tile> vybrane = this.tableArea_instance.take(sourceId, idx);

        if (vybrane.contains(Tile.STARTING_PLAYER)){
            this.startingPlayer = playerId;
        }

        if (vybrane.isEmpty()){
            this.exitCode = 3;
            return false;
        }

        this.boards.get(playerId).put(destinationIdx, vybrane);

        this.currentPlayer = (this.currentPlayer + 1) % this.boards.size();

        boolean check = true;
        if (this.tableArea_instance.isRoundEnd()){
            System.out.println("Koniec kola");

            for (BoardInterface board : this.boards) {
                FinishRoundResult r = board.finishRound();

                if (r == FinishRoundResult.GAME_FINISHED) {
                    this.exitCode = 4;
                    check = false;
                }
            }

            this.tableArea_instance.startNewRound();
            this.currentPlayer = this.startingPlayer;
        }

        return check;
    }

    public int getCurrentPlayer(){
        return this.currentPlayer;
    }

    public String state(){
        StringBuilder ans = new StringBuilder();
        ans.append("Game:\n");
        for (int i = 0; i < this.boards.size(); i++){
            ans.append("Player ").append(i).append(":\n");
            ans.append(this.boards.get(i).state()).append("\n");
        }
        ans.append(this.tableArea_instance.state());
        return ans.toString();
    }
}
