package model;

import org.junit.Before;
import org.junit.Test;
import santorini.model.Cell;
import santorini.model.Pawn;
import santorini.model.utils.Color;

import static org.junit.Assert.assertTrue;


public class TestPawn {
    private Pawn pawn;

    @Before
    public void before() {
        pawn = new Pawn();
    }

    /**
     * method that tests getIdPawn
     */
    @Test
    public void testGetIdPawn(){
        int x = pawn.getIdPawn();
        assertTrue(x==1);
        pawn.setIdPawn(0);
        assertTrue(pawn.getIdPawn()==0);
    }

    /**
     * method that tests getIdGamer
     */

    @Test
    public void testGetIdGamer(){
        int x = pawn.getIdGamer();
        assertTrue(x==1);
        pawn.setIdGamer(0);
        assertTrue(pawn.getIdGamer()==0);
    }
    /**
     * method that tests pastLevel and presentLevel
     */

    @Test
    public void testLevelsPawn(){
        int x = pawn.getPastLevel();
        int y = pawn.getPresentLevel();
        assertTrue(x==0);
        assertTrue(y==1);
        pawn.setPastLevel(2);
        pawn.setPresentLevel(2);
        assertTrue(pawn.getPastLevel()==2);
        assertTrue(pawn.getPresentLevel()==2);
    }

    /**
     * method that tests colorPawn, the team of the pawn
     */
    @Test
    public void testColorPawn(){
        Color c = pawn.getColorPawn();
        assertTrue(c == Color.BLUE);
        pawn.setColorPawn(Color.GREEN);
        assertTrue(pawn.getColorPawn()==Color.GREEN);
    }

    /**
     * method that tests the pawn's position
     */
    @Test
    public void testPawnCell() {
        Cell position;
        position = new Cell();
        position.setX(0);
        position.setX(0);
        pawn.setPawnNewCell(position);
        assertTrue(pawn.getPawnCell().getX() == position.getX());
        assertTrue(pawn.getPawnCell().getY() == position.getY());
    }
}
