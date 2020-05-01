package santorini.model;

import java.io.Serializable;

public class Mossa implements Serializable {
    public static enum Action {
        MOVE,
        BUILD,
    }

    private Action action;
    private int targetX;
    private int targetY;
    private int idPawn;


    public Mossa(Action action, int idPawn, int targetX, int targetY) {
        this.action = action;
        this.targetX = targetX;
        this.targetY = targetY;
        this.idPawn = idPawn;
    }

    /**
     * method getAction
     *
     * @return action
     */
    public Action getAction() {
        return action;
    }

    /**
     * method getTargetX
     * @return row
     */
    public int getTargetX() {
        return targetX;
    }

    /**
     * method getTargetY
     * @return column
     */
    public int getTargetY() {
        return targetY;
    }

    /**
     * method getIdPawn
     * @return id of the pawn
     */
    public int getIdPawn() {
        return idPawn;
    }

    /**
     * method setAction
     *
     * @param action .
     */
    public void setAction(Action action) {
        this.action = action;
    }

    /**
     * method setTargetX
     *
     * @param targetX .
     */
    public void setTargetX(int targetX) {
        this.targetX = targetX;
    }

    /**
     * method setIdPawn
     *
     * @param targetY .
     */
    public void setTargetY(int targetY) {
        this.targetY = targetY;
    }

    /**
     * method setIdPawn
     *
     * @param idPawn id pawn
     */
    public void setIdPawn(int idPawn) {

        this.idPawn = idPawn;
    }

    /**
     * method setMyMossa
     *
     * @param a action MOVE or BUILD
     * @param i id Pawn
     * @param x row
     * @param y column
     * @return my Mossa
     */
    public Mossa setMyMossa(Action a, int i, int x, int y) {
        Mossa m;
        if (a.equals(Action.MOVE)) {
            return m = new Mossa(Action.MOVE, i, x, y);
        } else {
            if (a.equals(Action.BUILD)) {
                return m = new Mossa(Action.BUILD, i, x, y);
            } else {
                return m = null;
            }
        }
    }

    //TODO review this method
    /**
     public Mossa InputMossa() throws IOException {
     BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
     System.out.println("Inserisci azione");
     String a = input.readLine();
     System.out.println("Inserisci pedina");
     String  s = input.readLine();
     int i = Integer.parseInt(s);
     System.out.println("Inserisci riga");
     s = input.readLine();
     int x = Integer.parseInt(s);
     System.out.println("Inserisci colonna");
     s = input.readLine();
     int y = Integer.parseInt(s);
     if(a.equals("MOVE")){
     return new Mossa(Action.MOVE,i,x,y);
     }else{
     if(a.equals("BUILD")){
     return new Mossa(Action.BUILD,i,x,y);
     }
     else{
     return null;
     }
     }
     }
     */

}


