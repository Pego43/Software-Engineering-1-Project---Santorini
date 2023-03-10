package santorini.model.godCards;

import santorini.controller.Turno;
import santorini.model.Cell;
import santorini.model.Gamer;
import santorini.model.Mossa;

/**
 * Class Demeter
 */

public class Demeter extends God {
    private Cell firstBuilding;
    private boolean demeterEffect;
    private Mossa build2;
    private boolean printerStatus = true;

    public Demeter() {
        super("Demeter", "Tua costruzione:\n" +
                "il tuo lavoratore può costruire una volta in più\n" +
                "ma non nella stessa casella");
    }

    /**
     * Initialize player variables with card
     *
     * @param turno player owner of card
     */
    public void initializeOwner(Turno turno) {

    }

    /**
     * Features added by card before its owner does his moves
     *
     * @param turno current turn
     */
    public void beforeOwnerMoving(Turno turno) {
    }

    /**
     * Features added by card after its owner does his moves
     *
     * @param turno current turn
     */
    public void afterOwnerMoving(Turno turno) {
        if (turno.isValidationMove()) {
            //broadcast message of movement
            turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha mosso: " + turno.getMove().getIdPawn() +
                    " in [" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
            //print status of the table
            turno.printTableStatusTurn(true);
        }

    }

    /**
     * Features added by card before its owner starts building
     *
     * @param turno current turn
     */
    public void beforeOwnerBuilding(Turno turno) {
    }

    /**
     * Features added by card after its owner starts building
     *
     * @param turno current turn
     */
    public void afterOwnerBuilding(Turno turno) {
        if (turno.isValidationBuild()) {
            if (printerStatus) {
                //broadcast message of building
                turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha costruito in: " +
                        "[" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
                //print table status
                turno.printTableStatusTurn(printerStatus);
                printerStatus = false;
            }
            firstBuilding = turno.getTable().getTableCell(turno.getMove().getTargetX(), turno.getMove().getTargetY());
            demeterEffect = false;
            turno.setCount(0);
            turno.getGamer().setBuilds(1);
            turno.getGameHandler().sendMessage(turno.getGamer(), "\u001B[34m" + "Hai Demeter, puoi costruire una volta in più, ma\n" +
                    "non nella stessa casella precedente. " +
                    "\nSe non vuoi costruire scegli l'opzione 'Salta'" + "\u001B[0m");
            do {
                build2 = turno.buildingRequest();
                if (turno.nullEffectForGodCards(build2)) {
                    demeterEffect = true;
                    printerStatus = false;
                    turno.getGameHandler().sendMessage(turno.getGamer(), "\u001B[34m" + "Effetto annullato " + "\u001B[0m");
                } else {
                    if ((firstBuilding.getX() == build2.getTargetX()) &&
                            (firstBuilding.getY() == build2.getTargetY())) {
                        demeterEffect = false;
                        turno.getGameHandler().sendFailed(turno.getGamer(), "##Non puoi costruire" +
                                " nella stessa casella precedente##");
                        turno.getValidation(false);
                    } else {
                        turno.baseBuilding(build2);
                        turno.getValidation(turno.isValidationBuild());
                        demeterEffect = turno.isValidationBuild();
                        printerStatus = demeterEffect;
                    }
                }
            } while (!demeterEffect && turno.getCount() < 3);
            if (demeterEffect && printerStatus) {
                turno.setMove(build2);
                //broadcast message of building
                turno.getGameHandler().getGame().broadcastMessage("\u001B[34m" + "Effetto di Demeter. " + "\u001B[0m");
                turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha costruito in: " +
                        "[" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
                //print table status
                turno.printTableStatusTurn(true);
            }
        }
        printerStatus = true;
    }

    /**
     * Features added by card before other player does his moves
     *
     * @param other player to customize
     */
    public void beforeOtherMoving(Gamer other) {

    }

    /**
     * Features added by card after other player does his moves
     *
     * @param other player to customize
     */
    public void afterOtherMoving(Gamer other) {

    }

    /**
     * Features added by card before other player starts building
     *
     * @param other player to customize
     */
    public void beforeOtherBuilding(Gamer other) {

    }

    /**
     * Features added by card before other player starts building
     *
     * @param other player to customize
     */
    public void afterOtherBuilding(Gamer other) {

    }
}
