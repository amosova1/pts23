package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class FactoryTestFinal {

    @Test
    public void take_validValue_updateTableCenter() {
        FakeTableCenter tableCenter = new FakeTableCenter();
        ArrayList<Factory> factories = new ArrayList<>();
        FakeUsedTyles usedTyles_instance = new FakeUsedTyles();
        FakeBag fakebag = new FakeBag(usedTyles_instance);

        Factory factory = new Factory(fakebag, tableCenter, new ArrayList<>());
        factories.add(factory);

        tableCenter.startNewRound();
        Triple nove = factory.startNewRound();
        factory = (Factory) nove.getNewFactory();

        Triple nove2 = factory.take(0);

        ArrayList<Tile> result = nove2.getSelectedTiles();
        usedTyles_instance.give(result);

        assertFalse(result.isEmpty());
        assertFalse(tableCenter.isEmpty());

        ArrayList<Tile> zvysok = tableCenter.take(1);
        zvysok.addAll(tableCenter.take(0));

        usedTyles_instance.give(zvysok);
    }

    @Test
    public void take_invalidValue() {
        FakeTableCenter tableCenter = new FakeTableCenter();
        FakeUsedTyles usedTyles_instance = new FakeUsedTyles();
        FakeBag fakebag = new FakeBag(usedTyles_instance);
        Factory factory = new Factory(fakebag, tableCenter, new ArrayList<>());

        Triple nove = factory.startNewRound();
        factory = (Factory) nove.getNewFactory();
        tableCenter.startNewRound();

        int invalidIdx = 5;
        Triple nove2 = factory.take(invalidIdx);
        factory = (Factory) nove2.getNewFactory();

        ArrayList<Tile> result = nove2.getSelectedTiles();
        assertTrue(result.isEmpty());
    }

    @Test
    public void isEmpty_notStarted() {
        FakeTableCenter tableCenter = new FakeTableCenter();
        FakeUsedTyles usedTyles_instance = new FakeUsedTyles();
        FakeBag fakebag = new FakeBag(usedTyles_instance);
        Factory factory = new Factory(fakebag, tableCenter, new ArrayList<>());

        Triple nove = factory.take(1);

        assertTrue(nove.getSelectedTiles().isEmpty());
    }

    @Test
    public void isEmpty_started() {
        FakeTableCenter tableCenter = new FakeTableCenter();
        ArrayList<Factory> factories = new ArrayList<>();
        FakeUsedTyles usedTyles_instance = new FakeUsedTyles();
        FakeBag fakebag = new FakeBag(usedTyles_instance);

        Factory factory = new Factory(fakebag, tableCenter, new ArrayList<>());
        factories.add(factory);

        tableCenter.startNewRound();
        Triple nove = factory.startNewRound();
        factory = (Factory) nove.getNewFactory();

        Triple nove2 = factory.take(0);
        factory = (Factory) nove2.getNewFactory();

        ArrayList<Tile> result = nove2.getSelectedTiles();
        usedTyles_instance.give(result);

        assertFalse(result.isEmpty());

        ArrayList<Tile> zvysok = tableCenter.take(1);
        zvysok.addAll(tableCenter.take(0));
        usedTyles_instance.give(zvysok);
    }

    @Test
    public void startNewRound_isWorking(){
        FakeTableCenter tableCenter = new FakeTableCenter();
        FakeUsedTyles usedTyles_instance = new FakeUsedTyles();
        FakeBag fakebag = new FakeBag(usedTyles_instance);
        Factory factory = new Factory(fakebag, tableCenter, new ArrayList<>());

        assertTrue(factory.isEmpty());

        Triple nove = factory.startNewRound();
        factory = (Factory) nove.getNewFactory();

        tableCenter.startNewRound();
        assertFalse(factory.isEmpty());

        Triple nove2 = factory.take(0);

        ArrayList<Tile> zvysok = nove2.getSelectedTiles();
        zvysok.addAll(tableCenter.take(1));
        zvysok.addAll(tableCenter.take(0));
        usedTyles_instance.give(zvysok);
    }

    @Test
    public void state_isWorking(){
        FakeTableCenter tableCenter = new FakeTableCenter();
        FakeUsedTyles usedTyles_instance = new FakeUsedTyles();
        FakeBag fakebag = new FakeBag(usedTyles_instance);
        Factory factory = new Factory(fakebag,tableCenter, new ArrayList<>());

        Triple nove = factory.startNewRound();
        factory = (Factory) nove.getNewFactory();

        tableCenter.startNewRound();
        String expected = "Factory:\n" +
                "G\n" +
                "R\n" +
                "R\n" +
                "B";
        assertEquals(expected, factory.state());

        Triple nove2 = factory.take(0);
        factory = (Factory) nove2.getNewFactory();

        ArrayList<Tile> zvysok = nove2.getSelectedTiles();
        zvysok.addAll(tableCenter.take(1));
        zvysok.addAll(tableCenter.take(0));
        usedTyles_instance.give(zvysok);
    }

    @Test
    public void state_emptyFactory(){
        FakeTableCenter tableCenter = new FakeTableCenter();
        FakeUsedTyles usedTyles_instance = new FakeUsedTyles();
        FakeBag fakebag = new FakeBag(usedTyles_instance);
        Factory factory = new Factory(fakebag, tableCenter, new ArrayList<>());

        assertEquals("Factory:\n", factory.state());
    }

}
