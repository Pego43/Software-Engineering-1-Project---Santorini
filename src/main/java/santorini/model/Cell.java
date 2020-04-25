/**
 * @author Magliano
 */
package santorini.model;

import java.io.Serializable;

public class Cell implements Serializable {

    private int level;
    private boolean free;
    private boolean complete;
    private int x;
    private int y;
    private final int MAX_LEVEL = 3;
    private Pawn pawn;


    /**
     * Cell with param x,y that i will use to initialize
     */
    public Cell() {
        this.level = 0;
        this.free = true;
        this.complete = false;
        this.pawn = null;
    }

    /**
     * method get level
     *
     * @return level of the brick
     */
    public int getLevel() {
        return level;
    }

    /**
     * method setLevel
     * this method see if the level is 4 is completed
     *
     * @param newLevel
     */
    public void setLevel(int newLevel) {
        level = newLevel;
        if (level == 4) {
            level = MAX_LEVEL;
            setComplete(true);
            setFree(false);
        }
    }

    /**
     * method isFree
     *
     * @return if the cell is free
     */
    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
        if (free) {
            this.pawn = null;
        }
    }

    /**
     * Puts a pawn in this cell if free and sets it not free
     *
     * @param pawn pawn to put in this cell
     * @return operation success or failure
     */
    public void setPawn(Pawn pawn) {
            this.pawn = pawn;
        if (this.pawn == null) {
            this.free = true;
        } else {
            this.free = false;
        }
    }

    /**
     * method getPawn
     *
     * @return
     */
    public Pawn getPawn() {
        return pawn;
    }

    /**
     * method isComplete
     * @return complete
     */
    public boolean isComplete() {
        return complete;
    }

    /**
     * method setComplete
     * @param complete
     */
    public void setComplete(boolean complete) {
        this.complete = complete;
        if (this.complete) {
            this.free = false;}
    }

    /**
     * method getX
     * @return row
     */
    public int getX() {
        return x;
    }

    /**
     * method getY
     * @return column
     */
    public int getY() {
        return y;
    }

    /**
     * method setX
     * @param newX new row
     */
    public void setX(int newX) {
        x = newX;
    }

    /**
     * method setY
     * @param newY new column
     */
    public void setY(int newY) {
        y = newY;
    }
}


