package sk.uniba.fmph.dcs;

public interface ObserverInterface {
    void registerObserver(ObserverInterface observer);
    void cancelObserver(ObserverInterface observer);
    void notify(String newState);
}
