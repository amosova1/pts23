package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class FactoryTestFinal {

    @Test
    public void take_validValue_updateTableCenter() {
        TableCenter tableCenter = TableCenter.getInstance();
        ArrayList<Factory> factories = new ArrayList<>();
        FakeUsedTyles usedTyles_instance = FakeUsedTyles.getInstance();
        FakeBag fakebag = new FakeBag(usedTyles_instance);

        Factory factory = new Factory(fakebag);
        factories.add(factory);
        TableArea tableArea = new TableArea(tableCenter, factories);
        tableArea.startNewRound();

        ArrayList<Tile> result = tableArea.take(1, 0);
        usedTyles_instance.give(result);

        assertFalse(result.isEmpty());
        assertFalse(tableCenter.isEmpty());

        ArrayList<Tile> zvysok = tableArea.take(0, 1);
        zvysok.addAll(tableArea.take(0, 0));

        usedTyles_instance.give(zvysok);
    }

    @Test
    public void take_invalidValue() {
        TableCenter tableCenter = TableCenter.getInstance();
        FakeUsedTyles usedTyles_instance = FakeUsedTyles.getInstance();
        FakeBag fakebag = new FakeBag(usedTyles_instance);
        Factory factory = new Factory(fakebag);

        factory.startNewRound();
        tableCenter.startNewRound();

        int invalidIdx = 5;
        ArrayList<Tile> result = factory.take(invalidIdx);
        assertTrue(result.isEmpty());

        ArrayList<Tile> zvysok = factory.take(0);
        zvysok.addAll(tableCenter.take(1));
        zvysok.addAll(tableCenter.take(0));
        usedTyles_instance.give(zvysok);
    }

    @Test
    public void isEmpty_notStarted() {
        FakeUsedTyles usedTyles_instance = FakeUsedTyles.getInstance();
        FakeBag fakebag = new FakeBag(usedTyles_instance);
        Factory factory = new Factory(fakebag);

        assertTrue(factory.take(1).isEmpty());
    }

    @Test
    public void isEmpty_started() {
        TableCenter tableCenter = TableCenter.getInstance();
        FakeUsedTyles usedTyles_instance = FakeUsedTyles.getInstance();
        FakeBag fakebag = new FakeBag(usedTyles_instance);
        Factory factory = new Factory(fakebag);

        ArrayList<Factory> factories = new ArrayList<>();

        factories.add(factory);
        TableArea tableArea = new TableArea(tableCenter, factories);
        tableArea.startNewRound();

        ArrayList<Tile> result = tableArea.take(1, 0);
        usedTyles_instance.give(result);

        assertFalse(result.isEmpty());
        assertTrue(tableArea.take(1, 0).isEmpty());

        ArrayList<Tile> zvysok = tableArea.take(0, 1);
        zvysok.addAll(tableArea.take(0, 0));
        usedTyles_instance.give(zvysok);
    }

    @Test
    public void startNewRound_isWorking(){
        TableCenter tableCenter = TableCenter.getInstance();
        FakeUsedTyles usedTyles_instance = FakeUsedTyles.getInstance();
        FakeBag fakebag = new FakeBag(usedTyles_instance);
        Factory factory = new Factory(fakebag);

        assertTrue(factory.isEmpty());

        factory.startNewRound();
        tableCenter.startNewRound();
        assertFalse(factory.isEmpty());

        ArrayList<Tile> zvysok = factory.take(0);
        zvysok.addAll(tableCenter.take(1));
        zvysok.addAll(tableCenter.take(0));
        usedTyles_instance.give(zvysok);
    }

    @Test
    public void state_isWorking(){
        TableCenter tableCenter = TableCenter.getInstance();
        FakeUsedTyles usedTyles_instance = FakeUsedTyles.getInstance();
        FakeBag fakebag = new FakeBag(usedTyles_instance);
        Factory factory = new Factory(fakebag);

        factory.startNewRound();
        tableCenter.startNewRound();
        String expected = "Factory:\n" +
                "G\n" +
                "R\n" +
                "R\n" +
                "B\n";
        assertEquals(expected, factory.state());

        ArrayList<Tile> zvysok = factory.take(0);
        zvysok.addAll(tableCenter.take(1));
        zvysok.addAll(tableCenter.take(0));
        usedTyles_instance.give(zvysok);
    }

    @Test
    public void state_emptyFactory(){
        FakeUsedTyles usedTyles_instance = FakeUsedTyles.getInstance();
        FakeBag fakebag = new FakeBag(usedTyles_instance);
        Factory factory = new Factory(fakebag);

        assertEquals("Factory:\n", factory.state());
    }

}
