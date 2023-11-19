package sk.uniba.fmph.dcs;

public class Factory implements TyleSource{
    public Factory(){

    }

    @Override
    public Tile[] take(int idx) {
        return new Tile[0];
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void startNewRound() {

    }

    @Override
    public String state() {
        return null;
    }
}
