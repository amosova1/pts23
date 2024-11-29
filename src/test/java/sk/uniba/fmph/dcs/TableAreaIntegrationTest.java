package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
public class TableAreaIntegrationTest {
    @Test
    public void integrationTest(){
        UsedTyles usedTyles = UsedTyles.getInstance();
        TableCenter tableCenter = TableCenter.getInstance();
        ArrayList<Factory> factories = new ArrayList<>();
        ArrayList<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            tiles.add(Tile.RED);
            tiles.add(Tile.GREEN);
            tiles.add(Tile.YELLOW);
            tiles.add(Tile.BLUE);
            tiles.add(Tile.BLACK);
        }

        Bag bag = new Bag(usedTyles, tiles);
        for (int i = 0; i < 2; i++) {
            factories.add(new Factory(bag, tableCenter, new ArrayList<>()));
        }

        TableArea tableArea = new TableArea(tableCenter, factories);

        assertEquals("TyleSources:\nTable Center:\n\nFactory:\n\nFactory:\n\n", tableArea.state());

        tableArea.startNewRound();

        String empty = "TyleSources:\nTable Center:\n\nFactory:\n\nFactory:\n\n";
        assertNotEquals(empty.length(), tableArea.state().length());

        assertTrue(tableArea.take(0, 1).isEmpty());
        assertFalse(tableArea.take(1, 1).isEmpty());
        String statePoPrvomTahu = tableCenter.state();
        assertFalse(tableCenter.isEmpty());
        assertTrue(factories.get(0).isEmpty());

        assertFalse(tableArea.take(2, 1).isEmpty());
        String statePoDruhomTahu = tableCenter.state();

        assertNotEquals(statePoPrvomTahu, statePoDruhomTahu);
        assertTrue(statePoPrvomTahu.length() < statePoDruhomTahu.length());

        assertTrue(tableCenter.state().contains("S"));
        assertFalse(tableArea.take(0, 1).isEmpty());
        assertFalse(tableCenter.state().contains("S"));

        while (!tableCenter.isEmpty()) {
            tableArea.take(0, 0);
        }

        assertEquals("TyleSources:\nTable Center:\n\nFactory:\n\nFactory:\n\n", tableArea.state());

        assertTrue(tableArea.isRoundEnd());
    }
}
