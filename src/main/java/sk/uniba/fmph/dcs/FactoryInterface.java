package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public interface FactoryInterface {
    Pair take(int idx);
    boolean isEmpty();
    void startNewRound();
    String state();
}
