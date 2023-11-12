package sk.uniba.fmph.dcs;

public interface GameInterface {
    Boolean take(Integer playerId, Integer sourceId, Integer idx, Integer destinationIdx);
}
