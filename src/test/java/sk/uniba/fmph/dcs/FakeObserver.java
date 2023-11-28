package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public class FakeObserver implements ObserverInterface{
    @Override
    public void registerObserver(ObserverInterface observer) {

    }

    @Override
    public void cancelObserver(ObserverInterface observer) {

    }

    @Override
    public void notify(String newState) {

    }

    @Override
    public String getState() {
        return null;
    }
}
