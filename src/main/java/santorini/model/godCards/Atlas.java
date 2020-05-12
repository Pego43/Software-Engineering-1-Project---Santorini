package santorini.model.godCards;

import santorini.Turno;
import santorini.model.Cell;
import santorini.model.Gamer;
import santorini.model.Mossa;

//P
public class Atlas extends God {
    private Mossa buildDome;
    private boolean atlasEffect;
    private boolean controlEffect;

    public Atlas() {
        super("Atlas", "Tua costruzione: il tuo lavoratore può costruire una cupola\n" +
                "su qualsiasi livello, compreso il terreno");
    }

    @Override
    public Mossa getEffectMove() {
        return buildDome;
    }

    @Override
    public void setEffectMove(Mossa effectMove) {
        this.buildDome = effectMove;
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
        controlEffect = true;
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
            turno.printTableStatusTurn(turno.isValidationMove());
            //Atlas effect
            turno.getGameHandler().sendMessage(turno.getGamer(), "\u001B[34m" + "Hai Atlas, puoi costruire una cupola dove vuoi.\n" +
                    "Se non vuoi costruire la cupola scegli l'opzione 'No'" + "\u001B[0m" + "\n");
        }
    }

    /**
     * Features added by card before its owner starts building
     *
     * @param turno current turn
     */
    public void beforeOwnerBuilding(Turno turno) {
        if (turno.isValidationMove() && controlEffect) {
            atlasEffect = false;
            turno.setCount(0);
            do {
                buildDome = turno.getMove();
                if (turno.nullEffectForGodCards(buildDome)) {
                    atlasEffect = true;
                    controlEffect = false;
                    turno.getGamer().setBuilds(1);
                    turno.setMove(turno.buildingRequest());
                } else {
                    if (!turno.controlStandardParameter(buildDome)) {
                        atlasEffect = false;
                    } else {
                        Cell end = turno.getTable().getTableCell(buildDome.getTargetX(), buildDome.getTargetY());
                        if (end.isComplete() || end.getPawn() != null) {
                            atlasEffect = false;
                        } else {
                            turno.getTable().getTableCell(buildDome.getTargetX(), buildDome.getTargetY()).setComplete(true);
                            atlasEffect = true;
                            turno.setValidationBuild(true);
                            turno.setValidationMove(false);
                            turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha costruito in: " +
                                    "[" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
                            turno.printTableStatusTurn(turno.isValidationBuild());
                        }
                    }
                    if (!atlasEffect) {
                        turno.sendFailed();
                        turno.setMove(turno.buildingRequest());
                    }
                }
            } while (!atlasEffect && turno.getCount() > 5);
            turno.setCount(0);
        }
    }

    /**
     * Features added by card after its owner starts building
     *
     * @param turno current turn
     */
    public void afterOwnerBuilding(Turno turno) {
        if (turno.isValidationBuild() && atlasEffect && !controlEffect) {
            turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha costruito in: " +
                    "[" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
            //turno.printTableStatusTurn(turno.isValidationBuild());
        }
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
