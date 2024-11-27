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

        Factory factory = new Factory(fakebag, tableCenter);
        factories.add(factory);

        tableCenter.startNewRound();
        factory.startNewRound();

        Pair nove = factory.take(0);
        factory = (Factory) nove.getNewFactory();

        ArrayList<Tile> result = nove.getSelectedTiles();
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
        Factory factory = new Factory(fakebag, tableCenter);

        factory.startNewRound();
        tableCenter.startNewRound();

        int invalidIdx = 5;
        Pair nove = factory.take(invalidIdx);
        factory = (Factory) nove.getNewFactory();

        ArrayList<Tile> result = nove.getSelectedTiles();
        assertTrue(result.isEmpty());

        nove = factory.take(0);
        factory = (Factory) nove.getNewFactory();

        ArrayList<Tile> zvysok = nove.getSelectedTiles();
        zvysok.addAll(tableCenter.take(1));
        zvysok.addAll(tableCenter.take(0));
        usedTyles_instance.give(zvysok);
    }

    @Test
    public void isEmpty_notStarted() {
        FakeTableCenter tableCenter = new FakeTableCenter();
        FakeUsedTyles usedTyles_instance = new FakeUsedTyles();
        FakeBag fakebag = new FakeBag(usedTyles_instance);
        Factory factory = new Factory(fakebag, tableCenter);

        Pair nove = factory.take(1);

        assertTrue(nove.getSelectedTiles().isEmpty());
    }

    @Test
    public void isEmpty_started() {
        FakeTableCenter tableCenter = new FakeTableCenter();
        ArrayList<Factory> factories = new ArrayList<>();
        FakeUsedTyles usedTyles_instance = new FakeUsedTyles();
        FakeBag fakebag = new FakeBag(usedTyles_instance);

        Factory factory = new Factory(fakebag, tableCenter);
        factories.add(factory);

        tableCenter.startNewRound();
        factory.startNewRound();

        Pair nove = factory.take(0);
        factory = (Factory) nove.getNewFactory();

        ArrayList<Tile> result = nove.getSelectedTiles();
        usedTyles_instance.give(result);

        assertFalse(result.isEmpty());

        nove = factory.take(0);
        factory = (Factory) nove.getNewFactory();

        assertTrue(nove.getSelectedTiles().isEmpty());

        ArrayList<Tile> zvysok = tableCenter.take(1);
        zvysok.addAll(tableCenter.take(0));
        usedTyles_instance.give(zvysok);
    }

    @Test
    public void startNewRound_isWorking(){
        FakeTableCenter tableCenter = new FakeTableCenter();
        FakeUsedTyles usedTyles_instance = new FakeUsedTyles();
        FakeBag fakebag = new FakeBag(usedTyles_instance);
        Factory factory = new Factory(fakebag, tableCenter);

        assertTrue(factory.isEmpty());

        factory.startNewRound();
        tableCenter.startNewRound();
        assertFalse(factory.isEmpty());

        Pair nove = factory.take(0);
        factory = (Factory) nove.getNewFactory();

        ArrayList<Tile> zvysok = nove.getSelectedTiles();
        zvysok.addAll(tableCenter.take(1));
        zvysok.addAll(tableCenter.take(0));
        usedTyles_instance.give(zvysok);
    }

    @Test
    public void state_isWorking(){
        FakeTableCenter tableCenter = new FakeTableCenter();
        FakeUsedTyles usedTyles_instance = new FakeUsedTyles();
        FakeBag fakebag = new FakeBag(usedTyles_instance);
        Factory factory = new Factory(fakebag,tableCenter);

        System.out.println(factory.state());

        factory.startNewRound();

        System.out.println(factory.state());

        tableCenter.startNewRound();
        String expected = "Factory:\n" +
                "G\n" +
                "R\n" +
                "R\n" +
                "B\n";
        assertEquals(expected, factory.state());

        Pair nove = factory.take(0);
        factory = (Factory) nove.getNewFactory();

        ArrayList<Tile> zvysok = nove.getSelectedTiles();
        zvysok.addAll(tableCenter.take(1));
        zvysok.addAll(tableCenter.take(0));
        usedTyles_instance.give(zvysok);
    }

    @Test
    public void state_emptyFactory(){
        FakeTableCenter tableCenter = new FakeTableCenter();
        FakeUsedTyles usedTyles_instance = new FakeUsedTyles();
        FakeBag fakebag = new FakeBag(usedTyles_instance);
        Factory factory = new Factory(fakebag, tableCenter);

        assertEquals("Factory:\n", factory.state());
    }

}
