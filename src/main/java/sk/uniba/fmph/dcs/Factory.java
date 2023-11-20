package sk.uniba.fmph.dcs;

public class Factory implements TyleSource{
    private TableCenter tableCenter;
    public Factory(){
        this.tableCenter = TableCenter.getInstance();
    }
    public Factory(TableCenter tableCenter){
        this.tableCenter = tableCenter;
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
        tableCenter.startNewRound();

    }

    @Override
    public String state() {
        return null;
    }
}
