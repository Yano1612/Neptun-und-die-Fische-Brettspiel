import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.widgets.Label;

import java.util.ArrayList;

public class main {
    // Declaration of necessary variables
    static int turn = 2;
    static Tile Mover = null;
    static Display display = new Display();
    static Shell shell = new Shell(display);
    static Label lTurn = new Label(shell, SWT.NONE);
    static Tile[][] tiles = new Tile[5][5];
    static boolean stop = false;

    static Label lHints = new Label(shell, SWT.NONE);
    static boolean inactivePieceSelected = false;

    public static void main(String[] args) {
        // Erstellen der GUI
        shell.setText("Board Game");
        shell.setSize(500, 500);
        shell.open();


        lTurn.setText("Currently Active: Black   ");
        lTurn.setBounds(10, 370, 100, 30);
        lTurn.pack();

        lHints.setText("                                                                                                       ");
        lHints.setBounds(10, 400, 100, 30);
        lHints.pack();

        int lenRow = 4;
        // erstellen der Spielfelder
        for (int n = 0; n <= lenRow; n++) {
            for (int i = 0; i <= lenRow; i++) {
                createTile(i * 70 + 10, n * 70 + 10, shell, n, i);
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

    public static void createTile(int x, int y, Shell shell, int row, int num) {
        // Creating tiles
        Tile tile = new Tile(shell, SWT.NO_REDRAW_RESIZE, row, num, counter);
        main.tiles[row][num] = tile;
        tile.setBounds(x, y, 50, 50);
        tile.pack();
        counter += 1;
        tile.addPaintListener(new PaintListener() {
            @Override
            public void paintControl(PaintEvent e) {
                // Painting the Tiles
                Font font = new Font(shell.getDisplay(), "Arial", 14, SWT.BOLD);
                Font font2 = new Font(shell.getDisplay(), "Arial", 14, SWT.NONE);
                e.gc.drawRectangle(0, 0, 50, 50);
                if (tile.getSelected()) {
                    e.gc.setBackground(e.display.getSystemColor(SWT.COLOR_RED));
                    e.gc.fillRectangle(0, 0, 50, 50);
                    tile.setSelected(false);
                }
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
                // Selecting a piece by Right-Clicking
                inactivePieceSelected = false;
                if (e.button == 3) {
                    if (tile.getState() == turn) {
                        tile.setSelected(true);
                        tile.redraw();
                        Mover = tile;
                        System.out.println(getAdjacentTiles(Mover));
                    } else {
                        lHints.setText("Selected piece is not active");
                        inactivePieceSelected = true;
                    }
                } else if (Mover != null) {
                    ArrayList<Tile> Adjacent = getAdjacentTiles(tile);

                    // Execution of the Move
                    if (checkLegality(Adjacent, tile) & !stop) {
                        tile.setState(turn);
                        tile.redraw(); // Updates the Paint-Function
                        Mover.setState(0);
                        Mover.redraw();
                        if (turn == 1) {
                            turn = 2;
                        } else if (turn == 2) {
                            turn = 1;
                        }
                    } else {
                        lHints.setText("Illegal Move");
                        Mover.setSelected(false);
                        Mover.redraw();

                    }

                    if (turn == 2 & !stop) {
                        lTurn.setText("Currently Active: Black   ");
                    } else if (turn == 1 & !stop) {
                        lTurn.setText("Currently Active: White   ");

                    }
                    //Reset the moving Piece
                    Mover = null;

                    // Checking, if a player has won
                    int winner = checkWinner();
                    if (winner != 0) {
                        stop = true;
                        if (winner == 1) {
                            lTurn.setText("Winner: White   ");
                        } else if (winner == 2) {
                            lTurn.setText("Winner: Black   ");
                        }
                    }
                } else {
                    if (!inactivePieceSelected) {
                        lHints.setText("No piece selected (Select piece by Right-Clicking it.)");
                        tile.setSelected(false);
                        tile.redraw();

                    }
                }
            }

            @Override
            public void mouseUp(MouseEvent mouseEvent) {
            }
        });
    }

    public static ArrayList<Tile> getAdjacentTiles(Tile tile) {
        // Creating a list of Adjacent tiles
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
        // Checking the legality of a move
        boolean legal = false;
        for (int i = 0; i <= Adjacent.size() - 1; i++) {
            if (Adjacent.get(i) != null) {
                if (Adjacent.get(i) == Mover) {
                    legal = true;
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
        // Checking if white has won
        int won = 0;
        int blacksVertical;
        int blacksHorizontal;
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
        // Checking if Black has won
        boolean possibleMoves = false;
        for (int k = 0; k < 3; k++) {
            ArrayList<Tile> Adj = getAdjacentTiles(blackTiles.get(k));
            for (int i = 0; i < 3; i++) {
                if (Adj.get(i) != null) {
                    if (Adj.get(i).getState() == 1) {
                        possibleMoves = true;
                    }
                }
            }
        }
        if (!possibleMoves) {
            won = 2;
        }
        return won;
    }

}