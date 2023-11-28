package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collection;

interface UsedTilesGiveInterface {
  void give(Collection<Tile> ts);
  ArrayList<Tile> takeAll();
}
