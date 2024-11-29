package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public interface FactoryInterface {
    Triple take(int idx);
    boolean isEmpty();
    Triple startNewRound();
    String state();
}
