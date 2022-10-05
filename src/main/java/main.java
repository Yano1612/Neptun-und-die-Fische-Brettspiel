import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class main {
    static int turn = 2;
    static int compensation = 0;
    static Tile Mover = null;
    static Display display = new Display();
    static Shell shell = new Shell(display);
    static Label lTurn = new Label(shell, SWT.NONE);
    static Tile[][] tiles = new Tile[5][5];
    static boolean stop = false;

    public static void main(String[] args) {
        // Erstellen der GUI
        ArrayList<Tile> tiles = new ArrayList<Tile>();

        shell.setText("Brettspiel");
        shell.setSize(500, 500);
        shell.open();


        lTurn.setText("Currently Active: Black");
        lTurn.setLocation(10, 370);
        lTurn.pack();
        int lenRow = 4;
        // erstellen der Spielfelder
        for (int n = 0; n <= lenRow; n++) {
            for (int i = 0; i <= lenRow; i++) {
                createTile(i * 70 + 10, n * 70 + 10, shell, tiles, n, i);
            }
        }


        // Mainloop
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

    static int counter = 0;

    public static void createTile(int x, int y, Shell shell, ArrayList<Tile> tiles, int row, int num) {
        // Ersteller eines Feldes
        Tile tile = new Tile(shell, SWT.NO_REDRAW_RESIZE, row, num, counter);
        main.tiles[row][num] = tile;
        tile.setBounds(x, y, 50, 50);
        tile.pack();
        counter += 1;
        tile.addPaintListener(new PaintListener() {
            @Override
            public void paintControl(PaintEvent e) {
                // Bemalen des Feldes
                Font font = new Font(shell.getDisplay(), "Arial", 14, SWT.BOLD);
                Font font2 = new Font(shell.getDisplay(), "Arial", 14, SWT.NONE);
                e.gc.drawRectangle(0, 0, 50, 50);
                if (tile.getState() == 1) {
                    e.gc.setFont(font2);
                    e.gc.drawText("W", 17, 15);

                } else if (tile.getState() == 2) {
                    e.gc.setFont(font);
                    e.gc.drawText("S", 17, 15);
                }
                font.dispose();

            }
        });
        tile.addMouseListener(new MouseListener() {
            @Override
            public void mouseDoubleClick(MouseEvent e) {
            }

            @Override
            public void mouseDown(MouseEvent e) {
                ArrayList<Tile> Adjacent = getAdjacentTiles(tile);
                // Ausführen des Zuges
                if (checkLegality(Adjacent, tile) & !stop) {
                    tile.setState(turn);
                    tile.redraw(); // Aktualisiert die Paint-Funktion
                    Mover.setState(0);
                    Mover.redraw();
                    if (turn == 1) {
                        turn = 2;
                    } else if (turn == 2) {
                        turn = 1;
                    }
                } else {
                    System.out.println("Illegal Move");
                }
                // Prüfen, ob jemand gewonnen hat
                int winner = checkWinner();
                if (winner != 0) {
                    stop = true;
                    if (winner == 1) {
                        lTurn.setText("Winner: White");
                    } else if (winner == 2) {
                        lTurn.setText("Winner: Black");
                    }
                }

                if (turn == 2 & !stop) {
                    lTurn.setText("Currently Active: Black");
                } else if (turn == 1 & !stop) {
                    lTurn.setText("Currently Active: White");
                }

            }

            @Override
            public void mouseUp(MouseEvent mouseEvent) {
            }
        });


    }
    public static ArrayList<Tile> getAdjacentTiles(Tile tile){
        // Erfassen der benachbarten Felder
        ArrayList<Tile> Adjacent = new ArrayList<Tile>();
        try {
            Adjacent.add(main.tiles[tile.row + 1][tile.num]);
        } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
            Adjacent.add(null);
        }
        try {
            Adjacent.add(main.tiles[tile.row - 1][tile.num]);
        } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
            Adjacent.add(null);
        }
        try {
            Adjacent.add(main.tiles[tile.row][tile.num + 1]);
        } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
            Adjacent.add(null);
        }
        try {
            Adjacent.add(main.tiles[tile.row][tile.num - 1]);
        } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
            Adjacent.add(null);
        }
        return Adjacent;
    }
    public static boolean checkLegality(ArrayList<Tile> Adjacent, Tile tile) {
        // Prüfen der Legalität eines Zuges
        boolean legal = false;
        for (int i = 0; i <= Adjacent.size() - 1; i++) {
            if (Adjacent.get(i) != null) {
                if (Adjacent.get(i).getState() == turn) {
                    legal = true;
                    Mover = Adjacent.get(i);
                    break;
                }
            }
        }
        if (legal & turn == 2 & tile.getState() != 1) {
            legal = false;
        } else if (legal & turn == 1 & tile.getState() != 0) {
            legal = false;
        }
        return legal;
    }
    public static int checkWinner() {
        // Prüfen, ob jemand gewonnen hat
        int won = 0;
        int blacksVertical = 0;
        int blacksHorizontal = 0;
        ArrayList<Tile> blackTiles = new ArrayList<Tile>();
        for (int i = 0; i < tiles.length; i++) {
            blacksVertical = 0;
            blacksHorizontal = 0;
            for (int k = 0; k < tiles.length; k++) {
                if (tiles[i][k].getState() == 2) {
                    blacksHorizontal += 1;
                    blackTiles.add(tiles[i][k]);
                    if (blacksHorizontal == 3) {
                        won = 1;
                        break;
                    }
                }
                if (tiles[k][i].getState() == 2) {
                    blacksVertical += 1;
                    if (blacksVertical == 3) {
                        won = 1;
                        break;
                    }
                }
            }
        }
        System.out.println(blackTiles);
        boolean possibleMoves = false;
        for(int k = 0; k< 3;k++) {
            ArrayList<Tile> Adj = getAdjacentTiles(blackTiles.get(k));
            for (int i = 0; i < 3; i++) {
                    if(Adj.get(i) != null ) {
                        if (Adj.get(i).getState() == 1) {
                            possibleMoves = true;
                        }
                    }
            }
        }
        if(!possibleMoves){
            won = 2;
        }
        return won;
    }

}