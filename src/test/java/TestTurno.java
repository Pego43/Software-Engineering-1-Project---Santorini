import org.junit.Before;
import org.junit.Test;
import santorini.network.NetworkHandlerServer;
import santorini.controller.Turno;
import santorini.model.*;
import santorini.model.godCards.Athena;
import santorini.model.godCards.God;
import santorini.model.godCards.Pdor;
import santorini.model.godCards.Prometheus;

import java.util.ArrayList;

import static junit.framework.TestCase.*;

public class TestTurno {
    private Turno turn;
    private Table table;
    private Gamer gamer;
    private God Athena, Prometheus, Pdor;
    private NetworkHandlerServer gamerHandler;
    private ArrayList<God> godCards;
    private boolean effect;

    @Before
    public void before() {
        table = new Table();
        gamer = new Gamer(null, "Hercules", 0, null, null);
        Athena = new Athena();
        Prometheus = new Prometheus();
        Pdor = new Pdor();
        godCards = new ArrayList<>();
        godCards.add(Pdor);
        godCards.add(Athena);
        godCards.add(Prometheus);
        turn = new Turno(godCards, gamer, table, gamerHandler);
    }

    /**
     * method that tests the start parameter of the base move (movement and building)
     */
    @Test
    public void testGetStandardParameter() {
        Mossa move00 = new Mossa(Mossa.Action.MOVE, 0, 2, 2);
        assertTrue(turn.controlStandardParameter(move00));
        Mossa move01 = new Mossa(Mossa.Action.BUILD, 0, 2, 2);
        assertTrue(turn.controlStandardParameter(move01));
        Mossa move02 = new Mossa(Mossa.Action.MOVE, -1, 2, 2);
        Mossa move03 = new Mossa(Mossa.Action.BUILD, 0, 3, 7);
        Mossa move04 = new Mossa(Mossa.Action.BUILD, 3, -4, 9);
        assertTrue(!turn.controlStandardParameter(move02));
        assertTrue(!turn.controlStandardParameter(move03));
        assertTrue(!turn.controlStandardParameter(move04));
    }

    @Test
    public void nullEffectForGodCards() {
        Mossa m0 = new Mossa(Mossa.Action.MOVE, -1, -1, -1);
        Mossa m1 = new Mossa(Mossa.Action.BUILD, -1, -1, -1);
        Mossa m2 = new Mossa(Mossa.Action.MOVE, 1, 1, 1);
        Mossa m3 = new Mossa(Mossa.Action.BUILD, 0, 2, 2);
        assertTrue(turn.nullEffectForGodCards(m0));
        assertTrue(turn.nullEffectForGodCards(m1));
        assertFalse(turn.nullEffectForGodCards(m2));
        assertFalse(turn.nullEffectForGodCards(m3));
    }

}