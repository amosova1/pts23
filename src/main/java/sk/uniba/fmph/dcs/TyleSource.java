package sk.uniba.fmph.dcs;

public interface TyleSource {
    Tile[] take(int idx);
    boolean isEmpty();
    void startNewRound();
    String state();
}