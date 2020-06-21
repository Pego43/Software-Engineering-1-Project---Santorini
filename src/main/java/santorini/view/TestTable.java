package santorini.view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import santorini.model.Cell;
import santorini.model.Gamer;
import santorini.model.Pawn;
import santorini.model.Table;

import java.util.ArrayList;

public class TestTable {

    @FXML
    private GridPane gridPane;

    @FXML
    private Button sendMove, jumpMove;

    @FXML
    private TextArea textArea;

    private Button[][] buttons;
    private Stage stage;
    private int c1, c2;
    private Lighting lighting = new Lighting();
    private DropShadow shadow = new DropShadow();

    public void setStage(Stage primaryStage) {
        this.stage = primaryStage;
    }
    //TODO :
    // Popolare la table iniziale di imageView vuote
    // Illuminare le imageView adiacenti
    // Spostamento pedina con secondo click

    public void initialize() {
        int x = -2, y = -2;
        buttons = new Button[5][5];
        Table table = new Table();
        Gamer gamer = new Gamer(null, "Pippo", 0, null, null);
        Gamer gamer2 = new Gamer(null, "Pno", 1, null, null);
        Gamer gamer3 = new Gamer(null, "Pano", 2, null, null);
        Pawn p0 = new Pawn();
        Pawn p1 = new Pawn();
        Pawn p2 = new Pawn();
        Pawn p3 = new Pawn();
        Pawn p4 = new Pawn();
        Pawn p5 = new Pawn();

        p0.setIdPawn(0);
        p0.setIdGamer(0);
        p1.setIdPawn(1);
        p1.setIdGamer(0);

        p2.setIdPawn(0);
        p2.setIdGamer(1);
        p3.setIdPawn(1);
        p3.setIdGamer(1);

        p4.setIdPawn(0);
        p4.setIdGamer(2);
        p5.setIdPawn(1);
        p5.setIdGamer(2);

        table.setACell(0, 3, 2, false, false, p0);
        table.setACell(0, 0, 0, false, false, p1);
        table.setACell(1, 1, 1, false, false, p2);
        table.setACell(2, 2, 2, false, false, p3);
        table.setACell(2, 3, 3, false, false, p4);
        table.setACell(2, 1, 1, false, false, p5);
        table.setACell(4, 4, 3, false, true, null);
        table.setACell(0, 4, 3, false, true, null);
        table.setACell(1, 4, 2, false, true, null);
        table.setACell(0, 4, 1, false, true, null);
        table.setACell(4, 0, 0, false, true, null);
        table.setACell(1, 2, 1, true, false, null);
        table.setACell(3, 0, 2, true, false, null);
        table.setACell(4, 2, 3, true, false, null);


        //startTable(table,buttons);
        printTableStatus(table, buttons);
        lightMyPawns(table, buttons, gamer.getId());
    }

    public void startTable(Table t, Button[][] bt) {
        printTableStatus(t, bt);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                bt[i][j].setOnMousePressed(e -> {
                    Button button;
                    button = (Button) e.getSource();
                    int x = GridPane.getRowIndex(button);
                    int y = GridPane.getColumnIndex(button);
                    bt[x][y].setOnMouseEntered(null);
                    bt[x][y].setOnMouseExited(null);
                    bt[x][y].setStyle("-fx-border-color:blue");
                    System.out.println("x:" + x + "\ty:" + y);
                });
                bt[i][j].setOnMouseEntered(e -> {
                    Button button;
                    button = (Button) e.getSource();
                    int x = GridPane.getRowIndex(button);
                    int y = GridPane.getColumnIndex(button);
                    bt[x][y].setStyle("-fx-border-color:yellow");
                });
                bt[i][j].setOnMouseExited(e -> {
                    Button button;
                    button = (Button) e.getSource();
                    int x = GridPane.getRowIndex(button);
                    int y = GridPane.getColumnIndex(button);
                    bt[x][y].setStyle("-fx-border-color:trasparent");
                });
                bt[i][j].setOnMouseClicked(e -> {
                    Button button;
                    button = (Button) e.getSource();
                    int x = GridPane.getRowIndex(button);
                    int y = GridPane.getColumnIndex(button);
                    bt[x][y].setOnMousePressed(null);
                    bt[x][y].setStyle("-fx-border-color:red");
                });
            }
        }
    }

    public int summ(int i) {
        return i++;
    }

    /**
     * //TODO: DA SISTEMARE CON LE IMMAGINI APPROPRIATE
     * Metodo per popolare la table
     *
     * @param t  table da gioco
     * @param bt tabella di bottoni
     */
    public void printTableStatus(Table t, Button[][] bt) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (t.getTableCell(i, j).isComplete()) {
                    bt[i][j] = new Button();
                    BackgroundImage myBI = new BackgroundImage(new Image(String.valueOf(getClass().getClassLoader().getResource("images/Levels/L" + t.getTableCell(i, j).getLevel() + "+Dome.png")), 75, 75, true, false),
                            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                            BackgroundSize.DEFAULT);
                    bt[i][j].setBackground(new Background(myBI));
                    bt[i][j].setMaxSize(65, 65);
                    bt[i][j].setMinSize(65, 65);
                    gridPane.add(bt[i][j], j, i);
                    gridPane.setHalignment(bt[i][j], HPos.CENTER);
                    gridPane.setValignment(bt[i][j], VPos.CENTER);
                } else if (t.getTableCell(i, j).getPawn() != null) {
                    //mettere immagine con livello + cupola con la seguente sintassi
                    //"images/.../I_" + t.getTableCell(i,j).getLevel() + "_" + t.getTableCell(i,j).getPawn().getIdGamer()" + "_" + t.getTableCell(i,j).getPawn().getIdPawn() + ".png"
                    bt[i][j] = new Button();
                    BackgroundImage myBI = new BackgroundImage(new Image(String.valueOf(getClass().getClassLoader().getResource("images/LevelAndPawns/L" + t.getTableCell(i, j).getLevel() + "+" + t.getTableCell(i, j).getPawn().getIdGamer() + "+" + t.getTableCell(i, j).getPawn().getIdPawn() + ".png")), 75, 75, true, false),
                            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                            BackgroundSize.DEFAULT);
                    bt[i][j].setBackground(new Background(myBI));
                    bt[i][j].setMaxSize(65, 65);
                    bt[i][j].setMinSize(65, 65);
                    gridPane.add(bt[i][j], j, i);
                    gridPane.setHalignment(bt[i][j], HPos.CENTER);
                    gridPane.setValignment(bt[i][j], VPos.CENTER);
                } else if (t.getTableCell(i, j).getLevel() != 0) {
                    //mettere immagine con solo livello con la seguente sintassi
                    //"images/.../I_" + t.getTableCell(i,j).getLevel() + ".png"
                    BackgroundImage myBI = new BackgroundImage(new Image(String.valueOf(getClass().getClassLoader().getResource("images/Levels/L" + t.getTableCell(i, j).getLevel() + ".png")), 75, 75, true, false),
                            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                            BackgroundSize.DEFAULT);
                    bt[i][j] = new Button();
                    bt[i][j].setBackground(new Background(myBI));
                    bt[i][j].setMaxSize(65, 65);
                    bt[i][j].setMinSize(65, 65);
                    gridPane.add(bt[i][j], j, i);
                    gridPane.setHalignment(bt[i][j], HPos.CENTER);
                    gridPane.setValignment(bt[i][j], VPos.CENTER);
                } else {
                    //mettere immagine vuota se il livello è 0
                    bt[i][j] = new Button();
                    bt[i][j].setAlignment(Pos.BOTTOM_CENTER);
                    bt[i][j].setBackground(Background.EMPTY);
                    bt[i][j].setMaxSize(65, 65);
                    bt[i][j].setMinSize(65, 65);
                    gridPane.add(bt[i][j], j, i);
                    gridPane.setHalignment(bt[i][j], HPos.CENTER);
                    gridPane.setValignment(bt[i][j], VPos.CENTER);
                }
            }
        }
    }


    /**
     * //TODO: DA SISTEMARE
     * Metodo che mi illumina solo le pedine del giocatore che sta giocando
     * Quando clicca sulla sua pedina gli si illuminano quelle attorno in cui può muoversi
     *
     * @param t       table da gioco
     * @param bt      tabella di bottoni
     * @param idGamer id del giocatore corrente
     */
    public void lightMyPawns(Table t, Button[][] bt, int idGamer) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Cell cell = t.getTableCell(i, j);
                if (cell.getPawn() != null && cell.getPawn().getIdGamer() == idGamer && cell.getPawn().getICanPlay()) {
                    //se: esiste la pedina nella cella && la pedina è della stessa squadra del gamer && la pedina può giocare
                    //rendere cliccabile e illuminata la cella
                    bt[i][j].setStyle("-fx-border-color:yellow");
                    bt[i][j].setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            Button bOne;
                            bOne = (Button) e.getSource();
                            int x = GridPane.getRowIndex(bOne);
                            int y = GridPane.getColumnIndex(bOne);
                            for (int i = 0; i < 5; i++) {
                                for (int j = 0; j < 5; j++) {
                                    Cell cell = t.getTableCell(i, j);
                                    if (cell.getPawn() != null && cell.getPawn().getIdGamer() == idGamer) {
                                        if (i == x && j == y) {
                                            bt[i][j].setStyle("-fx-border-color:red");
                                        } else {
                                            bt[i][j].setStyle("-fx-border-color:blue");
                                        }
                                    } else {
                                        bt[i][j].setStyle("-fx-border-color:transparent");
                                        bt[i][j].setOnMouseClicked(null);
                                    }
                                }
                            }
                            System.out.print("***Id pedina: " + t.getTableCell(x, y).getPawn().getIdPawn());
                            System.out.println("\tId Giocatore: " + t.getTableCell(x, y).getPawn().getIdGamer() + "***\n");
                            accessibleCells(t, t.getTableCell(x, y), bt, bOne);
                        }
                    });
                }
            }
        }
    }

    /**
     * //TODO: DA SISTEMARE
     * Metodo che illumina le caselle adiacenti possibili al movimento
     * @param t      tavolo da gioco
     * @param myCell posizione della pedina scelta
     * @param bt     tabella di immagini
     */
    //Chiamo il metodo in lightMyPawns
    public void accessibleCells(Table t, Cell myCell, Button[][] bt, Button myButton) {
        ArrayList<Cell> cells = new ArrayList<>();
        int x = myCell.getX();
        int y = myCell.getY();
        myButton.setStyle("-fx-border-color:red");
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                //controllo se la casella esiste
                if (((i != x) || (j != y)) && (i >= 0) && (i <= 4) && (j >= 0) && (j <= 4)) {
                    //controllo se non c'è la cupola
                    if ((!t.getTableCell(i, j).isComplete())) {
                        cells.add(t.getTableCell(i, j));
                    }
                }
            }
        }
        for (Cell lightMe : cells) {
            bt[lightMe.getX()][lightMe.getY()].setStyle("-fx-border-color:yellow");
            bt[lightMe.getX()][lightMe.getY()].setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    Button bTwo;
                    bTwo = (Button) e.getSource();
                    int x = GridPane.getRowIndex(bTwo);
                    int y = GridPane.getColumnIndex(bTwo);
                    bt[myCell.getX()][myCell.getY()].setStyle("-fx-border-color:red");
                    int l = cells.size();
                    for (int k = 0; k < l; k++) {
                        if (cells.get(k).getX() == x && cells.get(k).getY() == y) {
                            bt[cells.get(k).getX()][cells.get(k).getY()].setStyle("-fx-border-color:red");
                        } else {
                            bt[cells.get(k).getX()][cells.get(k).getY()].setStyle("-fx-border-color:yellow");
                        }
                    }
                    mySelection(bt, t.getTableCell(x, y), t);
                    //Building per ora commentata
                    //Building(t,bt,t.getTableCell(x,y));
                }
            });

        }
    }

    /**
     * Metodo che printa la casella cliccata
     *
     * @param bt       tavola di bottoni
     * @param moveCell cella cliccata
     * @param t        table
     */
    public void mySelection(Button[][] bt, Cell moveCell, Table t) {
        Button buttonMovement;
        buttonMovement = bt[moveCell.getX()][moveCell.getY()];
        System.out.println("Row: " + GridPane.getRowIndex(buttonMovement));
        System.out.println("Column: " + GridPane.getColumnIndex(buttonMovement));
    }

    /**
     * Metodo Building
     * Dopo il movimento prende la casella in cui si è spostata la pedina (start) e cerca le caselle adaicenti per la costruzione
     *
     * @param t     tavolo
     * @param bt    tabella di bottoni
     * @param start nuova posizione della pedina
     */
    public void Building(Table t, Button[][] bt, Cell start) {
        System.out.println("**Costruzione**\n");
        int x = start.getX();
        int y = start.getY();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i == x && j == y) {
                    bt[i][j].setStyle("-fx-border-color:white");
                } else {
                    bt[i][j].setStyle("-fx-border-color:trasparent");
                    bt[i][j].setOnMouseClicked(null);
                }
            }
        }
        ArrayList<Cell> cells = new ArrayList<>();
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                //controllo se la casella esiste
                if (((i != x) || (j != y)) && (i >= 0) && (i <= 4) && (j >= 0) && (j <= 4)) {
                    //controllo se non c'è la cupola
                    if ((!t.getTableCell(i, j).isComplete())) {
                        cells.add(t.getTableCell(i, j));
                    }
                }
            }
        }
        for (Cell lightMe : cells) {
            bt[lightMe.getX()][lightMe.getY()].setStyle("-fx-border-color:blue");
            bt[lightMe.getX()][lightMe.getY()].setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    Button bTwo;
                    bTwo = (Button) e.getSource();
                    int xs = GridPane.getRowIndex(bTwo);
                    int ys = GridPane.getColumnIndex(bTwo);
                    bt[start.getX()][start.getY()].setStyle("-fx-border-color:white");
                    bt[start.getX()][start.getY()].setOnMouseClicked(null);
                    int l = cells.size();
                    for (int k = 0; k < l; k++) {
                        if (cells.get(k).getX() == xs && cells.get(k).getY() == ys) {
                            bt[cells.get(k).getX()][cells.get(k).getY()].setStyle("-fx-border-color:red");
                        } else {
                            bt[cells.get(k).getX()][cells.get(k).getY()].setStyle("-fx-border-color:blue");
                        }
                    }
                    mySelection(bt, t.getTableCell(xs, ys), t);
                }
            });
        }

    }
}