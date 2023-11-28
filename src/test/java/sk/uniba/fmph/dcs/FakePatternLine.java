package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public class FakePatternLine implements PatternLineInterface{
    private int capacity;
    private WallLineInterface wallLine_instance;
    private FloorInterface floor_instance;
    private ArrayList<Tile> _tyles;
    private UsedTyles usedTyles_instance;
    public FakePatternLine(int capacity, WallLineInterface wallLine_instance, FloorInterface floor_instance) {
        this.capacity = capacity;
        this.wallLine_instance = wallLine_instance;
        this.floor_instance = floor_instance;
        this.usedTyles_instance = UsedTyles.getInstance();
        this._tyles = new ArrayList<>();
    }

    @Override
    public void put(ArrayList<Tile> tiles) {
        ArrayList<Tile> zvysne = new ArrayList<>();
        for (Tile tile : tiles) {
            if (this._tyles.toString().equals("S")) {
                this._tyles.add(tile);
                continue;
            }
            if (this._tyles.size() < this.capacity) {
                if (this._tyles.isEmpty()) {
                    this._tyles.add(tile);
                } else {
                    if (this._tyles.get(0).equals(tile)) {
                        this._tyles.add(tile);
                    } else {
                        zvysne.add(tile);
                    }
                }
            } else {
                zvysne.add(tile);
            }
        }
        this.floor_instance.put(zvysne);
    }

    @Override
    public Points finishRound() {
        Points points = new Points(0);
        if (this._tyles.isEmpty()){
            return points;
        }
        System.out.print(this._tyles.get(0) + " ");
        if (this._tyles.size() == this.capacity){
            if (this.wallLine_instance.canPutTile(this._tyles.get(0))){
                points = this.wallLine_instance.putTile(this._tyles.get(0));
                usedTyles_instance.give(this._tyles);
                this._tyles.clear();
            }
            else{
                this.usedTyles_instance.give(this._tyles);
                this._tyles.clear();
            }
        }
        System.out.println(points);
        return points;
    }

    @Override
    public String state() {
        StringBuilder ans = new StringBuilder();
        ans.append("PatternLine:\n");
        ans.append("capacity: ").append(capacity).append("\n");
        for (Tile tile : _tyles) {
            ans.append(tile.toString()).append("\n");
        }
        return ans.toString();
    }
}
