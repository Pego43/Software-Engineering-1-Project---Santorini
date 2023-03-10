package santorini.model.godCards;

import santorini.controller.Turno;
import santorini.model.Gamer;

/**
 * Class Pdor
 * God test class
 */

public class Pdor extends God {

    public Pdor() {
        super("Pdor", "Io sono Pdor, figlio di Kmer, della tribù di Ishtar,\n" +
                "della terra desolata dei Kfnir...\n" +
                "Uno degli ultimi sette saggi: Bvur, Ganer, Hastaparil, Ghiinin, Ganit, Ciuciul e Palar...\n" +
                "Pdor: colui che era, colui che è e colui che sempre sarà.\n" +
                "Ciucia chi e ciucia là.\n" +
                "Pdor, colui il quale ha sconfitto i demoni Sem, che ora vagano per il mondo e si domandano: <<ma num, chi semm?>>'\n" +
                "Avvicinati tu, o uomo dalla forma gnomica, come osi presentarti al cospetto di Pdor, colui il quale è sceso tra le ninfe\n" +
                "del lago Kgbnuberals, e li ha assaggiato il mitico cibo degli dei: la piadeina...\n" +
                "Avvicinati tu, o uomo dalla terra dei Fichi d'India, avvicinati! E ricorda che sei al cospetto di Pdor, figlio di Kmer...\n" +
                "Colui il quale ha amato le mille dee Ashfriteral, tra cui la dea Berta, la dea dalla gamba aperta..." +
                "Pdor...Colui il quale dopo un'epica battaglia sui monti di Kfestar, liberò il popolo schiavo di Fdniur\n" +
                "e lo accompagnò libero, nelle terre di Uohm... purtroppo già occupate dai perfidi Kerv che gli sterminarono.\n" +
                "Pdor, colui il quale visse felice per due lune col mite popolo dei Prot...\n" +
                "Popolo che comunicava solo con peti al profumo di gelso.");
    }

    /**
     * Initialize player variables with card
     *
     * @param turno player owner of card
     */
    @Override
    public void initializeOwner(Turno turno) {
    }

    /**
     * Features added by card before its owner does his moves
     *
     * @param turno .
     */
    @Override
    public void beforeOwnerMoving(Turno turno) {
    }

    /**
     * Features added by card after its owner does his moves
     *
     * @param turno .
     */
    @Override
    public void afterOwnerMoving(Turno turno) {

        if (turno.isValidationMove()) {
            //broadcast message of movement
            turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha mosso: " + turno.getMove().getIdPawn() +
                    " in [" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
            turno.printTableStatusTurn(true);
        }

    }

    /**
     * Features added by card before its owner starts building
     *
     * @param turno .
     */
    @Override
    public void beforeOwnerBuilding(Turno turno) {

    }

    /**
     * Features added by card after its owner starts building
     *
     * @param turno .
     */
    @Override
    public void afterOwnerBuilding(Turno turno) {

        if (turno.isValidationBuild()) {
            turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha costruito in: " +
                    "[" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
            //print status of the table
            turno.printTableStatusTurn(true);
            if (turno.getGamer().getPawn(turno.getMove().getIdPawn()).getPastLevel() == 2 &&
                    turno.getGamer().getPawn(turno.getMove().getIdPawn()).getPresentLevel() == 3) {
                turno.getGameHandler().getGame().broadcastMessage("PDOR HA VINTO!!!!!!\n" +
                        "ORA TU MIO ELETTO, TI GUADAGNERAI IL PANE COL SUDORE DELLA FRONTE\n" +
                        "SI, E MIGRERAI, E ROMPERAI LE BALLE AI POPOLI DEL NORD\n" +
                        "MENTRE TU...TU...TU PARTORIARI CON DOLORE\n" +
                        "QUESTA E' LA PAROLA DI PDOR");
            }
        }

    }

    /**
     * Features added by card before other player does his moves
     *
     * @param other player to customize
     */
    @Override
    public void beforeOtherMoving(Gamer other) {
    }

    /**
     * Features added by card after other player does his moves
     *
     * @param other player to customize
     */
    @Override
    public void afterOtherMoving(Gamer other) {
    }

    /**
     * Features added by card before other player starts building
     *
     * @param other player to customize
     */
    @Override
    public void beforeOtherBuilding(Gamer other) {
    }

    /**
     * Features added by card before other player starts building
     *
     * @param other player to customize
     */
    @Override
    public void afterOtherBuilding(Gamer other) {
    }
}
