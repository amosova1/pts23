package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public class GameObserver implements ObserverInterface{

    private ArrayList<ObserverInterface> observers;

    private String state;

    private GameObserver(){
        observers = new ArrayList<>();
    }

    private static class GameObserverHolder {
        private static final GameObserver INSTANCE = new GameObserver();
    }

    public static GameObserver getInstance() {
        return GameObserverHolder.INSTANCE;
    }

    public void registerObserver(ObserverInterface observer) {
        this.observers.add(observer);
    }

    public void cancelObserver(ObserverInterface observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notify(String newState) {
        //this.state = newState;
        for (ObserverInterface o:
                observers) {
            o.notify(newState);
        }
    }

    public void notifyEverybody(String state){
        for (ObserverInterface o:
             observers) {
            o.notify(state);
        }
    }
}
