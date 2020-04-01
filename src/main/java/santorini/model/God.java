package santorini.model;

import santorini.Game;
import santorini.Gamer;

public abstract class God {
    protected Gamer owner;

    public God(Gamer gamer) {
        this.owner = gamer;
    }

    public Gamer getOwner() {
        return owner;
    }

    /**
     * Initialize player variables with card
     * @param g player owner of card
     */
    public abstract void initializeOwner(Gamer g);
    /**
     * Features added by card before its owner does his moves
     */
    public abstract void beforeOwnerMoving();

    /**
     * Features added by card after its owner does his moves
     */
    public abstract void afterOwnerMoving();

    /**
     * Features added by card before its owner starts building
     */
    public abstract void beforeOwnerBuilding();

    /**
     * Features added by card after its owner starts building
     */
    public abstract void afterOwnerBuilding();

    /**
     * Features added by card before other player does his moves
     * @param other player to customize
     */
    public abstract void beforeOtherMoving(Gamer other);

    /**
     * Features added by card after other player does his moves
     * @param other player to customize
     */
    public abstract void afterOtherMoving(Gamer other);

    /**
     * Features added by card before other player starts building
     * @param other player to customize
     */
    public abstract void beforeOtherBuilding(Gamer other);

    /**
     * Features added by card before other player starts building
     * @param other player to customize
     */
    public abstract void afterOtherBuilding(Gamer other);

}