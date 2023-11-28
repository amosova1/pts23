package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public class FakeObserver implements ObserverInterface{
    private ArrayList<ObserverInterface> observers;

    private String state;

    private FakeObserver(){
        observers = new ArrayList<>();
    }

    private static class GameObserverHolder {
        private static final FakeObserver INSTANCE = new FakeObserver();
    }

    public static FakeObserver getInstance() {
        return FakeObserver.GameObserverHolder.INSTANCE;
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
        this.state = newState;
    }

    public void notifyEverybody(String state){
        for (ObserverInterface o:
                observers) {
            o.notify(state);
        }
    }

    @Override
    public String getState(){
        String s = "";
        for (ObserverInterface o: this.observers) {
            s += o.getState();
        }
        return s;
    }

    public void vypis(){
        System.out.println(this.getState());
    }
}
