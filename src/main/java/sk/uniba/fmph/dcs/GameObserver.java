package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public class GameObserver implements ObserverInterface{

    private ArrayList<ObserverInterface> observers;

    private GameObserver(){
        observers = new ArrayList<>();
    }

    private static class GameObserverHolder {
        private static final GameObserver INSTANCE = new GameObserver();
    }

    public static GameObserver getInstance() {
        return GameObserverHolder.INSTANCE;
    }

    @Override
    public void registerObserver(ObserverInterface observer) {
        this.observers.add(observer);
    }

    @Override
    public void cancelObserver(ObserverInterface observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notify(String newState) {
    }

    public void notifyEverybody(String state){
        for (ObserverInterface o:
             observers) {
            o.notify(state);
        }
    }
}
